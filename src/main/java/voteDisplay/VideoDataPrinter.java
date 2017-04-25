package voteDisplay;

import app.VotesResultApp;
import entity.Video;

import java.util.*;

public class VideoDataPrinter extends BaseViewer {


    //global votes counter
    private int mVotesCount;


    /**
     * Shows data for every country
     * @param video video data for parsing
     */
    public String showDataForVideo(Video video) {
        StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"")
            .append(VotesResultApp.mBaseUrl)
            .append("\">Back</a>")
            .append("<br/>")
            .append("<br/>");
        sb.append("Info for video # ")
            .append(video.getId())
            .append("<br/>");
        addNextLine(sb);
        Set<Integer> datesSet = video.getDataMap().keySet();
        List<Integer> datesList = new ArrayList<>(datesSet);
        Collections.sort(datesList);
        Collections.reverse(datesList);
        StringBuilder dataSb = new StringBuilder();
        for (Integer date : datesList) {
            dataSb.append(displayCountriesInDay(date, new StringBuilder(), video.getDataMap().get(date)));
        }
        sb.append("Sum of votes for all competition = ")
            .append(mVotesCount)
            .append("<br/>")
            .append("<br/>")
            .append("Dates:")
            .append("<br/>")
        ;
        sb.append(dataSb);
        return sb.toString();
    }


    private StringBuilder displayCountriesInDay(int date, StringBuilder sb, Map<String, Integer> countyVotesMap) {
        addNextLine(sb);
        makeItalic(sb, date + " April");
        addNextLine(sb);
        //sum counter used to append it every iteration with country
        int sum = 0;
        for (String country : countyVotesMap.keySet()) {
            //making bold moldova and russia
            if (country.contains("Moldova") || country.contains("Russia"))
                makeBold(sb, country);
            else
                sb.append(country);
            int countOfVotes = countyVotesMap.get(country);
            sum += countOfVotes;
            sb.append(" - ")
                .append(countOfVotes)
                .append(" votes.");
            addNextLine(sb);
        }
        addNextLine(sb);
        makeBold(sb, "Sum of votes for this day - " + sum);
        addNextLine(sb);
        sb.append("------------------------------------------");
        addNextLine(sb);
        //append sum to global votes counter
        mVotesCount += sum;
        return sb;
    }
}
