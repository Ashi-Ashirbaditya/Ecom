package com.ads.ecomproj1.repo;

import com.ads.ecomproj1.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
}
