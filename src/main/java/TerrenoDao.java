import com.gl05.bad.domain.Terreno;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TerrenoDao extends JpaRepository<Terreno, Long> {
    @Query("SELECT t FROM Terreno t WHERE t.idProyecto = :idProyecto")
    DataTablesOutput<Terreno> findAllByIdProyecto(Long idProyecto, DataTablesInput input);
}

