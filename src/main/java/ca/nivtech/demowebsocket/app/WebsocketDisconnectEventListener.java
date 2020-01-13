package ca.nivtech.demowebsocket.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebsocketDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger logger = LoggerFactory.getLogger(WebsocketDisconnectEventListener.class);

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        logger.info(event.toString());
    }
}
