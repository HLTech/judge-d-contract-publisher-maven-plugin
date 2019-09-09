package com.hltech.judged.contracts.publisher.maven.plugin;

import com.hltech.judged.contracts.publisher.Publisher;
import com.hltech.judged.contracts.publisher.PublisherProperties;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PublisherPropertiesBuilder {

    private final String serviceName;
    private final String serviceVersion;
    private List<String> capabilities;
    private List<String> expectations;
    private Map<String, String> extraProperties = new HashMap<>();

    static PublisherPropertiesBuilder forService(String serviceName, String serviceVersion) {
        return new PublisherPropertiesBuilder(serviceName, serviceVersion);
    }

    PublisherPropertiesBuilder withCapabilities(String... capabilities) {
        this.capabilities = Arrays.asList(capabilities);
        return this;
    }

    PublisherPropertiesBuilder withExpectations(String... expectations) {
        this.expectations = Arrays.asList(expectations);
        return this;
    }

    PublisherPropertiesBuilder withProperty(String name, String value) {
        if (value != null) {
            this.extraProperties.put(name, value);
        }

        return this;
    }

    private PublisherPropertiesBuilder(String serviceName, String serviceVersion) {
        this.serviceName = serviceName;
        this.serviceVersion = serviceVersion;
    }

    void publishTo(URL judgeDLocation) {
        Publisher publisher = new Publisher();
        publisher.publish(new PublisherProperties(
                serviceName,
                serviceVersion,
                judgeDLocation.toString(),
                capabilities,
                expectations,
                extraProperties
        ));
    }
}
