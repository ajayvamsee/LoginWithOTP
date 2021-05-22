package com.example.firstassign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;



import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private Context mcontext;
    private  ArrayList<ExampleItem> mExampleItem;

    public ExampleAdapter(Context context,ArrayList<ExampleItem> exampleItem) {
        this.mcontext = context;
        this.mExampleItem=exampleItem;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(mcontext).inflate(R.layout.example_item,parent,false);

        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        ExampleItem currentItem=mExampleItem.get(position);
        String imageUrl=currentItem.getmImageUrl();
        String authorName=currentItem.getmAuthorName();

        holder.mTextView.setText(authorName);

      //  Picasso.with(mcontext).load(imageUrl).resize(50,50).centerCrop().placeholder(R.id.imageView).into(holder.mImageView);

        Picasso.with(mcontext).load(imageUrl).fit().centerInside().into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mExampleItem.size();
    }


    public  class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView= itemView.findViewById(R.id.imageView);
           mTextView=itemView.findViewById(R.id.tvAuthorName);


        }
    }
}
