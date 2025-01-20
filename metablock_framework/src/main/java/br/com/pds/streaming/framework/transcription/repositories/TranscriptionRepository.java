package br.com.pds.streaming.framework.transcription.repositories;

import br.com.pds.streaming.framework.transcription.model.dto.Transcription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TranscriptionRepository extends MongoRepository<Transcription, String> {
    Transcription findFirstBySource(String source);

    Transcription getTranscriptionBySource(String source);
}
