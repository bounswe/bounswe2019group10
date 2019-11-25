package com.example.yallp_android.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yallp_android.R;
import com.example.yallp_android.models.WritingListElement;

import java.util.ArrayList;

public class WritingListAdapter extends RecyclerView.Adapter<WritingListAdapter.MyViewHolder> {

    public interface WritingListAdapterClickListener {

        void writingListAdapterClick(String writingId);
    }

    private Context context;
    private ArrayList<WritingListElement> writings;
    private LayoutInflater inflater;
    private WritingListAdapterClickListener writingListAdapterClickListener;

    public WritingListAdapter(Context context, ArrayList<WritingListElement> writings, WritingListAdapterClickListener writingListAdapterClickListener) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.writings = writings;
        this.writingListAdapterClickListener = writingListAdapterClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.writing_language_list, parent, false);
        return new MyViewHolder(context, view, writingListAdapterClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(writings.get(position), position);
    }


    @Override
    public int getItemCount() {
        return writings.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context context;
        TextView topicName;
        TextView isSolvedText;
        TextView scoreText;
        int position;
        WritingListElement writingListElement;
        WritingListAdapterClickListener writingListAdapterClickListener;

        MyViewHolder(Context context, View view, WritingListAdapterClickListener writingListAdapterClickListener) {
            super(view);
            this.context = context;
            topicName = view.findViewById(R.id.topicName);
            isSolvedText = view.findViewById(R.id.isSolvedText);
            scoreText = view.findViewById(R.id.scoreWriting);
            this.writingListAdapterClickListener = writingListAdapterClickListener;
            view.setOnClickListener(this);
        }

        void setData(WritingListElement writingListElement, int position) {
            this.writingListElement = writingListElement;
            this.position = position;
            this.topicName.setText(writingListElement.getWritingDTO().getWritingName());
            if (!writingListElement.isSolved()) {
                this.isSolvedText.setText(context.getResources().getString(R.string.not_solved));
                this.isSolvedText.setTextColor(Color.BLACK);
                this.isSolvedText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                this.scoreText.setText("-");
            } else if (!writingListElement.getWritingResultDTO().isScored()) {
                this.isSolvedText.setText(context.getResources().getString(R.string.pending));
                this.isSolvedText.setCompoundDrawablesWithIntrinsicBounds(null, null,  null, null);
                this.scoreText.setText("-");
                this.isSolvedText.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                this.isSolvedText.setText(context.getResources().getString(R.string.solved));
                this.isSolvedText.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.ic_check), null);
                this.isSolvedText.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                this.scoreText.setText("" + writingListElement.getWritingResultDTO().getScore() + "/10");

            }
        }

        @Override
        public void onClick(View view) {
             writingListAdapterClickListener.writingListAdapterClick (writingListElement.getWritingDTO().getId() + "");
        }
    }
}
