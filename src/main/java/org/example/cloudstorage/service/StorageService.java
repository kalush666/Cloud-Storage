package org.example.cloudstorage.service;

import org.example.cloudstorage.model.FileMetadata;
import org.example.cloudstorage.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class StorageService {
    private final FileMetadataRepository fileMetadataRepository;

    @Value("${storage.folder:uploads}")
    private String storageFolder;

    public StorageService(FileMetadataRepository fileMetadataRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
    }

    public void storeChunk(String fileId, String originalName, int chunkIndex, MultipartFile chunk) throws IOException {
        File dir = new File(storageFolder);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String chunkFileName = fileId + "_chunk" + chunkIndex;
        File dest = new File(dir, chunkFileName);
        chunk.transferTo(dest);

        FileMetadata meta = new FileMetadata();
        meta.setFileId(fileId);
        meta.setOriginalName(originalName);
        meta.setChunkIndex(chunkIndex);
        meta.setChunkPath(dest.getAbsolutePath());

        fileMetadataRepository.save(meta);
    }
}
