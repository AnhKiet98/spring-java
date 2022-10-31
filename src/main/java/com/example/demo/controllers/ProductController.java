package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/get-all")
	public Iterable<Product> products() {
		return productRepository.findAll();
	}
	
	@GetMapping(value = "findbycategoryid/{categoryID}")
	public Iterable<Product> getproductsbyid(@PathVariable String categoryID) {
		Iterable<Product> foundProduct = productRepository.findByCategoryID(categoryID);
		return (foundProduct);
	}
	
	
	@GetMapping(value = "findbyproductid/{productID}")
	public Iterable<Product> getproductsbyids(@PathVariable String productID) {
		Iterable<Product> foundProduct = productRepository.findByProductID(productID);
		return (foundProduct);
	}
	
	@RequestMapping(value = "/updateproduct/{productID}", method = RequestMethod.PUT)
	public String updateProduct(@RequestBody Product newProduct, @PathVariable String productID) {
		Product updatedProduct = productRepository.findById(productID)
				.map(product -> {
					product.setProductName(newProduct.getProductName());
					product.setCategoryID(newProduct.getCategoryID());
					product.setPrice(newProduct.getPrice());
					return productRepository.save(product);
				}).orElseGet(() -> {
					newProduct.setProductID(productID);
					return productRepository.save(newProduct);
				});
		return "success";
	}
}
