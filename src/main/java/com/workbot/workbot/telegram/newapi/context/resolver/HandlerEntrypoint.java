package com.workbot.workbot.telegram.newapi.context.resolver;

import com.workbot.workbot.telegram.newapi.context.resolver.type.Action;
import com.workbot.workbot.telegram.newapi.context.resolver.type.State;
import com.workbot.workbot.telegram.newapi.context.resolver.type.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandlerEntrypoint {
    @Autowired
    private List<HandlerExecutor> executors;

    public void handle(Handler handler, State state, Action action) {
        var executor =  executors.stream().filter(exec ->
                    exec.getState().equals(state)
                && exec.getHandlerType().equals(handler)
                && exec.getAction().equals(action)
        ).findFirst().orElseThrow();

        executor.execute();
    }

    public void handle(State state, Action action) {
        var executor =  executors.stream().filter(exec ->
                exec.getState().equals(state)
                        && exec.getAction().equals(action)
        ).findFirst().orElseThrow();
    }
}
