package com.main.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.DTO.CategoryDTO;
import com.main.Repositories.CategoriesRepository;
import com.main.models.Category;

@Service
@Transactional(readOnly = true)
public class CategoriesService implements ICategoriesService{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoriesRepository repo;

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<CategoryDTO> createCategory(CategoryDTO category) {
		
		try {

			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Category entity = mapper.map(category, Category.class);

			repo.saveAndFlush(entity);

			CategoryDTO returnDto = mapper.map(entity, CategoryDTO.class);

			return CompletableFuture.completedFuture(returnDto);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<CategoryDTO> update(CategoryDTO category) {
		
		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Category categoryEntity = this.repo.findById(category.getId()).get();
		CategoryDTO returnObject = null;

		try {

			this.mapper.map(category, categoryEntity);

			returnObject = new CategoryDTO();

			this.mapper.map(category, returnObject);

			this.repo.saveAndFlush(categoryEntity);

			return CompletableFuture.completedFuture(returnObject);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return CompletableFuture.completedFuture(returnObject);
	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public boolean delete(String id) {
		
		Optional<Category> categoryOptional = null;

		try {
			this.repo.deleteById(id);

			categoryOptional = this.repo.findById(id);

			if (categoryOptional.get() != null) {
				return false;
			}

			return true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return true;
	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<CategoryDTO> getCategoryDetails(String id) {

		CategoryDTO categoryDetails = null;
		Category category = null;
		try {

			category = this.repo.findById(id).get();

			if (category != null) {

				categoryDetails = this.mapper.map(category, CategoryDTO.class);

				return CompletableFuture.completedFuture(categoryDetails);

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return null;
	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<List<CategoryDTO>> getCategories() {

		List<CategoryDTO> categories = new ArrayList<CategoryDTO>();

		Type listType = new TypeToken<List<CategoryDTO>>() {
		}.getType();

		try {

			categories = this.mapper.map(this.repo.findAll(), listType);

			return CompletableFuture.completedFuture(categories);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return null;
	}
	
}
