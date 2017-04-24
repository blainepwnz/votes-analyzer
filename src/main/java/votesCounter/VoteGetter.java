package votesCounter;

import entity.Vote;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class VoteGetter {
    public static int COUNT_PER_ITERATION = 100;
    public static boolean FINISHED_PARSING = false;
    private VotesParser parser = new VotesParser();
    private OkHttpClient client = new OkHttpClient();
    private List<Vote> votes = new ArrayList<>(2000);
    public static int page = 0;


    public List<Vote> getVotes(int offset,int threadCount) {
        CounterThread[] counterThreads = new CounterThread[threadCount];
        for (int i = 0; i < counterThreads.length; i++) {
            counterThreads[i] = new CounterThread(i + offset + 1);
            counterThreads[i].start();
        }
        for (CounterThread counterThread : counterThreads) {
            try {
                counterThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("page nr "+page);
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
            int endingIndex = (startingIndex/10)*10;
            for (int i = startingIndex; i < COUNT_PER_ITERATION + endingIndex + 1; i += VoteSniffer.mThreadCount) {
                if(FINISHED_PARSING)
                    return;
                String rawResponse = getNextResponse(i);
                if (rawResponse == null) {
                    System.out.println("error");
                    i -= VoteSniffer.mThreadCount;
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
