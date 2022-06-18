package io.github.crimix.replaceplaceholders.configuration;

import io.github.crimix.replaceplaceholders.utils.Utils;
import org.gradle.api.Project;
import org.gradle.language.jvm.tasks.ProcessResources;

import java.util.Map;
import java.util.Set;

/**
 * Configuration for the processResources task
 */
public class ConfigureProcessResources {

    /**
     * Configure the ProcessResources task.
     * @param project the project.
     * @param configuration the configuration
     */
    public static void configure(Project project, ReplaceResourcePlaceholdersExtension configuration) {
        if (!configuration.getEnabled()) {
            return;
        }

        Set<String> files = configuration.getFiles();
        Map<String, ?> properties = Utils.getPlaceholderProperties(project, configuration.getProperties());

        project.getTasks().withType(ProcessResources.class).forEach(processResources -> {
            ConfigureCommon.configureInputs(processResources, project, properties);

            if (!files.isEmpty()) {
                processResources.filesMatching(files, fileCopyDetails -> fileCopyDetails.expand(properties));
            } else {
                processResources.expand(properties);
            }
        });
    }
}
