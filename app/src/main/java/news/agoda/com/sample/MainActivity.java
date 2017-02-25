package news.agoda.com.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.parceler.Parcels;

import java.util.List;

import news.agoda.com.sample.Constants.Constants;
import news.agoda.com.sample.Model.Result;
import news.agoda.com.sample.contracts.IMainActivityViewContract;
import news.agoda.com.sample.contracts.Services.NewsService;

public class MainActivity
        extends ListActivity
        implements IMainActivityViewContract {

    private static final String TAG = MainActivity.class.getSimpleName();
    MainActivityPresenter presenter;
    NewsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);

        NewsService newsService = new NewsService();
        presenter = new MainActivityPresenter(this, newsService);
        newsService.setCallBack(presenter);

        presenter.loadResource();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void dataSetUpdated(List results) {
        adapter = new NewsListAdapter(MainActivity.this, R.layout.list_item_news, results);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public ListView getNewsListView() {
        return getListView();
    }

    @Override
    public void goToDetailsActivity(Result data) {
        Intent intent = new Intent(MainActivity.this, DetailViewActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_DETAILS, Parcels.wrap(data));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
