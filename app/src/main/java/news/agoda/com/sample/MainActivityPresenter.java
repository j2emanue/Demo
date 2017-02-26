package news.agoda.com.sample;

import news.agoda.com.sample.Model.NewsEntities;
import news.agoda.com.sample.Model.Result;
import news.agoda.com.sample.Services.NewsService;
import news.agoda.com.sample.contracts.IMainActivityViewContract;
import news.agoda.com.sample.contracts.IMainPresenterContract;

/**
 * Created by jeffery.emanuel on 2017-02-24.
 */
public class MainActivityPresenter implements IMainPresenterContract, Callback {

    private IMainActivityViewContract view;//todo set up a weak reference to View to avoid leakage
    private NewsService interactor;

    public MainActivityPresenter(IMainActivityViewContract view, NewsService interactor) {
        this.view = view;
        this.interactor = interactor;
    }


    public void loadResource() {
        interactor.loadResource();
    }

    @Override
    public void onRequestComplete(final NewsEntities newsEntities) {

        if (newsEntities != null)
            view.dataSetUpdated(newsEntities.getResults());
        else
            view.showToast(R.string.error);
    }

    @Override
    public void onResult(final NewsEntities newsEntities) {
        onRequestComplete(newsEntities);
    }

    @Override
    public void goToDetailsActivity(Result result) {
        view.goToDetailsActivity(result);
    }
}
