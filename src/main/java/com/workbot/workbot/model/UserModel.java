package com.workbot.workbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class UserModel {
    @Id
    long id;

    @OneToMany(mappedBy = "user")
    List<Subscription> subscriptions;
}
