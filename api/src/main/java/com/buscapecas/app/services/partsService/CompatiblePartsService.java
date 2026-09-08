package com.buscapecas.app.services.partsService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.buscapecas.app.repositories.FitmentRepository;
import com.buscapecas.app.repositories.VehicleRepository;
import com.buscapecas.app.repositories.projections.CompatiblePart;
import com.buscapecas.app.utils.TextNormalizer;


@Service
public class CompatiblePartsService {

    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2100;

    private final FitmentRepository fitmentRepository;
    private final VehicleRepository vehicleRepository;

    public CompatiblePartsService(FitmentRepository fitmentRepository, VehicleRepository vehicleRepository) {
        this.fitmentRepository = fitmentRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public PartSearchResult search(PartSearchCriteria criteria, Pageable pageable) {
        validate(criteria);
        String vehicleNorm = TextNormalizer.name(criteria.vehicleName());

        boolean vehicleFound = vehicleRepository.existsByNameNorm(vehicleNorm);
        List<CompatiblePart> rows = filterByGroup(fetchRows(criteria, vehicleNorm), criteria.productGroup());

        List<CompatiblePartResult> allParts = groupByPart(rows);
        return new PartSearchResult(vehicleFound, page(allParts, pageable), allParts.size());
    }

    public List<String> listAvailableGroups(PartSearchCriteria criteria) {
        validate(criteria);
        String vehicleNorm = TextNormalizer.name(criteria.vehicleName());
        List<CompatiblePart> rows = fetchRows(criteria, vehicleNorm);

        return rows.stream()
            .map(CompatiblePart::getProductGroup)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .toList();
    }

    private void validate(PartSearchCriteria criteria) {
        if (criteria.vehicleName() == null || criteria.vehicleName().isBlank()) {
            throw new IllegalArgumentException("O nome do veículo é obrigatório.");
        }
        if (criteria.buildYear() == null && criteria.modelYear() == null) {
            throw new IllegalArgumentException("Informe o ano de fabricação ou o ano do modelo.");
        }
        if (isOutOfRange(criteria.buildYear()) || isOutOfRange(criteria.modelYear())) {
            throw new IllegalArgumentException("Ano inválido.");
        }
    }

    private static boolean isOutOfRange(Integer year) {
        return year != null && (year < MIN_YEAR || year > MAX_YEAR);
    }

    private List<CompatiblePart> fetchRows(PartSearchCriteria criteria, String vehicleNorm) {
        String trimNorm = criteria.trim() == null || criteria.trim().isBlank()
            ? null
            : TextNormalizer.name(criteria.trim());
        Integer buildYear = criteria.buildYear();
        Integer modelYear = criteria.modelYear();

        if (buildYear != null && modelYear != null) {
            return fitmentRepository.listCompatibleByBuildOrModelYear(
                vehicleNorm, buildYear, modelYear, criteria.displacement(), trimNorm, Pageable.unpaged());
        }
        int year = buildYear != null ? buildYear : modelYear;
        return fitmentRepository.listCompatible(
            vehicleNorm, year, criteria.displacement(), trimNorm, Pageable.unpaged());
    }

    private List<CompatiblePart> filterByGroup(List<CompatiblePart> rows, String productGroup) {
        if (productGroup == null || productGroup.isBlank()) {
            return rows;
        }
        String groupNorm = TextNormalizer.name(productGroup);
        return rows.stream()
            .filter(r -> groupNorm.equals(TextNormalizer.name(r.getProductGroup())))
            .toList();
    }

    private List<CompatiblePartResult> groupByPart(List<CompatiblePart> rows) {
        Map<Long, List<CompatiblePart>> byPart = new LinkedHashMap<>();
        for (CompatiblePart row : rows) {
            byPart.computeIfAbsent(row.getPartId(), id -> new ArrayList<>()).add(row);
        }

        List<CompatiblePartResult> results = new ArrayList<>();
        for (List<CompatiblePart> group : byPart.values()) {
            CompatiblePart first = group.get(0);
            List<FitmentOption> fitments = group.stream()
                .map(r -> new FitmentOption(r.getTrim(), r.getEngineDesc(), r.getEngineCc(),
                    r.getYearStart(), r.getYearEnd(), r.getNote()))
                .toList();
            results.add(new CompatiblePartResult(
                first.getPartId(), first.getCode(), first.getCodeNorm(),
                first.getProductGroup(), first.getImageFile(), fitments));
        }
        return results;
    }

    private static <T> List<T> page(List<T> items, Pageable pageable) {
        if (pageable.isUnpaged()) {
            return items;
        }
        int from = Math.min((int) pageable.getOffset(), items.size());
        int to = Math.min(from + pageable.getPageSize(), items.size());
        return items.subList(from, to);
    }
}
