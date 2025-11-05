package com.ads.ecomproj1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ads.ecomproj1.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

	
}
