package com.stevealves.phonebooktp.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stevealves.phonebooktp.R;
import com.stevealves.phonebooktp.contacto.Contacto;

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
    public void onBindViewHolder(@NonNull ListaContactosHolder holder, int position) {
        holder.name.setText(contactos.get(position).getFullName());
        holder.number.setText(contactos.get(position).getPhoneNumber());
        holder.img.setImageResource(contactos.get(position).getImg());

        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public class ListaContactosHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        //from here we processed to onBindViewHolder

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

            //simple click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Click "  + name.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
            //add CALL item to menu and implement click
            menu.add(0, itemView.getId(), 0, "Call").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Toast.makeText(context, "CALL " + position, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            //add SMS item to menu and implement click
            menu.add(0, itemView.getId(), 0, "SMS").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Toast.makeText(context, "SMS " + position, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            //add SMS item to menu and implement click
            menu.add(0, itemView.getId(), 0, "Favorite").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Toast.makeText(context, "Favorite " + position, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            //add SMS item to menu and implement click
            menu.add(0, itemView.getId(), 0, "Remove").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Toast.makeText(context, "Remove " + position, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
    }
}
