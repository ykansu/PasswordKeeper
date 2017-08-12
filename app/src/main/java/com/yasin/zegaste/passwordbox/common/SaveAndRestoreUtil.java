package com.yasin.zegaste.passwordbox.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordDataStructure;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Yasin on 12.11.2016.
 */

public class SaveAndRestoreUtil {
    public static String savePasswordDataStructureKey = "PasswordDataStructure";

    public static void saveObjectToLocal(Serializable objectToSave, Context context, String saveString) throws IOException {

        SharedPreferences sharedPref = context.getSharedPreferences("passwordbox", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(saveString, toString(objectToSave));
        editor.commit();
    }

    private static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    public static Object restoreObjectFromLocal(Context context, String saveString) throws IOException, ClassNotFoundException {
        SharedPreferences sharedPref = context.getSharedPreferences("passwordbox", Context.MODE_PRIVATE);
        String retrivalString = sharedPref.getString(saveString, null);
        if (retrivalString != null)
            return (PasswordDataStructure) fromString(retrivalString);
        else throw new NullPointerException("String null alındı");
    }

    private static Object fromString(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.decode(s, Base64.DEFAULT);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    public static boolean isObjectSavedWithThisString(Context context, String saveString) {
        SharedPreferences sharedPref = context.getSharedPreferences("passwordbox", Context.MODE_PRIVATE);
        return sharedPref != null && sharedPref.contains(saveString);
    }

}
