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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineclothing.springboot.entities.Orders;
import com.onlineclothing.springboot.services.OrderService;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/status")
	public String getStatus() {
		return "Status: OK";
	}
	
	//http://localhost:8080/api/v1/orders
	@GetMapping("/orders")
	public ResponseEntity<List<Orders>> getAllOrders(){
		List<Orders> allOrders = orderService.findAllOrders();
		if(!allOrders.isEmpty())
			return new ResponseEntity<List<Orders>>(allOrders,HttpStatus.OK);
		return new ResponseEntity<List<Orders>>(HttpStatus.BAD_REQUEST);
	}
	
	//http://localhost:8080/api/v1/orders
	@PostMapping("/orders")
	public ResponseEntity<Orders> saveOrder(@RequestBody Orders order){
		
		Orders savedOrder = orderService.insertNewOrder(order);
		return new ResponseEntity<Orders>(savedOrder,HttpStatus.CREATED);
	}
	
	//http://localhost:8080/api/v1/orders/1
	@GetMapping("/orders/{id}")
	public ResponseEntity<Orders> getOrderByOrderid(@PathVariable("id") Integer orderid){
		Orders order = orderService.findOrderById(orderid);
		if(order != null)
			return new ResponseEntity<Orders>(order,HttpStatus.OK);
		return new ResponseEntity<Orders>(order,HttpStatus.BAD_REQUEST);
	}
	
	//http://localhost:8080/api/v1/orders/user/2
	@GetMapping("/orders/user/{id}")
	public ResponseEntity<List<Orders>> getAllOrdersOfUser(@PathVariable("id") Integer userid){
		List<Orders> orders = orderService.findOrdersOfUser(userid);
		if(!orders.isEmpty())
			return new ResponseEntity<List<Orders>>(orders,HttpStatus.OK);
		return new ResponseEntity<List<Orders>>(HttpStatus.BAD_REQUEST);
	}
	
	//http://localhost:8080/api/v1/orders/user/2/page?pgnum=0&size=2
	@GetMapping("/orders/user/{id}/page")
	public ResponseEntity<List<Orders>> getPagedOrdersOfUser(@PathVariable("id") Integer userid,
			@RequestParam("pgnum") int pageNo, @RequestParam("size") int size){
		
		List<Orders> orders = orderService.findOrdersOfUserAndApplySortingAndPaging(userid, pageNo, size);
		if(!orders.isEmpty())
			return new ResponseEntity<List<Orders>>(orders,HttpStatus.OK);
		return new ResponseEntity<List<Orders>>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/orders/count")
    public long getCount() {
    	return orderService.getOrdersCount();
    }
}
