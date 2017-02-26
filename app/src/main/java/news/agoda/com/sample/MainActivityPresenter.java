package news.agoda.com.sample;

import news.agoda.com.sample.Model.NewsEntities;
import news.agoda.com.sample.Model.Result;
import news.agoda.com.sample.contracts.IMainActivityViewContract;
import news.agoda.com.sample.contracts.IMainPresenterContract;
import news.agoda.com.sample.contracts.Services.NewsService;

/**
 * Created by jeffery.emanuel on 2017-02-24.
 */
public class MainActivityPresenter implements IMainPresenterContract, Callback {

    IMainActivityViewContract view;//todo set up a weak reference to View to avoid leakage
    NewsService interactor;

    public MainActivityPresenter(IMainActivityViewContract view, NewsService interactor) {
        this.view = view;
        this.interactor = interactor;
    }


    public void loadResource() {
        interactor.loadResource();
    }


    public void onRequestComplete(final NewsEntities newsEntities) {

        view.dataSetUpdated(newsEntities.getResults());
    }

    @Override
    public void onResult(final NewsEntities newsEntities) {
        onRequestComplete(newsEntities);
    }

    public void goToDetailsActivity(Result result) {
        view.goToDetailsActivity(result);
    }
}
