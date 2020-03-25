package com.example.tagesstruktur;

import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayPlan_Database extends Internal_Database{

    private static List<String> columns = new ArrayList<>();
    private static List<DataBaseType> types = new ArrayList<>();
    static {
        columns.add("date");
        columns.add("activity");
        columns.add("idDone");
        types.add(DataBaseType.DATE);
        types.add(DataBaseType.TEXT);
        types.add(DataBaseType.INTEGER); //0 == False, 1 == True
    }

    public static class DataSet{
        private Date date;
        private String activity;
        private boolean isDone;
        public DataSet(Date date, String activity, boolean isDone){
            this.date = date;
            this.activity = activity;
            this.isDone = isDone;
        }

        public DataSet(String date, String activity, String isDone){
            try {
                this.date = Internal_Database.dateFormat.parse(date);
                this.activity = activity;
                this.isDone = !isDone.equals("0");
            } catch (ParseException e) {
                e.printStackTrace();
            }
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


    public boolean insert(Date date, String activity){
        List<String> data = new ArrayList<>();
        data.add(Internal_Database.dateFormat.format(date));
        data.add(activity);
        data.add("0");
        return super.insert(data);
    }


    public List<DataSet> selectDataSet(String condition){
        List<DataSet> result = new ArrayList<>();
        Cursor cursor = super.select(condition);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            result.add(new DataSet(cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("activity")),
                    cursor.getString(cursor.getColumnIndex("isDone"))));
            cursor.moveToNext();
        }
        return result;
    }

}
