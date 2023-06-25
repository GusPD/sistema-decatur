package com.gl05.bad.servicio;

import com.gl05.bad.domain.RedSocial;
import java.util.List;

public interface RedSocialService {
  
  public List<RedSocial> listarRedesSocial();
  
  public void agregarRS(RedSocial redSocial);
  
  public void actualizarRS(RedSocial redSocial);
  
  public void eliminarRS(RedSocial redSocial);
  
  public RedSocial encontrarRS(RedSocial redSocial);

}
