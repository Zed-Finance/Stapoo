package com.orion.stapoo.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.IInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.stapoo.R;
import com.orion.stapoo.models.QuestionObject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class QuizFragment extends Fragment implements View.OnClickListener {

    private TextView question;
    private TextView optionOne, optionTwo, optionThree, optionFour;
    private CardView cardViewOne, cardViewTwo, cardViewThree, cardViewFour;
    ProgressBar progressBar;
    TextView tvProgress;
    private List<QuestionObject> questionList;
    private Integer currPosition = 1;
    private Integer selectedOptionPos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        initView(view);
        fetchQuestions();

        setQuestion();

        cardViewOne.setOnClickListener(this);
        cardViewTwo.setOnClickListener(this);
        cardViewThree.setOnClickListener(this);
        cardViewFour.setOnClickListener(this);

        return view;
    }

    private void initView(View view) {
        question = view.findViewById(R.id.tv_question);
        optionOne = view.findViewById(R.id.option_one);
        optionTwo = view.findViewById(R.id.option_two);
        optionThree = view.findViewById(R.id.option_three);
        optionFour = view.findViewById(R.id.option_four);
        cardViewOne = view.findViewById(R.id.card_option_one);
        cardViewTwo = view.findViewById(R.id.card_option_two);
        cardViewThree = view.findViewById(R.id.card_option_three);
        cardViewFour = view.findViewById(R.id.card_option_four);
        progressBar = view.findViewById(R.id.progress_bar);
        tvProgress = view.findViewById(R.id.tv_progress);
        questionList = new ArrayList<>();
    }

    private void fetchQuestions() {
        FirebaseDatabase.getInstance().getReference().child("subjects").child("maths").child("quiz").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    questionList.add(dataSnapshot.getValue(QuestionObject.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setQuestion() {
        currPosition = 1;
        QuestionObject questionObject = questionList.get(currPosition - 1);

        progressBar.setProgress(currPosition);
        tvProgress.setText(currPosition + "/" + progressBar.getMax());
        defaultOptionsView();

        question.setText(questionObject.getQuestion().toString());
        optionOne.setText(questionObject.getOptionOne());
        optionTwo.setText(questionObject.getOptionTwo());
        optionThree.setText(questionObject.getOptionThree());
        optionFour.setText(questionObject.getOptionFour());
    }

    private void defaultOptionsView() {
        List<TextView> options = new ArrayList<>();
        options.add(0, optionOne);
        options.add(1, optionTwo);
        options.add(2, optionThree);
        options.add(3, optionFour);

        for (TextView option : options) {
            option.setTextColor(Color.parseColor("#7A8089"));
            option.setTypeface(Typeface.DEFAULT);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_option_one:
                selectedOptionView(cardViewOne,optionOne,1);
                break;
            case R.id.card_option_two:
                selectedOptionView(cardViewTwo,optionTwo,2);
                break;
            case R.id.card_option_three:
                selectedOptionView(cardViewThree,optionThree,3);
                break;
            case R.id.card_option_four:
                selectedOptionView(cardViewFour,optionFour,4);
                break;
        }
    }

    private void selectedOptionView(CardView cv,TextView tv, Integer selectedOptionNum) {
        defaultOptionsView();
        selectedOptionPos = selectedOptionNum;

        tv.setTextColor(Color.parseColor("#363A43"));
        tv.setTypeface(tv.getTypeface(),Typeface.BOLD);
        cv.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.card_border));

    }
}