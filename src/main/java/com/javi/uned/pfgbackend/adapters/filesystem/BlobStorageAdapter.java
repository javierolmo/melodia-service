package com.javi.uned.pfgbackend.adapters.filesystem;

import com.javi.uned.pfgbackend.domain.exceptions.MelodiaFileSystemException;
import com.javi.uned.pfgbackend.domain.ports.filesystem.FileFormat;
import com.javi.uned.pfgbackend.domain.ports.filesystem.FileSystem;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.logging.Logger;

public class BlobStorageAdapter implements FileSystem {

    private static final Logger log = Logger.getLogger(BlobStorageAdapter.class.getName());

    private CloudBlobClient blobClient;
    private CloudBlobContainer container;

    /**
     * Constructor. Initializes the BlobStorageAdapter. It creates a connection to the Azure Blob Storage. It also
     * creates a container in the Blob Storage if it does not exist.
     * @param containerRef The container where the files will be stored. It must be one of the values of the enum
     * @throws MelodiaFileSystemException If the initialization fails.
     */
    public BlobStorageAdapter(String connectionString, Containers containerRef) throws MelodiaFileSystemException {
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(connectionString);
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference(containerRef.getName());
            container.createIfNotExists();

            log.info("BlobStorageAdapter initialized successfully");
        } catch (URISyntaxException | InvalidKeyException | StorageException e) {
            log.info("BlobStorageAdapter initialization failed");
            throw new MelodiaFileSystemException("Error initializing BlobStorageAdapter", e);
        }
    }


    /**
     * Stores a file in the blob storage. The file is stored in the container specified in the constructor.
     * @param id The id of the sheet. It is used to generate the name of the file.
     * @param inputStream The input stream of the file.
     * @param format The format of the file. It is used to generate the name of the file.
     * @return The path of the file in the blob storage.
     * @throws MelodiaFileSystemException If an error occurs while storing the file.
     */
    @Override
    public String saveSheetFile(long id, InputStream inputStream, FileFormat format) throws MelodiaFileSystemException {

        String targetPath = String.format("%d/%d.%s", id, id, format.getExtension());

        try {
            CloudBlockBlob blockBlob = container.getBlockBlobReference(targetPath);
            blockBlob.upload(inputStream, inputStream.available());
            inputStream.close();
            log.info("File " + targetPath + " stored successfully");
            return targetPath;
        } catch (URISyntaxException | StorageException | IOException e) {
            log.info("File " + targetPath + " storage failed");
            throw new MelodiaFileSystemException("Error storing file", e);
        }
    }

    /**
     *
     * @param id The id of the sheet.
     * @param format The format of the file.
     * @return The input stream of the file. It must be closed by the caller.
     * @throws MelodiaFileSystemException If an error occurs while getting the file.
     */
    public InputStream readFile(long id, FileFormat format) throws MelodiaFileSystemException {
        String targetPath = String.format("%d/%d.%s", id, id, format.getExtension());
        try {
            CloudBlockBlob blockBlob = container.getBlockBlobReference(targetPath);
            return blockBlob.openInputStream();
        } catch (URISyntaxException | StorageException e) {
            log.info("File " + targetPath + " read failed");
            throw new MelodiaFileSystemException("Error reading file", e);
        }
    }


    @Override
    public boolean deleteSheetFolder(long sheetId) {
        return false;
    }
}
