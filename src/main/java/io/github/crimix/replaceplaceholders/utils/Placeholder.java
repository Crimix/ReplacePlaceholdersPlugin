package io.github.crimix.replaceplaceholders.utils;

import java.util.Map;

import static io.github.crimix.replaceplaceholders.utils.Constants.PLACEHOLDER_DOMAIN_PREFIX;

/**
 * Class to manipulate the property key value pair
 */
public class Placeholder<T> {

    private final String key;
    private final T value;

    public Placeholder(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public Placeholder(Map.Entry<String, T> entry) {
        this(entry.getKey(), entry.getValue());
    }

    /**
     * Gets the key of the property without the {@link Constants#PLACEHOLDER_DOMAIN_PREFIX} domain
     * @return the key without {@link Constants#PLACEHOLDER_DOMAIN_PREFIX} domain
     */
    public String getKeyWithoutDomain() {
        return key.startsWith(PLACEHOLDER_DOMAIN_PREFIX) ? key.replaceFirst(PLACEHOLDER_DOMAIN_PREFIX, "") : key;
    }

    /**
     * Gets the value of the property
     * @return the value
     */
    public T getValue() {
        return value;
    }
}
