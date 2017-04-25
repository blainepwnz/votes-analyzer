package votesCounter;

import com.google.gson.Gson;
import entity.Video;
import entity.Vote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to get data from all pages of poll
 */
public class VoteSniffer {
    private Map<Integer, Video> mVideoMap = new HashMap<>();
    //number of threads used for algorithm
    public static int mThreadCount=3;

    public VoteSniffer() {
        initVoteMap();
    }

    /**
     * Addends list to main map with data
     */
    private void appendSet(List<Vote> list) {
            for (Vote vote : list) {
                //ignore null votes
                if(vote==null)
                    continue;
                //append to map every vote depending from its video id , country and date
                mVideoMap.get(vote.getVideo()).addVoter(vote);
            }
    }

    /**
     * inits vote map for all 22 participants
     */
    private void initVoteMap() {
        for (int i = 1; i < 23; i++) {
            mVideoMap.put(i, new Video(i));
        }
    }

    /**
     * Main method for geting parsed data
     * @return parsed json with all data of poll
     */
    public String sniffData() {
        VoteGetter.FINISHED_PARSING = false;
        //get first 6600 pages of poll
        for (int i = 0; i < 6600; i += VoteGetter.COUNT_PER_ITERATION) {
            VoteGetter voteGetter = new VoteGetter();
            List<Vote> votes = voteGetter.getVotes(i, mThreadCount);
            appendSet(votes);
        }
        //get 67 pages that remains
        VoteGetter.COUNT_PER_ITERATION = 67;
        VoteGetter voteGetter = new VoteGetter();
        List<Vote> votes = voteGetter.getVotes(6600, mThreadCount);
        appendSet(votes);
        //serializing into json
        Gson gson = new Gson();
        return gson.toJson(mVideoMap);
    }
}
