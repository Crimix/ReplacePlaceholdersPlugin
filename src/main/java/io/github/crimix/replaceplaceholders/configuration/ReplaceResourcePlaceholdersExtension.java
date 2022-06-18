package io.github.crimix.replaceplaceholders.configuration;

import io.github.crimix.replaceplaceholders.utils.Constants;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The configuration for the plugin
 */
public class ReplaceResourcePlaceholdersExtension extends ReplacePlaceholdersExtension {

    private boolean isEnabled = false;
    private Set<String> files = Collections.emptySet();
    private Set<String> properties = Collections.emptySet();

    /**
     * If replace resources should be configured and run.
     * @param enabled the enabled state.
     */
    public void enabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    boolean getEnabled() {
        return isEnabled;
    }

    /**
     * The list of file names that will get its placeholders expanded and replaced with the values.
     */
    public void filesToExpand(String... files) {
        this.files = Stream.of(files)
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Set<String> getFiles() {
        return files;
    }

    /**
     * The set of properties outside the {@link Constants#PLACEHOLDER_DOMAIN_PREFIX} domain that the plugin is allowed to use in expanding properties.
     */
    public void extraProperties(String... properties) {
        this.properties = Stream.of(properties)
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Set<String> getProperties() {
        return properties;
    }
}
