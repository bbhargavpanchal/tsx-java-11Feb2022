package com.internship.GitAPI.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WebhookService {
    private final Logger logger = LoggerFactory.getLogger(GitHubCommitService.class);
    private final OkHttpClient client = new OkHttpClient();

    public void retrieveCommits() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/bbhargavpanchal/dummy-github-event/commits")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to retrieve commits: " + response);
            }

            String commits = response.body().string();
            logger.info("Retrieved commits: " + commits);
        }
    }
}

