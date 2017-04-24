package entity;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrew on 23.04.2017.
 */
public class Video {
    @SerializedName("id")
    private Integer id;
    @SerializedName("dataMap")
    private Map<Integer, Map<String, Integer>> dataMap = new HashMap<>();

    public Video(Integer id) {
        this.id = id;
    }

    public Map<Integer, Map<String, Integer>> getDataMap() {
        return dataMap;
    }

    public void addVoter(Vote vote) {
        Map<String, Integer> countryVotesMap = dataMap.computeIfAbsent(vote.getDate(), integer -> new HashMap<>());
        String country = vote.getCountry();
        Integer votesCount = countryVotesMap.get(country);
        if (votesCount == null)
            countryVotesMap.put(country, 1);
        else
            countryVotesMap.put(country, ++votesCount);
    }

    public Integer getId() {
        return id;
    }
}
