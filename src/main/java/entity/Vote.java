package entity;

/**
 * Created by Andrew on 23.04.2017.
 */
public class Vote {
    private String country;
    private String city;
    private int video;
    private int date;
    private int hours;
    private int minutes;
    private int seconds;


    public Vote(String country, String city, int video, int date, int hours, int minutes, int seconds) {
        this.country = country;
        this.city = city;
        this.video = video;
        this.date = date;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }


    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getVideo() {
        return video;
    }

    public int getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;

        Vote vote = (Vote) o;

        if (video != vote.video) return false;
        if (date != vote.date) return false;
        if (hours != vote.hours) return false;
        if (minutes != vote.minutes) return false;
        if (seconds != vote.seconds) return false;
        if (!country.equals(vote.country)) return false;
        return city.equals(vote.city);
    }

    @Override
    public int hashCode() {
        int result = country.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + video;
        result = 31 * result + date;
        result = 31 * result + hours;
        result = 31 * result + minutes;
        result = 31 * result + seconds;
        return result;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", video=" + video +
                ", date=" + date +
                ", hours=" + hours +
                ", minutes=" + minutes +
                ", seconds=" + seconds +
                '}';
    }
}
