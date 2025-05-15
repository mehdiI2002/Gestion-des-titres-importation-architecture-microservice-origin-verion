package org.gestiondestitresimportationbcp.repositories;

import org.gestiondestitresimportationbcp.entities.TitreImportation;
import org.gestiondestitresimportationbcp.models.TitreImportationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
public interface TitreImportationRepository extends JpaRepository<TitreImportation, TitreImportationId> {
    Optional<TitreImportation> findByIdNumEnregistrementAndIdNumeroMessage(@Param("numEnregistrement") Long numEnregistrement, @Param("numeroMessage") Long numeroMessage);
   TitreImportation findByIdNumeroMessage(Long numeroMessage);
    @Query("SELECT t FROM TitreImportation t WHERE t.etat = 'non traité'")
    List<TitreImportation> findNonTraites();
    @Query("SELECT t FROM TitreImportation t WHERE CAST(t.id.numEnregistrement AS string) LIKE %:numEnregistrement% AND t.etat = 'non traité'")
    List<TitreImportation> findAllById_NumEnregistrementContainingAndEtat(@Param("numEnregistrement") String numEnregistrement);



    @Query("SELECT t FROM TitreImportation t JOIN t.operator o WHERE o.ribBancaire LIKE %:ribBancaire% AND t.etat = 'non traité'")
    List<TitreImportation> findAllTitlesByRib(@Param("ribBancaire") String ribBancaire);


List<TitreImportation> findAll();
    @Query("SELECT FUNCTION('YEAR', t.dateReception) AS year, FUNCTION('MONTH', t.dateReception) AS month, COUNT(t) AS fileCount " +
            "FROM TitreImportation t " +
            "GROUP BY FUNCTION('YEAR', t.dateReception), FUNCTION('MONTH', t.dateReception)")
    List<Object[]> countTitlesByYearAndMonth();
}
