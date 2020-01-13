package ca.nivtech.demowebsocket.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LockedMap<KEY, USER_KEY> {
    private final Map<KEY, USER_KEY> map = new HashMap<>();

    public synchronized boolean isLocked(KEY key) {
        return map.containsKey(key);
    }

    public synchronized boolean isLockedForUser(KEY key, USER_KEY userKey) {
        return Objects.equals(userKey, map.get(key));
    }

    public synchronized void lock(KEY key, USER_KEY userKey) {
        if(isLocked(key)) {
            throw new LockException();
        } else {
            map.put(key, userKey);
        }
    }

    public synchronized void unlock(KEY key, USER_KEY userKey) {
        if(isLockedForUser(key, userKey)) {
            map.remove(key, userKey);
        } else {
            throw new LockException();
        }
    }

    public static class LockException extends RuntimeException {
        public LockException() {
            super("Un utilisateur est déjà en train de modifier l'élément");
        }
    }
}
