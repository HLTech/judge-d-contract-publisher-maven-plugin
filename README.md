# judge-d-contract-publisher-maven-plugin [![Build Status](https://travis-ci.org/HLTech/judge-d-contract-publisher-maven-plugin.svg?branch=master)](https://travis-ci.org/HLTech/judge-d-contract-publisher-maven-plugin) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.hltech/judge-d-contract-publisher-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.hltech/judge-d-contract-publisher-maven-plugin)

Maven plugin to help integrate your project with judge-d in order to validate communication between microservices.

## Usage

Add plugin to your pom.xml file:
```xml
<plugin>
    <groupId>com.hltech</groupId>
    <artifactId>judge-d-contract-publisher-maven-plugin</artifactId>
    <version>0.3.2</version>
</plugin>
```

If you have multi module maven app then you can add plugin to root pom.xml and set inherited to false:
```xml
<plugin>
    <groupId>com.hltech</groupId>
    <artifactId>judge-d-contract-publisher-maven-plugin</artifactId>
    <version>0.3.2</version>
    <inherited>false</inherited>
</plugin>
```

To publish contracts, run `publish` mojo:
```shell script
./mvnw judge-d-contract-publisher:publish 
  -Dpublish.judgeDLocation=https://judge-d.tech.hl.uk
  -Dpublish.capabilities=rest
  -Dpublish.swaggerLocation=./build/swagger/
  -Dpublish.expectations=rest
  -Dpublish.pactsLocation=./build/pacts/
```

## Parameters
- **publish.judgeDLocation** - url of judge-d instance to which you contracts will be uploaded (required)
- **publish.expectations** - list of comma separated values which determines expectations of you service against providers
- **publish.capabilities** - list of comma separated values which determines what capabalities your service expose
- **publish.version** - optional parameter to allow version override, by default value from pom.xml is used

*Neither expectations nor capabilities are required for now but nothing will be published in such case*

## Expectations
Depending on what values you provide as expectations you also need to pass some additional parameters to make things work:
- **publish.pactsLocation** - required for **rest** expectation, points to directory where pacts files are stored
- **publish.vauntLocation** - required for **jms** expectation, points to directory where vaunt files are stored

## Capabilities
Similar to expectations, you need to pass additional parameters for capabilities also:
- **publish.swaggerLocation** - required for **rest** capability, points to directory where swagger specification is stored
- **publish.vauntLocation** - required for **jms** capability, points to directory where vaunt files are stored

## Useful links
- [judge-d-contract-publisher-gradle-plugin](https://github.com/HLTech/judge-d-contract-publisher-gradle-plugin) - alternative plugin for gradle users
- [judge-d](https://github.com/HLTech/judge-d) - tool used to test contracts between microservices
- [pact-gen](https://github.com/HLTech/pact-gen) - library for generating pact files out of Feign clients
- [vaunt](https://github.com/HLTech/vaunt) - toolkit to define and validate contracts in JMS
