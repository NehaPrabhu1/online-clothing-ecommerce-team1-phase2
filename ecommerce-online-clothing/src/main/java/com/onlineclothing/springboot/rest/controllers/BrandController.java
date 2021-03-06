package com.onlineclothing.springboot.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineclothing.springboot.entities.Brands;
import com.onlineclothing.springboot.entities.Products;
import com.onlineclothing.springboot.services.BrandService;

@RestController
@RequestMapping("/api/v1")
public class BrandController {

	@Autowired
	private BrandService brandService;

	@GetMapping("/brands")
	public ResponseEntity<List<Brands>> getAllBrands() {
		List<Brands> allBrands = brandService.findAllBrands();
		return new ResponseEntity<List<Brands>>(allBrands, HttpStatus.OK);
	}

	@GetMapping("/brands/{id}")
	public ResponseEntity<List<Products>> getProductsByBrand(@PathVariable("id") Integer brandid) {
		List<Products> products = brandService.findProductsByBrand(brandid);
		if (!products.isEmpty()) {
			return new ResponseEntity<List<Products>>(products, HttpStatus.OK);
		}
		return new ResponseEntity<List<Products>>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/brands/count")
	public long getCount() {
		return brandService.getBrandsCount();
	}

}
