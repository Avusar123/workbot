package com.workbot.workbot.data.repo;

import com.workbot.workbot.data.model.Subscription;
import com.workbot.workbot.data.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Long> {
    @Query("SELECT s FROM UserModel u JOIN u.subscriptions s")
    Page<Subscription> findSubsByUser(long userId, Pageable pageable);
}
