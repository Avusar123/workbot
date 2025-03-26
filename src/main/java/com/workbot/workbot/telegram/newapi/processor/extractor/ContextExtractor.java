package com.workbot.workbot.telegram.newapi.processor.extractor;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.telegram.newapi.context.UpdateContext;
import com.workbot.workbot.telegram.newapi.context.model.ModelContext;
import com.workbot.workbot.telegram.newapi.context.pagination.PaginationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ContextExtractor implements Extractor<UpdateContext> {
    @Autowired
    private Extractor<ModelContext> modelExtractor;

    @Autowired
    private Extractor<PaginationContext> paginationExtractor;

    @Autowired
    private Extractor<UserDto> userDtoExtractor;

    @Override
    public UpdateContext extract(Update update) {
        var paginationContext = paginationExtractor.extract(update);

        var user = userDtoExtractor.extract(update);

        var updateContext = new UpdateContext(update, user, paginationContext);

        if (modelExtractor.has(update)) {
            updateContext.setModelContext(modelExtractor.extract(update));
        }

        return updateContext;
    }

    @Override
    public boolean has(Update update) {
        return true;
    }
}
