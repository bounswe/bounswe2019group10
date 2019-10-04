package com.example.yallp_android;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class GetStartedActivity extends AppCompatActivity {

    private ImageView btnBack;
    private Button signUpButton;
    private  EditText password;
    
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.get_started);

        btnBack = findViewById(R.id.leftArrow);
        signUpButton = findViewById(R.id.signUpButton);
        password = findViewById(R.id.password);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetStartedActivity.super.onBackPressed();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    final int x = (int) event.getX();
                    final int y = (int) event.getY();
                    final Rect bounds = password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds();
                    int right = v.getWidth() - v.getPaddingRight();
                    int left = right - bounds.width();
                    int top = (v.getHeight() / 2) - (bounds.height() / 2);
                    int bottom = (v.getHeight() / 2) + (bounds.height() / 2);
                    Rect rect = new Rect(left, top, right, bottom);
                    if(rect.contains(x, y)) {
                        if(password.getInputType() == InputType.TYPE_CLASS_TEXT){
                            password.setInputType(129);
                        }
                        else if(password.getInputType() == 129){
                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                        }
                        password.setSelection(password.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });

    }
}
