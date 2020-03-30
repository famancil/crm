package crm.controllers;

import crm.entities.*;
import crm.repositories.EmpresaRepository;
import crm.services.*;
import crm.utils.EncodingUtil;
import crm.validators.EmpresaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.TypedQuery;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controlador que contiene los metodos para la realizacion de busquedas de empresas
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */

@Controller
public class BusquedaEmpresasController {

    /**
     * Servicio relacionado a la entidad {@link crm.entities.EmailMensaje}.
     */
    @Autowired
    private EmailService emailService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Usuario}.
     */
    @Autowired
    private UsuarioService usuarioService;


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
     * Servicio relacionado a la entidad {@link crm.entities.UsuarioUsmempleoEmpresa}.
     */
    @Autowired
    private UsuarioUsmempleoEmpresaService usuarioUsmempleoEmpresaService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.ContactoHistoricoEmpresa}.
     */
    @Autowired
    private ContactoHistoricoService contactoHistoricoService;

    /**
     * Tamano de las paginas de listados a mostrar
     */
    private static final int PAGE_SIZE = 20;

    /**
     * Clase utilizada para codificar y decodificar URIs
     */
    EncodingUtil encodingUtil;

    /**
     * Carga los datos necesarios para la vista de busquedas de empresas
     *
     * @param model objeto de tipo modelo para ser usado en la vista que utilize este metodo
     * @return Vista de busqueda de empresas
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/busquedas/empresas")
    public String busquedas(Model model) {
        Empresa empresa = new Empresa();
        Pais p = new Pais();
        TipoSector ts = new TipoSector();
        TipoNivelFacturacion tnf = new TipoNivelFacturacion();
        //UsuarioUsmempleoEmpresa uue= new UsuarioUsmempleoEmpresa();

        empresa.setRutEmpresa("");
        empresa.setIdEmpresaExtranjera("");
        empresa.setNombreFantasiaEmpresa("");
        empresa.setGiroEmpresa("");
        empresa.setNumEmpleados(null);
        empresa.setNumContratAnu(null);

        p.setId((short) 0);
        empresa.setPais(p);

        ts.setCodigo((short) -1);
        empresa.setSector(ts);

        tnf.setCodNivelFacturacion((short)0);
        empresa.setNivelFacturacion(tnf);

        String cargo="";
        //uue.setUsuarioEmpresaUsmempleo(ueu);

        Integer Pagina = 1;
        String headHunter = "0";
        String empresaPremium = "0";
        String logo = "0";
        String critNumEmpleados = "0";
        String critNumContratAnu = "0";

        PageRequest pageRequest = new PageRequest(Pagina, PAGE_SIZE);
        Page<Empresa> resultadoBusqueda = empresaService.buscarEmpresas(
                empresa,
                headHunter,
                empresaPremium,
                logo,
                critNumEmpleados,
                critNumContratAnu,
                pageRequest);
        this.generatePagination(model, resultadoBusqueda);
        model.addAttribute("facturaciones", empresaService.cargarFacturaciones());
        model.addAttribute("paises", empresaService.cargarPaisesEmpresas());
        model.addAttribute("sectores", empresaService.cargarSectores());
        model.addAttribute("empresa", empresa);
        model.addAttribute("empresas", resultadoBusqueda);
        model.addAttribute("tieneLogo", logo);
        model.addAttribute("cargo",cargo);
        model.addAttribute("pais", empresa.getPais().getId());
        model.addAttribute("sector", empresa.getSector().getCodigo());
        model.addAttribute("facturacion",empresa.getNivelFacturacion().getCodNivelFacturacion());
        model.addAttribute("esHeadHunter", headHunter);
        model.addAttribute("empresaPremium", empresaPremium);
        model.addAttribute("critNumEmpleados", critNumEmpleados);
        model.addAttribute("critNumContratAnu", critNumContratAnu);
        return "/empresa/busquedaEmpresas";
    }

    /**
     * Carga los resultados de la busqueda de empresas con distintos criterios para la vista de resultado
     * de busquedas.
     *
     * @param rut indica el rut de la empresa que se quiere buscar
     * @param idExtranjero indica el id extranjero de la empresa a buscar
     * @param nombre indica el nombre de fantasia, razon social o sigla de la o las empresas que se quieren buscar
     *               (se buscan palabras parecidas al valor ingresado)
     * @param pais indica el pais al cual pertenece las empresas que se quieren buscar
     * @param giro indica el giro de la o las empresas que se quieren buscar
     * @param sector indica el sector al cual pertenecen las empresas que se quieren buscar
     * @param facturacion indica el nivel de facturacion de las empresas que se quieren buscar
     * @param headHunter indica si queremos buscar empresas del tipo headhunter o no, los valores son
     *                   0 - cualquiera
     *                   1 - headhunter
     *                   2 - no headhunter
     * @param empresaPremium indica si queremos buscar empresas premium o no, los valores son:
     *                       0 - cualquiera
     *                       1 - premium
     *                       2 - no premium
     * @param logo indica si queremos buscar empresas con logo o no, los valores son:
     *             0 - cualquiera
     *             1 - con logo
     *             2 - sin logo
     * @param critNumEmpleados indica el criterio que queremos utilizar para buscar empresas
     *                         mediante su numero de empleados, los valores son:
     *                         0 - iguales a
     *                         1 - menores a
     *                         2 - mayores a
     * @param critNumContratAnu indica el criterio que queremos utilizar para buscar empresas
     *                          mediante su numero de contratos anuales los valores son:
     *                          0 - iguales a
     *                          1 - menores a
     *                          2 - mayores a
     * @param numEmpleados indica el numero de empleados segun el criterio de numero de empleados que se utilice
     * @param numContratAnu indica el numero de contratos anuales segun el criterio de numero de contratos anuales que se utilice
     * @param pagina indica el numero de pagina que queremos ver del total de resultados
     * @param model objeto de tipo modelo para ser usado en la vista que utilize este metodo
     * @return Vista con los resultados de la busqueda de empresas
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/busquedas/empresas/resultados", method = RequestMethod.GET)
    public String resultadosBusquedas(Model model,
                                      @RequestParam("rut") String rut,
                                      @RequestParam("idExt") String idExtranjero,
                                      @RequestParam("nombre") String nombre,
                                      @RequestParam("pais") Short pais,
                                      @RequestParam("giro") String giro,
                                      @RequestParam("sector") Short sector,
                                      @RequestParam("facturacion") Short facturacion,
                                      @RequestParam("esHeadHunter") String headHunter,
                                      @RequestParam("empresaPremium") String empresaPremium,
                                      @RequestParam("tieneLogo") String logo,
                                      @RequestParam("critNumEmpleados") String critNumEmpleados,
                                      @RequestParam("numEmpleados") Integer numEmpleados,
                                      @RequestParam("critNumContratAnu") String critNumContratAnu,
                                      @RequestParam("numContratAnu") Short numContratAnu,
                                      @RequestParam("cargo") String cargo,
                                      @RequestParam("operador") String operador,
                                      @RequestParam("oportunidad") String oportunidad,
                                      @RequestParam("listRealizado") String realizado,
                                      @RequestParam("listVigente") String vigente,
                                      @RequestParam("contactoEmpresa") String contactoEmpresa,
                                      @RequestParam("pagina") String pagina){


        nombre = encodingUtil.decodeURIComponent(nombre);

        Empresa empresa = new Empresa();
        Pais p = new Pais();
        TipoSector ts = new TipoSector();
        TipoNivelFacturacion tnf = new TipoNivelFacturacion();

        empresa.setRutEmpresa(rut);
        empresa.setIdEmpresaExtranjera(idExtranjero);
        empresa.setNombreFantasiaEmpresa(nombre);
        empresa.setGiroEmpresa(giro);
        empresa.setNumEmpleados(numEmpleados);
        empresa.setNumContratAnu(numContratAnu);

        p.setId(pais);
        empresa.setPais(p);

        ts.setCodigo(sector);
        empresa.setSector(ts);

        tnf.setCodNivelFacturacion(facturacion);
        empresa.setNivelFacturacion(tnf);

        Integer Pagina = Integer.parseInt(pagina);
        PageRequest pageRequest = new PageRequest(Pagina, PAGE_SIZE);
        Page<Empresa> resultadoBusqueda = empresaService.buscarEmpresas(
                empresa,
                headHunter,
                empresaPremium,
                logo,
                critNumEmpleados,
                critNumContratAnu,
                pageRequest);

        int sum=0;
        Page<Empresa> empresasNoContactadas=null;
        Page<AporteEmpresa> aportes=null;
        Page<CompromisoEmpresa> compromisos=null;
        Page<UsuarioUsmempleoEmpresa> resultados=null;
        Page<ContactoHistoricoEmpresaPersonaParticipante> contacto=null;
        List<UsuarioUsmempleoEmpresa> uue;

        if(!cargo.equals("")){
            resultados= usuarioUsmempleoEmpresaService.buscarEmpleadosPorCargo(Pagina-1,cargo,sector,rut,nombre);
            //if(resultados==null)System.out.println("Error");
            uue= usuarioUsmempleoEmpresaService.buscarContactosPorCargo(cargo);
            int x;

            for(UsuarioUsmempleoEmpresa usuario : resultados) {
                x=0;
                for (UsuarioUsmempleoEmpresa usu: uue) {
                    if (usuario.getUsuarioEmpresaUsmempleo().getNombreCompleto().equals(usu.getUsuarioEmpresaUsmempleo().getNombreCompleto())){
                        x++;
                    }
                }
                usuario.setCantidadContactos(x);
            }
            this.generatePagination(model, resultados);
        } else if(!operador.equals("")){
            String[] words= operador.split(" ");
            if(words.length ==3)contacto=contactoHistoricoService.buscarContactoHistoricoPorNombreOperador(words[0],words[1],sector,Pagina-1);
            if(words.length ==4)contacto=contactoHistoricoService.buscarContactoHistoricoPorNombreOperador(words[0],words[2],sector,Pagina-1);
            this.generatePagination(model, contacto);
        }

        else if(!oportunidad.equals("")){
            if(oportunidad.equals("Donacion")){

                aportes=empresaService.buscarAporteEmpresaPorTipoCompromisoYTipoVigente(oportunidad,"Vigente",Pagina-1,sector);
            }
            else if(oportunidad.equals("Auspicio")){
                aportes=empresaService.buscarAporteEmpresaPorTipoCompromisoYTipoVigente(oportunidad,"Vigente",Pagina-1,sector);
            }
            else{
                aportes=empresaService.buscarAporteEmpresaPorTipoCompromisoYTipoVigente(oportunidad,"Vigente",Pagina-1,sector);
            }
            if(realizado.equals("Si") && vigente.equals("Si")) {
                this.generatePagination(model, aportes);
            }
            else if(realizado.equals("No") && vigente.equals("No")){
                int aux;
                compromisos=empresaService.buscarCompromisoEmpresaPorTipoCompromisoTipoVigenciaYTipoSector(oportunidad,"No Vigente",Pagina-1,sector);
                for(CompromisoEmpresa comp: compromisos) {
                    aux = 0;
                    for (AporteEmpresa apo : aportes)
                        if (comp.getId() == apo.getCompromisoEmpresa().getId()) aux++;
                    if (aux == 0) sum++;
                    comp.setCantidadAportes(aux);
                }
                if(sum==1){
                    sum=0;
                    compromisos=empresaService.buscarCompromisoEmpresaPorTipoCompromisoTipoVigenciaYAporte(oportunidad,"No Vigente",Pagina-1);
                }
                this.generatePagination(model, compromisos);

            }
            else if(realizado.equals("No")){
                int a;
                compromisos=empresaService.buscarCompromisoEmpresaPorTipoCompromisoYTipoSector(oportunidad,Pagina-1,sector);
                aportes=empresaService.buscarAporteEmpresaPorTipoCompromiso(oportunidad,Pagina-1,sector);
                for(CompromisoEmpresa compromiso: compromisos) {
                    a = 0;
                    for (AporteEmpresa aporte : aportes) {

                        if (compromiso.getId() == aporte.getCompromisoEmpresa().getId()) a++;
                    }
                    if (a == 0) sum++;
                    compromiso.setCantidadAportes(a);
                }
                this.generatePagination(model, compromisos);
                }
            else if(vigente.equals("No")){
                sum=-1;
                compromisos=empresaService.buscarCompromisoEmpresaPorTipoCompromisoTipoVigenciaYTipoSector(oportunidad,"No Vigente",Pagina-1,sector);
                this.generatePagination(model, compromisos);
            }
        }
        else if(!contactoEmpresa.equals("")){

            if(contactoEmpresa.equals("Contactada")){
                contacto=contactoHistoricoService.buscarTodosContactoHistorico(Pagina-1,sector);
                this.generatePagination(model, contacto);
            }
            else{
                empresasNoContactadas=empresaService.buscarEmpresasNoContactadas(Pagina-1,sector);
                this.generatePagination(model, empresasNoContactadas);
            }
        }
        else this.generatePagination(model, resultadoBusqueda);

        model.addAttribute("facturaciones", empresaService.cargarFacturaciones());
        model.addAttribute("paises", empresaService.cargarPaisesEmpresas());
        model.addAttribute("sectores", empresaService.cargarSectores());
        model.addAttribute("empresa", empresa);
        model.addAttribute("empresas", resultadoBusqueda);
        model.addAttribute("empleadores", resultados);
        model.addAttribute("nombre", nombre);
        model.addAttribute("tieneLogo", logo);
        model.addAttribute("cargo", cargo);
        model.addAttribute("operador", operador);
        model.addAttribute("oportunidad", oportunidad);
        model.addAttribute("listRealizado", realizado);
        model.addAttribute("listVigente", vigente);
        model.addAttribute("pais", empresa.getPais().getId());
        model.addAttribute("sector", empresa.getSector().getCodigo());
        model.addAttribute("facturacion",empresa.getNivelFacturacion().getCodNivelFacturacion());
        model.addAttribute("esHeadHunter", headHunter);
        model.addAttribute("empresaPremium", empresaPremium);
        model.addAttribute("critNumEmpleados", critNumEmpleados);
        model.addAttribute("critNumContratAnu", critNumContratAnu);
        if(!cargo.equals(""))return "/empresa/busquedaEmpresasPorCargo";
        if(!operador.equals("")){
            model.addAttribute("contactos",contacto);
            return "/empresa/busquedaEmpresasPorOperador";
        }
        if(!oportunidad.equals("")){
            model.addAttribute("compromisos",compromisos);
            model.addAttribute("compromisosSinAportes",sum);
            model.addAttribute("aportes",aportes);
            return "/empresa/busquedaEmpresasPorCompromiso";
        }
        if(!contactoEmpresa.equals("")){
            model.addAttribute("contactos",contacto);
            model.addAttribute("contactoEmpresa",contactoEmpresa);
            model.addAttribute("empresasNoContactadas",empresasNoContactadas);
            return "/empresa/busquedaEmpresasPorOperador";
        }
        else return "/empresa/busquedaEmpresas";
    }

    /**
     * Carga los datos necesarios para la vista validar empresas por alumno
     *
     * @param model objeto de tipo modelo para ser usado en la vista que utilize este metodo
     * @return Vista para validar empresas registradas por alumnos.
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/busquedas/empresas/por-validar/alumnos/")
    public String mostrarValidarEmpresasPorAlumno(Model model) {
        String criterio = "rut";
        String rut = "";
        String idExtranjero = "";
        String nombre = "";
        Integer numeroPagina = 1;
        String emailRechazo = emailService.getContenidoEmailMensajeById(2);
        Page<Empresa> empresas = empresaService.buscarEmpresasCreadasPorUsuarioAexaPorValidar(criterio, rut, idExtranjero, nombre, numeroPagina - 1);
        if(empresas != null) {
            this.generatePagination(model, empresas);
        }
        this.cargarDatosVistaValidarEmpresas(model, emailRechazo, null, criterio, rut, idExtranjero, nombre, empresas, false);
        return "/empresa/validarEmpresasPorAlumno";
    }

    /**
     * Obtiene un listado de {@link crm.entities.Empresa}, segun criterio y valor de busqueda especificados en la URL,
     * que tengan estado de vigencia "Por Validar" y que hayan sido ingresadas por alumno,
     * retornando el numero pagina de la paginacion del listado, especificada en la URL.
     *
     * @param model {@link org.springframework.ui.Model} Modelo a utilizar en la vista.
     *
     * @return La vista de resultado de la busqueda realizada.
     */
    @RequestMapping(value="/busquedas/empresas/por-validar/alumnos/resultados",method = RequestMethod.GET)
    public String resultadoBusquedasValidarEmpresasPorAlumno(Model model,
                                     @RequestParam("criterio") String criterio,
                                     @RequestParam("rut") String rut,
                                     @RequestParam("idExt") String idExtranjero,
                                     @RequestParam("nombre") String nombre,
                                     @RequestParam("pagina") String pagina){
        Integer numeroPagina;
        try {
            numeroPagina = Integer.parseInt(pagina);
        }catch(Exception e){
            numeroPagina = 1;
        }
        if (numeroPagina <= 0) {
            numeroPagina = 1;
        }
        nombre = encodingUtil.decodeURIComponent(nombre);
        String emailRechazo = emailService.getContenidoEmailMensajeById(2);
        Page<Empresa> empresas = empresaService.buscarEmpresasCreadasPorUsuarioAexaPorValidar(criterio, rut, idExtranjero, nombre, numeroPagina - 1);
        if(empresas != null) {
            this.generatePagination(model, empresas);
        }
        this.cargarDatosVistaValidarEmpresas(model, emailRechazo, null, criterio, rut, idExtranjero, nombre, empresas, false);
        return "/empresa/validarEmpresasPorAlumno";
    }

    /**
     * Carga los datos necesarios para la vista validar empresas por publicador
     *
     * @param model objeto de tipo modelo para ser usado en la vista que utilize este metodo
     * @return Vista para validar empresas registradas por publicadores.
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/busquedas/empresas/por-validar/publicadores/")
    public String mostrarValidarEmpresasPorPublicador(Model model) {
        String criterio = "rut";
        String rut = "";
        String idExtranjero = "";
        String nombre = "";
        Integer numeroPagina = 1;
        String emailRechazo = emailService.getContenidoEmailMensajeById(32);
        Page<Empresa> empresas = empresaService.buscarEmpresasCreadasPorUsuarioEmpresaUsmempleoPorValidar(criterio, rut, idExtranjero, nombre, numeroPagina - 1);
        if(empresas != null) {
            this.generatePagination(model, empresas);
        }
        this.cargarDatosVistaValidarEmpresas(model, emailRechazo, null, criterio, rut, idExtranjero, nombre, empresas, false);
        return "/empresa/validarEmpresasPorPublicador";
    }

    /**
     * Obtiene un listado de {@link crm.entities.Empresa}, segun criterio y valor de busqueda especificados en la URL,
     * que tengan estado de vigencia "Por Validar" y que hayan sido ingresadas por publicadores,
     * retornando el numero pagina de la paginacion del listado, especificada en la URL.
     *
     * @param model {@link org.springframework.ui.Model} Modelo a utilizar en la vista.
     *
     * @return La vista de resultado de la busqueda realizada.
     */
    @RequestMapping(value="/busquedas/empresas/por-validar/publicadores/resultados",method = RequestMethod.GET)
    public String resultadoBusquedasValidarEmpresasPorPublicador(Model model,
                                     @RequestParam("criterio") String criterio,
                                     @RequestParam("rut") String rut,
                                     @RequestParam("idExt") String idExtranjero,
                                     @RequestParam("nombre") String nombre,
                                     @RequestParam("pagina") String pagina){
        Integer numeroPagina;
        try {
            numeroPagina = Integer.parseInt(pagina);
        }catch(Exception e){
            numeroPagina = 1;
        }
        if (numeroPagina <= 0) {
            numeroPagina = 1;
        }
        nombre = encodingUtil.decodeURIComponent(nombre);
        String emailRechazo = emailService.getContenidoEmailMensajeById(32);
        Page<Empresa> empresas = empresaService.buscarEmpresasCreadasPorUsuarioEmpresaUsmempleoPorValidar(criterio, rut, idExtranjero, nombre, numeroPagina - 1);
        if(empresas != null) {
            this.generatePagination(model, empresas);
        }
        this.cargarDatosVistaValidarEmpresas(model, emailRechazo, null, criterio, rut, idExtranjero, nombre, empresas, false);
        return "/empresa/validarEmpresasPorPublicador";
    }

    /**
     * Carga los datos necesarios para la vista validar sucursales por alumno
     *
     * @param model objeto de tipo modelo para ser usado en la vista que utilize este metodo
     * @return Vista para validar sucursales registradas por alumnos.
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/busquedas/sucursales/por-validar/alumnos/")
    public String mostrarValidarSucursalesPorAlumno(Model model) {
        String criterio = "rut";
        String rut = "";
        String idExtranjero = "";
        String nombre = "";
        Integer numeroPagina = 1;
        Page<Empresa> empresas = empresaService.buscarEmpresasConSucursalesCreadasPorUsuarioAexaPorValidar(criterio, rut, idExtranjero, nombre, numeroPagina - 1);
        if(empresas != null) {
            this.generatePagination(model, empresas);
        }
        this.cargarDatosVistaValidarEmpresas(model, null, null, criterio, rut, idExtranjero, nombre, empresas, false);
        return "/empresa/sucursalEmpresa/validarSucursalesPorAlumno";
    }

    /**
     * Obtiene un listado de {@link crm.entities.Empresa}, segun criterio y valor de busqueda especificados en la URL,
     * que posean sucursales en estado de vigencia "Por Validar" y que hayan sido ingresadas por alumnos,
     * retornando el numero pagina de la paginacion del listado, especificada en la URL.
     *
     * @param model {@link org.springframework.ui.Model} Modelo a utilizar en la vista.
     *
     * @return La vista de resultado de la busqueda realizada.
     */
    @RequestMapping(value="/busquedas/sucursales/por-validar/alumnos/resultados",method = RequestMethod.GET)
    public String resultadoBusquedasValidarSucursalesPorAlumno(Model model,
                                                  @RequestParam("criterio") String criterio,
                                                  @RequestParam("rut") String rut,
                                                  @RequestParam("idExt") String idExtranjero,
                                                  @RequestParam("nombre") String nombre,
                                                  @RequestParam("pagina") String pagina){
        Integer numeroPagina;
        try {
            numeroPagina = Integer.parseInt(pagina);
        }catch(Exception e){
            numeroPagina = 1;
        }
        if (numeroPagina <= 0) {
            numeroPagina = 1;
        }
        nombre = encodingUtil.decodeURIComponent(nombre);
        Page<Empresa> empresas = empresaService.buscarEmpresasConSucursalesCreadasPorUsuarioAexaPorValidar(criterio, rut, idExtranjero, nombre, numeroPagina - 1);
        if(empresas != null) {
            this.generatePagination(model, empresas);
        }
        this.cargarDatosVistaValidarEmpresas(model, null, null, criterio, rut, idExtranjero, nombre, empresas, false);
        return "/empresa/sucursalEmpresa/validarSucursalesPorAlumno";
    }

    /**
     * Carga los datos necesarios para la vista validar sucursales por publicador
     *
     * @param model objeto de tipo modelo para ser usado en la vista que utilize este metodo
     * @return Vista para validar sucursales registradas por publicadores.
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/busquedas/sucursales/por-validar/publicadores/")
    public String mostrarValidarSucursalesPorPublicador(Model model) {
        String criterio = "rut";
        String rut = "";
        String idExtranjero = "";
        String nombre = "";
        Integer numeroPagina = 1;
        Page<Empresa> empresas = empresaService.buscarEmpresasConSucursalesCreadasPorUsuarioEmpresaUsmempleoPorValidar(criterio, rut, idExtranjero, nombre, numeroPagina - 1);
        if(empresas != null) {
            this.generatePagination(model, empresas);
        }
        this.cargarDatosVistaValidarEmpresas(model, null, null, criterio, rut, idExtranjero, nombre, empresas, false);
        return "/empresa/sucursalEmpresa/validarSucursalesPorPublicador";
    }

    /**
     * Obtiene un listado de {@link crm.entities.Empresa}, segun criterio y valor de busqueda especificados en la URL,
     * que posean sucursales en estado de vigencia "Por Validar" y que hayan sido ingresadas por publicadores,
     * retornando el numero pagina de la paginacion del listado, especificada en la URL.
     *
     * @param model {@link org.springframework.ui.Model} Modelo a utilizar en la vista.
     *
     * @return La vista de resultado de la busqueda realizada.
     */
    @RequestMapping(value="/busquedas/sucursales/por-validar/publicadores/resultados",method = RequestMethod.GET)
    public String resultadoBusquedasValidarSucursalesPorPublicador(Model model,
                                                               @RequestParam("criterio") String criterio,
                                                               @RequestParam("rut") String rut,
                                                               @RequestParam("idExt") String idExtranjero,
                                                               @RequestParam("nombre") String nombre,
                                                               @RequestParam("pagina") String pagina){
        Integer numeroPagina;
        try {
            numeroPagina = Integer.parseInt(pagina);
        }catch(Exception e){
            numeroPagina = 1;
        }
        if (numeroPagina <= 0) {
            numeroPagina = 1;
        }
        nombre = encodingUtil.decodeURIComponent(nombre);
        Page<Empresa> empresas = empresaService.buscarEmpresasConSucursalesCreadasPorUsuarioEmpresaUsmempleoPorValidar(criterio, rut, idExtranjero, nombre, numeroPagina - 1);
        if(empresas != null) {
            this.generatePagination(model, empresas);
        }
        this.cargarDatosVistaValidarEmpresas(model, null, null, criterio, rut, idExtranjero, nombre, empresas, false);
        return "/empresa/sucursalEmpresa/validarSucursalesPorPublicador";
    }

    /**
     * Busca una empresa y verifica si tiene sus datos completos o no, de ser asi la empresa se valida y se retorna la vista
     * "/empresa/validarEmpresasPorAlumno" en caso contrario, se retorna la misma vista con el booleano "datosIncompletos"
     * seteado en true el cual indica que se necesitan rellenar los datos incompletos de la empresa para validarla
     *
     * @param idEmpresa Id de la empresa que se desea validar
     * @param model objeto de tipo modelo para ser usado en la vista que utilize este metodo
     * @return Vista para validar empresas registradas por alumnos.
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/validar",method = RequestMethod.POST)
    public String validarEmpresa(Model model,
                                 @RequestParam("idEmpresa") Long idEmpresa,
                                 @RequestParam(value = "criterio", required = false) String criterio,
                                 @RequestParam(value = "rut", required = false) String rut,
                                 @RequestParam(value = "idExtranjero", required = false) String idExtranjero,
                                 @RequestParam(value = "nombre", required = false) String nombre,
                                 @RequestParam(value = "pagina", required = false) String pagina,
                                 @RequestParam("from") String from,
                                 @RequestParam(value = "sinDatos", required = false) Boolean sinDatos,
                                 RedirectAttributes redirectAttributes) {
        if(sinDatos != null && sinDatos == true){
            empresaService.validarEmpresa(idEmpresa);
            redirectAttributes.addFlashAttribute("flash.success", "La sucursal ha sido validada correctamente.");
            if(from.compareTo("por-alumno") == 0) return "redirect:/busquedas/sucursales/por-validar/alumnos/";
            else return "redirect:/busquedas/sucursales/por-validar/publicadores/";
        }
        Boolean datosIncompletos = empresaService.verificarDatosInvalidosOIncompletosEmpresa(idEmpresa);
        if (!datosIncompletos && from.compareTo("por-alumno") == 0) {
            empresaService.validarEmpresa(idEmpresa);
            redirectAttributes.addFlashAttribute("flash.success", "La empresa se ha validado correctamente.");
            return "redirect:/busquedas/empresas/por-validar/alumnos/";
        } else if (!datosIncompletos && from.compareTo("por-publicador") == 0) {
            empresaService.validarEmpresa(idEmpresa);
            redirectAttributes.addFlashAttribute("flash.success", "La empresa se ha validado correctamente.");
            return "redirect:/busquedas/empresas/por-validar/publicadores/";
        }
        Integer numeroPagina;
        try {
            numeroPagina = Integer.parseInt(pagina);
        } catch (Exception e) {
            numeroPagina = 1;
        }
        if (numeroPagina <= 0) {
            numeroPagina = 1;
        }
        nombre = encodingUtil.decodeURIComponent(nombre);
        if(from.compareTo("por-alumno") == 0) {
            Page<Empresa> empresas = empresaService.buscarEmpresasCreadasPorUsuarioAexaPorValidar(criterio, rut, idExtranjero, nombre, numeroPagina - 1);
            String emailRechazo = emailService.getContenidoEmailMensajeById(2);
            this.cargarDatosVistaValidarEmpresas(model, emailRechazo, idEmpresa, criterio, rut, idExtranjero, nombre, empresas, datosIncompletos);
            this.generatePagination(model, empresas);
            return "/empresa/validarEmpresasPorAlumno";
        }else{
            Page<Empresa> empresas = empresaService.buscarEmpresasCreadasPorUsuarioEmpresaUsmempleoPorValidar(criterio, rut, idExtranjero, nombre, numeroPagina - 1);
            String emailRechazo = emailService.getContenidoEmailMensajeById(32);
            this.cargarDatosVistaValidarEmpresas(model, emailRechazo, idEmpresa, criterio, rut, idExtranjero, nombre, empresas, datosIncompletos);
            this.generatePagination(model, empresas);
            return "/empresa/validarEmpresasPorPublicador";
        }
    }

    /**
     * Busca una sucursal y verifica si tiene sus datos completos o no, de ser asi la sucursal se valida y se retorna la vista
     * "/empresa/sucursalEmpresa/validarSucursalesPorAlumno" o "/empresa/sucursalEmpresa/validarSucursalesPorPublicador" segun
     * lo que indique el parametro 'from', en caso contrario, se retorna la misma vista con el booleano "datosIncompletos"
     * seteado en true el cual indica que se necesitan rellenar los datos incompletos de la sucursal para validarla
     *
     * @param idSucursal Id de la sucursal que se desea validar
     * @param model objeto de tipo modelo para ser usado en la vista que utilize este metodo
     * @return Vista para validar empresas registradas por alumnos.
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/sucursal/validar",method = RequestMethod.POST)
    public String validarSucursal(Model model,
                                  @RequestParam("idSucursal") Long idSucursal,
                                  @RequestParam("from") String from,
                                  @RequestParam(value = "sinDatos", required = false) Boolean sinDatos,
                                  RedirectAttributes redirectAttributes) {
        Long idEmpresaSucursal = sucursalEmpresaService.getSucursalEmpresaById(idSucursal).getEmpresa().getId();
        if(sinDatos != null && sinDatos == true){
            sucursalEmpresaService.validarSucursalEmpresa(idSucursal);
            redirectAttributes.addAttribute("from", from);
            redirectAttributes.addAttribute("idEmpresa", idEmpresaSucursal);
            redirectAttributes.addFlashAttribute("flash.success", "La sucursal ha sido validada correctamente.");
            return "redirect:/empresa/{idEmpresa}/sucursales/por-validar/{from}";
        }
        Boolean datosIncompletos = sucursalEmpresaService.verificarDatosInvalidosOIncompletosSucursalEmpresa(idSucursal);
        if (!datosIncompletos) {
            sucursalEmpresaService.validarSucursalEmpresa(idSucursal);
            redirectAttributes.addAttribute("from", from);
            redirectAttributes.addAttribute("idEmpresa", idEmpresaSucursal);
            redirectAttributes.addFlashAttribute("flash.success", "La sucursal ha sido validada correctamente.");
            return "redirect:/empresa/{idEmpresa}/sucursales/por-validar/{from}";
        }
        Empresa empresa = empresaService.getEmpresaById(idEmpresaSucursal);
        empresa.setSucursalesPorValidar(empresaService.getSucursalesEmpresaPorValidar(idEmpresaSucursal));
        String emailRechazo = emailService.getContenidoEmailMensajeById(16);
        model.addAttribute("emailRechazo", emailRechazo);
        model.addAttribute("empresa", empresa);
        model.addAttribute("idSucursal", idSucursal);
        model.addAttribute("datosIncompletos", datosIncompletos);
        return "/empresa/sucursalEmpresa/sucursalesPorValidarEmpresa";
    }

    private void cargarDatosVistaValidarEmpresas(Model model, String emailRechazo, Long idEmpresa, String criterio, String rut, String idExtranjero, String nombre, Page<Empresa> empresas, Boolean datosIncompletos) {
        model.addAttribute("idEmpresa", idEmpresa);
        model.addAttribute("emailRechazo", emailRechazo);
        model.addAttribute("criterio", criterio);
        model.addAttribute("rut", rut);
        model.addAttribute("idExtranjero", idExtranjero);
        model.addAttribute("nombre", nombre);
        model.addAttribute("empresas", empresas);
        model.addAttribute("datosIncompletos", datosIncompletos);
    }

    /**
     * Genera la paginacion para la busqueda de empresas
     *
     * @param model modelo utilizado en la vista que utilice este metodo
     * @param page resultado de la busqueda de empresas como paginacion
     */
    private void generatePagination(Model model, Page page) {
        int actual = page.getNumber();
        int inicio = Math.max(1, actual - 5);
        int fin = Math.min(inicio + 10, page.getTotalPages());
        model.addAttribute("beginIndex", inicio);
        model.addAttribute("endIndex", fin);
        model.addAttribute("currentIndex", actual);
    }
}
