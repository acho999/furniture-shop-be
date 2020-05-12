package com.main.services;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.main.DTO.ProductDTO;
import com.main.models.Category;
import com.main.models.Product;
import com.main.repositories.CategoriesRepository;
import com.main.repositories.ProductsRepository;

import javassist.NotFoundException;

@Service
@Transactional(readOnly = true)
public class ProductsService implements IProductsService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ProductsRepository repo;
	
	@Autowired
	private CategoriesRepository categoriesRepo;

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<ProductDTO> createProduct(ProductDTO product) {

		try {

			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Product entity = mapper.map(product, Product.class);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String dateString = format.format(new Date());

			Date date = format.parse(dateString);

			entity.setDateCreated(date);
			
			Category category = this.categoriesRepo.findById(product.getCategoryId()).get();
			
			entity.setCategory(category);

			repo.saveAndFlush(entity);

			ProductDTO returnDto = mapper.map(entity, ProductDTO.class);

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
	public CompletableFuture<ProductDTO> update(ProductDTO product) {

		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Product productEntity = this.repo.findById(product.getId()).get();
		
		ProductDTO returnObject = null;

		try {

			this.mapper.map(product,productEntity);
			
			Category category = this.categoriesRepo.findById(product.getCategoryId()).get();

            productEntity.setCategory(category);

			returnObject = new ProductDTO();

			this.mapper.map(product, returnObject);

			this.repo.saveAndFlush(productEntity);

			return CompletableFuture.completedFuture(returnObject);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return CompletableFuture.completedFuture(returnObject);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(String id) {


		try {
			
			this.repo.deleteById(id);

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
	public CompletableFuture<ProductDTO> getProductDetails(String id) {

		ProductDTO productDetails = null;
		Optional<Product> order = null;
		try {

			order = this.repo.findById(id);

			if (order.isPresent()) {

				productDetails = this.mapper.map(order.get(), ProductDTO.class);

				return CompletableFuture.completedFuture(productDetails);

			} else {
				throw new NotFoundException("Product not found!");
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
	public CompletableFuture<List<ProductDTO>> getProducts() {
		
		List<ProductDTO> products = new ArrayList<ProductDTO>();

		Type listType = new TypeToken<List<ProductDTO>>() {
		}.getType();

		try {

			products = this.mapper.map(this.repo.findAll(), listType);

			return CompletableFuture.completedFuture(products);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return null;
	}

}
