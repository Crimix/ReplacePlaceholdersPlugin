package io.github.crimix.replaceplaceholders.utils;

import org.gradle.api.Project;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.crimix.replaceplaceholders.utils.Constants.PLACEHOLDER_DOMAIN_PREFIX;

public class Utils {

    /**
     * Gets the placeholders that should be used during replacement.
     * @param project the project.
     * @param extraProperties the configured extra property keys
     * @return the map of the property keys and values.
     */
    public static Map<String, ?> getPlaceholderProperties(Project project, Set<String> extraProperties) {
        // We must only use the properties that have the prefix or has been marked as allowed to use
        return project.getProperties().entrySet().stream()
                .filter(kvp -> kvp.getKey().startsWith(PLACEHOLDER_DOMAIN_PREFIX) || extraProperties.contains(kvp.getKey()))
                .map(Placeholder::new)
                .collect(Collectors.toMap(Placeholder::getKeyWithoutDomain, Placeholder::getValue));
    }
}
