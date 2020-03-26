package com.example.tagesstruktur;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Internal_Database {

    private static final String PACKAGE_NAME = "com.example.tagesstruktur";
    private String dataBaseName, table;
    private Context context;
    private SQLiteDatabase dataBase;
    private List<String> columns;
    private List<DataBaseType> types;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //The Android's default system path of your application database (/data/data/<application_package_name>/databases)
    private static final String DB_PATH = "/data/data/" + PACKAGE_NAME + "/databases/";

    /**
     *
     * @param context      of calling constructor
     * @param dataBaseName name of database
     * @param table        name of table in database "dataBaseName"
     * @param columns      names of all columns in the table
     * @param types        types related to columns in the table
     *                     list of columns and types should have the same size
     */
    public Internal_Database(Context context, String dataBaseName, String table, List<String> columns, List<DataBaseType> types) {
        this.dataBaseName = dataBaseName;
        this.context = context;
        this.table = table;
        this.columns = columns;
        this.types = types;

//todo: Ich weiß nicht warum, aber die Datenbank kann nur erstellt werden wenn dieser Code vorher ausgeführt wird
        File dbFile = Internal_Database.this.context.getDatabasePath(dataBaseName);
        System.out.println("Path exists: " + dbFile.exists());


        dataBase = SQLiteDatabase.openOrCreateDatabase(DB_PATH + dataBaseName, null);
        StringBuilder query = new StringBuilder();


        // create table
        query.append("CREATE TABLE IF NOT EXISTS ").append(table).append("(id INTEGER AUTO_INCREMENT primary key,");
        for (int i = 0; i < columns.size(); i++) {
            query.append(columns.get(i)).append(" ").append(types.get(i).toString()).append(",");

        }
        query.deleteCharAt(query.length() - 1);
        query.append(");");

        dataBase.execSQL(query.toString());
    }


    /**
     * Classes that extend Internal_Database, can also extend this DataSet class and save Data with
     * the correct data types corresponding to the columns. With defining the class DataSet already
     * in this class it is possible to return a List of DataSet elements in the "select" method.
     */
    static class DataSet {
        protected List<String> stringValues;

        public DataSet(List<String> values) {
            this.stringValues = values;
        }

        public List<String> getStringValues() {
            return stringValues;
        }
    }


    /**
     * data should contain values of every column. If columns should stay empty, "data" should
     * contain a default instead. This is important, because otherwise its not clear which columns
     * are empty.
     *
     * @param data insert data with the same order as the columns
     * @return true if data could be inserted
     */
    public boolean insert(List<String> data) {
        if (data.size() != columns.size()) return false;

        StringBuilder query = new StringBuilder();
        // first value is always NULL because of the automatically incrementing id
        query.append("INSERT INTO ").append(table).append(" VALUES(NULL, ");
        for (String datum : data) {
            query.append("'").append(datum).append("',");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(");");
        dataBase.execSQL(query.toString());
        return true;
    }

    public boolean insert(DataSet dataSet) {
        return insert(dataSet.getStringValues());
    }

    public List<DataSet> select(String condition) {

        List<DataSet> result = new ArrayList<>();
        String query = "Select * from " + table + " " + condition;

        Cursor cursor = dataBase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            List<String> values = new ArrayList<>();
            for (String column : columns) {
                values.add(cursor.getString(cursor.getColumnIndex(column)));
            }
            result.add(new DataSet(values));
            cursor.moveToNext();
        }

        cursor.close();
        return result;
    }

}
