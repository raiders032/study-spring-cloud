package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/order-service")
@RestController
public class OrderController {

    private final Environment environment;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s", environment.getProperty("local.server.port"));
    }

    @PostMapping("{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@RequestBody RequestOrder requestOrder, @PathVariable String userId) {
        OrderDto orderDto = modelMapper.map(requestOrder, OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto savedOrderDto = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(savedOrderDto, ResponseOrder.class));
    }

    @GetMapping("{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable String userId) {
        Iterable<Order> ordersByUserId = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> result = new ArrayList<>();

        ordersByUserId.forEach(order-> result.add(modelMapper.map(order, ResponseOrder.class)));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
