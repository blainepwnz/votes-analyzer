package votesCounter;

import entity.Video;
import entity.Vote;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Andrew on 23.04.2017.
 */
public class VotesDivider {
    private Set<Vote> mVoteSet;
    private Map<Integer, Video> mVideoMap;

    public VotesDivider(Set<Vote> mVoteSet) {
        this.mVoteSet = mVoteSet;
        mVideoMap = new HashMap<>();
        for (int i = 1; i < 23; i++) {
            mVideoMap.put(i, new Video(i));
        }
    }

    public Map<Integer, Video> divideVotes() {
        for (Vote vote : mVoteSet) {
            mVideoMap.get(vote.getVideo()).addVoter(vote);
        }
        return mVideoMap;
    }


}
