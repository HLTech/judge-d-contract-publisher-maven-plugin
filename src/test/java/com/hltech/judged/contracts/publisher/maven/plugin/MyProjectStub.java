package com.hltech.judged.contracts.publisher.maven.plugin;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.codehaus.plexus.util.ReaderFactory;

import java.io.File;

public class MyProjectStub extends MavenProjectStub {

    public MyProjectStub() {
        MavenXpp3Reader reader = new MavenXpp3Reader();

        try {
            Model model = reader.read(ReaderFactory.newXmlReader(new File("src/test/resources/pom.xml")));
            setModel(model);
            setArtifactId(model.getArtifactId());
            setVersion(model.getVersion());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
