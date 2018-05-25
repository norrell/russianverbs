package com.example.me.annabella;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;

public class StudyListAdapter extends RecyclerView.Adapter<StudyListAdapter.ViewHolder> {
    private List<Long> mDataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        TextView mVerbPair;
        TextView mMeaning;
        ImageButton mToggleAdd;

        ViewHolder(LinearLayout v) {
            super(v);
            mLinearLayout = v;
            mVerbPair = v.findViewById(R.id.textview_verbpair_listelem);
            mMeaning = v.findViewById(R.id.textview_meaning_listelem);
            mToggleAdd = v.findViewById(R.id.imagebutton_toggleadd);
        }
    }

    StudyListAdapter(List<Long> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public StudyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                         int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element_layout, parent, false);

        return new StudyListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyListAdapter.ViewHolder holder, final int position) {
        holder.mToggleAdd.setImageResource(R.drawable.ic_menu_close_clear_cancel);
        holder.mToggleAdd.setColorFilter(Color.rgb(255,0,0));

        final long verbID = mDataset.get(position);
        String[] fields = Data.getInstance().getAllVerbs().getByID((int) verbID);
        String pair = fields[1] + " / " + fields[2];
        holder.mVerbPair.setText(pair);

        String meaning = fields[3];
        holder.mMeaning.setText(meaning);

        final ViewHolder viewHolderFinal = holder;

        viewHolderFinal.mToggleAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashSet<Long> studyList = Data.getInstance().getStudyList();
                studyList.remove(verbID);
                mDataset.remove(verbID);
                int position = viewHolderFinal.getAdapterPosition();
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataset.size());
            }
        });

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ConjugationActivity.class);
                intent.putExtra("VERB_ID", String.valueOf(verbID));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
