package com.project.compareproduct.backend.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.lang.InterruptedException;

import com.project.compareproduct.backend.exception.ResourceNotFoundException;
import com.project.compareproduct.backend.model.LaptopDMX;
import com.project.compareproduct.backend.model.LaptopFPT;
import com.project.compareproduct.backend.model.LaptopAmz;
import com.project.compareproduct.backend.model.Product;
import com.project.compareproduct.backend.payload.UrlRequest;
import com.project.compareproduct.backend.payload.productUpdateRequest;
import com.project.compareproduct.backend.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Scheduled(fixedRate = 86400000)
	public void crawlListLaptopFPT() {
		productService.crawlLaptopList();
	}

}
