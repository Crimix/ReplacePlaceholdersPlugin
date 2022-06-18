package io.github.crimix.replaceplaceholders.configuration;

import java.util.Set;

/**
 * Common extension methods
 */
public abstract class ReplacePlaceholdersExtension {

    /**
     * Gets if the task has been enabled.
     * @return true if the task has been enabled otherwise false.
     */
    abstract boolean getEnabled();

    /**
     * Returns the files to limit the replacement to if any.
     * @return the files to limit the replacement to if any.
     */
    abstract Set<String> getFiles();

    /**
     * Returns the extra properties to include when do the replacement.
     * @return the extra property keys to include when do the replacement
     */
    abstract Set<String> getProperties();
}
