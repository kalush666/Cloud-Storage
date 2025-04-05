package org.example.cloudstorage.repository;

import org.example.cloudstorage.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FIleMetadataRepository extends JpaRepository<FileMetadata,Long> {
    List<FileMetadata> findByFileIdOrderByChunkIndex(String fileId);
}
