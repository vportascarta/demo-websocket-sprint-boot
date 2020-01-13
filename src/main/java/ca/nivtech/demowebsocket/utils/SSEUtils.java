package ca.nivtech.demowebsocket.utils;

import ca.nivtech.demowebsocket.controller.EmployeeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class SSEUtils {
    public static final String GET_ALL_EVENT_NAME = "GET_ALL";
    public static final String PROGRESSION_EVENT_NAME = "PROGRESSION";
    public static final String COMPLETE_EVENT_NAME = "COMPLETE";
    private static final Logger logger = LoggerFactory.getLogger(SSEUtils.class);

    private static final ExecutorService nonBlockingService = Executors.newCachedThreadPool();

    public static SseEmitter createDataFetchSSE(Consumer<SseEmitter> function) {
        SseEmitter emitter = new SseEmitter();
        nonBlockingService.execute(() -> {
            try {
                function.accept(emitter);
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(System.currentTimeMillis()))
                        .name(COMPLETE_EVENT_NAME)
                        .data(true)
                );
                emitter.complete();
            } catch (IOException ex) {
                logger.error("Exception when sending data with SSE : " + ex.getMessage());
                emitter.completeWithError(ex);
            }
        });

        return emitter;
    }
}
