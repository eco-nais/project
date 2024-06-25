package com.eco.environet.projects.repository;

import com.eco.environet.projects.model.DocumentVersion;
import com.eco.environet.projects.model.id.DocumentVersionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, DocumentVersionId> {

    List<DocumentVersion> findByDocumentIdAndProjectId(Long documentId, Long projectId);

    @Query(value = "SELECT projects.get_next_version(:projectId, :documentId)", nativeQuery = true)
    Long getNextVersion(Long projectId, Long documentId);
}
