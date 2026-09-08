package com.buscapecas.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buscapecas.app.models.Specification;

public interface SpecificationRepository extends JpaRepository<Specification, Long> {

    List<Specification> findByPartIdOrderByNameAsc(Long partId);

    @Query("""
        SELECT s
          FROM Specification s
          JOIN s.part p
         WHERE p.codeNorm = :codeNorm
         ORDER BY s.name asc
        """)
    List<Specification> findByPartCode(@Param("codeNorm") String codeNorm);
}
