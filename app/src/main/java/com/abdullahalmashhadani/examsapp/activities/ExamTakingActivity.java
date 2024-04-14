package com.abdullahalmashhadani.examsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abdullahalmashhadani.examsapp.MyDatabaseHelper;
import com.abdullahalmashhadani.examsapp.R;
import com.abdullahalmashhadani.examsapp.models.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExamTakingActivity extends AppCompatActivity {
    MyDatabaseHelper db;
    ArrayList<Question> questions;
    int exam_id;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exam_taking);

        Intent intent = getIntent();
        db = new MyDatabaseHelper(this);
         exam_id = intent.getIntExtra("pos", -1); // Default value is -1 if "pos" isn't found
        questions = db.get_questions(exam_id);
        for (Question question:questions
             ) {
            Log.d("question_id" , String.valueOf(question.getId()));
        }
        loadQuestionsToScreen(questions);

        submitButton = findViewById(R.id.submit_exam_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitExam(questions);
            }
        });


    }

    private void submitExam(ArrayList<Question> questions) {
        boolean valid_answers = true;

        int numberOfQuestions = 10;
        int[] solutions = new int[10];

        for (int i = 1; i <= numberOfQuestions; i++) {
            int radioGroupId = getResources().getIdentifier("radioGroup" + i + "_exam_taking", "id", getPackageName());

            RadioGroup radioGroup = findViewById(radioGroupId);


            int selectedAnswerId = radioGroup.getCheckedRadioButtonId();

            if (selectedAnswerId == -1) {


                Toast.makeText(this, "Please select an answer for question " + i, Toast.LENGTH_SHORT).show();

                valid_answers = false;
                continue;
            }

            RadioButton selectedRadioButton = findViewById(selectedAnswerId);
            int solution = selectedRadioButton.getText().toString().equals("True") ? 1 : 0;
            solutions[i - 1] = solution;

        }

        if (valid_answers) {
            MyDatabaseHelper db = new MyDatabaseHelper(this);

            db.updateExam(exam_id,1);

            for (int i = 0; i < 10; i++) {
                Log.d("current_solution"+i, String.valueOf(solutions[i]));
                Log.d("current_question_is", String.valueOf(questions.get(i).getId()));
                 db.updateQuestionAnswer(questions.get(i).getId(),solutions[i]);
            }

            db.close();finish();
        }


    }

    private void loadQuestionsToScreen(ArrayList<Question> questions) {
        for (int i = 1; i <= questions.size(); i++) {
            int textViewId = getResources().getIdentifier("question_text_submit" + i, "id", getPackageName());
            TextView questionText = findViewById(textViewId);
            questionText.setText(questions.get(i - 1).getTitle());

        }
    }
}