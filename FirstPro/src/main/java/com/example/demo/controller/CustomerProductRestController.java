package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.CustomerProduct;
import com.example.demo.service.CustomerProductService;
import com.example.demo.util.ReflectionUtil;

@RestController
@CrossOrigin
@RequestMapping(path = "/object/cusotmerProduct/")
public class CustomerProductRestController {
	@Autowired
	private CustomerProductService cusService;

	ReflectionUtil refUtil = ReflectionUtil.getInstance();

	@PostMapping(path = "create")
	public ResponseEntity<?> createCusPro(@Valid @RequestBody CustomerProduct customerProduct, BindingResult result) {
		if (result != null && result.hasErrors()) {
			Map<String, String> error = new HashMap<String, String>();
			for (FieldError fieldError : result.getFieldErrors()) {
				error.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return new ResponseEntity<Map<String, String>>(error, HttpStatus.BAD_REQUEST);
		}
		CustomerProduct cp = cusService.createCusotmerProduct(customerProduct);
		System.out.println(cp);
		return new ResponseEntity<CustomerProduct>(cp, HttpStatus.CREATED);
	}

	@GetMapping(path = "get/{id}")
	public ResponseEntity<?> getCustomerProduct(@PathVariable(value = "id") Long id) {
		CustomerProduct cp = cusService.getCustomerProductById(id);
		if (cp == null) {
			return new ResponseEntity<String>("Nothing Found with id:- " + id, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<CustomerProduct>(cp, HttpStatus.OK);
	}

	@GetMapping(path = "get")
	public ResponseEntity<?> getAllCusotmerProduct() {
		List<CustomerProduct> cpList = cusService.getCustomerProductAll();
		if (cpList.size() == 0) {
			return new ResponseEntity<String>("Nothing Found ", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<CustomerProduct>>(cpList, HttpStatus.OK);
	}

	@DeleteMapping(path = "delete/{id}")
	public ResponseEntity<?> deleteCustomerProductById(@PathVariable(value = "id") Long id) {
		CustomerProduct cp = cusService.getCustomerProductById(id);
		if (cp == null) {
			return new ResponseEntity<String>("No CustomerProduct found for id:-" + id, HttpStatus.BAD_REQUEST);
		}
		cusService.deleteCustoProduct(id);
		return new ResponseEntity<String>("SuccessFully Deleted The CustomerProduct", HttpStatus.OK);

	}

	@PatchMapping(path = "update/{id}")
	public ResponseEntity<?> updateCustomerProduct(@Valid @RequestBody String cp, @PathVariable(value = "id") Long id)
			throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		CustomerProduct cpFromDB = cusService.getCustomerProductById(id);
		boolean deliveryFlag = cp.contains("productDeliveryDate");
		if (cpFromDB == null) {
			return new ResponseEntity<String>("No CustomerProduct with id:- " + id + "Found", HttpStatus.BAD_REQUEST);
		}
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(cp);

		for (Iterator iterator = ((Map<String, String>) obj).keySet().iterator(); iterator.hasNext();) {
			String props = (String) iterator.next();
			if (!props.equals("productDeliveryDate")) {
				refUtil.getSetterMethod("CustomerProduct", props).invoke(cpFromDB, obj.get(props));
			}
		}
		if (deliveryFlag == true) {
			String str = (String) obj.get("productDeliveryDate");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			LocalDate dateTime = LocalDate.parse(str, formatter);

			cpFromDB.setProductDeliveryDate(dateTime);
		}
		cusService.createCusotmerProduct(cpFromDB);
		return new ResponseEntity<CustomerProduct>(cpFromDB, HttpStatus.OK);
	}
}
