package com.tql.wll.recycleraviewtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tql.wll.recycleraviewtest.Fruit;

import java.util.List;

/**
 * Created by Administrator on 2018/3/10.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> mFruitList;

    static public class ViewHolder extends RecyclerView.ViewHolder{
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder(View v){
            super(v);
            fruitView = v;
            fruitImage = (ImageView)v.findViewById(R.id.fruit_image);
            fruitName = (TextView)v.findViewById(R.id.fruit_name);
        }
    }

    public FruitAdapter(List<Fruit> fruits){
        mFruitList = fruits;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(pos);
                Toast.makeText(v.getContext(), "You clicked view: " + fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(pos);
                Toast.makeText(v.getContext(), "You clicked image: " + fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Fruit f = mFruitList.get(position);
        holder.fruitImage.setImageResource(f.getImageId());
        holder.fruitName.setText(f.getName());
    }

    @Override
    public int getItemCount(){
        return mFruitList.size();
    }
}
