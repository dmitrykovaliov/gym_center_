package com.dk.gym.entity.join;

import com.dk.gym.entity.Order;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.Training;

public class JoinTraining {
    private Training training;
    private Order order;
    private Trainer trainer;

    public JoinTraining() {
        training = new Training();
        order = new Order();
        trainer = new Trainer();
    }

    public JoinTraining(Training training, Order order, Trainer trainer) {
        this.training = training;
        this.order = order;
        this.trainer = trainer;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JoinTraining that = (JoinTraining) o;

        if (training != null ? !training.equals(that.training) : that.training != null) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        return trainer != null ? trainer.equals(that.trainer) : that.trainer == null;
    }

    @Override
    public int hashCode() {
        int result = training != null ? training.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (trainer != null ? trainer.hashCode() : 0);
        return result;
    }

    @Override
    public String
    toString() {
        return "JoinTraining{" +
                "training=" + training +
                ", order=" + order +
                ", trainer=" + trainer +
                '}';
    }
}
