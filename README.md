[![CircleCI](https://circleci.com/gh/valuelogic/vertx-env-config/tree/master.svg?style=shield&circle-token=b8213c61a7640e64c706501fccf59f2aa9bf4a86)](https://circleci.com/gh/valuelogic/vertx-env-config/tree/master)

# Vertx env config

Sometimes there is a need to configure Vertx by OS environmental variables, instead of relying on packaged *.property
files.

# Usage

Add dependency in Gradle:

```groovy
dependencies {
  compile "one.valuelogic.vertx-env-config:0.1"
}
```

or in Maven:

```xml
<dependencies>
   <dependency>
      <groupId>one.valuelogic</groupId>
      <artifactId>vertx-env-config</artifactId>
      <version>0.1</version>
   </dependency>
</dependencies>   
```

To use default configuration:

```java
    ConfigRetriever configRetriever = ConfigRetriever
        .create(vertx, EnvConfigRetrieverOptions.create());
    Future<JsonObject> config = configRetriever.getConfigAsFuture();
```

# Limitations

Currently we support only lowercased property keys.
