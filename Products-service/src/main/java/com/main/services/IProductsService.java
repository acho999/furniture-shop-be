package com.main.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.main.DTO.ProductDTO;


public interface IProductsService {
	
	public CompletableFuture<ProductDTO> createProduct(ProductDTO product);
	
	public CompletableFuture<ProductDTO> update(ProductDTO product);
	
	public boolean delete(String id);
	
	public CompletableFuture<ProductDTO> getProductDetails(String id);
	
	public CompletableFuture<List<ProductDTO>> getProducts();


}
