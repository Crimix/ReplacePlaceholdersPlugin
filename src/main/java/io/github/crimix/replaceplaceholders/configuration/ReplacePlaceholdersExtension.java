package io.github.crimix.replaceplaceholders.configuration;

import io.github.crimix.replaceplaceholders.utils.Constants;
import org.gradle.api.provider.SetProperty;

/**
 * The configuration for the plugin
 */
public interface ReplacePlaceholdersExtension {

    /**
     * The list of file names that will get its placeholders expanded and replaced with the values.
     * @return a list of file names.
     */
    SetProperty<String> getFilesToExpand();

    /**
     * The set of properties outside the {@link Constants#PLACEHOLDER_DOMAIN_PREFIX} domain that the plugin is allowed to use in expanding properties.
     * @return a set of property names to use.
     */
    SetProperty<String> getExtraProperties();
}
