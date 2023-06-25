package com.gl05.bad.servicio;

import com.gl05.bad.dao.CoordinadorAcademicoDao;
import com.gl05.bad.dao.UsuarioDao;
import com.gl05.bad.domain.CoordinadorAcademico;
import com.gl05.bad.domain.Usuario;
import java.security.SecureRandom;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoordinadorAcademicoServiceImp implements CoordinadorAcademicoService{
  
    @Autowired
    private CoordinadorAcademicoDao coordinadorDao;
        
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void agregarCA(CoordinadorAcademico coordinador) {
      coordinadorDao.save(coordinador);
    }
  
    @Override
    @Transactional
    public void actualizarCA(CoordinadorAcademico coordinador) {
        CoordinadorAcademico coordinadorExistente = coordinadorDao.findById(coordinador.getIdCoorAca()).orElse(null);
        coordinadorExistente.setNombresCa(coordinador.getNombresCa());
        coordinadorExistente.setApellidosCa(coordinador.getApellidosCa());
        coordinadorExistente.setSexoCa(coordinador.getSexoCa());
        coordinadorExistente.setGeneroCa(coordinador.getGeneroCa());
        coordinadorExistente.setFechaNacCa(coordinador.getFechaNacCa());
        coordinadorExistente.setEstadoCivilCa(coordinador.getEstadoCivilCa());
        coordinadorExistente.setIdPais(coordinador.getIdPais());
        coordinadorExistente.setNacionalidadCa(coordinador.getNacionalidadCa());
        coordinadorExistente.setDuiCa(coordinador.getDuiCa());
        coordinadorExistente.setNitCa(coordinador.getNitCa());
        coordinadorExistente.setNupCa(coordinador.getNupCa());
        coordinadorExistente.setPasaporteCa(coordinador.getPasaporteCa());
        coordinadorExistente.setDocPersonalCa(coordinador.getDocPersonalCa());
        
        Blob fotoActual = coordinadorExistente.getFotografiaCa();
        
        if (coordinador.getFotografiaCa() == null && fotoActual != null) {
          byte[] fotoBytes;
          try {
            fotoBytes = fotoActual.getBytes(1, (int) fotoActual.length());
            coordinadorExistente.setFotografiaCa(new javax.sql.rowset.serial.SerialBlob(fotoBytes));
          } catch (SQLException ex) {
            Logger.getLogger(AtestadoTaServiceImp.class.getName()).log(Level.SEVERE, null, ex);
          }
        } else {
          coordinadorExistente.setFotografiaCa(null);
        }
        
        coordinadorDao.save(coordinadorExistente);
    }

    @Override
    public List<CoordinadorAcademico> listarCoordinadores() {
      return (List<CoordinadorAcademico>)coordinadorDao.findAll();
    }

    @Override
    public void eliminarCA(CoordinadorAcademico coordinador) {
      coordinadorDao.delete(coordinador);
    }

    @Override
    public CoordinadorAcademico encontrarCA(CoordinadorAcademico coordinador) {
      return coordinadorDao.findById(coordinador.getIdCoorAca()).orElse(null);
    }
    
    @Override
    @Transactional
    public void actualizarCampo(CoordinadorAcademico coordinador, String nombreCampo, Blob valorCampo) {
        CoordinadorAcademico coordinadorExistente = coordinadorDao.findById(coordinador.getIdCoorAca()).orElse(null);
        switch (nombreCampo) {
            case "fotografiaCa":
                coordinadorExistente.setFotografiaCa(valorCampo);
                break;
        }
        coordinadorDao.save(coordinadorExistente);
    }
    
    @Override
    @Transactional(readOnly=true)
    public CoordinadorAcademico encontrarPorIdUsuario(Integer idUsuario) {
        return coordinadorDao.findByIdusuario(idUsuario);
    }

    @Override
    public DataTablesOutput<CoordinadorAcademico> listarCoordinadorAcademico(DataTablesInput input) {
      return (DataTablesOutput<CoordinadorAcademico>)coordinadorDao.findAll(input);
    }
  
    @Override
    public void proIsertarCA(String cod, String nombre, String apellido, String email) {
      String password = generarPassword(8);
      String passwordEncode = passwordEncoder.encode(password);
      String asunto= "Credenciales de usuario del sistema de gestión de maestrías";
      String mensaje= "Bienvenid@ " + nombre + " " + apellido + " las credenciales proporcionadas como candidato a coordinador académico son:\nUsuario: " + cod.toLowerCase() + "\nContraseña: " + password;
      mensaje = mensaje.replaceAll("\n", System.getProperty("line.separator"));
      coordinadorDao.sp_insert_coordinador(cod.toLowerCase(), nombre, apellido, email, passwordEncode);
      enviarCorreo(email, asunto, mensaje);
    }
    
    @Override
    @Transactional
    public void enviarCorreo(String correoDestino, String asunto, String mensaje){
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(correoDestino);
      mailMessage.setSubject(asunto);
      mailMessage.setText(mensaje);

      mailSender.send(mailMessage);
    };

    public String generarPassword(int length) {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*()-_+[]{}<>.,?";

        String allCharacters = uppercaseLetters + lowercaseLetters + numbers + symbols;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        // Asegura que haya al menos un carácter de cada tipo
        password.append(getRandomCharacter(uppercaseLetters, random));
        password.append(getRandomCharacter(lowercaseLetters, random));
        password.append(getRandomCharacter(numbers, random));
        password.append(getRandomCharacter(symbols, random));

        // Genera el resto de la contraseña
        for (int i = 4; i < length; i++) {
            password.append(getRandomCharacter(allCharacters, random));
        }

        // Mezcla los caracteres en la contraseña
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(length);
            char temp = password.charAt(i);
            password.setCharAt(i, password.charAt(randomIndex));
            password.setCharAt(randomIndex, temp);
        }

        return password.toString();
    }

    private char getRandomCharacter(String characters, SecureRandom random) {
        int randomIndex = random.nextInt(characters.length());
        return characters.charAt(randomIndex);
    }
    
    @Override
    public String buscarPerfil(String username) {
        String usuarioCoordinador = "";
        String idUsuario="";
        List<Usuario> usuarios=userService.listaUsuarios();
        for (Usuario usuario: usuarios) {
               if(usuario.getUsername().equals(username)){
                   idUsuario=usuario.getIdUsuario().toString();
               }
        }
        List<CoordinadorAcademico> coordinadores = listarCoordinadores();
        for (CoordinadorAcademico coordinador: coordinadores) {
               if(coordinador.getIdusuario().toString().equals(idUsuario)){
                   usuarioCoordinador=coordinador.getIdCoorAca().toString();
               }
        }
        return usuarioCoordinador;
    }

    @Override
    public List<String> obtenerCorreosCordinadores() {
        List<CoordinadorAcademico> coordinadores = listarCoordinadores();
        List<Integer> idListCorreos = new ArrayList<>();
        List<String> correos = new ArrayList<>();

        for (CoordinadorAcademico coordinador : coordinadores) {
            idListCorreos.add(coordinador.getIdListCorreo());
        }

        List<String> correosAsociados = coordinadorDao.findCorreosByIdListCorreos(idListCorreos);
        correos.addAll(correosAsociados);
        
        return correos;
    }
}
