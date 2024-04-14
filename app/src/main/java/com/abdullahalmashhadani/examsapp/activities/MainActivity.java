package com.abdullahalmashhadani.examsapp.activities;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abdullahalmashhadani.examsapp.MyDatabaseHelper;
import com.abdullahalmashhadani.examsapp.R;

public class MainActivity extends AppCompatActivity {
    CardView studentCard;
    CardView teacherCard;
    Button empty_db_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        studentCard = findViewById(R.id.card_view_student);
        teacherCard = findViewById(R.id.card_view_teacher);

        studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent studentActivityIntent = new Intent(MainActivity.this, StudentActivity.class);
                startActivity(studentActivityIntent);
            }
        });


        teacherCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent teacherActivityIntent = new Intent(MainActivity.this, TeacherActivity.class);
                startActivity(teacherActivityIntent);
            }
        });
        MyDatabaseHelper db = new MyDatabaseHelper(MainActivity.this);
        empty_db_button = findViewById(R.id.delete_all_exams_button);
        empty_db_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.emptyAllTables();


            }
        });
    }
}