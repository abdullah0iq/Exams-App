package com.abdullahalmashhadani.examsapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.abdullahalmashhadani.examsapp.models.Exam;
import com.abdullahalmashhadani.examsapp.models.Question;

import java.lang.reflect.Array;
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

    public int add_exam(String exam_title, int exam_is_taken) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EXAMS_COLUMN_TITLE, exam_title);
        cv.put(EXAMS_COLUMN_IS_TAKEN, exam_is_taken);
        long result = db.insert(EXAMS_TABLE_NAME, null, cv);


        db = this.getReadableDatabase();
        ArrayList<Integer> id = new ArrayList<>();
        Cursor cursor = null;
        String query = "SELECT * FROM " + EXAMS_TABLE_NAME;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") Integer exam_id = cursor.getInt(cursor.getColumnIndex("_id"));
                id.add(exam_id);
            }

            cursor.close(); // Always close the cursor after use
        }


        return id.get(id.size() - 1);
    }

    public void add_questions(ArrayList<Question> questions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv;
        for (Question question : questions) {
            cv = new ContentValues();
            cv.put(QUESTIONS_COLUMN_TITLE, question.getTitle());
            cv.put(QUESTIONS_COLUMN_SOLUTION, question.getSolution());
            cv.put(QUESTIONS_COLUMN_ANSWER, 2);
            cv.put(QUESTIONS_COLUMN_EXAM_ID, question.getExam_id());
            long result = db.insert(QUESTIONS_TABLE_NAME, null, cv);
        }

    }

    public ArrayList<Exam> get_exams() {
        String query = "SELECT * FROM " + EXAMS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Exam> exams = new ArrayList<>();
        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);

        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(EXAMS_COLUMN_TITLE));
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(EXAMS_COLUMN_ID));
                @SuppressLint("Range") int is_taken = cursor.getInt(cursor.getColumnIndex(EXAMS_COLUMN_IS_TAKEN));

                exams.add(new Exam(id, title, get_questions(id),is_taken));
            }

            cursor.close(); // Always close the cursor after use
        }
        if (db != null) {
            db.close(); // Close database connection to prevent memory leaks
        }

        return exams;

    }

    public ArrayList<Question> get_questions(int exam_id) {
        String query = "SELECT * FROM " + QUESTIONS_TABLE_NAME + " WHERE exam_id = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{String.valueOf(exam_id)});
        }

        ArrayList<Question> questions = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(QUESTIONS_COLUMN_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(QUESTIONS_COLUMN_TITLE));
                @SuppressLint("Range") int solution = cursor.getInt(cursor.getColumnIndex(QUESTIONS_COLUMN_SOLUTION));
                @SuppressLint("Range") int answer = cursor.getInt(cursor.getColumnIndex(QUESTIONS_COLUMN_ANSWER));


                questions.add(new Question(title, solution, answer, exam_id, id));
            }
            cursor.close(); // Always close the cursor after use
        }
        if (db != null) {
            db.close(); // Close database connection to prevent memory leaks
        }

        return questions;
    }

    public void emptyAllTables() {
        SQLiteDatabase db = this.getWritableDatabase(); // Get the database in writable mode

        // Begin a transaction
        db.beginTransaction();
        try {
            // Empty each table
            db.delete(EXAMS_TABLE_NAME, null, null);
            db.delete(QUESTIONS_TABLE_NAME, null, null);

            // Mark the transaction as successful
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // Error in deleting tables
            Log.e("Database Operations", "Error when deleting data from tables", e);
        } finally {
            // End the transaction
            db.endTransaction();
            // Close the database connection
            db.close();
        }
    }

    public boolean updateExam(int examId, int isTaken) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EXAMS_COLUMN_IS_TAKEN, isTaken);

        // Constructing the selection criteria (where clause)
        String selection = EXAMS_COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(examId)};

        int updated = db.update(EXAMS_TABLE_NAME, cv, selection, selectionArgs);
        db.close();

        // Return true if at least one row was updated
        return updated > 0;
    }

    public boolean updateQuestionAnswer(int questionId, int newAnswer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QUESTIONS_COLUMN_ANSWER, newAnswer);

        // Constructing the selection criteria (where clause)
        String selection = QUESTIONS_COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(questionId)};

        int updated = db.update(QUESTIONS_TABLE_NAME, cv, selection, selectionArgs);
        db.close();

        // Return true if at least one row was updated
        return updated > 0;
    }


}
