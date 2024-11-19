package com.mewsinsa.global.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class FileStoreBean {
  Logger log = LoggerFactory.getLogger(Logger.class);
  @Value("${file.dir}")
  private String fileDir;
  public String getFullPath(String fileName) {
    return fileDir + fileName;
  }

  public List<String> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
    List<String> storeFileResult = new ArrayList<>();
    for(MultipartFile multipartFile : multipartFiles) {
      if(!multipartFile.isEmpty()) {
        storeFileResult.add(storeFile(multipartFile));
      }
    }

    return storeFileResult;
  }

  public String storeFile(MultipartFile multipartFile) throws IOException {
    if(multipartFile.isEmpty()) {
      return null;
    }

    String originalFilename = multipartFile.getOriginalFilename();
    String storeFileName = createStoreFileName(originalFilename);

    log.info("line 40: " + originalFilename + " " + storeFileName);
    multipartFile.transferTo(new File(getFullPath(storeFileName)));

    return storeFileName;
  }

  private String createStoreFileName(String originalFilename) {
    String ext = extractExt(originalFilename);
    String uuid = UUID.randomUUID().toString();
    return uuid + "." + ext;
  }

  private String extractExt(String originalFilename) {
    int pos = originalFilename.lastIndexOf(".");
    return originalFilename.substring(pos+1);
  }

}
