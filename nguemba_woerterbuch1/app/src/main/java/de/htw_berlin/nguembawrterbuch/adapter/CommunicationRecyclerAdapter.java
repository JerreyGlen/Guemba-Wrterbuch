package de.htw_berlin.nguembawrterbuch.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.htw_berlin.nguembawrterbuch.R;
import de.htw_berlin.nguembawrterbuch.model.data_communication;
import de.htw_berlin.nguembawrterbuch.session.preferences;

public class CommunicationRecyclerAdapter extends RecyclerView.Adapter<CommunicationRecyclerAdapter.CommunicationViewHolder>{
    Context context;
    List <data_communication> listCommunication;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public CommunicationRecyclerAdapter(Context context, List<data_communication> listCommunication) {
        this.context = context;
        this.listCommunication = listCommunication;
    }

    @NonNull
    @Override
    public CommunicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_communication, parent, false);
        return new CommunicationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunicationViewHolder holder, int position) {

        int plus = position + 1;
        data_communication itemPlus = listCommunication.get(plus);
        data_communication itemNormal = listCommunication.get(position);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        Locale locale = new Locale("de", "DE");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MMMM.yyyy", locale);

        if (itemPlus.getDatum().equals(itemNormal.getDatum())){
            holder.linearDatum.setVisibility(View.GONE);
            if (plus == listCommunication.size()){
                plus = plus -1;

            }
        }

        if(itemNormal.getZeit() > (date.getTime() - (1000 * 60 * 60 * 24)) && itemNormal.getZeit() < (date.getTime() - (1000 * 60 * 60 * 24 *2))){

            holder.textDatum.setText("Gestern");
        }else if(itemNormal.getZeit() < (date.getTime() - (1000 * 60 * 60 * 24))){
            holder.textDatum.setText("Jetzt");
        }



        holder.bindView(listCommunication.get(position));
    }

    @Override
    public int getItemCount() {
        return listCommunication.size();
    }

    public class CommunicationViewHolder extends RecyclerView.ViewHolder {
        TextView zeit,
                nachricht,
                textDatum,
                name;
        de.hdodenhof.circleimageview.CircleImageView imageView;
        CardView cardView, cardDatum;
        LinearLayout linear, linear2, linearDatum;

        public CommunicationViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            zeit = itemView.findViewById(R.id.zeit);
            nachricht = itemView.findViewById(R.id.nachricht);
            imageView = itemView.findViewById(R.id.imageView);
            linear = itemView.findViewById(R.id.linear);
            linear2 = itemView.findViewById(R.id.linear2);
            cardView = itemView.findViewById(R.id.cardView);
            cardDatum = itemView.findViewById(R.id.cardDatum);
            linearDatum = itemView.findViewById(R.id.linearDatum);
            textDatum = itemView.findViewById(R.id.textDatum);

        }

        public void bindView(data_communication data_communication) {
            Locale locale = new Locale("de", "DE");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa", locale);

            name.setText(data_communication.getName());
            nachricht.setText(data_communication.getNachricht());
            zeit.setText(simpleDateFormat.format(data_communication.getZeit()));
            textDatum.setText(data_communication.getDatum());

            database.child("login").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot item : snapshot.getChildren()){
                        String srcImage = item.child("image").getValue(String.class);
                        Glide.with(context).load(srcImage).placeholder(R.drawable.profile1).into(imageView);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            if (data_communication.getKey().equals(preferences.getKeyData(context))){
                name.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                linear.setGravity(Gravity.CENTER|Gravity.END);
                linear2.setGravity(Gravity.CENTER|Gravity.END);
                cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
        }
    }

}
