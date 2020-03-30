package crm.controllers;

import com.google.gson.Gson;
import crm.entities.*;
import crm.services.CarreraService;
import crm.services.EntidadesTipoService;
import crm.services.IndicesService;
import crm.validators.CarreraValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

/**
 * Controlador que maneja las vistas para los diversos Indices generados en la plataforma
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class IndicesController {


    /**
     * Servicio que maneja los diversos indices generados en la plataforma
     */
    @Autowired
    private IndicesService indicesService;

    /**
     * Servicio relacionado a todas las entidades Tipo.
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Carrera}.
     */
    @Autowired
    private CarreraService carreraService;

    /**
     * Tamanio por defecto a mostrar en los listados (para los PageRequest)
     */
    private static final int PAGE_SIZE_DEFAULT = 20;

    /**
     * Tamanio por defecto a mostrar en los listados
     */
    private static final String TAMANIO_LISTADO_DEFAULT = "20";

    /**
     * Tipo de Vigencia por defecto, para la generación de los indices (por defecto que se muestren Todos - Vigentes y no Vigentes)
     */
    private static final String COD_VIGENCIA_DEFAULT = "0";

    /**
     * Tipo de Compromiso por defecto, para la generación de los indices (por defecto que se muestren Todos - Donacion - Auspicio y Venta de Servicios)
     */
    private static final String COD_COMPROMISO_DEFAULT = "0";

    /**
     * Anio por defecto (anio actual), para la generación de los indices
     */
    private static final String ANIO_DEFAULT = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

    /**
     * Orden por defecto (ascendente/descendente) de los listados, para la generación de los indices (orden 1: Descendente, 2:Ascendente)
     */
    private static final String ORDEN_DEFAULT = "1";

    /**
     * Codido de la Institucion por defecto  de los listados, (25 : UTFSM)
     */
    private static final String INSTITUCION_DEFAULT = "25";

    /**
     * Codido de la Carrera por defecto  de los listados, (0 : Todas)
     */
    private static final String CARRERA_DEFAULT = "0";

    /**
     * Codido de la Carrera por defecto  de los listados, (2 : Ingles)
     */
    private static final String IDIOMA_DEFAULT = "2";








    // --------------------------------- EMPLEOS ---------------------------------




    /**
     * Despliega la vista para la creación del indice de cantidad de ofertas laborales por carrera, para la selección de
     * los valores de la busqueda deseada
     * (Se realiza una busqueda por defecto según ANIO_DEFAULT, COD_VIGENCIA_DEFAULT y PAGE_SIZE_DEFAULT)
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadOfertasLaboralesPorCarrera
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadOfertasLaboralesPorCarrera", method = RequestMethod.GET)
    public String cantidadOfertasLaboraresPorCarrera(Model model) {

        Page<List<String>> cantidadOfertasPorCarrera = indicesService.indiceCantidadOfertaLaboralPorCarrera(COD_VIGENCIA_DEFAULT, ANIO_DEFAULT, PAGE_SIZE_DEFAULT, 0);

        this.generatePagination(model, cantidadOfertasPorCarrera);

        model.addAttribute("cantidadOfertasPorCarrera", cantidadOfertasPorCarrera);
        model.addAttribute("tipoVigencia", COD_VIGENCIA_DEFAULT);
        model.addAttribute("anio", ANIO_DEFAULT);

        return "/indices/empleos/cantidadOfertasLaboralesPorCarrera";
    }




    /**
     * Despliega la vista con los resultados del indice de cantidad por {@link crm.entities.Carrera} de
     * {@link crm.entities.OfertaLaboralUsmempleo}, según el {@link crm.entities.TipoVigencia} y año seleccionados en la vista .
     *
     * @param tipoVigencia Tipo de Vigencia sobre la cual se desea realizar la busqueda
     * @param anio Año sobre el cual se desea realizar la busqueda
     * @param pagina Numero de pagina del listado de la busqueda.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadOfertasLaboralesPorCarrera
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/buscarCantidadOfertasLaboralesPorCarrera", method = RequestMethod.GET)
    public String buscarCantidadOfertasLaboralesPorCarrera(@RequestParam ("tipoVigencia") String tipoVigencia,
                                                           @RequestParam ("anio") String anio,
                                                           @RequestParam("pagina") String pagina,
                                                           Model model) {

        Integer numeroPagina;
        try {
            numeroPagina = Integer.parseInt(pagina);
        }
        catch(Exception e){
            numeroPagina = 1;
        }
        if (numeroPagina <= 0) {
            numeroPagina = 1;
        }

        Page<List<String>> cantidadOfertasPorCarrera = indicesService.indiceCantidadOfertaLaboralPorCarrera(tipoVigencia, anio, PAGE_SIZE_DEFAULT, numeroPagina - 1);

        this.generatePagination(model, cantidadOfertasPorCarrera);

        model.addAttribute("cantidadOfertasPorCarrera", cantidadOfertasPorCarrera);
        model.addAttribute("tipoVigencia", tipoVigencia);
        model.addAttribute("anio", anio);

        return "/indices/empleos/cantidadOfertasLaboralesPorCarrera";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de empresas que ofrecen empleo, para la selección de los valores
     * de la busqueda deseada
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadEmpresasOfrecenEmpleo
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadEmpresasOfrecenEmpleo", method = RequestMethod.GET)
    public String cantidadEmpresasOfrecenEmpleo( Model model) {

        // Array por mes, con la cantidad de empresas
        ArrayList<String>   empresasOfrecenEmpleoPorMes = indicesService.indiceCantidadEmpresasOfrecenEmpleo(COD_VIGENCIA_DEFAULT, ANIO_DEFAULT);
        ArrayList<String> cantidadesEmpresasOfrecenEmpleoPorMes = new ArrayList<String>();

        //obtencion de cantidades (utilizados en el grafico)
        for (int indice = 0; indice < empresasOfrecenEmpleoPorMes.size(); indice = indice + 2){
            cantidadesEmpresasOfrecenEmpleoPorMes.add(empresasOfrecenEmpleoPorMes.get(indice));
        }

        String cantidadesEmpresasOfrecenEmpleoPorMesJSON = new Gson().toJson(cantidadesEmpresasOfrecenEmpleoPorMes);

        model.addAttribute("empresasOfrecenEmpleoPorMes", empresasOfrecenEmpleoPorMes);                                     // utilizado en la tabla
        model.addAttribute("cantidadesEmpresasOfrecenEmpleoPorMesJSON", cantidadesEmpresasOfrecenEmpleoPorMesJSON);         // utilizado en el grafico
        model.addAttribute("tipoVigencia", COD_VIGENCIA_DEFAULT);
        model.addAttribute("anio", ANIO_DEFAULT);

        return "/indices/empleos/cantidadEmpresasOfrecenEmpleo";
    }




    /**
     * Despliega la vista con los resultados del indice de cantidad por mes de {@link crm.entities.Empresa} que tienen
     * {@link crm.entities.OfertaLaboralUsmempleo}, según un año y tipo vigencia seleccionado en la vista.
     * (Solamente son obtenidos los meses que cuenten ofertas de empresas con los parametros de busqueda especificados.)
     *
     * @param tipoVigencia Tipo de Vigencia sobre la cual se desea realizar la busqueda
     * @param anio Año sobre el cual se desea realizar la busqueda
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadEmpresasOfrecenEmpleo
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/buscarCantidadEmpresasOfrecenEmpleo", method = RequestMethod.GET)
    public String buscarCantidadEmpresasOfrecenEmpleo(@RequestParam ("tipoVigencia") String tipoVigencia,
                                                      @RequestParam ("anio") String anio,
                                                      Model model) {

        // Array por mes, con la cantidad de empresas
        ArrayList<String> empresasOfrecenEmpleoPorMes = indicesService.indiceCantidadEmpresasOfrecenEmpleo(tipoVigencia, anio);
        ArrayList<String> cantidadesEmpresasOfrecenEmpleoPorMes = new ArrayList<String>();

        //obtencion de cantidades (utilizados en el grafico)
        for (int indice = 0; indice < empresasOfrecenEmpleoPorMes.size(); indice = indice + 2){
            cantidadesEmpresasOfrecenEmpleoPorMes.add(empresasOfrecenEmpleoPorMes.get(indice));
        }

        String cantidadesEmpresasOfrecenEmpleoPorMesJSON = new Gson().toJson(cantidadesEmpresasOfrecenEmpleoPorMes);

        model.addAttribute("empresasOfrecenEmpleoPorMes", empresasOfrecenEmpleoPorMes);                                     // utilizado en la tabla
        model.addAttribute("cantidadesEmpresasOfrecenEmpleoPorMesJSON", cantidadesEmpresasOfrecenEmpleoPorMesJSON);         // utilizado en el grafico
        model.addAttribute("tipoVigencia", tipoVigencia);
        model.addAttribute("anio", anio);

        return "/indices/empleos/cantidadEmpresasOfrecenEmpleo";
    }



    /**
     * Despliega la vista para la creacion de indice de cantidad de {@link crm.entities.OfertaLaboralUsmempleo} clasificados por
     * {@link crm.entities.TipoOferta}, para la selección de los valores de la busqueda deseada
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadOfertasLaboralesPorTipoOferta
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadOfertasLaboralesPorTipoOferta", method = RequestMethod.GET)
    public String cantidadOfertasLaboraresPorTipoOferta (Model model) {

        // por cada tipo de oferta (tipo de oferta, cantidad de ofertas laborales)
        ArrayList<ArrayList<String>>  ofertasPorTipoOferta = indicesService.indiceCantidadOfertaLaboralPorTipoOferta(COD_VIGENCIA_DEFAULT, ANIO_DEFAULT);

        String ofertasPorTipoOfertaJSON = new Gson().toJson(ofertasPorTipoOferta);

        model.addAttribute("ofertasPorTipoOferta", ofertasPorTipoOferta);               // utilizado en la tabla
        model.addAttribute("ofertasPorTipoOfertaJSON", ofertasPorTipoOfertaJSON);       // utilizado en el grafico

        model.addAttribute("tipoVigencia", COD_VIGENCIA_DEFAULT);
        model.addAttribute("anio", ANIO_DEFAULT);

        return "/indices/empleos/cantidadOfertasLaboralesPorTipoOferta";
    }




    /**
     * Despliega la vista con los resultados del indice por mes de la cantidad de {@link crm.entities.OfertaLaboralUsmempleo}
     * clasificados por {@link crm.entities.TipoOferta}, según un año y tipo vigencia seleccionado en la vista
     *
     * @param tipoVigencia Tipo de busqueda sobre la cual se desean buscar ofertas laborales
     * @param anio Año sobre el cual se desean mostrar los indices
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadOfertasLaboralesPorTipoOferta
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/buscarCantidadOfertasLaboralesPorTipoOferta", method = RequestMethod.GET)
    public String buscarCantidadOfertasLaboralesPorTipoOferta(@RequestParam ("tipoVigencia") String tipoVigencia,
                                                              @RequestParam ("anio") String anio,
                                                              Model model) {

        // por cada tipo de oferta (tipo de oferta, cantidad de ofertas laborales)
        ArrayList<ArrayList<String>>  ofertasPorTipoOferta = indicesService.indiceCantidadOfertaLaboralPorTipoOferta(tipoVigencia, anio);

        String ofertasPorTipoOfertaJSON = new Gson().toJson(ofertasPorTipoOferta);

        model.addAttribute("ofertasPorTipoOferta", ofertasPorTipoOferta);               // utilizado en la tabla
        model.addAttribute("ofertasPorTipoOfertaJSON", ofertasPorTipoOfertaJSON);       // utilizado en el grafico

        model.addAttribute("tipoVigencia", tipoVigencia);
        model.addAttribute("anio", anio);

        return "/indices/empleos/cantidadOfertasLaboralesPorTipoOferta";
    }



    /**
     * Despliega la vista para la creacion de indice del ranking de las carreras clasificados por demanda, para la selección de
     * los valores de la busqueda deseada
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de rankingCarrerasPorDemanda
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/rankingCarrerasPorDemanda", method = RequestMethod.GET)
    public String rankingCarrerasPorDemanda (Model model) {

        // por carrera (nombre de la carrera, cantidad de ofertas asociadas)
        ArrayList<ArrayList<String>>  carrerasPorDemanda = indicesService.indiceRankingCarrerasPorDemanda(ANIO_DEFAULT, ORDEN_DEFAULT, TAMANIO_LISTADO_DEFAULT);

        String carrerasPorDemandaJSON = new Gson().toJson(carrerasPorDemanda);

        model.addAttribute("carrerasPorDemanda", carrerasPorDemanda);               // utilizado en la tabla
        model.addAttribute("carrerasPorDemandaJSON", carrerasPorDemandaJSON);       // utilizado en el grafico

        // valores por defecto seleccionados en el vista
        model.addAttribute("anio", ANIO_DEFAULT);
        model.addAttribute("orden", ORDEN_DEFAULT);
        model.addAttribute("cantidadMostrar", TAMANIO_LISTADO_DEFAULT);

        return "/indices/empleos/rankingCarrerasPorDemanda";
    }




    /**
     * Despliega la vista con los resultados del indice de ranking de las {@link crm.entities.Carrera} clasificados por demanda
     * de {@link crm.entities.OfertaLaboralUsmempleo}, según el año, cantidad y orden seleccionados en la vista
     *
     * @param anio Año sobre el cual se desean mostrar los indices
     * @param orden Orden del ranking; mayor cantidad o menor cantidad de ofertas
     * @param cantidadMostrar Cantidad carreras a mostrar como resultado del indice
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de rankingCarrerasPorDemanda
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/buscarRankingCarrerasPorDemanda", method = RequestMethod.GET)
    public String buscarRankingCarrerasPorDemanda(@RequestParam ("anio") String anio,
                                                  @RequestParam ("orden") String orden,
                                                  @RequestParam ("cantidadMostrar") String cantidadMostrar,
                                                  Model model) {


        // por carrera (nombre de la carrera, cantidad de ofertas asociadas)
        ArrayList<ArrayList<String>>  carrerasPorDemanda = indicesService.indiceRankingCarrerasPorDemanda(anio, orden, cantidadMostrar);

        String carrerasPorDemandaJSON = new Gson().toJson(carrerasPorDemanda);

        model.addAttribute("carrerasPorDemanda", carrerasPorDemanda);               // utilizado en la tabla
        model.addAttribute("carrerasPorDemandaJSON", carrerasPorDemandaJSON);       // utilizado en el grafico

        model.addAttribute("anio", anio);
        model.addAttribute("orden", orden);
        model.addAttribute("cantidadMostrar", cantidadMostrar);

        return "/indices/empleos/rankingCarrerasPorDemanda";
    }



    /**
     * Despliega la vista para la creacion de indice del ranking de las carreras clasificados por vacantes, para la selección de
     * los valores de la busqueda deseada
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de rankingCarrerasPorVacantes
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/rankingCarrerasPorVacantes", method = RequestMethod.GET)
    public String rankingCarrerasPorVacantes (Model model) {


        // por carrera (nombre de la carrera, cantidad de vacantes asociadas)
        ArrayList<ArrayList<String>>  carrerasPorVacantes = indicesService.indiceRankingCarrerasPorVacantes(ANIO_DEFAULT, ORDEN_DEFAULT, TAMANIO_LISTADO_DEFAULT);

        String carrerasPorVacantesJSON = new Gson().toJson(carrerasPorVacantes);

        model.addAttribute("carrerasPorVacantes", carrerasPorVacantes);              // utilizado en la tabla
        model.addAttribute("carrerasPorVacantesJSON", carrerasPorVacantesJSON);       // utilizado en el grafico

        // valores por defecto seleccionados en el vista
        model.addAttribute("anio", ANIO_DEFAULT);
        model.addAttribute("orden", ORDEN_DEFAULT);
        model.addAttribute("cantidadMostrar", TAMANIO_LISTADO_DEFAULT);

        return "/indices/empleos/rankingCarrerasPorVacantes";
    }




    /**
     * Despliega la vista con los resultados del indice de ranking de las {@link crm.entities.Carrera} clasificados por
     * vacantes, declaradas en {@link crm.entities.OfertaLaboralUsmempleo}, según un año, cantidad y orden seleccionados
     * en la vista
     *
     * @param orden Orden del ranking; mayor cantidad o menor cantidad de vacantes
     * @param anio Año sobre el cual se desean mostrar los indices
     * @param cantidadMostrar Cantidad carreras a mostrar como resultado del indice
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de rankingCarrerasPorVacantes
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/buscarRankingCarrerasPorVacantes", method = RequestMethod.GET)
    public String buscarRankingCarrerasPorVacantes(@RequestParam ("anio") String anio,
                                                   @RequestParam ("orden") String orden,
                                                   @RequestParam ("cantidadMostrar") String cantidadMostrar,
                                                   Model model) {

        // por carrera (nombre de la carrera, cantidad de vacantes asociadas)
        ArrayList<ArrayList<String>>  carrerasPorVacantes = indicesService.indiceRankingCarrerasPorVacantes(anio, orden, cantidadMostrar);

        String carrerasPorVacantesJSON = new Gson().toJson(carrerasPorVacantes);

        model.addAttribute("carrerasPorVacantes", carrerasPorVacantes);              // utilizado en la tabla
        model.addAttribute("carrerasPorVacantesJSON", carrerasPorVacantesJSON);       // utilizado en el grafico

        model.addAttribute("anio", anio);
        model.addAttribute("orden", orden);
        model.addAttribute("cantidadMostrar", cantidadMostrar);

        return "/indices/empleos/rankingCarrerasPorVacantes";
    }



    /**
     * Despliega la vista para la creacion de indice del ranking de las carreras clasificados por el salario promedio de las
     * ofertas laborales, para la selección de los valores de la busqueda deseada
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de rankingCarrerasPorSalario
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/rankingCarrerasPorSalario", method = RequestMethod.GET)
    public String rankingCarrerasPorSalario (Model model) {

        // por carrera (nombre de la carrera, ... salario promedio por cada tipo de oferta... , cantidad de vacantes asociadas, promedio total salario )
        ArrayList<ArrayList<String>>  carrerasPorSalario = indicesService.indiceRankingCarrerasPorSalario(ANIO_DEFAULT, ORDEN_DEFAULT, TAMANIO_LISTADO_DEFAULT, "0", "1000000");

        model.addAttribute("carrerasPorSalario", carrerasPorSalario);

        // valores por defecto seleccionados en el vista
        model.addAttribute("anio", ANIO_DEFAULT);
        model.addAttribute("orden", ORDEN_DEFAULT);
        model.addAttribute("cantidadMostrar", TAMANIO_LISTADO_DEFAULT);
        model.addAttribute("rangoInicial", "0");
        model.addAttribute("rangoFinal", "1000000");

        return "/indices/empleos/rankingCarrerasPorSalario";
    }




    /**
     * Despliega la vista con los resultados del indice de ranking de las {@link crm.entities.Carrera} clasificados por
     * salario declarado en {@link crm.entities.OfertaLaboralUsmempleo}, según un año, cantidad y orden
     * seleccionados en la vista
     *
     * @param orden Orden del ranking; mayor cantidad o menor cantidad de vacantes
     * @param anio Año sobre el cual se desean mostrar los indices
     * @param cantidadMostrar Cantidad carreras a mostrar como resultado del indice
     * @param rangoInicial Inicio del rango promedio de salario de las carreras a mostrar como resultado del indice
     * @param rangoFinal Final del rango promedio de salario de las carreras a mostrar como resultado del indice
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de rankingCarrerasPorSalario
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/buscarRankingCarrerasPorSalario", method = RequestMethod.GET)
    public String buscarRankingCarrerasPorSalario(@RequestParam ("anio") String anio,
                                                  @RequestParam ("orden") String orden,
                                                  @RequestParam ("cantidadMostrar") String cantidadMostrar,
                                                  @RequestParam ("rangoInicial") String rangoInicial,
                                                  @RequestParam ("rangoFinal") String rangoFinal,
                                                  Model model) {

        // Revisa si vista posee errores
        if(rangoInicial.length() == 0 || rangoFinal.length() == 0){
            if (rangoInicial.length() == 0 ) {
                model.addAttribute("errorRangoInicial", "Ingrese Inicio del Rango de Salario");
            }

            if (rangoFinal.length() == 0 ) {
                model.addAttribute("errorRangoFinal", "Ingrese Fin del Rango de Salario");
            }

            model.addAttribute("anio", ANIO_DEFAULT);
            model.addAttribute("orden", ORDEN_DEFAULT);
            model.addAttribute("cantidadMostrar", TAMANIO_LISTADO_DEFAULT);
            model.addAttribute("rangoInicial", "0");
            model.addAttribute("rangoFinal", "1000000");

            return "/indices/empleos/rankingCarrerasPorSalario";
        }

        // por carrera (nombre de la carrera, ... salario promedio por cada tipo de oferta... , cantidad de vacantes asociadas, promedio total salario )
        ArrayList<ArrayList<String>>  carrerasPorSalario = indicesService.indiceRankingCarrerasPorSalario(anio, orden, cantidadMostrar, rangoInicial, rangoFinal);

        model.addAttribute("carrerasPorSalario", carrerasPorSalario);

        model.addAttribute("anio", anio);
        model.addAttribute("orden", orden);
        model.addAttribute("cantidadMostrar", cantidadMostrar);
        model.addAttribute("rangoInicial", rangoInicial);
        model.addAttribute("rangoFinal", rangoFinal);

        return "/indices/empleos/rankingCarrerasPorSalario";
    }




    /**
     * Despliega la vista para la creacion de indice de la cantidad de {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     * a {@link crm.entities.OfertaLaboralUsmempleo}
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPostulacionesOfertasEmpleo
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadPostulacionesOfertasEmpleo", method = RequestMethod.GET)
    public String cantidadPostulacionesOfertasEmpleo (Model model) {

        // valores por defecto seleccionados en el vista
        ArrayList<ArrayList<String>>  postulacionesOfertasYVacantesEmpleo = indicesService.indiceCantidadPostulacionesOfertasEmpleo(COD_VIGENCIA_DEFAULT, ANIO_DEFAULT);

        String postulacionesOfertasYVacantesJSON = new Gson().toJson(postulacionesOfertasYVacantesEmpleo);

        model.addAttribute("postulacionesOfertasYVacantesEmpleo", postulacionesOfertasYVacantesEmpleo);           //   utilizado en la tabla
        model.addAttribute("postulacionesOfertasYVacantesJSON", postulacionesOfertasYVacantesJSON);               //   utilizado en el grafico

        model.addAttribute("tipoVigencia", COD_VIGENCIA_DEFAULT);
        model.addAttribute("anio", ANIO_DEFAULT);

        return "/indices/empleos/cantidadPostulacionesOfertasEmpleo";
    }




    /**
     * Despliega la vista con los resultados del indice de la cantidad de las {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     * a {@link crm.entities.OfertaLaboralUsmempleo}, según un año de la {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     * y vigencia de la {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @param tipoVigencia Tipo de Vigencia sobre la cual se desean buscar ofertas laborales
     * @param anio Año sobre el cual se desean mostrar los indices
     *
     * @return Retorna la vista de cantidadPostulacionesOfertasEmpleo
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/buscarCantidadPostulacionesOfertasEmpleo", method = RequestMethod.GET)
    public String buscarCantidadPostulacionesOfertasEmpleo( @RequestParam ("tipoVigencia") String tipoVigencia,
                                                            @RequestParam ("anio") String anio,
                                                            Model model) {

        // por cada mes
        ArrayList<ArrayList<String>>  postulacionesOfertasYVacantesEmpleo = indicesService.indiceCantidadPostulacionesOfertasEmpleo(tipoVigencia, anio);

        String postulacionesOfertasYVacantesJSON = new Gson().toJson(postulacionesOfertasYVacantesEmpleo);

        model.addAttribute("postulacionesOfertasYVacantesEmpleo", postulacionesOfertasYVacantesEmpleo);           //   utilizado en la tabla
        model.addAttribute("postulacionesOfertasYVacantesJSON", postulacionesOfertasYVacantesJSON);               //   utilizado en el grafico

        model.addAttribute("tipoVigencia", tipoVigencia);
        model.addAttribute("anio", anio);

        return "/indices/empleos/cantidadPostulacionesOfertasEmpleo";
    }




    /**
     * Despliega la vista para la creacion de indice de la cantidad de {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     * a {@link crm.entities.OfertaLaboralUsmempleo} clasificados por región de la {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPostulacionesOfertasEmpleoPorRegion
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadPostulacionesOfertasEmpleoPorRegion", method = RequestMethod.GET)
    public String cantidadPostulacionesOfertasEmpleoPorRegion (Model model) {

        // por cada region
        ArrayList<ArrayList<String>>  postulacionesOfertasYVacantesEmpleoPorRegion = indicesService.indiceCantidadPostulacionesOfertasEmpleoPorRegionDetalleCantidadOfertasVacantes(COD_VIGENCIA_DEFAULT, ANIO_DEFAULT);

        String postulacionesOfertasYVacantesEmpleoPorRegionJSON = new Gson().toJson(postulacionesOfertasYVacantesEmpleoPorRegion);      // utilizado en el grafico

        model.addAttribute("postulacionesOfertasYVacantesEmpleoPorRegion", postulacionesOfertasYVacantesEmpleoPorRegion);               //   utilizado en la tabla
        model.addAttribute("postulacionesOfertasYVacantesEmpleoPorRegionJSON", postulacionesOfertasYVacantesEmpleoPorRegionJSON);       // utilizado en el grafico

        model.addAttribute("tipoVigencia", COD_VIGENCIA_DEFAULT);
        model.addAttribute("anio", ANIO_DEFAULT);

        return "/indices/empleos/cantidadPostulacionesOfertasEmpleoPorRegion";
    }




    /**
     * Despliega la vista con los resultados del indice de ranking de las {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     * a {@link crm.entities.OfertaLaboralUsmempleo} clasificados por región de la Oferta, según un año de la
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo} y vigencia de la {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @param tipoVigencia Tipo de Vigencia sobre la cual se desean buscar ofertas laborales
     * @param anio Año sobre el cual se desean mostrar los indices
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPostulacionesOfertasEmpleoPorRegion
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/buscarCantidadPostulacionesOfertasEmpleoPorRegion", method = RequestMethod.GET)
    public String buscarCantidadPostulacionesOfertasEmpleoPorRegion ( @RequestParam ("tipoVigencia") String tipoVigencia,
                                                                      @RequestParam ("anio") String anio,
                                                                      Model model) {

        // por cada region
        ArrayList<ArrayList<String>>  postulacionesOfertasYVacantesEmpleoPorRegion = indicesService.indiceCantidadPostulacionesOfertasEmpleoPorRegionDetalleCantidadOfertasVacantes(tipoVigencia, anio);

        String postulacionesOfertasYVacantesEmpleoPorRegionJSON = new Gson().toJson(postulacionesOfertasYVacantesEmpleoPorRegion);

        model.addAttribute("postulacionesOfertasYVacantesEmpleoPorRegion", postulacionesOfertasYVacantesEmpleoPorRegion);               //   utilizado en la tabla
        model.addAttribute("postulacionesOfertasYVacantesEmpleoPorRegionJSON", postulacionesOfertasYVacantesEmpleoPorRegionJSON);       // utilizado en el grafico

        model.addAttribute("tipoVigencia", tipoVigencia);
        model.addAttribute("anio", anio);

        return "/indices/empleos/cantidadPostulacionesOfertasEmpleoPorRegion";
    }



    /**
     * Despliega la vista para la creación del indice de cantidad de {@link crm.entities.PostulacionOfertaLaboralUsmempleo} a
     * {@link crm.entities.OfertaLaboralUsmempleo}, para la selección de los parámetros de busqueda del indice
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPostulacionesOfertasEmpleoPorTipoOferta
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadPostulacionesOfertasEmpleoPorTipoOferta", method = RequestMethod.GET)
    public String cantidadPostulacionesOfertasEmpleoPorTipoOferta (Model model) {

        // por cada tipo oferta
        ArrayList<ArrayList<String>>  postulacionesOfertasYVacantesEmpleoPorTipoOferta = indicesService.indiceCantidadPostulacionesOfertasEmpleoPorTipoOferta(COD_VIGENCIA_DEFAULT, ANIO_DEFAULT);

        String postulacionesOfertasYVacantesEmpleoPorTipoOfertaJSON = new Gson().toJson(postulacionesOfertasYVacantesEmpleoPorTipoOferta);

        model.addAttribute("postulacionesOfertasYVacantesEmpleoPorTipoOferta", postulacionesOfertasYVacantesEmpleoPorTipoOferta);                 //   utilizado en la tabla
        model.addAttribute("postulacionesOfertasYVacantesEmpleoPorTipoOfertaJSON", postulacionesOfertasYVacantesEmpleoPorTipoOfertaJSON);         //   utilizado en el grafico

        // valores por defecto seleccionados en el vista
        model.addAttribute("tipoVigencia", COD_VIGENCIA_DEFAULT);
        model.addAttribute("anio", ANIO_DEFAULT);

        return "/indices/empleos/cantidadPostulacionesOfertasEmpleoPorTipoOferta";
    }




    /**
     * Despliega la vista con los resultados del indice de cantidad de {@link crm.entities.PostulacionOfertaLaboralUsmempleo} a
     * {@link crm.entities.OfertaLaboralUsmempleo} realizando una busqueda según un año y tipoVigencia especificados como parametros.
     *
     * @param tipoVigencia Tipo de busqueda sobre la cual se desea mostrar el indice
     * @param anio Año sobre el cual se desean mostrar los indices
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPostulacionesOfertasEmpleoPorTipoOferta con los resultados del indice
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/buscarCantidadPostulacionesOfertasEmpleoPorTipoOferta", method = RequestMethod.GET)
    public String buscarCantidadPostulacionesOfertasEmpleoPorTipoOferta(@RequestParam ("tipoVigencia") String tipoVigencia,
                                                                        @RequestParam ("anio") String anio,
                                                                        Model model) {

        // por cada tipo oferta
        ArrayList<ArrayList<String>>  postulacionesOfertasYVacantesEmpleoPorTipoOferta = indicesService.indiceCantidadPostulacionesOfertasEmpleoPorTipoOferta(tipoVigencia, anio);

        String postulacionesOfertasYVacantesEmpleoPorTipoOfertaJSON = new Gson().toJson(postulacionesOfertasYVacantesEmpleoPorTipoOferta);

        model.addAttribute("postulacionesOfertasYVacantesEmpleoPorTipoOferta", postulacionesOfertasYVacantesEmpleoPorTipoOferta);                 //   utilizado en la tabla
        model.addAttribute("postulacionesOfertasYVacantesEmpleoPorTipoOfertaJSON", postulacionesOfertasYVacantesEmpleoPorTipoOfertaJSON);         //   utilizado en el grafico


        model.addAttribute("tipoVigencia", tipoVigencia);
        model.addAttribute("anio", anio);

        return "/indices/empleos/cantidadPostulacionesOfertasEmpleoPorTipoOferta";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de {@link crm.entities.Usuario} que han realizado
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo}, según el rango de edad al que pertenezca el
     * {@link crm.entities.Usuario} que realizó la postulacion. Por defecto se desplega inmediatamente la vista con
     * los resultados del año actual
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadUsuariosPostulantesOfertasEmpleoPorEdad
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadUsuariosPostulantesOfertasEmpleoPorEdad", method = RequestMethod.GET)
    public String cantidadPostulacionesPorEdad (Model model) {

        // por cada rango de edad
        ArrayList<ArrayList<String>>  usuariosPostulantesPorEdad = indicesService.cantidadUsuariosPostulantesPorEdad(ANIO_DEFAULT);

        // valores por defecto seleccionados en el vista
        model.addAttribute("anio", ANIO_DEFAULT);
        model.addAttribute("usuariosPostulantesPorEdad", usuariosPostulantesPorEdad);

        return "/indices/empleos/cantidadUsuariosPostulantesOfertasEmpleoPorEdad";
    }



    /**
     * Despliega la vista con los resultados del indice de cantidad de {@link crm.entities.Usuario} que han realizado
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo}, según el rango de edad al que pertenezca el
     * {@link crm.entities.Usuario} que realizó la postulacion. realizando la busqueda según el año de la
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo} seleccionado en la vista
     *
     * @param anio Anio sobre el cual se desea realizar la busqueda
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadUsuariosPostulantesOfertasEmpleoPorEdad
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadUsuariosPostulantesOfertasEmpleoPorEdad", method = RequestMethod.POST)
    public String cantidadPostulacionesPorEdad (@RequestParam ("anio") String anio,
                                                Model model) {

        // por cada rango de edad
        ArrayList<ArrayList<String>>  usuariosPostulantesPorEdad = indicesService.cantidadUsuariosPostulantesPorEdad(anio);

        // valores por defecto seleccionados en el vista
        model.addAttribute("anio", anio);
        model.addAttribute("usuariosPostulantesPorEdad", usuariosPostulantesPorEdad);

        return "/indices/empleos/cantidadUsuariosPostulantesOfertasEmpleoPorEdad";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de {@link crm.entities.Usuario} que han realizado
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo}, según la {@link crm.entities.TipoSituacionLaboral} del
     * {@link crm.entities.Usuario} que realizó la postulacion. Por defecto se desplega inmediatamente la vista con
     * los resultados del año actual
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadUsuariosPostulantesOfertasEmpleoPorSituacionLaboral
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadUsuariosPostulantesOfertasEmpleoPorSituacionLaboral", method = RequestMethod.GET)
    public String cantidadPostulacionesPorSituacionLaboral (Model model) {

        // por cada TipoSituacionLaboral
        ArrayList<ArrayList<String>>  usuariosPostulantesPorSituacionLaboral = indicesService.cantidadUsuariosPostulantesPorSituacionLaboral(ANIO_DEFAULT);

        // valores por defecto seleccionados en el vista
        model.addAttribute("anio", ANIO_DEFAULT);
        model.addAttribute("usuariosPostulantesPorSituacionLaboral", usuariosPostulantesPorSituacionLaboral);

        return "/indices/empleos/cantidadUsuariosPostulantesOfertasEmpleoPorSituacionLaboral";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de {@link crm.entities.Usuario} que han realizado
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo}, según la {@link crm.entities.TipoSituacionLaboral} del
     * {@link crm.entities.Usuario} que realizó la postulacion, realizando la busqueda según el año de la
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo} seleccionado en la vista
     *
     * @param anio Anio sobre el cual se desea realizar la busqueda
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadUsuariosPostulantesOfertasEmpleoPorSituacionLaboral
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadUsuariosPostulantesOfertasEmpleoPorSituacionLaboral", method = RequestMethod.POST)
    public String cantidadPostulacionesPorSituacionLaboral (@RequestParam ("anio") String anio,
                                                            Model model) {

        // por cada TipoSituacionLaboral
        ArrayList<ArrayList<String>>  usuariosPostulantesPorSituacionLaboral = indicesService.cantidadUsuariosPostulantesPorSituacionLaboral(anio);

        // valores por defecto seleccionados en el vista
        model.addAttribute("anio", anio);
        model.addAttribute("usuariosPostulantesPorSituacionLaboral", usuariosPostulantesPorSituacionLaboral);

        return "/indices/empleos/cantidadUsuariosPostulantesOfertasEmpleoPorSituacionLaboral";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de {@link crm.entities.Usuario} que han realizado
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo}, según la {@link crm.entities.Region} del
     * {@link crm.entities.Usuario} que realizó la postulacion. Por defecto se desplega inmediatamente la vista con
     * los resultados del año actual
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPostulacionOfertasEmpleoPorRegion
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadUsuariosPostulantesOfertasEmpleoPorRegionYProvincia", method = RequestMethod.GET)
    public String cantidadPostulacionesPorRegion (Model model) {

        // por cada Region
        ArrayList<ArrayList<String>>  usuariosPostulantesPorRegion = indicesService.cantidadUsuariosPostulantesPorRegion(ANIO_DEFAULT);

        // por cada Provincia
        ArrayList<ArrayList<String>>  usuariosPostulantesPorProvincia = indicesService.cantidadUsuariosPostulantesPorProvincia(ANIO_DEFAULT);

        // valores por defecto seleccionados en el vista
        model.addAttribute("anio", ANIO_DEFAULT);
        model.addAttribute("usuariosPostulantesPorRegion", usuariosPostulantesPorRegion);
        model.addAttribute("usuariosPostulantesPorProvincia", usuariosPostulantesPorProvincia);

        return "/indices/empleos/cantidadUsuariosPostulantesOfertasEmpleoPorRegionYProvincia";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de {@link crm.entities.Usuario} que han realizado
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo}, según la {@link crm.entities.Region} del
     * {@link crm.entities.Usuario} que realizó la postulacion, realizando la busqueda según el año de la
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo} seleccionado en la vista
     *
     * @param anio Anio sobre el cual se desea realizar la busqueda
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPostulacionOfertasEmpleoPorRegion
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadUsuariosPostulantesOfertasEmpleoPorRegionYProvincia", method = RequestMethod.POST)
    public String cantidadPostulacionesPorRegion (@RequestParam ("anio") String anio,
                                                            Model model) {

        // por cada Region
        ArrayList<ArrayList<String>>  usuariosPostulantesPorRegion = indicesService.cantidadUsuariosPostulantesPorRegion(anio);

        // por cada Provincia
        ArrayList<ArrayList<String>>  usuariosPostulantesPorProvincia = indicesService.cantidadUsuariosPostulantesPorProvincia(anio);

        // valores por defecto seleccionados en el vista
        model.addAttribute("anio", anio);
        model.addAttribute("usuariosPostulantesPorRegion", usuariosPostulantesPorRegion);
        model.addAttribute("usuariosPostulantesPorProvincia", usuariosPostulantesPorProvincia);

        return "/indices/empleos/cantidadUsuariosPostulantesOfertasEmpleoPorRegionYProvincia";
    }




    /**
     * Despliega la vista para la creacion del indice de la segmentación de las vacantes de las
     * {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de segmentacionVacantes
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/segmentacionVacantes", method = RequestMethod.GET)
    public String segmentacionVacantes (Model model) {

        // por cada segmentacion
        ArrayList<ArrayList<String>>  vacantes = indicesService.indiceSegmentacionVacantes(COD_VIGENCIA_DEFAULT, ANIO_DEFAULT);

        model.addAttribute("vacantes", vacantes);

        // valores por defecto seleccionados en el vista
        model.addAttribute("tipoVigencia", COD_VIGENCIA_DEFAULT);
        model.addAttribute("anio", ANIO_DEFAULT);

        return "/indices/empleos/segmentacionVacantes";
    }




    /**
     * Despliega la vista con los resultados del indice de la segmentación de las vacantes de las
     * {@link crm.entities.OfertaLaboralUsmempleo}, según un año de publicación y vigencia de la
     * {@link crm.entities.OfertaLaboralUsmempleo} seleccionados en la vista.
     *
     * @param tipoVigencia Tipo de Vigencia sobre la cual se desean buscar ofertas laborales
     * @param anio Año sobre el cual se desean mostrar los indices
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de segmentacionVacantes con los datos obtenidos
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/buscarSegmentacionVacantes", method = RequestMethod.GET)
    public String buscarSegmentacionVacantes ( @RequestParam ("tipoVigencia") String tipoVigencia,
                                              @RequestParam ("anio") String anio,
                                              Model model) {

        // por cada segmentacion
        ArrayList<ArrayList<String>>  vacantes = indicesService.indiceSegmentacionVacantes(tipoVigencia, anio);

        model.addAttribute("vacantes", vacantes);

        model.addAttribute("tipoVigencia", tipoVigencia);
        model.addAttribute("anio", anio);

        return "/indices/empleos/segmentacionVacantes";
    }





    /**
     * Despliega la vista para la creación del indice de cantidad de vacantes de las
     * {@link crm.entities.OfertaLaboralUsmempleo} clasificados por {@link crm.entities.Carrera}, para la selección de
     * los valores de la busqueda deseada.
     * (Se realiza una busqueda por defecto según ANIO_DEFAULT, COD_VIGENCIA_DEFAULT y PAGE_SIZE_DEFAULT)
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadVacantesOfertasLaboralesPorCarrera
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadVacantesOfertasLaboralesPorCarrera", method = RequestMethod.GET)
    public String cantidadVacantesOfertasLaboralesPorCarrera(Model model) {

        Page<List<String>> cantidadVacantesOfertasPorCarrera = indicesService.indiceCantidadVacantesOfertaLaboralPorCarrera(COD_VIGENCIA_DEFAULT, ANIO_DEFAULT, PAGE_SIZE_DEFAULT, 0);

        this.generatePagination(model, cantidadVacantesOfertasPorCarrera);

        model.addAttribute("cantidadVacantesOfertasPorCarrera", cantidadVacantesOfertasPorCarrera);
        model.addAttribute("tipoVigencia", COD_VIGENCIA_DEFAULT);
        model.addAttribute("anio", ANIO_DEFAULT);

        return "/indices/empleos/cantidadVacantesOfertasLaboralesPorCarrera";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de vacantes de las
     * {@link crm.entities.OfertaLaboralUsmempleo} clasificados por {@link crm.entities.Carrera}, según el
     * {@link crm.entities.TipoVigencia} y año de inicio de la {@link crm.entities.OfertaLaboralUsmempleo},
     * seleccionados en la vista .
     *
     * @param tipoVigencia Tipo de busqueda sobre la cual se desean buscar ofertas laborales
     * @param anio Año sobre el cual se desean mostrar los indices
     * @param pagina Numero de pagina del listado de la busqueda.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadVacantesOfertasLaboralesPorCarrera
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/buscarCantidadVacantesOfertasLaboralesPorCarrera", method = RequestMethod.GET)
    public String buscarCantidadVacantesOfertasLaboralesPorCarrera(@RequestParam ("tipoVigencia") String tipoVigencia,
                                                                   @RequestParam ("anio") String anio,
                                                                   @RequestParam("pagina") String pagina,
                                                                   Model model) {

        Integer numeroPagina;
        try {
            numeroPagina = Integer.parseInt(pagina);
        }
        catch(Exception e){
            numeroPagina = 1;
        }
        if (numeroPagina <= 0) {
            numeroPagina = 1;
        }

        Page<List<String>> cantidadVacantesOfertasPorCarrera = indicesService.indiceCantidadVacantesOfertaLaboralPorCarrera(tipoVigencia, anio, PAGE_SIZE_DEFAULT, numeroPagina - 1);

        this.generatePagination(model, cantidadVacantesOfertasPorCarrera);

        model.addAttribute("cantidadVacantesOfertasPorCarrera", cantidadVacantesOfertasPorCarrera);

        model.addAttribute("tipoVigencia", tipoVigencia);
        model.addAttribute("anio", anio);

        return "/indices/empleos/cantidadVacantesOfertasLaboralesPorCarrera";
    }




    /**
     * Despliega la vista para la creacion de indice de cantidad de vacantes de {@link crm.entities.OfertaLaboralUsmempleo}
     * clasificados por {@link crm.entities.TipoOferta}, para la selección de los valores de la busqueda deseada
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadVacantesOfertasLaboralesPorTipoOferta
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/cantidadVacantesOfertasLaboralesPorTipoOferta", method = RequestMethod.GET)
    public String cantidadVacantesOfertasLaboralesPorTipoOferta (Model model) {

        // por cada tipo de oferta (tipo de oferta, cantidad de vacantes de las ofertas laborales)
        ArrayList<ArrayList<String>>  cantidadVacantesOfertasPorTipoOferta = indicesService.indiceCantidadVacantesOfertaLaboralPorTipoOferta(COD_VIGENCIA_DEFAULT, ANIO_DEFAULT);

        String cantidadVacantesOfertasPorTipoOfertaJSON = new Gson().toJson(cantidadVacantesOfertasPorTipoOferta);

        model.addAttribute("cantidadVacantesOfertasPorTipoOferta", cantidadVacantesOfertasPorTipoOferta);              // utilizado en la tabla
        model.addAttribute("cantidadVacantesOfertasPorTipoOfertaJSON", cantidadVacantesOfertasPorTipoOfertaJSON);       // utilizado en el grafico

        // valores por defecto seleccionados en el vista
        model.addAttribute("tipoVigencia", COD_VIGENCIA_DEFAULT);
        model.addAttribute("anio", ANIO_DEFAULT);

        return "/indices/empleos/cantidadVacantesOfertasLaboralesPorTipoOferta";
    }




    /**
     * Despliega la vista con los resultados del indice por mes de la cantidad de vacantes de las
     * {@link crm.entities.OfertaLaboralUsmempleo} clasificados por {@link crm.entities.TipoOferta}, según un año y vigencia
     * seleccionadas en la vista
     *
     * @param tipoVigencia Tipo de busqueda sobre la cual se desean buscar ofertas laborales
     * @param anio Año sobre el cual se desean mostrar los indices
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadOfertasLaboralesPorTipoOferta
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empleos/buscarCantidadVacantesOfertasLaboralesPorTipoOferta", method = RequestMethod.GET)
    public String buscarCantidadVacantesOfertasLaboralesPorTipoOferta(@RequestParam ("tipoVigencia") String tipoVigencia,
                                                                      @RequestParam ("anio") String anio,
                                                                      Model model) {

        // por cada tipo de oferta (tipo de oferta, cantidad de vacantes de las ofertas laborales)
        ArrayList<ArrayList<String>>  cantidadVacantesOfertasPorTipoOferta = indicesService.indiceCantidadVacantesOfertaLaboralPorTipoOferta(tipoVigencia, anio);

        String cantidadVacantesOfertasPorTipoOfertaJSON = new Gson().toJson(cantidadVacantesOfertasPorTipoOferta);

        model.addAttribute("cantidadVacantesOfertasPorTipoOferta", cantidadVacantesOfertasPorTipoOferta);              // utilizado en la tabla
        model.addAttribute("cantidadVacantesOfertasPorTipoOfertaJSON", cantidadVacantesOfertasPorTipoOfertaJSON);       // utilizado en el grafico

        model.addAttribute("tipoVigencia", tipoVigencia);
        model.addAttribute("anio", anio);

        return "/indices/empleos/cantidadVacantesOfertasLaboralesPorTipoOferta";
    }







    // --------------------------------- CURRICULOS ---------------------------------


    /**
     * Despliega la vista para la creación del indice de cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) clasificados por {@link crm.entities.Carrera}
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadCurriculosPorCarrera
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/curriculos/cantidadCurriculosPorCarrera", method = RequestMethod.GET)
    public String cantidadCurriculosPorCarrera(Model model) {

        // datos del indice
        Page<List<String>> curriculosPorCarrera = indicesService.indiceCurriculosPorCarrera(INSTITUCION_DEFAULT, PAGE_SIZE_DEFAULT, 0);

        this.generatePagination(model, curriculosPorCarrera);

        model.addAttribute("curriculosPorCarrera", curriculosPorCarrera);

        // datos de la busqueda, enviados a la vista para generar los link de la paginación
        model.addAttribute("codInstitucion", INSTITUCION_DEFAULT);
        model.addAttribute("pagina", 0);

        return "/indices/curriculos/cantidadCurriculosPorCarrera";
    }




    /**
     * Despliega la vista para los resultados del indice de cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) según el {@link crm.entities.TipoEstadoEstudio}, clasificados por
     * {@link crm.entities.Carrera}
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} sobre la cual mostrar el indice.
     * @param pagina Numero de pagina del listado de la busqueda.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadCurriculosPorCarrera con los resultados del indice
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/curriculos/buscarCantidadCurriculosPorCarrera", method = RequestMethod.GET)
    public String buscarCantidadCurriculosPorCarrera( @RequestParam("codInstitucion") String codInstitucion,
                                                      @RequestParam("pagina") String pagina,
                                                      Model model) {

        Integer numeroPagina;
        try {
            numeroPagina = Integer.parseInt(pagina);
        }
        catch(Exception e){
            numeroPagina = 1;
        }
        if (numeroPagina <= 0) {
            numeroPagina = 1;
        }

        // datos del indice
        Page<List<String>> curriculosPorCarrera = indicesService.indiceCurriculosPorCarrera(codInstitucion, PAGE_SIZE_DEFAULT, numeroPagina - 1);

        this.generatePagination(model, curriculosPorCarrera);

        model.addAttribute("curriculosPorCarrera", curriculosPorCarrera);

        // datos de la busqueda, enviados a la vista para generar los link de la paginación
        model.addAttribute("codInstitucion", codInstitucion);
        model.addAttribute("pagina", pagina);

        return "/indices/curriculos/cantidadCurriculosPorCarrera";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) según la fecha de modificación del curriculo
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadCurriculosPorFechaModificacion
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/curriculos/cantidadCurriculosPorFechaModificacion", method = RequestMethod.GET)
    public String cantidadCurriculosPorFechaModificacion(Model model) {

        // ArrayList (cantidad, porcentaje)
        ArrayList<String>  curriculosPorFechaModificacion = indicesService.indiceCurriculosPorFechaModificacion(INSTITUCION_DEFAULT, CARRERA_DEFAULT);   // cantidades y porcentaje
        ArrayList<String> cantidadesCurriculosPorFechaModificacion = new ArrayList<String>();

        //obtencion de cantidades (utilizados en el grafico)
        for (int indice = 0; indice < curriculosPorFechaModificacion.size(); indice = indice + 2){
            cantidadesCurriculosPorFechaModificacion.add(curriculosPorFechaModificacion.get(indice));
        }

        String curriculosPorFechaModificacionJSON = new Gson().toJson(cantidadesCurriculosPorFechaModificacion);

        model.addAttribute("curriculosPorFechaModificacion", curriculosPorFechaModificacion);              // utilizado en la tabla
        model.addAttribute("curriculosPorFechaModificacionJSON", curriculosPorFechaModificacionJSON);       // utilizado en el grafico


        return "/indices/curriculos/cantidadCurriculosPorFechaModificacion";
    }




    /**
     * Despliega la vista con los resultados del indice de cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) según la fecha de modificación del curriculo
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} sobre la cual mostrar el indice.
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} sobre la cual mostrar el indice.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadCurriculosPorFechaModificacion con los resultados del indice
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/curriculos/buscarCantidadCurriculosPorFechaModificacion", method = RequestMethod.GET)
    public String buscarCantidadCurriculosPorFechaModificacion( @RequestParam("codInstitucion") String codInstitucion,
                                                                @RequestParam("codCarrera") String codCarrera,
                                                                Model model) {
         // datos del indice
        ArrayList<String> curriculosPorFechaModificacion = indicesService.indiceCurriculosPorFechaModificacion(codInstitucion, codCarrera);
        ArrayList<String> cantidadesCurriculosPorFechaModificacion = new ArrayList<String>();

        //obtencion de cantidades (utilizados en el grafico)
        for (int indice = 0; indice < curriculosPorFechaModificacion.size(); indice = indice + 2){
            cantidadesCurriculosPorFechaModificacion.add(curriculosPorFechaModificacion.get(indice));
        }

        String curriculosPorFechaModificacionJSON = new Gson().toJson(cantidadesCurriculosPorFechaModificacion);

        model.addAttribute("curriculosPorFechaModificacion", curriculosPorFechaModificacion);              // utilizado en la tabla
        model.addAttribute("curriculosPorFechaModificacionJSON", curriculosPorFechaModificacionJSON);       // utilizado en el grafico

        return "/indices/curriculos/cantidadCurriculosPorFechaModificacion";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) según el sexo registrado en el curriculo
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadCurriculosPorSexo
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/curriculos/cantidadCurriculosPorSexo", method = RequestMethod.GET)
    public String cantidadCurriculosPorSexo(Model model) {

        ArrayList<String>  curriculosPorSexo = indicesService.indiceCurriculosPorSexo(INSTITUCION_DEFAULT, CARRERA_DEFAULT);

        ArrayList<String> cantidadesCurriculosPorSexo = new ArrayList<String>();

        //obtencion de cantidades (utilizados en el grafico)
        for (int indice = 0; indice < curriculosPorSexo.size(); indice = indice + 2){
            cantidadesCurriculosPorSexo.add(curriculosPorSexo.get(indice));
        }

        String curriculosPorSexoJSON = new Gson().toJson(cantidadesCurriculosPorSexo);

        model.addAttribute("curriculosPorSexo", curriculosPorSexo);                                   // utilizado en la tabla
        model.addAttribute("curriculosPorSexoJSON", curriculosPorSexoJSON);                           // utilizado en el grafico


        return "/indices/curriculos/cantidadCurriculosPorSexo";
    }




    /**
     * Despliega la vista con los resultados del indice de cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) segmentado por el sexo registrado en el curriculo, según la {@link crm.entities.Carrera}
     * e {@link crm.entities.Institucion} ingresadas para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} sobre la cual mostrar el indice.
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} sobre la cual mostrar el indice.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadCurriculosPorSexo con los resultados del indice
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/curriculos/buscarCantidadCurriculosPorSexo", method = RequestMethod.GET)
    public String buscarCantidadCurriculosPorSexo( @RequestParam("codInstitucion") String codInstitucion,
                                                    @RequestParam("codCarrera") String codCarrera,
                                                    Model model) {

        ArrayList<String>  curriculosPorSexo = indicesService.indiceCurriculosPorSexo(codInstitucion, codCarrera);

        ArrayList<String> cantidadesCurriculosPorSexo = new ArrayList<String>();

        //obtencion de cantidades (utilizados en el grafico)
        for (int indice = 0; indice < curriculosPorSexo.size(); indice = indice + 2){
            cantidadesCurriculosPorSexo.add(curriculosPorSexo.get(indice));
        }

        String curriculosPorSexoJSON = new Gson().toJson(cantidadesCurriculosPorSexo);

        model.addAttribute("curriculosPorSexo", curriculosPorSexo);                                   // utilizado en la tabla
        model.addAttribute("curriculosPorSexoJSON", curriculosPorSexoJSON);                           // utilizado en el grafico

        return "/indices/curriculos/cantidadCurriculosPorSexo";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) según la edad registrada en el curriculo
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadCurriculosPorEdad
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/curriculos/cantidadCurriculosPorEdad", method = RequestMethod.GET)
    public String cantidadCurriculosPorEdad(Model model) {

        // datos del indice
        ArrayList<String> curriculosPorEdad = indicesService.indiceCurriculosPorEdad(INSTITUCION_DEFAULT, CARRERA_DEFAULT);

        ArrayList<String> cantidadesCurriculosPorEdad = new ArrayList<String>();

        //obtencion de cantidades (utilizados en el grafico)
        for (int indice = 0; indice < curriculosPorEdad.size(); indice = indice + 2){
            cantidadesCurriculosPorEdad.add(curriculosPorEdad.get(indice));
        }

        String curriculosPorEdadJSON = new Gson().toJson(cantidadesCurriculosPorEdad);

        model.addAttribute("curriculosPorEdad", curriculosPorEdad);                                   // utilizado en la tabla
        model.addAttribute("curriculosPorEdadJSON", curriculosPorEdadJSON);                           // utilizado en el grafico

        return "/indices/curriculos/cantidadCurriculosPorEdad";
    }




    /**
     * Despliega la vista con los resultados del indice de cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) segmentado por la edad registrado en el curriculo, según la {@link crm.entities.Carrera}
     * e {@link crm.entities.Institucion} ingresadas para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} sobre la cual mostrar el indice.
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} sobre la cual mostrar el indice.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadCurriculosPorEdad con los resultados del indice
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/curriculos/buscarCantidadCurriculosPorEdad", method = RequestMethod.GET)
    public String buscarCantidadCurriculosPorEdad( @RequestParam("codInstitucion") String codInstitucion,
                                                   @RequestParam("codCarrera") String codCarrera,
                                                   Model model) {

        // datos del indice
        ArrayList<String>  curriculosPorEdad = indicesService.indiceCurriculosPorEdad(codInstitucion, codCarrera);

        ArrayList<String> cantidadesCurriculosPorEdad = new ArrayList<String>();

        //obtencion de cantidades (utilizados en el grafico)
        for (int indice = 0; indice < curriculosPorEdad.size(); indice = indice + 2){
            cantidadesCurriculosPorEdad.add(curriculosPorEdad.get(indice));
        }

        String curriculosPorEdadJSON = new Gson().toJson(cantidadesCurriculosPorEdad);

        model.addAttribute("curriculosPorEdad", curriculosPorEdad);                                   // utilizado en la tabla
        model.addAttribute("curriculosPorEdadJSON", curriculosPorEdadJSON);                           // utilizado en el grafico

        return "/indices/curriculos/cantidadCurriculosPorEdad";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) segmentadas por {@link crm.entities.Region} del {@link crm.entities.Usuario}
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadCurriculosPorRegion
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/curriculos/cantidadCurriculosPorRegion", method = RequestMethod.GET)
    public String cantidadCurriculosPorRegion(Model model) {

        // por cada region
        ArrayList<ArrayList<String>>  curriculosPorRegion = indicesService.indiceCantidadCurriculosPorRegion(INSTITUCION_DEFAULT, CARRERA_DEFAULT);

        String curriculosPorRegionJSON = new Gson().toJson(curriculosPorRegion);

        model.addAttribute("curriculosPorRegion", curriculosPorRegion);              // utilizado en la tabla
        model.addAttribute("curriculosPorRegionJSON", curriculosPorRegionJSON);       // utilizado en el grafico

        return "/indices/curriculos/cantidadCurriculosPorRegion";
    }




    /**
     * Despliega la vista con los resultados del indice de cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) segmentado por {@link crm.entities.Region} del {@link crm.entities.Usuario},
     * según la {@link crm.entities.Carrera} e {@link crm.entities.Institucion} ingresadas en la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} sobre la cual mostrar el indice.
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} sobre la cual mostrar el indice.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadCurriculosPorEdad con los resultados del indice
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/curriculos/buscarCantidadCurriculosPorRegion", method = RequestMethod.GET)
    public String buscarCantidadCurriculosPorRegion ( @RequestParam("codInstitucion") String codInstitucion,
                                                      @RequestParam("codCarrera") String codCarrera,
                                                      Model model) {
        // por cada region
        ArrayList<ArrayList<String>>  curriculosPorRegion = indicesService.indiceCantidadCurriculosPorRegion(codInstitucion, codCarrera);

        String curriculosPorRegionJSON = new Gson().toJson(curriculosPorRegion);

        model.addAttribute("curriculosPorRegion", curriculosPorRegion);              // utilizado en la tabla
        model.addAttribute("curriculosPorRegionJSON", curriculosPorRegionJSON);       // utilizado en el grafico

        return "/indices/curriculos/cantidadCurriculosPorRegion";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) según el porcentaje de la Completitud de Contacto de los datos del
     * {@link crm.entities.Usuario}
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadCurriculosCompletos
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/curriculos/cantidadCurriculosCompletos", method = RequestMethod.GET)
    public String cantidadCurriculosCompletos(Model model) {

        // datos del indice
        ArrayList<String> curriculosPorTrayectoriaCompleta = indicesService.indiceCurriculosPorTrayectoriaCompleta(INSTITUCION_DEFAULT, CARRERA_DEFAULT);
        ArrayList<String> curriculosPorCompletitudContacto = indicesService.indiceCurriculosPorCompletitudContacto(INSTITUCION_DEFAULT, CARRERA_DEFAULT);

        ArrayList<String> cantidadesCurriculosPorTrayectoriaCompleta = new ArrayList<String>();
        ArrayList<String> cantidadesCurriculosPorCompletitudContacto = new ArrayList<String>();

        //obtencion de cantidades para trayectoria (utilizados en el grafico)
        for (int indice = 0; indice < curriculosPorTrayectoriaCompleta.size(); indice = indice + 2){
            cantidadesCurriculosPorTrayectoriaCompleta.add(curriculosPorTrayectoriaCompleta.get(indice));
        }
        //obtencion de cantidades prra completitud (utilizados en el grafico)
        for (int indice = 0; indice < curriculosPorCompletitudContacto.size(); indice = indice + 2){
            cantidadesCurriculosPorCompletitudContacto.add(curriculosPorCompletitudContacto.get(indice));
        }

        String curriculosPorTrayectoriaCompletaJSON = new Gson().toJson( cantidadesCurriculosPorTrayectoriaCompleta );
        String curriculosPorCompletitudContactoJSON = new Gson().toJson( cantidadesCurriculosPorCompletitudContacto );

        model.addAttribute("curriculosPorTrayectoriaCompleta", curriculosPorTrayectoriaCompleta);       // utilizado en la tabla
        model.addAttribute("curriculosPorCompletitudContacto", curriculosPorCompletitudContacto);       // utilizado en la tabla
        model.addAttribute("curriculosPorTrayectoriaCompletaJSON", curriculosPorTrayectoriaCompletaJSON);       // utilizado en el grafico
        model.addAttribute("curriculosPorCompletitudContactoJSON", curriculosPorCompletitudContactoJSON);       // utilizado en el grafico

        return "/indices/curriculos/cantidadCurriculosCompletos";
    }




    /**
     * Despliega la vista con los resultados del indice de cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) segmentado por el porcentaje de la Completitud de Contacto de los datos del
     * {@link crm.entities.Usuario}
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} sobre la cual mostrar el indice.
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} sobre la cual mostrar el indice.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadCurriculosCompletos con los resultados del indice
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/curriculos/buscarCantidadCurriculosCompletos", method = RequestMethod.GET)
    public String buscarCantidadCurriculosCompletos( @RequestParam("codInstitucion") String codInstitucion,
                                                   @RequestParam("codCarrera") String codCarrera,
                                                   Model model) {

        // datos del indice
        ArrayList<String> curriculosPorTrayectoriaCompleta = indicesService.indiceCurriculosPorTrayectoriaCompleta(codInstitucion, codCarrera);
        ArrayList<String> curriculosPorCompletitudContacto = indicesService.indiceCurriculosPorCompletitudContacto(codInstitucion, codCarrera);

        ArrayList<String> cantidadesCurriculosPorTrayectoriaCompleta = new ArrayList<String>();
        ArrayList<String> cantidadesCurriculosPorCompletitudContacto = new ArrayList<String>();

        //obtencion de cantidades para trayectoria (utilizados en el grafico)
        for (int indice = 0; indice < curriculosPorTrayectoriaCompleta.size(); indice = indice + 2){
            cantidadesCurriculosPorTrayectoriaCompleta.add(curriculosPorTrayectoriaCompleta.get(indice));
        }
        //obtencion de cantidades prra completitud (utilizados en el grafico)
        for (int indice = 0; indice < curriculosPorCompletitudContacto.size(); indice = indice + 2){
            cantidadesCurriculosPorCompletitudContacto.add(curriculosPorCompletitudContacto.get(indice));
        }

        String curriculosPorTrayectoriaCompletaJSON = new Gson().toJson( cantidadesCurriculosPorTrayectoriaCompleta );
        String curriculosPorCompletitudContactoJSON = new Gson().toJson( cantidadesCurriculosPorCompletitudContacto );

        model.addAttribute("curriculosPorTrayectoriaCompleta", curriculosPorTrayectoriaCompleta);       // utilizado en la tabla
        model.addAttribute("curriculosPorCompletitudContacto", curriculosPorCompletitudContacto);       // utilizado en la tabla
        model.addAttribute("curriculosPorTrayectoriaCompletaJSON", curriculosPorTrayectoriaCompletaJSON);       // utilizado en el grafico
        model.addAttribute("curriculosPorCompletitudContactoJSON", curriculosPorCompletitudContactoJSON);       // utilizado en el grafico

        return "/indices/curriculos/cantidadCurriculosCompletos";
    }







    // --------------------------------- PERSONAS ---------------------------------



    /**
     * Despliega la vista para la creación del indice de cantidad de exalumnos, para la selección de
     * los valores de la busqueda deseada
     *
     * @param model
     *
     * @return Retorna la vista de cantidadExalumnos
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/personas/cantidad-exalumnos", method = RequestMethod.GET)
    public String cantidadExalumnos(Model model) {
        Map<String, Integer> valoresBusqueda = new HashMap<>();
        valoresBusqueda.put("idPais", 56);
        valoresBusqueda.put("codInstitucion", 25);
        valoresBusqueda.put("codCarrera", -1);
        valoresBusqueda.put("anioIngreso", 0);
        valoresBusqueda.put("anioEgreso", 0);
        valoresBusqueda.put("anioTitulacion", 0);
        valoresBusqueda.put("anioInicio", 1980);
        valoresBusqueda.put("anioFin", 2015);
        ArrayList<Integer> cantidadExalumnos = indicesService.cantidadExalumnosRango(valoresBusqueda);
        ArrayList<String> anios = new ArrayList<>();
        for (Integer anio = valoresBusqueda.get("anioInicio"); anio <= valoresBusqueda.get("anioFin"); anio++) {
            anios.add(anio.toString());
        }
        String cantidadExalumnosJSON = new Gson().toJson(cantidadExalumnos);
        String aniosJSON = new Gson().toJson(anios);
        model.addAttribute("crit", "rangoAnios");
        model.addAttribute("par", valoresBusqueda);
        model.addAttribute("cantidadExalumnos", cantidadExalumnosJSON);
        model.addAttribute("anios", aniosJSON);
        return "/indices/personas/cantidadExalumnos";
    }




    /**
     * Despliega la vista con los resultados del indice de la cantidad de exalumnos según el
     * criterio ingresado (rango de años o años especificos de ingreso, egreso o titulacion)
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadExalumnos
     *
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/personas/buscar-cantidad-exalumnos", method = RequestMethod.GET)
    public String buscarCantidadExalumnos(@RequestParam("pais") Integer idPais,
                                          @RequestParam("cod_institucion") Integer codInstitucion,
                                          @RequestParam("cod_carrera") Integer codCarrera,
                                          @RequestParam("anioIngreso") Integer anioIngreso,
                                          @RequestParam("anioEgreso") Integer anioEgreso,
                                          @RequestParam("anioTitulacion") Integer anioTitulacion,
                                          @RequestParam("anioInicio") Integer anioInicio,
                                          @RequestParam("anioFin") Integer anioFin,
                                          @RequestParam("criterio") String criterio,
                                          Model model,
                                          RedirectAttributes redirectAttributes) {
        Map<String, Integer> valoresBusqueda = new HashMap<>();
        valoresBusqueda.put("idPais", idPais);
        valoresBusqueda.put("codInstitucion", codInstitucion);
        valoresBusqueda.put("codCarrera", codCarrera);
        valoresBusqueda.put("anioIngreso", anioIngreso);
        valoresBusqueda.put("anioEgreso", anioEgreso);
        valoresBusqueda.put("anioTitulacion", anioTitulacion);
        valoresBusqueda.put("anioInicio", anioInicio);
        valoresBusqueda.put("anioFin", anioFin);
        if(anioFin<anioInicio){
            redirectAttributes.addFlashAttribute("flash.error", "'Año Inicio' mayor que 'Año Fin'");
            model.addAttribute("par", valoresBusqueda);
            model.addAttribute("crit", criterio);
            return "redirect:/indices/personas/cantidad-exalumnos";
        }
        if(anioInicio!=0 && anioFin!=0) {
            ArrayList<Integer> cantidadExalumnos = indicesService.cantidadExalumnosRango(valoresBusqueda);
            ArrayList<String> anios = new ArrayList<>();
            for (Integer anio = anioInicio; anio <= anioFin; anio++) {
                anios.add(anio.toString());
            }
            String cantidadExalumnosJSON = new Gson().toJson(cantidadExalumnos);
            String aniosJSON = new Gson().toJson(anios);
            model.addAttribute("crit", criterio);
            model.addAttribute("par", valoresBusqueda);
            model.addAttribute("cantidadExalumnos", cantidadExalumnosJSON);
            model.addAttribute("anios", aniosJSON);
            //model.addAttribute("cantidadExalumnos", cantidadExalumnos);
            //model.addAttribute("anios", anios);
        }else if(anioIngreso != 0 || anioEgreso != 0 || anioTitulacion != 0){
            String anios = "Exalumnos";
            Integer cantidadExalumnos = indicesService.cantidadExalumnos(valoresBusqueda);
            String cantidadExalumnosJSON = new Gson().toJson(cantidadExalumnos);
            String aniosJSON = new Gson().toJson(anios);
            model.addAttribute("crit", criterio);
            model.addAttribute("par", valoresBusqueda);
            model.addAttribute("cantidadExalumnos", "["+cantidadExalumnosJSON+"]");
            model.addAttribute("anios", "["+aniosJSON+"]");
        }
        return "/indices/personas/cantidadExalumnos";
    }




    /**
     * Despliega la vista para la creacion de indice de la cantidad exalumnos por situacion contractual
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadExalumnosPorSituacionContractual
     *
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/personas/cantidad-exalumnos-por-situacion-contractual", method = RequestMethod.GET)
    public String cantidadExalumnosPorSituacionContractual(Model model) {
        List<TipoSituacionLaboral> situacionesLaborales = entidadesTipoService.buscarTodosTipoSituacionLaboral();
        ArrayList<String> situacionesLaboralesNombres = new ArrayList<>();
        ArrayList<Integer> cantidadExalumnosPorSituacionContractual = indicesService.indiceCantidadExalumnosPorSituacionContractual(situacionesLaborales);
        ArrayList<Integer> cantidadExalumnosSituacionContractual = new ArrayList<>();
        for(Integer i=0;i<situacionesLaborales.size();i++){
            if(i!=0){
                situacionesLaboralesNombres.add(situacionesLaborales.get(i).getNombre());
                cantidadExalumnosSituacionContractual.add(cantidadExalumnosPorSituacionContractual.get(i));
            }
        }
        String situacionesLaboralesJSON = new Gson().toJson(situacionesLaboralesNombres);
        String cantidadExalumnosJSON = new Gson().toJson(cantidadExalumnosSituacionContractual);
        model.addAttribute("cantidadExalumnosSinInformacion", cantidadExalumnosPorSituacionContractual.get(0));
        model.addAttribute("situacionesLaborales", situacionesLaboralesJSON);
        model.addAttribute("cantidadExalumnos", cantidadExalumnosJSON);
        return "/indices/personas/cantidadExalumnosPorSituacionContractual";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de personas ( {@link crm.entities.Usuario} ) segmentado
     * por los años de experiencia de su {@link crm.entities.InfoProfesionalExalumno}
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPersonasPorAniosExperiencia para la selección de los parámetros de busqueda
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/personas/cantidadPersonasPorAniosExperiencia", method = RequestMethod.GET)
    public String cantidadPersonasPorAniosExperiencia(Model model) {

        // datos del indice
        Page<ArrayList<String>> personasPorAniosExperiencia = indicesService.indiceCantidadPersonasPorAniosExperiencia(INSTITUCION_DEFAULT, CARRERA_DEFAULT, PAGE_SIZE_DEFAULT, 0);

        this.generatePagination(model, personasPorAniosExperiencia);

        model.addAttribute("personasPorAniosExperiencia", personasPorAniosExperiencia);

        // datos de la busqueda, enviados a la vista para generar los link de la paginación
        model.addAttribute("codInstitucion", INSTITUCION_DEFAULT);
        model.addAttribute("codCarrera", CARRERA_DEFAULT);
        model.addAttribute("pagina", 1);

        return "/indices/personas/cantidadPersonasPorAniosExperiencia";
    }




    /**
     * Despliega la vista con los resultados del indice de cantidad de personas ( {@link crm.entities.Usuario} ) segmentado
     * por los años de experiencia de su {@link crm.entities.InfoProfesionalExalumno}, según la {@link crm.entities.Carrera}
     * e {@link crm.entities.Institucion} seleccionados para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} sobre la cual mostrar el indice.
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} sobre la cual mostrar el indice.
     * @param pagina Numero de pagina del listado de la busqueda.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPersonasPorAniosExperiencia con los resultados del indice
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/personas/buscarCantidadPersonasPorAniosExperiencia", method = RequestMethod.GET)
    public String buscarCantidadPersonasPorAniosExperiencia( @RequestParam("codInstitucion") String codInstitucion,
                                                             @RequestParam("codCarrera") String codCarrera,
                                                             @RequestParam("pagina") String pagina,
                                                             Model model) {

        Integer numeroPagina;
        try {
            numeroPagina = Integer.parseInt(pagina);
        }
        catch(Exception e){
            numeroPagina = 1;
        }
        if (numeroPagina <= 0) {
            numeroPagina = 1;
        }

        // datos del indice
        Page<ArrayList<String>> personasPorAniosExperiencia = indicesService.indiceCantidadPersonasPorAniosExperiencia(codInstitucion, codCarrera, PAGE_SIZE_DEFAULT, numeroPagina - 1);

        this.generatePagination(model, personasPorAniosExperiencia);

        model.addAttribute("personasPorAniosExperiencia", personasPorAniosExperiencia);

        // datos de la busqueda, enviados a la vista para generar los link de la paginación
        model.addAttribute("codInstitucion", codInstitucion);
        model.addAttribute("codCarrera", codCarrera);
        model.addAttribute("pagina", pagina);

        return "/indices/personas/cantidadPersonasPorAniosExperiencia";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de personas ( {@link crm.entities.Usuario} ) con trabajo,
     * registrado en sus {@link crm.entities.ActividadExalumno}, segmentado por el {@link crm.entities.TipoSector} de la
     * {@link crm.entities.Empresa} en la cual trabaja.
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPersonasConTrabajoPorTipoRubro para la selección de los parámetros de busqueda
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/personas/cantidadPersonasConTrabajoPorTipoRubro", method = RequestMethod.GET)
    public String cantidadPersonasConTrabajoPorTipoRubro(Model model) {

        // datos del indice
        ArrayList<ArrayList<String>> personasConTrabajoPorRubro = indicesService.indiceCantidadPersonasConTrabajoPorTipoRubro(INSTITUCION_DEFAULT, CARRERA_DEFAULT);

        String personasConTrabajoPorRubroJSON = new Gson().toJson(personasConTrabajoPorRubro);

        model.addAttribute("personasConTrabajoPorRubro", personasConTrabajoPorRubro);               // utilizado en la tabla
        model.addAttribute("personasConTrabajoPorRubroJSON", personasConTrabajoPorRubroJSON);       // utilizado en el grafico

        return "/indices/personas/cantidadPersonasConTrabajoPorTipoRubro";
    }




    /**
     * Despliega la vista con los resultados del indice de cantidad de personas ( {@link crm.entities.Usuario} ) con trabajo,
     * registrado en sus {@link crm.entities.ActividadExalumno}, segmentado por el {@link crm.entities.TipoSector} de la
     * {@link crm.entities.Empresa} en la cual trabaja; según la {@link crm.entities.Carrera}
     * e {@link crm.entities.Institucion} seleccionados para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} sobre la cual mostrar el indice.
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} sobre la cual mostrar el indice.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPersonasConTrabajoPorTipoRubro con los resultados del indice
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/personas/buscarCantidadPersonasConTrabajoPorTipoRubro", method = RequestMethod.GET)
    public String buscarCantidadPersonasConTrabajoPorTipoRubro( @RequestParam("codInstitucion") String codInstitucion,
                                                                @RequestParam("codCarrera") String codCarrera,
                                                                Model model) {

        // datos del indice
        ArrayList<ArrayList<String>> personasConTrabajoPorRubro = indicesService.indiceCantidadPersonasConTrabajoPorTipoRubro(codInstitucion, codCarrera);

        String personasConTrabajoPorRubroJSON = new Gson().toJson(personasConTrabajoPorRubro);

        model.addAttribute("personasConTrabajoPorRubro", personasConTrabajoPorRubro);               // utilizado en la tabla
        model.addAttribute("personasConTrabajoPorRubroJSON", personasConTrabajoPorRubroJSON);       // utilizado en el grafico


        return "/indices/personas/cantidadPersonasConTrabajoPorTipoRubro";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de personas ( {@link crm.entities.Usuario} ) con trabajo,
     * registrado en sus {@link crm.entities.ActividadExalumno}, segmentado por el {@link crm.entities.TipoCargo}
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPersonasConTrabajoPorTipoCargo para la selección de los parámetros de busqueda
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/personas/cantidadPersonasConTrabajoPorTipoCargo", method = RequestMethod.GET)
    public String cantidadPersonasConTrabajoPorTipoCargo(Model model) {

        // datos del indice
        ArrayList<ArrayList<String>> personasConTrabajoPorTipoCargo = indicesService.indicePersonasConTrabajoPorTipoCargo(INSTITUCION_DEFAULT, CARRERA_DEFAULT);

        String personasConTrabajoPorTipoCargoJSON = new Gson().toJson(personasConTrabajoPorTipoCargo);

        model.addAttribute("personasConTrabajoPorTipoCargo", personasConTrabajoPorTipoCargo);               // utilizado en la tabla
        model.addAttribute("personasConTrabajoPorTipoCargoJSON", personasConTrabajoPorTipoCargoJSON);       // utilizado en el grafico

        return "/indices/personas/cantidadPersonasConTrabajoPorTipoCargo";
    }




    /**
     * Despliega la vista con los resultados del indice de cantidad de personas ( {@link crm.entities.Usuario} ) con trabajo,
     * registrado en sus {@link crm.entities.ActividadExalumno}, segmentado por el {@link crm.entities.TipoCargo};
     * según la {@link crm.entities.Carrera} e {@link crm.entities.Institucion} seleccionados para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} sobre la cual mostrar el indice.
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} sobre la cual mostrar el indice.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPersonasConTrabajoPorTipoCargo con los resultados del indice
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/personas/buscarCantidadPersonasConTrabajoPorTipoCargo", method = RequestMethod.GET)
    public String buscarCantidadPersonasConTrabajoPorTipoCargo( @RequestParam("codInstitucion") String codInstitucion,
                                                                @RequestParam("codCarrera") String codCarrera,
                                                                Model model) {

        // datos del indice
        ArrayList<ArrayList<String>> personasConTrabajoPorTipoCargo = indicesService.indicePersonasConTrabajoPorTipoCargo(codInstitucion, codCarrera);

        String personasConTrabajoPorTipoCargoJSON = new Gson().toJson(personasConTrabajoPorTipoCargo);

        model.addAttribute("personasConTrabajoPorTipoCargo", personasConTrabajoPorTipoCargo);               // utilizado en la tabla
        model.addAttribute("personasConTrabajoPorTipoCargoJSON", personasConTrabajoPorTipoCargoJSON);       // utilizado en el grafico

        return "/indices/personas/cantidadPersonasConTrabajoPorTipoCargo";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de personas ( {@link crm.entities.Usuario} ) con trabajo,
     * registrado en sus {@link crm.entities.ActividadExalumno}, segmentado por el tramo de la remuneración
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPersonasConTrabajoPorPorTramoIngreso para la selección de los parámetros de busqueda
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/personas/cantidadPersonasConTrabajoPorTramoIngreso", method = RequestMethod.GET)
    public String cantidadPersonasConTrabajoPorPorTramoIngreso(Model model) {

        // datos del indice
        ArrayList<String> personasConTrabajoPorTramoIngreso = indicesService.indicePersonasConTrabajoPorTramoIngreso(INSTITUCION_DEFAULT, CARRERA_DEFAULT);
        ArrayList<String> cantidadesPersonasConTrabajoPorTramoIngreso = new ArrayList<String>();

        //obtencion de cantidades para cada tramo ingreso (utilizados en el grafico)
        for (int indice = 0; indice < personasConTrabajoPorTramoIngreso.size(); indice = indice + 2){
            cantidadesPersonasConTrabajoPorTramoIngreso.add(personasConTrabajoPorTramoIngreso.get(indice));
        }

        String personasConTrabajoPorTramoIngresoJSON = new Gson().toJson(cantidadesPersonasConTrabajoPorTramoIngreso);

        model.addAttribute("personasConTrabajoPorTramoIngreso", personasConTrabajoPorTramoIngreso);
        model.addAttribute("personasConTrabajoPorTramoIngresoJSON", personasConTrabajoPorTramoIngresoJSON);

        return "/indices/personas/cantidadPersonasConTrabajoPorTramoIngreso";
    }




    /**
     * Despliega la vista con los resultados del indice de cantidad de personas ( {@link crm.entities.Usuario} ) con trabajo,
     * registrado en sus {@link crm.entities.ActividadExalumno}, segmentado por el tramo de la remuneración;
     * según la {@link crm.entities.Carrera} e {@link crm.entities.Institucion} seleccionados para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} sobre la cual mostrar el indice.
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} sobre la cual mostrar el indice.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPersonasConTrabajoPorPorTramoIngreso con los resultados del indice
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/personas/buscarCantidadPersonasConTrabajoPorTramoIngreso", method = RequestMethod.GET)
    public String buscarCantidadPersonasConTrabajoPorPorTramoIngreso( @RequestParam("codInstitucion") String codInstitucion,
                                                                    @RequestParam("codCarrera") String codCarrera,
                                                                    Model model) {

        // datos del indice
        ArrayList<String> personasConTrabajoPorTramoIngreso = indicesService.indicePersonasConTrabajoPorTramoIngreso(codInstitucion, codCarrera);

        ArrayList<String> cantidadesPersonasConTrabajoPorTramoIngreso = new ArrayList<String>();

        //obtencion de cantidades para cada tramo ingreso (utilizados en el grafico)
        for (int indice = 0; indice < personasConTrabajoPorTramoIngreso.size(); indice = indice + 2){
            cantidadesPersonasConTrabajoPorTramoIngreso.add(personasConTrabajoPorTramoIngreso.get(indice));
        }

        String personasConTrabajoPorTramoIngresoJSON = new Gson().toJson(cantidadesPersonasConTrabajoPorTramoIngreso);

        model.addAttribute("personasConTrabajoPorTramoIngreso", personasConTrabajoPorTramoIngreso);
        model.addAttribute("personasConTrabajoPorTramoIngresoJSON", personasConTrabajoPorTramoIngresoJSON);

        return "/indices/personas/cantidadPersonasConTrabajoPorTramoIngreso";
    }




    /**
     * Despliega la vista para la creación del indice de cantidad de personas ( {@link crm.entities.Usuario} ) según el
     * nivel de conocimiento del {@link crm.entities.Idioma} registrado en {@link crm.entities.ManejoIdioma},
     * para la selección de los parámetros de busqueda del indice.
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPersonasPorNivelIdioma para la selección de los parámetros de busqueda
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/personas/cantidadPersonasPorNivelIdioma", method = RequestMethod.GET)
    public String cantidadPersonasPorNivelIdioma(Model model) {

        // datos del indice
        String[] personasPorNivelIdioma = indicesService.indicePersonasPorNivelIdioma(INSTITUCION_DEFAULT, CARRERA_DEFAULT, IDIOMA_DEFAULT);

        String personasPorNivelIdiomaJSON = new Gson().toJson(personasPorNivelIdioma);

        model.addAttribute("personasPorNivelIdioma", personasPorNivelIdioma);                                   // utilizado en la tabla
        model.addAttribute("personasPorNivelIdiomaJSON", personasPorNivelIdiomaJSON);                           // utilizado en el grafico

        return "/indices/personas/cantidadPersonasPorNivelIdioma";
    }




    /**
     * Despliega la vista con los resultados del indice de cantidad de personas ( {@link crm.entities.Usuario} ) segmentado
     * por el nivel de conocimiento del {@link crm.entities.Idioma} registrado en {@link crm.entities.ManejoIdioma}
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} sobre la cual mostrar el indice.
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} sobre la cual mostrar el indice.
     * @param codIdioma Identificador del {@link crm.entities.Idioma} sobre la cual mostrar el indice.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPersonasPorNivelIdioma con los resultados del indice
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/personas/buscarCantidadPersonasPorNivelIdioma", method = RequestMethod.GET)
    public String buscarCantidadPersonasPorNivelIdioma( @RequestParam("codInstitucion") String codInstitucion,
                                                        @RequestParam("codCarrera") String codCarrera,
                                                        @RequestParam("codIdioma") String codIdioma,
                                                        Model model) {

        // datos del indice
        String[] personasPorNivelIdioma = indicesService.indicePersonasPorNivelIdioma(codInstitucion, codCarrera, codIdioma);

        String personasPorNivelIdiomaJSON = new Gson().toJson(personasPorNivelIdioma);

        model.addAttribute("personasPorNivelIdioma", personasPorNivelIdioma);                                   // utilizado en la tabla
        model.addAttribute("personasPorNivelIdiomaJSON", personasPorNivelIdiomaJSON);                           // utilizado en el grafico

        return "/indices/personas/cantidadPersonasPorNivelIdioma";
    }






    // --------------------------------- EMPRESAS ---------------------------------



    /**
     * Despliega la vista para la creación del indice de ranking de ( {@link crm.entities.Empresa} ) con más
     * {@link crm.entities.Usuario} contratrados, para la selección de los parámetros de busqueda del indice
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de rankingEmpresasMasPersonasContratadas para la selección de los parámetros de busqueda
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empresas/rankingEmpresasMasPersonasContratadas", method = RequestMethod.GET)
    public String rankingEmpresasMasPersonasContratadas(Model model) {

        // datos del indice
        ArrayList<ArrayList<String>> empresasConMasPersonasContratadas = indicesService.indiceRankingEmpresasMasPersonasContratadas(INSTITUCION_DEFAULT, CARRERA_DEFAULT, ANIO_DEFAULT, TAMANIO_LISTADO_DEFAULT);

        model.addAttribute("empresasConMasPersonasContratadas", empresasConMasPersonasContratadas);

        // valores por defecto seleccionados en el vista
        model.addAttribute("anio", ANIO_DEFAULT);
        model.addAttribute("cantidadMostrar", TAMANIO_LISTADO_DEFAULT);

        return "/indices/empresas/rankingEmpresasMasPersonasContratadas";
    }




    /**
     * Despliega la vista con los resultados del indice de ranking de ( {@link crm.entities.Empresa} ) con más
     * {@link crm.entities.Usuario} contratrados.
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} sobre la cual mostrar el indice.
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} sobre la cual mostrar el indice.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadPersonasPorNivelIdioma con los resultados del indice
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empresas/buscarRankingEmpresasMasPersonasContratadas", method = RequestMethod.GET)
    public String buscarRankingEmpresasMasPersonasContratadas( @RequestParam("codInstitucion") String codInstitucion,
                                                                @RequestParam("codCarrera") String codCarrera,
                                                                @RequestParam("anio") String anio,
                                                                @RequestParam ("cantidadMostrar") String cantidadMostrar,
                                                                Model model) {

        // datos del indice
        ArrayList<ArrayList<String>> empresasConMasPersonasContratadas = indicesService.indiceRankingEmpresasMasPersonasContratadas(codInstitucion, codCarrera, anio, cantidadMostrar);

        model.addAttribute("empresasConMasPersonasContratadas", empresasConMasPersonasContratadas);

        // valores por defecto seleccionados en el vista
        model.addAttribute("anio", anio);
        model.addAttribute("cantidadMostrar", cantidadMostrar);

        return "/indices/empresas/rankingEmpresasMasPersonasContratadas";
    }

    @RequestMapping(value = "/indices/empresas/cantidadAportesEmpresas", method = RequestMethod.GET)
    public String cantidadAportesEmpresas(Model model) {

        // datos del indice
        //ArrayList<ArrayList<String>> empresasConMasPersonasContratadas = indicesService.indiceRankingEmpresasMasPersonasContratadas(INSTITUCION_DEFAULT, CARRERA_DEFAULT, ANIO_DEFAULT, TAMANIO_LISTADO_DEFAULT);
        //Page<List<String>> cantidadAportesEmpresas = indicesService.indiceCantidadAportesEmpresasaux(ANIO_DEFAULT,PAGE_SIZE_DEFAULT,0);
        ArrayList<ArrayList<String>> cantidadAportesEmpresas = indicesService.indiceCantidadAportesEmpresas(COD_COMPROMISO_DEFAULT,COD_VIGENCIA_DEFAULT,PAGE_SIZE_DEFAULT,0);

        //ArrayList<ArrayList<String>> empresaConAporte = indicesService.graficoCantidadAportesEmpresas(COD_OPORTUNIDAD_DEFAULT,COD_VIGENCIA_DEFAULT,PAGE_SIZE_DEFAULT,0);

       //String empresasconAportesJSON = new Gson().toJson(empresaConAporte);

        model.addAttribute("cantidadAportesEmpresas", cantidadAportesEmpresas);
        //model.addAttribute("cantidadAportesEmpresasGraf", empresasconAportesJSON);

        // valores por defecto seleccionados en el vista
        model.addAttribute("tipoCompromiso", COD_COMPROMISO_DEFAULT);
        model.addAttribute("anio", ANIO_DEFAULT);
        model.addAttribute("tipoVigencia", COD_VIGENCIA_DEFAULT);
        model.addAttribute("cantidadMostrar", TAMANIO_LISTADO_DEFAULT);

        return "/indices/empresas/cantidadAportesEmpresas";
    }

    @RequestMapping(value = "/indices/empresas/buscarCantidadAportesEmpresas", method = RequestMethod.POST)
    public String buscarCantidadAportesEmpresas(@RequestParam("codInstitucion") String codInstitucion,
                                                @RequestParam("tipoCompromiso") String tipoCompromiso,
                                                @RequestParam ("tipoVigencia") String vigencia,
                                                @RequestParam ("cantidadMostrar") Integer cantidadMostrar,
                                                Model model) {

        // datos del indice

        ArrayList<ArrayList<String>> cantidadAportesEmpresas = indicesService.indiceCantidadAportesEmpresas(tipoCompromiso,vigencia,cantidadMostrar,0);


        String compromiso = null;
        if(tipoCompromiso.equals("0"))compromiso="Todos";
        else if(tipoCompromiso.equals("1"))compromiso="Donacion";
        else if(tipoCompromiso.equals("2"))compromiso="Auspicio";
        else if(tipoCompromiso.equals("3"))compromiso="Venta de Servicios";

        model.addAttribute("cantidadAportesEmpresas", cantidadAportesEmpresas);
        model.addAttribute("tipoCompromiso", tipoCompromiso);
        model.addAttribute("tipoVigencia", vigencia);
        model.addAttribute("anio", ANIO_DEFAULT);
        model.addAttribute("cantidadMostrar", cantidadMostrar);

        return "/indices/empresas/cantidadAportesEmpresas";
    }

    /**
     * Despliega la vista para la creación del indice de cantidad de {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante} de un
     * Operador {@link crm.entities.Usuario}
     *
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de cantidadContactoEmpresaPorOperador
     *
     * @author  Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @RequestMapping(value = "/indices/empresas/cantidadContactoEmpresaPorOperador", method = RequestMethod.GET)
    public String cantidadContactoEmpresaPorOperador(Model model) {

        // datos del indice
        Page<List<String>> cantidadContactoEmpresaPorOperador = indicesService.indiceCantidadContactoEmpresaPorOperador("Todos",ANIO_DEFAULT, PAGE_SIZE_DEFAULT,0);

        this.generatePagination(model, cantidadContactoEmpresaPorOperador);

        model.addAttribute("cantidadContactoEmpresaPorOperador", cantidadContactoEmpresaPorOperador);
        model.addAttribute("operador", " ");
        model.addAttribute("tipoOportunidad", -1);
        model.addAttribute("tipoOportunidades", entidadesTipoService.buscarTodosTipoOportunidad());
        model.addAttribute("anio", ANIO_DEFAULT);

        return "/indices/empresas/cantidadContactoEmpresaPorOperador";
    }

    @RequestMapping(value = "/indices/empresas/buscarCantidadContactoEmpresaPorOperador", method = RequestMethod.GET)
    public String buscarCantidadContactoEmpresaPorOperador(Model model,
                                                           @RequestParam("operador") String operador,
                                                           @RequestParam("usuarioOperador") String nombreOperador,
                                                           @RequestParam("tipoOportunidad") Short tipoOportunidad,
                                                           @RequestParam("anio") String anio) {

        // datos del indice
        Page<List<String>> cantidadContactoEmpresaPorOperador = null;

        cantidadContactoEmpresaPorOperador = indicesService.indiceCantidadContactoEmpresaPorOportunidad(tipoOportunidad,operador,nombreOperador,anio, PAGE_SIZE_DEFAULT, 0);

        this.generatePagination(model, cantidadContactoEmpresaPorOperador);

        model.addAttribute("cantidadContactoEmpresaPorOperador", cantidadContactoEmpresaPorOperador);
        model.addAttribute("tipoOportunidad", tipoOportunidad);
        model.addAttribute("tipoOportunidades", entidadesTipoService.buscarTodosTipoOportunidad());
        model.addAttribute("operador", operador);
        model.addAttribute("anio", anio);
        model.addAttribute("usuarioOperador", nombreOperador);

        return "/indices/empresas/cantidadContactoEmpresaPorOperador";
    }


    // --------------------------------- PAGINACION ---------------------------------


    /**
     * Metodo que obtiene el numero de pagina de inicio, final y actual para la paginación.
     *
     * @param model Modelo donde se cargaran los datos de la paginacion.
     * @param page Resultados de busqueda paginados para una entidad en particular.
     */
    private void generatePagination(Model model, Page page) {
        int actual = page.getNumber() + 1;
        int inicio = Math.max(1, actual - 5);
        int fin = Math.min(inicio + 10, page.getTotalPages());
        model.addAttribute("beginIndex", inicio);
        model.addAttribute("endIndex", fin);
        model.addAttribute("currentIndex", actual);
    }

}
