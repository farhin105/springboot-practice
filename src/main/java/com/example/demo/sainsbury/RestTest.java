package com.example.demo.sainsbury;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import com.google.gson.Gson;

public class RestTest {
    public static int run(String teamKey) {
        /*
         * Write your code below; return type and arguments should be according to the problem's requirements
         */

        League league = getMatchData();
        int goals = countScore(league, teamKey);
        return goals;
    }

    private static String sendGET() {
        try {
            URL obj = new URL("https://s3.eu-west-1.amazonaws.com/hackajob-assets1.p.hackajob/challenges/football_session/football.json");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            // set property if any
            //con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }
                bufferedReader.close();
                return response.toString();
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }
    public static League getMatchData() {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL("https://s3.eu-west-1.amazonaws.com/hackajob-assets1.p.hackajob/challenges/football_session/football.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            System.out.println(result);

            League league = new Gson().fromJson(result.toString(), League.class);
            return league;
        } catch (Exception e) {
            return null;
        }

    }

    public static int countScore(League league, String teamKey) {
        int score = 0;

        for (Round round: league.getRounds()) {
            for (Match match: round.getMatches()) {
                if (match.getTeam1().getKey().equals(teamKey)) {
                    score += match.getScore1();
                }
                else if (match.getTeam2().getKey().equals(teamKey)) {
                    score += match.getScore2();
                }
            }
        }
        return score;
    }

    public class League {
        String name;
        List<Round> rounds;

        public List<Round> getRounds() {
            return rounds;
        }
    }

    public class Round {
        String name;
        List<Match> matches;

        public List<Match> getMatches() {
            return matches;
        }
    }

    public class Match {
        private String date;
        private Team team1;
        private Team team2;
        private int score1;
        private int score2;

        public String getDate() {
            return date;
        }

        public Team getTeam1() {
            return team1;
        }

        public Team getTeam2() {
            return team2;
        }

        public int getScore1() {
            return score1;
        }

        public int getScore2() {
            return score2;
        }
    }
    public class Team {
        private String key;
        private String name;
        private String code;

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }
    }

    public static void main(String[] args) {
        run("something");
    }
}
