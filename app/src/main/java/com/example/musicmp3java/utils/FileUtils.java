package com.example.musicmp3java.utils;

import java.text.DecimalFormat;

public class FileUtils {

    public static String getDuration(int totalDuration) {
        String totalDurationText;
        int hrs = totalDuration / (1000 * 60 * 60);
        int min = totalDuration % (1000 * 60 * 60) / (1000 * 60);
        int secs = (((totalDuration % (1000 * 60 * 60)) % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

        if (hrs < 1) {
            totalDurationText = String.format("%02d:%02d", min, secs);
        } else {
            totalDurationText = String.format("%1d:%02d:%02d", hrs, min, secs);
        }
        return totalDurationText;
    }

    public static String getSize(long byres) {
        String hrSize;

        double k = byres / 1024.0;
        double m = ((byres / 1024.0) / 1024.0);
        double g = (((byres / 1024.0) / 1024.0) / 1024.0);
        double t = ((((byres / 1024.0) / 1024.0) / 1024.0) / 1024.0);

        //the format
        DecimalFormat dec = new DecimalFormat("0.00");
        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else if (k > 1) {
            hrSize = dec.format(m).concat(" KB");
        } else {
            hrSize = dec.format(m).concat(" Bytes");
        }
        return hrSize;
    }

    public static  String getReadableTime(int duration) {
        String time;
        int hrs = duration / (1000 * 60 * 60);
        int min = duration % (1000 * 60 * 60) / (1000 * 60);
        int secs = (((duration % (1000 * 60 * 60)) % (1000 * 60 * 60)) % (1000 * 60)) / 1000;
        if (hrs < 1) {
            time = min + ":" + secs;
        } else {
            time = hrs + ":" + min + ":" + secs;
        }
        return time;
    }

}
