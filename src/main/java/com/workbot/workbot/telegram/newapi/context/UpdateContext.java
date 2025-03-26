package com.workbot.workbot.telegram.newapi.context;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.telegram.newapi.context.model.ModelContext;
import com.workbot.workbot.telegram.newapi.context.pagination.PaginationContext;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateContext {
    private Update update;

    private final UserDto userDto;

    private PaginationContext paginationContext;

    private ModelContext modelContext;

    public UpdateContext(Update update, UserDto userDto, PaginationContext paginationContext) {
        this.update = update;
        this.userDto = userDto;
        this.paginationContext = paginationContext;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public UserDto getUser() {
        return userDto;
    }

    public PaginationContext getPaginationContext() {
        return paginationContext;
    }

    public void setPaginationContext(PaginationContext paginationContext) {
        this.paginationContext = paginationContext;
    }

    public boolean hasModel() {
        return modelContext != null;
    }

    public ModelContext getModelContext() {
        return modelContext;
    }

    public void setModelContext(ModelContext modelContext) {
        this.modelContext = modelContext;
    }
}
