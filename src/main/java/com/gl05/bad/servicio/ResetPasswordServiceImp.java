package com.gl05.bad.servicio;

import com.gl05.bad.dao.ResetPasswordDao;
import com.gl05.bad.domain.ResetPassword;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResetPasswordServiceImp implements ResetPasswordService{
    
    @Autowired
    private ResetPasswordDao tokenDao;

    @Override
    @Transactional(readOnly = true)
    public List<ResetPassword> listaTokens() {
        return (List<ResetPassword>) tokenDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(ResetPassword token) {
        tokenDao.save(token);
    }

    @Override
    @Transactional
    public void eliminar(ResetPassword token) {
        tokenDao.delete(token);
    }

    @Override
    @Transactional(readOnly = true)
    public ResetPassword encontrar(Long idToken) {
        return tokenDao.findById(idToken).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ResetPassword encontrarToken(String token) {
        return tokenDao.findByToken(token);
    }

    @Override
    @Transactional
    public void actualizar(ResetPassword token) {
        if (tokenDao.existsById(token.getIdAsignacion())) {
            tokenDao.save(token);
        } else {
            throw new IllegalArgumentException("El token no existe.");
        }
    }
}
