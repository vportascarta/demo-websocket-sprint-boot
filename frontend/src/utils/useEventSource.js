import {useEffect} from "react";

export default (url, subscriptions, onClose) => {
  useEffect(() => {
    const eventSource = new EventSource(url);

    subscriptions.forEach(subscription => {
      eventSource.addEventListener(subscription.type, subscription.callback);
    });

    eventSource.addEventListener("COMPLETE", () => {
      eventSource.close();
      onClose()
    });

    return () => {
      eventSource.close();
    }
  }, []);
}
