package com.javi.uned.pfgbackend.adapters.filesystem;

import com.javi.uned.pfgbackend.domain.exceptions.FileServiceException;
import com.javi.uned.pfgbackend.domain.ports.filesystem.FileFormat;
import com.javi.uned.pfgbackend.domain.ports.filesystem.FileService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileServiceImpl implements FileService {

    public static final String DATA_FOLDER_PATH = "data";
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private final File baseDirectory = new File(DATA_FOLDER_PATH);

    public FileServiceImpl() {
        if(baseDirectory.isDirectory()) {
            logger.info("Utilizando sistema de archivos ya existente: {}", baseDirectory.getAbsolutePath());
        } else {
            baseDirectory.mkdirs();
            logger.warn("No se ha encontrado una estructura de ficheros previa. Se ha inicializado una nueva: {}", baseDirectory.getAbsolutePath());
        }
    }

    public File getSheetFolder(long sheetId) {
        File result = new File(baseDirectory, ""+sheetId);
        if(!result.isDirectory()){
            result.mkdir();
        }
        return result;
    }

    public boolean deleteSheetFolder(long sheetId) {
        File folder = new File(baseDirectory, "" + sheetId);
        if (folder.exists()) {
            return folder.delete();
        } else {
            return true;
        }
    }

    public String saveSheetFile(Long id, InputStream inputStream, FileFormat format) throws FileServiceException {

        File folder = getSheetFolder(id);
        File destFile = new File(folder, String.format("%d.%s", id, format.name().toLowerCase()));

        try(OutputStream outputStream = new FileOutputStream(destFile)) {
            IOUtils.copy(inputStream, outputStream);
        } catch (FileNotFoundException e) {
            throw new FileServiceException("Could not find destination file", e);
        } catch (IOException e) {
            throw new FileServiceException("Could not copy file");
        }

        return destFile.getAbsolutePath();
    }

    public File baseDirectory() {
        return baseDirectory;
    }

}
