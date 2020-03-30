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
public class SubirArchivoController {

    /**
     * Servicio utilizado para el manejo del usuario
     */
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value="/usuario/importar", method=RequestMethod.GET)
    public String provideUploadInfo() {
        return "/usuario/subirArchivoExcel";
    }

    @RequestMapping(value="/usuario/importar", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpSession session){
        String nombre = file.getOriginalFilename();
        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("flash.error", "archivo vacio");
            return "redirect:/usuario/importar";
        }
        String[] nombreParts = nombre.split("\\.");
        String extension = nombreParts[nombreParts.length-1];
        if(extension.compareTo("xlsx") == 0) {
            session.setAttribute("percent", 0);
            try {
                    InputStream archivo = file.getInputStream();
                    usuarioService.leerExcelUsuarios(archivo, session);
                } catch (AccessDeniedException e) {
                    redirectAttributes.addFlashAttribute("flash.error", "Usted no tiene permisos para importar usuarios");
                    return "redirect:/";
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("flash.error", "Error al procesar el archivo. Mensaje de error: '" + e.getMessage() + "'");
                    return "redirect:/usuario/importar";
                }
            return "redirect:/usuario/importar";
        }else{
            redirectAttributes.addFlashAttribute("flash.error", "La extension del archivo no es .xlsx.");
            return "redirect:/usuario/importar";
        }
    }

    @RequestMapping(value="/usuario/importar/percentage")
    public @ResponseBody Integer porcentajeCarga(HttpSession session) {
        return (Integer) session.getAttribute("percent");
    }

    @RequestMapping(value="/usuario/importar/cantidad-usuarios-validos")
    public @ResponseBody Integer cantidadUsuariosValidos(HttpSession session) {
        Integer n = (Integer) session.getAttribute("usuariosValidos");
        session.removeAttribute("usuariosValidos");
        session.removeAttribute("percent");
        return n;
    }

}