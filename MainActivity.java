package com.example.flashcardapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.DirectAction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView flashcardQuestion;
    TextView flashcardAnswer;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int cardIndex = 0;
 //allFlashcards.deleteAll();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcardQuestion = findViewById(R.id.flashcard_question_textview);
        flashcardAnswer = findViewById(R.id.flashcard_answer_textview);
        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cx = flashcardAnswer.getWidth() / 2;
                int cy = flashcardQuestion.getHeight() / 2;

// get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(flashcardAnswer, cx, cy, 0f, finalRadius);

// hide the question and show the answer to prepare for playing the animation!

                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();

            }
        });


        flashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQuestion.setVisibility(View.VISIBLE);
                flashcardAnswer.setVisibility(View.INVISIBLE);
            }
        });
        ImageView addIcon2 = findViewById(R.id.flashCard_next_button);
        addIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardIndex++;
                if( cardIndex >= allFlashcards.size()) {
                    Snackbar.make(v,"you have reached the end of the flashcard",Snackbar.LENGTH_SHORT).show();
                    cardIndex = 0;
                }
                //Flashcard currentCard = allFlashcards.get(cardIndex);
             //   flashcardQuestion.setText((currentCard.getQuestion()));
               // flashcardAnswer.setText(currentCard.getAnswer());

                final Animation leftAni = AnimationUtils.loadAnimation(v.getContext(),R.anim.animationlleft);
                final Animation rightAni = AnimationUtils.loadAnimation(v.getContext(),R.anim.animationright);

                leftAni.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        flashcardQuestion.startAnimation(rightAni);

                        Flashcard currentCard = allFlashcards.get(cardIndex);
                        flashcardQuestion.setText(currentCard.getQuestion());
                        flashcardAnswer.setText(currentCard.getAnswer());
                        flashcardQuestion.setVisibility(View.VISIBLE);
                        flashcardAnswer.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });
                flashcardQuestion.startAnimation(leftAni);
            }
        });





        ImageView addIcon = findViewById(R.id.flashCard_add_button);
        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
               //MainActivity.this.startActivity(intent);
               startActivityForResult(intent,100);
                overridePendingTransition(R.anim.animationright, R.anim.animationlleft);
            }
        });
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
      //  flashcardDatabase.deleteAll();
        allFlashcards = flashcardDatabase.getAllCards();
        if (allFlashcards != null && allFlashcards.size() > 0) {
            Flashcard firstcard = allFlashcards.get(0);
            flashcardQuestion.setText(firstcard.getQuestion());
            flashcardAnswer.setText(firstcard.getAnswer());
        }
     ;

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        if (requestCode == 100){
            if (data  != null){
                String ques = data.getExtras().getString("question_key");
                String ans = data.getExtras().getString("answer_key");
                flashcardAnswer.setText(ans);
                flashcardQuestion.setText(ques);
                flashcardDatabase.insertCard(new Flashcard(ques, ans));
                allFlashcards = flashcardDatabase.getAllCards();
                //Flashcard flashcard = new Flashcard(ques,ans);
                //flashcardDatabase.insertCard(flashcard);
                //allFlashcards = flashcardDatabase.getAllCards();
            }
        }
    }

}