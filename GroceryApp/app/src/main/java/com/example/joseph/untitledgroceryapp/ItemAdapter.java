package com.example.joseph.untitledgroceryapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context mCtx;
    private List<Item> itemList;//Store an item for the list

    public ItemAdapter(Context mCtx, List<Item> itemList) {
        this.mCtx = mCtx;
        this.itemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_item, null);
        return new ItemAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.textViewItemName.setText(item.getItem_name());
    }

    @Override
    public int getItemCount() {return itemList.size();}

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textViewItemName;

        public ItemViewHolder(View itemView) {
            super(itemView);

            textViewItemName = itemView.findViewById(R.id.textViewItemName);
        }
    }//end of ItemViewHolder class
}//end of ItemAdapter class
