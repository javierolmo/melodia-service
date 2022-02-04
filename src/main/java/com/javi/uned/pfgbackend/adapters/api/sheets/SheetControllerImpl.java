package com.javi.uned.pfgbackend.adapters.api.sheets;

import com.javi.uned.pfgbackend.adapters.api.RestException;
import com.javi.uned.pfgbackend.adapters.api.sheets.model.SheetDTO;
import com.javi.uned.pfgbackend.adapters.api.sheets.model.SheetDTOTransformer;
import com.javi.uned.pfgbackend.adapters.filesystem.FileServiceImpl;
import com.javi.uned.pfgbackend.domain.enums.Formats;
import com.javi.uned.pfgbackend.domain.exceptions.EntityNotFound;
import com.javi.uned.pfgbackend.domain.exceptions.FileServiceException;
import com.javi.uned.pfgbackend.domain.exceptions.RetryException;
import com.javi.uned.pfgbackend.domain.ports.filesystem.FileFormat;
import com.javi.uned.pfgbackend.domain.sheet.SheetService;
import com.javi.uned.pfgbackend.domain.sheet.model.Sheet;
import io.swagger.annotations.Api;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@Api(tags = "Sheets")
public class SheetControllerImpl implements SheetController {

    @Autowired
    private SheetService sheetService;
    @Autowired
    private FileServiceImpl fileServiceImpl;
    private Logger logger = LoggerFactory.getLogger(SheetControllerImpl.class);

    public Page<SheetDTO> getSheets(int page, int size, String text) {

        // Request page
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Sheet> sheetPage = sheetService.getSheetPage(pageRequest, text);

        // Transform to DTO and return
        return sheetPage.map(sheet -> SheetDTOTransformer.toTransferObject(sheet));
    }

    public List<SheetDTO> getSheets(String nameContains, Boolean finished, Long ownerId, Long id) {

        // Querying by parameters
        List<Sheet> sheets = sheetService.findBy(id, nameContains, ownerId, finished);

        // Building DTOs and return
        return sheets.stream()
                .map(sheet1 -> SheetDTOTransformer.toTransferObject(sheet1))
                .collect(Collectors.toList());
    }

    public SheetDTO createSheet(SheetDTO sheetDTO) {
        Sheet sheet = SheetDTOTransformer.toDomainObject(sheetDTO);
        sheet = sheetService.save(sheet);
        return SheetDTOTransformer.toTransferObject(sheet);
    }

    public SheetDTO sheet(Integer id) throws EntityNotFound {

        Sheet sheet = sheetService.getSheet(id);

        SheetDTO sheetDTO = SheetDTOTransformer.toTransferObject(sheet);
        return sheetDTO;
    }

    public String deleteSheet(int id) {
        sheetService.delete(id);
        return "Partitura eliminada con éxito";
    }

    @Override
    public String uploadFile(long id, String format, MultipartFile multipartFile) throws EntityNotFound, IOException, FileServiceException {

        // Parse format
        FileFormat fileFormat = FileFormat.valueOf(format.toUpperCase());
        if(fileFormat == null ){
            throw new RestException(
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    String.format("Format not supported. Available formats: %s.",
                            Arrays.stream(FileFormat.values()).map(f -> f.name().toLowerCase()).collect(Collectors.joining(", "))));

        }

        // Store file
        switch (fileFormat) {
            case JSON: sheetService.uploadSpecs(id, multipartFile.getInputStream()); break;
            case MUSICXML: sheetService.uploadXml(id, multipartFile.getInputStream()); break;
            case PDF: sheetService.uploadPdf(id, multipartFile.getInputStream()); break;
        }

        return "Archivo subido con éxito";
    }

    public ResponseEntity visualizePDF(int id) {

        try {
            // File
            File file = sheetService.pdfFile(id);
            byte[] bytes = FileUtils.readFileToByteArray(file);

            // Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName());
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error trying to read pdf");
        }
    }

    public ResponseEntity visualizeXML(int id) {

        try {
            // File
            File file = sheetService.xmlFile(id);
            byte[] bytes = FileUtils.readFileToByteArray(file);

            // Headers
            HttpHeaders headers = new HttpHeaders();
            //headers.setContentType(MediaType.APPLICATION_XML); Creo que no haría falta esto
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName());
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error trying to read musicxml");
        }


    }

    public ResponseEntity visualizeSpecs(int id) {
        try {
            // File
            File file = sheetService.specsFile(id);
            byte[] bytes = FileUtils.readFileToByteArray(file);

            // Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName());
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<String> retry(int id) {
        try {
            sheetService.retry(id);
            return ResponseEntity.ok("Retry scheduled successfuly");
        } catch (RetryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity downloadFileXML(int id) {
        try {
            Sheet sheet = sheetService.getSheet(id);
            File file = sheetService.xmlFile(id);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + sheet.getName() + Formats.MUSICXML + "\"")
                    .contentLength(file.length())
                    //.contentType(MediaType.APPLICATION_OCTET_STREAM) creo que ya no hace falta, lo puse por anotación en la interfaz...
                    .body(resource);
        } catch (EntityNotFound enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enf.getMessage());
        } catch (FileNotFoundException fnf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find file in system storage");
        }
    }

    public ResponseEntity downloadFilePDF(int id) {
        try {
            Sheet sheet = sheetService.getSheet(id);
            File file = sheetService.pdfFile(id);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + sheet.getName() + Formats.PDF + "\"")
                    .contentLength(file.length())
                    //.contentType(MediaType.APPLICATION_OCTET_STREAM) creo que ya no hace falta, lo puse por anotación en la interfaz...
                    .body(resource);
        } catch (EntityNotFound enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enf.getMessage());
        } catch (FileNotFoundException fnf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find file in system storage");
        }
    }
}
