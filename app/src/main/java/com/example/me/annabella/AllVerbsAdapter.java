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

public class AllVerbsAdapter extends RecyclerView.Adapter<AllVerbsAdapter.ViewHolder> {
    private VerbSummaryArray mDataset;

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

    AllVerbsAdapter(VerbSummaryArray myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public AllVerbsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                          int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element_layout, parent, false);

        return new AllVerbsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AllVerbsAdapter.ViewHolder holder, int position) {
        String pair = mDataset.getByIndex(position)[1] + " / " + mDataset.getByIndex(position)[2];
        holder.mVerbPair.setText(pair);

        String meaning = mDataset.getByIndex(position)[3];
        holder.mMeaning.setText(meaning);

        final long verbID = Long.parseLong(mDataset.getByIndex(position)[0]);

        HashSet<Long> studyList = Data.getInstance().getStudyList();

        final boolean alreadySaved = studyList.contains(verbID);
        if (alreadySaved) {
            holder.mToggleAdd.setImageResource(R.drawable.ic_menu_close_clear_cancel);
            holder.mToggleAdd.setColorFilter(Color.rgb(255,0,0));
        } else {
            holder.mToggleAdd.setImageResource(R.drawable.ic_input_add);
            holder.mToggleAdd.setColorFilter(Color.rgb(51, 181, 229));
        }

        holder.mToggleAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashSet<Long> studyList = Data.getInstance().getStudyList();
                boolean alreadySaved = studyList.contains(verbID);
                if (alreadySaved) {
                    studyList.remove(verbID);
                    ((ImageButton) v).setImageResource(R.drawable.ic_input_add);
                    ((ImageButton) v).setColorFilter(Color.rgb(51, 181, 229));
                } else {
                    studyList.add(verbID);
                    ((ImageButton) v).setImageResource(R.drawable.ic_menu_close_clear_cancel);
                    ((ImageButton) v).setColorFilter(Color.rgb(255,0,0));
                }
            }
        });

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Show conjugation of " + id, Toast.LENGTH_SHORT).show();
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

