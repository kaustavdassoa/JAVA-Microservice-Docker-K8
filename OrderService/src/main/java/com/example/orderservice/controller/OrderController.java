package com.example.orderservice.controller;

import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.model.OrderRequest;
import com.example.orderservice.model.OrderResponse;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {


    @Autowired
    OrderService orderService;



    @PostMapping
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
         log.info("Received order {}",orderRequest.getProductId());
         Long orderId= orderService.placeOrder(orderRequest);
         log.info("Saved order id {}",orderId);
         return new ResponseEntity<>(orderId, HttpStatus.OK);
    }//

    @GetMapping("/{orderID}")
    public ResponseEntity<OrderResponse>getOderDetails(@PathVariable Long orderID)
    {
       return new ResponseEntity<>(orderService.getOrderDetails(orderID),HttpStatus.OK);
    }

}//OrderController
