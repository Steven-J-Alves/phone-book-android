package com.stevealves.phonebooktp.adapter;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.stevealves.phonebooktp.DAO.ContactosDao;
import com.stevealves.phonebooktp.Favorite;
import com.stevealves.phonebooktp.ListaContactos;
import com.stevealves.phonebooktp.R;
import com.stevealves.phonebooktp.model.Contacto;
import com.stevealves.phonebooktp.utils.Common;


public class ListaFavoritosAdapter  extends RecyclerView.Adapter<ListaFavoritosAdapter.ListaFavoritosHolder>{

    Context context;
    private static final String CHANNEL_ID = "0";
    private static final int NOTIFICATION_ID = 0;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int getItemCount() {
     int count = 0;
        for (int i = 0; i < Common.listaContactos.size(); i++){
            if (Common.listaContactos.get(i).getFavorite() == 1){
                count++;
            }
        }
        return count;
    }

    public class ListaFavoritosHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        int position;

        ImageView imgFav;
        ImageView trashFav;
        TextView nameFav;
        TextView numberFav;

        ListaFavoritosHolder(@NonNull View itemView) {
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

    private void call(String phoneNumber){
        String uri = "tel:" + phoneNumber ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        context.startActivity(intent);
    }

    @SuppressLint("IntentReset")
    private void sms(String phoneNumber){
        Uri smsUri = Uri.parse("tel:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
        intent.setType("vnd.android-dir/mms-sms");
        context.startActivity(intent);
    }

    private void RemoveContactFav(int position){
        Contacto contacto = Common.listaContactos.get(position);
        ContactosDao contactosDao = new ContactosDao(context);

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Deseja eliminar?");

        dialog.setNegativeButton("Não",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialog.setPositiveButton("Sim",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean res = contactosDao.delete(contacto);
                        if(res){
                            contacto.setFavorite(0);
                            contactosDao.setFav(contacto);

                            notification(Common.listaContactos.get(position).getFullName());

                            Intent refresh = new Intent(context, Favorite.class);
                            context.startActivity(refresh);
                        }
                    }
                });
        dialog.create();
        dialog.show();
    }

    private void notification(String nome){
        createNotificationChannel();
        androidx.core.app.NotificationCompat.Builder builder = new androidx.core.app.NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_contact_phone_black_24dp)
                .setContentTitle("Notificação")
                .setContentText(nome + " foi deletado do favorito")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notificacao_del_contacto";
            String description =  "del notificacao";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }

}
