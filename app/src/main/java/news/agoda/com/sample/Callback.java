package news.agoda.com.sample;

import news.agoda.com.sample.Model.NewsEntities;

public interface Callback {
    void onResult(final NewsEntities newsEntities);
}
