package org.gestiondestitresimportationbcp.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

public record TitreImportationHistoriqueDTO(
        @JsonSerialize(using = ToStringSerializer.class)
        Long numEnregistrement,
        Long numeromessage,
        double montantTotale,
        double montantFOB,
        double motantFret,
        String devise,
        String incotermString,
        String ribBancaire,
        String typeMessage,
        Date dateReception,
        Date dateTraitement,
        String etat


){}
