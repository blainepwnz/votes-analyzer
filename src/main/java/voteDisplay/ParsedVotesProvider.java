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
    private String mGistUrl = "https://gist.githubusercontent.com/blainepwnz/550488bec81a256e6a7ab7d995f19082/raw/388d8e3697b2c10e1bb570e132a56c661545c7e5/gistfile1.txt";
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
