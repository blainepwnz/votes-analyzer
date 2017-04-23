package votesCounter;

import entity.Vote;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Andrew on 23.04.2017.
 */
public class VotesParser {
    private static long mFinishTimestamp;
    public static long mFirstItemTimestamp;

    public HashSet<Vote> parseVotes(String raw) {
        HashSet<Vote> votes = new HashSet<>();
        Document html = Jsoup.parse(raw);
        Element table = html.body().getElementsByTag("table").first();
        Elements trElements = table.getElementsByTag("tr");
        for (int i = 1; i < trElements.size(); i++) {
            Element trElement = trElements.get(i);
            votes.add(parseVote(trElement));
        }
        return votes;
    }

    private Vote parseVote(Element voteElement) {
        int date = Integer.valueOf(voteElement.getElementsByClass("date").text().substring(0, 2));
        int hours = Integer.valueOf(voteElement.getElementsByClass("hours").text());
        int minutes = Integer.valueOf(voteElement.getElementsByClass("minutes").text().substring(1, 3));
        int seconds = Integer.valueOf(voteElement.getElementsByClass("seconds").text().substring(1, 3));
        String country = voteElement.getElementsByClass("country").text();
        if (country.isEmpty())
            country = "Unknown";
        String city = voteElement.getElementsByClass("city").text();
        int video = Integer.valueOf(voteElement.getElementsByClass("answers").text().substring(7));
        long voteTimeStamp = getTimestampForEvent(date, hours, minutes, seconds);
        if (mFirstItemTimestamp == 0) {
            mFirstItemTimestamp = voteTimeStamp;
        }
        if (voteTimeStamp == mFinishTimestamp) {
            System.out.println("finished");
            VoteGetter.FINISHED_PARSING = true;
            mFinishTimestamp = mFirstItemTimestamp;
        }
        return new Vote(country, city, video, date, hours, minutes, seconds);
    }

    public static void initTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.APRIL, 18, 20, 55, 44);
        calendar.clear(Calendar.MILLISECOND);
        mFinishTimestamp = calendar.getTimeInMillis();
    }

    private long getTimestampForEvent(int day, int hours, int minutes, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.APRIL, day, hours, minutes, seconds);
        calendar.clear(Calendar.MILLISECOND);
        return calendar.getTimeInMillis();
    }

}
