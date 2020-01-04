package com.stevealves.phonebooktp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.stevealves.phonebooktp.common.common;
import com.stevealves.phonebooktp.contacto.Contacto;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class New extends AppCompatActivity {

    private ImageView imgGalery;

    private EditText fullName;
    private EditText phoneNumber;
    private EditText Email;
    private EditText Birthday;

    private Button btnCancel;
    private Button btnOk;

    String fullname;
    String phonemunber;
    String email;
    String birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        imgGalery = findViewById(R.id.galeryPhotoId);
        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumberId);
        Email = findViewById(R.id.emailId);
        Birthday = findViewById(R.id.birthdayId);

        btnCancel = findViewById(R.id.btnCancelId);
        btnOk = findViewById(R.id.btnOkId);

        imgGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 0);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 fullname = fullName.getText().toString();
                 phonemunber = phoneNumber.getText().toString();
                 email = Email.getText().toString();
                 birthday = Birthday.getText().toString();
                //String photo = imgGalery.getText().toString();

                addItensList();
                //Toast.makeText(getApplicationContext(), fullname, Toast.LENGTH_LONG).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    //galeria
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgGalery.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void addItensList() {
        //Contacto contacto = new Contacto(fullname, phoneNumber, email, birthday, 345, photo);
        //ommon.listaContactos.add(contacto);
        Contacto contact1 = new Contacto("steven2", "34354534534", "stevenemail@gmail.com", "05/12/19", 345, R.drawable.ic_keyboard_arrow_right_black_24dp);
        common.listaContactos.add(contact1);
    }


}
