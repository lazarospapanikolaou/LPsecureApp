package com.example.test2app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class OnboardActivity extends AppCompatActivity {

    private OnBoardingAdapter onBoardingAdapter;
    private LinearLayout layoutOnBoardIndicators;
    private MaterialButton Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        layoutOnBoardIndicators = findViewById(R.id.layoutOnBoardIndicators);
        Button = findViewById(R.id.Button);

        setupOnBoardingAdapter();

        ViewPager2 onBoardingViewPager = findViewById(R.id.onboardViewPager);
        onBoardingViewPager.setAdapter(onBoardingAdapter);

        setupOnBoardIndicators();
        setCurrentOnBoardIndicator(0);

        onBoardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnBoardIndicator(position);
            }
        });

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBoardingViewPager.getCurrentItem() +1 < onBoardingAdapter.getItemCount()) {
                    onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem());

                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));


                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();

                }
            }
        });

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float a = 1 - Math.abs(position);
                page.setScaleY(0.85f + a * 0.15f);
            }
        });

        onBoardingViewPager.setPageTransformer(transformer);
    }

    private void setupOnBoardingAdapter() {

        List<OnBoardingItem> onBoardingItems = new ArrayList<>();

        OnBoardingItem ItemPayOnline = new OnBoardingItem();
        ItemPayOnline.setTitle("Register");
        ItemPayOnline.setDescription("Join our Company and keep your account safe.");
        ItemPayOnline.setImage(R.drawable.register_image);

        OnBoardingItem ItemOnWay = new OnBoardingItem();
        ItemOnWay.setTitle("Login");
        ItemOnWay.setDescription("Take a look at your business profile.");
        ItemOnWay.setImage(R.drawable.login_image);

        onBoardingItems.add(ItemPayOnline);
        onBoardingItems.add(ItemOnWay);

        onBoardingAdapter = new OnBoardingAdapter(onBoardingItems);

    }

    private void setupOnBoardIndicators() {
        ImageView[] indicators = new ImageView[onBoardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(18,0,0,0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnBoardIndicators.addView(indicators[i]);
        }

    }

    private void setCurrentOnBoardIndicator(int index) {
        int childCount = layoutOnBoardIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnBoardIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }

        if (index == onBoardingAdapter.getItemCount() -1) {
            Button.setText("Login");
        } else {
            Button.setText("Register");
        }
    }

}