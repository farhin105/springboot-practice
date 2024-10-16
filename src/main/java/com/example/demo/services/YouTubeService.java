package com.example.demo.services;

import com.example.demo.models.Todo;
import com.example.demo.models.Video;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class YouTubeService {

    private final String API_KEY = "AIzaSyBjsJPCehrvSQfMLH928-Rj80JWJ9JfuyI";
    private final String ENDPOINT = "https://youtube.googleapis.com/youtube/v3/search?part=snippet" +
            "&fields=items(id%2Fkind%2Cid%2FvideoId%2Csnippet%2Ftitle%2Csnippet%2FpublishedAt%2Csnippet%2FchannelId%2Csnippet%2Fdescription)";

    private final Integer MAX_RESULT = 2;
    public int callExternalApi () throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            HttpGet request = new HttpGet("https://httpbin.org/get");


            // add request headers
//            request.addHeader("custom-key", "mkyong");
//            request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return 0;
    }

    /**
     * HTTP get
     * @param query
     * @param maxResult
     * @return
     * @throws IOException
     */
    public int getVideos (String query, Integer maxResult) throws IOException {
        maxResult = maxResult == null ? MAX_RESULT : maxResult;

        URI uri = null;

        try {
            // Create a URIBuilder instance
            uri = new URIBuilder(ENDPOINT)
                    .addParameter("maxResults", maxResult.toString())
                    .addParameter("q", query)
                    .addParameter("key", API_KEY)
                    .build();

//            // Add query parameters
//            uriBuilder.addParameter("maxResults", maxResult.toString());
//            uriBuilder.addParameter("q", query);
//            uriBuilder.addParameter("key", API_KEY);

            // Build the URI
//            uri = uriBuilder.build();


        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

//        String requestUrl = ENDPOINT
//                + "&maxResults=" + maxResult
//                + "&q=" + query
//                + "&key=" + API_KEY;

        HttpGet request = new HttpGet(uri.toString());

        /**
         * The try-with-resources statement is a try statement that declares one or more resources.
         * A resource is an object that must be closed after the program is finished with it.
         * The try-with-resources statement ensures that each resource is closed at the end of the statement.
         */
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            System.out.println(response);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }

        }
        return 0;

    }




    public String sendPOST() throws IOException {
        String url = "https://httpbin.org/post";

        String result = "";
        HttpPost post = new HttpPost(url);

        Todo todo = new Todo();
        todo.setId(333);
        todo.setUserId(456);
        todo.setCompleted(true);

//        Gson gson = new Gson();
        String jsonRequestBody = new Gson().toJson(todo);

//        StringBuilder json = new StringBuilder();
//        json.append("{");
//        json.append("\"anything\":\"mkyong\",");
//        json.append("\"okay\":\"hello\"");
//        json.append("}");

        // send a JSON data
        post.setEntity(new StringEntity(jsonRequestBody));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            result = EntityUtils.toString(response.getEntity());
        }

        return result;
    }





}
