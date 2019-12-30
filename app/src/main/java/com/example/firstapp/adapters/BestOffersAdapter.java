package com.example.firstapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.firstapp.activities.OfferDetailsActivity;
import com.example.firstapp.activities.R;
import com.example.firstapp.models.BestItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BestOffersAdapter extends RecyclerView.Adapter<BestOffersAdapter.MyHolder> {

    List<BestItemModel> recyclerdata;
    private Context mContext;


    public BestOffersAdapter(Context context, List<BestItemModel> recyclerdata) {
        this.mContext = context;
        this.recyclerdata = recyclerdata;

    }

    @Override
    public MyHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.best_model_item, parent, false);
        final MyHolder myHolder = new MyHolder(view);


        return myHolder;

    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        BestItemModel dataModel = recyclerdata.get(position);
        if (dataModel!=null){
            holder.name.setText(dataModel.getTitle());
            holder.description.setText(dataModel.getCategory());
            holder.url.setText(dataModel.getUrl());
            holder.offerDetails.setText(dataModel.getOffer());

            Picasso.with(mContext)
                    .load(dataModel.getThumbnail())
                    .into(holder.thumbnail);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = v.findViewById(R.id.proName);
                    TextView textView1 = v.findViewById(R.id.bestOfferDetails);
                    TextView textView2 = v.findViewById(R.id.offerCategory);
                    ImageView imageView = v.findViewById(R.id.thumbnail);
                    TextView url = v.findViewById(R.id.urlHolder);


                    String offerUrl = url.getText().toString();
                    String bestCat = textView2.getText().toString();

                    String cname = textView.getText().toString();
                    String details = textView1.getText().toString();
                    Intent intent = new Intent(mContext, OfferDetailsActivity.class);


                    intent.putExtra("cName", cname);
                    intent.putExtra("bDetails", details);
                    intent.putExtra("cate", bestCat);
                    intent.putExtra("url", offerUrl);

                    imageView.buildDrawingCache();
                    Bitmap bitmap = imageView.getDrawingCache();

                    intent.putExtra("picture", bitmap);

                    mContext.startActivity(intent);
                }
            });

            if (position % 3 == 0) {

                holder.cashback.setVisibility(View.VISIBLE);
                holder.pick.setVisibility(View.VISIBLE);
                holder.varified.setVisibility(View.VISIBLE);
            } else {

                holder.cashback.setVisibility(View.GONE);
                holder.pick.setVisibility(View.GONE);
                holder.varified.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public int getItemCount() {
        try {
            return recyclerdata.size();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name, description, cashback, pick, varified, url, offerDetails;
        ImageView thumbnail;
        View mView;

        public MyHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = itemView.findViewById(R.id.proName);
            description = itemView.findViewById(R.id.offerCategory);
            url = itemView.findViewById(R.id.urlHolder);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            offerDetails = itemView.findViewById(R.id.bestOfferDetails);


            cashback = itemView.findViewById(R.id.cdOffer);
            pick = itemView.findViewById(R.id.editors);
            varified = itemView.findViewById(R.id.varify);


        }
    }

}