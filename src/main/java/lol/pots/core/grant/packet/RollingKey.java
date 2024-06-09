package lol.pots.core.grant.packet;

import java.util.Random;

public class RollingKey {
    private String currentKey;
    private int keyChangeInterval;
    private Random random;
    private static final long REFERENCE_TIME = 0; // Unix epoch time as reference

    public RollingKey(int keyChangeInterval, long seed) {
        this.keyChangeInterval = keyChangeInterval;
        this.random = new Random(seed);
        generateNewKey(REFERENCE_TIME);
    }

    private void generateNewKey(long time) {
        random.setSeed(time / keyChangeInterval);
        currentKey = Integer.toString(random.nextInt());
    }

    public String getCurrentKey() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - REFERENCE_TIME;
        long currentInterval = elapsedTime / keyChangeInterval;

        long keyGenerationTime = currentInterval * keyChangeInterval;
        generateNewKey(keyGenerationTime);

        return currentKey;
    }
}
