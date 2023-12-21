package com.gl05.bad.controller;

import com.gl05.bad.servicio.BitacoraService;
import com.gl05.bad.web.Backup;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BackupController {
  
    @Autowired
    private BitacoraService bitacoraService;

    //Función que redirige a la vista de los backup locales
    @GetMapping("/BackupLocal")
    public String MostrarBackupLocal(Model model, Authentication authentication) throws Exception {
        model.addAttribute("pageTitle", "Backup");
        return "/Backup/GestionarBackupLocal";
    }

    //Función que redirige a la vista de los backup en la nube
    @GetMapping("/BackupNube")
    public String MostrarBackupNube(Model model, Authentication authentication) throws Exception {
        model.addAttribute("pageTitle", "Backup");
        return "/Backup/GestionarBackupNube";
    }

    //Función para obtener los backups almacenados
    @GetMapping(value="/backuplocal/data", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<Map<String, String>> getBackupLocal(@Valid DataTablesInput input) throws Exception {
        DataTablesOutput<Map<String, String>> output = new DataTablesOutput<>();
        Backup backupJob = new Backup();
        try {
            // Verificar y crear el directorio si no existe
            File backupDirectory = new File("C:\\BackupSistemaDecatur");
            if (!backupDirectory.exists()) {
                backupDirectory.mkdirs();
            }
            Drive driveService = backupJob.initializeDriveService();
            List<Map<String, String>> backupFiles = new ArrayList<>();
            String sistemaDecaturFolderId = backupJob.getFolderId(driveService, "SistemaDecatur");
            if (sistemaDecaturFolderId != null) {
                String backupsFolderId = backupJob.getFolderId(driveService, "Backups", sistemaDecaturFolderId);
                if (backupsFolderId != null) {
                    List<Map<String, String>> archivosLocales = listarArchivosLocales("C:\\BackupSistemaDecatur");
                    List<Map<String, String>> archivosDrive = listarArchivosEnDrive(driveService, backupsFolderId);
                    backupFiles = listarArchivosLocales(archivosLocales, archivosDrive);
                }
            }
            output.setDraw(1);
            output.setData(backupFiles);
            output.setRecordsTotal(backupFiles.size());
            output.setRecordsFiltered(backupFiles.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return output;
    }

    @GetMapping(value="/backupnube/data", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<Map<String, String>> getBackupNube(@Valid DataTablesInput input) throws Exception {
        DataTablesOutput<Map<String, String>> output = new DataTablesOutput<>();
        Backup backupJob = new Backup();
        try {
            // Verificar y crear el directorio si no existe
            File backupDirectory = new File("C:\\BackupSistemaDecatur");
            if (!backupDirectory.exists()) {
                backupDirectory.mkdirs();
            }
            Drive driveService = backupJob.initializeDriveService();
            List<Map<String, String>> backupFiles = new ArrayList<>();
            String sistemaDecaturFolderId = backupJob.getFolderId(driveService, "SistemaDecatur");
            if (sistemaDecaturFolderId != null) {
                String backupsFolderId = backupJob.getFolderId(driveService, "Backups", sistemaDecaturFolderId);
                if (backupsFolderId != null) {
                    List<Map<String, String>> archivosLocales = listarArchivosLocales("C:\\BackupSistemaDecatur");
                    List<Map<String, String>> archivosDrive = listarArchivosEnDrive(driveService, backupsFolderId);
                    backupFiles = listarArchivosNube(archivosLocales, archivosDrive);
                }
            }
            output.setDraw(1);
            output.setData(backupFiles);
            output.setRecordsTotal(backupFiles.size());
            output.setRecordsFiltered(backupFiles.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return output;
    }
    
    //Función para crear un backup del sistema
    @PostMapping("/CrearBackup")
    public ResponseEntity<String> CrearBackup(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Backup backupJob = new Backup();
            backupJob.realizarCopiaDeSeguridad();
            String mensaje = "Se ha creado el backup correctamente.";
            bitacoraService.registrarAccion("Crear backup");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            String error = "Ocurrió un error al crear el backup." + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para restaurar un backup del sistema
    @PostMapping("/RestaurarBackup")
    public ResponseEntity<String> RestaurarBackup(@RequestParam("nombre") String nombre, HttpServletRequest request, RedirectAttributes redirectAttributes) throws JobExecutionException {
        try {
            Backup backupJob = new Backup();
            backupJob.restaurarCopiaDeSeguridad(nombre);
            String mensaje = "Se ha restaurado el backup correctamente.";
            bitacoraService.registrarAccion("Restaurar backup");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            String error = "Ocurrió un error al restaurar el backup." + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para descargar un backup del sistema
    @PostMapping("/DescargarBackup")
    public ResponseEntity<String> DescargarBackup(@RequestParam("nombre") String nombre, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
        Backup backupJob = new Backup();
        try {
            // Verificar y crear el directorio si no existe
            File backupDirectory = new File("C:\\BackupSistemaDecatur");
            if (!backupDirectory.exists()) {
                backupDirectory.mkdirs();
            }
            Drive driveService = backupJob.initializeDriveService();
            String sistemaDecaturFolderId = backupJob.getFolderId(driveService, "SistemaDecatur");
            if (sistemaDecaturFolderId != null) {
                String backupsFolderId = backupJob.getFolderId(driveService, "Backups", sistemaDecaturFolderId);
                if (backupsFolderId != null) {
                    String idArchivo = backupJob.buscarArchivo(driveService, nombre, backupsFolderId);
                    backupJob.downloadFile(driveService, idArchivo, "C:\\BackupSistemaDecatur\\" + nombre);
                }
            }
            String mensaje = "Se ha descargado el backup correctamente.";
            bitacoraService.registrarAccion("Descargar backup");
            return ResponseEntity.ok(mensaje);
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            String error = "Ocurrió un error al descargar el backup." + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para exportar un backup del sistema
    @PostMapping("/ExportarBackup")
    public ResponseEntity<String> ExportarBackup(@RequestParam("nombre") String nombre, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String fullPath = "C:\\BackupSistemaDecatur" + File.separator + nombre;
        Backup backupJob = new Backup();
        try {
            Drive driveService = backupJob.initializeDriveService();
            String sistemaDecaturFolderId = backupJob.getFolderId(driveService, "SistemaDecatur");
            if (sistemaDecaturFolderId != null) {
                String backupsFolderId = backupJob.getFolderId(driveService, "Backups", sistemaDecaturFolderId);
                if (backupsFolderId != null) {
                    backupJob.uploadFileToFolder(driveService, fullPath, nombre, backupsFolderId);
                    String mensaje = "Se ha exportado el backup correctamente.";
                    bitacoraService.registrarAccion("Exportar backup");
                    return ResponseEntity.ok(mensaje);
                } else {
                    String error = "No se encontró la carpeta 'Backups' dentro de 'SistemaDecatur'.";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
                }
            } else {
                String error = "No se encontró la carpeta 'SistemaDecatur'.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            String error = "Ocurrió un error al exportar el backup." + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para eliminar un backup del sistema
    @PostMapping("/EliminarBackup")
    public ResponseEntity<String> EliminarBackup(@RequestParam("nombre") String nombre, RedirectAttributes redirectAttributes) {
        try {
            String rutaArchivo = "C:\\BackupSistemaDecatur\\" + nombre;
            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                if (archivo.delete()) {
                    String mensaje = "Se ha eliminado el backup correctamente.";
                    bitacoraService.registrarAccion("Eliminar backup");
                    return ResponseEntity.ok(mensaje);
                } else {
                    String error = "Ocurrió un error al eliminar el backup.";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
                }
            } else {
                String error = "Ocurrió un error al eliminar el backup, no existe.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String error = "Ocurrió un error al eliminar el backup. " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que lista los archivos locales y de google drive
    // Método para combinar, eliminar duplicados y ordenar archivos locales
    public List<Map<String, String>> listarArchivosLocales(List<Map<String, String>> archivosLocales, List<Map<String, String>> archivosDrive) {
        // Actualizar atributo exportado en archivos locales
        archivosLocales.forEach(archivoLocal -> archivoLocal.put("exportado",
                Boolean.toString(archivosDrive.stream().anyMatch(archivoDrive -> archivoDrive.get("nombre").equals(archivoLocal.get("nombre"))))));
        // Actualizar atributo importado en archivos de Drive y combinar archivos
        Map<String, Map<String, String>> archivosUnificados = archivosLocales.stream()
                .collect(Collectors.toMap(archivo -> archivo.get("nombre"), Function.identity(), (a, b) -> a, LinkedHashMap::new));
        // Ordenar por nombre de forma descendente
        List<Map<String, String>> archivosOrdenados = new ArrayList<>(archivosUnificados.values());
        archivosOrdenados.sort(Comparator.comparing(archivo -> archivo.get("nombre"), Comparator.reverseOrder()));
        return archivosOrdenados;
    }

    // Método para combinar, eliminar duplicados y ordenar archivos
    public List<Map<String, String>> listarArchivosNube(List<Map<String, String>> archivosLocales, List<Map<String, String>> archivosDrive) {
        // Actualizar atributo exportado en archivos locales
        archivosDrive.forEach(archivoDrive -> archivoDrive.put("exportado",
                Boolean.toString(archivosLocales.stream().anyMatch(archivoLocal -> archivoLocal.get("nombre").equals(archivoDrive.get("nombre"))))));
        // Actualizar atributo importado en archivos de Drive y combinar archivos
        Map<String, Map<String, String>> archivosUnificados = archivosDrive.stream()
                .collect(Collectors.toMap(archivo -> archivo.get("nombre"), Function.identity(), (a, b) -> a, LinkedHashMap::new));
        // Ordenar por nombre de forma descendente
        List<Map<String, String>> archivosOrdenados = new ArrayList<>(archivosUnificados.values());
        archivosOrdenados.sort(Comparator.comparing(archivo -> archivo.get("nombre"), Comparator.reverseOrder()));
        return archivosOrdenados;
    }

    // Método para listar archivos locales
    public List<Map<String, String>> listarArchivosLocales(String rutaLocal) throws IOException {
        Backup backupJob = new Backup();
        try (Stream<Path> paths = Files.walk(Paths.get(rutaLocal)).filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".sql"))) {
            return paths.map(path -> {
                Map<String, String> detallesArchivo = new HashMap<>();
                detallesArchivo.put("nombre", path.getFileName().toString());
                detallesArchivo.put("tamano", backupJob.convertirBytesAMegabytes(backupJob.getTamanoArchivo(path)));
                detallesArchivo.put("exportado", "false");
                detallesArchivo.put("ubicacion", "local");
                return detallesArchivo;
            }).collect(Collectors.toList());
        }
    }

    // Método para listar archivos en Drive
    public List<Map<String, String>> listarArchivosEnDrive(Drive service, String folderId) throws IOException {
        FileList result = service.files().list().setQ("'" + folderId + "' in parents and trashed = false")
                .setFields("files(id, name, size)").execute();
        List<Map<String, String>> resultados = new ArrayList<>(result.getFiles().size());
        Backup backupJob = new Backup();
        for (com.google.api.services.drive.model.File file : result.getFiles()) {
            Map<String, String> detallesArchivo = new HashMap<>();
            detallesArchivo.put("nombre", file.getName());
            detallesArchivo.put("tamano", backupJob.convertirBytesAMegabytes(file.getSize()));
            detallesArchivo.put("exportado", "false");
            detallesArchivo.put("ubicacion", "nube");
            resultados.add(detallesArchivo);
        }
        return resultados;
    }
}
