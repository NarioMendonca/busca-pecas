package com.buscapecas.app.services.partsDetailsService;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.buscapecas.app.models.Part;
import com.buscapecas.app.repositories.CrossReferenceRepository;
import com.buscapecas.app.repositories.FitmentRepository;
import com.buscapecas.app.repositories.PartRepository;
import com.buscapecas.app.repositories.SpecificationRepository;
import com.buscapecas.app.services.partsDetailsService.records.CrossReferenceItem;
import com.buscapecas.app.services.partsDetailsService.records.PartDetails;
import com.buscapecas.app.services.partsDetailsService.records.PartVehicleFitment;
import com.buscapecas.app.services.partsDetailsService.records.SpecificationItem;

@Service
public class PartDetailsService {

    private final PartRepository partRepository;
    private final FitmentRepository fitmentRepository;
    private final SpecificationRepository specificationRepository;
    private final CrossReferenceRepository crossReferenceRepository;

    public PartDetailsService(
        PartRepository partRepository,
        FitmentRepository fitmentRepository,
        SpecificationRepository specificationRepository,
        CrossReferenceRepository crossReferenceRepository
    ) {
        this.partRepository = partRepository;
        this.fitmentRepository = fitmentRepository;
        this.specificationRepository = specificationRepository;
        this.crossReferenceRepository = crossReferenceRepository;
    }

    public Optional<PartDetails> findDetails(Long partId) {
        return partRepository.findById(partId).map(part -> new PartDetails(
            part.getId(),
            part.getCode(),
            part.getCodeNorm(),
            part.getProductGroup(),
            part.getImageFile(),
            specificationRepository.findByPartIdOrderByNameAsc(partId).stream()
                .map(s -> new SpecificationItem(s.getName(), s.getValue()))
                .toList(),
            fitmentRepository.findVehiclesForPart(part.getCodeNorm()).stream()
                .map(f -> new PartVehicleFitment(
                    f.getVehicle().getName(),
                    f.getVehicle().getTrim(),
                    f.getVehicle().getEngineDesc(),
                    f.getVehicle().getEngineCc(),
                    f.getYearStart(),
                    f.getDisplayYearEnd(),
                    f.getNote()))
                .toList(),
            crossReferenceRepository.findByPartIdOrderByReferenceBrandAscReferenceCodeAsc(partId).stream()
                .map(r -> new CrossReferenceItem(
                    r.getReferenceBrand(), r.getReferenceCode(), r.getReferenceCodeNorm()))
                .toList()
        ));
    }
}
