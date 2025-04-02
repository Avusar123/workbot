package com.workbot.workbot.telegram.setup.extractor;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.telegram.setup.context.data.CacheData;
import com.workbot.workbot.telegram.setup.context.data.DelegatedCacheData;
import com.workbot.workbot.telegram.setup.redis.ChatDelegatedMessagesRepo;
import com.workbot.workbot.telegram.setup.redis.DataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CacheExtractor implements Extractor<CacheData> {
    @Autowired
    private DataRepo repo;

    @Autowired
    private ChatDelegatedMessagesRepo delayedMessagesRepo;

    @Autowired
    private Extractor<UserDto> userExtractor;

    @Autowired
    private MessageExtractor messageExtractor;

    @Override
    public boolean has(Update update) {
        var user = userExtractor.extract(update);

        var messageId = messageExtractor.extract(update);

        return (update.hasMessage() && delayedMessagesRepo.contains(user.getId())) || repo.contains(user.getId(), messageId);
    }

    @Override
    public CacheData extract(Update update) {
        var user = userExtractor.extract(update);

        int messageId;

        boolean delegated = false;

        if (update.hasMessage()) {
            messageId = delayedMessagesRepo.get(user.getId());

            delegated = true;
        } else {
            messageId = messageExtractor.extract(update);
        }

        if (delegated) {
            return new DelegatedCacheData(repo.get(user.getId(), messageId), messageId);
        } else {
            return repo.get(user.getId(), messageId);
        }
    }
}
