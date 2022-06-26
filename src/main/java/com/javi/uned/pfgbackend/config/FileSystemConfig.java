package com.javi.uned.pfgbackend.config;

import com.javi.uned.pfgbackend.adapters.filesystem.BlobStorageAdapter;
import com.javi.uned.pfgbackend.adapters.filesystem.Containers;
import com.javi.uned.pfgbackend.domain.exceptions.MelodiaFileSystemException;
import com.javi.uned.pfgbackend.domain.ports.filesystem.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileSystemConfig {

    @Value("${BLOB_CONNECTION_STRING:DefaultEndpointsProtocol=https;AccountName=melodiabs;AccountKey=XWXIoOJdLY2tb2dE2fep3i20SSNDByULxkN7HBXFW/gDDvnJCvYL67Fp2Uel8ttrbrqIB7tRWbVF+ASt+UILrw==;EndpointSuffix=core.windows.net}")
    private String connectionString;

    @Bean
    public FileSystem fileSystem() {
        try {
            return new BlobStorageAdapter(connectionString, Containers.SHEETS);
        } catch (MelodiaFileSystemException e) {
            throw new RuntimeException(e);
        }
    }

}
