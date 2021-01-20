/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2021-01-13 22:40:25
 * @modify date 2021-01-13 22:40:25
 * @desc [description]
 */
package com.cg.productservice.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import com.cg.productservice.dto.ProductInfoDto;
import com.cg.productservice.dto.ProductInfoRequest;
import com.cg.productservice.dto.StockDto;
import com.cg.productservice.entities.ProductCategory;
import com.cg.productservice.entities.ProductInfo;
import com.cg.productservice.exception.CategoryNotFoundException;
import com.cg.productservice.exception.ProductNotFoundException;
import com.cg.productservice.repositories.ProductInfoRepository;
import com.cg.productservice.services.ProductCategoryService;
import com.cg.productservice.services.ProductInfoService;
import com.cg.productservice.util.ProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	private ProductInfoRepository productInfoRepository;
	@Autowired
	private ProductCategoryService categoryService;

	@Override
	public List<ProductInfo> fetchAll() {
		return this.productInfoRepository.findAll();
	}

	@Override
	public List<ProductInfo> fetchByCategory(String category) {
		List<ProductInfo> products =  productInfoRepository.findAll().stream().filter(c -> c.getProductCategory().getCategoryName().equals(category)).collect(Collectors.toList());
		if (products.size() == 0) throw new CategoryNotFoundException("category", "Invalid category");
		return products;
	}

	@Override
	public ProductInfo fetchById(Long id) {
		return this.productInfoRepository.findById(id).orElseThrow(() ->  new ProductNotFoundException("product", "Not Found"));
	}
	
	@Override
	public boolean removeProduct(Long productId) {
		this.productInfoRepository.deleteById(productId);
		return true;
	}

	@Override
	public ProductInfoDto update(ProductInfoRequest request) {
		if (request.getProductId() == null) throw new ProductNotFoundException("product", "Product ID cannot be null");
		productInfoRepository.findById(request.getProductId()).orElseThrow(() ->  new ProductNotFoundException("product", "Not Found"));
		ProductInfo info = ProductMapper.DtoToEntity(request);
		ProductCategory category = categoryService.findById(request.getCategoryId());
		info.setProductCategory(category);
		return ProductMapper.EntityToDto(productInfoRepository.save(info));
	}
	

	@Override
	public ProductInfoDto add(ProductInfoRequest request) {
		ProductInfo info = ProductMapper.DtoToEntity(request);
		ProductCategory category = categoryService.findById(request.getCategoryId());
		info.setProductCategory(category);
		return ProductMapper.EntityToDto(productInfoRepository.save(info));
	}

	@Override
	public ProductInfo updateStock(StockDto stockDto) {
		ProductInfo productInfo = productInfoRepository.findById(stockDto.getProductId()).orElseThrow(() ->  new ProductNotFoundException("product", "Not Found"));
		productInfo.setProductStock(stockDto.getQuantity());
		return productInfoRepository.save(productInfo);
	}

	@Override
	public ProductInfo increaseStock(StockDto stockDto) {
		// Auto-generated method stub
		return null;
	}

	@Override
	public ProductInfo reduceStock(StockDto stockDto) {
		//  Auto-generated method stub
		return null;
	}

}
