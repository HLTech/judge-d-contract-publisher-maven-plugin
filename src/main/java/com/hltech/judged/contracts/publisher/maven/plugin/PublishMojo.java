package com.hltech.judged.contracts.publisher.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.net.URL;
import java.util.Arrays;

@Mojo(name = "publish")
public class PublishMojo extends AbstractMojo {

    @Component
    private MavenProject project;

    @Parameter(property = "publish.version")
    private String version;

    @Parameter(property = "publish.judgeDLocation", required = true)
    private URL judgeDLocation;

    @Parameter(property = "publish.capabilities")
    private String[] capabilities;

    @Parameter(property = "publish.expectations")
    private String[] expectations;

    @Parameter(property = "publish.swaggerLocation")
    private String swaggerLocation;

    @Parameter(property = "publish.pactsLocation")
    private String pactsLocation;

    @Parameter(property = "publish.vauntLocation")
    private String vauntLocation;

    @Override
    public void execute() throws MojoExecutionException {
        String publishVersion = version != null ? version : project.getVersion();

        getLog().info("serviceName=" + project.getArtifactId());
        getLog().info("serviceVersion=" + publishVersion);
        getLog().info("judgeDLocation=" + judgeDLocation);
        getLog().info("capabilities=" + Arrays.toString(capabilities));
        getLog().info("expectations=" + Arrays.toString(expectations));

        PublisherPropertiesBuilder.forService(project.getArtifactId(), publishVersion)
                .withCapabilities(capabilities)
                .withExpectations(expectations)
                .withProperty("swaggerLocation", swaggerLocation)
                .withProperty("pactsLocation", pactsLocation)
                .withProperty("vauntLocation", vauntLocation)
                .publish(judgeDLocation);
    }

}
