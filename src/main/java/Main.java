import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Video;
import entity.Vote;
import voteDisplay.ParsedVotesProvider;
import votesCounter.VoteGetter;
import voteDisplay.VoteViewer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static spark.Spark.*;

public class Main {

    private static Map<Integer, Video> mVideoMap = new HashMap<>();
    private static VoteViewer mVoteViewer = new VoteViewer();

    public static void main(String[] args) throws Exception {

//        port(Integer.valueOf(System.getenv("PORT")));
        port(8080);
        ParsedVotesProvider parsedVotesProvider = new ParsedVotesProvider();
        mVideoMap = parsedVotesProvider.getVideoMap();
        staticFileLocation("/public");
        get("/", (request, response) -> {
            return mVoteViewer.showData(mVideoMap);
        });
        get("/:voteId", (request, response) -> {
            int voteId=0;
            try {
                voteId = Integer.parseInt(request.params(":voteId"));
            } catch (Exception e) {
                return "Incorrect id";
            }
            return mVoteViewer.showDataForVideo(mVideoMap.get(voteId));
        });
    }


}
