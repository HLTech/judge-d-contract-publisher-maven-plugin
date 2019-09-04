package com.hltech.judged.contracts.publisher.maven.plugin;

import com.hltech.judged.contracts.publisher.Publisher;
import com.hltech.judged.contracts.publisher.PublisherProperties;
import org.apache.maven.project.MavenProject;

import java.net.URL;
import java.util.Map;

import static java.util.Arrays.asList;

class PublisherAdapter {

    private final Publisher publisher = new Publisher();

    void publish(MavenProject project, URL judgeDLocation, String[] capabilities, String[] expectations, Map<String, String> extraPropertiesMap) {
        publisher.publish(new PublisherProperties(
                project.getArtifactId(),
                project.getVersion(),
                judgeDLocation.toString(),
                asList(capabilities),
                asList(expectations),
                extraPropertiesMap
        ));
    }
}
