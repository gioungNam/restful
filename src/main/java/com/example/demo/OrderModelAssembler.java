package com.example.demo;

import com.example.demo.controller.EmployeeController;
import com.example.demo.controller.OrderController;
import com.example.demo.entity.Order;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {


    @Override
    public EntityModel<Order> toModel(Order order) {

        return EntityModel.of(order, linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).all()).withRel("orders"));
    }
}
