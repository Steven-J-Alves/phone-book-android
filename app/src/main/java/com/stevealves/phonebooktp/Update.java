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

import com.stevealves.phonebooktp.model.Contacto;
import com.stevealves.phonebooktp.utils.Common;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Update extends AppCompatActivity {

    // Views
    private EditText fullNameUp;
    private EditText phoneNumberUp;
    private EditText EmailUp;
    private EditText BirthdayUp;
    private ImageView imgGaleryUp;

    private EditText latitudeEdtUp;
    private EditText longitudeEdtUp;

    private Button btnCancelUpdate;
    private Button btnUpdate;

    private AlertDialog.Builder dialog;

    //to get and set
    String fullname;
    String phonemunber;
    String email;
    String birthday;
    Bitmap photo;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        fullNameUp = findViewById(R.id.fullNameUp);
        phoneNumberUp = findViewById(R.id.phoneNumberIdUp);
        EmailUp = findViewById(R.id.emailIdUp);
        BirthdayUp = findViewById(R.id.birthdayIdUp);
        imgGaleryUp = findViewById(R.id.galeryPhotoIdUp);

        latitudeEdtUp = findViewById(R.id.latitudeUpId);
        longitudeEdtUp = findViewById(R.id.longitudeUpId);

        btnCancelUpdate = findViewById(R.id.btnCancelUpdateId);
        btnUpdate = findViewById(R.id.btnUpdateId);

        imgGaleryUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 0);

            }
        });

        getData();
        setData();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
            }
        });

        btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new AlertDialog.Builder(Update.this);

                dialog.setMessage("Deseja Cancelar?");

                dialog.setNegativeButton("Não",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Continua...", Toast.LENGTH_SHORT).show();
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


    private void getData(){
        id = getIntent().getIntExtra("id", 0);

        fullname = Common.listaContactos.get(id).getFullName();
        phonemunber = Common.listaContactos.get(id).getPhoneNumber();
        email = Common.listaContactos.get(id).getEmail();
        birthday = Common.listaContactos.get(id).getBirthdayDate();
        photo = Common.listaContactos.get(id).getImg();

    }

    private void setData(){
        fullNameUp.setText(fullname);
        phoneNumberUp.setText(phonemunber);
        EmailUp.setText(email);
        BirthdayUp.setText(birthday);

        if(Common.listaContactos.get(id).getImg() != null){
            imgGaleryUp.setImageBitmap(photo);
        } else {
            imgGaleryUp.setImageResource(R.drawable.ic_camera_alt_black_24dp);
        }
    }

    private void Update(){
        Contacto contacto = Common.listaContactos.get(id);

        contacto.setFullName(fullNameUp.getText().toString());
        contacto.setPhoneNumber(phoneNumberUp.getText().toString());
        contacto.setEmail(EmailUp.getText().toString());
        contacto.setBirthdayDate(BirthdayUp.getText().toString());
        contacto.setImg(photo);

        Common.listaContactos.set(id, contacto);
        Intent intent = new Intent(getApplicationContext(), ListaContactos.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgGaleryUp.setImageBitmap(selectedImage);
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
