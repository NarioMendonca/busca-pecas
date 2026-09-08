package com.buscapecas.app.catalog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buscapecas.app.catalog.entities.CrossReference;

public interface CrossReferenceRepository extends JpaRepository<CrossReference, Long> {

    List<CrossReference> findByReferenceCodeNorm(String referenceCodeNorm);

    // Every equivalent code for a part
    List<CrossReference> findByPartIdOrderByReferenceBrandAscReferenceCodeAsc(Long partId);
}
