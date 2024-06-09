package lol.pots.core.redis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import lol.pots.core.Arctic;
import lol.pots.core.redis.packet.Packet;

import java.util.UUID;
import java.util.function.Consumer;

public class RedisHandler {

    private String host;
    private int port;

    private JedisPool jedisPool;

    private String channel;
    private String password;

    private Gson gson;

    /**
     * create a new RedisHandler
     *
     * @param host the ip address of the redis server.
     * @param port the port of the redis server.
     */
    public RedisHandler(String host, int port, String channel, String password) {
        this.host = host;
        this.port = port;
        this.password = password;

        this.gson = new Gson();
        this.channel = channel;

    }

    /**
     * Attempts to make a connection to the
     * redis database with the specified credentials and
     * starts a thread for receiving messages
     */
    public void connect() {
        this.jedisPool = new JedisPool(new JedisPoolConfig(), host, port, 20_000, password);

        new Thread(() -> {

            this.runCommand(redis -> {
                redis.subscribe(new JedisPubSub() {

                    @Override
                    public void onMessage(String channel, String message) {
                        try {
                            // Create the packet
                            String[] strings = message.split("||");
                            Object jsonObject = gson.fromJson(strings[0], Class.forName(strings[1]));
                            Packet packet = (Packet) jsonObject;

                            packet.onReceive();

                        } catch (Exception ignored) {
                        }
                    }
                }, channel);
            });
        }).start();

    }

    /**
     * sends a packet through redis
     *
     * @param packet the packet to get sent
     */

    public void sendPacket(Packet packet) {
        packet.onSend();

        new Thread(() ->
                runCommand(redis -> {
                    redis.publish(channel, packet.getClass().getName() + "||" + gson.toJson(packet));
                })).start();
    }

    /**
     * sends a packet through redis
     *
     * @param consumer the callback to be executed
     */
    private void runCommand(Consumer<Jedis> consumer) {
        Jedis jedis = jedisPool.getResource();
        if (jedis != null) {
            consumer.accept(jedis);
            jedisPool.returnResource(jedis);
        }
    }

    public JsonObject getPlayerData(UUID uuid, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String data = jedis.hget(key, uuid.toString());

            if (data == null) {
                return null;
            }

            try {
                JsonObject object = Arctic.getInstance().getParser().parse(data).getAsJsonObject();

                if (object.has("data")) {
                    return object.get("data").getAsJsonObject();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void deletePlayerData(String key, UUID uuid) {
        Jedis jedis = jedisPool.getResource();
        jedis.hdel(key, uuid.toString());
    }
}
