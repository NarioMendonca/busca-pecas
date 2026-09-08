package com.buscapecas.app.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buscapecas.app.models.Fitment;
import com.buscapecas.app.repositories.projections.CompatiblePart;

public interface FitmentRepository extends JpaRepository<Fitment, Long> {

    @Query("""
        select f
          from Fitment f
          join fetch f.part p
          join fetch f.vehicle v
         where v.nameNorm = :vehicleNorm
           and f.yearStart <= :year
           and f.yearEnd >= :year
           and (:displacement is null or v.engineCc is null or v.engineCc = :displacement)
           and (cast(:trim as String) is null
                or v.trimNorm like concat('%', cast(:trim as String), '%'))
         order by p.productGroup asc, p.code asc, v.trim asc, f.id asc
        """)
    List<Fitment> findCompatible(
        @Param("vehicleNorm") String vehicleNorm,
        @Param("year") int year,
        @Param("displacement") Integer displacement,
        @Param("trim") String trim
    );

    @Query("""
        select p.id            as partId,
               p.code          as code,
               p.codeNorm      as codeNorm,
               p.productGroup  as productGroup,
               p.imageFile     as imageFile,
               v.trim          as trim,
               v.engineDesc    as engineDesc,
               v.engineCc      as engineCc,
               f.yearStart     as yearStart,
               f.yearEnd       as yearEnd,
               f.note          as note
          from Fitment f
          join f.part p
          join f.vehicle v
         where v.nameNorm = :vehicleNorm
           and f.yearStart <= :year
           and f.yearEnd >= :year
           and (:displacement is null or v.engineCc is null or v.engineCc = :displacement)
           and (cast(:trim as String) is null
                or v.trimNorm like concat('%', cast(:trim as String), '%'))
         order by p.productGroup asc, p.code asc, v.trim asc, f.id asc
        """)
    List<CompatiblePart> listCompatible(
        @Param("vehicleNorm") String vehicleNorm,
        @Param("year") int year,
        @Param("displacement") Integer displacement,
        @Param("trim") String trim,
        Pageable pageable
    );

    @Query("""
        select p.id            as partId,
               p.code          as code,
               p.codeNorm      as codeNorm,
               p.productGroup  as productGroup,
               p.imageFile     as imageFile,
               v.trim          as trim,
               v.engineDesc    as engineDesc,
               v.engineCc      as engineCc,
               f.yearStart     as yearStart,
               f.yearEnd       as yearEnd,
               f.note          as note
          from Fitment f
          join f.part p
          join f.vehicle v
         where v.nameNorm = :vehicleNorm
           and f.yearStart <= :year
           and f.yearEnd >= :year
           and (:displacement is null or v.engineCc is null or v.engineCc = :displacement)
           and (cast(:trim as String) is null
                or v.trimNorm like concat('%', cast(:trim as String), '%'))
           and p.productGroupNorm = :groupNorm
         order by p.code asc, v.trim asc, f.id asc
        """)
    List<CompatiblePart> listCompatibleByGroup(
        @Param("vehicleNorm") String vehicleNorm,
        @Param("year") int year,
        @Param("displacement") Integer displacement,
        @Param("trim") String trim,
        @Param("groupNorm") String groupNorm,
        Pageable pageable
    );

    @Query("""
        select p.id            as partId,
               p.code          as code,
               p.codeNorm      as codeNorm,
               p.productGroup  as productGroup,
               p.imageFile     as imageFile,
               v.trim          as trim,
               v.engineDesc    as engineDesc,
               v.engineCc      as engineCc,
               f.yearStart     as yearStart,
               f.yearEnd       as yearEnd,
               f.note          as note
          from Fitment f
          join f.part p
          join f.vehicle v
         where v.nameNorm = :vehicleNorm
           and (
                 (f.yearStart <= :buildYear and f.yearEnd >= :buildYear)
              or (f.yearStart <= :modelYear and f.yearEnd >= :modelYear)
               )
           and (:displacement is null or v.engineCc is null or v.engineCc = :displacement)
           and (cast(:trim as String) is null
                or v.trimNorm like concat('%', cast(:trim as String), '%'))
         order by p.productGroup asc, p.code asc, v.trim asc, f.id asc
        """)
    List<CompatiblePart> listCompatibleByBuildOrModelYear(
        @Param("vehicleNorm") String vehicleNorm,
        @Param("buildYear") int buildYear,
        @Param("modelYear") int modelYear,
        @Param("displacement") Integer displacement,
        @Param("trim") String trim,
        Pageable pageable
    );

    @Query("""
        select distinct p.productGroup
          from Fitment f
          join f.part p
          join f.vehicle v
         where v.nameNorm = :vehicleNorm
           and f.yearStart <= :year
           and f.yearEnd >= :year
           and p.productGroup is not null
           and (:displacement is null or v.engineCc is null or v.engineCc = :displacement)
           and (cast(:trim as String) is null
                or v.trimNorm like concat('%', cast(:trim as String), '%'))
         order by p.productGroup asc
        """)
    List<String> listCompatibleGroups(
        @Param("vehicleNorm") String vehicleNorm,
        @Param("year") int year,
        @Param("displacement") Integer displacement,
        @Param("trim") String trim
    );

    @Query("""
        select count(f)
          from Fitment f
          join f.vehicle v
         where v.nameNorm = :vehicleNorm
           and f.yearStart <= :year
           and f.yearEnd >= :year
           and (:displacement is null or v.engineCc is null or v.engineCc = :displacement)
           and (cast(:trim as String) is null
                or v.trimNorm like concat('%', cast(:trim as String), '%'))
        """)
    long countCompatible(
        @Param("vehicleNorm") String vehicleNorm,
        @Param("year") int year,
        @Param("displacement") Integer displacement,
        @Param("trim") String trim
    );

    @Query("""
        select f
          from Fitment f
          join fetch f.vehicle v
          join f.part p
         where p.codeNorm = :codeNorm
         order by v.name asc, v.trim asc, f.yearStart asc, f.id asc
        """)
    List<Fitment> findVehiclesForPart(@Param("codeNorm") String codeNorm);
}
