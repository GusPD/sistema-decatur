package com.gl05.bad.servicio;

import com.gl05.bad.dao.AspiranteProfesorDao;
import com.gl05.bad.dao.PostulacionCohorteDao;
import com.gl05.bad.domain.AspiranteProfesor;
import com.gl05.bad.domain.PostulacionCohorte;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostuladoCohorteServiceImp implements PostuladoCohorteService {

    @Autowired
    private AspiranteProfesorDao aspiranteDao;
    
    @Autowired
    private PostulacionCohorteDao postuladoDao;

    @Override
    public List<AspiranteProfesor> listarAspiranteProfesor(Long idCohorte) {
        return (List<AspiranteProfesor>) aspiranteDao.findAspiranteByCohorte(idCohorte);
    }

    @Override
    public void agregarP(PostulacionCohorte postulado) {
        postuladoDao.save(postulado);
    }

    @Override
    public List<PostulacionCohorte> listarPostulaciones(Long idAspiranteProfesor) {
        return (List<PostulacionCohorte>) postuladoDao.findPostulaciones(idAspiranteProfesor);
    }

}
