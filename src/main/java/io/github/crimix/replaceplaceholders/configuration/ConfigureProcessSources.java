package io.github.crimix.replaceplaceholders.configuration;

import io.github.crimix.replaceplaceholders.utils.SourceSets;
import io.github.crimix.replaceplaceholders.utils.Utils;
import org.apache.tools.ant.filters.ReplaceTokens;
import org.gradle.api.Project;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.compile.JavaCompile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Configuration for the processSources task
 */
public class ConfigureProcessSources {

    /**
     * Configure the ProcessSources task.
     * @param project the project.
     * @param configuration the configuration
     */
    public static void configure(Project project, Sync processSources, SourceSets sourceSet, ReplaceSourcePlaceholdersExtension configuration) {
        if (!configuration.getEnabled()) {
            return;
        }

        Set<String> files = configuration.getFiles();
        Map<String, ?> properties = Utils.getPlaceholderProperties(project, configuration.getProperties());

        ConfigureCommon.configureInputs(processSources, project, properties);
        configureInputOutput(processSources, sourceSet);

        Map<String, Map<String, ?>> tokens = getTokens(properties);

        if (!files.isEmpty()) {
            processSources.filesMatching(files, fileCopyDetails -> fileCopyDetails.filter(tokens, ReplaceTokens.class));
        } else {
            processSources.filter(tokens, ReplaceTokens.class);
        }

        // Hook this sync task into the compile task
        project.getTasks().named(sourceSet.getTaskName(), JavaCompile.class).configure(compile -> {
            compile.setSource(processSources.getDestinationDir());
            compile.dependsOn(processSources);
        });
    }

    private static void configureInputOutput(Sync processSources, SourceSets sourceSet) {
        // Sync process to replace tokens in source files before compile
        processSources.from(sourceSet.getInputDir());
        processSources.into(sourceSet.getOutputDir());
    }

    private static Map<String, Map<String, ?>> getTokens(Map<String, ?> properties) {
        Map<String, Map<String, ?>> tokens = new HashMap<>();
        tokens.put("hash", properties);
        return tokens;
    }
}
