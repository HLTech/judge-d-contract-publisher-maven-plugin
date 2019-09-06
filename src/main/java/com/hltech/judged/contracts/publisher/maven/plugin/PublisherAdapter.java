package com.hltech.judged.contracts.publisher.maven.plugin;

import com.hltech.judged.contracts.publisher.Publisher;
import com.hltech.judged.contracts.publisher.PublisherProperties;
import org.apache.maven.project.MavenProject;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

class PublisherAdapter {

    private final Publisher publisher = new Publisher();

    void publish(MavenProject project, URL judgeDLocation, String[] capabilities, String[] expectations, Map<String, Optional<String>> extraPropertiesMap) {
        publisher.publish(new PublisherProperties(
                project.getArtifactId(),
                project.getVersion(),
                judgeDLocation.toString(),
                asList(capabilities),
                asList(expectations),
                skipNullValues(extraPropertiesMap)
        ));
    }

    private Map<String, String> skipNullValues(Map<String, Optional<String>> map) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().isPresent())
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get()));
    }
}
