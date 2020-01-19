//package com.stevealves.phonebooktp;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//
//import com.stevealves.phonebooktp.adapter.ListaContactosAdapter;
//import com.stevealves.phonebooktp.adapter.ListaFavoritosAdapter;
//import com.stevealves.phonebooktp.model.Contacto;
//import com.stevealves.phonebooktp.utils.Common;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Favorite extends AppCompatActivity {
//
//    RecyclerView recyclerView;
//    private List<Contacto> contactosFav = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_favorite);
//
//        recyclerView = findViewById(R.id.favorite_recycler_id);
//
//        ListaFavoritosAdapter listacontactosadapter = new ListaFavoritosAdapter(this);
//
//        recyclerView.setAdapter(listacontactosadapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//    }
//}
//
//
