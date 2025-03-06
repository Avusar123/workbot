package com.workbot.workbot.data.repo;

import com.workbot.workbot.data.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubRepo extends JpaRepository<Subscription, Integer> {
}
