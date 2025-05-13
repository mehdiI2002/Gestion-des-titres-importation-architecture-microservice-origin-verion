package org.gestiondestitresimportationbcp.components;

import org.gestiondestitresimportationbcp.config.PathsProperties;
import org.gestiondestitresimportationbcp.service.WatchFolderServicesDefault;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DirectoriesInitializer {
    WatchFolderServicesDefault watchFolder;
PathsProperties pathsProperties;

    public DirectoriesInitializer(WatchFolderServicesDefault watchFolder, PathsProperties pathsProperties) {
        this.watchFolder = watchFolder;
        this.pathsProperties = pathsProperties;
    }

    public void createlogFile() {
        File archivedDirectory = new File(pathsProperties.getLogs());
        if (!archivedDirectory.exists()) {
            archivedDirectory.mkdirs();
        }
        File directoryFiles = new File( pathsProperties.getFiles());
        if (!directoryFiles.exists()) {
            directoryFiles.mkdirs();
        }
        File archiveDirectory = new File(pathsProperties.getArchives());
        if (!archiveDirectory.exists()) {
            archiveDirectory.mkdirs();
        }
        File repDocs = new File(pathsProperties.getDocs());
        if(!repDocs.exists()){
            repDocs.mkdirs();
        }
    }
    public File createArchiveFile(String fileName) {
        File archiveFile = new File(pathsProperties.getArchives() +"\\"+ fileName);
        return archiveFile;
    }
}
