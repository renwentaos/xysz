package com.xws.xysz.util;


/**
 * Created by 鍏�on 2015/3/30.
 */
public class IdWorker {

    private static IdWorker idWorker = new IdWorker(1);

    public static IdWorker getInstance(){
        return idWorker;
    }

    private final long workerId;
    private final long twepoch = 1288834974657L;
    private long sequence = 0L;
    private final long workerIdBits = 4L;
    public final long maxWorkerId = -1L ^ -1L << workerIdBits;
    private final long sequenceBits = 10L;

    private final long workerIdShift = sequenceBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits;
    public final long sequenceMask = -1L ^ -1L << sequenceBits;

    private long lastTimestamp = -1L;

    private IdWorker(final long workerId) {
        super();
        if (workerId > this.maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format(
                    "worker Id can't be greater than %d or less than 0",
                    this.maxWorkerId));
        }
        this.workerId = workerId;
    }

    public synchronized Long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & this.sequenceMask;
            if (this.sequence == 0) {
//                System.out.println("###########" + sequenceMask);
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            try {
                throw new Exception(
                        String.format(
                                "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                                this.lastTimestamp - timestamp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.lastTimestamp = timestamp;
        long nextId = ((timestamp - twepoch << timestampLeftShift))
                | (this.workerId << this.workerIdShift) | (this.sequence);
//        System.out.println("timestamp:" + timestamp + ",timestampLeftShift:"
//                + timestampLeftShift + ",nextId:" + nextId + ",workerId:"
//                + workerId + ",sequence:" + sequence);
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }



}
