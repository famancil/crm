package crm.controllers;

import crm.entities.Carrera;
import crm.entities.CompetenciaUsmempleo;
import crm.entities.TipoCompetencia;
import crm.services.CarreraService;
import crm.services.CompetenciaUsmempleoService;
import crm.services.DescargasService;
import crm.services.EntidadesTipoService;
import crm.utils.ArchivoAdjunto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;






@Controller
public class DescargasController {

    @Autowired
    private CarreraService carreraService;

    @Autowired
    private DescargasService descargasService;

    @Autowired
    private EntidadesTipoService entidadesTipoService;

    @Autowired
    private CompetenciaUsmempleoService competenciaUsmempleoService;









    // ------------------------------------------ CARRERAS------------------------------------------------------------

    /**
     * Despliega la vista para la descarga de los datos relacionados con las {@link crm.entities.Carrera}, para realizar
     * la seleccion de los parametros de busqueda
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de descargaOfertasLaboralCarrera
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carrera/descargar", method = RequestMethod.GET)
    public String descargasKPICarreras(Model model) {

        return "/descargas/descargaKPICarrera";
    }




    /**
     * Despliega la vista con los resultados de la busqueda de  {@link crm.entities.Carrera}, según una {@link crm.entities.Institucion}
     * seleccionada
     *
     * @param codInstitucion Id de la {@link crm.entities.Institucion} sobre la cual buscar
     *
     * @return Retorna la vista de descargaOfertasLaboralCarrera, con las {@link crm.entities.Carrera} perteneciente a la
     *          {@link crm.entities.Institucion} buscada
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carrera/buscarDescarga", method = RequestMethod.POST)
    public String descargasKPICarreras(@RequestParam(value = "codInstitucion") String codInstitucion,
                                    Model model) {

        List<Carrera> carreras = carreraService.buscarCarrerasPorCodInstitucion(Short.parseShort(codInstitucion));

        model.addAttribute("carreras", carreras);
        model.addAttribute("codInstitucion", codInstitucion);

        return "/descargas/descargaKPICarrera";
    }






    /**
     * Genera el archivo de descarga para las {@link crm.entities.Carrera} seleccionadas, uno para cada carrera seleccionada,
     * con los datos de las carreras
     *
     * @param idsCarreras Id de las {@link crm.entities.Carrera} sobre la cual generar el (los) archivos de descarga de atos
     * @param codInstitucion Id de la {@link crm.entities.Institucion} sobre la cual buscar
     *
     * @return Retorna el archivo generado para poder ser descargado desde el navegador
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carrera/generarDescarga", method = RequestMethod.POST)
    public void generarReporteKPICarreras(@RequestParam(value = "idsCarreras") String idsCarreras,
                                         @RequestParam(value = "codInstitucion") String codInstitucion,
                                         HttpServletResponse response) throws IOException {

        String[] codsCarrerasSplit;

        // id de carreras seleccionadas en los checkbox de mezclar
        if (idsCarreras != null) {
            codsCarrerasSplit = idsCarreras.split(",");
        }
        else{
            codsCarrerasSplit = new String[]{"21", "31"};
        }

        String rutaArchivoReporte = descargasService.crearArchivosReporteKPICarreras(codsCarrerasSplit, codInstitucion);

        // genera archivo adjunto
        ArchivoAdjunto atachment = new ArchivoAdjunto();
        atachment.generarArchivoAdjunto(response, rutaArchivoReporte);
    }




    // ------------------------------------------ EMPRESAS ------------------------------------------------------------

    /**
     * Despliega la vista para la selección de la {@link crm.entities.Empresa} a generar la descarga de datos
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de descargaEmpresa
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/descargar", method = RequestMethod.GET)
    public String descargasEmpresas(Model model) {

        return "/descargas/descargaEmpresa";
    }




    /**
     * Genera el archivo de descarga para las {@link crm.entities.Empresa} seleccionadas, uno para cada {@link crm.entities.Empresa} seleccionada,
     * con los datos de las empresas
     *
     * @param nombreEmpresa nombre de la empresa que se desea descargar los datos
     * @param response
     *
     * @return Retorna el archivo generado para poder ser descargado desde el navegador
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/descargar", method = RequestMethod.POST)
    public void generarReporteEmpresas( @RequestParam(value = "nombreEmpresa") String nombreEmpresa,
                                        @RequestParam(value = "incluirPublicadores", required = false) String incluirPublicadores,
                                        HttpServletResponse response) throws IOException {

        String rutaArchivoReporte = descargasService.crearArchivosReporteEmpresas(nombreEmpresa, incluirPublicadores);

        // genera archivo adjunto
        ArchivoAdjunto atachment = new ArchivoAdjunto();
        atachment.generarArchivoAdjunto(response, rutaArchivoReporte);
    }




    // ------------------------------------------ USUARIOS ------------------------------------------------------------

    /**
     * Despliega la vista para la descarga de los datos relacionados con los {@link crm.entities.Usuario}, para realizar
     * la seleccion de los parametros de busqueda
     *
     * @return Retorna la vista de descargaUsuario, con las {@link crm.entities.Carrera} perteneciente a la
     *          {@link crm.entities.Institucion} buscada
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/usuario/descargar", method = RequestMethod.GET)
    public String descargasUsuarios(Model model) {

        return "/descargas/descargaUsuario";
    }




    /**
     * Despliega la vista con los resultados de la busqueda de  {@link crm.entities.Carrera}, según una {@link crm.entities.Institucion}
     * seleccionada.
     *
     * @param codInstitucion Id de la {@link crm.entities.Institucion} sobre la cual buscar
     *
     * @return Retorna la vista de descargaUsuario, con las {@link crm.entities.Carrera} perteneciente a la
     *          {@link crm.entities.Institucion} buscada
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/usuario/buscarDescarga", method = RequestMethod.POST)
    public String descargasUsuarios(@RequestParam(value = "codInstitucion") String codInstitucion,
                                    Model model) {

        List<Carrera> carreras = carreraService.buscarCarrerasPorCodInstitucion(Short.parseShort(codInstitucion));

        model.addAttribute("carreras", carreras);
        model.addAttribute("codInstitucion", codInstitucion);

        return "/descargas/descargaUsuario";
    }




    /**
     * Genera el archivo de descarga para las {@link crm.entities.Carrera} seleccionadas, uno para cada carrera seleccionada,
     * con los datos de los usuarios pertenecientes a ella
     *
     * @param idsCarreras Id de las {@link crm.entities.Carrera} sobre la cual generar el (los) archivos de descarga de atos
     * @param codInstitucion Id de la {@link crm.entities.Institucion} sobre la cual buscar
     *
     * @return Retorna el archivo generado para poder ser descargado desde el navegador
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/usuario/generarDescarga", method = RequestMethod.POST)
    public void generarReporteUsuarios( @RequestParam(value = "idsCarreras") String idsCarreras,
                                        @RequestParam(value = "codInstitucion") String codInstitucion,
                                        HttpServletResponse response) throws IOException {

        String[] codsCarrerasSplit;

        // id de carreras seleccionadas en los checkbox de mezclar
        if (idsCarreras != null) {
            codsCarrerasSplit = idsCarreras.split(",");
        }
        else{
            codsCarrerasSplit = new String[]{"21", "31"};
        }

        String rutaArchivoReporte = descargasService.crearArchivosReporteUsuariosCarreras(codsCarrerasSplit, codInstitucion);

        // genera archivo adjunto
        ArchivoAdjunto atachment = new ArchivoAdjunto();
        atachment.generarArchivoAdjunto(response, rutaArchivoReporte);
    }





    // ------------------------------------------ OFERTAS LABORALES ----------------------------------------------------



    /**
     * Despliega la vista para la selección de los datos de las {@link crm.entities.OfertaLaboralUsmempleo} para generar la descarga de datos
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de descargaOfertasLaborales
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/ofertaLaboral/descargar", method = RequestMethod.GET)
    public String descargasOfertaLaboral(Model model) {

        List<Carrera> carreras = carreraService.buscarTodasCarrerasOrdenNombreCarreraAsc();

        List<TipoCompetencia> listaTipoCompetencias = entidadesTipoService.buscarTodosTipoCompetencia();

        List<CompetenciaUsmempleo> listaCompetenciasTransversales = competenciaUsmempleoService.buscarCompetenciaUsmempleoPorIdTipoCompetencia(Integer.parseInt("1"));
        List<CompetenciaUsmempleo> listaCompetenciasPorCargo = competenciaUsmempleoService.buscarCompetenciaUsmempleoPorIdTipoCompetencia(Integer.parseInt("2"));
        List<CompetenciaUsmempleo> listaCompetenciasPorArea = competenciaUsmempleoService.buscarCompetenciaUsmempleoPorIdTipoCompetencia(Integer.parseInt("3"));


        model.addAttribute("carreras", carreras);
        model.addAttribute("nombreTransversales", listaCompetenciasTransversales.get(0).getTipoCompetencia().getNombre());
        model.addAttribute("nombrePorCargo", listaCompetenciasPorCargo.get(0).getTipoCompetencia().getNombre());
        model.addAttribute("nombrePorArea", listaCompetenciasPorArea.get(0).getTipoCompetencia().getNombre());
        model.addAttribute("listaCompetenciasTransversales", listaCompetenciasTransversales);
        model.addAttribute("listaCompetenciasPorCargo", listaCompetenciasPorCargo);
        model.addAttribute("listaCompetenciasPorArea", listaCompetenciasPorArea);

        return "/descargas/descargaOfertaLaboral";
    }




    /**
     *
     * @param idsCarreras Id de las {@link crm.entities.Carrera} sobre la cual generar el (los) archivos de descarga de datos
     *
     * @return Retorna el archivo generado para poder ser descargado desde el navegador
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/ofertaLaboral/generarDescarga", method = RequestMethod.POST)
    public void generarReporteOfertaLaboral( @RequestParam(value = "idsCarreras") String idsCarreras,
                                            @RequestParam(value = "nombreEmpresa") String nombreEmpresa,
                                             @RequestParam(value = "fechaDesde") String fechaDesde,
                                             @RequestParam(value = "fechaHasta") String fechaHasta,
                                             @RequestParam(value = "idsCompetencias", required = false) String idsCompetencias,
                                            HttpServletResponse response) throws IOException {

        String[] codsCarrerasSplit = null;
        String[] idsCompetenciasSplit = null;

        // id de carreras seleccionadas en los checkbox de mezclar
        if (idsCarreras != null) {
            codsCarrerasSplit = idsCarreras.split(",");
        }

        if (idsCompetencias != null) {
            idsCompetenciasSplit = idsCompetencias.split(",");
        }

        String rutaArchivoReporte = descargasService.crearArchivosReporteOfertaLaboral(nombreEmpresa, codsCarrerasSplit, fechaDesde, fechaHasta, idsCompetenciasSplit);

        // genera archivo adjunto
        ArchivoAdjunto atachment = new ArchivoAdjunto();
        atachment.generarArchivoAdjunto(response, rutaArchivoReporte);
    }





}