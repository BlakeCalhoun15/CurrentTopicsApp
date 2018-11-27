package com.example.joseph.untitledgroceryapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Product> productList;

    //getting the context and product list with constructor
    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_products, null);
        view.setOnClickListener(mOnClickListener);
        return new ProductViewHolder(view);
    }

    @Override
    public void onClick(final View view) {
        //RecyclerView recyclerView;
        //recyclerView = findViewById(R.id.listMenu);
        int itemPosition = ListMenu.recyclerView.getChildLayoutPosition(view);
        String item = productList.get(itemPosition);
        Toast.makeText(mCtx, item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Product product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewListName.setText(product.getName());
        //holder.textViewListType.setText(product.getType());

        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }



//implements View.OnClickListener
    class ProductViewHolder extends RecyclerView.ViewHolder  {

        private String mItem;
        TextView textViewListName, textViewListType;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            //itemView.setOnClickListener(this);
            textViewListName = itemView.findViewById(R.id.textViewListName);
            //textViewListType = itemView.findViewById(R.id.textViewListType);
            //textViewType = itemView.findViewById(R.id.textViewType);
        }



//        @Override
//        public void onClick(View v) {
//            Log.d(TAG, "onClick" + getAdapterPosition() + " " + mItem);
//        }
    }



}