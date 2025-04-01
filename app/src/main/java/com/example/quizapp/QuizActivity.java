package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView questionText, progressText;
    RadioGroup optionsGroup;
    RadioButton option1, option2, option3, option4;
    Button submitBtn;

    ArrayList<Question> questionList;
    int currentQuestionIndex = 0;
    int score = 0;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        progressBar = findViewById(R.id.progressBar);
        questionText = findViewById(R.id.questionText);
        progressText = findViewById(R.id.progressText);
        optionsGroup = findViewById(R.id.optionsGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        submitBtn = findViewById(R.id.submitBtn);

        username = getIntent().getStringExtra("USERNAME");

        // Sample questions
        questionList = new ArrayList<>();
        questionList.add(new Question("What is the capital of France?", "Paris", "Berlin", "London", "Madrid", 1));
        questionList.add(new Question("5 + 3 = ?", "6", "7", "8", "9", 3));
        questionList.add(new Question("Which planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Saturn", 2));

        loadQuestion();

        submitBtn.setOnClickListener(v -> {
            int selectedId = optionsGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selected = findViewById(selectedId);
            int answerIndex = optionsGroup.indexOfChild(selected) + 1;
            int correctAnswer = questionList.get(currentQuestionIndex).getCorrectAnswer();

            // Disable options to prevent changing answer
            for (int i = 0; i < optionsGroup.getChildCount(); i++) {
                optionsGroup.getChildAt(i).setEnabled(false);
            }

            if (answerIndex == correctAnswer) {
                selected.setBackgroundColor(Color.GREEN);
                score++;
            } else {
                selected.setBackgroundColor(Color.RED);
                RadioButton correctBtn = (RadioButton) optionsGroup.getChildAt(correctAnswer - 1);
                correctBtn.setBackgroundColor(Color.GREEN);
            }

            submitBtn.setText("Next");
            submitBtn.setOnClickListener(view -> nextQuestion());
        });
    }

    private void loadQuestion() {
        optionsGroup.clearCheck();
        for (int i = 0; i < optionsGroup.getChildCount(); i++) {
            optionsGroup.getChildAt(i).setEnabled(true);
            optionsGroup.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }

        Question q = questionList.get(currentQuestionIndex);
        questionText.setText(q.getQuestion());
        option1.setText(q.getOption1());
        option2.setText(q.getOption2());
        option3.setText(q.getOption3());
        option4.setText(q.getOption4());

        int progress = ((currentQuestionIndex + 1) * 100) / questionList.size();
        progressBar.setProgress(progress);
        progressText.setText((currentQuestionIndex + 1) + "/" + questionList.size());
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questionList.size()) {
            loadQuestion();
            submitBtn.setText("Submit");
            submitBtn.setOnClickListener(v -> {
                int selectedId = optionsGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selected = findViewById(selectedId);
                int answerIndex = optionsGroup.indexOfChild(selected) + 1;
                int correctAnswer = questionList.get(currentQuestionIndex).getCorrectAnswer();

                for (int i = 0; i < optionsGroup.getChildCount(); i++) {
                    optionsGroup.getChildAt(i).setEnabled(false);
                }

                if (answerIndex == correctAnswer) {
                    selected.setBackgroundColor(Color.GREEN);
                    score++;
                } else {
                    selected.setBackgroundColor(Color.RED);
                    RadioButton correctBtn = (RadioButton) optionsGroup.getChildAt(correctAnswer - 1);
                    correctBtn.setBackgroundColor(Color.GREEN);
                }

                submitBtn.setText("Next");
                submitBtn.setOnClickListener(view -> nextQuestion());
            });
        } else {
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("SCORE", score);
            intent.putExtra("TOTAL", questionList.size());
            intent.putExtra("USERNAME", username);
            startActivity(intent);
            finish();
        }
    }
}

