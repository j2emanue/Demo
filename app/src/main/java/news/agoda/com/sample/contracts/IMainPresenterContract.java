package news.agoda.com.sample.contracts;

import news.agoda.com.sample.Model.NewsEntities;
import news.agoda.com.sample.Model.Result;

/**
 * Created by jeffery.emanuel on 2017-02-24.
 */

public interface IMainPresenterContract {
    void onRequestComplete(final NewsEntities newsEntities);
    void goToDetailsActivity(Result result);
}
