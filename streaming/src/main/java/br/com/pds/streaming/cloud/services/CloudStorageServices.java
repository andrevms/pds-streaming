package br.com.pds.streaming.cloud.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudStorageServices {

    String uploadVideo(MultipartFile file) throws IOException;

}
