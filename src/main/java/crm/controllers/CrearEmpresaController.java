package crm.controllers;

import crm.entities.*;
import crm.forms.RegistrarEmpresaForm;
import crm.services.EmpresaService;
import crm.services.EntidadesTipoService;
import crm.services.GeograficoService;
import crm.services.UsuarioService;
import crm.validators.RegistrarEmpresaFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.*;
/**
 * Controlador que maneja la creacion de una {@link crm.entities.Empresa}
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Controller
public class CrearEmpresaController {

    /**
     * Servicio utilizado para el manejo de las entidades relacionadas con la localizacion geografica
     */
    @Autowired
    private GeograficoService geograficoService;

    /**
     * Servicio utilizado para el manejo de una entidad empresa
     */
    @Autowired
    private EmpresaService empresaService;

    /**
     * Servicio utilizado para el manejo de una entidad usuario
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Servicio utilizado para el manejo de las entidades tipo
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * InitBinder utilizado para setear el validador correspondiente de la clase RegistrarEmpresaForm.
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     */
    @InitBinder
    private void crearEmpresaBinder(WebDataBinder binder) {
        //Setea el validador de empresa en binder desde la clase RegistrarEmpresaFormValidator
        binder.setValidator(new RegistrarEmpresaFormValidator());
    }


    /**
     * Muestra el form para registrar una nueva {@link crm.entities.Empresa}.
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Una vista con el form para el registro de una {@link crm.entities.Empresa}.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresas/registrar", method = RequestMethod.GET)
    public String registrarEmpresa(Model model){

        Pais pais = new Pais();
        Region region = new Region();
        Provincia provincia = new Provincia();
        Comuna comuna = new Comuna();

        pais.setId((short) 56);

        Empresa empresa = new Empresa();
        empresa.setPais(pais);

        SucursalEmpresa sucursalEmpresa = new SucursalEmpresa();
        sucursalEmpresa.setEmpresa(empresa);
        sucursalEmpresa.setPais(pais);
        sucursalEmpresa.setComuna(comuna);

        RegistrarEmpresaForm registrarEmpresaForm = new RegistrarEmpresaForm(empresa, sucursalEmpresa, region, provincia);

        model.addAttribute("registrarEmpresaForm", registrarEmpresaForm);
        model.addAttribute("paises", empresaService.cargarPaises());
        model.addAttribute("sectores", empresaService.cargarSectores());
        model.addAttribute("facturaciones", empresaService.cargarFacturaciones());
        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());
        return "/empresa/registrarEmpresa";
    }

    /**
     * Muestra el retorno de una vez que se envia el form por el metodo POST.
     *
     * @param registrarEmpresaForm {@link crm.entities.Empresa}
     *
     * @return Retorna una vista con el resultado de la operacion ingresar
     * {@link crm.entities.Empresa}.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresas/registrar", method = RequestMethod.POST)
    public String registrarEmpresa(@RequestParam @Valid MultipartFile file,@Valid @ModelAttribute(value = "registrarEmpresaForm") RegistrarEmpresaForm registrarEmpresaForm, BindingResult error, RedirectAttributes redirectAttributes, Model model){
        if(error.hasErrors()){
            model.addAttribute("form", registrarEmpresaForm);
            model.addAttribute("paises", empresaService.cargarPaises());
            model.addAttribute("sectores", empresaService.cargarSectores());
            model.addAttribute("facturaciones", empresaService.cargarFacturaciones());
            model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());
            return "/empresa/registrarEmpresa";
        }
        String nombre = file.getOriginalFilename();
        String[] nombreParts = nombre.split("\\.");
        String extension = nombreParts[nombreParts.length-1];
        Integer flag = 0;
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
                            return "redirect:/empresas/registrar";
                        }
                    } else {
                        redirectAttributes.addFlashAttribute("flash.error", "Formato Logo incorrecto.");
                        return "redirect:/empresas/registrar";
                    }
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("flash.error", "Error al procesar el archivo. Mensaje de error: '" + e.getMessage() + "'");
                    return "redirect:/empresas/registrar";
                }
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("flash.error", "No se pudo procesar la imagen.");
                return "redirect:/empresa/registrar";
            }
        }
            Empresa empresa = empresaService.registrarEmpresaYSucursal(registrarEmpresaForm.getEmpresa(), registrarEmpresaForm.getSucursalEmpresa());
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
        redirectAttributes.addFlashAttribute("flash.success", "La empresa ha sido agregada correctamente.");
        return "redirect:/empresa/" + empresa.getId();
    }
}
