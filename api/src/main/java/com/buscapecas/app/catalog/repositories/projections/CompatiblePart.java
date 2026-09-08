package com.buscapecas.app.catalog.repositories.projections;

public interface CompatiblePart {

    Long getPartId();

    String getCode();

    String getCodeNorm();

    String getProductGroup();

    String getImageFile();

    String getTrim();

    String getEngineDesc();

    Integer getEngineCc();

    Short getYearStart();

    Short getYearEnd();

    String getNote();
}
