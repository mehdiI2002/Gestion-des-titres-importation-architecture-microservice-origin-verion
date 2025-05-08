package org.gestiondestitresimportationbcp.web;
import org.gestiondestitresimportationbcp.dto.TitreImportationDTO;
import org.gestiondestitresimportationbcp.dto.TitreImportationDetailsDTO;
import org.gestiondestitresimportationbcp.dto.TitreImportationHistoriqueDTO;
import org.gestiondestitresimportationbcp.models.TitreImportationId;
import org.gestiondestitresimportationbcp.service.ConvertToPdf;
import org.gestiondestitresimportationbcp.service.TitreImportationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/titles/")
public class RestControllerApp {
   private TitreImportationServices titreImportation;
   @Autowired
   private ConvertToPdf convertToPdf;
   public RestControllerApp(TitreImportationServices titreImportation) {
        this.titreImportation = titreImportation;
    }
       @GetMapping("/selectTittles")
    public List<TitreImportationDTO> selectTittles(){
       return  titreImportation.afficherTitreImportation();
    }

    @GetMapping("/detailTitle/{numEnregistrement}/{headerMessage}")
    public TitreImportationDetailsDTO selectDetailsTitle(@PathVariable Long numEnregistrement , @PathVariable Long headerMessage ) {
        TitreImportationId id = new TitreImportationId(numEnregistrement,headerMessage);
        return titreImportation.afficherDetailTitreImportation(id);
    }
    @GetMapping ("/downloadPdf")
    public ResponseEntity<FileSystemResource> transformFileToPDF(@RequestParam String filePath) throws IOException {
       return convertToPdf.transformFileToPDF(filePath);
    }
    @GetMapping("/accepter/{numeromessage}" )
    public void accepterTitre(@PathVariable Long  numeromessage){
    titreImportation.accepterTitre(numeromessage);
    }
    @GetMapping("/refuser/{numeromessage}/{motif}")
    public void refuserTitre(@PathVariable Long numeromessage,@PathVariable String motif){
       titreImportation.refuserTitre(numeromessage,motif);
    }
    @GetMapping  ("/researchByNumEnregistrement/{numEnregistrement}")
        public List<TitreImportationDTO> researchByNumEnregistement(@PathVariable String numEnregistrement){
        return titreImportation.researchTitleByNumEnregistrement(numEnregistrement);
    }
    @GetMapping("/researchByRibBancaire/{ribBancaire}")
    public List<TitreImportationDTO> researchByRibBancaire(@PathVariable String ribBancaire){
       return titreImportation.researchTitleByRibBancaire(ribBancaire);
    }
    @GetMapping("/historique")
       public List<TitreImportationHistoriqueDTO> getAllTitlesHistorique() {
        return titreImportation.findAllTitlesHistorique();
    }
    @GetMapping("/count-by-month")
    public List<Map<String, Object>> getTitlesCountByMonth() {
        return titreImportation.getTitlesCountByMonth();
    }




}
