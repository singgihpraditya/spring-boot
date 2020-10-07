package com.singgih.spring.boot.controller;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singgih.spring.boot.entity.Product;
import com.singgih.spring.boot.entity.ProductResponse;

//@RestController
public class ProductTestController {
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="/test/products")
	public String getProductList(){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
		
		return restTemplate.exchange("http://localhost:8080/products", HttpMethod.GET, httpEntity, String.class).getBody();
		
	}
	
	@RequestMapping(value="/test/products/{id}")
	public ResponseEntity<Object> getProductById(@PathVariable("id") String id){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Product> httpEntity = new HttpEntity<Product>(httpHeaders);
		
		String response = restTemplate.exchange("http://localhost:8080/products/"+id, HttpMethod.GET, httpEntity, String.class).getBody();
		ObjectMapper mapper = new ObjectMapper();
		ProductResponse productResponse = new ProductResponse();
		productResponse.setStatus("OK");
		Product product;
		try {
			product = mapper.readValue(response,Product.class);
			productResponse.setPayload(product);
			return new ResponseEntity<>(productResponse, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("Failed", HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		
	}
}
