package com.abdullahalmashhadani.examsapp.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.abdullahalmashhadani.examsapp.models.Exam;
import com.abdullahalmashhadani.examsapp.MyDatabaseHelper;
import com.abdullahalmashhadani.examsapp.models.Question;
import com.abdullahalmashhadani.examsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NewExamActivity extends AppCompatActivity {
    Exam exam ;
    FloatingActionButton fab;
    MyDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_exam);
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("test_question_1",1,2,1));
        questions.add(new Question("test_question_2",1,2,1));
        questions.add(new Question("test_question_3",1,2,1));
        fab = findViewById(R.id.save_new_exam_floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exam = new Exam(Exam.getCounter(),"final test exam", questions);
                db = new MyDatabaseHelper(NewExamActivity.this);
                db.add_exam(exam);

            }
        });


    }
}