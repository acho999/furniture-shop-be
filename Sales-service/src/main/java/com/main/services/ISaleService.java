package com.main.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.main.DTO.SaleDTO;

public interface ISaleService {
	
    public CompletableFuture<SaleDTO> createAdmin(SaleDTO sale);
	
	public CompletableFuture<SaleDTO> update(SaleDTO sale);
	
	public boolean delete(String id);
	
	public CompletableFuture<SaleDTO> getSaleDetails(String id);
	
	public CompletableFuture<List<SaleDTO>> getSales();


}
