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
