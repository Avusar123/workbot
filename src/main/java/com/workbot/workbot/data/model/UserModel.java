package com.workbot.workbot.data.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_model")
public class UserModel {
    @Id
    @Column(name = "id")
    private Long id;

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
