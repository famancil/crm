package crm.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Created by gonzalo on 08-09-15.
 */

@JsonAutoDetect
public class KeyValueContainer<T> {

    private String key;

    private T value;

    public KeyValueContainer(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
