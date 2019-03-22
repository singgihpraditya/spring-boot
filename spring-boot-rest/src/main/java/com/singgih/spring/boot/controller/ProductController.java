package com.singgih.spring.boot.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.singgih.spring.boot.entity.Product;
import com.singgih.spring.boot.exception.DataNotfoundException;

@RestController
public class ProductController {
	private Logger logger = LoggerFactory.getLogger("DemoApplication");

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ResponseEntity<Object> getProduct() {
		Map<String, Product> productRepo = new HashMap<String, Product>();

		Product product = new Product();
		product.setId("1");
		product.setName("name1");
		product.setEmailAddress("foo@bar.com");
		productRepo.put(product.getId(), product);

		return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") String id) {
		logger.debug("Delete with id : " + id);
		return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> get(@PathVariable("id") String id) {
		logger.debug("Get with id : " + id);
		if(id.equalsIgnoreCase("1")) throw new DataNotfoundException(); 
		Product product = new Product();
		product.setId("1");
		product.setName("name1");
		product.setEmailAddress("foo@bar.com");
		
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody Product product) {
		try {
			logger.debug("Input : "+new ObjectMapper().writeValueAsString(product));
		} catch (JsonProcessingException jsonProcessingException) {
			logger.error("Error : "+jsonProcessingException.getLocalizedMessage());
		}
		return new ResponseEntity<>("Product is created successsfully", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/products", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@RequestBody Product product) {
		try {
			logger.debug("Input : "+new ObjectMapper().writeValueAsString(product));
		} catch (JsonProcessingException jsonProcessingException) {
			logger.error("Error : "+jsonProcessingException.getLocalizedMessage());
		}
		return new ResponseEntity<>("Product is updated successsfully", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		File convertFile = new File(file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		return "File is upload successfully";
	}
}
