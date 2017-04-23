import entity.Video;
import entity.Vote;
import votesCounter.VoteGetter;
import votesCounter.VoteViewer;
import votesCounter.VotesDivider;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static spark.Spark.*;

public class Main {

    private static Map<Integer, Video> mVideoMap = new HashMap<>();
    private static VoteViewer mVoteViewer = new VoteViewer();

    public static void main(String[] args) {

        port(Integer.valueOf(System.getenv("PORT")));
//        port(8080);
        staticFileLocation("/public");
        get("/", (request, response) -> {
            return mVoteViewer.showData(mVideoMap);
        });
        VoteGetter voteGetter = new VoteGetter();
        for (int i = 0; i < 1000; i += VoteGetter.COUNT_PER_ITERATION) {
            Set<Vote> votes = voteGetter.getVotes(i);
            VotesDivider divider = new VotesDivider(votes);
            mVideoMap.putAll(divider.divideVotes());
        }
    }

}
