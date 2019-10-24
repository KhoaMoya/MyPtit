package com.khoa.myptit.main;

/*
 * Created at 10/18/19 11:16 AM by Khoa
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getCurrentTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
