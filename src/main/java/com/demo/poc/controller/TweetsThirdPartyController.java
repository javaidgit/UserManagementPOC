package com.demo.poc.controller;

import com.demo.poc.dto.Tweet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TweetsThirdPartyController {

    @GetMapping("/third-party-tweets")
    private List<Tweet> getAllTweets() throws Exception {
        return Arrays.asList(
                new Tweet("RestTemplate third for demo", "@user1"),
                new Tweet("party api calls", "@user2"),
                new Tweet("test demo", "@user1"));
    }
}
