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

import com.main.DTO.SaleDTO;
import com.main.models.Customer;
import com.main.models.Sale;
import com.main.repositories.CustomersRepository;
import com.main.repositories.SalesRepository;

@Service
@Transactional(readOnly = true)
public class SalesService implements ISalesService {

	@Autowired
	private SalesRepository repo;
	
	@Autowired
	private CustomersRepository customerRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<SaleDTO> createSale(SaleDTO sale) {

		try {

			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Sale entity = mapper.map(sale, Sale.class);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String dateString = format.format(new Date());

			Date date = format.parse(dateString);

			entity.setDateCreated(date);
			
			Customer customer = this.customerRepo.findById(sale.getCustomerId()).get();
			
			entity.setCustomer(customer);

			repo.saveAndFlush(entity);

			SaleDTO returnDto = mapper.map(entity, SaleDTO.class);

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
	public CompletableFuture<SaleDTO> update(SaleDTO sale) {

		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Sale saleEntity = this.repo.findById(sale.getId()).get();
		SaleDTO returnObject = null;

		try {

			this.mapper.map(saleEntity, sale);

			returnObject = new SaleDTO();
			
			Customer customer = this.customerRepo.findById(sale.getCustomerId()).get();
			
			saleEntity.setCustomer(customer);

			this.mapper.map(sale, returnObject);

			this.repo.saveAndFlush(saleEntity);

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

		Optional<Sale> saleOptional = null;

		try {
			this.repo.deleteById(id);

			saleOptional = this.repo.findById(id);

			if (saleOptional.get() != null) {
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
	public CompletableFuture<SaleDTO> getSaleDetails(String id) {

		SaleDTO saleDetails = null;
		Sale sale = null;
		try {
			
			sale = this.repo.findById(id).get();
			
			if (sale != null) {
				
				saleDetails = this.mapper.map(sale, SaleDTO.class);
				
				return CompletableFuture.completedFuture(saleDetails);
				
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
	public CompletableFuture<List<SaleDTO>> getSales() {
		
		List<SaleDTO> sales = new ArrayList<SaleDTO>();
		Type listType = new TypeToken<List<SaleDTO>>() {}.getType();
		
		try {
		
			sales = this.mapper.map(this.repo.findAll(),listType);
			
			return CompletableFuture.completedFuture(sales);
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return null;
	}

}
