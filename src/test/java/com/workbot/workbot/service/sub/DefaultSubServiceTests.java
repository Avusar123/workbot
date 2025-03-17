package com.workbot.workbot.service.sub;

import com.workbot.workbot.data.repo.SubRepo;
import com.workbot.workbot.data.repo.VacancyRepo;
import com.workbot.workbot.logic.service.sub.DefaultSubService;
import com.workbot.workbot.logic.service.vacancy.DefaultVacancyService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
public class DefaultSubServiceTests {
    @Mock
    private SubRepo subRepo;

    @InjectMocks
    private DefaultSubService subService;


}
