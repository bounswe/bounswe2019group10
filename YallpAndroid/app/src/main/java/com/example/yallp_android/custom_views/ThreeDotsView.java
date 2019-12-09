package com.example.yallp_android.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yallp_android.R;


public class ThreeDotsView extends RelativeLayout {

    private ThreeDotsClickListener listener;

    public ThreeDotsView(Context context) {
        super(context);
        initView();
    }

    public ThreeDotsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ThreeDotsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        inflate(getContext(), R.layout.three_dots_menu,this);
        ImageView threeDot = findViewById(R.id.three_dot);
        final LinearLayout settings_menu = findViewById(R.id.settings_menu);
        threeDot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settings_menu.getVisibility()==VISIBLE){
                    settings_menu.setVisibility(GONE);
                }else{
                    settings_menu.setVisibility(VISIBLE);
                }
            }
        });

        TextView edit_profile = findViewById(R.id.edit_profile);
        TextView my_writings = findViewById(R.id.my_writings);
        TextView waiting_writings = findViewById(R.id.waiting_writings);
        TextView log_out = findViewById(R.id.log_out);

        edit_profile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(0);
            }
        });

        my_writings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(1);
            }
        });

        waiting_writings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(2);
            }
        });

        log_out.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(3);
            }
        });
    }

    public void setListener(ThreeDotsClickListener listener) {
        this.listener = listener;
    }

    public interface ThreeDotsClickListener {
        void itemClick(int item);
    }
}
