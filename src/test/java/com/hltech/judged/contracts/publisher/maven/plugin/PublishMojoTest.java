package com.hltech.judged.contracts.publisher.maven.plugin;


import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PublisherPropertiesBuilder.class)
public class PublishMojoTest extends BDDMockito {

    @Rule
    public MojoRule rule = new MojoRule();

    @Rule
    public TestResources resources = new TestResources();

    @Test
    public void shouldPublishContractsWhenExecutingMojo() throws Exception {
        File testPom = new File("src/test/resources/pom.xml");
        assertNotNull(testPom);
        assertTrue(testPom.exists());

        PublishMojo mojo = (PublishMojo) rule.lookupMojo("publish", testPom);
        assertNotNull(mojo);

        PublisherPropertiesBuilder builder = PublisherPropertiesBuilder
                .forService("project-to-test", "1.0-SNAPSHOT");

        PublisherPropertiesBuilder spy = spy(builder);
        doNothing().when(spy).publishTo(eq(new URL("http://localhost:8888")));

        PowerMockito.mockStatic(PublisherPropertiesBuilder.class);
        PowerMockito
                .when(PublisherPropertiesBuilder.forService(eq("project-to-test"), eq("1.0-SNAPSHOT")))
                .thenReturn(spy);

        mojo.execute();

        verify(spy).withCapabilities(eq("jms"));
        verify(spy).withExpectations(eq("rest"));
        verify(spy).withProperty(eq("vauntLocation"), eq("src/test/resources/vaunt"));
        verify(spy).withProperty(eq("pactsLocation"), eq("src/test/resources/pacts"));
        verify(spy).withProperty(eq("swaggerLocation"), isNull());
    }
}