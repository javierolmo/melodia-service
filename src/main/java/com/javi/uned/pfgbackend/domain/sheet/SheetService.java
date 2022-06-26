package com.javi.uned.pfgbackend.domain.sheet;

import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.pfgbackend.domain.exceptions.*;
import com.javi.uned.pfgbackend.domain.ports.database.RequestDAO;
import com.javi.uned.pfgbackend.domain.ports.database.SheetDAO;
import com.javi.uned.pfgbackend.domain.ports.filesystem.FileFormat;
import com.javi.uned.pfgbackend.domain.ports.filesystem.FileSystem;
import com.javi.uned.pfgbackend.domain.ports.messagebroker.MessageBrokerGeneticComposer;
import com.javi.uned.pfgbackend.domain.sheet.model.Request;
import com.javi.uned.pfgbackend.domain.sheet.model.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.List;

@Service
public class SheetService {

    private static final Logger logger = LoggerFactory.getLogger(SheetService.class);

    @Autowired
    private SheetDAO sheetDAO;
    @Autowired
    private RequestDAO requestDAO;
    @Autowired
    private FileSystem fileSystem;
    @Autowired
    private KafkaTemplate<String, ScoreSpecs> retryXmlTemplate;
    @Autowired
    private MessageBrokerGeneticComposer messageBrokerGeneticComposer;

    public Sheet save(Sheet sheet) {
        return this.sheetDAO.save(sheet);
    }

    public Sheet getSheet(int id) throws EntityNotFound {
        return this.sheetDAO.findById(id);
    }

    public void delete(int id) {
        sheetDAO.deleteById(id);
        fileSystem.deleteSheetFolder(id);
    }


    public void retry(int id) throws RetryException {
        /* TODO: Implementar
        try {
            File specsFile = new File(fileSystem.getSheetFolder(id), "specs.json");
            Sheet sheet = sheetDAO.findById(id);

            if (specsFile.exists()) {
                File xml = new File(fileSystem.getSheetFolder(id), id + Formats.MUSICXML);
                File pdf = new File(fileSystem.getSheetFolder(id), id + Formats.PDF);

                if (xml.exists() && pdf.exists()) {
                    throw new RetryException("Omitido el reintento. Ya existen el xml y el pdf");
                }

                if (!xml.exists()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    GeneticSpecs specs = objectMapper.readValue(specsFile, GeneticSpecs.class);
                    String sheetid = "" + id;
                    retryXmlTemplate.send("melodia.backend.retryxml", sheetid, specs);
                    sheetDAO.markAsFinished(id);
                }

                if (!pdf.exists()) {
                    messageBrokerGeneticComposer.orderPDFConversion(""+id, xml);
                    sheetDAO.markAsFinished(id);
                }

            } else {
                throw new RetryException("Imposible reconstruir. No se han encontrado las especificaciones. Eliminar partitura o contactar con administrador");
            }

        } catch (IOException | MelodiaIOException ioe) {
            throw new RetryException("Error retrying because of IO error", ioe);
        } catch (EntityNotFound entityNotFound) {
            //TODO:
        }

     */
    }



    public Page<Sheet> getSheetPage(PageRequest pageRequest, String name) {
        return sheetDAO.getSheetPage(pageRequest, name);
    }

    public List<Sheet> findBy(Long id, String nameContains, Long ownerId, Boolean finished) {
        return this.sheetDAO.findBy(id, nameContains, ownerId, finished);
    }

    public Sheet findById(Long id) throws EntityNotFound {
        return this.sheetDAO.findById(id);
    }

    public void uploadSpecs(long id, InputStream inputStream) throws EntityNotFound, MelodiaFileSystemException {

        // Find sheet
        Sheet sheet = findById(id);

        // Save sheet in filesystem
        String path = fileSystem.saveSheetFile(id, inputStream, FileFormat.JSON);

        // Update specs path
        sheetDAO.updateSpecsPath(id, path);

    }

    public void uploadXml(long id, InputStream inputStream) throws EntityNotFound, MelodiaFileSystemException {

        // Find sheet
        Sheet sheet = findById(id);

        // Save sheet in filesystem
        String path = fileSystem.saveSheetFile(id, inputStream, FileFormat.MUSICXML);

        // Update specs path
        sheetDAO.updateXMLPath(id, path);

    }

    public void uploadPdf(long id, InputStream inputStream) throws EntityNotFound, MelodiaFileSystemException {

        // Find sheet
        Sheet sheet = findById(id);

        // Save sheet in filesystem
        String path = fileSystem.saveSheetFile(id, inputStream, FileFormat.PDF);

        // Update specs path
        sheet = sheetDAO.updatePDFPath(id, path);
        refreshFinished(sheet);

    }

    private void refreshFinished(Sheet sheet) throws EntityNotFound {

        // Calculate finished value
        boolean finished = sheet.getSpecsPath() != null && sheet.getSpecsPath().length() > 0;
        finished = sheet.getXmlPath() != null && sheet.getXmlPath().length() > 0 && finished;
        finished = sheet.getPdfPath() != null && sheet.getPdfPath().length() > 0 && finished;

        // Compare with current finished value
        if(finished != sheet.getFinished()) {
            sheetDAO.setFinished(sheet.getId(), finished);
        }
    }

    public Request submitSheetRequest(Request request) {
        return requestDAO.save(request);
    }

}
