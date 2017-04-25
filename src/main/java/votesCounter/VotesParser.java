package votesCounter;

import entity.Vote;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * Used to parse raw html info Vote objects
 */
public class VotesParser {

    public List<Vote> parseVotes(String raw) {
        List<Vote> votes = new ArrayList<>(20);
        Document html = Jsoup.parse(raw);
        Element table = html.body().getElementsByTag("table").first();
        Elements trElements = table.getElementsByTag("tr");
        //ignore first element that contains generic data about html elements
        //usually one raw string contains 21 elements of tr 1 - header , other 20 with data
        for (int i = 1; i < trElements.size(); i++) {
            Element trElement = trElements.get(i);
            votes.add(parseVote(trElement));
        }
        return votes;
    }

    private Vote parseVote(Element voteElement) {
        //to decrease weight of data parse only nummbers from date
        int date = Integer.valueOf(voteElement.getElementsByClass("date").text().substring(0, 2));
        int hours = Integer.valueOf(voteElement.getElementsByClass("hours").text());
        int minutes = Integer.valueOf(voteElement.getElementsByClass("minutes").text().substring(1, 3));
        int seconds = Integer.valueOf(voteElement.getElementsByClass("seconds").text().substring(1, 3));
        String country = voteElement.getElementsByClass("country").text();
        //if there is no country - show unknown
        if (country.isEmpty())
            country = "Unknown";
        String city = voteElement.getElementsByClass("city").text();
        int video = Integer.valueOf(voteElement.getElementsByClass("answers").text().substring(7));
        return new Vote(country, city, video, date, hours, minutes, seconds);
    }


}
