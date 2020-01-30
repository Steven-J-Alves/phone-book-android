package com.stevealves.phonebooktp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stevealves.phonebooktp.dbConf.FeedReaderContract;
import com.stevealves.phonebooktp.dbConf.FeedReaderDbHelper;
import com.stevealves.phonebooktp.model.Contacto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactosDao {

    private SQLiteDatabase db_escrever;
    private SQLiteDatabase db_ler;
    private Context context;

    public ContactosDao(Context context){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        db_escrever = dbHelper.getWritableDatabase();
        db_ler = dbHelper.getReadableDatabase();
        this.context = context;
    }

    public List<Contacto> getContatos(){
        List<Contacto> listaContacto = new ArrayList<>();

        String sql = "SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME + " ;";
        Cursor cursor = db_ler.rawQuery(sql, null);

        while (cursor.moveToNext()){
             Contacto contatos = new Contacto();

             int id = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry._ID));
             String nome = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NOME));
             String telefone = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_PHONE));
             String email = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_EMAIL));
             String dataNasc = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_BIRTHDAY));
             Double latitude = cursor.getDouble(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_LATITUDE));
             Double longitude = cursor.getDouble(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_LONGITUDE));
             int fav = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_FAV));
             byte[] foto = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_PHOTO));
             ByteArrayInputStream inputStream = new ByteArrayInputStream(foto);
             Bitmap fotoBitmap = BitmapFactory.decodeStream(inputStream);

             contatos.setId(id);
             contatos.setFullName(nome);
             contatos.setPhoneNumber(telefone);
             contatos.setEmail(email);
             contatos.setBirthdayDate(dataNasc);
             contatos.setFavorite(fav);
             contatos.setImg(fotoBitmap);
             contatos.setLatitude(latitude);
             contatos.setLongitude(longitude);

             listaContacto.add(contatos);
        }
        cursor.close();
        return listaContacto;
    }

    public boolean salvar(Contacto contacto){
        Bitmap bitmap = contacto.getImg();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imagem = stream.toByteArray();

        ContentValues values = new ContentValues();

        values.put(FeedReaderContract.FeedEntry.COLUMN_NOME, contacto.getFullName());
        values.put(FeedReaderContract.FeedEntry.COLUMN_PHONE, contacto.getPhoneNumber());
        values.put(FeedReaderContract.FeedEntry.COLUMN_EMAIL, contacto.getEmail());
        values.put(FeedReaderContract.FeedEntry.COLUMN_BIRTHDAY, contacto.getBirthdayDate());
        values.put(FeedReaderContract.FeedEntry.COLUMN_PHOTO, imagem);
        values.put(FeedReaderContract.FeedEntry.COLUMN_FAV, contacto.getFavorite());
        values.put(FeedReaderContract.FeedEntry.COLUMN_LATITUDE, contacto.getLatitude());
        values.put(FeedReaderContract.FeedEntry.COLUMN_LONGITUDE, contacto.getLongitude());

        try {
            long newRowId = db_escrever.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        }catch (Exception e){
            return false;
        }
        db_escrever.close();
        return true;
    }

    public boolean atualizar(Contacto contacto){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = contacto.getImg();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imagem = stream.toByteArray();

        ContentValues contentValues = new ContentValues();

        contentValues.put(FeedReaderContract.FeedEntry._ID, contacto.getId());
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NOME, contacto.getFullName());
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_PHONE, contacto.getPhoneNumber());
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_EMAIL, contacto.getEmail());
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_BIRTHDAY, contacto.getBirthdayDate());
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_PHOTO, imagem);
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_LATITUDE, contacto.getLatitude());
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_LONGITUDE, contacto.getLongitude());
        try {
           String id = String.valueOf(contacto.getId());
           String[] ids = {id};
            db_escrever.update(FeedReaderContract.FeedEntry.TABLE_NAME, contentValues, "_id = ?", ids);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean delete(Contacto contacto){
        try {
            String[] ids = {String.valueOf(contacto.getId())};
            db_escrever.delete(FeedReaderContract.FeedEntry.TABLE_NAME, "_id = ?",ids);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean setFav(Contacto contacto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_FAV, contacto.getFavorite());
        try {
            String[] ids = {String.valueOf(contacto.getId())};
            db_escrever.update(FeedReaderContract.FeedEntry.TABLE_NAME, contentValues,"_id=?", ids);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
