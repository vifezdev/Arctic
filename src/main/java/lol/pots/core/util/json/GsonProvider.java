package lol.pots.core.util.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.function.Function;

public class GsonProvider {

    private static GsonBuilder gsonBuilder = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .serializeNulls()
            .setLenient();

    public static Gson GSON = gsonBuilder.create();

    public static void useGsonBuilderThenRebuild(Function<GsonBuilder, GsonBuilder> function) {
        synchronized (gsonBuilder) {
            gsonBuilder = function.apply(gsonBuilder);
            GSON = gsonBuilder.create();
        }
    }
}
