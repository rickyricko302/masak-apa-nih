package com.hikki.masakapanih.activity;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.hikki.masakapanih.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Button button =  findViewById(R.id.mulai);
        button.setTransformationMethod(null);
        button.setOnClickListener(v->{
            startActivity(new Intent(this,MainActivity.class));
        });
    }
}