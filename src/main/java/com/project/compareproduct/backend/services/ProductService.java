package com.project.compareproduct.backend.services;

import java.util.*;

import com.project.compareproduct.backend.exception.ResourceNotFoundException;
import com.project.compareproduct.backend.model.*;
import com.project.compareproduct.backend.payload.UrlRequest;
import com.project.compareproduct.backend.payload.productUpdateRequest;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    Product crawlProduct(UrlRequest url);

    LaptopDMX crawlLaptopDMX(UrlRequest url);

    List<Product> crawlAllProduct(UrlRequest url);

    LaptopFPT crawlLaptopFPT(UrlRequest url);

    LaptopAmz crawlLaptopAmz(UrlRequest url);

    LaptopAmz crawlLaptopAmzBySearchBar();

    void crawlLaptopList();

}
