package com.hltech.judged.contracts.publisher.maven.plugin;


import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.hamcrest.MockitoHamcrest;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.collection.ArrayMatching.hasItemInArray;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PublishMojo.class)
public class PublishMojoTest extends BDDMockito {

    @Rule
    public MojoRule rule = new MojoRule();

    @Rule
    public TestResources resources = new TestResources();

    @Mock
    PublisherAdapter publisherMock;

    @Test
    public void shouldPublishContractsWhenExecutingMojo() throws Exception {
        File testPom = new File("src/test/resources/pom.xml");
        assertNotNull(testPom);
        assertTrue(testPom.exists());

        PublishMojo mojo = (PublishMojo) rule.lookupMojo("publish", testPom);
        assertNotNull(mojo);

        System.setProperty("vauntLocation", "src/test/resources/vaunt");
        System.setProperty("pactsLocation", "src/test/resources/pacts");

        PowerMockito.whenNew(PublisherAdapter.class).withNoArguments().thenReturn(publisherMock);
        mojo.execute();

        ArgumentCaptor<MavenProject> mavenProjectCaptor = ArgumentCaptor.forClass(MavenProject.class);

        verify(publisherMock).publish(
                mavenProjectCaptor.capture(),
                eq(new URL("http://localhost:8888")),
                MockitoHamcrest.argThat(hasItemInArray("jms")),
                MockitoHamcrest.argThat(hasItemInArray("rest")),
                MockitoHamcrest.argThat(allOf(
                        hasEntry("vauntLocation", "src/test/resources/vaunt"),
                        hasEntry("pactsLocation", "src/test/resources/pacts")
                ))
        );

        assertThat(mavenProjectCaptor.getValue().getArtifactId(), equalTo("project-to-test"));
        assertThat(mavenProjectCaptor.getValue().getVersion(), equalTo("1.0-SNAPSHOT"));

    }

}