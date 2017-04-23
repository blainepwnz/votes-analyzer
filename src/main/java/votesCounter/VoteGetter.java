package votesCounter;

import entity.Vote;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class VoteGetter {
    public static int COUNT_PER_ITERATION = 100;
//    public static int MAX_PAGE_COUNT = 400;
    public static int MAX_PAGE_COUNT = 6390;
    private VotesParser parser = new VotesParser();
    private OkHttpClient client = new OkHttpClient();
    private Set<Vote> votes = new HashSet<>();
    public static int page = 0;


    public Set<Vote> getVotes(int offset) {
        CounterThread[] counterThreads = new CounterThread[3];
        for (int i = 0; i < counterThreads.length; i++) {
            counterThreads[i] = new CounterThread(i + offset);
            counterThreads[i].start();
        }
        for (CounterThread counterThread : counterThreads) {
            try {
                counterThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        page = COUNT_PER_ITERATION+offset;
        return votes;
    }

    private class CounterThread extends Thread {
        private int startingIndex;
        private String parseURL = "https://pollservice.ru/p/p0jp1x7xkh/results/votes?page=";

        CounterThread(int startingIndex) {
            this.startingIndex = startingIndex;
        }

        @Override
        public void run() {
            for (int i = startingIndex; i < 100 + startingIndex && i < MAX_PAGE_COUNT; i += 3) {
                String rawResponse = getNextResponse(i);
                if (rawResponse == null) {
                    i -= 3;
                    continue;
                }
                page = i;
                votes.addAll(parser.parseVotes(rawResponse));
            }
        }

        private String getNextResponse(int page) {
            try {
                Request request = new Request.Builder()
                    .url(parseURL + page)
                    .get()
                    .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                return null;
            }
        }
    }

}
