package voteDisplay;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.Video;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Andrew on 24.04.2017.
 */
public class ParsedVotesProvider {
    private OkHttpClient mClient = new OkHttpClient();
    private String mGistUrl = "https://gist.githubusercontent.com/blainepwnz/35c5f2ea88c097ab0c57996b54987693/raw/2d8cbba22dc18a0bdf6760122f2a7f13c113dc51/data";
    private Gson mGson = new Gson();


    public Map<Integer, Video> getVideoMap() {
        try {
            Request request = new Request.Builder()
                .url(mGistUrl)
                .get()
                .build();
            Response response = mClient.newCall(request).execute();
            return mGson.fromJson(response.body().string(), new TypeToken<Map<Integer, Video>>() {
            }.getType());
        } catch (IOException e) {
            return null;
        }
    }

}
