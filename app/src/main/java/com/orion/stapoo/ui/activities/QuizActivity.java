package com.orion.stapoo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.stapoo.R;
import com.orion.stapoo.models.QuestionObject;
import com.orion.stapoo.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView question;
    private TextView optionOne, optionTwo, optionThree, optionFour;
    private CardView cardViewOne, cardViewTwo, cardViewThree, cardViewFour;
    ProgressBar progressBar;
    TextView tvProgress;
    private List<QuestionObject> questionList;
    private Integer currPosition = 1;
    private Integer selectedOptionPos = 0;
    private Button btnSubmit;
    private Integer correctAnsNum = 0;
    private String subject;
    private String day;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        subject = getIntent().getStringExtra("subject");
        day = getIntent().getStringExtra("day");
        username = new PrefManager(this).getUsername();


        initView();
        fetchQuestions();

        cardViewOne.setOnClickListener(this);
        cardViewTwo.setOnClickListener(this);
        cardViewThree.setOnClickListener(this);
        cardViewFour.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);


    }

    private void initView() {
        question = findViewById(R.id.tv_question);
        optionOne = findViewById(R.id.option_one);
        optionTwo = findViewById(R.id.option_two);
        optionThree = findViewById(R.id.option_three);
        optionFour = findViewById(R.id.option_four);
        cardViewOne = findViewById(R.id.card_option_one);
        cardViewTwo = findViewById(R.id.card_option_two);
        cardViewThree = findViewById(R.id.card_option_three);
        cardViewFour = findViewById(R.id.card_option_four);
        progressBar = findViewById(R.id.progress);
        tvProgress = findViewById(R.id.tv_progress);
        questionList = new ArrayList<>();
        btnSubmit = findViewById(R.id.btn_submit);
    }

    private void fetchQuestions() {
        FirebaseDatabase.getInstance().getReference().child("subjects").child(subject).child(day).child("quiz").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    questionList.add(dataSnapshot.getValue(QuestionObject.class));
                }
                setQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setQuestion() {
        QuestionObject questionObject = questionList.get(currPosition - 1);

        progressBar.setProgress(currPosition);
        tvProgress.setText(currPosition + "/" + progressBar.getMax());
        defaultOptionsView();

        question.setText(questionObject.getQuestion().toString());
        optionOne.setText(questionObject.getOptionOne());
        optionTwo.setText(questionObject.getOptionTwo());
        optionThree.setText(questionObject.getOptionThree());
        optionFour.setText(questionObject.getOptionFour());

        btnSubmit.setText("SUBMIT");
    }

    private void defaultOptionsView() {
        List<TextView> options = new ArrayList<>();
        options.add(0, optionOne);
        options.add(1, optionTwo);
        options.add(2, optionThree);
        options.add(3, optionFour);

        List<CardView> cards = new ArrayList<>();
        cards.add(0, cardViewOne);
        cards.add(1, cardViewTwo);
        cards.add(2, cardViewThree);
        cards.add(3, cardViewFour);

        for (TextView option : options) {
            option.setTextColor(Color.parseColor("#7A8089"));
            option.setTypeface(Typeface.DEFAULT);
        }

        for (CardView card : cards) {
            card.setBackground(ContextCompat.getDrawable(this, R.drawable.card_border_white));
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_option_one:
                selectedOptionView(cardViewOne, optionOne, 1);
                break;
            case R.id.card_option_two:
                selectedOptionView(cardViewTwo, optionTwo, 2);
                break;
            case R.id.card_option_three:
                selectedOptionView(cardViewThree, optionThree, 3);
                break;
            case R.id.card_option_four:
                selectedOptionView(cardViewFour, optionFour, 4);
                break;
            case R.id.btn_submit:
                if (selectedOptionPos == 0) {
                    currPosition++;

                    if (currPosition <= questionList.size()) {
                        setQuestion();
                    } else {
                        Intent intent = new Intent(this, ResultActivity.class);
                        intent.putExtra("score", correctAnsNum.toString());
                        FirebaseDatabase.getInstance().getReference().child("subjects").child(subject).child(day).child("scoreList").child(username).setValue(correctAnsNum.toString());
                        startActivity(intent);
                        finish();
                    }
                } else {
                    QuestionObject questionObject = questionList.get(currPosition - 1);
                    if (!questionObject.getCorrectOption().equals(selectedOptionPos)) {
                        answerView(selectedOptionPos, R.drawable.wrong_option_bg);
                        final MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong_buzzer);
                        mp.start();
                    } else {
                        correctAnsNum++;
                        final MediaPlayer mp = MediaPlayer.create(this, R.raw.correct_buzzer);
                        mp.start();
                    }
                    answerView(questionObject.getCorrectOption(), R.drawable.correct_option_bg);

                    if (currPosition == questionList.size()) {
                        btnSubmit.setText("FINISH");
                    } else {
                        btnSubmit.setText("GO TO NEXT QUESTION");
                    }
                    selectedOptionPos = 0;
                }

        }
    }

    private void answerView(Integer answer, Integer drawableView) {
        switch (answer) {
            case 1:
                cardViewOne.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 2:
                cardViewTwo.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 3:
                cardViewThree.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 4:
                cardViewFour.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
        }
    }

    private void selectedOptionView(CardView cv, TextView tv, Integer selectedOptionNum) {
        defaultOptionsView();
        selectedOptionPos = selectedOptionNum;

        tv.setTextColor(Color.parseColor("#363A43"));
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        cv.setBackground(ContextCompat.getDrawable(this, R.drawable.card_border));


    }
}