package ca.nivtech.demowebsocket.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Component
public class WebsocketConnectEventListener implements ApplicationListener<SessionConnectEvent> {

    private static final Logger logger = LoggerFactory.getLogger(WebsocketConnectEventListener.class);

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        logger.info(event.toString());
    }
}
