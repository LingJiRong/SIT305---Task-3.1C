// ResultActivity.java
package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView resultText, userText;
    Button restartBtn, finishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultText = findViewById(R.id.resultText);
        userText = findViewById(R.id.userText);
        restartBtn = findViewById(R.id.restartBtn);
        finishBtn = findViewById(R.id.finishBtn);

        int score = getIntent().getIntExtra("SCORE", 0);
        int total = getIntent().getIntExtra("TOTAL", 0);
        String username = getIntent().getStringExtra("USERNAME");

        resultText.setText("Your Score: " + score + " / " + total);
        userText.setText("Well done, " + username + "!");

        restartBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
            finish();
        });

        finishBtn.setOnClickListener(v -> finishAffinity()); // Close app
    }
}
