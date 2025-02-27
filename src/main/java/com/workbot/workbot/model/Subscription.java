package com.workbot.workbot.model;

import jakarta.persistence.*;

@Entity
public class Subscription {
    @Id
    @GeneratedValue
    int id;

    String title;

    @OneToOne(cascade = CascadeType.ALL)
    Filter filter;

    @ManyToOne
    UserModel user;

    public Subscription(String title, Filter filter) {
        this.title = title;
        this.filter = filter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
