package com.main.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.DTO.SaleDTO;
import com.main.models.Sale;
import com.main.repositories.SalesRepository;

@Service
@Transactional(readOnly = true)
public class SaleService implements ISaleService {

	@Autowired
	private SalesRepository repo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CompletableFuture<SaleDTO> createAdmin(SaleDTO sale) {

		try {

			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Sale entity = mapper.map(sale, Sale.class);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String dateString = format.format(new Date());

			Date date = format.parse(dateString);

			entity.setDateCreated(date);

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
	public CompletableFuture<SaleDTO> update(SaleDTO sale) {
		
		
		return null;
	}

	@Override
	public boolean delete(String id) {
		
		
		return false;
	}

	@Override
	public CompletableFuture<SaleDTO> getSaleDetails(String id) {
		
		
		return null;
	}

	@Override
	public CompletableFuture<List<SaleDTO>> getSales() {
		
		
		return null;
	}

}
