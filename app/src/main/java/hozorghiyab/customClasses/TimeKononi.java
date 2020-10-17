package hozorghiyab.customClasses;

import java.text.ParseException;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class TimeKononi {

    public String getGregorianTime(){

        PersianDate persianDate = new PersianDate();
        String gregorianTime;
        int gregorianYear = persianDate.getGrgYear();
        int gregorianMonth = persianDate.getGrgMonth();
        int gregorianDay = persianDate.getGrgDay();
        int hour = persianDate.getHour();
        int minute = persianDate.getMinute();
        int second = persianDate.getSecond();
        gregorianTime = gregorianYear + "-" + gregorianMonth + "-" + gregorianDay + " " + hour + ":" + minute + ":" + second;
        return gregorianTime;
    }

    public String getPersianTime(){

        PersianDate persianDate = new PersianDate();
        //-- model dovvom --> PersianDateFormat format = new PersianDateFormat("y-m-d H:i:s");
        PersianDateFormat format = new PersianDateFormat("l y/m/d");
        String persianTime = format.format(persianDate);
        return persianTime;

    }

    public String getPersianTimeWithoutDayName(){

        PersianDate persianDate = new PersianDate();
        //-- model dovvom --> PersianDateFormat format = new PersianDateFormat("y-m-d H:i:s");
        PersianDateFormat format = new PersianDateFormat("y/m/d");
        String persianTime = format.format(persianDate);
        return persianTime;

    }


    public String changeGregorianToPersian(String dateString){

        PersianDate persianDate = new PersianDate();
        int year = Integer.parseInt(dateString.substring(0,4));
        int month = Integer.parseInt(dateString.substring(5,7));
        int day = Integer.parseInt(dateString.substring(8,10));
        /*int hour = Integer.parseInt(dateString.substring(11,13));
        int minute = Integer.parseInt(dateString.substring(14,16));
        int second = Integer.parseInt(dateString.substring(17,19));*/

        if (month > 00){

            int[] persianTime = new int[2];

            persianTime = persianDate.toJalali(year,month,day);
            //String englishNumberToPersian = new EnglishNumberToPersian().convert(persianTime);
            return String.valueOf(new EnglishNumberToPersian().convert(String.valueOf(persianTime[0]))) + "/" + String.valueOf(new EnglishNumberToPersian().convert(String.valueOf(persianTime[1]))) + "/" + String.valueOf(new EnglishNumberToPersian().convert(String.valueOf(persianTime[2])));
        }else {
            return "تاریخ ثبت نشده";

        }
    }

    public String changeGregorianToPersianAndReturnYMD(String dateString){

        PersianDate persianDate = new PersianDate();
        PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");

        int year = Integer.parseInt(dateString.substring(0,4));
        int month = Integer.parseInt(dateString.substring(5,7));
        int day = Integer.parseInt(dateString.substring(8,10));

        if (month > 00){
            try {
                persianDate = pdformater1.parseGrg(dateString,"Y/m/d");

            } catch (ParseException e) {
                e.printStackTrace();
            }

            String englishNumberToPersian = new EnglishNumberToPersian().convert(pdformater1.format(persianDate));
            return englishNumberToPersian;
        }else {
            return "تاریخ ثبت نشده";
        }
    }

    public String changeGregorianToPersianAndReturnHiS(String dateString){

        int hour = Integer.parseInt(dateString.substring(11,13));
        int minute = Integer.parseInt(dateString.substring(14,16));
        int second = Integer.parseInt(dateString.substring(17,19));

        if (hour > 00){

            String clock = hour + ":" + minute + ":" + second;
            String englishNumberToPersian = new EnglishNumberToPersian().convert(clock);
            return englishNumberToPersian;
        }else {
            return "تاریخ ثبت نشده";
        }
    }

}