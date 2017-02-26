package news.agoda.com.sample;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import news.agoda.com.sample.Constants.Constants;
import news.agoda.com.sample.Model.Result;

import static news.agoda.com.sample.Constants.Constants.EXTRA_RESULTS_KEY;

/**
 * News detail view
 */
public class DetailViewFragment extends Fragment {
    @BindView(R.id.title)
    TextView titleView;
    @BindView(R.id.summary_content)
    TextView summaryView;
    @BindView(R.id.news_image)
    DraweeView imageView;
    private String mStoryURL = "";
    private Result mResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_detail,
                container, false);

        ButterKnife.bind(this,view);

        if (savedInstanceState != null){
            //in two pane mode savedInstanceState might not be empty. retrieving the state here
            mResult =  Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_RESULTS_KEY));
        }
        return view;
    }

    private void updateView() {

        String title = mResult.getTitle();
        String imageURL = mResult.getMultimedia().get(0).getUrl();
        mStoryURL = mResult.getUrl();
        String summary = mResult.getAbstract();

        titleView.setText(title);
        summaryView.setText(summary);

        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(Uri.parse(imageURL)))
                .setOldController(imageView.getController()).build();
        imageView.setController(draweeController);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current result in case we need to recreate the fragment
        outState.putParcelable(EXTRA_RESULTS_KEY,Parcels.wrap(mResult));
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null) {
            mResult = Parcels.unwrap(bundle.getParcelable(EXTRA_RESULTS_KEY));
            updateView();
        }
    }






    @OnClick(R.id.full_story_link)
    public void onFullStoryBtnClicked(View v) {
        if(mStoryURL!=null){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mStoryURL));
        startActivity(browserIntent);
        }
    }

    public void updateResult(Result result) {
        mResult = result;
        updateView();

    }
}

