package org.gestiondestitresimportationbcp.service;

import org.gestiondestitresimportationbcp.dto.TitreImportationDTO;
import org.gestiondestitresimportationbcp.dto.TitreImportationDetailsDTO;
import org.gestiondestitresimportationbcp.dto.TitreImportationHistoriqueDTO;
import org.gestiondestitresimportationbcp.models.DemandeDomiciliationMessage;
import org.gestiondestitresimportationbcp.models.TitreImportationId;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TitreImportationServices {
    void insertTitle(DemandeDomiciliationMessage demandeDomiciliationMessage, Date dateReception);

    List<TitreImportationDTO> afficherTitreImportation();

    TitreImportationDetailsDTO afficherDetailTitreImportation(TitreImportationId id);

    void accepterTitre(Long numeroMessage);

    void refuserTitre(Long numeroMessage, String motif);

    List<TitreImportationDTO> researchTitleByNumEnregistrement(String numeroEnregistrement);

    List<TitreImportationDTO> researchTitleByRibBancaire(String ribBancaire);
List<TitreImportationHistoriqueDTO> findAllTitlesHistorique();
    List<Map<String, Object>> getTitlesCountByMonth() ;
}