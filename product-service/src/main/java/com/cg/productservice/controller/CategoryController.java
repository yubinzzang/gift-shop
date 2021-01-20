package com.cg.productservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.productservice.entities.ProductCategory;
import com.cg.productservice.services.ProductCategoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
	
	@Autowired
	private final ProductCategoryService productCategoryService;
	
	@GetMapping
	public List<ProductCategory> fetchAllCategories(){
		return productCategoryService.fetchAllCategories();
	}
	
	@PostMapping
	public ProductCategory createCategory(@Valid @RequestBody ProductCategory productCategory) {
		return productCategoryService.createCategory(productCategory);
	}

}
