package com.abdullahalmashhadani.examsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdullahalmashhadani.examsapp.R;
import com.abdullahalmashhadani.examsapp.RecycleViewInterface;
import com.abdullahalmashhadani.examsapp.models.Exam;

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
        View view = inflater.inflate(R.layout.exam_widget,parent,false);


        return new ExamAdapter.MyViewHolder(view,recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.exam_title.setText(exams.get(position).getTitle());
        holder.exam_image.setImageResource(R.drawable.exam_image);
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    @Override
    public void onExamClick(int pos) {

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
         ImageView exam_image;
         TextView exam_title,exam_state,exam_score;
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
                    if (pos !=RecyclerView.NO_POSITION){
                        recycleViewInterface.onExamClick(pos);
                    }
                }
            });
        }
    }
}
