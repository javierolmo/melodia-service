package com.javi.uned.pfgbackend.adapters.database.sheet;

import com.javi.uned.pfgbackend.domain.sheet.model.Sheet;

public class SheetEntityTransformer {

    private SheetEntityTransformer() {

    }

    public static Sheet toDomainObject(SheetEntity sheetEntity) {

        return new Sheet(
                sheetEntity.getId(),
                sheetEntity.getName(),
                sheetEntity.getDate(),
                sheetEntity.getOwnerId(),
                sheetEntity.getFinished(),
                sheetEntity.getSpecsPath(),
                sheetEntity.getXmlPath(),
                sheetEntity.getPdfPath());
    }

    public static SheetEntity toEntity(Sheet sheet) {
        SheetEntity sheetEntity = new SheetEntity();
        sheetEntity.setId(sheet.getId());
        sheetEntity.setName(sheet.getName());
        sheetEntity.setDate(sheet.getDate());
        sheetEntity.setOwnerId(sheet.getOwnerId());
        sheetEntity.setFinished(sheet.getFinished());
        if (sheetEntity.getFinished() == null) sheetEntity.setFinished(false);
        sheetEntity.setSpecsPath(sheet.getSpecsPath());
        sheetEntity.setXmlPath(sheet.getXmlPath());
        sheetEntity.setPdfPath(sheet.getPdfPath());
        return sheetEntity;
    }

}
