package org.zeniafrik.ui.market.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import org.zeniafrik.api.ZeniNetResponse;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.Feed;
import org.zeniafrik.repository.MarketRepository;

import java.util.ArrayList;

import javax.inject.Inject;

public class FeedFragmentViewModel extends ViewModel {

    private final LiveData<Resource<ZeniNetResponse<ArrayList<Feed>>>> feeds;
    private final MutableLiveData<Integer> page = new MutableLiveData<>();

    @Inject
    public FeedFragmentViewModel(MarketRepository repository) {
        feeds = Transformations.switchMap(page, repository::getFeeds);
    }

    public LiveData<Resource<ZeniNetResponse<ArrayList<Feed>>>> getFeeds() {
        return feeds;
    }

    public void setPage(int next_page) {
        this.page.setValue(next_page);
    }
}