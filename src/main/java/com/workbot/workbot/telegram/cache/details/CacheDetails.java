package com.workbot.workbot.telegram.cache.details;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public abstract class CacheDetails {
}
