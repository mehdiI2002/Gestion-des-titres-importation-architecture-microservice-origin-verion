package org.gestiondestitresimportationbcp.mappers;

import org.gestiondestitresimportationbcp.dto.TitreImportationHistoriqueDTO;
import org.gestiondestitresimportationbcp.entities.TitreImportation;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class TitreImportationHistoriqueDTOMapper implements Function<TitreImportation, TitreImportationHistoriqueDTO> {
    @Override
    public TitreImportationHistoriqueDTO apply(TitreImportation titreImportation) {
        return new TitreImportationHistoriqueDTO(
                titreImportation.getId().getNumEnregistrement(),
                titreImportation.getId().getNumeroMessage(),
                titreImportation.getMontantTotale(),
                titreImportation.getMontantFOB(),
                titreImportation.getMotantFret(),
                titreImportation.getDevise(),
                titreImportation.getIncotermString(),
                titreImportation.getOperator().getRibBancaire(),
                titreImportation.getMessage().getTypeMessage(),
                titreImportation.getDateReception(),
                titreImportation.getDateTraitement(),
                titreImportation.getEtat()
        );
    }

}
