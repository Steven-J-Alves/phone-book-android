package com.stevealves.phonebooktp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stevealves.phonebooktp.adapter.ListaContactosAdapter;
import com.stevealves.phonebooktp.common.common;
import com.stevealves.phonebooktp.contacto.Contacto;

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

        ListaContactosAdapter ListaContactosAdapter = new ListaContactosAdapter(this, common.listaContactos);

        recyclerView.setAdapter(ListaContactosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.buttonMainId);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), New.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

    }


}
