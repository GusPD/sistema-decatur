package com.gl05.bad.servicio;

import com.gl05.bad.domain.CoordinadorAcademico;
import java.sql.Blob;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface CoordinadorAcademicoService {
  
  public List<CoordinadorAcademico> listarCoordinadores();
  
  public DataTablesOutput<CoordinadorAcademico> listarCoordinadorAcademico(DataTablesInput input);
  
  public void agregarCA(CoordinadorAcademico coordinador);
  
  public void actualizarCA(CoordinadorAcademico coordinador);
  
  public void eliminarCA(CoordinadorAcademico coordinador);
  
  public CoordinadorAcademico encontrarCA(CoordinadorAcademico coordinador);
  
  public void proIsertarCA(String cod, String nombre, String apellido, String email);
  
  public void actualizarCampo(CoordinadorAcademico coordinador, String nombreCampo, Blob blob);
  
  public CoordinadorAcademico encontrarPorIdUsuario(Integer idUsuario);
  
  public void enviarCorreo(String correoDestino, String asunto, String mensaje);
  
  public String buscarPerfil(String username);
  
  List<String> obtenerCorreosCordinadores();
  
}
