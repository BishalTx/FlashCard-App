package com.example.flashcardapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddCardActivity extends AppCompatActivity {
    TextView flashcardQuestion;
    TextView flashcardAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ImageView addIcon1 = findViewById(R.id.flashCard_cancel_button);
        addIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        ImageView addIcon2 = findViewById(R.id.flashCard_save_button);
        addIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                String string1 = ((EditText) findViewById(R.id.editTextField)).getText().toString();
                String string2 = ((EditText) findViewById(R.id.editTextField1)).getText().toString();
                data.putExtra("question_key",string1);
                data.putExtra("answer_key",string2);
                setResult(RESULT_OK,data);
                //startActivityForResult(data, 200);
                finish();




            }
        });



    }
}
