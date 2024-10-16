import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import static java.util.stream.Collectors.joining;


class Result {

    /*
     * Complete the 'getTotalGoals' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. STRING team
     *  2. INTEGER year
     */
    private static final String ENDPOINT = "https://jsonmock.hackerrank.com/api/football_matches";

    public static Match getMatchRecord(String team, int year, int page, boolean isHomeMatch) {

        String teamNumber = isHomeMatch ? "team1" : "team2";

        HttpGet getRequest = new HttpGet(ENDPOINT + "?year=" + year + "&" + teamNumber + "=" + team + "&page=" + page );

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(getRequest)) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                Match match = new Gson().fromJson(result, Match.class);
                //System.out.println(match.getData().get(0).getTeam1Goals());
                //System.out.println(match.getData().get(0).getTeam1Goals());
                return match;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static int getNumberOfPagesForMatchRecord(String team, int year, boolean isHomeMatch) {
        Match match = getMatchRecord(team, year, 1, isHomeMatch);
        if (match != null) {
            return match.getTotalPages();
        }
        return 0;
    }

    public static int countGoalsOfMatch (String team, int year, boolean isHomeMatch, int totalPages) {
        int totalGoals = 0;
        for (int page=1; page<=totalPages; page++) {
            Match match = getMatchRecord(team, year, page, isHomeMatch);
            if (match != null) {
                for (MatchData data : match.getData()) {
                    if (isHomeMatch) {
                        totalGoals += data.getTeam1Goals();
                    }
                    else {
                        totalGoals += data.getTeam2Goals();
                    }

                }
            }
        }
        return totalGoals;
    }

    public static int getTotalGoals(String team, int year) {
        int totalGoals = 0;
        try {
            team = URLEncoder.encode(team, "UTF-8");

            int homeMatchRecordPages = getNumberOfPagesForMatchRecord(team, year, true);
            int visitingMatchRecordPages = getNumberOfPagesForMatchRecord(team, year, false);

            totalGoals = countGoalsOfMatch(team, year, true, homeMatchRecordPages)
                    + countGoalsOfMatch(team, year, false, visitingMatchRecordPages);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }



        return totalGoals;
    }

}

class Match {
    private int page;
    private int total_pages;
    List<MatchData> data;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return total_pages;
    }

    public List<MatchData> getData() {
        return data;
    }

}

class MatchData {
    private int team1goals;
    private int team2goals;

    public int getTeam1Goals()
    {
        return team1goals;
    }

    public int getTeam2Goals()
    {
        return team2goals;
    }


}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String team = bufferedReader.readLine();

        int year = Integer.parseInt(bufferedReader.readLine().trim());

        int result = Result.getTotalGoals(team, year);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
