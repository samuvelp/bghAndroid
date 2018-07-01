package com.gospel.bethany.bgh.activities.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.model.MainMenu;

import java.util.ArrayList;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.MyViewHolder> {
    ArrayList<MainMenu> mMenuList;
    Context mContext;
    private OnItemClickListner mListener;

    public MainMenuAdapter(ArrayList<MainMenu> mMenuList, Context context) {
        this.mMenuList = mMenuList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_menu_cardview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mMenuName.setText(mMenuList.get(position).getName());
        holder.mMenuLable.setImageResource(mMenuList.get(position).getImageId());
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mMenuLable;
        TextView mMenuName;
        CardView mMenuCardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mMenuLable = itemView.findViewById(R.id.menuCardImage);
            mMenuName = itemView.findViewById(R.id.menuCardText);
            mMenuCardView = itemView.findViewById(R.id.menuCardView);
            mMenuCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(mMenuName.getText().toString());
        }
    }

    public void setClickListener(OnItemClickListner mListener) {
        this.mListener = mListener;
    }

    public interface OnItemClickListner {
        void onItemClick(String menuName);
    }
}
