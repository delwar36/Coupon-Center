package com.example.firstapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firstapp.activities.R;
import com.example.firstapp.fragments.ApprovedFragment;
import com.example.firstapp.models.UsersModel;

import java.util.ArrayList;

public class ApprovedAdapter extends RecyclerView.Adapter<ApprovedAdapter.ViewHolder>{
    private ApprovedFragment mContext;
    private ArrayList<UsersModel> mlist;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView userNameAp,buyProduct, codeUsed, approvetm;
        ImageView userImgAp;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            codeUsed = view.findViewById(R.id.codeUsed);
            userNameAp = view.findViewById(R.id.usersNameAp);
            buyProduct = view.findViewById(R.id.boughtProduct);
            approvetm = view.findViewById(R.id.approvedTime);
            userImgAp = view.findViewById(R.id.usersImgAp);

        }

    }



    public ApprovedAdapter(ApprovedFragment context, ArrayList<UsersModel> list) {
        mContext = context;
        mlist = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approved_model_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ImageView image1;
        TextView userNameAp,buyProduct, codeUsed, approvetm;

        UsersModel itemReciever;

        itemReciever = mlist.get(position);

        image1 = holder.userImgAp;
        userNameAp = holder.userNameAp;
        buyProduct = holder.buyProduct;
        codeUsed = holder.codeUsed;
        approvetm = holder.approvetm;



        image1.setImageResource(itemReciever.getImageView1());


        userNameAp.setText(itemReciever.getTextView1());
        buyProduct.setText(itemReciever.getTextView2());
        codeUsed.setText(itemReciever.getApproved());
        approvetm.setText(itemReciever.getPending());
    }
    public UsersModel getValueAt(int position) {
        return mlist.get(position);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}