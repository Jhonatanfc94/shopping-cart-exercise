package auxiliary.tools;

import auxiliary.Data;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

public class DatesCalendar {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger( DatesCalendar.class.getName() );
    private static String pattern = "yyyy-MM-dd";
    private static String timeZone = Data.time.timeZone;
    private static Locale locale = Locale.ENGLISH;

    private static Date reviewPattern(String date){
        SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
        format.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date revisedDate = null;
        try{
            revisedDate = format.parse(date);
        }catch (Exception ParseException) {
            Assert.fail(ParseException.getMessage());
        }
        return  revisedDate;
    }

    public static void changeTimeZone(String newTimeZone){
        timeZone = newTimeZone;
    }

    /**
     * Cambia el formato dde una fecha "yyyy-MM-dd" a formato de letras
     */
    public static String dateDescriptive(String changeDate) {
        Date date = parseDate(changeDate);
        DateFormat dfDateFull = DateFormat.getDateInstance(DateFormat.FULL, locale);
        String dateType = dfDateFull.format(date);
        dateType = Character.toUpperCase(dateType.charAt(0)) + dateType.substring(1);
        return dateType;
    }
    private static Date parseDate(String changeDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", locale);
        Date date = null;
        try {
            date = format.parse(changeDate);
        } catch (Exception ParseException) {
            Assert.fail(ParseException.getMessage());
        }
        return date;
    }

    public static int getTimeDifferenceBetweenDates(String stringInitialDate, String stringFinalDate, String timeUnit) {
        Date initialDate = reviewPattern(stringInitialDate);
        Date finalDate = reviewPattern(stringFinalDate);

        if(initialDate != null && finalDate != null){
            long initialTime = initialDate.getTime();
            long finalTime = finalDate.getTime();
            long timeDifference = finalTime - initialTime;
            switch(timeUnit) {
                case "second":{
                    timeDifference = timeDifference/1000;
                    break;
                }
                case "minute":{
                    timeDifference = timeDifference/(1000*60);
                    break;
                }
                case "hour":{
                    timeDifference = timeDifference/(1000*60*60);
                    break;
                }
                case "day":{
                    timeDifference = timeDifference/(1000*60*60*24);
                    break;
                }
                //month no accurate
                case "month":{
                    Calendar calInitial = Calendar.getInstance();
                    calInitial.setTime(initialDate);
                    Calendar calFinal = Calendar.getInstance();
                    calFinal.setTime(finalDate);
                    timeDifference = (calFinal.get(Calendar.YEAR) - calInitial.get(Calendar.YEAR)) * 12
                            + (calFinal.get(Calendar.MONTH) - calInitial.get(Calendar.MONTH));
                    break;
                }
            }
            return (int) timeDifference;
        }else{
            return Integer.parseInt(null);
        }
    }

    public static boolean belongsToTheRange(String dateToCompare, String initialDate, String finalDate) {
        return (getTimeDifferenceBetweenDates(dateToCompare, initialDate, "days") <= 0) && (getTimeDifferenceBetweenDates(finalDate, dateToCompare, "days") <= 0);
    }

    public static String get(String option){
        Date d = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(d);

        String day, month, year, hour, minute, second, selection;

        day = Integer.toString(calendar.get(Calendar.DATE));
        month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        year = Integer.toString(calendar.get(Calendar.YEAR));
        hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        minute = Integer.toString(calendar.get(Calendar.MINUTE));
        second = Integer.toString(calendar.get(Calendar.SECOND));

        if(day.length() == 1){
            day = 0 + day;
        }
        if(month.length() == 1){
            month = 0 + month;
        }
        if(hour.length() == 1){
            hour = 0 + hour;
        }
        if(minute.length() == 1){
            minute = 0 + minute;
        }

        option = option.toLowerCase();
        switch (option) {
            case "day": selection = day;break;
            case "month": selection = month;break;
            case "year": selection = year;break;
            case "hour": selection = hour;break;
            case "minute": selection = minute;break;
            case "second": selection = second;break;
            default: selection = "Invalid month";break;
        }

        return selection;
    }

