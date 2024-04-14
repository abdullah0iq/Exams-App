package com.abdullahalmashhadani.examsapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdullahalmashhadani.examsapp.models.Exam;
import com.abdullahalmashhadani.examsapp.adapters.ExamAdapter;
import com.abdullahalmashhadani.examsapp.MyDatabaseHelper;
import com.abdullahalmashhadani.examsapp.models.Question;
import com.abdullahalmashhadani.examsapp.R;
import com.abdullahalmashhadani.examsapp.RecycleViewInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity implements RecycleViewInterface {

    FloatingActionButton fab;
    MyDatabaseHelper db;
    ArrayList<Exam> exams;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher);

        fab = findViewById(R.id.new_exam_floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newExamActivityIntent = new Intent(TeacherActivity.this, NewExamActivity.class);
                startActivity(newExamActivityIntent);

            }
        });

        loadData();


    }

    protected void onResume() {

        super.onResume();
        loadData();
    }

    private void loadData() {
        recyclerView = findViewById(R.id.rvExams);
        db = new MyDatabaseHelper(TeacherActivity.this);
        exams = new ArrayList<>();


        exams = db.get_exams();


        ExamAdapter examAdapter = new ExamAdapter(this, TeacherActivity.this, exams);
        recyclerView.setAdapter(examAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onExamClick(int pos) {

    }
}