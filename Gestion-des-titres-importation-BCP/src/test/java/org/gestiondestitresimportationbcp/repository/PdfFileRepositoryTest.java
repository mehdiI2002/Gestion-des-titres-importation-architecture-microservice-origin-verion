package org.gestiondestitresimportationbcp.repository;

import org.gestiondestitresimportationbcp.components.DirectoriesInitializer;
import org.gestiondestitresimportationbcp.entities.PdfFile;
import org.gestiondestitresimportationbcp.repositories.PdfFileRepository;
import org.gestiondestitresimportationbcp.service.WatchFolderServicesDefault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class PdfFileRepositoryTest {
    @MockBean
    private WatchFolderServicesDefault watchFolderServicesDefault;
    @MockBean
    private DirectoriesInitializer directoriesInitializer;

    @Autowired
    private PdfFileRepository pdfFileRepository;

    @BeforeEach
    public void setUp() {
        PdfFile file1 = new PdfFile();
        file1.setNumeroEnregistrement(123456789L);
        file1.setEtat("non traiter");
        file1.setFilePath("/documents/facture1.pdf");
        file1.setNom("facture1.pdf");
        pdfFileRepository.save(file1);
        PdfFile file2 = new PdfFile();
        file2.setNumeroEnregistrement(12345678L);
        file2.setEtat("non traiter");
        file2.setFilePath("/documents/facture2.pdf");
        file2.setNom("facture2.pdf");
        pdfFileRepository.save(file2);
    }
    @Test
    public void findPathByNumeroEnregistrementTest() {
        List<String> file = pdfFileRepository.findPathByNumeroEnregistrement(123456789L);
        assertEquals("/documents/facture1.pdf", file.get(0));
    }
    @Test
    public void findPdfAllFileByNomTest() {
        List<PdfFile> pdfFiles = pdfFileRepository.findPdfAllFileByNom("facture1.pdf");
        assertEquals(1, pdfFiles.size());
        assertEquals("facture1.pdf", pdfFiles.get(0).getNom());
    }
}
