package com.ads.ecomproj1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ads.ecomproj1.model.Product;
import com.ads.ecomproj1.repo.ProductRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo repo;

	public List<Product> getAllProducts() {
		return repo.findAll();
	}

	public void saveProduct(Product product) {
		product.setLastUpdated(LocalDateTime.now());
		repo.save(product);
	}
	
	public Product getProductById(long id) {
	    return repo.findById(id).orElse(null);
	}
	
	public void deleteProductById(long id) {
		repo.deleteById(id);
	}
	
	public void updateProduct(Product product) {
		product.setLastUpdated(LocalDateTime.now());
	    repo.save(product);
	}

	public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) {
		Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		Pageable pageable= PageRequest.of(pageNo -1, pageSize,sort);
		return this.repo.findAll(pageable);
	}
	
}
