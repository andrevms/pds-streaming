package br.com.pds.streaming.transcription.repository;

import br.com.pds.streaming.transcription.model.dto.Transcription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TranscriptionRepository extends MongoRepository<Transcription, String> {
    Transcription findFirstBySource(String source);

    Transcription getTranscriptionBySource(String source);
}
