package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.external.client.PaymentService;
import com.example.orderservice.external.client.ProductService;
import com.example.orderservice.model.*;
import com.example.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    //Order Entity -> Save the data with Status Order Created
    //Product Service - Block Products (Reduce the Quantity)
    //Payment Service -> Payments -> Success-> COMPLETE, Else
    //CANCELLED

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    PaymentService paymentService;


    @Value("${microservice.product}")
    private String productServiceUrl;

    @Value("${microservice.payment}")
    private String paymentServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest orderRequest) {

        //Reduce Product Quantity
        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());



        //Save the order
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();


        order = orderRepository.save(order);

        String orderStatus = null;

        try {

            //Make Payments
            paymentService.doPayment(PaymentRequest.builder()
                    .orderId(order.getId())
                    .paymentMode(orderRequest.getPaymentMode())
                    .amount(orderRequest.getTotalAmount())
                    .build());
            log.info("Payment done Successfully. Changing the Oder status to PLACED");
            orderStatus = "PLACED";

        }
        catch (Exception payException)
        {
            log.error("Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {

        Order order=orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order ID #"+orderId+ " Not Found", HttpStatus.NOT_FOUND));

//        productServiceUrl="http://localhost:9001/product/";
//        paymentServiceUrl="http://localhost:7001/payment/";
        productServiceUrl="http://PRODUCT-SERVICE/product/";
        paymentServiceUrl="http://PAYMENTS-SERVICE/payment/";
        log.info("Calling Product URL : {}", productServiceUrl + order.getProductId());
        ProductResponse productResponse = restTemplate.getForObject(productServiceUrl  + order.getProductId(),ProductResponse.class);
        log.info("Found product details with productID # {}",productResponse.getProductId());


        log.info("Calling payment URL : {}", paymentServiceUrl+"order/"+order.getId());
        PaymentResponse paymentResponse=restTemplate.getForObject(paymentServiceUrl+"order/"+order.getId(),PaymentResponse.class);
        log.info("Found payment details with OrderID # {}",paymentResponse.getOrderId());


        //ProductResponse productResponse= productService.getProductById(order.getProductId()).getBody();
        //PaymentResponse paymentResponse= paymentService.getPaymentDetailsByOrderId(Long.toString(order.getId())).getBody();


//        ProductResponse productResponse
//                = restTemplate.getForObject(
//                productServiceUrl  + order.getProductId(),
//                ProductResponse.class
//        );
//
//        log.info("Getting payment information form the payment Service");
//        PaymentResponse paymentResponse
//                = restTemplate.getForObject(
//                paymentServiceUrl + "order/" + order.getId(),
//                PaymentResponse.class
//        );


        OrderResponse.ProductDetails productDetails=OrderResponse.ProductDetails.builder()
                .productId(order.getProductId())
                .productName(productResponse.getProductName())
                .price(productResponse.getPrice())
                .build();

        OrderResponse.PaymentDetails paymentDetails=OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getStatus())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        return OrderResponse.builder()
                .orderId(orderId)
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

    }
}
