package br.com.pds.streaming.blockfy.exceptions;

import br.com.pds.streaming.framework.exceptions.InvalidFileException;

public class InvalidAudioException extends InvalidFileException {
    
    public InvalidAudioException(String fileName) {
        super(fileName, "audio", "mp3, wav, aiff, flac, alac, ape, aac, ogg, wma, mp4, opus, midi, dsd");
    }
}
