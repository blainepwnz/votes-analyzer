package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Andrew on 23.04.2017.
 */
public class Video {
    private Integer id;
    private HashMap<String,List<Integer>> dataMap = new HashMap<>();

    public Video(Integer id) {
        this.id = id;
    }

    public HashMap<String, List<Integer>> getDataMap() {
        return dataMap;
    }

    public void addVoter(Vote vote){
        dataMap.computeIfAbsent(vote.getCountry(), k -> new ArrayList<>()).add(vote.getDate());
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", dataMap=" + dataMap +
                '}';
    }
}
