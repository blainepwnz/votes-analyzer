package votesCounter;

import entity.Video;

import java.util.List;
import java.util.Map;

/**
 * Created by Andrew on 23.04.2017.
 */
public class VoteViewer {
    public String showData(Map<Integer, Video> videoMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("Pages parsed :");
        sb.append(VoteGetter.page);
        sb.append("<br />");
        sb.append("Updated every minute");
        sb.append("<br />");
        sb.append("<br />");
        for (int i = 1; i < 23; i++) {
            int votesSum = 0;
            Video current = videoMap.get(i);
            sb.append("Video #");
            sb.append(i);
            sb.append("<br />");
            Map<String, List<Integer>> dataMap = current.getDataMap();
            for (String s : dataMap.keySet()) {
                int votesCount = dataMap.get(s).size();
                sb.append(s)
                    .append(" ")
                    .append(votesCount)
                    .append("<br />");
                votesSum += votesCount;
            }

            sb.append("In sum= ")
                .append(votesSum)
                .append("<br />")
                .append("--------------------------------------------------------------------------------------------------------")
                .append("<br />");
        }
        sb.append("(c)Andrew Tomash 2017");
        return sb.toString();
    }


}
