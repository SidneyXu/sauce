package com.bookislife.sauce.utils;

import java.math.BigDecimal;

/**
 * Created by SidneyXu on 2016/01/04.
 */
public class Duration {

    private long id;
    private long startTime;

    public Duration() {
        id = -1;
        startTime = System.currentTimeMillis();
    }

    public static long from(Runnable runnable) {
        Duration duration = new Duration();
        runnable.run();
        return duration.getDurationSeconds(System.currentTimeMillis());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDurationSeconds(long endTime) {
        BigDecimal bigTime = new BigDecimal(endTime - startTime);
        return bigTime.divide(new BigDecimal(1000f), BigDecimal.ROUND_CEILING)
                .longValue();
    }

    public static String formatDuration(int durationSeconds) {
        int seconds = durationSeconds % 60;
        int minutes = ((durationSeconds - seconds) / 60) % 60;
        int hours = (durationSeconds - (minutes * 60) - seconds) / 3600;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
