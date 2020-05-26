package com.main.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.DTO.SaleDTO;
import com.main.models.Customer;
import com.main.models.Product;
import com.main.models.Sale;
import com.main.models.SoldProduct;
import com.main.repositories.CustomersRepository;
import com.main.repositories.ProductsRepository;
import com.main.repositories.SalesRepository;
import com.main.repositories.SoldProductsRepository;

@Service
@Transactional(readOnly = true)
public class SalesService implements ISalesService {

	@Autowired
	private SalesRepository repo;

	@Autowired
	private CustomersRepository customerRepo;

	@Autowired
	private ProductsRepository prodRepo;

	@Autowired
	private SoldProductsRepository soldProdRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<SaleDTO> createSale(SaleDTO sale) {

		try {

			List<String> products = new ArrayList<>();

			sale.getPurchasedProducts().forEach(x -> products.add(x.getId()));

			List<Product> productsEntities = prodRepo.findAllById(products);

			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Sale entity = mapper.map(sale, Sale.class);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String dateString = format.format(new Date());

			Date date = format.parse(dateString);

			entity.setDateCreated(date);

			double sum = sale.getPurchasedProducts().stream().mapToDouble(x -> x.getPrice()).sum();

			entity.setSumOfSale(sum);

			Customer customer = this.customerRepo.findById(sale.getCustomerId()).get();

			entity.setCustomer(customer);

			repo.saveAndFlush(entity);

			List<SoldProduct> currentProducts = new ArrayList<>();

			for (int i = 0; i < productsEntities.size(); i++) {

				SoldProduct currentProd = new SoldProduct();

				currentProd.setDateCreated(date);

				currentProd.setProduct(productsEntities.get(i));

				currentProd.setSale(entity);

				this.saveSoldProduct(currentProd);

				currentProducts.add(this.soldProdRepo.findById(currentProd.getId()).get());

			}

			entity.setSoldProducts(currentProducts);

			repo.saveAndFlush(entity);

			SaleDTO returnDto = mapper.map(entity, SaleDTO.class);

			returnDto.setSoldProducts(entity.getSoldProducts());

			returnDto.setCustomerId(entity.getCustomer().getId());

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
		/*
		 * this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
		 * ;
		 * 
		 * Sale saleEntity = this.repo.findById(sale.getId()).get(); SaleDTO
		 * returnObject = null;
		 */
		try {

			this.repo.deleteById(sale.getId());

			sale.setId(null);
			/*
			 * this.mapper.map(saleEntity, sale);
			 * 
			 * returnObject = new SaleDTO();
			 * 
			 * Customer customer = this.customerRepo.findById(sale.getCustomerId()).get();
			 * 
			 * saleEntity.setCustomer(customer);
			 * 
			 * this.mapper.map(sale, returnObject);
			 * 
			 * this.repo.saveAndFlush(saleEntity);
			 */

			return CompletableFuture.completedFuture(this.createSale(sale).get());

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
	public CompletableFuture<SaleDTO> getSaleDetails(String id) {

		SaleDTO saleDetails = null;
		Optional<Sale> sale = null;
		try {

			sale = this.repo.findById(id);
			
			//Hibernate.initialize(sale.get().getSoldProducts());

			if (sale.isPresent()) {

				saleDetails = this.mapper.map(sale.get(), SaleDTO.class);

				saleDetails.setSoldProducts(sale.get().getSoldProducts());

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

		
		/*
		 * Type listType = new TypeToken<List<SaleDTO>>() { }.getType();
		 */

		try {
			
			List<SaleDTO> sales = new ArrayList<SaleDTO>();

			List<Sale> currentSales = this.repo.findAll();

			for (int i = 0; i < currentSales.size(); i++) {

				Hibernate.initialize(currentSales.get(i).getSoldProducts());

				SaleDTO dto = new SaleDTO();

				dto.setCustomer(currentSales.get(i).getCustomer());
				dto.setCustomerId(currentSales.get(i).getCustomer().getId());
				dto.setDateCreated(currentSales.get(i).getDateCreated());
				dto.setId(currentSales.get(i).getId());
				dto.setSoldProducts(currentSales.get(i).getSoldProducts());
				dto.setSumOfSale(currentSales.get(i).getSumOfSale());

				sales.add(dto);

			}

			return CompletableFuture.completedFuture(sales);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return null;
	}

	@Override
	public CompletableFuture<List<SaleDTO>> getSalesForCurrentUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveSoldProduct(SoldProduct product) {

		this.soldProdRepo.saveAndFlush(product);
	}

}
