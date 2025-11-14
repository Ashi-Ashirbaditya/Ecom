package com.ads.ecomproj1.service;

import com.ads.ecomproj1.repo.CategoryRepo;
import com.ads.ecomproj1.model.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public List<Category> getAllCategory() {
        return categoryRepo.findAll();
    }

    public void saveCategory(Category category) {
        categoryRepo.save(category);
    }
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElse(null);
    }
    public void deleteCategoryById(Long id) {
        categoryRepo.deleteById(id);
    }

    public Page<Category> findPaginated2(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.categoryRepo.findAll(pageable);
    }
}
