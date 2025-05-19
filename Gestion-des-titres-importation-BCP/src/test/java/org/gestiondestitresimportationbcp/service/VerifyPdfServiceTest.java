package org.gestiondestitresimportationbcp.service;

import org.gestiondestitresimportationbcp.entities.PdfFile;
import org.gestiondestitresimportationbcp.repositories.PdfFileRepository;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class VerifyPdfServiceTest {
    @MockBean
    PdfFileRepository pdfFileRepository;
    private List<PdfFile> pdfFileList1;
    private List<PdfFile> pdfFileList2;
    @Mock
    private PdfFile pdfFile = new PdfFile(null,"/test.pdf",12L,"non traité","test.pdf");
    @Autowired
   private  VerifyPdfService verifyPdfService;


    public void verifyPdfTest() {
        assertEquals(true,pdfFileList1.isEmpty());
        pdfFileList2.add(pdfFile);
        assertEquals(false,pdfFileList2.isEmpty());

    }
}