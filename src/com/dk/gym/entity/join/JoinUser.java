package com.dk.gym.entity.join;

import com.dk.gym.entity.Client;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.User;

public class JoinUser {
    private User user;
    private Client client;
    private Trainer trainer;

    public JoinUser() {
        user = new User();
        client = new Client();
        trainer = new Trainer();
    }

    public JoinUser(User user, Client client, Trainer trainer) {
        this.user = user;
        this.client = client;
        this.trainer = trainer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

        JoinUser joinUser = (JoinUser) o;

        if (user != null ? !user.equals(joinUser.user) : joinUser.user != null) return false;
        if (client != null ? !client.equals(joinUser.client) : joinUser.client != null) return false;
        return trainer != null ? trainer.equals(joinUser.trainer) : joinUser.trainer == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (trainer != null ? trainer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JoinUser{" +
                "user=" + user +
                ", client=" + client +
                ", trainer=" + trainer +
                '}';
    }
}
