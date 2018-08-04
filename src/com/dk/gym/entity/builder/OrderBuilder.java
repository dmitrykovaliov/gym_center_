package com.dk.gym.entity.builder;

import com.dk.gym.entity.Order;
import com.dk.gym.validator.NotEmptyValidator;

import java.math.BigDecimal;
import java.time.LocalDate;

class OrderBuilder {

    private Order order;

    public OrderBuilder() {
        order = new Order();
    }
    public OrderBuilder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void buildDate(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            order.setDate(LocalDate.parse(parameter));
        }
    }
    public void buildPrice(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            order.setPrice(new BigDecimal(parameter));
        }
    }
    public void buildDiscount(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            order.setDiscount(Integer.parseInt(parameter));
        }
    }
    public void buildClosureDate(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            order.setClosureDate(LocalDate.parse(parameter));
        }
    }
    public void buildFeedback(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            order.setFeedback(parameter);
        }
    }
    public void buildIdClient(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            order.setIdClient(Integer.parseInt(parameter));
        }
    }
    public void buildIdActivity(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            order.setIdActivity(Integer.parseInt(parameter));
        }
    }
}

