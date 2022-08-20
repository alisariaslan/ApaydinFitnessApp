package com.pakachu.apaydinfitness.helpers;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Hashing {

    private final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private String byteArray2Hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (final byte b : bytes) {
            sb.append(hex[(b & 0xF0) >> 4]);
            sb.append(hex[b & 0x0F]);
        }
        return sb.toString();
    }

    private String getStringFromSHA256(String stringToEncrypt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(stringToEncrypt.getBytes());
        return byteArray2Hex(messageDigest.digest());
    }


    public int kalanSure = 0;

    public String TimeBend(boolean manuel) {
        String bendedTime = "";
        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        float year = calendar.get(Calendar.YEAR);
        float month = calendar.get(Calendar.MONTH);
        float dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        float hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        float minute = calendar.get(Calendar.MINUTE);
        float second = calendar.get(Calendar.SECOND);
        kalanSure = 60 - (int) second;
        float timeBend = year + month + dayOfMonth + hourOfDay + minute;
        Log.e("zaman","year: "+year+"\nmonth: "+month+"\ndayOfMonth: "+dayOfMonth+"\nhourOfDay: "+hourOfDay+
                "\nminute: "+minute+"\nsecond: "+second);
        Log.e("zaman","timebend: "+timeBend);
        bendedTime = String.valueOf(timeBend);
        try {
            bendedTime = getStringFromSHA256(bendedTime);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (manuel)
            if (bendedTime.length() > 5)
                bendedTime = bendedTime.substring(0, 5);
        return bendedTime.toUpperCase();
    }
}
