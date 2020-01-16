package com.stevealves.phonebooktp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.os.Build;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.stevealves.phonebooktp.R;
import com.stevealves.phonebooktp.model.Contacto;
import com.stevealves.phonebooktp.utils.Common;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


public class ListaFavoritosAdapter  extends RecyclerView.Adapter<ListaFavoritosAdapter.ListaFavoritosHolder>{

    Context context;

    public ListaFavoritosAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ListaFavoritosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(context);
        View view = inflate.inflate(R.layout.item_fav_recycler, parent, false);
        return new ListaFavoritosAdapter.ListaFavoritosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaFavoritosHolder holder, final int position) {

            if (Common.listaContactos.get(position).getFavorite()) {
                holder.nameFav.setText(Common.listaContactos.get(position).getFullName());
                holder.numberFav.setText(Common.listaContactos.get(position).getPhoneNumber());
                holder.position = position;

                if (Common.listaContactos.get(position).getImg() != null) {
                    holder.imgFav.setImageBitmap(Common.listaContactos.get(position).getImg());
                } else {
                    holder.imgFav.setImageResource(R.drawable.ic_person_outline_black_24dp);
                }

                holder.trashFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RemoveContactFav(position);
                    }
                });
            }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int getItemCount() {
     int count = 0;
        for (int i = 0; i < Common.listaContactos.size(); i++){
            if (Common.listaContactos.get(i).getFavorite()){
                count++;
            }
        }
        //long count =  Common.listaContactos.stream().filter((i) -> i.getFavorite()).count();
        return count;
    }

    public class ListaFavoritosHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        ImageView imgFav;
        ImageView trashFav;
        TextView nameFav;
        TextView numberFav;

        public int position;

        public ListaFavoritosHolder(@NonNull View itemView) {
            super(itemView);
            imgFav = itemView.findViewById(R.id.img_fav_custum_view_id);
            nameFav = itemView.findViewById(R.id.name_fav_custum_view_id);
            numberFav = itemView.findViewById(R.id.number_fav_custum_view_id);
            trashFav = itemView.findViewById(R.id.trash_fav_custum_view_id);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, itemView.getId(), 0, "Call").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    String phoneNumber = Common.listaContactos.get(position).getPhoneNumber();
                    call(phoneNumber);
                    return false;
                }
            });
            menu.add(0, itemView.getId(), 0, "SMS").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    String phoneNumber = Common.listaContactos.get(position).getPhoneNumber();
                    sms(phoneNumber);
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

    public void RemoveContactFav(int position){
        Common.listaContactos.get(position).setFavorite(false);
        this.notifyDataSetChanged();

    }


}
