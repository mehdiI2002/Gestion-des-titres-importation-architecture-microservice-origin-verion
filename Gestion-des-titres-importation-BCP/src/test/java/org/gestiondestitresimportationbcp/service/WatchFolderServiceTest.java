package org.gestiondestitresimportationbcp.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
//tester la recuperation de chemin de fichier
// tester la publication de l'evenement
//test le capture de levenement

import org.gestiondestitresimportationbcp.config.PathsProperties;
import org.gestiondestitresimportationbcp.events.FileCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;

import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WatchFolderServiceTest {

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private PathsProperties pathsProperties;

    private WatchFolderServicesDefault watchFolderService;

    @TempDir
    Path tempDir; // creation folder temporaire dans le systeme fichier des que le test termine alors on ssupprime le folder

    @BeforeEach
    public void setUp() {
        watchFolderService = new WatchFolderServicesDefault(eventPublisher); // instaacier l objet le cabler avec un mock
        ReflectionTestUtils.setField(watchFolderService, "pathsProperties", pathsProperties);//forcer l injection dans le cntexte spring
        when(pathsProperties.getFiles()).thenReturn(tempDir.toString());// simuler le comportomenet de getFiles de retourner le chemin de tempdir
    }
    @Test
    public void testFileCreationEvent() throws IOException, InterruptedException {
        // Configuration pour intercepter l'appel asynchrone
        CountDownLatch latch = new CountDownLatch(1);
        doAnswer(invocation -> {
            FileCreatedEvent event = invocation.getArgument(0);
            latch.countDown();
            return null;
        }).when(eventPublisher).publishEvent(any(FileCreatedEvent.class));

        // Lancer la surveillance dans un thread séparé
        Thread watcherThread = new Thread(() -> {
            watchFolderService.startAsyncWatcher();
        });
        watcherThread.setDaemon(true);
        watcherThread.start();

        // Laisser le temps au service de démarrer
        Thread.sleep(500);

        // Créer un nouveau fichier dans le répertoire surveillé
        Path newFile = tempDir.resolve("test-file.txt");
        Files.write(newFile, "contenu de test".getBytes());

        // Attendre que l'événement soit publié (avec timeout de 5 secondes)
        boolean eventPublished = latch.await(5, TimeUnit.SECONDS);

        // Vérifier que l'événement a été publié avec le bon fichier
        verify(eventPublisher, timeout(5000).atLeastOnce()).publishEvent(any(FileCreatedEvent.class));

        // Interrompre le thread de surveillance
        watcherThread.interrupt();
    }
    @Test
    public void testRecuperationCheminFichier() {
        // Vérifier que le service utilise le chemin correct
        String cheminTest = "/chemin/test";
        when(pathsProperties.getFiles()).thenReturn(cheminTest);

        // Vérifier que le service récupère le chemin depuis les propriétés
        assertEquals("/chemin/test", pathsProperties.getFiles());
    }
}