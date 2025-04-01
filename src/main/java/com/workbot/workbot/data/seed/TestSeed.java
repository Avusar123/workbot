package com.workbot.workbot.data.seed;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Filter;
import com.workbot.workbot.data.model.Subscription;
import com.workbot.workbot.data.model.UserModel;
import com.workbot.workbot.data.repo.SubRepo;
import com.workbot.workbot.data.repo.UserRepo;
import com.workbot.workbot.logic.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class TestSeed implements CommandLineRunner {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubRepo subRepo;

    @Override
    public void run(String... args) throws Exception {

        var user = new UserModel(1157583330);

        user = userRepo.save(user);

        for (int i = 0; i < 30; i++) {
            var filter = new Filter(
                    Set.of(),
                    Area.IT,
                    Set.of(),
                    LocalDateTime.now()
            );

            var sub = new Subscription(
                    String.valueOf(i),
                    filter
            );

            sub.setUser(user);

            subRepo.save(sub);
        }
    }
}
