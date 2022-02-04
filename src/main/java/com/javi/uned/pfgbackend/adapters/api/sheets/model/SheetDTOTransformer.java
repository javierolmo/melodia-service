package com.javi.uned.pfgbackend.adapters.api.sheets.model;

import com.javi.uned.pfgbackend.domain.sheet.model.Sheet;

public class SheetDTOTransformer {

    private SheetDTOTransformer() {
    }

    public static Sheet toDomainObject(SheetDTO sheetDTO) {
        return new Sheet(
                sheetDTO.getId(),
                sheetDTO.getName(),
                sheetDTO.getDate(),
                sheetDTO.getOwnerId(),
                sheetDTO.getFinished(),
                sheetDTO.getSpecsPath(),
                sheetDTO.getXmlPath(),
                sheetDTO.getPdfPath());
    }

    public static SheetDTO toTransferObject(Sheet sheet) {
        SheetDTO sheetDTO = new SheetDTO();
        sheetDTO.setId(sheet.getId());
        sheetDTO.setName(sheet.getName());
        sheetDTO.setDate(sheet.getDate());
        sheetDTO.setOwnerId(sheet.getOwnerId());
        sheetDTO.setFinished(sheet.getFinished());
        sheetDTO.setSpecsPath(sheet.getSpecsPath());
        sheetDTO.setXmlPath(sheet.getXmlPath());
        sheetDTO.setPdfPath(sheet.getPdfPath());
        return sheetDTO;
    }
}
