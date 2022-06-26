package com.javi.uned.pfgbackend.domain.ports.filesystem;

import com.javi.uned.pfgbackend.domain.exceptions.MelodiaFileSystemException;

import java.io.InputStream;
import java.io.OutputStream;

public interface FileSystem {

    String saveSheetFile(long id, InputStream inputStrea, FileFormat format) throws MelodiaFileSystemException;

    boolean deleteSheetFolder(long sheetId);

    InputStream readFile(long id, FileFormat format) throws MelodiaFileSystemException;



}
