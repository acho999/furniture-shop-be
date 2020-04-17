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

import com.main.DTO.OrderDTO;
import com.main.models.Customer;
import com.main.models.Order;
import com.main.repositories.CustomerRepository;
import com.main.repositories.OrdersRepository;

@Service
@Transactional(readOnly = true)
public class OrdersService implements IOrdersService {

	@Autowired
	private OrdersRepository repo;
	
	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<OrderDTO> createOrder(OrderDTO order) {

		try {

			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Order entity = mapper.map(order, Order.class);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String dateString = format.format(new Date());

			Date date = format.parse(dateString);

			entity.setDateCreated(date);
			
			Customer customer = this.customerRepo.findById(order.getCustomerId()).get();
			
			entity.setCustomer(customer);

			repo.saveAndFlush(entity);

			OrderDTO returnDto = mapper.map(entity, OrderDTO.class);

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
	public CompletableFuture<OrderDTO> update(OrderDTO order) {

		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Order orderEntity = this.repo.findById(order.getId()).get();
		OrderDTO returnObject = null;

		try {

			this.mapper.map(orderEntity, order);

			returnObject = new OrderDTO();
			
            Customer customer = this.customerRepo.findById(order.getCustomerId()).get();
			
			orderEntity.setCustomer(customer);

			this.mapper.map(order, returnObject);

			this.repo.saveAndFlush(orderEntity);

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
		Optional<Order> orderOptional = null;

		try {
			this.repo.deleteById(id);

			orderOptional = this.repo.findById(id);

			if (orderOptional.get() != null) {
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
	public CompletableFuture<OrderDTO> getOrderDetails(String id) {

		OrderDTO orderDetails = null;
		Order order = null;
		try {
			
			order = this.repo.findById(id).get();
			
			if (order != null) {
				
				orderDetails = this.mapper.map(order, OrderDTO.class);
				
				return CompletableFuture.completedFuture(orderDetails);
				
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
	public CompletableFuture<List<OrderDTO>> getOrders() {
		
		List<OrderDTO> orders = new ArrayList<OrderDTO>();
		
		Type listType = new TypeToken<List<OrderDTO>>() {}.getType();
		
		try {
		
			orders = this.mapper.map(this.repo.findAll(),listType);
			
			return CompletableFuture.completedFuture(orders);
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return null;
	}

}
