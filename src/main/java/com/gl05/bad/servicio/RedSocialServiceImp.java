package com.gl05.bad.servicio;

import com.gl05.bad.dao.RedSocialDao;
import com.gl05.bad.domain.RedSocial;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedSocialServiceImp implements RedSocialService{
  
    @Autowired
    private RedSocialDao redSocialDao;

    @Override
    public List<RedSocial> listarRedesSocial() {
        return (List<RedSocial>) redSocialDao.findAll();
    }

    @Override
    @Transactional
    public void agregarRS(RedSocial redSocial) {
        redSocialDao.save(redSocial);
    }
    
    @Override
    @Transactional
    public void actualizarRS(RedSocial redSocial) {
        RedSocial redSocialExistente = redSocialDao.findById(redSocial.getIdRs()).orElse(null);
        redSocialDao.save(redSocialExistente);
    }

    @Override
    public void eliminarRS(RedSocial redSocial) {
        redSocialDao.delete(redSocial);
    }

    @Override
    public RedSocial encontrarRS(RedSocial redSocial) {
        return redSocialDao.findById(redSocial.getIdRs()).orElse(null);
    }
}
