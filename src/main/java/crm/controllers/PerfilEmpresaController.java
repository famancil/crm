package crm.controllers;

import crm.entities.*;
import crm.services.EmailService;
import crm.services.EmpresaService;
import crm.services.EntidadesTipoService;
import crm.services.SucursalEmpresaService;
import crm.utils.MailUtil;
import crm.validators.EmpresaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controlador que maneja el perfil de una {@link crm.entities.Empresa}.
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Controller
public class PerfilEmpresaController {

    /**
     * InitBinder utilizado para setear el formato de fecha ingresada por formulario y para
     * setear el validador correspondiente de la clase Empresa
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     */
    @InitBinder
    private void perfilEmpresaBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
        binder.setValidator(new EmpresaValidator());
    }

    @Autowired
    private MailUtil mailUtil;


    /**
     * Servicio relacionado a la entidad {@link crm.entities.EmailMensaje}.
     */
    @Autowired
    private EmailService emailService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Empresa}.
     */
    @Autowired
    private EmpresaService empresaService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.SucursalEmpresa}.
     */
    @Autowired
    private SucursalEmpresaService sucursalEmpresaService;

    /**
     * Servicio utilizado para el manejo de las entidades tipo
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Carga los datos necesarios para la vista ver perfil empresa
     *
     * @param id id de la empresa que se cargara en la vista ver perfil
     * @param model objeto de tipo modelo para ser usado en la vista que utilize este metodo
     * @return Vista que muestra el perfil de una empresa.
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/{id}")
    public String verPerfilEmpresa(Model model, @PathVariable Long id) {
        Date actual = new java.util.Date();
        //SucursalEmpresa sucursalEmpresa = sucursalEmpresaService.getSucursalEmpresaById(Long.parseLong("86966"));
        List<CompromisoEmpresa> compromisos= empresaService.getCompromisoEmpresa(id);
        List<AporteEmpresa> aportes = empresaService.getAporteEmpresa(id);
        //List<Integer> totalAportes = new ArrayList<Integer>();
        for (CompromisoEmpresa compromiso : compromisos) {
            /*String fechaTermino=compromiso.getFechaTermino().toString().split(" ")[0];
            if(fechaTermino.equals("0001-01-01")){
                compromiso.setFechaTermino(null);
            }
            if(fechaTermino.equals("9999-01-01")){
                compromiso.setFechaTermino(null);
            }*/
            Integer totalAportes = empresaService.obtenerTotalAporteEmpresaPorIdEmpresa(id,compromiso.getId());
            compromiso.setCantidadAportes(totalAportes);
        }
        //System.out.println("GG WP");
        /*if(aportes!=null) {
            for (AporteEmpresa apo : aportes) {
                totalAportes.add(empresaService.getTotalAporteEmpresa(id, apo.getCompromisoEmpresa().getId()));
            }

        }*/
        //if(compromisos!=null)System.out.println("GG WP");
        //System.out.println(compromisos);
        /*for(CompromisoEmpresa hola : compromisos){
            if(hola==null)System.out.println("GG WP");
            else System.out.println(hola.getMontoComprometido());
        }*/

        //Integer totalAportes = empresaService.getTotalAporteEmpresa(id);
        //if(sucursalEmpresa==null)System.out.println("No hay nada");
        //else System.out.println(sucursalEmpresa.getSucursalCodigo());
        model.addAttribute("fechaHoy", actual);
        model.addAttribute("empresa", empresaService.getEmpresaById(id));
        model.addAttribute("nCorreos", empresaService.getNCorreosEmpresa(id));
        model.addAttribute("nReuniones", empresaService.getNReunionesEmpresa(id));
        model.addAttribute("nLlamadas", empresaService.getNLlamadasEmpresa(id));
        model.addAttribute("nOfertas", empresaService.getNOfertasEmpresa(id));
        model.addAttribute("nVacantes", empresaService.getNVacantesEmpresa(id));
        model.addAttribute("nPostulantes", empresaService.getNPostulantesEmpresa(id));
        model.addAttribute("primeraOferta", empresaService.getFechaPrimeraOfertaEmpresa(id));
        model.addAttribute("ultimaOferta", empresaService.getFechaUltimaOfertaEmpresa(id));
        model.addAttribute("contactosActivos", empresaService.getContactosActivosEmpresa(id));
        model.addAttribute("contactosInactivos", empresaService.getContactosInactivosEmpresa(id));
        model.addAttribute("contactoHistorico", empresaService.getContactoHistoricoConEmpresa(id));
        model.addAttribute("exalumnosYSocios", empresaService.getExalumnosYSociosPrimeros20(id));
        model.addAttribute("resumenOfertas", empresaService.getResumenOfertasEmpresas(id));
        model.addAttribute("detalleOfertas", empresaService.getOfertasLaboralesEmpresaDetallePrimeras20(id));
        model.addAttribute("sucursales", empresaService.getSucursalesEmpresa(id));
        model.addAttribute("compromisos", compromisos);
        model.addAttribute("aportes", aportes);
        //model.addAttribute("totalAportes", empresaService.obtenerTotalAporteEmpresaPorIdEmpresa(id));
        model.addAttribute("contactosSucursales", empresaService.getCantidadContactosSucursalesEmpresa(id));
        model.addAttribute("ofertasSucursales", empresaService.getCantidadOfertasSucursalesEmpresa(id));
        model.addAttribute("exalumnosSucursales", empresaService.getCantidadExalumnosSucursalesEmpresa(id));
        return "/empresa/perfilEmpresa";
    }

    /**
     * Carga los datos necesarios para la vista ver exalumnos y socios empresa
     *
     * @param id id de la empresa que tiene a los exalumnos y socios
     * @param model objeto de tipo modelo para ser usado en la vista que utilize este metodo
     * @return Vista que muestra los exalumnos y socios que tiene una empresa
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/{id}/exalumnos-y-socios")
    public String verExalumnosYSociosEmpresa(Model model, @PathVariable Long id) {
        model.addAttribute("empresa", empresaService.getEmpresaById(id));
        model.addAttribute("nOfertas", empresaService.getNOfertasEmpresa(id));
        model.addAttribute("exalumnosYSocios", empresaService.getExalumnosYSocios(id));
        return "empresa/exalumnosYSociosEmpresa";
    }

    /**
     * Carga los datos necesarios para la vista ver ofertas laborales empresa
     *
     * @param id id de la empresa que tiene las ofertas laborales
     * @param model objeto de tipo modelo para ser usado en la vista que utilize este metodo
     * @return Vista que muestra las ofertas laborales que tiene una empresa
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/{id}/ofertas-laborales-detalle")
    public String verOfertasLaboralesEmpresa(Model model, @PathVariable Long id) {
        model.addAttribute("empresa", empresaService.getEmpresaById(id));
        model.addAttribute("nOfertas", empresaService.getNOfertasEmpresa(id));
        model.addAttribute("detalleOfertas", empresaService.getOfertasLaboralesEmpresaDetalle(id));
        return "empresa/ofertasLaboralesDetalleEmpresa";
    }

    /**
     * Accion que despliega la interfaz que posibilita la edicion del perfil de una
     * {@link crm.entities.Empresa} en particular.
     *
     * @param model Modelo utilizado en la vista.
     * @param id Id de la {@link crm.entities.Empresa} a modificar.
     *
     * @return Retorna la vista de modificar empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/{id}/modificar", method = RequestMethod.GET)
    public String mostrarModificarFichaEmpresa(Model model, @PathVariable Long id) {
        model.addAttribute("empresa", empresaService.getEmpresaById(id));
        cargarDatosParaModificarEmpresa(model);
        return "/empresa/modificarEmpresa";
    }

    /**
     * Toma el formulario desde la vista modificarEmpresa, actualiza la base de datos con los valores
     * ingresados y retorna a la misma vista.
     *
     * @param empresa {@link crm.entities.Empresa} a modificar.
     * @param error Errores presentes en el formulario (si es que existen).
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista modificarEmpresa
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/modificar", method = RequestMethod.POST)
    public String modificarFichaEmpresa(@RequestParam @Valid MultipartFile file,@ModelAttribute("empresa") @Valid Empresa empresa, BindingResult error,
                                        RedirectAttributes redirectAttributes, Model model) {
        if(error.hasErrors()) {
            Empresa emp = empresaService.getEmpresaById(empresa.getId());
            empresa.setIdEmpresaExtranjera(emp.getIdEmpresaExtranjera());
            empresa.setRutEmpresa(emp.getRutEmpresa());
            empresa.setPais(emp.getPais());
            model.addAttribute("empresa", empresa);
            cargarDatosParaModificarEmpresa(model);
            return "/empresa/modificarEmpresa";
        }
        String nombre = file.getOriginalFilename();
        String[] nombreParts = nombre.split("\\.");
        String extension = nombreParts[nombreParts.length-1];
        Integer flag = 0;
        String rootPath = System.getProperty("user.home");
        File dir = new File("var" + File.separator + "empleos" + File.separator + "empresas" + File.separator + "logo");
        if (!dir.exists())
            dir.mkdirs();
        if(!file.isEmpty()) {
            try {
                InputStream imagen = file.getInputStream();
                BufferedImage image = ImageIO.read(imagen);
                Integer width = image.getWidth();
                Integer height = image.getHeight();
                try {
                    if (extension.compareTo("png") == 0 || extension.compareTo("jpg") == 0) {
                        if ((width == 100 && height == 100) || (width == 250 && height == 250)) {
                            byte[] bytes = file.getBytes();

                            File serverFile = new File(dir.getAbsolutePath()
                                    + File.separator + nombre);
                            BufferedOutputStream stream = new BufferedOutputStream(
                                    new FileOutputStream(serverFile));
                            stream.write(bytes);
                            stream.close();
                            if (width == 100) {
                                flag = 1;
                            } else {
                                flag = 2;
                            }
                        } else {
                            redirectAttributes.addFlashAttribute("flash.error", "Tamaño del archivo incorrecto.");
                            return "redirect:/empresa/"+empresa.getId().toString()+"/modificar";
                        }
                    } else {
                        redirectAttributes.addFlashAttribute("flash.error", "Formato Logo incorrecto.");
                        return "redirect:/empresa/"+empresa.getId().toString()+"/modificar";
                    }
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("flash.error", "Error al procesar el archivo. Mensaje de error: '" + e.getMessage() + "'");
                    return "redirect:/empresa/"+empresa.getId().toString()+"/modificar";
                }
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("flash.error", "No se pudo procesar la imagen.");
                return "redirect:/empresa/"+empresa.getId().toString()+"/modificar";
            }
        }
        empresaService.saveEmpresa(empresa);
        if(flag==1){
            File oldName = new File(dir.getAbsolutePath()
                    + File.separator + nombre);
            nombre = empresa.getId().toString() + "_250." + extension;
            File newName = new File(dir.getAbsolutePath()
                    + File.separator + nombre);
            oldName.renameTo(newName);
        }
        if(flag==2){
            File oldName = new File(dir.getAbsolutePath()
                    + File.separator + nombre);
            nombre = empresa.getId().toString() + "_250." + extension;
            File newName = new File(dir.getAbsolutePath()
                    + File.separator + nombre);
            oldName.renameTo(newName);
        }
        redirectAttributes.addFlashAttribute("flash.success", "El perfil de la empresa ha sido actualizado correctamente.");
        return "redirect:/empresa/"+empresa.getId();
    }

    /**
     * Carga al {@link org.springframework.ui.Model} los datos necesarios para desplegar la vista de modificar empresa.
     *
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    private void cargarDatosParaModificarEmpresa(Model model) {
        model.addAttribute("paises", empresaService.cargarPaises());
        model.addAttribute("facturaciones", empresaService.cargarFacturaciones());
        model.addAttribute("sectores", empresaService.cargarSectores());
        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());
    }

    /**
     * Cambia el estado booleano de premium de una empresa.
     *
     * @param idEmpresa {@link crm.entities.Empresa} a modificar.
     *
     * @return Retorna la vista de busqueda de empresas
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/{idEmpresa}/cambiarPremiumEmpresa", method = RequestMethod.POST)
    public String cambiarPremiumEmpresa(@PathVariable Long idEmpresa, RedirectAttributes redirectAttributes) {
        Empresa empresa = empresaService.getEmpresaById(idEmpresa);
        if(empresa.getPremiumEmpresa()==true) empresa.setPremiumEmpresa(false);
        else if(empresa.getPremiumEmpresa()==false) empresa.setPremiumEmpresa(true);
        empresaService.saveEmpresa(empresa);
        redirectAttributes.addFlashAttribute("flash.success", "La empresa ha sido actualizada correctamente.");
        return "redirect:/busquedas/empresas";
    }

    /**
     * Corrige una empresa, asociando los antecedentes laborales asociados a la empresa a corregir a una empresa valida.
     *
     * @param nombreEmpresaAReasignar nombre de la {@link crm.entities.Empresa} que reemplazara la empresa a corregir.
     * @param idEmpresa id de la {@link crm.entities.Empresa} que se va a corregir.
     * @param idSucursal id de la sucursal perteneciente a la empresa a reasignar
     *
     * @return Retorna la vista de busqueda de empresas
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/corregir", method = RequestMethod.POST)
    public String corregirEmpresa(@RequestParam("nombreEmpresaAReasignar") String nombreEmpresaAReasignar,
                                  @RequestParam("idSucursal") Long idSucursal,
                                  @RequestParam("idEmpresa") Long idEmpresa,
                                  @RequestParam("from") String from,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) {
        if(nombreEmpresaAReasignar.compareTo("") == 0 || idSucursal == 0){
            redirectAttributes.addFlashAttribute("flash.error", "Debe seleccionar una empresa y una sucursal.");
            return "redirect:"+request.getHeader("Referer");
        }
        Empresa empresaReasignar = empresaService.buscarEmpresasPorCalceRazonSocialONombreFantasiaOSigla(nombreEmpresaAReasignar).get(0);
        SucursalEmpresa sucursalReasignar = sucursalEmpresaService.getSucursalEmpresaById(idSucursal);
        Empresa empresaCorregir = empresaService.getEmpresaById(idEmpresa);
        empresaService.corregirEmpresa(empresaReasignar,sucursalReasignar,empresaCorregir);
        List<String> destinatarios = new ArrayList<>();
        EmailMensaje emailMensaje = new EmailMensaje();
        if(from.compareTo("por-alumno") == 0) {
            List<ActividadExalumno> actividadesExalumnos = empresaService.getExalumnosYSocios(idEmpresa);
            for (ActividadExalumno act : actividadesExalumnos) {
                destinatarios.add(act.getUsuario().getEmail());
            }
            emailMensaje = emailService.getEmailMensajeById(2);
        }
        else if(from.compareTo("por-publicador") == 0){
            List<UsuarioUsmempleoEmpresa> usuarioUsmempleoEmpresas = empresaService.getContactosActivosEmpresa(idEmpresa);
            for (UsuarioUsmempleoEmpresa usu: usuarioUsmempleoEmpresas) {
                destinatarios.add(usu.getUsuarioEmpresaUsmempleo().getEmail());
            }
            emailMensaje = emailService.getEmailMensajeById(31);
            String destinatariosString = "";
            for(String d: destinatarios) destinatariosString = destinatariosString+d+", ";
            emailMensaje.setMensaje(emailMensaje.getMensaje().replace("[usuarioPublicador]",destinatarios.get(0))
                                                             .replace("[empresaNueva]",empresaReasignar.getNombreFantasiaEmpresa())
                                                             .replace("[empresaRechazada]",empresaCorregir.getNombreFantasiaEmpresa()));
        }

        try {
            mailUtil.sendMail(mailUtil.getMailSender().getUsername(), destinatarios, emailMensaje.getAsunto(), emailMensaje.getMensaje());
        } catch (MessagingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flash.error", "Ha ocurrido un error al enviar el e-mail.");
            return "redirect:/busquedas/empresas/por-validar/alumnos/";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flash.error", "Ha ocurrido un error al enviar el e-mail.");
            return "redirect:/busquedas/empresas/por-validar/alumnos/";
        }
        redirectAttributes.addFlashAttribute("flash.success", "La empresa se ha corregido correctamente.");
        return "redirect:/busquedas/empresas/por-validar/alumnos/";
    }

    /**
     * Rechaza una empresa, eliminando todos los antecedentes laborales y otras entidades asociadas a ella.
     *
     * @param idEmpresa id de la {@link crm.entities.Empresa} que se va a corregir.
     *
     * @return Retorna la vista de busqueda de empresas
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/rechazar", method = RequestMethod.POST)
    public String rechazarEmpresa(@RequestParam("idEmpresa") Long idEmpresa,
                                  @RequestParam("mailContent") String mailContent,
                                  @RequestParam("from") String from,
                                  RedirectAttributes redirectAttributes) {
        Empresa empresaRechazar = empresaService.getEmpresaById(idEmpresa);
        empresaService.rechazarEmpresa(empresaRechazar);
        List<String> destinatarios = new ArrayList<>();
        EmailMensaje emailMensaje = new EmailMensaje();
        if(from.compareTo("por-alumno") == 0) {
            List<ActividadExalumno> actividadesExalumnos = empresaService.getExalumnosYSocios(idEmpresa);
            for (ActividadExalumno act : actividadesExalumnos) {
                destinatarios.add(act.getUsuario().getEmail());
            }
            emailMensaje = emailService.getEmailMensajeById(2);
        }else if( from.compareTo("por-publicador") == 0 ){
            List<UsuarioUsmempleoEmpresa> usuarioUsmempleoEmpresas = empresaService.getContactosActivosEmpresa(idEmpresa);
            for(UsuarioUsmempleoEmpresa usu: usuarioUsmempleoEmpresas){
                destinatarios.add(usu.getUsuarioEmpresaUsmempleo().getEmail());
            }
            emailMensaje = emailService.getEmailMensajeById(32);
        }
        emailMensaje.setMensaje(mailContent);
        try {
            mailUtil.sendMail(mailUtil.getMailSender().getUsername(), destinatarios, emailMensaje.getAsunto(), emailMensaje.getMensaje());
        } catch (MessagingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flash.error", "Ha ocurrido un error al enviar el e-mail.");
            return "redirect:/busquedas/empresas/por-validar/alumnos/";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flash.error", "Ha ocurrido un error al enviar el e-mail.");
            return "redirect:/busquedas/empresas/por-validar/alumnos/";
        }
        redirectAttributes.addFlashAttribute("flash.success", "La empresa se ha eliminado correctamente.");
        return "redirect:/busquedas/empresas/por-validar/alumnos/";
    }
}
