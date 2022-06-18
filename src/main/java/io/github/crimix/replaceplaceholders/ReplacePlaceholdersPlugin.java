package io.github.crimix.replaceplaceholders;

import io.github.crimix.replaceplaceholders.configuration.ConfigureProcessResources;
import io.github.crimix.replaceplaceholders.configuration.ConfigureProcessSources;
import io.github.crimix.replaceplaceholders.configuration.ReplaceResourcePlaceholdersExtension;
import io.github.crimix.replaceplaceholders.configuration.ReplaceSourcePlaceholdersExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Sync;

/**
 * The main class of the plugin, responsible for configuring it
 */
public class ReplacePlaceholdersPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        ReplaceResourcePlaceholdersExtension resourceConfiguration = project.getExtensions().create("replaceResourcePlaceholders", ReplaceResourcePlaceholdersExtension.class);
        ReplaceSourcePlaceholdersExtension sourceConfiguration = project.getExtensions().create("replaceSourcePlaceholders", ReplaceSourcePlaceholdersExtension.class);
        Sync processSources = project.getTasks().create("processSources", Sync.class);
        project.getPlugins().withType(JavaPlugin.class, plugin -> project.afterEvaluate(p -> ConfigureProcessResources.configure(p, resourceConfiguration)));
        project.getPlugins().withType(JavaPlugin.class, plugin -> project.afterEvaluate(p -> ConfigureProcessSources.configure(p, processSources, sourceConfiguration)));
    }
}
