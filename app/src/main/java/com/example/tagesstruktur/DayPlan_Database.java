package com.example.tagesstruktur;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class extending Internal_Database to adjust it for the day plan data.
 */
public class DayPlan_Database extends Internal_Database{

    //column names:
    private static final String DATE = "date",  ACTIVITY = "activity", ISDONE = "isDone";

    private static List<String> columns = new ArrayList<>();
    private static List<DataBaseType> types = new ArrayList<>();
    static {
        columns.add(DATE);
        columns.add(ACTIVITY);
        columns.add(ISDONE);
        types.add(DataBaseType.DATETIME);
        types.add(DataBaseType.TEXT);
        types.add(DataBaseType.TEXT); //0 == False, 1 == True
    }

    /**
     * To save the values in the correct data types and convert data from the database to the
     * correct data type (boolean and Date).
     */
    public static class DataSet_DayPlan extends DataSet{
        private Date date;
        private String activity;
        private boolean isDone;

        /**
         *
         * @param dataSet convert superclass object to subclass object
         */
        public DataSet_DayPlan(DataSet dataSet){
            super(dataSet.getStringValues());
            try {
                this.date = Internal_Database.dateFormat.parse(dataSet.getStringValues().get(0));
                this.activity = dataSet.getStringValues().get(1);
                this.isDone = !dataSet.getStringValues().get(2).equals("0");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        public DataSet_DayPlan(Date date, String activity, boolean isDone){
            super(new ArrayList<String>());
            this.date = date;
            this.activity = activity;
            this.isDone = isDone;
            super.stringValues.add(dateFormat.format(date));
            super.stringValues.add(activity);
            super.stringValues.add(isDone ? "1": "0");

        }

        public Date getDate() {
            return date;
        }

        public String getActivity() {
            return activity;
        }

        public boolean isDone() {
            return isDone;
        }
    }


    /**
     * @param context      of calling constructor
     */
    public DayPlan_Database(Context context) {
        super(context, "internalDatabase", "dayPlan", columns, types);
    }


    /**
     * Convert data to strings
     * @param date
     * @param activity
     * @return
     */
    public boolean insert(Date date, String activity){
        return insert(date, activity, false);
    }

    public boolean insert(Date date, String activity, boolean isDone){
        List<String> data = new ArrayList<>();
        data.add(Internal_Database.dateFormat.format(date));
        data.add(activity);
        data.add(isDone? "1":"0");
        return super.insert(data);
    }

    public List<DataSet_DayPlan> getTodayDayPlan(){
        Date today = Calendar.getInstance().getTime();
        return getDayPlan(today);
    }

    /**
     * Create condition to select data only with this "date" day and order it by date. Then the
     * super select method is called with this condition.
     *
     * @param date
     * @return
     */
    public List<DataSet_DayPlan> getDayPlan(Date date){
        List<DataSet_DayPlan> dayPlan = new ArrayList<>();
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
        String day = dayFormat.format(date);
        List<DataSet> result = select("WHERE "+DATE+" >= '"+day+" 00:00:00' AND "+DATE+" <= '"+day+" 23:59:59' ORDER BY "+DATE+" ASC");
        for(DataSet datum: result){
            dayPlan.add(new DataSet_DayPlan(datum));
        }
        return dayPlan;
    }

}
