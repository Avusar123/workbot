package com.workbot.workbot.telegram.cache.repo;

import com.workbot.workbot.telegram.cache.pagination.PaginationModel;
import org.springframework.stereotype.Service;

@Service
public class PaginationRepo extends CacheRepo<PaginationModel> {
    @Override
    public String postfix() {
        return "pagination";
    }
}
