package com.lnet.frame.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Snowflake implements IdGenerator {

    private final long sequenceBits = 12;
    private final long nodeIdBits = 10L;
    private final long maxNodeId = ~(-1L << nodeIdBits);

    private final long nodeIdShift = sequenceBits;
    private final long timestampLeftShift = sequenceBits + nodeIdBits;

    private final long twepoch = 1288834974657L;
    private final long nodeId;
    private final long sequenceMax = 4096;

    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;

    public Snowflake(long nodeId)  {
        if (nodeId > maxNodeId || nodeId < 0) {
            throw new IllegalArgumentException(String.format("nodeId must be between %s and %s", 0, maxNodeId));
        }
        this.nodeId = nodeId;
    }

    public String next()  {
        long timestamp = System.currentTimeMillis();

        synchronized (this) {
            if (timestamp < lastTimestamp) {
                throw new RuntimeException("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds.");
            }
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) % sequenceMax;
                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                sequence = 0;
            }
            lastTimestamp = timestamp;
        }

        Long id = ((timestamp - twepoch) << timestampLeftShift) |
                (nodeId << nodeIdShift) |
                sequence;
        return id.toString();
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    /**
     * Thread safe singleton
     */
    private static class Holder {
        private static long getMacId() {
            try {
                InetAddress ip = InetAddress.getLocalHost();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                long id;
                if (network == null) {
                    id = 1;
                } else {
                    byte[] mac = network.getHardwareAddress();
                    id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                }
                return id;
            } catch (SocketException | UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }

        static final Snowflake INSTANCE = new Snowflake(getMacId());
    }

    public static Snowflake getInstance(){
        return Holder.INSTANCE;
    }
}