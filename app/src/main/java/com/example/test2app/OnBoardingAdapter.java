package com.example.test2app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder> {

    private List<OnBoardingItem> onBoardingItems;

    public OnBoardingAdapter(List<OnBoardingItem> onBoardingItems) {
        this.onBoardingItems = onBoardingItems;
    }

    @NonNull
    @Override
    public OnBoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnBoardingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull OnBoardingViewHolder holder, int position) {
        holder.setImageOnBoardingData(onBoardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onBoardingItems.size();
    }

    class OnBoardingViewHolder extends RecyclerView.ViewHolder {

        private TextView textDescription;
        private TextView textTitle;
        private ImageView imageOnBoarding;

         OnBoardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textDescription = itemView.findViewById(R.id.textDescription);
            textTitle = itemView.findViewById(R.id.textTitle);
            imageOnBoarding = itemView.findViewById(R.id.imageOnBoarding);
        }

        void setImageOnBoardingData(OnBoardingItem onBoardingItem) {
            textDescription.setText(onBoardingItem.getDescription());
            textTitle.setText(onBoardingItem.getTitle());
            imageOnBoarding.setImageResource(onBoardingItem.getImage());
        }
    }
}
