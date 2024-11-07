package br.com.pds.streaming.cloud.amazon;

import com.amazonaws.services.transcribe.AmazonTranscribe;
import com.amazonaws.services.transcribe.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class AmazonTranscriptionServices {

    @Autowired
    private S3Client s3Client;
    @Autowired
    private AmazonTranscribe transcribeClient;

    Logger log = Logger.getLogger(AmazonTranscriptionServices.class.getName());

    @Value("${cloud.aws.bucket-name}")
    private String bucketName;

    public StartTranscriptionJobResult startTranscriptionJob(String key) {
        log.info("Start Transcription Job By Key {}");
        URL url = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key));
        Media media = new Media().withMediaFileUri(url.toExternalForm());
        String jobName = key.concat(String.valueOf(UUID.randomUUID()));
        StartTranscriptionJobRequest startTranscriptionJobRequest = new StartTranscriptionJobRequest()
                .withLanguageCode(LanguageCode.PtBR).withTranscriptionJobName(jobName).withMedia(media);
        StartTranscriptionJobResult startTranscriptionJobResult = transcribeClient
                .startTranscriptionJob(startTranscriptionJobRequest);
        return startTranscriptionJobResult;
    }

    public String getTranscriptionJobUri(String jobName) {
        log.info("Getting Transcription Job Result By Job Name : {}");
        GetTranscriptionJobRequest getTranscriptionJobRequest = new GetTranscriptionJobRequest()
                .withTranscriptionJobName(jobName);

        while (true) {
            GetTranscriptionJobResult getTranscriptionJobResult = transcribeClient.getTranscriptionJob(getTranscriptionJobRequest);
            TranscriptionJob transcriptionJob = getTranscriptionJobResult.getTranscriptionJob();

            String jobStatus = transcriptionJob.getTranscriptionJobStatus();
            if (jobStatus.equalsIgnoreCase(TranscriptionJobStatus.COMPLETED.name())) {
                String transcriptUri = transcriptionJob.getTranscript().getTranscriptFileUri();
                log.info("Transcription job completed. Transcript URI: {}");
                return transcriptUri;
            } else if (jobStatus.equalsIgnoreCase(TranscriptionJobStatus.FAILED.name())) {
                log.warning("Transcription job failed.");
                return null;
            } else if (jobStatus.equalsIgnoreCase(TranscriptionJobStatus.IN_PROGRESS.name())) {
                log.info("Transcription job in progress. Waiting for completion...");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    log.warning("Interrupted Exception {}");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public String getTranscriptionText(String transcriptUri) {
        try {
            URL url = new URL(transcriptUri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            ObjectMapper objectMapper = new ObjectMapper();

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    content.append(line);
                }

                JsonNode rootNode = objectMapper.readTree(content.toString());

                return rootNode.path("results")
                        .path("transcripts")
                        .get(0)
                        .path("transcript")
                        .asText();
            }
        } catch (IOException e) {
            log.info("Error fetching transcription text from URI: {}");
            return null;
        }
    }
}
