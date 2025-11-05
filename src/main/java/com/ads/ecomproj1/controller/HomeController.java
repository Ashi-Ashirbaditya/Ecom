package com.ads.ecomproj1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ads.ecomproj1.model.Product;
import com.ads.ecomproj1.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/products")
	public String showProducts(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		model.addAttribute("newProduct", new Product());
		return findPaginated(1, "id", "asc", model);
	}

	@PostMapping("/products")
	public String addProduct(@ModelAttribute Product newProduct) {
		productService.saveProduct(newProduct);
		return "redirect:/products";
	}

	@GetMapping("/addProduct")
	public String showAddProductForm(Model model) {
		model.addAttribute("newProduct", new Product());
		return "addProduct";
	}

	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productService.deleteProductById(id);
		return "redirect:/products";
	}

	@GetMapping("/updateProduct/{id}")
	public String showEditProductForm(@PathVariable("id") int id, Model model) {
		Product existingProduct = productService.getProductById((long) id);
		model.addAttribute("product", existingProduct);
		return "updateProduct";
	}

	@PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute("product") Product product) {
		productService.updateProduct(product);
		return "redirect:/products";
	}

//Pagination and Sorting
	@GetMapping("/page/{pageNo}")

	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {

		int pageSize = 14;

		Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Product> listProducts = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("products", listProducts);

		return "products";
	}

}
