package com.workbot.workbot.telegram.newapi.processor.extractor;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.telegram.newapi.context.pagination.EmptyPagination;
import com.workbot.workbot.telegram.newapi.context.pagination.PaginationCallbackContext;
import com.workbot.workbot.telegram.newapi.context.pagination.PaginationContext;
import com.workbot.workbot.telegram.newapi.context.pagination.Paginations;
import com.workbot.workbot.telegram.newapi.context.resolver.type.Handler;
import com.workbot.workbot.telegram.newapi.redis.PaginationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class PaginationExtractor implements Extractor<PaginationContext> {
    @Autowired
    private PaginationRepo paginationRepo;

    @Autowired
    private Extractor<UserDto> userExtractor;

    @Autowired
    private MessageExtractor messageExtractor;

    private static final int PAGE_SIZE = 5;

    @Override
    public PaginationContext extract(Update update) {
        var user = userExtractor.extract(update);

        var messageId = messageExtractor.extract(update);

        if (!paginationRepo.contains(user.getId(), messageId) || messageId == -1) {
            return new EmptyPagination();
        } else {
            return paginationRepo.get(user.getId(), messageId);
        }
    }

    @Override
    public boolean has(Update update) {
        return true;
    }
}
