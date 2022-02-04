package com.javi.uned.pfgbackend.domain.ports.database;

import com.javi.uned.pfgbackend.domain.exceptions.EntityNotFound;
import com.javi.uned.pfgbackend.domain.sheet.model.Sheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface SheetDAO {

    List<Sheet> findAll();

    Sheet save(Sheet sheet);

    Sheet findById(long id) throws EntityNotFound;

    void deleteById(long id);

    Sheet markAsFinished(long id) throws EntityNotFound;

    List<Sheet> findBy(Long id, String nameContains, Long ownerId, Boolean finished);

    Page<Sheet> getSheetPage(PageRequest pageRequest, String name);

    Sheet updateSpecsPath(long id, String path) throws EntityNotFound;

    Sheet updateXMLPath(long id, String path) throws EntityNotFound;

    Sheet updatePDFPath(long id, String path) throws EntityNotFound;

    Sheet setFinished(long id, boolean finished) throws EntityNotFound;
}
