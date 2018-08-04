package com.dk.gym.entity.builder;

import com.dk.gym.controller.RequestContent;
import com.dk.gym.entity.Order;

import static com.dk.gym.constant.ParamConstant.*;

public class OrderDirector {

    public Order buildOrder(RequestContent content) {

        OrderBuilder builder = new OrderBuilder();

        buildFields(builder, content);

        return builder.getOrder();
    }

    public Order buildOrder(Order order, RequestContent content) {

        OrderBuilder builder = new OrderBuilder(order);

        buildFields(builder, content);

        return builder.getOrder();
    }

    private void buildFields(OrderBuilder builder, RequestContent content) {

        builder.buildDate(content.findParameter(PARAM_DATE));
        builder.buildPrice(content.findParameter(PARAM_PRICE));
        builder.buildDiscount(content.findParameter(PARAM_DISCOUNT));
        builder.buildClosureDate(content.findParameter(PARAM_CLOSURE));
        builder.buildFeedback(content.findParameter(PARAM_FEEDBACK));
        builder.buildIdClient(content.findParameter(PARAM_CLIENT_ID));
        builder.buildIdActivity(content.findParameter(PARAM_ACTIVITY_ID));
    }
}
