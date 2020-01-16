package com.stevealves.phonebooktp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.stevealves.phonebooktp.conf.FeedReaderContract;
import com.stevealves.phonebooktp.conf.FeedReaderDbHelper;
import com.stevealves.phonebooktp.model.Contacto;

import java.io.ByteArrayOutputStream;

public class ContactosDao {

    Context context;

    //Create/Find DB
    FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
    // Gets the data repository in write mode
    SQLiteDatabase db = dbHelper.getWritableDatabase();

    public boolean salvar(Contacto contacto){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = contacto.getImg();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imagem = stream.toByteArray();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(FeedReaderContract.FeedEntry.COLUMN_NOME, contacto.getFullName());
        values.put(FeedReaderContract.FeedEntry.COLUMN_PHONE, contacto.getPhoneNumber());
        values.put(FeedReaderContract.FeedEntry.COLUMN_EMAIL, contacto.getFullName());
        values.put(FeedReaderContract.FeedEntry.COLUMN_BIRTHDAY, contacto.getFullName());
        values.put(FeedReaderContract.FeedEntry.COLUMN_PHOTO, imagem);
        values.put(FeedReaderContract.FeedEntry.COLUMN_LATITUDE, contacto.getLatitude());
        values.put(FeedReaderContract.FeedEntry.COLUMN_LONGITUDE, contacto.getLongitude());

        try {
            db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
            Log.d("TAG", "CONTACTO SALVO");
        }catch (Exception e){
            Log.d("TAG", "ERRO AO SALVAR CONTACTO");
            return false;
        }
        return true;
    }

    public boolean atualizar(Contacto contacto){
        return false;
    }

    public boolean delete(Contacto contacto){
        return false;
    }
}
