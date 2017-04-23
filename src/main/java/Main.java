import entity.Video;
import entity.Vote;
import votesCounter.VoteGetter;
import votesCounter.VoteViewer;

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
        initVoteMap();
        get("/", (request, response) -> {
            return mVoteViewer.showData(mVideoMap);
        });
        for (int i = 0; i < VoteGetter.MAX_PAGE_COUNT - VoteGetter.COUNT_PER_ITERATION; i += VoteGetter.COUNT_PER_ITERATION) {
            VoteGetter voteGetter = new VoteGetter();
            Set<Vote> votes = voteGetter.getVotes(i);
            appendSet(votes);
        }
    }

    private static void initVoteMap() {
        for (int i = 1; i < 23; i++) {
            mVideoMap.put(i, new Video(i));
        }
    }

    private static void appendSet(Set<Vote> set) {
        for (Vote vote : set) {
            mVideoMap.get(vote.getVideo()).addVoter(vote);
        }
    }

}
