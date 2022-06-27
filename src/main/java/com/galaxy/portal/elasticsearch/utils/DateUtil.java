package com.galaxy.portal.elasticsearch.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    public static void main(String[] args) {

    }

    private static List<String> analysisTimearea(String startTime, String endTime){

        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int num = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            Date date1=sdf.parse(startTime);
            Date date2 = sdf.parse(endTime);
            c1.setTime(date1);
            c2.setTime(date2);
            num = c2.get(Calendar.DAY_OF_YEAR)-c1.get(Calendar.DAY_OF_YEAR);
            if (num == 0) {
                dateList.add(sdf.format(c1.getTime()));
                return dateList;
            }



            int startDay = c1.get(Calendar.DATE);


            int endDay = c1.get(Calendar.DATE);



        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        for ( int i = 0 ; i <= num;i++) {
            dateList.add(sdf.format(c1.getTime()));
            c1.add(Calendar.DAY_OF_YEAR, 1 );

        }

        return dateList;
    }

    private List<String> getYearssBetweenTimeArea(Calendar startTime,Calendar endTime){

        List<String> dateList = new ArrayList<>();
        int startYear = startTime.get(Calendar.YEAR);
        int endyear = endTime.get(Calendar.YEAR);
        if(startYear < endyear){
            int tempYear = startYear+1;
            while (tempYear < endyear){
                dateList.add(tempYear+"");
            }
        }
        return dateList;
    }

    private List<String> getMonthsBetweenTimeAreaInyear(Calendar startTime,Calendar endTime){

        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int startMonth = startTime.get(Calendar.MONTH) + 1;
        int endMonth = endTime.get(Calendar.MONTH) + 1;
        if(startMonth < endMonth){
            startTime.add(Calendar.MONTH, 1 );
            int tempMonth = startMonth+1;
            while (tempMonth < endMonth){

                dateList.add(sdf.format(startTime.getTime()).substring(6));
                tempMonth = startTime.get(Calendar.MONTH) + 1;
                startTime.add(Calendar.MONTH, 1 );
            }
        }
        return dateList;
    }

    private List<String> getDayssBetweenTimeInMonth(Calendar startTime,Calendar endTime){

        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int startDay = startTime.get(Calendar.DAY_OF_MONTH) + 1;
        int endDay = endTime.get(Calendar.DAY_OF_MONTH) + 1;
        if(startDay < endDay){
            startTime.add(Calendar.DAY_OF_MONTH, 1 );
            int tempDay = startTime.get(Calendar.DAY_OF_MONTH) + 1;;
            while (tempDay <= endDay){
                dateList.add(sdf.format(startTime.getTime()).substring(8));
                startTime.add(Calendar.DAY_OF_MONTH, 1 );
                tempDay = startTime.get(Calendar.DAY_OF_MONTH) + 1;

            }
        }
        return dateList;
    }

}
