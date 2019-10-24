package com.khoa.myptit.login.repository;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * Created at 9/27/19 9:57 PM by Khoa
 */

/*
 * Created at 9/22/19 3:02 PM by Khoa
 */
public class BaseRepository<T> {


    /**
     *
     * @param context Context
     * @param fileName tên file cần đọc
     * @return đối tượng T cần đọc
     */
    public T read(Context context, String fileName){
        ObjectInputStream inputStream = null;
        T object = null;
        try {
            inputStream = new ObjectInputStream(context.openFileInput(fileName));
            object = (T) inputStream.readObject();
        }catch (Exception e){
            Log.e("Loi", "Loi doc file " + fileName +" : " + e.getMessage());
        } finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }


    /**
     *
     * @param context Context
     * @param fileName ten file can ghi
     * @param object doi tuong T can ghi
     * @return true nếu ghi thành công, false nếu thất bại
     */
    public boolean write(Context context, String fileName, T object){
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(object);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
