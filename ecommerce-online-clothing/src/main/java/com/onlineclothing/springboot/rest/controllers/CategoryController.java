package com.onlineclothing.springboot.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineclothing.springboot.entities.Categories;
import com.onlineclothing.springboot.entities.Products;
import com.onlineclothing.springboot.services.CategoryService;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/categories")
	public ResponseEntity<List<Categories>> getAllCategories(){
		List<Categories> allBrands = categoryService.findAllCategories();
		return new ResponseEntity<List<Categories>>(allBrands,HttpStatus.OK);
	}

	@GetMapping("/categories/{id}")
	public ResponseEntity<List<Products>> getAllProductsByCategory(@PathVariable("id") Integer id){
		List<Products> products = categoryService.findProductsByCategory(id);
		if(!products.isEmpty())
			return new ResponseEntity<List<Products>>(products,HttpStatus.OK);
		return new ResponseEntity<List<Products>>(HttpStatus.BAD_REQUEST);
	}
	
	 @GetMapping("/categories/count")
	    public long getCount() {
	    	return categoryService.getCategoriesCount();
	    }
}
