package com.buscapecas.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buscapecas.app.models.Part;

public interface PartRepository extends JpaRepository<Part, Long> {

    // should use normalized code
    Optional<Part> findByCodeNorm(String codeNorm);

    @Query("""
        SELECT DISTINCT p
          FROM Part p
         WHERE p.codeNorm = :codeNorm
            OR EXISTS (
                 SELECT 1
                   FROM CrossReference r
                  WHERE r.part = p
                    AND r.referenceCodeNorm = :codeNorm
               )
         ORDER BY p.code ASC
        """)
    List<Part> findByCodeOrCrossReference(@Param("codeNorm") String codeNorm);

    @Query("""
        SELECT p
          FROM Part p
         WHERE p.codeNorm LIKE concat(:prefix, '%')
         ORDER BY p.code ASC
        """)
    List<Part> findByCodePrefix(@Param("prefix") String prefix, Pageable pageable);

    List<Part> findByProductGroupNormOrderByCodeAsc(String productGroupNorm, Pageable pageable);
}
