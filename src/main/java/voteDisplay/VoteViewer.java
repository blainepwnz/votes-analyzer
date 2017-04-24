package voteDisplay;

import app.VotesResultApp;
import entity.Video;

import java.util.Map;

/**
 * Created by Andrew on 23.04.2017.
 */
public class VoteViewer extends BaseViewer {
    public String showData(Map<Integer, Video> videoMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("Detailed info about poll participants");
        addNextLine(sb);
        sb.append("Videos:");
        addNextLine(sb);
        for (int i = 1; i < 23; i++) {
            sb.append("<a href=\"")
                .append(VotesResultApp.mBaseUrl)
                .append(i)
                .append("\">Video #")
                .append(i)
                .append("</a>");
            addNextLine(sb);
        }
        addNextLine(sb);
        sb.append("(c)Andrew Tomash 2017");
        return sb.toString();
    }

    public String showDataForVideo(Video video) {
        return new VideoDataPrinter().showDataForVideo(video);
    }


}
