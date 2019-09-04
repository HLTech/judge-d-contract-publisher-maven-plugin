package com.hltech.judged.contracts.publisher.maven.plugin;

import com.google.common.collect.ImmutableSet;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Mojo(name = "publish")
public class PublishMojo extends AbstractMojo {

    private static final Set<String> SUPPORTED_EXTRA_PROPERTIES = new ImmutableSet.Builder<String>()
            .add("swaggerLocation")
            .add("pactsLocation")
            .add("vauntLocation")
            .build();

    @Component
    private MavenProject project;

    @Parameter(property = "publish.judgeDLocation", required = true)
    private URL judgeDLocation;

    @Parameter(property = "publish.capabilities")
    private String[] capabilities;

    @Parameter(property = "publish.expectations")
    private String[] expectations;

    @Override
    public void execute() throws MojoExecutionException {
        getLog().info("serviceName=" + project.getArtifactId());
        getLog().info("serviceVersion=" + project.getVersion());
        getLog().info("judgeDLocation=" + judgeDLocation);
        getLog().info("capabilities=" + Arrays.toString(capabilities));
        getLog().info("expectations=" + Arrays.toString(expectations));

        PublisherAdapter publisher = new PublisherAdapter();
        publisher.publish(project, judgeDLocation, capabilities, expectations, extraPropertiesMap());
    }

    private Map<String, String> extraPropertiesMap() {
        return SUPPORTED_EXTRA_PROPERTIES.stream()
                .filter(prop -> System.getProperty(prop) != null)
                .collect(toMap(identity(), System::getProperty));
    }
}
