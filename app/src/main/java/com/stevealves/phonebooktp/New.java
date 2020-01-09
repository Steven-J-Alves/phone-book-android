package com.stevealves.phonebooktp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.stevealves.phonebooktp.utils.Common;
import com.stevealves.phonebooktp.model.Contacto;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class New extends AppCompatActivity {

    // Views
    private EditText fullName;
    private EditText phoneNumber;
    private EditText Email;
    private EditText Birthday;
    private ImageView imgGalery;

    private Button btnCancel;
    private Button btnOk;

    private AlertDialog.Builder dialog;

    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumberId);
        Email = findViewById(R.id.emailId);
        Birthday = findViewById(R.id.birthdayId);
        imgGalery = findViewById(R.id.galeryPhotoId);


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

        //SAVE CONTACTC
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname = fullName.getText().toString();
                String phonemunber = phoneNumber.getText().toString();
                String email = Email.getText().toString();
                String birthday = Birthday.getText().toString();

                Common.listaContactos.add(new Contacto(fullname, phonemunber, email, birthday, photo));

                Intent intent = new Intent(getApplicationContext(),  ListaContactos.class);
                startActivity(intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new AlertDialog.Builder(New.this);

                dialog.setMessage("Deseja Cancelar?");

                dialog.setNegativeButton("Não",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Preenche o formulário...", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                dialog.create();
                dialog.show();
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
                photo = selectedImage;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }



}
