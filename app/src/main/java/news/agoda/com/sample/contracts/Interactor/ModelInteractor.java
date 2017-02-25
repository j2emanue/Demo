package news.agoda.com.sample.contracts.Interactor;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;

import news.agoda.com.sample.Callback;
import news.agoda.com.sample.MainActivityPresenter;
import news.agoda.com.sample.Model.NewsEntities;
import news.agoda.com.sample.Model.Result;
import news.agoda.com.sample.contracts.IModelInteractorContract;

/**
 * Created by jeffery.emanuel on 2017-02-24.
 */

public class ModelInteractor implements IModelInteractorContract, Callback {

    MainActivityPresenter presenter;
    Gson gson;
    public ModelInteractor(MainActivityPresenter MainActivityPresenter) {
    presenter = MainActivityPresenter;
        configureGson();
    }

    private void configureGson() {

        class CollectionAdapter implements JsonSerializer<Collection<?>> {
            @Override
            public JsonElement serialize(Collection<?> src, Type typeOfSrc, JsonSerializationContext context) {
                if (src == null || src.isEmpty() || src.equals("")) // exclusion is made here
                    return null;

                JsonArray array = new JsonArray();

                for (Object child : src) {
                    JsonElement element = context.serialize(child);
                    array.add(element);
                }

                return array;
            }
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Result.class,new CollectionAdapter());
        builder.excludeFieldsWithoutExposeAnnotation();
         gson = builder.create();
    }

    @Override
    public void loadResource() {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                String readStream="";
                try {
                    URL url = new URL("https://api.myjson.com/bins/nl6jh");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    readStream = readStream(con.getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return readStream;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                ModelInteractor.this.onResult(result);


            }
        }.execute();


        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {

            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    @Override
    public void onResult(String data) {

        data = data.replaceAll("\"multimedia\":\"\"","\"multimedia\":[]");
        NewsEntities  newsEntities = gson.fromJson(data, NewsEntities.class);
        presenter.onRequestComplete(newsEntities);
    }
}
