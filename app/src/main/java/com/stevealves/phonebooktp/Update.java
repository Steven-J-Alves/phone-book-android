package com.stevealves.phonebooktp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.stevealves.phonebooktp.DAO.ContactosDao;
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
    private ImageView mapUp;

    Button btnCancelUpdate;
    Button btnUpdate;

    //to get and set
    int position;
    int id;

    String fullname;
    String phonemunber;
    String email;
    String birthday;
    Bitmap photo;
    double lat;
    double log;

    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        fullNameUp = findViewById(R.id.fullNameUp);
        phoneNumberUp = findViewById(R.id.phoneNumberIdUp);
        EmailUp = findViewById(R.id.emailIdUp);
        BirthdayUp = findViewById(R.id.birthdayIdUp);
        imgGaleryUp = findViewById(R.id.galeryPhotoIdUp);
        mapUp = findViewById(R.id.mapUpId);
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

        getData_setData();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updates();
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

        mapUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // recuperar info lat e
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getData_setData(){
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        Contacto contacto = new Contacto();

        contacto = Common.listaContactos.get(position);
        id = contacto.getId();

        fullname = contacto.getFullName();
        phonemunber = contacto.getPhoneNumber();
        email = contacto.getEmail();
        birthday = contacto.getBirthdayDate();
        photo = contacto.getImg();
        lat = contacto.getLatitude();
        log = contacto.getLongitude();

        fullNameUp.setText(fullname);
        phoneNumberUp.setText(phonemunber);
        EmailUp.setText(email);
        BirthdayUp.setText(contacto.getBirthdayDate());
        imgGaleryUp.setImageBitmap(photo);

//        if(Common.listaContactos.get(id).getImg() != null){
//            imgGaleryUp.setImageBitmap(photo);
//        } else {
//            imgGaleryUp.setImageResource(R.drawable.ic_camera_alt_black_24dp);
//        }
    }


    private void Updates(){
        /* get lat and long from map click update adress */
//        Bundle extras = getIntent().getExtras();
        double latt = 25.25;
        double longi = 2525.2;

        Contacto con = new Contacto();

        con.setId(id);
        con.setFullName(fullNameUp.getText().toString());
        con.setPhoneNumber(phoneNumberUp.getText().toString());
        con.setEmail(EmailUp.getText().toString());
        con.setBirthdayDate(BirthdayUp.getText().toString());
        con.setImg(photo);
        con.setLatitude(latt);
        con.setLongitude(longi);

        ContactosDao contactosDao = new ContactosDao(getApplicationContext());
        contactosDao.atualizar(con);

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
