package com.gl05.bad.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

public class Backup implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        realizarCopiaDeSeguridad();
    }

    public void realizarCopiaDeSeguridad() {
        Process p;
        ProcessBuilder pb;
        // Verificar y crear el directorio si no existe
        File backupDirectory = new File("C:\\BackupSistemaDecatur");
        if (!backupDirectory.exists()) {
            backupDirectory.mkdirs();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fechaActual = dateFormat.format(new Date());
        String backupFileName = "Backup_" + fechaActual + ".sql";
        String fullPath = backupDirectory.getAbsolutePath() + File.separator + backupFileName;
        pb = new ProcessBuilder(
                "pg_dump",
                "--host", "localhost",
                "--port", "5432",
                "--dbname", "sistema-decatur",
                "--username", "decatur",
                "--no-password",
                "--format", "custom",
                "--blobs",
                "--verbose", "--file", fullPath);
        try {
            final Map<String, String> env = pb.environment();
            env.put("PGPASSWORD", "admin755");
            p = pb.start();
            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();
            }
            r.close();
            p.waitFor();
            System.out.println(p.exitValue());
            try {
                Drive driveService = initializeDriveService();;
                String sistemaDecaturFolderId = getFolderId(driveService, "SistemaDecatur");
                if (sistemaDecaturFolderId != null) {
                    String backupsFolderId = getFolderId(driveService, "Backups", sistemaDecaturFolderId);
                    if (backupsFolderId != null) {
                        uploadFileToFolder(driveService, fullPath, backupFileName, backupsFolderId);
                    }
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error al subir el backup. " + e.getMessage());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error al crear el backup. " + e.getMessage());
        }
    }

    public void restaurarCopiaDeSeguridad(String nombre) {
        Process p;
        ProcessBuilder pb;
        String fullPath = "C:\\BackupSistemaDecatur" + File.separator + nombre;
        pb = new ProcessBuilder(
            "pg_restore",
            "--host=localhost",
            "--port=5432",
            "--username=decatur",
            "--no-password",
            "--clean",
            "--dbname=sistema-decatur",
            fullPath);

        try {
            final Map<String, String> env = pb.environment();
            env.put("PGPASSWORD", "admin755");
            p = pb.start();
            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();
            }
            r.close();
            p.waitFor();
            System.out.println("Se restauró el backup correctamente.");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error al restaurar el backup." + e.getMessage());
        }
    }

    //Función que inicializa el servicio de google drive
    public Drive initializeDriveService() throws Exception {
        InputStream credentialsStream = getClass().getClassLoader().getResourceAsStream("credentials.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream).createScoped(Collections.singleton(DriveScopes.DRIVE));
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("SistemaDecatur")
                .build();
    }

    //Función que busca la carpeta dentro de otra carpeta
    public String getFolderId(Drive service, String folderName, String parentFolderId) throws IOException {
        FileList result = service.files().list()
                .setQ("name='" + folderName + "' and mimeType='application/vnd.google-apps.folder' and '" + parentFolderId + "' in parents and trashed = false")
                .execute();

        if (result.getFiles() != null && !result.getFiles().isEmpty()) {
            return result.getFiles().get(0).getId();
        }
        return null;
    }

    //Función que busca la carpeta compartida en drive
    public String getFolderId(Drive service, String folderName) throws IOException {
        FileList result = service.files().list()
        .setQ("name='" + folderName + "' and mimeType='application/vnd.google-apps.folder' and sharedWithMe = true and trashed = false")
        .execute();
        if (result.getFiles() != null && !result.getFiles().isEmpty()) {
            return result.getFiles().get(0).getId();
        }
        return null;
    }

    //Función que busca el archivo en google drive
    public String buscarArchivo(Drive service, String nombreArchivo, String carpetaCompartidaId) throws IOException {
        FileList result = service.files().list()
                .setQ("name='" + nombreArchivo + "' and '" + carpetaCompartidaId + "' in parents and trashed = false")
                .execute();
        if (result.getFiles() != null && !result.getFiles().isEmpty()) {
            return result.getFiles().get(0).getId();
        } else {
            System.out.println("Archivo no encontrado.");
            return null;
        }
    }

    //Función que sube los archivos a google drive
    public void uploadFileToFolder(Drive service, String localFilePath, String fileNameInDrive, String folderId) throws IOException {
        com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
        fileMetadata.setName(fileNameInDrive);
        fileMetadata.setParents(Collections.singletonList(folderId));
        java.io.File filePath = new java.io.File(localFilePath);
        com.google.api.client.http.FileContent mediaContent = new com.google.api.client.http.FileContent("application/octet-stream", filePath);
        com.google.api.services.drive.model.File uploadedFile = service.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        System.out.println("Archivo subido con ID: " + uploadedFile.getId());
    }

    //Funcion que descarga los archivos de google drive
    public void downloadFile(Drive service, String fileId, String localFilePath) throws IOException {
        if (fileId == null) {
            System.out.println("El ID del archivo es nulo. No se puede descargar.");
            return;
        }
        try (OutputStream outputStream = Files.newOutputStream(Path.of(localFilePath))) {
            service.files().get(fileId).executeMediaAndDownloadTo(outputStream);
            System.out.println("Archivo descargado: " + localFilePath);
        } catch (Exception e) {
            System.out.println("Error al descargar archivo: " + e.getMessage());
        }
    }

    // Método para obtener el tamaño de un archivo
    public long getTamanoArchivo(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
            return 0L;
        }
    }

     // Método para convertir el tamaño del archivo de bytes a megabytes
    public String convertirBytesAMegabytes(long fileSizeInBytes) {
        double fileSizeInMegabytes = (double) fileSizeInBytes / (1024 * 1024);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(fileSizeInMegabytes) + " MB";
    }
}
