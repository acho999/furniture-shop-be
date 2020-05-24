package com.main.services;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.hibernate.Hibernate;
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
import com.main.models.OrderedProduct;
import com.main.models.Product;
import com.main.repositories.CustomerRepository;
import com.main.repositories.OrderedProductsRepository;
import com.main.repositories.OrdersRepository;
import com.main.repositories.ProductsRepository;

import javassist.NotFoundException;

@Service
@Transactional(readOnly = false)
public class OrdersService implements IOrdersService {

	@Autowired
	private OrdersRepository repo;

	@Autowired
	private ProductsRepository productRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private OrderedProductsRepository orderedProductsRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<OrderDTO> createOrder(OrderDTO order) {

		List<String> products = new ArrayList<>();

		order.getOrderedProducts().forEach(x -> products.add(x.getId()));

		List<Product> productsEntities = productRepo.findAllById(products);

		// Type listType = new TypeToken<List<Product>>() {}.getType();

		try {

			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Order entity = mapper.map(order, Order.class);

			double sum = order.getOrderedProducts().stream().mapToDouble(x -> x.getPrice()).sum();

			entity.setSumOfOrder(sum);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String dateString = format.format(new Date());

			Date date = format.parse(dateString);

			entity.setDateCreated(date);

			Customer customer = this.customerRepo.findById(order.getCustomerId()).get();

			entity.setCustomer(customer);
			
			repo.saveAndFlush(entity);
			
			List<OrderedProduct> currentProducts = new ArrayList<>();
			
			for (int i = 0; i < productsEntities.size(); i++) {

				OrderedProduct currentProd = new OrderedProduct();

				currentProd.setDateCreated(date);

				currentProd.setProduct(productsEntities.get(i));
				
				currentProd.setOrder(entity);

				this.saveOrderedProduct(currentProd);

				currentProducts.add(this.orderedProductsRepo.findById(currentProd.getId()).get());

			}
			
			entity.setOrderedProducts(currentProducts);
			
			repo.saveAndFlush(entity);
			
			//Hibernate.initialize(entity.getOrderedProducts());

			OrderDTO returnDto = mapper.map(entity, OrderDTO.class);

			returnDto.setOrderedProductEntities(entity.getOrderedProducts());

			returnDto.setCustomerId(customer.getId());

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

		/*
		 * this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
		 * ;
		 * 
		 * Order orderEntity = this.repo.findById(order.getId()).get(); OrderDTO
		 * returnObject = null;
		 */

		try {

			this.repo.deleteById(order.getId());

			/*
			 * this.mapper.map(orderEntity, order);
			 * 
			 * returnObject = new OrderDTO();
			 * 
			 * Customer customer = this.customerRepo.findById(order.getCustomerId()).get();
			 * 
			 * orderEntity.setCustomer(customer);
			 * 
			 * this.mapper.map(order, returnObject);
			 * 
			 * orderEntity.getOrderedProducts().clear();
			 * 
			 * this.repo.saveAndFlush(orderEntity);
			 * 
			 * List<String> products = new ArrayList<>();
			 * 
			 * order.getOrderedProducts().forEach(x->products.add(x.getId()));
			 * 
			 * List<Product> productsEntities = productRepo.findAllById(products);
			 * 
			 * 
			 * 
			 * orderEntity.setOrderedProducts(entities);
			 * 
			 * this.repo.saveAndFlush(orderEntity);
			 */

			return CompletableFuture.completedFuture(this.createOrder(order).get());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return CompletableFuture.completedFuture(null);

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
	public CompletableFuture<OrderDTO> getOrderDetails(String id) {

		OrderDTO orderDetails = null;
		Optional<Order> order = null;
		try {

			order = this.repo.findById(id);
			

			if (order.isPresent()) {

				Hibernate.initialize(order.get().getOrderedProducts());
				
				orderDetails = this.mapper.map(order.get(), OrderDTO.class);

				orderDetails.setOrderedProductEntities(order.get().getOrderedProducts());

				return CompletableFuture.completedFuture(orderDetails);

			} else {
				throw new NotFoundException("Order not found!");
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

		List<Order> currentOrders = this.repo.findAll();

		List<OrderDTO> orders = new ArrayList<OrderDTO>();

		Type listType = new TypeToken<List<OrderDTO>>() {
		}.getType();

		try {

			Hibernate.initialize(currentOrders.stream().map(x -> x.getOrderedProducts()));

			orders = this.mapper.map(currentOrders, listType);

			return CompletableFuture.completedFuture(orders);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return null;
	}

	@Override
	public void saveOrderedProduct(OrderedProduct product) {

		this.orderedProductsRepo.saveAndFlush(product);

	}

}
