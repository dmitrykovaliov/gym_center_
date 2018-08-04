package com.dk.gym.entity.join;

import com.dk.gym.entity.Activity;
import com.dk.gym.entity.Client;
import com.dk.gym.entity.Order;

public class JoinOrder {
    private Order order;
    private Client client;
    private Activity activity;

    public JoinOrder() {
        order = new Order();
        client = new Client();
        activity = new Activity();
    }

    public JoinOrder(Order order, Client client, Activity activity) {
        this.order = order;
        this.client = client;
        this.activity = activity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JoinOrder that = (JoinOrder) o;

        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (client != null ? !client.equals(that.client) : that.client != null) return false;
        return activity != null ? activity.equals(that.activity) : that.activity == null;
    }

    @Override
    public int hashCode() {
        int result = order != null ? order.hashCode() : 0;
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (activity != null ? activity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JoinOrder{" +
                "order=" + order +
                ", client=" + client +
                ", activity=" + activity +
                '}';
    }
}
