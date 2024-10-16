package com.example.demo.controller;

import com.example.demo.services.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "/yt/videos")
public class YouTubeController {
    private final YouTubeService youTubeService;

    @Autowired
    public YouTubeController(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    @GetMapping(path = "{query}")
    public int getVideos (@PathVariable("query") String query,
                          @RequestParam(required = false) Integer maxResult) {
        try {
            youTubeService.getVideos(query, maxResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @PostMapping(path = "/str")
    public String getVideos () {
        try {
            return youTubeService.sendPOST();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
