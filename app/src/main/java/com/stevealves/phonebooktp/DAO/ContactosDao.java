package com.stevealves.phonebooktp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;
import android.util.Log;

import com.stevealves.phonebooktp.conf.FeedReaderContract;
import com.stevealves.phonebooktp.conf.FeedReaderDbHelper;
import com.stevealves.phonebooktp.model.Contacto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactosDao {

    public SQLiteDatabase db_escrever;
    public SQLiteDatabase db_ler;
    Context context;

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

            Long id = cursor.getLong(cursor.getColumnIndex(FeedReaderContract.FeedEntry._ID));
             String nome = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NOME));
             String telefone = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_PHONE));
             String email = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_EMAIL));
             String dataNasc = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_BIRTHDAY));
             Double latitude = cursor.getDouble(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_LATITUDE));
             Double longitude = cursor.getDouble(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_LONGITUDE));
             byte[] foto = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_PHOTO));

             ByteArrayInputStream inputStream = new ByteArrayInputStream(foto);
             Bitmap fotoBitmap = BitmapFactory.decodeStream(inputStream);

             contatos.setId(id);
             contatos.setImg(fotoBitmap);
             contatos.setFullName(nome);
             contatos.setPhoneNumber(telefone);
             contatos.setEmail(email);
             contatos.setBirthdayDate(dataNasc);
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

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(FeedReaderContract.FeedEntry.COLUMN_NOME, contacto.getFullName());
        values.put(FeedReaderContract.FeedEntry.COLUMN_PHONE, contacto.getPhoneNumber());
        values.put(FeedReaderContract.FeedEntry.COLUMN_EMAIL, contacto.getEmail());
        values.put(FeedReaderContract.FeedEntry.COLUMN_BIRTHDAY, contacto.getBirthdayDate());
        values.put(FeedReaderContract.FeedEntry.COLUMN_PHOTO, imagem);
        values.put(FeedReaderContract.FeedEntry.COLUMN_LATITUDE, contacto.getLatitude());
        values.put(FeedReaderContract.FeedEntry.COLUMN_LONGITUDE, contacto.getLongitude());

        try {
            long newRowId = db_escrever.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
            Log.d("lll", "SALVAR CONTACTO:" + newRowId);
        }catch (Exception e){
            Log.d("lll", "ERRO AO SALVAR CONTACTO");
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

        contentValues.put("nome", contacto.getFullName());
        contentValues.put("telefone", contacto.getPhoneNumber());
        contentValues.put("email", contacto.getEmail());
        contentValues.put("dataNasc", contacto.getBirthdayDate());
        contentValues.put("foto", imagem);
        try {
            String[] ids = {contacto.getId().toString()};
            db_escrever.update(FeedReaderContract.FeedEntry.TABLE_NAME, contentValues, "id=?", ids);
            Log.i("INFO", "Contato Atualizado com sucesso");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao Atualizar contato" + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean delete(Contacto contacto){
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        Bitmap bitmap = contatosUsuarios.getImagemUsuario();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] imagem = stream.toByteArray();
//
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("nome", contatosUsuarios.getNomeUsuario());
//        contentValues.put("telefone", contatosUsuarios.getTelefoneUsuario());
//        contentValues.put("email", contatosUsuarios.getEmailUsuario());
//        contentValues.put("dataNasc", contatosUsuarios.getDatanascimento());
//        contentValues.put("foto", imagem);
//        try {
//
//            String[] ids = {contatosUsuarios.getID().toString()};
//            escreve.delete(DbContatosUsuarios.TABELA_CONTATOS,"id=?",ids);
//            Log.i("INFO", "Contato " + contatosUsuarios.getNomeUsuario() + " Deletado com sucesso");
//
//
//
//        } catch (Exception e) {
//            Log.e("INFO", "Erro ao Deletar contato" + e.getMessage());
//            return false;
//        }
//
//
        return true;
    }
}
