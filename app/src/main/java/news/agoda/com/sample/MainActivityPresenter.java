package news.agoda.com.sample;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import news.agoda.com.sample.Model.NewsEntities;
import news.agoda.com.sample.contracts.IMainActivityViewContract;
import news.agoda.com.sample.contracts.IMainPresenterContract;
import news.agoda.com.sample.contracts.Interactor.ModelInteractor;

/**
 * Created by jeffery.emanuel on 2017-02-24.
 */
public class MainActivityPresenter implements IMainPresenterContract {

    IMainActivityViewContract view;//todo set up a weak reference to View to avoid leakage
    ModelInteractor interactor;

    public MainActivityPresenter(IMainActivityViewContract view) {
        this.view = view;
        this.interactor = new ModelInteractor(this);
    }


    public void loadResource() {
        interactor.loadResource();
    }


    public void onRequestComplete(final NewsEntities newsEntities) {

                view.dataSetUpdated(newsEntities.getResults());

                ListView listView = view.getNewsListView();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
                        view.goToDetailsActivity(newsEntities.getResults().get(position));

                    }
                });

    }

}
