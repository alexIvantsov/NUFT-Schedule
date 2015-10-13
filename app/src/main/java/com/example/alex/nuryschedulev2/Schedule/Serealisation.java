package com.example.alex.nuryschedulev2.Schedule;

import android.content.Context;

import com.example.alex.nuryschedulev2.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;


public class Serealisation {

    public static Object readFromFile(Context context) {
        Object object = null;
        try {
            FileInputStream fis = context.openFileInput(context.getString(R.string.file_name));
            ObjectInputStream is = new ObjectInputStream(fis);
            object = is.readObject();
            is.close();
        } catch (IOException w) {
            w.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static void writeToFile(Context context, Object object) {
        try {
            ObjectOutput os = new ObjectOutputStream(new FileOutputStream
                    (new File(context.getFilesDir(),"")+File.separator+context.getString(R.string.file_name)));
            os.writeObject(object);
            os.close();
        } catch (IOException w) {
            w.printStackTrace();
        }
    }
}

