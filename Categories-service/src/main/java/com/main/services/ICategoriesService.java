package com.main.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.main.DTO.CategoryDTO;

public interface ICategoriesService {
	
public CompletableFuture<CategoryDTO> createCategory(CategoryDTO category);
	
	public CompletableFuture<CategoryDTO> update(CategoryDTO category);
	
	public Boolean delete(String id);
	
	public CompletableFuture<CategoryDTO> getCategoryDetails(String id);
	
	public CompletableFuture<List<CategoryDTO>> getCategories();

}
