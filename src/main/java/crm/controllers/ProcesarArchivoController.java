package crm.controllers;

import java.io.*;

import crm.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Controlador que maneja la subida de archivos al crm.
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */

@Controller
public class ProcesarArchivoController {

    /**
     * Servicio utilizado para el manejo del usuario
     */
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value="/usuario/procesar-excel", method=RequestMethod.GET)
    public String provideUploadInfo() {
        return "/usuario/buscarInformacionUsuariosExcel";
    }

    @RequestMapping(value="/usuario/procesar-excel", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpSession session){
        String nombre = file.getOriginalFilename();
        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("flash.error", "archivo vacio");
            return "redirect:/usuario/procesar-excel";
        }
        String[] nombreParts = nombre.split("\\.");
        String extension = nombreParts[nombreParts.length-1];
        if(extension.compareTo("xlsx") == 0) {
            try {
                InputStream archivo = file.getInputStream();
                usuarioService.procesarExcelUsuarios(archivo, nombre);
            } catch (AccessDeniedException e) {
                redirectAttributes.addFlashAttribute("flash.error", "Usted no tiene permisos para importar usuarios");
                return "redirect:/";
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("flash.error", "Error al procesar el archivo. Mensaje de error: '" + e.getMessage() + "'");
                return "redirect:/usuario/procesar-excel";
            }
            redirectAttributes.addFlashAttribute("flash.success", "Archivo procesado con exito.");
            return "redirect:/usuario/procesar-excel";
        }else{
            redirectAttributes.addFlashAttribute("flash.error", "La extension del archivo no es .xlsx.");
            return "redirect:/usuario/procesar-excel";
        }
    }

}