package com.workbot.workbot.data.model.dto;

import com.workbot.workbot.data.model.UserModel;

public class UserDto {
    final private long id;

    public UserDto(UserModel userModel) {
        this.id = userModel.getId();
    }

    public long getId() {
        return id;
    }
}
