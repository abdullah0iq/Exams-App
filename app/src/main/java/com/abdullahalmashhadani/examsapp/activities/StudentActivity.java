package com.abdullahalmashhadani.examsapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdullahalmashhadani.examsapp.adapters.ExamAdapter;
import com.abdullahalmashhadani.examsapp.MyDatabaseHelper;
import com.abdullahalmashhadani.examsapp.R;
import com.abdullahalmashhadani.examsapp.RecycleViewInterface;
import com.abdullahalmashhadani.examsapp.models.Exam;
import com.abdullahalmashhadani.examsapp.models.Question;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity implements RecycleViewInterface {


    MyDatabaseHelper db;
    ArrayList<Exam> exams;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student);

        loadData();


    }
    protected void onResume() {

        super.onResume();
        loadData();
    }

    private void loadData() {
        recyclerView = findViewById(R.id.rvExams_forStudent);


        db = new MyDatabaseHelper(StudentActivity.this);
        exams = db.get_exams();


        ExamAdapter examAdapter = new ExamAdapter(this, StudentActivity.this, exams);
        recyclerView.setAdapter(examAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onExamClick(int pos) {
        if (exams.get(pos).getIs_taken()==1){return;}
        Intent intent = new Intent(StudentActivity.this, ExamTakingActivity.class);
        intent.putExtra("pos", exams.get(pos).getId());
        startActivity(intent);
    }
}