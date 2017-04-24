package votesCounter;

import entity.Vote;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;

/**
 * Created by Andrew on 23.04.2017.
 */
public class VotesParser {

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
        return new Vote(country, city, video, date, hours, minutes, seconds);
    }


}
