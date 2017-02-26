package news.agoda.com.sample;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.List;

import news.agoda.com.sample.Constants.Constants;
import news.agoda.com.sample.Model.Result;
import news.agoda.com.sample.Services.NewsService;
import news.agoda.com.sample.contracts.IMainActivityViewContract;
import news.agoda.com.sample.contracts.OnNewsItemSelectionChangeListener;

import static news.agoda.com.sample.Constants.Constants.EXTRA_RESULTS_KEY;

public class MainActivity
        extends FragmentActivity
        implements IMainActivityViewContract, OnNewsItemSelectionChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityPresenter presenter;
    private boolean isTwoPane;
    private List mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTwoPane = getResources().getBoolean(R.bool.twoPaneMode);

        NewsService newsService = new NewsService();
        presenter = new MainActivityPresenter(this, newsService);
        newsService.setCallBack(presenter);

        if (savedInstanceState == null) {
            if (findViewById(R.id.fragment_container) != null) {

                // Create an Instance of Fragment
                NewsListFragment newsListFragment = new NewsListFragment();
                newsListFragment.setArguments(getIntent().getExtras());
                getFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, newsListFragment, "newsListFragment")
                        .commit();
            }

        }
        getFragmentManager().executePendingTransactions();
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
        setDataSource(results);
        updateNewsListFragment();

    }

    private void updateNewsListFragment() {
        NewsListFragment newsListFragment = (NewsListFragment) getFragmentManager().findFragmentByTag(isTwoPane ? "newsListFragment_landscape" : "newsListFragment");
        newsListFragment.refreshList(getDataSource());
    }

    @Override
    public void goToDetailsActivity(Result data) {
        Intent intent = new Intent(MainActivity.this, DetailViewFragment.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_DETAILS, Parcels.wrap(data));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showToast(int resource) {
        Toast.makeText(this, resource, Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnSelectionChanged(int index) {

        Result result = (Result) getDataSource().get(index);
        if (isTwoPane) {
            DetailViewFragment descriptionFragment = (DetailViewFragment) getFragmentManager()
                    .findFragmentById(R.id.description_fragment);
            descriptionFragment.updateResult(result);
        } else {
            DetailViewFragment newDesriptionFragment = new DetailViewFragment();
            Bundle args = new Bundle();

            args.putParcelable(EXTRA_RESULTS_KEY, Parcels.wrap(result));
            newDesriptionFragment.setArguments(args);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container, newDesriptionFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }


    }

    private List getDataSource() {
        return mDataSource;
    }

    private void setDataSource(List dataSource) {
        mDataSource = dataSource;
    }

}
