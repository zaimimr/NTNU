package com.example.zaimi.ov_07;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class fileManager {

    public static Context context;

    public static String readFile(File dir, String fileName) {
        File file = new File(dir, fileName);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {

                }
            }
        }
    }

    public static void writeToFile(File dir, String fileName, String body){
        File file = new File(dir, fileName);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
            writer.write(body);
            return;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(writer != null){
                try{
                    writer.close();
                }catch(Exception e){

                }
            }

        }
    }

    public static void writeObjectToFile(File dir, String fileName, Object[] body){
        File file = new File(dir, fileName);
        FileOutputStream fileOut = null;
        ObjectOutputStream objectOut = null;
        try {
            fileOut = context.openFileOutput(file.getName(), 0);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(body);
            return;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(objectOut != null){
                try{
                    objectOut.close();
                }catch(Exception e){

                }
            }

        }
    }

    public static Object readObjectFile(File dir, String fileName) {
        File file = new File(dir, fileName);
        FileInputStream fileIn = null;
        ObjectInputStream objectIn = null;
        try {
            fileIn = context.openFileInput(file.getName());
            objectIn = new ObjectInputStream(fileIn);
            return objectIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException e) {

                }
            }
            if (objectIn != null) {
                try {
                    objectIn.close();
                } catch (IOException e) {
                }
            }
        }
    }


}
