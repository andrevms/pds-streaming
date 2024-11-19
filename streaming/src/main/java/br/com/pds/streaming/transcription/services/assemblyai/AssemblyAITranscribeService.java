package br.com.pds.streaming.transcription.services.assemblyai;

import com.assemblyai.api.AssemblyAI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssemblyAITranscribeService {

    @Autowired
    private AssemblyAI assemblyAI;

}
