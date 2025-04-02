package com.workbot.workbot.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserModel {
    @Id
    long id;

    @OneToMany(mappedBy = "user")
    List<Subscription> subscriptions;

    public UserModel(long id) {
        this.id = id;
        this.subscriptions = new ArrayList<>();
    }

    protected UserModel() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
