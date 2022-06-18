package io.github.crimix.replaceplaceholders.configuration;

import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractCopyTask;

import java.util.Map;

/**
 * Common configuration for the tasks
 */
public class ConfigureCommon {

    /**
     * Configure the common parts of both tasks
     * @param task the {@link AbstractCopyTask}
     * @param project the project
     * @param properties the properties to use
     */
    public static void configureInputs(AbstractCopyTask task, Project project, Map<String, ?> properties) {
        // This will ensure that this task is redone when the versions change.
        task.getInputs().property("version", project.getProperties().get("version"));

        // This will ensure that this task is redone when a property we can map, is changed.
        task.getInputs().properties(properties);
    }
}
