package com.example.tagesstruktur;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.List;

public class Internal_Database {
    private String dataBaseName, table;
    private Context context;
    private SQLiteDatabase dataBase;
    private List<String> columns;
    private List<DataBaseType> types;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM");

    /**
     *
     * @param context of calling constructor
     * @param dataBaseName name of database
     * @param table name of table in database "dataBaseName"
     * @param columns names of all columns in the table
     * @param types types related to columns in the table
     *              list of columns and types should have the same size
     */
    public Internal_Database(Context context, String dataBaseName, String table, List<String> columns, List<DataBaseType> types){
        this.dataBaseName = dataBaseName;
        this.context = context;
        this.table = table;
        this.columns = columns;
        this.types = types;
        dataBase = SQLiteDatabase.openOrCreateDatabase(dataBaseName,null);
        StringBuilder query = new StringBuilder();



        // create table
        query.append("CREATE TABLE IF NOT EXISTS ").append(table).append("(id INTEGER AUTO_INCREMENT primary key,");
        for(int i = 0; i<columns.size(); i++){
            if (types.get(i).equals(DataBaseType.DATE)){
                query.append(columns.get(i)).append(" ").append("TEXT").append(",");
            }else {
                query.append(columns.get(i)).append(" ").append(types.get(i).toString()).append(",");
            }
        }
        query.deleteCharAt(query.length()-1);
        query.append(");");
        dataBase.execSQL(query.toString());
    }

    /**
     *
     * @param data insert data with the same order as the columns
     * @return true if data could be inserted
     */
    public boolean insert(List<String> data){
        if(data.size()!=columns.size()) return false;
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(table).append(" VALUES(");
        for(String datum: data){
            query.append("'").append(datum).append("',");
        }
        query.deleteCharAt(query.length()-1);
        query.append(");");
        dataBase.execSQL(query.toString());
        return true;
    }

    public Cursor select(String condition){
        return dataBase.rawQuery("Select * from "+table+" WHERE "+condition,null);
    }

}
