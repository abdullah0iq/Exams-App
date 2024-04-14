package com.abdullahalmashhadani.examsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.abdullahalmashhadani.examsapp.models.Exam;
import com.abdullahalmashhadani.examsapp.models.Question;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Exams";
    private static final int DATABASE_VERSION = 1;

    private static final String EXAMS_TABLE_NAME = "Exams";
    private static final String EXAMS_COLUMN_ID = "_id";
    public static final String EXAMS_COLUMN_TITLE = "title";
    public static final String EXAMS_COLUMN_IS_TAKEN = "is_taken";

    private static final String QUESTIONS_TABLE_NAME = "Questions";
    private static final String QUESTIONS_COLUMN_ID = "_id";
    private static final String QUESTIONS_COLUMN_TITLE = "title";
    private static final String QUESTIONS_COLUMN_SOLUTION = "solution";
    private static final String QUESTIONS_COLUMN_ANSWER = "answer";
    private static final String QUESTIONS_COLUMN_EXAM_ID = "exam_id";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + EXAMS_TABLE_NAME +
                " (" + EXAMS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EXAMS_COLUMN_TITLE + " TEXT, " +
                EXAMS_COLUMN_IS_TAKEN + " INTEGER);";
        db.execSQL(query);

        query = "CREATE TABLE " + QUESTIONS_TABLE_NAME +
                " (" + QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QUESTIONS_COLUMN_TITLE + " TEXT, " +
                QUESTIONS_COLUMN_SOLUTION + " INTEGER, " +
                QUESTIONS_COLUMN_ANSWER + " INTEGER, " +
                QUESTIONS_COLUMN_EXAM_ID + " INTEGER, " +
                "FOREIGN KEY (" + QUESTIONS_COLUMN_EXAM_ID + ") REFERENCES " +
                EXAMS_TABLE_NAME + " (" + EXAMS_COLUMN_ID + "));";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void add_exam(Exam exam){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EXAMS_COLUMN_TITLE, exam.getTitle());
        cv.put(EXAMS_COLUMN_IS_TAKEN,exam.getIs_taken());
        long result = db.insert(EXAMS_TABLE_NAME, null,cv);
        add_questions(exam.getQuestions(),db,Exam.getCounter());
    }

    private void add_questions(ArrayList<Question> questions, SQLiteDatabase db, int exam_id) {
        ContentValues cv;
        for (Question question : questions) {
            cv = new ContentValues();
            cv.put(QUESTIONS_COLUMN_TITLE,question.getTitle());
            cv.put(QUESTIONS_COLUMN_SOLUTION,question.getSolution());
            cv.put(QUESTIONS_COLUMN_ANSWER,2);
            cv.put(QUESTIONS_COLUMN_EXAM_ID,exam_id);
            long result = db.insert(QUESTIONS_TABLE_NAME,null,cv);
        }

    }

    public Cursor get_exams(){
        String query = "SELECT * FROM " + EXAMS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query,null);

        }
        return cursor;

    }




}
