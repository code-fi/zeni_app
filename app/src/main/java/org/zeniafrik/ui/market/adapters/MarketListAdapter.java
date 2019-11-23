package org.zeniafrik.ui.market.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zeniafrik.R;
import org.zeniafrik.R.layout;
import org.zeniafrik.helper.ItemOffsetDecoration;
import org.zeniafrik.models.MarketSectionModel;
import org.zeniafrik.ui.market.viewholders.SectionRowViewHolder;
import org.zeniafrik.ui.market.viewholders.SectionRowViewPagerHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Objects.requireNonNull;
import static org.zeniafrik.models.MarketSectionModel.V_LAYOUT;

/**
 * Created by BraDev ${LOCALE} on 5/8/2018.
 */
public class MarketListAdapter extends Adapter<ViewHolder> {


    private final ArrayList<MarketSectionModel> sections = new ArrayList<>();
    private Timer timer = new Timer();
    private Handler handler = new Handler();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context activity = parent.getContext();
        LayoutInflater sectionLayoutInflater = LayoutInflater.from(activity);
        MarketSectionModel section = sections.get(viewType);
        if (section.type == 1) {
            View sectionView = sectionLayoutInflater.inflate(layout.section_list_component, parent, false);
            SectionRowViewHolder sectionRowViewHolder = new SectionRowViewHolder(sectionView);
            String sectionLayout = section.layout;
            int viewLayout = sectionLayout.equals(V_LAYOUT) ? 1 : 2,
                    spanCount = activity.getResources().getInteger(R.integer.deviceSize) == 720 ? 3 : 2;
            if (section.layout.equals(V_LAYOUT))
                sectionRowViewHolder.sectionRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
            else {
                sectionRowViewHolder.sectionRecyclerView.setLayoutManager(new GridLayoutManager(activity, spanCount));
                sectionRowViewHolder.sectionRecyclerView.addItemDecoration(new ItemOffsetDecoration(activity, R.dimen.itemoffset));
            }
            sectionRowViewHolder.sectionRecyclerView.setHasFixedSize(true);
            sectionRowViewHolder.sectionRecyclerView.setAdapter(new ProductViewAdapter(viewLayout));
            return sectionRowViewHolder;

        } else {
            View sectionView = sectionLayoutInflater.inflate(layout.banner_slider_component, parent, false);
            SectionRowViewPagerHolder pagerHolder = new SectionRowViewPagerHolder(sectionView);
            pagerHolder.imageViewPager.setAdapter(new SliderAdapter());
            pagerHolder.indicator.setupWithViewPager(pagerHolder.imageViewPager);

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    int currentItem = pagerHolder.imageViewPager.getCurrentItem(),
                            bannerMaxIndex = ((List) section.getSectionList()).size() - 1,
                            pos = currentItem + 1;

                    handler.post(() -> {
                        if (currentItem < bannerMaxIndex)
                            pagerHolder.imageViewPager.setCurrentItem(pos);
                        else pagerHolder.imageViewPager.setCurrentItem(0);
                    });
                }
            }, 4000, 6000);

            return pagerHolder;

        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder sectionRowViewHolder, int position) {
        MarketSectionModel section = sections.get(position);
        if (section.type == 1) {
            SectionRowViewHolder holder = (SectionRowViewHolder) sectionRowViewHolder;
            String sectionTitle = section.getSectionTitle();
            List sectionItems = (List) section.getSectionList();
            holder.sectionTitle.setText(sectionTitle);
            ProductViewAdapter adapter = (ProductViewAdapter) holder.sectionRecyclerView.getAdapter();
            adapter.setProductList(sectionItems);

        } else {
            List sliderImages = (List) section.getSectionList();
            if (!requireNonNull(sliderImages).isEmpty()) {
                SliderAdapter adapter = (SliderAdapter) ((SectionRowViewPagerHolder) sectionRowViewHolder)
                        .imageViewPager.getAdapter();
                requireNonNull(adapter).setBanners(sliderImages);
            }
        }
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void AddSection(MarketSectionModel section) {
        int index = sections.size();
        if (index > 1 && section.type == 2) {
            index = 0;
            sections.clear();
            notifyDataSetChanged();
        }

        this.sections.add(index, section);
        notifyItemInserted(index);
    }


    public void destroy() {
        timer.cancel();
        timer = null;
        handler = null;
    }
}
