# Replace Placeholders Plugin
A Gradle plugin to auto-configure the `ProcessResources` task from the Java plugin to replace placeholders in a list of files
with a set of properties, these properties are defined with a `placeholder.` prefix.  
This way there is no need to both define the placeholder in the target file, in a gradle.properties file and in the build.gradle file, 
but instead only declare it and add it to the target file without the `placeholder.` prefix.

## Installation
Recommended way is to apply the plugin to the `build.gradle` in the `plugins` block
```groovy
plugins {
    id 'io.github.crimix.replace-placeholders' version 'VERSION'
}
```
and then configure the plugin using the following block `build.gradle`
```groovy
replacePlaceholders {
    filesToExpand = [
            "some.file"
    ]
    extraProperties = [
            "version"
    ]
}
```

## Configuration
As seen above, there are two configuration options available.

| **Option**      | **Explanation**                                                                                                                                                                                                 |
|-----------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| filesToExpand   | The required list of files to expand (replace) placeholders in.                                                                                                                                                 |
| extraProperties | A list of property names that the plugin can use to expand placeholders, that does not start with `placeholder.`.<br>This way you get control over exactly which properties can be expanded into resource files |

## Usage
It is very simple to use the plugin, just add it to `build.gradle` and configure it as shown above.  
It has a built-in input using the `version` property to make sure the `ProcessResources` task gets run when the version changes,
this also means that you should be using the `version` property.

## Example
This is a basic example that shows how it works.

The following has been defined in `build.gradle`
```groovy
version = '1.0-SNAPSHOT'

replacePlaceholders {
    filesToExpand = [
            "META-INF/info.toml"
    ]
    extraProperties = [
            "version"
    ]
}
```

The `info.toml` file looks as the following with the placeholders
```toml
displayName="My awesome program"
version="${version}"
authors="${authors}"
description="${description}"
```

The `gradle.properties` contains the following related properties
```properties
placeholder.authors=Crimix
placeholder.description=This is my very awesome program
```

Then after building the jar, the `info.toml` will have had its placeholders expanded, so it will look as the following
```toml
displayName="My awesome program"
version="1.0-SNAPSHOT"
authors="Crimix"
description="This is my very awesome program"
```


## Why did I make this
I have multiple smaller projects where I have different placeholders which I need to replace, 
and I started to be annoyed by having to first declare the property, add the placeholder to the file 
and then maintain a mapping task with a map of properties.

This plugin cuts out the maintaining of the mapping task with the map of properties,
without sacrificing control over which properties are used for expanding and in which files.

