package org.gestiondestitresimportationbcp.service;

import org.gestiondestitresimportationbcp.entities.PdfFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.gestiondestitresimportationbcp.repositories.PdfFileRepository;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VerifyPdfServiceTest {
    private List<PdfFile> pdfFileList1;
    private List<PdfFile> pdfFileList2;
    private PdfFile pdfFile1;
    private PdfFile pdfFile2;
    @InjectMocks
    private VerifyPdfService verifyPdfService;
    @Mock
    private PdfFileRepository pdfFileRepository;
    @BeforeEach
    public void setUp() {
        pdfFile1 = new PdfFile(null, "/test.pdf", 12L, "non traité", "test.pdf");
        pdfFile2 = new PdfFile(null, "/test/test.pdf", 13L, "non traité", "test.pdf");
        pdfFileList1 = new ArrayList<>();
        pdfFileList2 = new ArrayList<>();
    }
     @Test
     public void verifyPdfTestWhenListIsFull() {
         pdfFileList1.add(pdfFile1);
         when(pdfFileRepository.findPdfAllFileByNom(pdfFile2.getNom())).thenReturn(pdfFileList1);
         boolean result = verifyPdfService.verifyPdf(pdfFile2);
            assertEquals(false, result);
    }
    @Test
    public void verifyPdfTestWhenListInEmpty(){
        when(pdfFileRepository.findPdfAllFileByNom(pdfFile2.getNom())).thenReturn(pdfFileList1);
        boolean result = verifyPdfService.verifyPdf(pdfFile2);
        assertEquals(true, result);
    }
    @Test
    public void updateStateTest(){
         verifyPdfService.updateState(pdfFile2);
         assertEquals("traiter", pdfFile2.getEtat());
         verify(pdfFileRepository).save(pdfFile2);
    }
}






















































































































































































