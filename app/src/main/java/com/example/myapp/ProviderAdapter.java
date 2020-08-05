package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ProviderViewHolder> {

    private Context mCtx;
    private List<Provider> ProviderList;
    private String service;

    public ProviderAdapter(Context mCtx, List<Provider> ProviderList,String service) {
        this.mCtx = mCtx;
        this.ProviderList = ProviderList;
        this.service=service;
    }

    @NonNull
    @Override
    public ProviderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_provider, parent, false);
        return new ProviderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderViewHolder holder, int position) {
        Provider Provider = ProviderList.get(position);
        holder.textViewName.setText("Name : " + Provider.name);
        holder.textViewAge.setText("Age : " + Provider.age);
        holder.textViewNumber.setText("PhoneNumber : " + Provider.phoneNumber);
        holder.textViewRating.setText("Rating : ");
        holder.textViewDistance.setText("Price : "+Provider.price);
        //holder.rating.setRating(Provider.rating);
        holder.rating.setRating(Provider.rating);

    }

    @Override
    public int getItemCount() {
        return ProviderList.size();
    }


    class ProviderViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        TextView textViewName, textViewAge, textViewNumber,textViewRating,textViewDistance;
        RatingBar rating;
        public ProviderViewHolder(@NonNull final View itemView) {
            super(itemView);
            context=itemView.getContext();
            textViewName = itemView.findViewById(R.id.name);
            textViewAge = itemView.findViewById(R.id.age);
            textViewNumber = itemView.findViewById(R.id.number);
            textViewRating = itemView.findViewById(R.id.rating_txt);
            rating = itemView.findViewById(R.id.ratingBar);
            textViewDistance=itemView.findViewById(R.id.tvdistance);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    Intent intent=new Intent(itemView.getContext(),Placeorder.class);
                    intent.putExtra("Provider",ProviderList.get(pos));
                    intent.putExtra("service",service);
                    context.startActivity(intent);
                }
            });

        }
    }
}
