import {useEffect} from 'react';
import {Client} from "@stomp/stompjs";

export default (url, subscriptions) => {
  useEffect(() => {
    const client = new Client({
      brokerURL: url,
      reconnectDelay: 20000,
      heartbeatIncoming: 10000,
      heartbeatOutgoing: 10000
    });

    client.onConnect = () => {
      subscriptions.forEach(subscription => {
        client.subscribe(subscription.url, subscription.callback);
      });
    };

    client.onStompError = function (frame) {
      console.log('Broker reported error: ' + frame.headers['message']);
      console.log('Additional details: ' + frame.body);
    };

    client.activate();

    return () => {
      client.deactivate();
    }
  }, []);
}
