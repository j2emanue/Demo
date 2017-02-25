package news.agoda.com.sample.contracts;

import news.agoda.com.sample.Model.NewsEntities;

/**
 * Created by jeffery.emanuel on 2017-02-24.
 */

public interface IMainPresenterContract {
    void onRequestComplete(final NewsEntities newsEntities);
}
