package io.github.crimix.replaceplaceholders.configuration;

import io.github.crimix.replaceplaceholders.utils.Utils;
import org.apache.tools.ant.filters.ReplaceTokens;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.compile.JavaCompile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Configuration for the processSources task
 */
public class ConfigureProcessSources {

    /**
     * Configure the ProcessSources task.
     * @param project the project.
     * @param configuration the configuration
     */
    public static void configure(Project project, Sync task, ReplaceSourcePlaceholdersExtension configuration) {
        if (!configuration.getEnabled()) {
            return;
        }

        Set<String> files = configuration.getFiles();
        Map<String, ?> properties = Utils.getPlaceholderProperties(project, configuration.getProperties());

        Stream.of(task).forEach(processSources -> {
            ConfigureCommon.configureInputs(processSources, project, properties);
            configureInputOutput(processSources, project);
            Map<String, Map<String, ?>> tokens = getTokens(properties);

            if (!files.isEmpty()) {
                processSources.filesMatching(files, fileCopyDetails -> fileCopyDetails.filter(tokens, ReplaceTokens.class));
            } else {
                processSources.filter(tokens, ReplaceTokens.class);
            }
        });
    }

    private static void configureInputOutput(Sync processSources, Project project) {
        SourceDirectorySet resources = project.getExtensions().getByType(SourceSetContainer.class).getByName(SourceSet.MAIN_SOURCE_SET_NAME).getJava();
        processSources.from(resources);
        processSources.into(project.getBuildDir().toPath().resolve("main/src"));

        project.getTasks().withType(JavaCompile.class).forEach(compile -> {
            compile.setSource(processSources.getOutputs());
        });
    }

    private static Map<String, Map<String, ?>> getTokens(Map<String, ?> properties) {
        Map<String, Map<String, ?>> tokens = new HashMap<>();
        tokens.put("hash", properties);
        return tokens;
    }
}
