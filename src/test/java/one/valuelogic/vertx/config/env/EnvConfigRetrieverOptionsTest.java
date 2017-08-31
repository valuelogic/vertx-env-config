package one.valuelogic.vertx.config.env;

import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.json.JsonObject;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnvConfigRetrieverOptionsTest {

    private ConfigRetrieverOptions options = EnvConfigRetrieverOptions.create();

    @Test
    public void should_contain_three_stores() {
        assertThat(options.getStores()).hasSize(3);
    }

    @Test
    public void should_first_store_be_property_file() {
        ConfigStoreOptions firstStore = options.getStores().get(0);

        assertThat(firstStore.getType()).isEqualTo("file");
        assertThat(firstStore.getFormat()).isEqualTo("properties");
        assertThat(firstStore.getConfig()).isEqualTo(
                new JsonObject().put("path", "default.properties"));
    }

    @Test
    public void should_second_store_be_environment() {
        ConfigStoreOptions secondStore = options.getStores().get(1);

        assertThat(secondStore.getType()).isEqualTo("env");
        assertThat(secondStore.getFormat()).isEqualTo("env");
        assertThat(secondStore.getConfig()).isNull();
    }

    @Test
    public void should_third_store_be_system_properties() {
        ConfigStoreOptions thirdStore = options.getStores().get(2);

        assertThat(thirdStore.getType()).isEqualTo("sys");
        assertThat(thirdStore.getFormat()).isNull();
        assertThat(thirdStore.getConfig()).isNull();
    }
}