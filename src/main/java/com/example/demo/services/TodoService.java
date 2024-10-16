package com.example.demo.services;

import com.example.demo.models.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private final String TODO_ENDPOINT = "https://jsonplaceholder.typicode.com/todos";

    ObjectMapper objectMapper = new ObjectMapper();
    Gson gson = new GsonBuilder().create();


    /**
     * HTTP get
     */
    public Todo[] getTodos ()  {

        HttpGet request = new HttpGet(TODO_ENDPOINT);

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
                // System.out.println(result);
                Todo[] todo = objectMapper.readValue(result, Todo[].class);
                return todo;
            }


        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    public List<Todo> getTodosGson () {
        try {
            String restUrl = URLEncoder.encode("You url parameter value", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        HttpGet request = new HttpGet(TODO_ENDPOINT);

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
                // System.out.println(result);

                //new Gson().fromJson(result, Todo.class);
                List<Todo> todo = new Gson().fromJson(result, ArrayList.class);
                //List<Todo> todo = gson.fromJson(result, new TypeToken<List<Todo>>(){}.getType());
                return todo;
            }


        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;

    }





}
