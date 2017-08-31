package one.valuelogic.vertx.config.env;

import io.vertx.config.spi.ConfigProcessor;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Vertx config processor that reads environment variables and transforms their names like <val>AAA_BBB</val>
 * into <val>aaa.bbb</val>.
 *
 * <p>
 * For usability sake look at {@link EnvConfigRetrieverOptions}.
 * </p>
 */
public class EnvConfigProcessor implements ConfigProcessor {

    @Override
    public String name() {
        return "env";
    }

    @Override
    public void process(Vertx vertx, JsonObject configuration, Buffer input, Handler<AsyncResult<JsonObject>> handler) {
        if (input.length() == 0) {
            handler.handle(Future.succeededFuture(new JsonObject()));
            return;
        }
        vertx.executeBlocking(
                future -> {
                    try {
                        Map<String, Object> mappedEnv = input.toJsonObject()
                                .stream()
                                .collect(Collectors.toMap(key -> convertEnv(key.getKey()), Map.Entry::getValue));
                        JsonObject result = new JsonObject(mappedEnv);
                        future.complete(result);
                    } catch (Exception e) {
                        future.fail(e);
                    }
                },
                handler
        );
    }

    private String convertEnv(String key) {
        if (isBlank(key)) {
            return key;
        }
        return lowerCase(key).replace('_', '.');
    }

    private String lowerCase(String key) {
        if (key == null) {
            return null;
        } else {
            return key.toLowerCase();
        }
    }

    private boolean isBlank(String key) {
        return (key == null || key.trim().equals(""));
    }
}
