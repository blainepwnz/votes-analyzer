import entity.Video;
import voteDisplay.ParsedVotesProvider;
import voteDisplay.VoteViewer;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    private static Map<Integer, Video> mVideoMap = new HashMap<>();
    private static VoteViewer mVoteViewer = new VoteViewer();

    public static void main(String[] args) throws Exception {
        String port = System.getenv("PORT");
        if (port == null)
            port(8080);
        else
            port(Integer.valueOf(port));
        ParsedVotesProvider parsedVotesProvider = new ParsedVotesProvider();
        mVideoMap = parsedVotesProvider.getVideoMap();
        staticFileLocation("/public");
        get("/", (request, response) -> {
            return mVoteViewer.showData(mVideoMap);
        });
        get("/:voteId", (request, response) -> {
            int voteId = 0;
            try {
                voteId = Integer.parseInt(request.params(":voteId"));
            } catch (Exception e) {
                return "Incorrect id";
            }
            return mVoteViewer.showDataForVideo(mVideoMap.get(voteId));
        });
    }


}
