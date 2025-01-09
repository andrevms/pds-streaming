package br.com.pds.streaming.framework.cloud.services;

import br.com.pds.streaming.framework.exceptions.InvalidSourceException;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudStorageService {

    String uploadFile(MultipartFile file) throws IOException;
    void deleteFile(String file) throws EntityNotFoundException, InvalidSourceException;

}
