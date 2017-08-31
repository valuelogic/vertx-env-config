package one.valuelogic.vertx.config.env;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(VertxUnitRunner.class)
public class EnvConfigProcessorTest {

    @Rule
    public final RunTestOnContext vertxRule = new RunTestOnContext();

    private EnvConfigProcessor envConfigProcessor = new EnvConfigProcessor();
    private Vertx vertx;

    @Before
    public void setUp(TestContext tc) {
        vertx = vertxRule.vertx();
        vertx.exceptionHandler(tc.exceptionHandler());
    }

    @Test
    public void should_have_proper_name() throws Exception {
        assertThat(envConfigProcessor.name()).isEqualTo("env");
    }

    @Test
    public void should_process_empty_buffer(TestContext context) throws Exception {
        Async async = context.async();
        Future<JsonObject> future = Future.future();
        future.setHandler(event -> {
            System.out.println("FUTURE " + event);
            assertThat(event.succeeded()).isEqualTo(true);
            assertThat(event.result()).isEqualTo(new JsonObject());
            async.complete();
        });
        envConfigProcessor.process(vertx, new JsonObject(), Buffer.buffer(), future);
    }

    @Test
    public void should_process_variables(TestContext context) throws Exception {
        Async async = context.async();
        Future<JsonObject> future = Future.future();
        future.setHandler(result -> {
            assertThat(result.succeeded()).isEqualTo(true);
            assertThat(result.result()).isEqualTo(new JsonObject().put("some.key", "SOME_VALUE"));
            async.complete();
        });
        envConfigProcessor.process(vertx, new JsonObject(), Buffer.buffer("{\"SOME_KEY\": \"SOME_VALUE\"}"), future);
    }

    @Test
    public void should_fail_on_incorrect_config(TestContext context) throws Exception {
        Async async = context.async();
        Future<JsonObject> future = Future.future();
        future.setHandler(result -> {
            assertThat(result.failed()).isEqualTo(true);
            async.complete();
        });
        envConfigProcessor.process(vertx, new JsonObject(), Buffer.buffer("incorrect"), future);
    }

}