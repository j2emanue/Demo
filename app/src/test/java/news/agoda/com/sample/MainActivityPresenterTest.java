package news.agoda.com.sample;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import news.agoda.com.sample.Model.NewsEntities;
import news.agoda.com.sample.Model.Result;
import news.agoda.com.sample.contracts.IMainActivityViewContract;
import news.agoda.com.sample.Services.NewsService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by jeffery.emanuel on 2017-02-25.
 */


// i'll be following the  AAA principle - Arrange, Act, Assert testing pattern

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    @Mock
    private IMainActivityViewContract view;
    @Mock
    private NewsService service;

    private MainActivityPresenter presenter;


    @Before
    public void setUp() throws Exception {
    //lets test the presenter in isolation
        presenter = new MainActivityPresenter(view,service);
        service.setCallBack(presenter);
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void shouldDoAPIRequestOnLoadResources() throws Exception {
        presenter.loadResource();
        verify(service,times(1)).loadResource();
    }


    @org.junit.Test
    public void shouldDisplayResultsOnRequestComplete() throws Exception {

        presenter.onRequestComplete(new NewsEntities());

        verify(view,times(1)).dataSetUpdated(any(List.class));
    }

    @org.junit.Test
    public void clickingItemStartsDetailActivity() throws Exception {

        Result fakeResult = new Result();
        presenter.goToDetailsActivity(fakeResult);

        verify(view,times(1)).goToDetailsActivity(fakeResult);
    }

}