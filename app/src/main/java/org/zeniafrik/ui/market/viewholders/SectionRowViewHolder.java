package org.zeniafrik.ui.market.viewholders;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import org.zeniafrik.R.id;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectionRowViewHolder extends ViewHolder {
    @BindView(id.section_list_title)
    public TextView sectionTitle;

    @BindView(id.section_list_recycler)
    public RecyclerView sectionRecyclerView;

    public SectionRowViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}