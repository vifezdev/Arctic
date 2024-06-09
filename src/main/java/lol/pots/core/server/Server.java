package lol.pots.core.server;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Server {
    private String name;
    private String id;

    private String address;
    private String port;

    private int onlinePlayers;
    private int maxPlayers;

    private double[] tps;

    private long allocatedMemory;
    private long usedMemory;
}
