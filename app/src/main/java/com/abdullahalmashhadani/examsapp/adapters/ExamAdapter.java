package com.abdullahalmashhadani.examsapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.abdullahalmashhadani.examsapp.R;
import com.abdullahalmashhadani.examsapp.RecycleViewInterface;
import com.abdullahalmashhadani.examsapp.models.Exam;
import com.abdullahalmashhadani.examsapp.models.Question;

import java.util.ArrayList;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.MyViewHolder> implements RecycleViewInterface {
    private final RecycleViewInterface recycleViewInterface;
    Context context;
    ArrayList<Exam> exams;

    public ExamAdapter(RecycleViewInterface recycleViewInterface, Context context, ArrayList<Exam> exams) {
        this.recycleViewInterface = recycleViewInterface;
        this.context = context;
        this.exams = exams;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exam_widget, parent, false);


        return new ExamAdapter.MyViewHolder(view, recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.exam_title.setText(exams.get(position).getTitle());
        holder.exam_image.setImageResource(R.drawable.exam_image);
        Log.d("exam_is_taken", String.valueOf(exams.get(position).getIs_taken()));
        if (exams.get(position).getIs_taken() == 1) {
            holder.exam_state.setText("Taken");
            holder.exam_state.setTextColor(ContextCompat.getColor(context, R.color.taken));
            holder.exam_score.setText(grade_calculator(position));
        } else {
            holder.exam_state.setText("Not Taken");
            holder.exam_state.setTextColor(ContextCompat.getColor(context, R.color.not_taken));
            holder.exam_score.setText("100/No Grade");
        }


    }

    private String grade_calculator(int pos) {
        ArrayList<Question> questions;
        questions = exams.get(pos).getQuestions();
        int total_true_answers = 0;
        for (Question question : questions) {
            if (question.getAnswer() == question.getSolution()) {
                total_true_answers++;
            }
        }

        total_true_answers = total_true_answers * 10;

        return "100/" + total_true_answers;
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    @Override
    public void onExamClick(int pos) {

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView exam_image;
        TextView exam_title, exam_state, exam_score;

        public MyViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            exam_image = itemView.findViewById(R.id.exam_widget_exam_image);
            exam_title = itemView.findViewById(R.id.exam_widget_exam_title);
            exam_state = itemView.findViewById(R.id.exam_widget_exam_state);
            exam_score = itemView.findViewById(R.id.exam_widget_exam_score);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recycleViewInterface.onExamClick(pos);
                    }
                }
            });
        }
    }
}
