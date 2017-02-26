package news.agoda.com.sample.Services;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import news.agoda.com.sample.Callback;
import news.agoda.com.sample.Model.NewsEntities;
import news.agoda.com.sample.contracts.INewsServiceContract;

/**
 * Created by jeffery.emanuel on 2017-02-24.
 */

public class NewsService implements INewsServiceContract {

    private Gson gson;
    private Callback mCallback;


    public NewsService() {
        configureGson();
    }

    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void setCallBack(Callback cb) {
        mCallback = cb; // or we can set up event bus
    }

    private void configureGson() {


        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        gson = builder.create();
    }

    @Override
    public void loadResource() {
//Todo could use a loader instead help with the config change or a headless fragment
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                String readStream = "";
                HttpURLConnection con = null;
                try {
                    URL url = new URL("https://api.myjson.com/bins/nl6jh");
                    con = (HttpURLConnection) url.openConnection();
                    readStream = readStream(con.getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                finally {
                    if(con!=null)
                    con.disconnect();
                }
                return readStream;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                NewsService.this.onRequestComplete(result);


            }
        }.execute();
    }

    private void onRequestComplete(String data) {

        data = data.replaceAll("\"multimedia\":\"\"", "\"multimedia\":[]");
        news.agoda.com.sample.Model.NewsEntities newsEntities = gson.fromJson(data, NewsEntities.class);
        mCallback.onResult(newsEntities);
    }
}
