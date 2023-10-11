package com.gl05.bad.servicio;

import com.gl05.bad.domain.ResetPassword;
import java.util.List;

public interface ResetPasswordService {
    
    public List<ResetPassword> listaTokens();
   
    public void agregar(ResetPassword token);
        
    public void eliminar(ResetPassword token);
    
    public ResetPassword encontrar(Long token);
    
    public ResetPassword encontrarToken(String token);
    
    public void actualizar(ResetPassword token);
}
