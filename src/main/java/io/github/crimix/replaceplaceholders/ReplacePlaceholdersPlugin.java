package io.github.crimix.replaceplaceholders;

import io.github.crimix.replaceplaceholders.configuration.ReplacePlaceholdersExtension;
import io.github.crimix.replaceplaceholders.utils.Placeholder;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.language.jvm.tasks.ProcessResources;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.crimix.replaceplaceholders.utils.Constants.PLACEHOLDER_DOMAIN_PREFIX;

/**
 * The main class of the plugin, responsible for configuring it
 */
public class ReplacePlaceholdersPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        ReplacePlaceholdersExtension configuration = project.getExtensions().create("replacePlaceholders", ReplacePlaceholdersExtension.class);
        project.getPlugins().withType(JavaPlugin.class, plugin -> project.afterEvaluate(p -> configure(p, configuration)));
    }

    private void configure(Project project, ReplacePlaceholdersExtension configuration) {
        Set<String> files = configuration.getFilesToExpand()
                .getOrElse(Collections.emptySet());
        Set<String> extraProperties = configuration.getExtraProperties()
                .getOrElse(Collections.emptySet());

        // Build the map of properties we can use during replacement
        Map<String, ?> properties = getPlaceholderProperties(project, extraProperties);

        project.getTasks().withType(ProcessResources.class).forEach(processResources -> {
            // This will ensure that this task is redone when the versions change.
            processResources.getInputs().property("version", project.getProperties().get("version"));

            // If the there were no configured files, just skip configuring it for now
            if (!files.isEmpty())
                processResources.filesMatching(files, fileCopyDetails -> fileCopyDetails.expand(properties));
        });
    }

    private Map<String, ?> getPlaceholderProperties(Project project, Set<String> extraProperties) {
        // We must only use the properties that have the prefix or has been marked as allowed to use
        return project.getProperties().entrySet().stream()
                .filter(kvp -> kvp.getKey().startsWith(PLACEHOLDER_DOMAIN_PREFIX) || extraProperties.contains(kvp.getKey()))
                .map(Placeholder::new)
                .collect(Collectors.toMap(Placeholder::getKeyWithoutDomain, Placeholder::getValue));
    }
}
