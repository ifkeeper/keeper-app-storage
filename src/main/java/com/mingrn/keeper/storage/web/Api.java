package com.mingrn.keeper.storage.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Iterator;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class Api {

    @Value("${storage.real.path}")
    private String storageRealPath;


    private static final Logger LOGGER = LoggerFactory.getLogger(Api.class);

    @PostMapping("/upload")
    public void upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        while (iterator.hasNext()) {
            String originalPath = iterator.next();
            MultipartFile multipartFile = multipartHttpServletRequest.getFile(originalPath);
            if (multipartFile == null || multipartFile.isEmpty()) {
                continue;
            }
            int len;
            byte[] bytes = new byte[1024];
            String originalFileName = multipartFile.getOriginalFilename();
            String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();

            String newFile = UUID.randomUUID() + "." + ext;
            LOGGER.info("new file name: {}", newFile);
            try (OutputStream outputStream = new FileOutputStream(storageRealPath + newFile);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(multipartFile.getInputStream())) {
                while ((len = bufferedInputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }
                outputStream.flush();
                String netUrl = "localhost:8003/store/" + newFile;
                LOGGER.info("new file net url: {}", netUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
