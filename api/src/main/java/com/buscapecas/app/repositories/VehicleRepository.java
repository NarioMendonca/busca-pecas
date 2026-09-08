package com.buscapecas.app.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buscapecas.app.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByNameNorm(String nameNorm);

    List<Vehicle> findByNameNormAndEngineCc(String nameNorm, Integer engineCc);

    boolean existsByNameNorm(String nameNorm);

    Optional<Vehicle> findByNameNormAndTrimNormAndEngineDesc(
        String nameNorm, String trimNorm, String engineDesc);

    @Query("""
        SELECT DISTINCT v.trim
          FROM Vehicle v
         WHERE v.nameNorm = :nameNorm
           AND v.trim <> ''
         ORDER BY v.trim ASC
        """)
    List<String> listTrims(@Param("nameNorm") String nameNorm);

    @Query("""
        SELECT DISTINCT v.engineCc
          FROM Vehicle v
         WHERE v.nameNorm = :nameNorm
           AND v.engineCc IS NOT null
         ORDER BY v.engineCc ASC
        """)
    List<Integer> listDisplacements(@Param("nameNorm") String nameNorm);

    /*fragment should be normalized */
    @Query("""
        SELECT DISTINCT v.name
          FROM Vehicle v
         WHERE v.nameNorm LIKE concat('%', :fragment, '%')
         ORDER BY v.name ASC
        """)
    List<String> findNamesContaining(@Param("fragment") String fragment);
}
