package io.github.crimix.replaceplaceholders;

import io.github.crimix.replaceplaceholders.configuration.ConfigureProcessResources;
import io.github.crimix.replaceplaceholders.configuration.ConfigureProcessSources;
import io.github.crimix.replaceplaceholders.configuration.ReplaceResourcePlaceholdersExtension;
import io.github.crimix.replaceplaceholders.configuration.ReplaceSourcePlaceholdersExtension;
import io.github.crimix.replaceplaceholders.utils.SourceSets;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Sync;

/**
 * The main class of the plugin, responsible for configuring it
 */
public class ReplacePlaceholdersPlugin implements Plugin<Project> {

    @Override
    public void apply(Project rootProject) {
        rootProject.getPlugins().withType(JavaPlugin.class, plugin -> configure(rootProject));
    }

    private void configure(Project rootProject) {
        ReplaceResourcePlaceholdersExtension resourceConfiguration = rootProject.getExtensions().create("replaceResourcePlaceholders", ReplaceResourcePlaceholdersExtension.class);
        ReplaceSourcePlaceholdersExtension sourceConfiguration = rootProject.getExtensions().create("replaceSourcePlaceholders", ReplaceSourcePlaceholdersExtension.class);

        rootProject.allprojects(project -> {
            Sync processSources = project.getTasks().create("processSources", Sync.class);
            Sync processTestSources = project.getTasks().create("processTestSources", Sync.class);

            project.afterEvaluate(p -> {
                ConfigureProcessResources.configure(p, resourceConfiguration);
                ConfigureProcessSources.configure(p, processSources, SourceSets.MAIN, sourceConfiguration);
                ConfigureProcessSources.configure(p, processTestSources, SourceSets.TEST, sourceConfiguration);
            });
        });
    }
}
