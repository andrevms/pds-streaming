package br.com.pds.streaming.cloud.services;

import br.com.pds.streaming.exceptions.InvalidSourceException;
import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudStorageService {

    String uploadFile(MultipartFile file) throws IOException;
    void deleteFile(String file) throws ObjectNotFoundException, InvalidSourceException;

}
