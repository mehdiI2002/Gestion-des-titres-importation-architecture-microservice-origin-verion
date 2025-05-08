package org.gestiondestitresimportationbcp.repositories;

import org.gestiondestitresimportationbcp.entities.AccuseTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface AccuseTableRepository extends JpaRepository <AccuseTable,Long> {


}
