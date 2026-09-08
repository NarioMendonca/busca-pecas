package com.buscapecas.app.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.buscapecas.app.repositories.CrossReferenceRepository;
import com.buscapecas.app.repositories.FitmentRepository;
import com.buscapecas.app.repositories.PartRepository;
import com.buscapecas.app.repositories.SpecificationRepository;
import com.buscapecas.app.repositories.VehicleRepository;

/**
 * Boots the context without a database, purely so Hibernate builds the entity
 * metamodel and Spring Data compiles every {@code @Query}.
 *
 * <p>A JPQL mistake (a field that does not exist, a wrong projection alias)
 * blows up here, at build time, instead of on the first call at runtime.
 */
@SpringBootTest(properties = {
    "spring.flyway.enabled=false",
    "spring.jpa.hibernate.ddl-auto=none",
    "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
    "spring.jpa.properties.hibernate.boot.allow_jdbc_metadata_access=false",
    "spring.datasource.url=jdbc:postgresql://localhost:1/no-database",
    "spring.datasource.username=no-database",
    "spring.datasource.password=no-database",
    "placas-api.base-url=http://localhost",
    "placas-api.token=no-token"
})
class CatalogMappingTest {

    @Autowired private FitmentRepository fitments;
    @Autowired private VehicleRepository vehicles;
    @Autowired private PartRepository parts;
    @Autowired private SpecificationRepository specifications;
    @Autowired private CrossReferenceRepository crossReferences;

    @Test
    void everyRepositoryStartsWithItsQueriesCompiled() {
        assertThat(fitments).isNotNull();
        assertThat(vehicles).isNotNull();
        assertThat(parts).isNotNull();
        assertThat(specifications).isNotNull();
        assertThat(crossReferences).isNotNull();
    }
}
