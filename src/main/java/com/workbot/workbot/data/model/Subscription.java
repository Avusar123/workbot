package com.workbot.workbot.data.model;

import jakarta.persistence.*;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "filter_id", nullable = false)
    Filter filter;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserModel user;

    public Subscription(String title, Filter filter) {
        this.title = title;
        this.filter = filter;
    }

    protected Subscription() {

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
