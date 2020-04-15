package com.main.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.main.DTO.OrderDTO;


public interface IOrdersService {
	
	    public CompletableFuture<OrderDTO> createOrder(OrderDTO order);
		
		public CompletableFuture<OrderDTO> update(OrderDTO order);
		
		public boolean delete(String id);
		
		public CompletableFuture<OrderDTO> getOrderDetails(String id);
		
		public CompletableFuture<List<OrderDTO>> getOrders();

}
