package org.gestiondestitresimportationbcp.service;

import org.gestiondestitresimportationbcp.entities.PdfFile;
import org.gestiondestitresimportationbcp.repositories.PdfFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerifyPdfService {
   private  List<PdfFile> pdfFiles;
    @Autowired
    PdfFileRepository pdfFileRepository;
    public boolean verifyPdf(PdfFile pdfFile) {
        this.pdfFiles = pdfFileRepository.findPdfAllFileByNom(pdfFile.getNom());
        return pdfFiles.isEmpty();
    }
    public void updateState(PdfFile existingPdf) {
        existingPdf.setEtat("traiter");
        pdfFileRepository.save(existingPdf);
    }


    }

