package com.ads.ecomproj1.controller;

import com.ads.ecomproj1.model.Product;
import com.ads.ecomproj1.service.ProductService;
import com.ads.ecomproj1.model.Category;
import com.ads.ecomproj1.service.CategoryService;

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

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String home() {
        return "index";
    }
    //Show Products
    @GetMapping("/products")
    public String showProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("newProduct", new Product());
        return findPaginated(1, "id", "asc", model);
    }
    //Show category
    @GetMapping("/category")
    public String showCategory(Model model) {
        model.addAttribute("category", categoryService.getAllCategory());
        model.addAttribute("newCategory", new Category());
        return findPaginated2(1, "id", "asc", model);
    }
    //Save or update Product
    @PostMapping("/products")
    public String addProduct(@ModelAttribute Product newProduct) {
        productService.saveProduct(newProduct);
        return "redirect:/products";
    }
    // Save or update category
    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("category") Category category) {
        categoryService.saveCategory(category);
        return "redirect:/category";
    }
    //Show form to add product
    @GetMapping("/addProduct")
    public String showAddProductForm(Model model) {
        model.addAttribute("newProduct", new Product());
        model.addAttribute("category", categoryService.getAllCategory());
        return "addProduct";
    }
    // Show form to add a new Category
    @GetMapping("/showNewCategoryForm")
    public String showNewCategoryForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "addCategory";
    }

    //Delete Product
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }
    // Delete Category
    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") long id) {
        this.categoryService.deleteCategoryById(id);
        return "redirect:/category";
    }

    //Update
    @GetMapping("/updateProduct/{id}")
    public String showEditProductForm(@PathVariable("id") int id, Model model) {
        Product existingProduct = productService.getProductById((long) id);
        model.addAttribute("category", categoryService.getAllCategory());
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

        int pageSize = 5;

        Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Product> listProducts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listProducts", listProducts);

        return "products";
    }

    @GetMapping("/page2/{pageNo2}")

    public String findPaginated2(@PathVariable(value = "pageNo2") int pageNo2, @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir, Model model) {

        int pageSize = 5;

        Page<Category> page = categoryService.findPaginated2(pageNo2, pageSize, sortField, sortDir);
        List<Category> listCategory = page.getContent();

        model.addAttribute("currentPage", pageNo2);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCategory", listCategory);

        return "category";
    }

}