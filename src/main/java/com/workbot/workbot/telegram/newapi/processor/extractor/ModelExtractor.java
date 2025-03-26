package com.workbot.workbot.telegram.newapi.processor.extractor;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.telegram.newapi.context.model.ModelContext;
import com.workbot.workbot.telegram.newapi.redis.ModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ModelExtractor implements Extractor<ModelContext> {
    @Autowired
    private Extractor<UserDto> userExtractor;

    @Autowired
    private MessageExtractor messageExtractor;

    @Autowired
    private ModelRepo modelRepo;

    @Override
    public boolean has(Update update) {
        var userId = userExtractor.extract(update).getId();

        var messageId = messageExtractor.extract(update);

        return modelRepo.hasForChat(userId) || modelRepo.contains(userId, messageId);
    }

    @Override
    public ModelContext extract(Update update) {
        var userId = userExtractor.extract(update).getId();

        var messageId = messageExtractor.extract(update);

        if (modelRepo.hasForChat(userId)) {
            return modelRepo.getForChat(userId);
        } else {
            return modelRepo.get(userId, messageId);
        }
    }
}
