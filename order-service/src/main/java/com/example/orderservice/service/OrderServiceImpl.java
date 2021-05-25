package com.example.orderservice.service;


import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        Order order = modelMapper.map(orderDto, Order.class);
        Order savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder, OrderDto.class);

    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public Iterable<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
