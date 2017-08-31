package one.valuelogic.vertx.config.env;

import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.json.JsonObject;

/**
 * Factory that creates useful configuration retriever options.
 *
 * <p>Configuration retrieving policy is ordered:</p>
 * <ol>
 *     <li>to take configuration from <var>default.properties</var> file</li>
 *     <li>overwrite values by using OS environment variables</li>
 *     <li>overwrite values by using Java system properties</li>
 * </ol>
 */
public final class EnvConfigRetrieverOptions {

    private EnvConfigRetrieverOptions() {
    }

    public static ConfigRetrieverOptions create() {
        ConfigStoreOptions fileStore = new ConfigStoreOptions()
                .setType("file")
                .setFormat("properties")
                .setConfig(new JsonObject().put("path", "default.properties"));

        ConfigStoreOptions environmentStore = new ConfigStoreOptions().setType("env").setFormat("env");
        ConfigStoreOptions systemStore = new ConfigStoreOptions().setType("sys");

        return new ConfigRetrieverOptions()
                .addStore(fileStore)
                .addStore(environmentStore)
                .addStore(systemStore);
    }
}
