package com.abdullahalmashhadani.examsapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.abdullahalmashhadani.examsapp.models.Exam;
import com.abdullahalmashhadani.examsapp.MyDatabaseHelper;
import com.abdullahalmashhadani.examsapp.models.Question;
import com.abdullahalmashhadani.examsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewExamActivity extends AppCompatActivity {
    Exam exam;
    Button save_button;
    MyDatabaseHelper db;
    EditText question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_exam);

        save_button = findViewById(R.id.save_new_exam_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExam();

            }
        });


    }

    private void saveExam() {
        boolean valid_questions = true;

        EditText exam_title = findViewById(R.id.new_exam_title);
        String examTitleText = exam_title.getText().toString().trim();

        if (examTitleText.isEmpty()) {
            exam_title.setError("You must enter an exam title");
            return; // Stop execution if no exam title
        }

        int numberOfQuestions = 10;
        ArrayList<Question> questions = new ArrayList<>();
        Map<Integer, Map<String, String>> questions_map = new HashMap<>();

        for (int i = 1; i <= numberOfQuestions; i++) {
            int editTextId = getResources().getIdentifier("question_text_widget" + i, "id", getPackageName());
            int radioGroupId = getResources().getIdentifier("radioGroup" + i, "id", getPackageName());

            EditText questionText = findViewById(editTextId);
            RadioGroup radioGroup = findViewById(radioGroupId);

            String question_text = questionText.getText().toString().trim();
            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (question_text.isEmpty() || selectedId == -1) {
                if (question_text.isEmpty()) {
                    questionText.setError("You must enter a question");
                }
                if (selectedId == -1) {
                    Toast.makeText(this, "Please select an answer for question " + i, Toast.LENGTH_SHORT).show();
                }
                valid_questions = false;
                continue;
            }

            RadioButton selectedRadioButton = findViewById(selectedId);
            int solution = selectedRadioButton.getText().toString().equals("True") ? 1 : 0;
            Map<String, String> details = new HashMap<>();
            details.put("title", question_text);
            details.put("solution", String.valueOf(solution));
            questions_map.put(i, details);
        }

        if (valid_questions) {
            MyDatabaseHelper db = new MyDatabaseHelper(this);
            int exam_id = db.add_exam(examTitleText, 0);

            for (int i = 1; i <= 10; i++) {
                Map<String, String> questionDetails = questions_map.get(i);
                if (questionDetails != null) {  // Check if question data exists
                    String title = questionDetails.get("title");
                    int solution = Integer.parseInt(questionDetails.get("solution"));  // Correct parsing
                    questions.add(new Question(title, solution, 2, exam_id,0));
                }
            }
            db.add_questions(questions);
            db.close();finish();
        }

    }


//    private void saveExam() {
//        boolean valid_questions = true;
//
//        EditText exam_title = findViewById(R.id.new_exam_title);
//        if (exam_title.getText().toString().trim().isEmpty()) {
//            exam_title.setError("You must enter a question");
//            return;
//        }
//
//        int numberOfQuestions = 10;
//        ArrayList<Question> questions = new ArrayList<>();
//
//        Map<Integer, Map<String, String>> questions_map = new HashMap<>();
//
//        for (int i = 1; i <= numberOfQuestions; i++) {
//
//
//            int editText_view_id = getResources().getIdentifier("question_text_widget" + i, "id", getPackageName());
//            int radioGroupId_view_id = getResources().getIdentifier("radioGroup" + i, "id", getPackageName());
//
//            EditText questionText = findViewById(editText_view_id);
//            RadioGroup radioGroup = findViewById(radioGroupId_view_id);
//
//            String question_text = questionText.getText().toString();
//
//            int selectedId = radioGroup.getCheckedRadioButtonId();
//            RadioButton selectedRadioButton = findViewById(selectedId);
//
//
//            if (question_text.trim().isEmpty()) {
//                questionText.setError("You must enter a question");
//                valid_questions = false;
//                return;
//            }
//            if (selectedId == -1) {
//
//                Toast.makeText(this, "Please select an answer for question " + i, Toast.LENGTH_SHORT).show();
//                valid_questions = false;
//                return;
//            }
//            if (valid_questions) {
//
//                int solution = selectedRadioButton.getText().toString().equals("True") ? 1 : 0;
//                Map<String, String> details = new HashMap<>();
//                details.put("title", question_text);
//                details.put("solution", String.valueOf(solution));
//                questions_map.put(i, details);
//            }
//
//        }
//
//
//
//            String exam_title_text = exam_title.getText().toString();
//
//            MyDatabaseHelper db = new MyDatabaseHelper(this);
//            int exam_id = db.add_exam(exam_title_text, 0);
//
//            for (int i = 1; i <= 10; i++) {
//                String title = questions_map.get(i).get("title");
//                int solution = Integer.getInteger(questions_map.get(i).get("solution"));
//
//                questions.add(new Question(title, solution, 2, exam_id));
////                Question(String title, int solution, int answer, int exam_id)
//            }
//        Log.d("Exma_id_log", String.valueOf(exam_id));
//            db.add_questions(questions);
//            db.close();
//
//
//
//    }
}