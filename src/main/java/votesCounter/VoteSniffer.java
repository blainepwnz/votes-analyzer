package votesCounter;

import com.google.gson.Gson;
import entity.Video;
import entity.Vote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrew on 24.04.2017.
 */
public class VoteSniffer {
    private Map<Integer, Video> mVideoMap = new HashMap<>();
    public static int mThreadCount=3;

    public VoteSniffer() {
        initVoteMap();
    }

    private void appendSet(List<Vote> list) {
            for (Vote vote : list) {
                if(vote==null)
                    continue;
                mVideoMap.get(vote.getVideo()).addVoter(vote);
            }
    }

    private void initVoteMap() {
        for (int i = 1; i < 23; i++) {
            mVideoMap.put(i, new Video(i));
        }
    }

    public String sniffData() {
        VoteGetter.FINISHED_PARSING = false;
        for (int i = 0; i < 6600; i += VoteGetter.COUNT_PER_ITERATION) {
            VoteGetter voteGetter = new VoteGetter();
            List<Vote> votes = voteGetter.getVotes(i, mThreadCount);
            appendSet(votes);
        }
        VoteGetter.COUNT_PER_ITERATION = 67;
        VoteGetter voteGetter = new VoteGetter();
        List<Vote> votes = voteGetter.getVotes(6600, mThreadCount);
        appendSet(votes);
        Gson gson = new Gson();
        return gson.toJson(mVideoMap);
    }
}
