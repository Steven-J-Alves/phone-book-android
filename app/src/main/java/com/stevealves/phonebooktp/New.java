package com.stevealves.phonebooktp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.stevealves.phonebooktp.DAO.ContactosDao;
import com.stevealves.phonebooktp.utils.Common;
import com.stevealves.phonebooktp.model.Contacto;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class New extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Views
    private EditText fullName;
    private EditText phoneNumber;
    private EditText Email;
    private EditText Birthday;
    private ImageView imgGalery;

    private ImageView map;

    private Button btnCancel;
    private Button btnOk;

    private AlertDialog.Builder dialog;

    Bitmap photo;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumberId);
        Email = findViewById(R.id.emailId);
        Birthday = findViewById(R.id.birthdayId);
        imgGalery = findViewById(R.id.galeryPhotoId);

        map = findViewById(R.id.mapId);

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

        Birthday.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDatePickerDialog();
                return false;
            }
        });

        //SAVE CONTACT
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname = fullName.getText().toString();
                String phonemunber = phoneNumber.getText().toString();
                String email = Email.getText().toString();
                String birthday = Birthday.getText().toString();

                /* get lat and long from map click adress */
                Bundle extras = getIntent().getExtras();
                double lat = extras.getDouble("latitude", 0);
                double longi = extras.getDouble("longitude", 0);

                ContactosDao contactosDao = new ContactosDao(getApplicationContext());
                contactosDao.salvar(new Contacto(fullname, phonemunber, email, birthday, photo, lat, longi));

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

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
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

    // Date Picket Configuration
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String data = dayOfMonth + "/" + month + "/" + year;
        Birthday.setText(data);
    }
}
