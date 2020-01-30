package com.stevealves.phonebooktp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stevealves.phonebooktp.DAO.ContactosDao;
import com.stevealves.phonebooktp.adapter.ListaContactosAdapter;
import com.stevealves.phonebooktp.model.Contacto;
import com.stevealves.phonebooktp.utils.Common;

import java.util.ArrayList;
import java.util.List;

public class ListaContactos extends AppCompatActivity {

    RecyclerView recyclerView;

    static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);

        recyclerView = findViewById(R.id.main_recycler_id);

        ContactosDao contactosDao = new ContactosDao(getApplicationContext());

        Common.listaContactos = contactosDao.getContatos();
        ListaContactosAdapter listacontactosadapter = new ListaContactosAdapter(this);

        recyclerView.setAdapter(listacontactosadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.buttonMainId);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), New.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_settings_action_bar_id:
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                return true;
            case R.id.main_favorite_action_bar_id:
                Intent intent = new Intent(getApplicationContext(), Favorite.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
