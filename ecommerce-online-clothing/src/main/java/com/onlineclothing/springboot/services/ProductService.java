package com.onlineclothing.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.onlineclothing.springboot.entities.Discount;
import com.onlineclothing.springboot.entities.Products;
import com.onlineclothing.springboot.repositories.CategoryRepository;
import com.onlineclothing.springboot.repositories.ProductRepository;


@Service
@Component
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	private DiscountService discountService;

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Products> getProductsBasedOnPage(int pageNumber, int pageSize) {
		updateProductDiscount();
		// query parameters are pgnum and size
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Products> page = productRepository.findAll(pageable);
		// May comment the above lines out so it can be more generic,
		// e.g. we can get a Page object that only contains search query results
		int totalPages = page.getTotalPages();
		while (totalPages <= pageNumber) {
			pageNumber = totalPages - 1;
		}
		pageable = PageRequest.of(pageNumber, pageSize);
		page = productRepository.findAll(pageable);
		List<Products> products = page.getContent();

		return products;
	}
	
	public List<Products> getAllProducts(){
		updateProductDiscount();
		List<Products> products =  productRepository.findAll();
		return products;
	}
	
	public List<Products> searchProducts(String query) {
		updateProductDiscount();
		List<Products> products = productRepository.findAllByName(query);
		return products;
	}
	
	public Products getProductById(int productid) {
		updateProductDiscount();
		Optional<Products> product = productRepository.findById(productid);
		if (product.isPresent()) {
			return product.get();
		} else {
			System.out.println("Product not found");
			return null;
		}
	}
	
	public List<Products> getProductsByGender(String gender) {
		updateProductDiscount();
		List<Products> products = productRepository.findAllByGender(gender);
		return products;
	}
	
	public Products save(Products product) {
		Products savedProduct = productRepository.save(product);
		return savedProduct;
	}

	public void updateProductDiscount() {
		int count = (int) categoryRepository.count();
		for (int i = 1; i <= count; i++) {
			Discount liveDiscount = discountService.getLiveDiscountByCategory(i);
			List<Products> products = productRepository.findByCategoryid(i);
			for (Products product : products) {
				if (liveDiscount != null) {
					product.setDiscount(liveDiscount.getDiscountPercent());
					productRepository.save(product);
				} else {
					product.setDiscount(0d);
					productRepository.save(product);
				}
			}
		}
	}
	
	//wrote this method to select a product by color
	public List<Products> getProductsByColor(String color){
		updateProductDiscount();
		List<Products> products = productRepository.findByColor(color);
		return products;
	}

}
