package org.gestiondestitresimportationbcp.service;

import org.gestiondestitresimportationbcp.config.PathsProperties;
import org.gestiondestitresimportationbcp.events.FileCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.File;
import java.nio.file.*;
import java.util.Date;

@Service
public class WatchFolderServicesDefault implements WatchFolderServices {
    private final ApplicationEventPublisher eventPublisher;
    private static final Logger logger = LoggerFactory.getLogger("FilesLogger");
    @Autowired
    private PathsProperties pathsProperties;
    @Autowired
    public WatchFolderServicesDefault(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    @Override
    public void fileswatcher() {
       startAsyncWatcher();
    }
    @Async
  void startAsyncWatcher() {
        Path path = Paths.get(pathsProperties.getFiles());
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
            logger.info("Monitoring directory: " + path.toString());
            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    File file = new File(path.toAbsolutePath().toString(), event.context().toString());
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        Date dateReception = new Date(); // Capture le timestamp actuel
                        int maxRetries = 10;
                        int waitTime = 500;
                        while (maxRetries-- > 0 && !file.canRead()) {                        try {
                            Thread.sleep(waitTime); // attendre avant de réessayer
                        }
                        catch (InterruptedException e) {
                            Thread.currentThread().interrupt(); // rétablir l'état d'interruption
                            logger.error("Le thread a été interrompu pendant l'attente", e);
                            return;
                        }
                        }
                        if(file.canRead()) {
                            eventPublisher.publishEvent(new FileCreatedEvent(this, file,dateReception));
                            logger.info("File created: " + file.getName());
                        }
                    } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        logger.info("File deleted: " + file.getName());
                    }
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}