    //ISO 8601 sirve en calendarios.
    public static String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,locale);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date date = new Date();
        String dateFormat = simpleDateFormat.format(date);
        LOGGER.log(Level.INFO,"Get date:" + dateFormat);
        return dateFormat;
    }

    public static String addSubstract(String date, String timeUnit, int amount) {
        String goodDate = date;
        if(pattern.contains("-")){
            if(date.contains("/")){
                goodDate = DatesCalendar.changeDiagonalToISOFormat(date);
            }
        }
        Date temporal = reviewPattern(goodDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(temporal);

        switch(timeUnit) {

            case "minute":{
                calendar.add(Calendar.MINUTE, amount);
                break;
            }

            case "hour":{
                calendar.add(Calendar.HOUR, amount);
                break;
            }

            case "day":{
                calendar.add(Calendar.DAY_OF_YEAR, amount);
                break;
            }

            case "month":{
                calendar.add(Calendar.MONTH, amount);
                break;
            }

            case "year":{
                calendar.add(Calendar.YEAR , amount);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + timeUnit);
        }

        temporal = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,locale);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(temporal);
    }

    private static Date functionDate(String changeDate){
        SimpleDateFormat format = new SimpleDateFormat(pattern,locale);
        format.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date date = null;
        try{
            date = format.parse(changeDate);
        }catch (Exception ParseException){
            Assert.fail(ParseException.getMessage());
        }
        return date;
    }

    //dd/MM/yyyy to yyyy-MM-dd
    public static String changeDiagonalToISOFormat(String dateWithoutFormat){
        String[] splitDate = dateWithoutFormat.split("/");
        return splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];
    }

    public static String changeISOToDiagonalFormat(String dateWithoutFormat){
        String[] splitDate = dateWithoutFormat.split("-");
        return splitDate[2] + "/" + splitDate[1] + "/" + splitDate[0];
    }

    public static List<String> sortDatesAscending(List<String> dateList){
        List<Date> dates = new ArrayList<>();
        for(String dateStr : dateList){
            dates.add(DatesCalendar.functionDate(dateStr));
        }
        for(int i = 0; i < dates.size(); i++){
            for(int j = 0; j < dates.size(); j++){
                if(dates.get(i).before(dates.get(j))){
                    Date aux = dates.get(i);
                    dates.set(i, dates.get(j));
                    dates.set(j, aux);
                }
            }
        }
        List<String> orderedDates = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy",locale);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        for(Date dateSingle : dates){
            String dateFormat = simpleDateFormat.format(dateSingle);
            orderedDates.add(dateFormat);
        }
        return orderedDates;
    }

    public static String changeDateFormat(String date, String inputPattern, String outputPattern) {
        Date temporal = functionDateWithFormat(date, inputPattern);
        SimpleDateFormat sdf = new SimpleDateFormat(outputPattern,locale);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(temporal);
    }

    private static Date functionDateWithFormat(String inputDate, String outputPattern) {
        SimpleDateFormat format = new SimpleDateFormat(outputPattern,locale);
        format.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date date = null;
        try {
            date = format.parse(inputDate);
        } catch (Exception ParseException) {
            Assert.fail(ParseException.getMessage());
        }
        return date;
    }

    public static List<String> generateAllDatesRange(String inclusiveInitialDate, String inclusiveFinalDate) {
        List<String> dateRange = new ArrayList<>();
        String temporaryDate = inclusiveInitialDate;
        String temporaryDateFinal = inclusiveFinalDate;
        if(pattern.contains("-")){
            if(inclusiveInitialDate.contains("/")){
                temporaryDate = DatesCalendar.changeDiagonalToISOFormat(inclusiveInitialDate);
            }
            if(inclusiveFinalDate.contains("/")){
                temporaryDateFinal = DatesCalendar.changeDiagonalToISOFormat(inclusiveFinalDate);
            }
        }

        while(getTimeDifferenceBetweenDates(temporaryDate, temporaryDateFinal, "days") >= 0) {
            dateRange.add(temporaryDate);
            temporaryDate = addSubstract(temporaryDate, "day", 1);
        }

        System.out.println("Difference between dates: " + getTimeDifferenceBetweenDates(temporaryDateFinal, temporaryDate, "days"));

        return dateRange;
    }

    public static String getTheFirstDateOfTheMonth(String date) {
        int minimumDay;
        String minimumDate;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,locale);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date dateTemporal = reviewPattern(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTemporal);

        minimumDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, minimumDay);

        dateTemporal = cal.getTime();

        minimumDate = sdf.format(dateTemporal);

        return minimumDate;
    }

    public static String getTheLastDateOfTheMonth(String date) {
        int maximumDay;
        String maximumDate;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,locale);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date dateTemporal = reviewPattern(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTemporal);
        maximumDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, maximumDay);
        dateTemporal = cal.getTime();
        maximumDate = sdf.format(dateTemporal);

        return maximumDate;
    }

    public static String getRandomDateRange(String initialDate, String finalDate) {
        Date temporaryStartDate = reviewPattern(initialDate);
        Date temporaryEndDate = reviewPattern(finalDate);
        Date departureDate;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,locale);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        departureDate = new Date(ThreadLocalRandom.current().nextLong(temporaryStartDate.getTime(), temporaryEndDate.getTime()));
        return sdf.format(departureDate);
    }

    public static void defineLocale(Locale newLocale){
        locale = newLocale;
    }
    public static void definePattern(String newPattern) {
        pattern = newPattern;
    }
    public static void defineISOPattern() {
        pattern = "yyyy-MM-dd";
    }
    public static void defineDiagonalPattern() {
        pattern = "dd/MM/yyyy";
    }
}
