package news.agoda.com.sample;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.List;

import news.agoda.com.sample.Constants.Constants;
import news.agoda.com.sample.contracts.OnNewsItemSelectionChangeListener;

import static news.agoda.com.sample.Constants.Constants.EXTRA_RESULTS_KEY;

/**
 * Created by jeffery.emanuel on 2017-02-25.
 */

public class NewsListFragment extends ListFragment {
    private NewsListAdapter adapter;
    private List mdataSource;

    public NewsListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);

    }

    @Override
    public void onResume() {
        if(mdataSource!=null)
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null) {
            mdataSource = Parcels.unwrap(bundle.getParcelable(Constants.EXTRA_RESULTS_KEY));

        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the current result in case we need to recreate the fragment
        outState.putParcelable(EXTRA_RESULTS_KEY, Parcels.wrap(mdataSource));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        OnNewsItemSelectionChangeListener listener = (OnNewsItemSelectionChangeListener) getActivity();
        listener.OnSelectionChanged(position);
    }

    public void refreshList(List dataSource) {
        mdataSource = dataSource;
        adapter = new NewsListAdapter(getActivity(), R.layout.list_item_news, dataSource);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
