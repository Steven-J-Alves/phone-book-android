package com.stevealves.phonebooktp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stevealves.phonebooktp.DAO.ContactosDao;
import com.stevealves.phonebooktp.MapsActivity;
import com.stevealves.phonebooktp.R;
import com.stevealves.phonebooktp.Update;
import com.stevealves.phonebooktp.utils.Common;
import com.stevealves.phonebooktp.model.Contacto;

import java.util.List;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ListaContactosHolder>{

    private List<Contacto> contactos;
    private Context context;

    public ListaContactosAdapter(Context ctx, List<Contacto> listaContacto){
        this.context = ctx;
        this.contactos = listaContacto;
    }

    @NonNull
    @Override
    public ListaContactosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //do this first
        LayoutInflater inflate = LayoutInflater.from(context);
        View view = inflate.inflate(R.layout.item_list_recycler, parent, false);
        return new ListaContactosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaContactosHolder holder, final int position) {

        Contacto contacto = contactos.get(position);

        holder.name.setText(contacto.getFullName());
        holder.number.setText(contacto.getPhoneNumber());

        holder.position = position;

        if(contacto.getImg() != null){
            holder.img.setImageBitmap(contacto.getImg());
        } else {
            holder.img.setImageResource(R.drawable.ic_person_outline_black_24dp);
        }

        holder.arrowNew.setOnClickListener(v -> {
            Intent intent = new Intent(context, Update.class);
            intent.putExtra("id", position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public class ListaContactosHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        ImageView img;
        ImageView arrowNew;
        TextView name;
        TextView number;

        public int position;

        public ListaContactosHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_custum_view_id);
            name = itemView.findViewById(R.id.name_custum_view_id);
            number = itemView.findViewById(R.id.number_custum_view_id);
            arrowNew = itemView.findViewById(R.id.arrow_custum_view_id);

            itemView.setOnCreateContextMenuListener(this);
            
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, itemView.getId(), 0, "Call").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    String phoneNumber = contactos.get(position).getPhoneNumber();
                    call(phoneNumber);
                    return false;
                }
            });
            menu.add(0, itemView.getId(), 0, "SMS").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    String phoneNumber = contactos.get(position).getPhoneNumber();
                    sms(phoneNumber);
                    return false;
                }
            });
            menu.add(0, itemView.getId(), 0, "Map").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    getMap(position);
                    return false;
                }
            });
            menu.add(0, itemView.getId(), 0, "Favorite").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    //addFavorite(position);
                    return false;
                }
            });
            menu.add(0, itemView.getId(), 0, "Remove").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    //remove(position);
                    return false;
                }
            });
        }
    }

    public void call(String phoneNumber){
        String uri = "tel:" + phoneNumber ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        context.startActivity(intent);
    }

    public void sms(String phoneNumber){
        Uri smsUri = Uri.parse("tel:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
        intent.setType("vnd.android-dir/mms-sms");
        context.startActivity(intent);
    }

   /* public void addFavorite(int position){
        Common.listaContactos.get(position).setFavorite(true);
    }*/

//    public void remove(int position){
//        Common.listaContactos.remove(position);
//        this.notifyDataSetChanged();
//    }

    public void getMap(int position){
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra("id", position);
        context.startActivity(intent);
    }
}
