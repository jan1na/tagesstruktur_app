package com.example.tagesstruktur;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Internal_Storage {

    private String file;
    private Context context;

    public Internal_Storage(Context context, String file){
        this.file = file;
        this.context = context;
    }

    public void saveData(List<String> data) {
        try {
            // Open Stream to write file.
            FileOutputStream out = context.openFileOutput(file, Context.MODE_APPEND);

            for(String s: data){
                s+="\n";
                out.write(s.getBytes());
            }
            out.close();
        } catch (Exception e) {
            Toast.makeText(context,"Error in Internal Storage"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void saveData(String data) {
        try {
            // Open Stream to write file.
            FileOutputStream out = context.openFileOutput(file, Context.MODE_APPEND);
            data+="\n";
            out.write(data.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(context,"Error in Internal Storage"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void overrideData(String data){
        try {
            // Open Stream to write file.
            FileOutputStream out = context.openFileOutput(file, MODE_PRIVATE);
            data+="\n";
            out.write(data.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(context,"Error in Internal Storage"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public boolean fileExists(){
        File f = context.getFileStreamPath(file);
        return !(f == null || !f.exists());
    }

    public List<String> readData() {
        try {
            // Open stream to read file.
            FileInputStream in = context.openFileInput(file);

            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            List<String> data = new ArrayList<>();
            String s = "";
            while ((s = br.readLine()) != null) {
                data.add(s);
                s = "";
            }
            return data;

        } catch (FileNotFoundException e){
            return new ArrayList<>();

        }catch (Exception e) {
            Toast.makeText(context,"Error:"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
