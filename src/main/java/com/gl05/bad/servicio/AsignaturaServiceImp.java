package com.gl05.bad.servicio;

import com.gl05.bad.dao.AsignaturaDao;
import com.gl05.bad.domain.Asignatura;
import com.gl05.bad.domain.MallaCurricular;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsignaturaServiceImp implements AsignaturaService {

    @Autowired
    private AsignaturaDao asignaturaDao;

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Asignatura> listarAsignatura(DataTablesInput input) {
        return (DataTablesOutput<Asignatura>) asignaturaDao.findAll(input);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Asignatura> listarAsignatura() {
        return (Collection<Asignatura>) asignaturaDao.findAll();
    }

    @Override
    public void agregarA(Asignatura asignatura) {
        asignaturaDao.save(asignatura);
    }

    @Override
    public void eliminarA(Asignatura asignatura) {
        asignaturaDao.delete(asignatura);
    }

    @Override
    @Transactional(readOnly = true)
    public Asignatura encontrarA(Asignatura asignatura) {
        return asignaturaDao.findById(asignatura.getIdAsignatura()).orElse(null);
    }

    @Override
    public void actualizarA(Asignatura asignatura) {
        System.out.println(1);
        // Verifica si el área de conocimiento existe en la base de datos
        if (asignaturaDao.existsById(asignatura.getIdAsignatura())) {
            // Actualiza el área de conocimiento en la base de datos
            asignaturaDao.save(asignatura);
        } else {
            // El área de conocimiento no existe, puedes lanzar una excepción o manejar el caso según tus necesidades
            throw new IllegalArgumentException("La Asignatura especificada no existe.");
        }
    }

    @Override
    public void AgregarAsig(String codigoAsignatura, String nombreAsignatura, int uv, int numeroCorrelativo, int ciclo, long idAreaC, long idMalla, int duracion, int horasT, int horasP, int horaCiclo, String introduccion, String descipcionPrograma, String objetivo, String metodologia, String sistemaEvaluacion, String bibliografia, String actividad, String ponderacion) {
        asignaturaDao.sp_insert_asignatura(codigoAsignatura, nombreAsignatura, uv, numeroCorrelativo, ciclo, idAreaC, idMalla, duracion, horasT, horasP, horaCiclo, introduccion, descipcionPrograma, objetivo, metodologia, sistemaEvaluacion, bibliografia, actividad, ponderacion);

    }

    @Override
    @Transactional(readOnly = true)
    public long encontrarMalla(Long idPlanEstudio) {
        return asignaturaDao.findMallaByPlanId(idPlanEstudio);
    }

    @Override
    public DataTablesOutput<Asignatura> listarAsignaturaFiltrado(DataTablesInput input, Long idMallaCurricular) {
        Specification<Asignatura> additionalSpecification = (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("idMallaCurricular"), idMallaCurricular);
        return asignaturaDao.findAll(input, additionalSpecification);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Asignatura> encontrarAsignaturasPorMalla(MallaCurricular idMallaCurricular){
        return asignaturaDao.findByIdMallaCurricular(idMallaCurricular);
    }

    @Override
    public Asignatura encontrarAsig(Long asignatura) {
        return asignaturaDao.findById(asignatura).orElse(null);
    }

}
