package io.github.crimix.replaceplaceholders.utils;

public enum SourceSets {
    MAIN("compileJava", "src/main/java", "build/replacePlaceholderSrc/main"),
    TEST("compileTestJava", "src/test/java", "build/replacePlaceholderSrc/test");

    private final String taskName;
    private final String inputDir;
    private final String outputDir;

    SourceSets(String taskName, String inputDir, String outputDir) {
        this.taskName = taskName;
        this.inputDir = inputDir;
        this.outputDir = outputDir;
    }

    /**
     * Gets the task name for the task we need to set the input for.
     *
     * @return the task name that should have our processed input
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Gets the name of the input dir to process for placeholder replacement.
     *
     * @return the name of the input dir to process for placeholder replacement
     */
    public String getInputDir() {
        return inputDir;
    }

    /**
     * Gets the name of the output dir to process for placeholder replacement.
     *
     * @return the name of the output dir to process for placeholder replacement
     */
    public String getOutputDir() {
        return outputDir;
    }
}
