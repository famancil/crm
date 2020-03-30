package crm.services;


import crm.entities.*;
import crm.repositories.*;
import crm.utils.KeyValueContainer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Servicio que contiene los metodos para manejar los diversos Indices generados en la plataforma
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Component
public class IndicesService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PostulacionOfertaLaboralUsmempleo}.
     */
    @Autowired
    PostulacionOfertaLaboralUsmempleoRepository postulacionOfertaLaboralUsmempleoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.OfertaCarreraUsmempleo}.
     */
    @Autowired
    OfertaCarreraUsmempleoRepository ofertaCarreraUsmempleoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.InfoProfesionalExalumno}.
     */
    @Autowired
    InfoProfesionalExalumnoRepository infoProfesionalExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.OfertaLaboralUsmempleo}.
     */
    @Autowired
    OfertaLaboralUsmempleoRepository ofertaLaboralUsmempleoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Carrera}.
     */
    @Autowired
    CarreraRepository carreraRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AntecedenteEducacional}.
     */
    @Autowired
    AntecedenteEducacionalRepository antecedenteEducacionalRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ActividadExalumno}.
     */
    @Autowired
    ActividadExalumnoRepository actividadExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AntecedenteEducacional}.
     */
    @Autowired
    UsuarioRepository usuarioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoSector}.
     */
    @Autowired
    TipoSectorRepository tipoSectorRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AporteEmpresa}.
     */
    @Autowired
    AporteEmpresaRepository aporteEmpresaRepository;

    @Autowired
    SucursalEmpresaRepository sucursalEmpersaRepository;

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    ContactoHistoricoEmpresaPersonaParticipanteRepository contactoHistoricoEmpresaPersonaParticipanteRepository;

    @Autowired
    EncuestaOfertaLaboralRepository encuestaOfertaLaboralRepository;

    @Autowired
    EncuestaPostulacionLaboralRepository encuestaPostulacionLaboralRepository;

    @Autowired
    VideoCurriculoUsuarioRepository videoCurriculoUsuarioRepository;

    @Autowired
    AdminOfertaLaboralUsmempleoRepository adminOfertaLaboralUsmempleoRepository;

    @Autowired
    AutorizacionUsuarioRepository autorizacionUsuarioRepository;

    /**
     * Objeto del tipo EntityManagerFactory utilizado para el manejo de criterias.
     */
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private EntityManagerFactory emf;

    /**
     * {@link org.apache.log4j.Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(IndicesService.class);



    // --------------------------------- EMPLEOS ---------------------------------



    /**
     * Obtiene un listado, de manera paginada, de la cantidad de {@link crm.entities.OfertaLaboralUsmempleo} por mes
     * de todas las {@link crm.entities.Carrera}, realizando una busqueda según un año y tipoVigencia especificados como parametros.
     *
     * @param anio Año sobre el cual realizar la busqueda
     * @param tipoVigencia TipoVigencia sobre el cual realizar la busqueda
     * @param tamanoPagina Cantidad de elementos a mostrar por página
     * @param numeroPagina Numero de la pagina de la paginación, que se desea mostrar
     *
     * @return Coleccion {@Link java.util.Page} de {@Link java.util.List}, contenedora del nombre de la carrera y la
     * cantidad de ofertas por mes
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public Page<List<String>> indiceCantidadOfertaLaboralPorCarrera (String tipoVigencia,
                                                                     String anio,
                                                                     Integer tamanoPagina,
                                                                     Integer numeroPagina) {

        // caso en que se seleccionó todos los tipos de vigencia
        if (tipoVigencia.equals("0")) {
            tipoVigencia = "%";
        }

        PageRequest pageRequest = new PageRequest(numeroPagina, tamanoPagina);

        return ofertaLaboralUsmempleoRepository.indiceCantidadOfertaLaboralPorCarrera(tipoVigencia, Integer.parseInt(anio), pageRequest);
    }



    /**
     * Obtiene un listado por mes de la cantidad de {@link crm.entities.Empresa} por que ofrecen {@link crm.entities.OfertaLaboralUsmempleo},
     * realizando una busqueda según un año y tipoVigencia especificados como parametros. Solamente son obtenidos los
     * meses que cuenten ofertas de empresas con los parametros de busqueda especificados.
     *
     * @param tipoVigencia Tipo de Vigencia sobre la cual se desea realizar la busqueda
     * @param anio Año sobre el cual se desea realizar la busqueda
     *
     * @return Array {@Link java.util.String} por mes de la cantidad de empresas que ofrecen empleo
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<String>  indiceCantidadEmpresasOfrecenEmpleo (String tipoVigencia, String anio) {

        Long totalEmpresas = 0L;
        ArrayList<String> indiceCantidadEmpresasOfrecenEmpleo = new ArrayList<String>();

        // caso en que se seleccionó todos los tipos de vigencia
        if (tipoVigencia.equals("0")) {
            tipoVigencia = "%";
        }

        // inicialización del Array con las cantidades por mes (cada posicion, un mes) y uno del total
        String[] indicePorMes = new String[12];
        for (int i=0; i < 12; i ++) {
            indicePorMes[i] = "0";
        }

        // obtencion de los datos del indice
        ArrayList<ArrayList<String>> cantidadEmpresasOfrecenEmpleo = ofertaLaboralUsmempleoRepository.indiceCantidadEmpresasOfrecenEmpleo(tipoVigencia, Integer.parseInt(anio));

        // iteracion en la lista de resultados
        Iterator iterCantidadEmpresasOfrecenEmpleo = cantidadEmpresasOfrecenEmpleo.iterator();
        Iterator iterTotales = cantidadEmpresasOfrecenEmpleo.iterator();

        // obtencion de los datos
        while (iterTotales.hasNext()) {
            Object[] datos = (Object[]) iterTotales.next();

            Integer mes = (Integer) datos[0];
            Long cantidad = (Long) datos[1];
            totalEmpresas = totalEmpresas + cantidad;

            // guardado de la cantidad, para cada mes
            indicePorMes[mes-1] = String.valueOf(cantidad);
        }

        for(int indice = 0; indice < 12; indice ++) {
            Long cantidad = Long.valueOf(indicePorMes[indice]);
            Float porcentajeEmpresasPorMes = (100.0f * cantidad ) / totalEmpresas;

            DecimalFormat df = new DecimalFormat("#.##");                                                                       // formato conversion a porcentaje

            // guardado de la cantidad, para cada mes
            indiceCantidadEmpresasOfrecenEmpleo.add(String.valueOf(cantidad));
            indiceCantidadEmpresasOfrecenEmpleo.add( String.valueOf(df.format(porcentajeEmpresasPorMes) + "%"));
        }

        // valores totales
        indiceCantidadEmpresasOfrecenEmpleo.add(String.valueOf(totalEmpresas));
        indiceCantidadEmpresasOfrecenEmpleo.add("100%");

        return indiceCantidadEmpresasOfrecenEmpleo;
    }



    /**
     * Obtiene un listado de las cantidades de {@link crm.entities.OfertaLaboralUsmempleo} según el
     * {@link crm.entities.TipoOferta}, realizando una busqueda según un año y tipoVigencia especificados como
     * parametros.
     *
     * @param tipoVigencia Tipo de Vigencia sobre la cual se desea realizar la busqueda
     * @param anio Año sobre el cual se desea realizar la busqueda
     *
     * @return Coleccion {@Link java.util.ArrayList} por cada {@link crm.entities.TipoOferta}, que contiene una
     *          coleccción {@Link java.util.ArrayList} que almacena el nombre del {@link crm.entities.TipoOferta} y la
     *          cantidad de {@link crm.entities.OfertaLaboralUsmempleo} asociadas
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  indiceCantidadOfertaLaboralPorTipoOferta (String tipoVigencia,
                                                                                   String anio) {
        Long totalOfertas = 0L;
        ArrayList<ArrayList<String>> indiceCantidadOfertaLaboralPorTipoOferta = new ArrayList<ArrayList<String>>();

        // caso en que se seleccionó todos los tipo
        if (tipoVigencia.equals("0")) {
            tipoVigencia = "%";
        }

        ArrayList<ArrayList<String>> resultadoCantidadOfertaLaboralPorTipoOferta = ofertaLaboralUsmempleoRepository.indiceCantidadOfertaLaboralPorTipoOferta(tipoVigencia, Integer.parseInt(anio));

        Iterator iterTotales = resultadoCantidadOfertaLaboralPorTipoOferta.iterator();
        Iterator iterResultadoCantidadOfertasPorTipoOferta = resultadoCantidadOfertaLaboralPorTipoOferta.iterator();

        // obtencion del total de personas
        while (iterTotales.hasNext()) {
            Object[] datosPorTipoOferta = (Object[]) iterTotales.next();

            totalOfertas = totalOfertas + (Long) datosPorTipoOferta[1];
        }

        // obtencion del porcentaje
        while (iterResultadoCantidadOfertasPorTipoOferta.hasNext()) {
            Object[] datosPorTipoOferta = (Object[]) iterResultadoCantidadOfertasPorTipoOferta.next();
            ArrayList<String> datosIntegrados = new ArrayList<String>();

            Long cantidadPorOferta = (Long) datosPorTipoOferta[1];                                          // cantidad por tipo oferta
            Float porcentajePorTipoOferta = (100.0f * cantidadPorOferta ) / totalOfertas;

            DecimalFormat df = new DecimalFormat("#.##");                                                   // conversion a porcentaje

            datosIntegrados.add( (String) datosPorTipoOferta[0] );                                          // Nombre del tipo de oferta
            datosIntegrados.add( String.valueOf( cantidadPorOferta ));                                      // Cantidad por tipo de oferta
            datosIntegrados.add( String.valueOf( df.format(porcentajePorTipoOferta) + "%") );                // Porcentaje por tipo de oferta

            indiceCantidadOfertaLaboralPorTipoOferta.add(datosIntegrados);                                   // datos integrados por Tipo oferta
        }

        // agrega datos totales
        ArrayList<String> datosIntegrados = new ArrayList<String>();
        datosIntegrados.add( "Total" );
        datosIntegrados.add( String.valueOf(totalOfertas));
        datosIntegrados.add( "100%");
        indiceCantidadOfertaLaboralPorTipoOferta.add(datosIntegrados);

        return indiceCantidadOfertaLaboralPorTipoOferta;
    }




    /**
     * Obtiene un listado con los resultados del indice de ranking de las {@link crm.entities.Carrera} según su demanda
     * de {@link crm.entities.OfertaLaboralUsmempleo}, según el año, cantidad y orden especificados como parametros.
     *
     * @param anio Año sobre el cual se desean mostrar los indices
     * @param orden Orden del ranking; mayor cantidad o menor cantidad de ofertas
     * @param cantidadMostrar Cantidad carreras a mostrar como resultado del indice
     *
     * @return Coleccion {@Link java.util.ArrayList} por cada {@link crm.entities.Carrera}, que contiene una coleccción {@Link java.util.List}
     * que almacena el nombre de la {@link crm.entities.Carrera} y la cantidad de {@link crm.entities.OfertaCarreraUsmempleo}
     * asociadas
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  indiceRankingCarrerasPorDemanda (String anio, String orden, String cantidadMostrar) {

        PageRequest pageRequest = null;

        // Pagerequest permite limitar la cantidad de elementos  y el orden ascendente/descendente de los resultados
        // (hql no lo permite por si mismo)

        if (orden.equals("1")) {
            pageRequest = new PageRequest(0, Integer.parseInt(cantidadMostrar),  Sort.Direction.DESC, "COUNT (c.codCarrera)");
        }
        if (orden.equals("2")) {
            pageRequest = new PageRequest(0, Integer.parseInt(cantidadMostrar),  Sort.Direction.ASC, "COUNT (c.codCarrera)");
        }

        return ofertaLaboralUsmempleoRepository.rankingCarrerasPorDemanda(Integer.parseInt(anio), pageRequest);
    }





    /**
     * Obtiene un listado con los resultados del indice de ranking de las {@link crm.entities.Carrera} según la cantidad
     * de vacantes declaradas en {@link crm.entities.OfertaLaboralUsmempleo}, según el año, cantidad y orden
     * especificados como parametros.
     *
     * @param anio Año sobre el cual se desean mostrar los indices
     * @param orden Orden del ranking; mayor cantidad o menor cantidad de ofertas
     * @param cantidadMostrar Cantidad carreras a mostrar como resultado del indice
     *
     * @return Coleccion {@Link java.util.ArrayList} por cada {@link crm.entities.Carrera}, que contiene una coleccción {@Link java.util.ArrayList}
     * que almacena el nombre de la {@link crm.entities.Carrera} y la cantidad de vacantes de las
     * {@link crm.entities.OfertaCarreraUsmempleo} asociadas
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  indiceRankingCarrerasPorVacantes (String anio, String orden, String cantidadMostrar) {

        PageRequest pageRequest = null;

        // Pagerequest permite limitar la cantidad de elementos  y el orden ascendente/descendente de los resultados
        // (hql no lo permite por si mismo)

        if (orden.equals("1")) {
            pageRequest = new PageRequest(0, Integer.parseInt(cantidadMostrar),  Sort.Direction.DESC, "SUM (o.vacantes)");
        }
        if (orden.equals("2")) {
            pageRequest = new PageRequest(0, Integer.parseInt(cantidadMostrar), Sort.Direction.ASC, "SUM (o.vacantes)");
        }

        return ofertaLaboralUsmempleoRepository.rankingCarrerasPorVacantes(Integer.parseInt(anio), pageRequest);
    }





    /**
     * Obtiene un listado con los resultados del indice de ranking de las {@link crm.entities.Carrera} según el salario
     * promedio declarada en {@link crm.entities.OfertaLaboralUsmempleo}, según el año, cantidad y orden
     * especificados como parametros.
     *
     * @param orden Orden del ranking; mayor cantidad o menor cantidad de vacantes
     * @param anio Año sobre el cual se desean mostrar los indices
     * @param cantidadMostrar Cantidad carreras a mostrar como resultado del indice
     * @param rangoInicial Inicio del rango promedio de salario de las carreras a mostrar como resultado del indice
     * @param rangoFinal Final del rango promedio de salario de las carreras a mostrar como resultado del indice
     *
     * @return Coleccion {@Link java.util.ArrayList} por cada {@link crm.entities.Carrera}, que contiene una coleccción
     *         {@Link java.util.ArrayList} que almacena el nombre de la {@link crm.entities.Carrera}, el salario promedio,
     *         los salarios promedios según {@Link java.util.TipoOferta} y la cantidad de
     *         {@link crm.entities.OfertaCarreraUsmempleo} asociadas, ordenadas según el promedio de salario
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  indiceRankingCarrerasPorSalario (String anio, String orden, String cantidadMostrar, String rangoInicial, String rangoFinal) {

        PageRequest pageRequest = null;

        // Pagerequest permite limitar la cantidad de elementos  y el orden ascendente/descendente de los resultados
        // (hql no lo permite por si mismo)

        if (orden.equals("1")) {
            pageRequest = new PageRequest(0, Integer.parseInt(cantidadMostrar),  Sort.Direction.DESC, "ROUND(AVG(o.salario))");
        }
        if (orden.equals("2")) {
            pageRequest = new PageRequest(0, Integer.parseInt(cantidadMostrar), Sort.Direction.ASC, "ROUND(AVG(o.salario))");
        }

        return ofertaLaboralUsmempleoRepository.indiceRankingCarrerasPorSalario(Integer.parseInt(anio), Double.parseDouble(rangoInicial), Double.parseDouble(rangoFinal), pageRequest);
    }



    /**
     * Obtiene un listado por mes de la cantidad de {@link crm.entities.PostulacionOfertaLaboralUsmempleo} y
     * {@link crm.entities.OfertaLaboralUsmempleo} y vacantes
     *
     * @param tipoVigencia Tipo de Vigencia sobre la cual se desean buscar ofertas laborales
     * @param anio Año sobre el cual se desean mostrar los indices
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de las meses; para cada una de ellos
     *          coleccción {@Link java.util.ArrayList} del mes y la cantidad de  {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     *          asociadas
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  indiceCantidadPostulacionesOfertasEmpleo (String tipoVigencia, String anio) {

        // caso en que se seleccionó todos los tipos de vigencia
        if (tipoVigencia.equals("0")) {
            tipoVigencia = "%";
        }

        ArrayList<ArrayList<String>> indiceCantidadPostulacionesOfertasEmpleo = new ArrayList<ArrayList<String>>();

        String resultadoPostulaciones = postulacionOfertaLaboralUsmempleoRepository.indiceCantidadPostulacionesOfertasEmpleoPorMes(Integer.parseInt(anio));
        String resultadoOfertas = ofertaLaboralUsmempleoRepository.indiceCantidadOfertasEmpleoPorMes(tipoVigencia, Integer.parseInt(anio));
        String resultadoVacantes = ofertaLaboralUsmempleoRepository.indiceCantidadVacantesOfertasEmpleoPorMes(tipoVigencia, Integer.parseInt(anio));

        String[] cantidadPostulaciones = resultadoPostulaciones.split(",");
        String[] cantidadOfertas = resultadoOfertas.split(",");
        String[] cantidadVacantes = resultadoVacantes.split(",");

        for (int indice = 0 ; indice < 13; indice++) {
            ArrayList<String> datosIntegrados = new ArrayList<String>();

            if(indice != 12) {
                datosIntegrados.add(String.valueOf(indice + 1));                        // Numero del mes
            }
            else {
                datosIntegrados.add("Total");                                           // Total
            }

            datosIntegrados.add(cantidadPostulaciones[indice]);                      // cantidad postulaciones del mes
            datosIntegrados.add(cantidadOfertas[indice]);                            // cantidad ofertas del mes
            datosIntegrados.add(cantidadVacantes[indice]);                           // cantidad vacantes del mes

            // razon "postulaciones/ofertas"    (en caso que sea distinto de cero - evita division por cero)
            if (Integer.parseInt(cantidadOfertas[indice]) != 0) {
                datosIntegrados.add( String.valueOf( Integer.parseInt(cantidadPostulaciones[indice]) / Integer.parseInt(cantidadOfertas[indice]) ));
            }
            else {
                datosIntegrados.add("0");
            }

            // razon "postulaciones/vacantes"    (en caso que sea distinto de cero - evita division por cero)
            if (Integer.parseInt(cantidadVacantes[indice]) != 0) {
                datosIntegrados.add( String.valueOf( Integer.parseInt(cantidadPostulaciones[indice]) / Integer.parseInt(cantidadVacantes[indice]) ));
            }
            else {
                datosIntegrados.add("0");
            }

            indiceCantidadPostulacionesOfertasEmpleo.add(datosIntegrados);          // datos integrados por mes
        }

        return indiceCantidadPostulacionesOfertasEmpleo;
    }




    /**
     * Obtiene un listado por Región, de la cantidad de {@link crm.entities.PostulacionOfertaLaboralUsmempleo}, y los datos
     * asociadas a ellas, como la cantidad de {@link crm.entities.OfertaLaboralUsmempleo} asociadas a las
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo} y la cantidad de vacantes asociadas a dichas
     * {@link crm.entities.OfertaLaboralUsmempleo}; realizando una busqueda según el año de la
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo} y vigencia de la {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @param tipoVigencia Tipo de Vigencia sobre la cual se desean buscar ofertas laborales
     * @param anio Año sobre el cual se desean mostrar los indices
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de las regiones; para cada una de ellos
     *          coleccción {@Link java.util.ArrayList} de la cantidad de  {@link crm.entities.PostulacionOfertaLaboralUsmempleo},
     *          {@link crm.entities.OfertaLaboralUsmempleo} y sus vacantes asociadas
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  indiceCantidadPostulacionesOfertasEmpleoPorRegionDetalleCantidadOfertasVacantes (String tipoVigencia, String anio) {
        Long cantidadPostulaciones = 0L;
        Long cantidadOfertas = 0L;
        Long cantidadVacantes = 0L;
        Long totalPostulaciones = 0L;
        Long totalOfertas = 0L;
        Long totalVacantes = 0L;

        // caso en que se seleccionó todos los tipos de vigencia
        if (tipoVigencia.equals("0")) {
            tipoVigencia = "%";
        }

        ArrayList<ArrayList<String>> indiceCantidadPostulacionesOfertasEmpleoPorRegion = new ArrayList<ArrayList<String>>();

        ArrayList<ArrayList<String>> resultadoPostulaciones = postulacionOfertaLaboralUsmempleoRepository.indiceCantidadPostulacionesOfertasEmpleoPorRegion(tipoVigencia, Integer.parseInt(anio));
        ArrayList<ArrayList<String>> resultadoOfertas = ofertaLaboralUsmempleoRepository.indiceCantidadOfertasEmpleoPorRegion(tipoVigencia, Integer.parseInt(anio));
        ArrayList<ArrayList<String>> resultadoVacantes = ofertaLaboralUsmempleoRepository.indiceCantidadVacantesOfertasEmpleoPorRegion(tipoVigencia, Integer.parseInt(anio));

        // iteracion en la lista de resultados
        Iterator iterPostulaciones = resultadoPostulaciones.iterator();
        Iterator iterOfertas = resultadoOfertas.iterator();
        Iterator iterVacantes = resultadoVacantes.iterator();

        while (iterPostulaciones.hasNext()) {
            ArrayList<String> datosIntegrados = new ArrayList<String>();

            Object[] datosPostulaciones = (Object[]) iterPostulaciones.next();
            Object[] datosOfertas = (Object[]) iterOfertas.next();
            Object[] datosVacantes = (Object[]) iterVacantes.next();

            // valores
            cantidadPostulaciones = (Long) datosPostulaciones[1];
            cantidadOfertas = (Long) datosOfertas[1];
            cantidadVacantes = (Long) datosVacantes[1];

            // suma para los totales
            totalPostulaciones = totalPostulaciones + cantidadPostulaciones;
            totalOfertas = totalOfertas + cantidadOfertas;
            totalVacantes = totalVacantes + cantidadVacantes;

            // datos por region
            datosIntegrados.add( (String) datosPostulaciones[0] );                   // Nombre de le region
            datosIntegrados.add( String.valueOf(cantidadPostulaciones ));              // cantidad de postulaciones
            datosIntegrados.add( String.valueOf(cantidadOfertas ));                    // cantidad de ofertas
            datosIntegrados.add( String.valueOf(cantidadVacantes ));                   // cantidad de vacantes

            // razon "postulaciones/ofertas"    (en caso que sea distinto de cero - evita division por cero)
            if (cantidadOfertas != 0L) {
                datosIntegrados.add( String.valueOf( cantidadPostulaciones / cantidadOfertas ));
            }
            else {
                datosIntegrados.add("0");
            }

            // razon "postulaciones/vacantes"    (en caso que sea distinto de cero - evita division por cero)
            if (cantidadVacantes != 0L) {
                datosIntegrados.add( String.valueOf( cantidadPostulaciones / cantidadVacantes ));
            }
            else {
                datosIntegrados.add("0");
            }

            indiceCantidadPostulacionesOfertasEmpleoPorRegion.add(datosIntegrados);          // datos integrados por mes
        }

        // inclusion de los datos totales
        ArrayList<String> datosIntegrados = new ArrayList<String>();
        datosIntegrados.add("Total");                                               // Total
        datosIntegrados.add( String.valueOf(totalPostulaciones) );                  // total de postulaciones
        datosIntegrados.add( String.valueOf(totalOfertas) );                        // total de ofertas
        datosIntegrados.add( String.valueOf(totalVacantes) );                       // total de vacantes

        // razon "postulaciones/ofertas"    (en caso que sea distinto de cero - evita division por cero)
        if (cantidadOfertas != 0L) {
            datosIntegrados.add( String.valueOf( totalPostulaciones / totalOfertas ));
        }
        else {
            datosIntegrados.add("0");
        }

        // razon "postulaciones/vacantes"    (en caso que sea distinto de cero - evita division por cero)
        if (cantidadVacantes != 0L) {
            datosIntegrados.add( String.valueOf( totalPostulaciones / totalVacantes ));
        }
        else {
            datosIntegrados.add("0");
        }

        indiceCantidadPostulacionesOfertasEmpleoPorRegion.add(datosIntegrados);          // datos integrados por mes

        return indiceCantidadPostulacionesOfertasEmpleoPorRegion;
    }




    /**
     * Obtiene un listado de la cantidad, para cada rango de edad, de {@link crm.entities.Usuario} que han hecho
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo}, realizando una busqueda según el año de
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo} ingresado como parametro
     *
     * @param anio Anio sobre el cual se desea realizar la busqueda
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de los rango de edad; para cada una de ellos tendrá
     *          la cantidad y el porcentaje asociado
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  cantidadUsuariosPostulantesPorEdad(String anio) {

        DecimalFormat df = new DecimalFormat("#.##");                                                   // conversion a porcentaje
        ArrayList<ArrayList<String>> indiceCantidadUsuariosPostulantesPorEdad = new ArrayList<ArrayList<String>>();


        String datosBrutosIndice = postulacionOfertaLaboralUsmempleoRepository.cantidadUsuariosPostulantesPorEdad(Integer.parseInt(anio));

        String[] cantidadPostulaciones = datosBrutosIndice.split(",");

        Integer tamanioIndices = cantidadPostulaciones.length;
        Long total = Long.parseLong(cantidadPostulaciones[tamanioIndices-1]);

        // cantidad y porcentaje por rango
        int i;
        for (i=0; i < tamanioIndices - 1; i++) {
            ArrayList<String> datosIntegrados = new ArrayList<String>();

            int rango = Integer.parseInt(cantidadPostulaciones[i]);
            float porcentajeRango = 100 * ((float) rango) / ((float) total);

            datosIntegrados.add(String.valueOf(i));
            datosIntegrados.add(String.valueOf(rango));
            datosIntegrados.add(String.valueOf(df.format(porcentajeRango) + "%"));                // Porcentaje por tipo de oferta
            indiceCantidadUsuariosPostulantesPorEdad.add(datosIntegrados);
        }

        // datos totales
        ArrayList<String> datosIntegrados = new ArrayList<String>();
        datosIntegrados.add(String.valueOf(i));
        datosIntegrados.add(String.valueOf(total));
        datosIntegrados.add(String.valueOf("100 %"));                // Porcentaje por tipo de oferta


        indiceCantidadUsuariosPostulantesPorEdad.add(datosIntegrados);

        return indiceCantidadUsuariosPostulantesPorEdad;
    }




    /**
     * Obtiene un listado de la cantidad, para cada {@link crm.entities.TipoSituacionLaboral}, de {@link crm.entities.Usuario} que han hecho
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo}, realizando una busqueda según el año de
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo} ingresado como parametro
     *
     * @param anio Anio sobre el cual se desea realizar la busqueda
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de datos para cada {@link crm.entities.TipoSituacionLaboral};
     *          para cada una de ellos tendrá el nombre del tipo, la cantidad y el porcentaje asociado
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  cantidadUsuariosPostulantesPorSituacionLaboral (String anio) {

        Long totalPostulaciones = 0L;
        ArrayList<ArrayList<String>> indiceCantidadUsuariosPostulantesPorSituacionLaboral = new ArrayList<ArrayList<String>>();


        ArrayList<ArrayList<String>> resultadoCantidadUsuariosPostulantesPorSituacionLaboral = postulacionOfertaLaboralUsmempleoRepository.cantidadUsuariosPostulantesPorSituacionLaboral(Integer.parseInt(anio));


        Iterator iterTotales = resultadoCantidadUsuariosPostulantesPorSituacionLaboral.iterator();          // iterador para calcular el total
        Iterator iterResultadoCantidadPostulacionPorSituacionLaboral = resultadoCantidadUsuariosPostulantesPorSituacionLaboral.iterator(); // iterador para ir por cada situacion laboral

        // obtencion del total de postulaciones
        while (iterTotales.hasNext()) {
            Object[] datosPorTipoSituacionLaboral = (Object[]) iterTotales.next();

            totalPostulaciones = totalPostulaciones + (Long) datosPorTipoSituacionLaboral[1];
        }

        // obtencion del porcentaje para cada tipo de situacion laboral
        while (iterResultadoCantidadPostulacionPorSituacionLaboral.hasNext()) {
            Object[] datosPorTipoSituacionLaboral = (Object[]) iterResultadoCantidadPostulacionPorSituacionLaboral.next();      // cada datosPorTipoSituacionLaboral contiene: (nombre de situacion laboral, cantidad)
            ArrayList<String> datosIntegrados = new ArrayList<String>();                         // mantiene los datos de cada tiposituacion laboral nombre de situacion laboral, cantidad, porcentaje)

            Long cantidadPorSituacionLaboral = (Long) datosPorTipoSituacionLaboral[1];                                         // cantidad por tipo oferta
            Float porcentajePorTipoSituacionLaboral = (100.0f * cantidadPorSituacionLaboral ) / totalPostulaciones;

            DecimalFormat df = new DecimalFormat("#.##");                                                                   // conversion a porcentaje

            datosIntegrados.add( (String) datosPorTipoSituacionLaboral[0] );                                                  // Nombre del TipoSituacionLaboral
            datosIntegrados.add( String.valueOf( cantidadPorSituacionLaboral ));                                              // Cantidad por TipoSituacionLaboral
            datosIntegrados.add( String.valueOf( df.format(porcentajePorTipoSituacionLaboral) + "%") );                                   // Porcentaje por TipoSituacionLaboral

            indiceCantidadUsuariosPostulantesPorSituacionLaboral.add(datosIntegrados);                                        // agrega a la lista final, los datos integrados de cada TipoSituacionLaboral
        }

        // agrega datos totales, a la lista con todos los datos
        ArrayList<String> datosIntegrados = new ArrayList<String>();
        datosIntegrados.add( "Total" );
        datosIntegrados.add( String.valueOf(totalPostulaciones));
        datosIntegrados.add( "100%");
        indiceCantidadUsuariosPostulantesPorSituacionLaboral.add(datosIntegrados);

        return indiceCantidadUsuariosPostulantesPorSituacionLaboral;
    }





    /**
     * Obtiene un listado de la cantidad, para cada {@link crm.entities.Region}, de {@link crm.entities.Usuario} que han hecho
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo}, realizando una busqueda según el año de
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo} ingresado como parametro
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     * TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  cantidadUsuariosPostulantesPorRegion (String anio) {
        Long totalPostulaciones = 0L;

        ArrayList<ArrayList<String>> indiceCantidadUsuariosPostulantesPorRegion = new ArrayList<ArrayList<String>>();

        ArrayList<ArrayList<String>> resultadoCantidadPostulacionesPorRegion = postulacionOfertaLaboralUsmempleoRepository.cantidadUsuariosPostulantesPorRegion(Integer.parseInt(anio));

        Iterator iterTotales = resultadoCantidadPostulacionesPorRegion.iterator();                                              // iterador para calcular el total
        Iterator iterResultadoCantidadPostulacionPorRegion = resultadoCantidadPostulacionesPorRegion.iterator();                // iterador para ir por cada region

        // obtencion del total de postulaciones
        while (iterTotales.hasNext()) {
            Object[] datosPorRegion = (Object[]) iterTotales.next();

            totalPostulaciones = totalPostulaciones + (Long) datosPorRegion[1];
        }

        // obtencion del porcentaje para cada region
        while (iterResultadoCantidadPostulacionPorRegion.hasNext()) {
            Object[] datosPorRegion = (Object[]) iterResultadoCantidadPostulacionPorRegion.next();                      // cada datosPorRegion contiene: (nombre de region, cantidad por region)
            ArrayList<String> datosIntegrados = new ArrayList<String>();                                                     // mantiene los datos de cada region: (nombre de region, cantidad, porcentaje)

            Long cantidadPorRegion = (Long) datosPorRegion[1];                                                          // cantidad por region
            Float porcentajePorRegion = (100.0f * cantidadPorRegion ) / totalPostulaciones;

            DecimalFormat df = new DecimalFormat("#.##");                                                               // conversion a porcentaje

            datosIntegrados.add( (String) datosPorRegion[0] );                                                          // Nombre de la Region
            datosIntegrados.add( String.valueOf( cantidadPorRegion ));                                                  // Cantidad por Region
            datosIntegrados.add( String.valueOf( df.format(porcentajePorRegion) + "%") );                               // Porcentaje por Region

            indiceCantidadUsuariosPostulantesPorRegion.add(datosIntegrados);                                                  // agrega a la lista final, los datos de cada Region
        }

        // agrega datos totales, a la lista con todos los datos
        ArrayList<String> datosIntegrados = new ArrayList<String>();
        datosIntegrados.add( "Total" );
        datosIntegrados.add( String.valueOf(totalPostulaciones));
        datosIntegrados.add( "100%");
        indiceCantidadUsuariosPostulantesPorRegion.add(datosIntegrados);

        return indiceCantidadUsuariosPostulantesPorRegion;
    }




    /**
     * Obtiene un listado de la cantidad, para cada {@link crm.entities.Provincia}, de {@link crm.entities.Usuario} que han hecho
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo}, realizando una busqueda según el año de
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo} ingresado como parametro
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     * TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  cantidadUsuariosPostulantesPorProvincia (String anio) {
        Long totalPostulaciones = 0L;

        ArrayList<ArrayList<String>> indiceCantidadUsuariosPostulantesPorProvincia = new ArrayList<ArrayList<String>>();
        Map<String, String> nombreRegionUnico = new HashMap<>();

        ArrayList<ArrayList<String>> resultadoCantidadPostulacionesPorProvincia = postulacionOfertaLaboralUsmempleoRepository.cantidadUsuariosPostulantesPorProvincia(Integer.parseInt(anio));


        Iterator iterTotales = resultadoCantidadPostulacionesPorProvincia.iterator();                                              // iterador para calcular el total
        Iterator iterResultadoCantidadPostulacionPorProvincia = resultadoCantidadPostulacionesPorProvincia.iterator();                // iterador para ir por cada provincia

        // obtencion del total de postulaciones
        while (iterTotales.hasNext()) {
            Object[] datosPorProvincia = (Object[]) iterTotales.next();

            totalPostulaciones = totalPostulaciones + (Long) datosPorProvincia[2];
        }

        // obtencion del porcentaje para cada provincia
        while (iterResultadoCantidadPostulacionPorProvincia.hasNext()) {
            Object[] datosPorProvincia = (Object[]) iterResultadoCantidadPostulacionPorProvincia.next();                      // cada datosPorProvincia contiene: (nombre de region, nombre de provincia, cantidad por region)
            ArrayList<String> datosIntegrados = new ArrayList<String>();                                                     // mantiene los datos de cada provincia: (nombre de region, nombre de provincia, cantidad por region, porcentaje)

            Long cantidadPorProvincia = (Long) datosPorProvincia[2];                                                          // cantidad por provincia
            Float porcentajePorProvincia = (100.0f * cantidadPorProvincia ) / totalPostulaciones;

            DecimalFormat df = new DecimalFormat("#.##");                                                               // conversion a porcentaje

            // Nombre de la Region  (se agrega solo para la primera provincia que la tenga)
            String nombreRegion = (String) datosPorProvincia[0];
            if ( nombreRegionUnico.get(nombreRegion) == null ) {
                datosIntegrados.add( nombreRegion );
                nombreRegionUnico.put(nombreRegion, nombreRegion);
            }
            else {
                datosIntegrados.add( "" );
            }
            datosIntegrados.add( (String) datosPorProvincia[1] );                                                          // Nombre de la Provincia
            datosIntegrados.add( String.valueOf( cantidadPorProvincia ));                                                  // Cantidad por Provincia
            datosIntegrados.add( String.valueOf( df.format(porcentajePorProvincia) + "%") );                            // Porcentaje por Provincia

            indiceCantidadUsuariosPostulantesPorProvincia.add(datosIntegrados);                                         // agrega a la lista final, los datos de cada Provincia
        }

        // agrega datos totales, a la lista con todos los datos
        ArrayList<String> datosIntegrados = new ArrayList<String>();
        datosIntegrados.add( "Total" );
        datosIntegrados.add( "");
        datosIntegrados.add( String.valueOf(totalPostulaciones));
        datosIntegrados.add( "100%");
        indiceCantidadUsuariosPostulantesPorProvincia.add(datosIntegrados);

        return indiceCantidadUsuariosPostulantesPorProvincia;
    }






    /**
     * Obtiene un listado de la cantidad por segmento (cantidad de vacantes), de la cantidad de vacantes de la
     * {@link crm.entities.OfertaLaboralUsmempleo}, según su vigencia y año de publicación, especificados como parámetros.
     *
     * @param tipoVigencia Tipo de Vigencia sobre la cual se desea realizar la busqueda
     * @param anio Año sobre el cual se desea realizar la busqueda
     *
     * Coleccion {@Link java.util.List} por segmento de vacantes, de la cantidad de vacantes de las
     * {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>> indiceSegmentacionVacantes (String tipoVigencia, String anio) {

        // caso en que se seleccionó todos los tipos de vigencia
        if (tipoVigencia.equals("0")) {
            tipoVigencia = "%";
        }

        return ofertaLaboralUsmempleoRepository.indiceSegmentacionVacantes(tipoVigencia, Integer.parseInt(anio));
    }




    /**
     * Obtiene un listado, de manera paginada, de la cantidad de vacantes de {@link crm.entities.OfertaLaboralUsmempleo}
     * por {@link crm.entities.Carrera}, realizando una busqueda según un año de inicio y tipoVigencia especificados
     * como parametros.
     *
     * @param tipoVigencia Tipo de Vigencia sobre la cual se desea realizar la busqueda
     * @param anio Año sobre el cual se desea realizar la busqueda
     * @param tamanoPagina Cantidad de elementos a mostrar por página
     * @param numeroPagina Numero de la pagina de la paginación, que se desea mostrar
     *
     * @return Coleccion {@Link java.util.Page} contenedora de las {@link crm.entities.Carrera}; para cada una de ellas
     *          coleccion {@Link java.util.List}, contenedora de la cantidad de vacantes de las
     *          {@link crm.entities.OfertaLaboralUsmempleo} para cada mes.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public Page<List<String>> indiceCantidadVacantesOfertaLaboralPorCarrera (String tipoVigencia,
                                                                             String anio,
                                                                            Integer tamanoPagina,
                                                                            Integer numeroPagina) {

        // caso en que se seleccionó todos los tipos de vigencia
        if (tipoVigencia.equals("0")) {
            tipoVigencia = "%";
        }

        PageRequest pageRequest = new PageRequest(numeroPagina, tamanoPagina);

        return ofertaLaboralUsmempleoRepository.indiceCantidadVacantesOfertaLaboralPorCarrera(tipoVigencia, Integer.parseInt(anio), pageRequest);
    }




    /**
     * Obtiene un listado de las cantidades de vacantes de {@link crm.entities.OfertaLaboralUsmempleo} para cada
     * {@link crm.entities.TipoOferta}, realizando una busqueda según un año y tipoVigencia especificados como
     * parametros.
     *
     * @param tipoVigencia Tipo de busqueda sobre la cual se desea mostrar el indice
     * @param anio Año sobre el cual se desean mostrar los indices
     *
     * @return Coleccion {@Link java.util.ArrayList} por cada {@link crm.entities.TipoOferta}; para cada una de ellas
     *          coleccción {@Link java.util.ArrayList} que almacena el nombre del {@link crm.entities.TipoOferta} y la
     *          cantidad de vacantes de las {@link crm.entities.OfertaLaboralUsmempleo} asociad
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  indiceCantidadVacantesOfertaLaboralPorTipoOferta (String tipoVigencia, String anio) {

        Long totalOfertas = 0L;
        ArrayList<ArrayList<String>> indiceCantidadVacantesOfertaLaboralPorTipoOferta = new ArrayList<ArrayList<String>>();

        // caso en que se seleccionó todos los tipo
        if (tipoVigencia.equals("0")) {
            tipoVigencia = "%";
        }

        ArrayList<ArrayList<String>> resultadoCantidadVacantesOfertaLaboralPorTipoOferta = ofertaLaboralUsmempleoRepository.indiceCantidadVacantesOfertaLaboralPorTipoOferta(tipoVigencia, Integer.parseInt(anio));

        Iterator iterTotales = resultadoCantidadVacantesOfertaLaboralPorTipoOferta.iterator();
        Iterator iterResultadoCantidadVacantesOfertaLaboralPorTipoOferta = resultadoCantidadVacantesOfertaLaboralPorTipoOferta.iterator();

        // obtencion del total de ofertas
        while (iterTotales.hasNext()) {
            Object[] datosPorTipoOferta = (Object[]) iterTotales.next();

            totalOfertas = totalOfertas + (Long) datosPorTipoOferta[1];
        }

        // obtencion del porcentaje
        while (iterResultadoCantidadVacantesOfertaLaboralPorTipoOferta.hasNext()) {
            Object[] datosPorTipoOferta = (Object[]) iterResultadoCantidadVacantesOfertaLaboralPorTipoOferta.next();
            ArrayList<String> datosIntegrados = new ArrayList<String>();

            Long cantidadPorOferta = (Long) datosPorTipoOferta[1];                                                  // cantidad por tipo oferta
            Float porcentajePorTipoOferta = (100.0f * cantidadPorOferta ) / totalOfertas;

            DecimalFormat df = new DecimalFormat("#.##");                                                           // conversion a porcentaje

            datosIntegrados.add( (String) datosPorTipoOferta[0] );                                                  // Nombre del tipo de oferta
            datosIntegrados.add( String.valueOf( cantidadPorOferta ));                                              // Cantidad por tipo de oferta
            datosIntegrados.add( String.valueOf( df.format(porcentajePorTipoOferta) + "%") );                       // Porcentaje por tipo de oferta

            indiceCantidadVacantesOfertaLaboralPorTipoOferta.add(datosIntegrados);                                   // datos integrados por Tipo oferta
        }

        // agrega datos totales
        ArrayList<String> datosIntegrados = new ArrayList<String>();
        datosIntegrados.add( "Total" );
        datosIntegrados.add( String.valueOf(totalOfertas));
        datosIntegrados.add( "100%");
        indiceCantidadVacantesOfertaLaboralPorTipoOferta.add(datosIntegrados);

        return indiceCantidadVacantesOfertaLaboralPorTipoOferta;


    }



    /**
     * Obtiene un listado de las cantidades de {@link crm.entities.PostulacionOfertaLaboralUsmempleo} a
     * {@link crm.entities.OfertaLaboralUsmempleo}, segmentado por cada {@link crm.entities.TipoOferta},
     * realizando una busqueda según un año y tipoVigencia especificados como parametros.
     *
     * @param tipoVigencia Tipo de busqueda sobre la cual se desea mostrar el indice
     * @param anio Año sobre el cual se desean mostrar los indices
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de las {@link crm.entities.TipoOferta}; para cada una de ellas,
     *          se tiene una coleccion {@Link java.util.ArrayList}, contenedora del nombre del {@link crm.entities.TipoOferta}
     *          y la cantidad de {@link crm.entities.PostulacionOfertaLaboralUsmempleo} asociadas.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  indiceCantidadPostulacionesOfertasEmpleoPorTipoOferta (String tipoVigencia, String anio) {
        Long cantidadPostulaciones = 0L;
        Long cantidadOfertas = 0L;
        Long cantidadVacantes = 0L;
        Long totalPostulaciones = 0L;
        Long totalOfertas = 0L;
        Long totalVacantes = 0L;

        // caso en que se seleccionó todos los tipos de vigencia
        if (tipoVigencia.equals("0")) {
            tipoVigencia = "%";
        }

        ArrayList<ArrayList<String>> indiceCantidadPostulacionesOfertasEmpleoPorTipoOferta = new ArrayList<ArrayList<String>>();

        ArrayList<ArrayList<String>> resultadoPostulaciones = postulacionOfertaLaboralUsmempleoRepository.indiceCantidadPostulacionesOfertasEmpleoPorTipoOferta(tipoVigencia, Integer.parseInt(anio));
        ArrayList<ArrayList<String>> resultadoOfertas = ofertaLaboralUsmempleoRepository.indiceCantidadOfertasEmpleoPorTipoOferta(tipoVigencia, Integer.parseInt(anio));
        ArrayList<ArrayList<String>> resultadoVacantes = ofertaLaboralUsmempleoRepository.indiceCantidadVacantesOfertasEmpleoPorTipoOferta(tipoVigencia, Integer.parseInt(anio));

        // iteracion en la lista de resultados
        Iterator iterPostulaciones = resultadoPostulaciones.iterator();
        Iterator iterOfertas = resultadoOfertas.iterator();
        Iterator iterVacantes = resultadoVacantes.iterator();

        while (iterPostulaciones.hasNext()) {
            ArrayList<String> datosIntegrados = new ArrayList<String>();

            Object[] datosPostulaciones = (Object[]) iterPostulaciones.next();
            Object[] datosOfertas = (Object[]) iterOfertas.next();
            Object[] datosVacantes = (Object[]) iterVacantes.next();

            // valores
            cantidadPostulaciones = (Long) datosPostulaciones[1];
            cantidadOfertas = (Long) datosOfertas[1];
            cantidadVacantes = (Long) datosVacantes[1];

            // suma para los totales
            totalPostulaciones = totalPostulaciones + cantidadPostulaciones;
            totalOfertas = totalOfertas + cantidadOfertas;
            totalVacantes = totalVacantes + cantidadVacantes;

            // datos por tipo de oferta
            datosIntegrados.add( (String) datosPostulaciones[0] );                      // Nombre del tipo de oferta
            datosIntegrados.add( String.valueOf(cantidadPostulaciones ));              // cantidad de postulaciones
            datosIntegrados.add( String.valueOf(cantidadOfertas ));                    // cantidad de ofertas
            datosIntegrados.add( String.valueOf(cantidadVacantes ));                   // cantidad de vacantes

            // razon "postulaciones/ofertas"    (en caso que sea distinto de cero - evita division por cero)
            if (cantidadOfertas != 0L) {
                datosIntegrados.add( String.valueOf( cantidadPostulaciones / cantidadOfertas ));
            }
            else {
                datosIntegrados.add("0");
            }

            // razon "postulaciones/vacantes"    (en caso que sea distinto de cero - evita division por cero)
            if (cantidadVacantes != 0L) {
                datosIntegrados.add( String.valueOf( cantidadPostulaciones / cantidadVacantes ));
            }
            else {
                datosIntegrados.add("0");
            }

            indiceCantidadPostulacionesOfertasEmpleoPorTipoOferta.add(datosIntegrados);          // datos integrados por Tipo Oferta
        }

        // inclusion de los datos totales
        ArrayList<String> datosIntegrados = new ArrayList<String>();
        datosIntegrados.add("Total");                                               // Total
        datosIntegrados.add( String.valueOf(totalPostulaciones) );                  // total de postulaciones
        datosIntegrados.add( String.valueOf(totalOfertas) );                        // total de ofertas
        datosIntegrados.add( String.valueOf(totalVacantes) );                       // total de vacantes

        // razon "postulaciones/ofertas"    (en caso que sea distinto de cero - evita division por cero)
        if (cantidadOfertas != 0L) {
            datosIntegrados.add( String.valueOf( totalPostulaciones / totalOfertas ));
        }
        else {
            datosIntegrados.add("0");
        }

        // razon "postulaciones/vacantes"    (en caso que sea distinto de cero - evita division por cero)
        if (cantidadVacantes != 0L) {
            datosIntegrados.add( String.valueOf( totalPostulaciones / totalVacantes ));
        }
        else {
            datosIntegrados.add("0");
        }

        indiceCantidadPostulacionesOfertasEmpleoPorTipoOferta.add(datosIntegrados);          // datos integrados por tipoOferta

        return indiceCantidadPostulacionesOfertasEmpleoPorTipoOferta;
    }






    // --------------------------------- CURRICULOS ---------------------------------



    /**
     * Obtiene un listado, de manera paginada, de la cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) según el {@link crm.entities.TipoEstadoEstudio}, clasificados por
     * {@link crm.entities.Carrera}
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param tamanoPagina Cantidad de elementos a mostrar por página
     * @param numeroPagina Numero de la pagina de la paginación, que se desea mostrar
     *
     * @return Coleccion {@Link java.util.Page} contenedora de las {@link crm.entities.Carrera}; para cada una de ellas
     *          se tiene una coleccion {@Link java.util.List}, contenedora del nombre de la carrera y la cantidad de curriculos
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public Page<List<String>> indiceCurriculosPorCarrera (String codInstitucion,
                                                          Integer tamanoPagina,
                                                          Integer numeroPagina) {

        PageRequest pageRequest = new PageRequest(numeroPagina, tamanoPagina);

        return antecedenteEducacionalRepository.indiceCurriculosPorCarrera( Short.parseShort(codInstitucion), pageRequest);
    }




    /**
     * Obtiene un ArrayList de String con la cantidad de curriculos ( curriculo es equivalente a {@link crm.entities.Usuario} ) y
     * su porcentaje con respecto al total, que han sido modificado dentro de los 3, 6, 12, 18, 24, 36 , y mayor a 36 meses,
     * según la fecha de modificación del curriculo y la {@link crm.entities.Carrera} e {@link crm.entities.Institucion} del
     * {@link crm.entities.AntecedenteEducacional} asociado al {@link crm.entities.Usuario}
     *
     * @return  Array de String contenedor de la cantidad de curriculos que han sido modificado dentro
     *          de los 3, 6, 12, 18, 24, 36, y mayor a 36 meses
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<String> indiceCurriculosPorFechaModificacion (String codInstitucion, String codCarrera) {

        Long totalCurriculos = 0L;
        ArrayList<String> indiceCurriculosPorFechaModificacion = new ArrayList<String>();

        // caso en que se seleccionó todas las carreras
        if (codCarrera.equals("0")) {
            codCarrera = "%";
        }

        // Creacion de rango
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.MONTH, -3);
        Date cota3 = cal.getTime();
        cal.add(Calendar.MONTH, 3);

        cal.add(Calendar.MONTH, -6);
        Date cota6 = cal.getTime();
        cal.add(Calendar.MONTH, 6);

        cal.add(Calendar.MONTH, -12);
        Date cota12 = cal.getTime();
        cal.add(Calendar.MONTH, 12);

        cal.add(Calendar.MONTH, -18);
        Date cota18 = cal.getTime();
        cal.add(Calendar.MONTH, 18);

        cal.add(Calendar.MONTH, -24);
        Date cota24 = cal.getTime();
        cal.add(Calendar.MONTH, 24);

        cal.add(Calendar.MONTH, -36);
        Date cota36 = cal.getTime();
        cal.add(Calendar.MONTH, 36);

        String resultado = usuarioRepository.indiceCurriculosPorFechaModificacion(Short.parseShort(codInstitucion), codCarrera, cota3, cota6, cota12, cota18, cota24, cota36);

        String[] cantidadCurriculosPorFechaModificacion = resultado.split(",");

        totalCurriculos = Long.parseLong( cantidadCurriculosPorFechaModificacion[cantidadCurriculosPorFechaModificacion.length - 1] );

        for (int indice = 0; indice < cantidadCurriculosPorFechaModificacion.length  ; indice++) {
            Long datoFechaModificacion = Long.parseLong(cantidadCurriculosPorFechaModificacion[indice]);                      // cantidad por fecha modificacion
            Float porcentajePorFechaModificacion = (100.0f * datoFechaModificacion ) / totalCurriculos;

            DecimalFormat df = new DecimalFormat("#.##");                                                                       // formato conversion a porcentaje

            indiceCurriculosPorFechaModificacion.add(cantidadCurriculosPorFechaModificacion[indice]);                           // cantidad por fecha modificacion
            indiceCurriculosPorFechaModificacion.add(String.valueOf(df.format(porcentajePorFechaModificacion) + "%"));           // porcentaje por fecha modificacion
        }

        return indiceCurriculosPorFechaModificacion;
    }




    /**
     * Obtiene un ArrayList de String con la cantidad de curriculos ( curriculo es equivalente a {@link crm.entities.Usuario} ),y
     * su porcentaje con respecto al total; segmentada por el sexo del {@link crm.entities.Usuario}, según la {@link crm.entities.Carrera} e
     * {@link crm.entities.Institucion} del {@link crm.entities.AntecedenteEducacional} asociado al
     * {@link crm.entities.Usuario}
     *
     * @return  Array de String contenedor de la cantidad de curriculos por sexo
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<String> indiceCurriculosPorSexo (String codInstitucion, String codCarrera) {

        Long totalCurriculos = 0L;
        ArrayList<String> indiceCurriculosPorFechaModificacion = new ArrayList<String>();

        // caso en que se seleccionó todas las carreras
        if (codCarrera.equals("0")) {
            codCarrera = "%";
        }

        String resultado = usuarioRepository.indiceCurriculosPorSexo(codInstitucion, codCarrera);

        String[] cantidadCurriculosPorSexo = resultado.split(",");

        totalCurriculos = Long.parseLong( cantidadCurriculosPorSexo[cantidadCurriculosPorSexo.length - 1] );

        for (int indice = 0; indice < cantidadCurriculosPorSexo.length  ; indice++) {
            Long datoPorSexo = Long.parseLong(cantidadCurriculosPorSexo[indice]);                                   // cantidad por fecha modificacion
            Float porcentajePorSexo= (100.0f * datoPorSexo ) / totalCurriculos;

            DecimalFormat df = new DecimalFormat("#.##");                                                           // formato conversion a porcentaje

            indiceCurriculosPorFechaModificacion.add(cantidadCurriculosPorSexo[indice]);                           // cantidad por fecha modificacion
            indiceCurriculosPorFechaModificacion.add(String.valueOf(df.format(porcentajePorSexo) + "%"));           // porcentaje por fecha modificacion
        }

        return indiceCurriculosPorFechaModificacion;
    }




    /**
     * Obtiene un ArrayList de String con la cantidad de curriculos ( curriculo es equivalente a {@link crm.entities.Usuario} )
     * segmentado en rangos de edad. según  la {@link crm.entities.Carrera} e {@link crm.entities.Institucion} del
     * {@link crm.entities.AntecedenteEducacional} asociado al {@link crm.entities.Usuario}
     *
     * @return  Array de String contenedor de la cantidad de curriculos según el rango de edad
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<String>  indiceCurriculosPorEdad (String codInstitucion, String codCarrera) {

        Long totalCurriculos = 0L;
        ArrayList<String> indiceCurriculosPorEdad = new ArrayList<String>();

        // caso en que se seleccionó todas las carreras
        if (codCarrera.equals("0")) {
            codCarrera = "%";
        }

        // Creacion de rango
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.YEAR, -15);
        Date cota15 = cal.getTime();
        cal.add(Calendar.YEAR, 15);

        cal.add(Calendar.YEAR, -20);
        Date cota20 = cal.getTime();
        cal.add(Calendar.YEAR, 20);

        cal.add(Calendar.YEAR, -25);
        Date cota25 = cal.getTime();
        cal.add(Calendar.YEAR, 25);

        cal.add(Calendar.YEAR, -30);
        Date cota30 = cal.getTime();
        cal.add(Calendar.YEAR, 30);

        cal.add(Calendar.YEAR, -35);
        Date cota35 = cal.getTime();
        cal.add(Calendar.YEAR, 35);

        cal.add(Calendar.YEAR, -40);
        Date cota40 = cal.getTime();
        cal.add(Calendar.YEAR, 40);

        cal.add(Calendar.YEAR, -45);
        Date cota45 = cal.getTime();
        cal.add(Calendar.YEAR, 45);

        cal.add(Calendar.YEAR, -50);
        Date cota50 = cal.getTime();
        cal.add(Calendar.YEAR, 50);

        cal.add(Calendar.YEAR, -55);
        Date cota55 = cal.getTime();
        cal.add(Calendar.YEAR, 55);

        cal.add(Calendar.YEAR, -60);
        Date cota60 = cal.getTime();
        cal.add(Calendar.YEAR, 60);

        cal.add(Calendar.YEAR, -65);
        Date cota65 = cal.getTime();
        cal.add(Calendar.YEAR, 65);

        String resultado = usuarioRepository.indiceCurriculosPorEdad(Short.parseShort(codInstitucion), codCarrera, cota15, cota20, cota25, cota30, cota35, cota40, cota45, cota50, cota55, cota60, cota65);

        String[] cantidadCurriculosPorEdad = resultado.split(",");

        totalCurriculos = Long.parseLong( cantidadCurriculosPorEdad[cantidadCurriculosPorEdad.length - 1] );

        for (int indice = 0; indice < cantidadCurriculosPorEdad.length  ; indice++) {
            Long datoPorSexo = Long.parseLong(cantidadCurriculosPorEdad[indice]);                                   // cantidad por edad
            Float porcentajePorEdad= (100.0f * datoPorSexo ) / totalCurriculos;

            DecimalFormat df = new DecimalFormat("#.##");                                                           // formato conversion a porcentaje

            indiceCurriculosPorEdad.add(cantidadCurriculosPorEdad[indice]);                                          // cantidad por edad
            indiceCurriculosPorEdad.add(String.valueOf(df.format(porcentajePorEdad) + "%"));                        // porcentaje por edad
        }

        return indiceCurriculosPorEdad;
    }





    /**
     * Obtiene un listado de la cantidad de curriculos ( curriculo es equivalente a {@link crm.entities.Usuario} )
     * segmentado por {@link crm.entities.Region} del {@link crm.entities.Usuario}
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de las {@link crm.entities.Region}; para cada una de ellas
     *          se tiene una coleccion {@Link java.util.ArrayList}, contenedora del nombre de la Region y la cantidad de curriculos
     *          asociados
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  indiceCantidadCurriculosPorRegion (String codInstitucion, String codCarrera) {

        Long totalCurriculos = 0L;
        ArrayList<ArrayList<String>> indiceCantidadCurriculosPorRegion = new ArrayList<ArrayList<String>>();

        // caso en que se seleccionó todas las carreras
        if (codCarrera.equals("0")) {
            codCarrera = "%";
        }

        ArrayList<ArrayList<String>> resultadoCantidadCurriculosPorRegion = antecedenteEducacionalRepository.indiceCantidadCurriculosPorRegion(Short.parseShort(codInstitucion), codCarrera);

        Iterator iterTotales = resultadoCantidadCurriculosPorRegion.iterator();
        Iterator iterResultadoCantidadCurriculosPorRegion = resultadoCantidadCurriculosPorRegion.iterator();

        // obtencion del total de curriculos
        while (iterTotales.hasNext()) {
            Object[] datosPorRegion = (Object[]) iterTotales.next();

            totalCurriculos = totalCurriculos + (Long) datosPorRegion[1];
        }

        // obtencion del porcentaje
        while (iterResultadoCantidadCurriculosPorRegion.hasNext()) {
            Object[] datosPorRegion = (Object[]) iterResultadoCantidadCurriculosPorRegion.next();

            ArrayList<String> datosIntegrados = new ArrayList<String>();

            for (int indice = 1; indice < datosPorRegion.length  ; indice++) {
                Long cantidadPorRegion = (Long) datosPorRegion[indice] ;                                          // cantidad por region
                Float porcentajePorRegion = (100.0f * cantidadPorRegion ) / totalCurriculos;

                DecimalFormat df = new DecimalFormat("#.##");                                                // conversion a porcentaje

                datosIntegrados.add( (String) datosPorRegion[0] );                                       // Nombre de la region
                datosIntegrados.add( String.valueOf( (Long) datosPorRegion[1] ));                        // Cantidad por region
                datosIntegrados.add( String.valueOf( df.format(porcentajePorRegion) + "%") );            // Porcentaje por region

                indiceCantidadCurriculosPorRegion.add(datosIntegrados);                          // datos integrados por region
            }
        }

        // agrega datos totales
        ArrayList<String> datosIntegrados = new ArrayList<String>();
        datosIntegrados.add( "Total" );
        datosIntegrados.add( String.valueOf(totalCurriculos));
        datosIntegrados.add( "100%" );
        indiceCantidadCurriculosPorRegion.add(datosIntegrados);

        return indiceCantidadCurriculosPorRegion;
    }





    /**
     * Obtiene un listado de la cantidad de curriculos ( curriculo es equivalente a {@link crm.entities.Usuario} )
     * segmentado por el porcentaje de la Completitud de Contacto de los datos del {@link crm.entities.Usuario}
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     *
     * @return  Array de String contenedora de la cantidad de curriculos para cada segmento de porcentaje del nivel de trayectoria
     *          completa del Usuario
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<String>  indiceCurriculosPorTrayectoriaCompleta (String codInstitucion, String codCarrera) {

        Long totalCurriculos = 0L;
        ArrayList<String> indiceCurriculosPorTrayectoria = new ArrayList<String>();

        // caso en que se seleccionó todas las carreras
        if (codCarrera.equals("0")) {
            codCarrera = "%";
        }

        String resultado = usuarioRepository.indiceCurriculosPorTrayectoriaCompleta(Short.parseShort(codInstitucion), codCarrera);

        String[] cantidadCurriculosPorTrayectoriaCompleta = resultado.split(",");

        totalCurriculos = Long.parseLong( cantidadCurriculosPorTrayectoriaCompleta[cantidadCurriculosPorTrayectoriaCompleta.length - 1] );

        for (int indice = 0; indice < cantidadCurriculosPorTrayectoriaCompleta.length  ; indice++) {
            Long datoPorSexo = Long.parseLong(cantidadCurriculosPorTrayectoriaCompleta[indice]);                                   // cantidad por trayectoria
            Float porcentajePorTrayectoria= (100.0f * datoPorSexo ) / totalCurriculos;

            DecimalFormat df = new DecimalFormat("#.##");                                                                           // formato conversion a porcentaje

            indiceCurriculosPorTrayectoria.add(cantidadCurriculosPorTrayectoriaCompleta[indice]);                                   // cantidad por trayectoria
            indiceCurriculosPorTrayectoria.add(String.valueOf(df.format(porcentajePorTrayectoria) + "%"));                          // porcentaje por trayectoria
        }

        return indiceCurriculosPorTrayectoria;
    }





    /**
     * Obtiene un listado de la cantidad de curriculos ( curriculo es equivalente a {@link crm.entities.Usuario} )
     * segmentado por el porcentaje de la Completitud de Contacto de los datos del {@link crm.entities.Usuario}
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     *
     * @return  Array de String contenedora de la cantidad de curriculos para cada segmento de porcentaje del nivel de
     *          completitud del contacto del Usuario
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<String> indiceCurriculosPorCompletitudContacto (String codInstitucion, String codCarrera) {

        Long totalCurriculos = 0L;
        ArrayList<String> indiceCurriculosPorCompletitud = new ArrayList<String>();

        // caso en que se seleccionó todas las carreras
        if (codCarrera.equals("0")) {
            codCarrera = "%";
        }

        String resultado = usuarioRepository.indiceCurriculosPorCompletitudContacto(Short.parseShort(codInstitucion), codCarrera);

        String[] cantidadCurriculosPorCompletitudContacto = resultado.split(",");

        totalCurriculos = Long.parseLong( cantidadCurriculosPorCompletitudContacto[cantidadCurriculosPorCompletitudContacto.length - 1] );

        for (int indice = 0; indice < cantidadCurriculosPorCompletitudContacto.length  ; indice++) {
            Long datoPorSexo = Long.parseLong(cantidadCurriculosPorCompletitudContacto[indice]);                                   // cantidad por completitud
            Float porcentajePorTrayectoria= (100.0f * datoPorSexo ) / totalCurriculos;

            DecimalFormat df = new DecimalFormat("#.##");                                                                           // formato conversion a porcentaje

            indiceCurriculosPorCompletitud.add(cantidadCurriculosPorCompletitudContacto[indice]);                                   // cantidad por completitud
            indiceCurriculosPorCompletitud.add(String.valueOf(df.format(porcentajePorTrayectoria) + "%"));                          // porcentaje por completitud
        }

        return indiceCurriculosPorCompletitud;
    }






    // --------------------------------- PERSONAS ---------------------------------



    /**
     * Retorna una lista con la cantidad de exalumnos por cada {@link crm.entities.TipoSituacionLaboral} que hay en la base
     * de datos.
     * Debido a que esta informacion se obtiene a partir de la tabla public.info_profesional_exalumno cabe la posibilidad de
     * que en esta tabla no existan registros de algun {@link crm.entities.TipoSituacionLaboral} por lo que en estos casos
     * se ingresa a la lista el valor 0 correspondiente a dicho tipo de situacion laboral.
     *
     * @param situacionesLaborales Lista de todos los tipos de situacion laboral {@link crm.entities.TipoSituacionLaboral}
     *                             que hay en la base de datos
     *
     * @return Coleccion {@Link java.util.List} de la cantidad de exalumnos por cada {@link crm.entities.TipoSituacionLaboral}
     * que hay en la base de datos.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     *
     */
    public ArrayList<Integer> indiceCantidadExalumnosPorSituacionContractual(List<TipoSituacionLaboral> situacionesLaborales) {
        ArrayList<Integer> cantidadExalumnosPorSituacionLaboralEnInfoProfesionalExalumno = infoProfesionalExalumnoRepository.cantidadExalumnosPorSituacionContractual();
        ArrayList<Short> codigosSituacionLaboralInfoProfesionalExalumno = infoProfesionalExalumnoRepository.codSituacionLaboral();
        ArrayList<Integer> cantidadExalumnosPorSituacionContractual = new ArrayList<>();
        Integer j=0;
        for(Integer i=0; i<situacionesLaborales.size();i++){
            if(j==codigosSituacionLaboralInfoProfesionalExalumno.size()){
                cantidadExalumnosPorSituacionContractual.add(0);
            }
            else if(situacionesLaborales.get(i).getCodigo() == codigosSituacionLaboralInfoProfesionalExalumno.get(j)){
                cantidadExalumnosPorSituacionContractual.add(cantidadExalumnosPorSituacionLaboralEnInfoProfesionalExalumno.get(j));
                j++;
            }else if(situacionesLaborales.get(i).getCodigo() != codigosSituacionLaboralInfoProfesionalExalumno.get(j)){
                cantidadExalumnosPorSituacionContractual.add(0);
            }
        }
        return cantidadExalumnosPorSituacionContractual;
    }





    /**
     * Obtiene la cantidad de exalumnos que tiene una institucion pudiendo filtrar estos por
     * carrera, año de ingreso, año de egreso y año de titulacion
     *
     * @param valoresBusqueda Pais, Institucion, Carrera, año de ingreso, año de egreso y año de titulacion
     *                        de la institucion en cuestion.
     *
     * @return Cantidad {@Link java.lang.Integer} de exalumnos que tiene una institucion que cumplen con los
     * criterios que dictan los parametros otorgados
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public Integer cantidadExalumnos(Map<String,Integer> valoresBusqueda) {
        Integer idPais = valoresBusqueda.get("idPais");
        Integer codInstitucion = valoresBusqueda.get("codInstitucion");
        Integer codCarrera = valoresBusqueda.get("codCarrera");
        Integer anioIngreso = valoresBusqueda.get("anioIngreso");
        Integer anioEgreso = valoresBusqueda.get("anioEgreso");
        Integer anioTitulacion = valoresBusqueda.get("anioTitulacion");
        Integer cantidadExalumnos;
        if(valoresBusqueda.get("codCarrera") == -1) {
            if (anioIngreso == 0 && anioEgreso != 0 && anioTitulacion != 0)
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosAnioEgresoAnioTitulacion(idPais, codInstitucion, anioEgreso, anioTitulacion);
            else if (anioEgreso == 0 && anioIngreso != 0 && anioTitulacion != 0)
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosAnioIngresoAnioTitulacion(idPais, codInstitucion, anioIngreso, anioTitulacion);
            else if (anioTitulacion == 0 && anioIngreso != 0 && anioEgreso != 0)
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosAnioIngresoAnioEgreso(idPais, codInstitucion, anioIngreso, anioEgreso);
            else if (anioIngreso == 0 && anioEgreso == 0 && anioTitulacion != 0) {
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosAnioTitulacion(idPais, codInstitucion, anioTitulacion);
                Integer a = 0;
            }
            else if(anioIngreso == 0 && anioTitulacion == 0 && anioEgreso != 0)
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosAnioEgreso(idPais, codInstitucion, anioEgreso);
            else if(anioEgreso == 0 && anioTitulacion == 0 && anioIngreso != 0)
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosAnioIngreso(idPais, codInstitucion, anioIngreso);
            else
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosAnioIngresoAnioEgresoAnioTitulacion(idPais, codInstitucion, anioIngreso, anioEgreso, anioTitulacion);
        }else{
            if(anioIngreso == 0 && anioEgreso != 0 && anioTitulacion != 0)
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosCarreraAnioEgresoAnioTitulacion(idPais, codInstitucion, codCarrera, anioEgreso, anioTitulacion);
            else if(anioEgreso == 0 && anioIngreso != 0 && anioTitulacion != 0)
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosCarreraAnioIngresoAnioTitulacion(idPais, codInstitucion, codCarrera, anioIngreso, anioTitulacion);
            else if(anioTitulacion == 0 && anioIngreso != 0 && anioEgreso != 0)
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosCarreraAnioIngresoAnioEgreso(idPais, codInstitucion, codCarrera, anioIngreso, anioEgreso);
            else if(anioIngreso == 0 && anioEgreso == 0 && anioTitulacion != 0)
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosCarreraAnioTitulacion(idPais, codInstitucion, codCarrera, anioTitulacion);
            else if(anioIngreso == 0 && anioTitulacion == 0 && anioEgreso != 0)
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosCarreraAnioEgreso(idPais, codInstitucion, codCarrera, anioEgreso);
            else if(anioEgreso == 0 && anioTitulacion == 0 && anioIngreso != 0)
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosCarreraAnioIngreso(idPais, codInstitucion, codCarrera, anioIngreso);
            else
                cantidadExalumnos = antecedenteEducacionalRepository.cantidadExalumnosCarreraAnioIngresoAnioEgresoAnioTitulacion(idPais, codInstitucion, codCarrera, anioIngreso, anioEgreso, anioTitulacion);
        }
        return cantidadExalumnos;
    }

    /**
     * Obtiene la cantidad de exalumnos que tiene una institucion segun un rango de años
     *
     * @param valoresBusqueda Pais, Institucion, Carrera, año de inicio del rango, año de fin del rango.
     *                        de la institucion en cuestion.
     *
     * @return Cantidad {@Link java.lang.Integer} de exalumnos que tiene una institucion para cada año dentro del rango
     * de años ingresado
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public ArrayList<Integer> cantidadExalumnosRango(Map<String,Integer> valoresBusqueda) {
        ArrayList<Integer> cantidadExalumnos = new ArrayList<>();
        if(valoresBusqueda.get("codCarrera")==-1) {
            for (Integer anio = valoresBusqueda.get("anioInicio"); anio <= valoresBusqueda.get("anioFin"); anio++) {
                cantidadExalumnos.add(antecedenteEducacionalRepository.getCantidadExalumnosRangoAniosInstitucion(valoresBusqueda.get("idPais").shortValue(),valoresBusqueda.get("codInstitucion").shortValue(), anio));
            }
        }else{
            for (Integer anio = valoresBusqueda.get("anioInicio"); anio <= valoresBusqueda.get("anioFin"); anio++) {
                cantidadExalumnos.add(antecedenteEducacionalRepository.getCantidadExalumnosRangoAniosInstitucionCarrera(valoresBusqueda.get("idPais").shortValue(),valoresBusqueda.get("codInstitucion").shortValue(), valoresBusqueda.get("codCarrera").longValue(), anio));
            }
        }
        return cantidadExalumnos;
    }





    /**
     * Obtiene un listado, de manera paginada, de la cantidad de {@link crm.entities.Usuario} segmentado por los años de experiencia de su
     * {@link crm.entities.InfoProfesionalExalumno}, según la {@link crm.entities.Carrera} y la {@link crm.entities.Institucion}
     * especificados como parametros para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     * @param tamanioPagina Cantidad de elementos a mostrar por página
     * @param numeroPagina Numero de la pagina de la paginación, que se desea mostrar
     *
     * @return Coleccion {@Link java.util.Page} contenedora de las {@link crm.entities.Carrera}; para cada una de ellas
     *          se tiene una coleccion {@Link java.util.List}, contenedora del nombre de la Carrera y la cantidad de
     *          personas segmentadas por los años de Experiencia
     *          asociados
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    public Page<ArrayList<String>> indiceCantidadPersonasPorAniosExperiencia (String codInstitucion,
                                                                              String codCarrera,
                                                                              Integer tamanioPagina,
                                                                              Integer numeroPagina) {

        // caso en que se seleccionó todas las carreras
        if (codCarrera.equals("0")) {
            codCarrera = "%";
        }

        PageRequest pageRequest = new PageRequest(numeroPagina, tamanioPagina);

        // obtiene las cantidades del indice
        Page<ArrayList<String>> resultadoCantidadPersonasPorAniosExperiencia = infoProfesionalExalumnoRepository.indiceCantidadPersonasPorAniosExperiencia(Short.parseShort(codInstitucion), codCarrera, pageRequest);

        // conversion a porcentaje
        Iterator iterResultadoCantidadPersonas = resultadoCantidadPersonasPorAniosExperiencia.iterator();

        while (iterResultadoCantidadPersonas.hasNext()) {
            Object[] datosPorAnio = (Object[]) iterResultadoCantidadPersonas.next();

            Long totalPorCarrera = (Long) datosPorAnio[datosPorAnio.length - 1];

            for (int indice = 1; indice < datosPorAnio.length -1 ; indice++) {
                Long cantidadPorAnio = (Long) datosPorAnio[indice] ;                                          // cantidad por año
                Float porcentajePorAnio = (100.0f * cantidadPorAnio ) / totalPorCarrera;

                DecimalFormat df = new DecimalFormat("#.##");                                                // conversion a porcentaje
                datosPorAnio[indice] = String.valueOf( df.format(porcentajePorAnio) + "%");
            }
        }

        return resultadoCantidadPersonasPorAniosExperiencia;
    }





    /**
     * Obtiene un listado de la cantidad de personas ( {@link crm.entities.Usuario} ) con trabajo,
     * registrado en sus {@link crm.entities.ActividadExalumno}, segmentado por el {@link crm.entities.TipoSector} de la
     * {@link crm.entities.Empresa} en la cual trabaja, según la {@link crm.entities.Carrera} y la {@link crm.entities.Institucion}
     * especificados como parametros para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de los {@link crm.entities.TipoSector}; para cada una de ellas
     *          coleccion {@Link java.util.ArrayList}, contenedora del nombre del  {@link crm.entities.TipoSector} y la cantidad
     *          de personas asociados.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    public ArrayList<ArrayList<String>>  indiceCantidadPersonasConTrabajoPorTipoRubro (String codInstitucion,
                                                                                       String codCarrera) {
        Long totalPersonas = 0L;
        ArrayList<ArrayList<String>> indiceCantidadPersonasConTrabajoPorTipoRubro = new ArrayList<ArrayList<String>>();
        TipoSector tipoSector = new TipoSector();

        // caso en que se seleccionó todas las carreras
        if (codCarrera.equals("0")) {
            codCarrera = "%";
        }

        ArrayList<ArrayList<String>> resultadoCantidadPersonasConTrabajoPorTipoRubro = actividadExalumnoRepository.indiceCantidadPersonasConTrabajoPorTipoRubro(Short.parseShort(codInstitucion), codCarrera);

        Iterator iterTotales = resultadoCantidadPersonasConTrabajoPorTipoRubro.iterator();
        Iterator iterResultadoCantidadPersonasConTrabajoPorTipoRubro = resultadoCantidadPersonasConTrabajoPorTipoRubro.iterator();

        // obtencion del total de personas
        while (iterTotales.hasNext()) {
            Object[] datosPorTipoRubro = (Object[]) iterTotales.next();

            totalPersonas = totalPersonas + ((BigDecimal)datosPorTipoRubro[1]).longValue();
        }

        // obtencion del porcentaje
        while (iterResultadoCantidadPersonasConTrabajoPorTipoRubro.hasNext()) {
            Object[] datosPorTipoRubro = (Object[]) iterResultadoCantidadPersonasConTrabajoPorTipoRubro.next();

            ArrayList<String> datosIntegrados = new ArrayList<String>();

            for (int indice = 1; indice < datosPorTipoRubro.length  ; indice++) {
                Short idRubro = (Short) datosPorTipoRubro[0];
                Long cantidadPorRubro = (((BigDecimal)datosPorTipoRubro[indice]).longValue());                   // cantidad por rubro
                Float porcentajePorTipoRubro = (100.0f * cantidadPorRubro ) / totalPersonas;

                DecimalFormat df = new DecimalFormat("#.##");                                                // conversion a porcentaje
                String nombreRubro = tipoSectorRepository.buscarNombreTipoSector(idRubro);                 // obtencion del nombre del rubro

                datosIntegrados.add( nombreRubro );                                                         // Nombre del tipo de rubro
                datosIntegrados.add( String.valueOf( cantidadPorRubro ));                                   // Cantidad por tipo de rubro
                datosIntegrados.add( String.valueOf( df.format(porcentajePorTipoRubro) + "%") );            // Porcentaje por tipo de rubro

                indiceCantidadPersonasConTrabajoPorTipoRubro.add(datosIntegrados);                          // datos integrados por Tipo rubro
            }
        }

        // agrega datos totales
        ArrayList<String> datosIntegrados = new ArrayList<String>();
        datosIntegrados.add( "Total" );
        datosIntegrados.add( String.valueOf(totalPersonas));
        datosIntegrados.add( "100%" );
        indiceCantidadPersonasConTrabajoPorTipoRubro.add(datosIntegrados);

        return indiceCantidadPersonasConTrabajoPorTipoRubro;
    }





    /**
     * Obtiene un listado de la cantidad de personas ( {@link crm.entities.Usuario} ) con trabajo,
     * registrado en sus {@link crm.entities.ActividadExalumno}, segmentado por el {@link crm.entities.TipoCargo},
     * según la {@link crm.entities.Carrera} y la {@link crm.entities.Institucion}
     * especificados como parametros para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     *
     * @return Coleccion {@Link java.util.List} contenedora de los {@link crm.entities.TipoCargo}; para cada una de ellos
     *          coleccion {@Link java.util.List}, contenedora del nombre del  {@link crm.entities.TipoCargo} y la cantidad
     *          de personas asociados.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    public ArrayList<ArrayList<String>>  indicePersonasConTrabajoPorTipoCargo (String codInstitucion,
                                                                               String codCarrera) {
        Long totalPersonas = 0L;
        ArrayList<ArrayList<String>> indiceCantidadPersonasConTrabajoPorTipoCargo = new ArrayList<ArrayList<String>>();

        // caso en que se seleccionó todas las carreras
        if (codCarrera.equals("0")) {
            codCarrera = "%";
        }

        ArrayList<ArrayList<String>> resultadoCantidadPersonasConTrabajoPorTipoCargo = actividadExalumnoRepository.indicePersonasConTrabajoPorTipoCargo(Short.parseShort(codInstitucion), codCarrera);

        Iterator iterTotales = resultadoCantidadPersonasConTrabajoPorTipoCargo.iterator();
        Iterator iterResultadoCantidadPersonasConTrabajoPorTipoCargo = resultadoCantidadPersonasConTrabajoPorTipoCargo.iterator();

        // obtencion del total de personas
        while (iterTotales.hasNext()) {
            Object[] datosPorTipoCargo = (Object[]) iterTotales.next();

            totalPersonas = totalPersonas + (Long) datosPorTipoCargo[1];
        }

        // obtencion del porcentaje
        while (iterResultadoCantidadPersonasConTrabajoPorTipoCargo.hasNext()) {
            Object[] datosPorTipoCargo = (Object[]) iterResultadoCantidadPersonasConTrabajoPorTipoCargo.next();

            ArrayList<String> datosIntegrados = new ArrayList<String>();

            for (int indice = 1; indice < datosPorTipoCargo.length  ; indice++) {
                Long datoPorAnio = (Long) datosPorTipoCargo[indice] ;                                          // cantidad por cargo
                Float porcentajePorTipoCargo = (100.0f * datoPorAnio ) / totalPersonas;

                DecimalFormat df = new DecimalFormat("#.##");                                                // conversion a porcentaje

                datosIntegrados.add( (String) datosPorTipoCargo[0] );                                       // Nombre del tipo de cargo
                datosIntegrados.add( String.valueOf( (Long) datosPorTipoCargo[1] ));                        // Cantidad por tipo de cargo
                datosIntegrados.add( String.valueOf( df.format(porcentajePorTipoCargo) + "%") );            // Porcentaje por tipo de cargo

                indiceCantidadPersonasConTrabajoPorTipoCargo.add(datosIntegrados);                          // datos integrados por Tipo cargo
            }
        }

        // agrega datos totales
        ArrayList<String> datosIntegrados = new ArrayList<String>();
        datosIntegrados.add( "Total" );
        datosIntegrados.add( String.valueOf(totalPersonas));
        datosIntegrados.add( "100%" );
        indiceCantidadPersonasConTrabajoPorTipoCargo.add(datosIntegrados);

        return indiceCantidadPersonasConTrabajoPorTipoCargo;
    }




    /**
     * Obtiene un listado de la cantidad de personas ( {@link crm.entities.Usuario} ) con trabajo,
     * registrado en sus {@link crm.entities.ActividadExalumno}, segmentado por el tramo de remuneración registrado,
     * según la {@link crm.entities.Carrera} y la {@link crm.entities.Institucion} especificados como parametros para la
     * busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     *
     * @return  Array de String contenedora de la cantidad de personas para cada segmento de tramo de remuneración del Usuario
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<String>  indicePersonasConTrabajoPorTramoIngreso (String codInstitucion, String codCarrera) {

        Long totalCurriculos = 0L;
        ArrayList<String> indicePersonasConTrabajoPorTramoIngreso = new ArrayList<String>();

        // caso en que se seleccionó todas las carreras
        if (codCarrera.equals("0")) {
            codCarrera = "%";
        }

        String resultado = actividadExalumnoRepository.indicePersonasConTrabajoPorTramoIngreso (Short.parseShort(codInstitucion), codCarrera);

        String[] cantidadPersonasConTrabajoPorTramoIngreso = resultado.split(",");

        totalCurriculos = Long.parseLong( cantidadPersonasConTrabajoPorTramoIngreso[cantidadPersonasConTrabajoPorTramoIngreso.length - 1] );

        for (int indice = 0; indice < cantidadPersonasConTrabajoPorTramoIngreso.length  ; indice++) {
            Long datoPorSexo = Long.parseLong(cantidadPersonasConTrabajoPorTramoIngreso[indice]);                                   // cantidad por tramo ingreso
            Float porcentajePorTrayectoria= (100.0f * datoPorSexo ) / totalCurriculos;

            DecimalFormat df = new DecimalFormat("#.##");                                                                           // formato conversion a porcentaje

            indicePersonasConTrabajoPorTramoIngreso.add(cantidadPersonasConTrabajoPorTramoIngreso[indice]);                                   // cantidad por tramo ingreso
            indicePersonasConTrabajoPorTramoIngreso.add(String.valueOf(df.format(porcentajePorTrayectoria) + "%"));                          // porcentaje por tramo ingreso
        }

        return indicePersonasConTrabajoPorTramoIngreso;
    }




    /**
     * Obtiene un listado de la cantidad de personas ( {@link crm.entities.Usuario} ) segmentado por el
     * nivel de conocimiento del {@link crm.entities.Idioma} registrado en {@link crm.entities.ManejoIdioma},
     * según la {@link crm.entities.Carrera}, la {@link crm.entities.Institucion} y el {@link crm.entities.Idioma}
     * especificados como parametros para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     * @param codIdioma Identificador del {@link crm.entities.Idioma} sobre la cual mostrar el indice.
     *
     * @return  Array de String contenedora del nombre del {@link crm.entities.Idioma}, y la cantidades de
     *          {@link crm.entities.Usuario} para cada nivel de Conversacion, Escritura y Traduccion
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public String[]  indicePersonasPorNivelIdioma (String codInstitucion, String codCarrera, String codIdioma) {

        // caso en que se seleccionó todas las carreras
        if (codCarrera.equals("0")) {
            codCarrera = "%";
        }

        String resultado = antecedenteEducacionalRepository.indicePersonasPorNivelIdioma(Short.parseShort(codInstitucion), codCarrera, Short.parseShort(codIdioma));

        String[] cantidadPersonasPorNivelIdioma = resultado.split(",");

        return cantidadPersonasPorNivelIdioma;
    }







    // --------------------------------- EMPRESAS ---------------------------------


    /**
     * Obtiene un listado con el ranking de las {@link crm.entities.Empresa} con mayor cantidad de personas ( {@link crm.entities.Usuario} )
     * según la {@link crm.entities.Carrera} y la {@link crm.entities.Institucion}
     * especificados como parametros para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     *
     * @return Coleccion {@Link java.util.List} contenedora de las {@link crm.entities.Empresa}; para cada una de ellos
     *          coleccion {@Link java.util.List}, contenedora de la cantidad de {@link crm.entities.Usuario}
     *          contratados, cantidad de altos ejecutivos, cantidad de hombres, cantidad de mujeres
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public ArrayList<ArrayList<String>>  indiceRankingEmpresasMasPersonasContratadas (String codInstitucion, String codCarrera, String anio, String cantidadMostrar) {

        ArrayList<ArrayList<String>> indiceRankingEmpresasMasPersonasContratadas = new ArrayList<ArrayList<String>>();

        // caso en que se seleccionó todas las carreras
        if (codCarrera.equals("0")) {
            codCarrera = "%";
        }

        // Pagerequest permite limitar la cantidad de elementos  y el orden ascendente/descendente de los resultados
        // COUNT(u) indica sobre que hacer el ORDER BY
        // (hql no lo permite por si mismo)
        PageRequest pageRequest = new PageRequest(0, Integer.parseInt(cantidadMostrar), Sort.Direction.DESC, "COUNT (u)");

        ArrayList<ArrayList<String>> resultadoRankingEmpresasMasPersonasContratadas = actividadExalumnoRepository.indiceRankingEmpresasMasPersonasContratadas(Short.parseShort(codInstitucion), codCarrera, Integer.parseInt(anio), pageRequest);

        Iterator iterResultadoRankingEmpresasMasPersonasContratadas = resultadoRankingEmpresasMasPersonasContratadas.iterator();

        // obtencion del porcentaje
        while (iterResultadoRankingEmpresasMasPersonasContratadas.hasNext()) {
            Object[] datosPorEmpresa = (Object[]) iterResultadoRankingEmpresasMasPersonasContratadas.next();

            ArrayList<String> datosIntegrados = new ArrayList<String>();

            Long cantidadHombres = (Long) datosPorEmpresa[3] ;                                          // cantidad hombres por empresa
            Long cantidadMujeres = (Long) datosPorEmpresa[4] ;                                          // cantidad mujeres por empresa

            Float porcentajeHombres = (100.0f * cantidadHombres ) / (cantidadHombres + cantidadMujeres);
            Float porcentajeMujeres = (100.0f * cantidadMujeres ) / (cantidadHombres + cantidadMujeres);

            DecimalFormat df = new DecimalFormat("#.##");                                                // conversion a porcentaje

            datosIntegrados.add( (String) datosPorEmpresa[0] );                                         // Nombre empresa
            datosIntegrados.add( String.valueOf( (Long) datosPorEmpresa[1] ));                          // Cantidad contratados
            datosIntegrados.add( String.valueOf( (Long) datosPorEmpresa[2] ));                          // Cantidad altos ejecutivos contratados
            datosIntegrados.add( String.valueOf( (Long) datosPorEmpresa[3] ));                          // Cantidad hombres contratados
            datosIntegrados.add( String.valueOf( (Long) datosPorEmpresa[4] ));                          // Cantidad mujeres contratados
            datosIntegrados.add( String.valueOf( df.format(porcentajeHombres) + "%") );                 // Porcentaje por tipo de oferta
            datosIntegrados.add( String.valueOf( df.format(porcentajeMujeres) + "%") );                 // Porcentaje por tipo de oferta

            indiceRankingEmpresasMasPersonasContratadas.add(datosIntegrados);                          // datos integrados por Tipo Oferta
        }

        return indiceRankingEmpresasMasPersonasContratadas;

    }

    /**
     * Obtiene un listado, de manera paginada, de la cantidad de {@link crm.entities.AporteEmpresa} por año
     * de todas los {@link crm.entities.Empresa}, realizando una busqueda según un año, tipo de oportunidad y vigencia como parametros.
     *
     * @param tipoCompromiso String del tipo de compromiso.
     * @param tipoVigencia String del tipo de vigencia.
     * @param numeroPagina numero que hay que paginar.
     * @param tamanoPagina tamaño de la pagina.
     *
     * @return  Array de String contenedor de la cantidad de {@link crm.entities.AporteEmpresa} por año.
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    public ArrayList<ArrayList<String>> indiceCantidadAportesEmpresas (String tipoCompromiso,
                                                                       String tipoVigencia,
                                                                       Integer tamanoPagina,
                                                                       Integer numeroPagina) {

        // caso en que se seleccionó todos los tipos de oportunidades
        if (tipoCompromiso.equals("0")) {
            tipoCompromiso = "%";
        }
        else if (tipoCompromiso.equals("1")) tipoCompromiso = "Donacion";
        else if (tipoCompromiso.equals("2")) tipoCompromiso = "Auspicio";
        else if (tipoCompromiso.equals("3")) tipoCompromiso = "Venta de Servicios";

        // caso en que se seleccionó todos los tipos de vigencia
        if(tipoVigencia.equals("0")) tipoVigencia="%";
        else if(tipoVigencia.equals("1")) tipoVigencia="Vigente";
        else if(tipoVigencia.equals("2")) tipoVigencia="No Vigente";

        PageRequest pageRequest = new PageRequest(numeroPagina, tamanoPagina);

        return aporteEmpresaRepository.indiceCantidadAportesEmpresas(tipoCompromiso,tipoVigencia,pageRequest);
    }

    /**
     * Obtiene un listado, de manera paginada, de la cantidad de {@link crm.entities.Usuario} por mes
     * de todas los {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}, realizando una busqueda según un año y tipo de compromiso como parametros.
     *
     * @param operador String del operador a buscar.
     * @param anio Año sobre el cual se desea obtener los datos.
     * @param numeroPagina numero que hay que paginar.
     * @param tamanoPagina tamaño de la pagina.
     *
     * @return  Array de String contenedor de la cantidad de {@link crm.entities.Usuario} por mes
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    public Page<List<String>> indiceCantidadContactoEmpresaPorOperador (String operador,
                                                                        String anio,
                                                                        Integer tamanoPagina,
                                                                        Integer numeroPagina) {

        PageRequest pageRequest = new PageRequest(numeroPagina, tamanoPagina);
        if(operador.equals("Todos"))
            return contactoHistoricoEmpresaPersonaParticipanteRepository.indiceCantidadContactoEmpresa(Integer.parseInt(anio),pageRequest);
        else if(operador.equals("Otro"))
            return contactoHistoricoEmpresaPersonaParticipanteRepository.indiceCantidadContactoEmpresa(Integer.parseInt(anio),pageRequest);
        else {
            String[] words= operador.split(" ");
            if(words.length==3)
                return contactoHistoricoEmpresaPersonaParticipanteRepository.indiceCantidadContactoEmpresaPorOperador(words[0],words[1],words[2],Integer.parseInt(anio), pageRequest);
            else
                return contactoHistoricoEmpresaPersonaParticipanteRepository.indiceCantidadContactoEmpresaPorOperador(words[0],words[2],words[3],Integer.parseInt(anio), pageRequest);
        }
    }

    /**
     * Obtiene un listado, de manera paginada, de la cantidad de {@link crm.entities.Usuario} por mes
     * de todas los {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}, realizando una busqueda según un año y tipo de compromiso como parametros.
     *
     * @param oportunidad nombre de la oportunidad sobre el cual se desea obtener los datos.
     * @param operador String del operador a buscar.
     * @param nombreOperador String que tiene el nombre del operador a buscar.
     * @param anio Año sobre el cual se desea obtener los datos.
     * @param numeroPagina numero que hay que paginar.
     * @param tamanoPagina tamaño de la pagina.
     *
     * @return  Array de String contenedor de la cantidad de {@link crm.entities.ContactoHistoricoEmpresa} por mes
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    public Page<List<String>> indiceCantidadContactoEmpresaPorOportunidad (Short oportunidad,
                                                                           String operador,
                                                                           String nombreOperador,
                                                                           String anio,
                                                                           Integer tamanoPagina,
                                                                           Integer numeroPagina) {

        //String[] nombreCompleto = nombreOperador.split(" ");
        String[] nombreCompleto= {"%", "%", "%"};
        // caso en que se seleccionó todos los tipos de vigencia
        if (!operador.equals("Todos")) {
            nombreCompleto = nombreOperador.split(" ");
        }
        /*if (oportunidad!=-1)
            oportunidad= "%";
        if (oportunidad.equals("1"))
            compromiso= "Donaciones";
        if (compromiso.equals("2"))
            oportunidad= "cdp usm";*/


        PageRequest pageRequest = new PageRequest(numeroPagina, tamanoPagina);
        if(oportunidad!=-1) {
            if (nombreCompleto.length == 3)
                return contactoHistoricoEmpresaPersonaParticipanteRepository.indiceCantidadContactoEmpresaPorOportunidad(oportunidad, nombreCompleto[0], nombreCompleto[1], nombreCompleto[2], Integer.parseInt(anio), pageRequest);
            else
                return contactoHistoricoEmpresaPersonaParticipanteRepository.indiceCantidadContactoEmpresaPorOportunidad(oportunidad, nombreCompleto[0], nombreCompleto[2], nombreCompleto[3], Integer.parseInt(anio), pageRequest);
        }
        else{
            if (nombreCompleto.length == 3) return contactoHistoricoEmpresaPersonaParticipanteRepository.indiceCantidadTotalContactoEmpresaPorOportunidad(nombreCompleto[0],nombreCompleto[1],nombreCompleto[2],Integer.parseInt(anio),pageRequest);
            else return contactoHistoricoEmpresaPersonaParticipanteRepository.indiceCantidadTotalContactoEmpresaPorOportunidad(nombreCompleto[0],nombreCompleto[2],nombreCompleto[3],Integer.parseInt(anio),pageRequest);
        }
    }

    // --------------------------------- DASHBOARD ---------------------------------

    /**
     * Indices sobre sucursales y empresas para ser usados en dashboard
     *
     * @return Lista de {@link crm.utils.KeyValueContainer}
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    public List<KeyValueContainer<Long>> getDatosEmpresaSucursalesDashboard() {
        List<KeyValueContainer<Long>> datos = new ArrayList<>();

        Long sucursales = sucursalEmpersaRepository.count();
        Long empresas = empresaRepository.count();

        datos.add(new KeyValueContainer<>("Empresas", empresas));
        datos.add(new KeyValueContainer<>("Sucursales", sucursales));

        return datos;
    }

    /**
     * Indices sobre ofertas, postulaciones y vacantes para ser usados en dashboard
     *
     * @return Lista de {@link crm.utils.KeyValueContainer}
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    public List<KeyValueContainer<Long>> getDatosOfertasPostulacionesVacantesDashboard() {
        List<KeyValueContainer<Long>> datos = new ArrayList<>();

        Long ofertas = ofertaLaboralUsmempleoRepository.count();
        Long vacantes = ofertaLaboralUsmempleoRepository.contarVacantes();
        Long postulaciones = postulacionOfertaLaboralUsmempleoRepository.count();

        datos.add(new KeyValueContainer<>("Ofertas", ofertas));
        datos.add(new KeyValueContainer<>("Vacantes", vacantes));
        datos.add(new KeyValueContainer<>("Postulaciones", postulaciones));

        return datos;
    }

    /**
     * Indices sobre vigencia de carreras para ser usados en dashboard
     *
     * @return Lista de {@link crm.utils.KeyValueContainer}
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    public List<KeyValueContainer<Long>> getDatosCarrerasVigencia() {
        List<KeyValueContainer<Long>> datos = new ArrayList<>();

        String[] vigencia = carreraRepository.contarCarrerasPorVigencia().split(",");

        datos.add(new KeyValueContainer<>("Vigentes", Long.parseLong(vigencia[0])));
        datos.add(new KeyValueContainer<>("No vigentes", Long.parseLong(vigencia[1])));
        datos.add(new KeyValueContainer<>("Por validar", Long.parseLong(vigencia[2])));

        return datos;
    }

    /**
     * Indices sobre vigencia de ofertas para ser usados en dashboard
     *
     * @return Lista de {@link crm.utils.KeyValueContainer}
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    public List<KeyValueContainer<Long>> getVigenciaOfertas() {
        List<KeyValueContainer<Long>> datos = new ArrayList<>();

        String[] vigencia = ofertaLaboralUsmempleoRepository.contarOfertasPorVigencia().split(",");

        datos.add(new KeyValueContainer<>("Vigentes", Long.parseLong(vigencia[0])));
        datos.add(new KeyValueContainer<>("No vigentes", Long.parseLong(vigencia[1])));
        datos.add(new KeyValueContainer<>("Por Validar", Long.parseLong(vigencia[2])));

        return datos;
    }

    /**
     * Indices sobre vigencia de empresas para ser usados en dashboard
     *
     * @return Lista de {@link crm.utils.KeyValueContainer}
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    public List<KeyValueContainer<Long>> getVigenciaEmpresas() {
        List<KeyValueContainer<Long>> datos = new ArrayList<>();

        String[] vigencia = empresaRepository.contarEmpresasPorVigencia().split(",");

        datos.add(new KeyValueContainer<>("Vigentes", Long.parseLong(vigencia[0])));
        datos.add(new KeyValueContainer<>("No vigentes", Long.parseLong(vigencia[1])));
        datos.add(new KeyValueContainer<>("Por validar", Long.parseLong(vigencia[2])));

        return datos;
    }

    /**
     * Indices sobre vigencia de sucursales para ser usados en dashboard
     *
     * @return Lista de {@link crm.utils.KeyValueContainer}
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    public List<KeyValueContainer<Long>> getVigenciaSucursales() {
        List<KeyValueContainer<Long>> datos = new ArrayList<>();

        String[] vigencia = sucursalEmpersaRepository.contarSucursalesPorVigencia().split(",");

        datos.add(new KeyValueContainer<>("Vigentes", Long.parseLong(vigencia[0])));
        datos.add(new KeyValueContainer<>("No vigentes", Long.parseLong(vigencia[1])));
        datos.add(new KeyValueContainer<>("Por validar", Long.parseLong(vigencia[2])));

        return datos;
    }

    /**
     * Indices sobre las encuestas respondidas por empleadores y postulantes
     *
     * @return Lista de {@link crm.utils.KeyValueContainer}
     */
    public List<KeyValueContainer<Long>> getEncuestasRespondidas() {
        List<KeyValueContainer<Long>> datos = new ArrayList<>();

        Long encuestasOfertaLaboralRespondidas = encuestaOfertaLaboralRepository.contarEncuestasRespondidas();
        Long encuestasPostulacionLaboralRespondidas = encuestaPostulacionLaboralRepository.contarEncuestasResponidas();

        datos.add(new KeyValueContainer<>("Por empleadores", encuestasOfertaLaboralRespondidas));
        datos.add(new KeyValueContainer<>("Por postulantes", encuestasPostulacionLaboralRespondidas));

        return datos;
    }

    /**
     * Indices sobre la cantidad de video curriculos publicados y por publicar
     *
     * @return Lista de {@link crm.utils.KeyValueContainer}
     */
    public List<KeyValueContainer<Long>> getVideoCurriculosPublicados() {
        List<KeyValueContainer<Long>> datos = new ArrayList<>();

        Long vcvPublicados = videoCurriculoUsuarioRepository.contarVcvPublicados();
        Long vcvPorPublicar = videoCurriculoUsuarioRepository.contarVcvPorPublicar();

        datos.add(new KeyValueContainer<>("Publicados", vcvPublicados));
        datos.add(new KeyValueContainer<>("Por publicar", vcvPorPublicar));

        return datos;
    }

    /**
     * Indices sobre los tipos de administradores de las ofertas laborales.
     *
     * @return Lista de {@link crm.utils.KeyValueContainer}
     */
    public List<KeyValueContainer<Long>> getAdminsPorTipo() {
        List<KeyValueContainer<Long>> datos = new ArrayList<>();

        String[] tipos = adminOfertaLaboralUsmempleoRepository.contarPorTipo().split(",");

        datos.add(new KeyValueContainer<>("Creadores", Long.parseLong(tipos[0])));
        datos.add(new KeyValueContainer<>("Publicadores", Long.parseLong(tipos[1])));

        return datos;
    }

    /**
     * Indices sobre la cantidad de administradores que hay en el CRM
     *
     * @return Lista de {@link crm.utils.KeyValueContainer}
     */
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public List<KeyValueContainer<Long>> getAdminsCrm() {
        List<KeyValueContainer<Long>> datos = new ArrayList<>();

        String[] tipos = autorizacionUsuarioRepository.contarAdministradoresCrm().split(",");

        datos.add(new KeyValueContainer<>("Super admin", Long.parseLong(tipos[0])));
        datos.add(new KeyValueContainer<>("Admin institución", Long.parseLong(tipos[1])));
        datos.add(new KeyValueContainer<>("Ayudante institución", Long.parseLong(tipos[2])));
        datos.add(new KeyValueContainer<>("Lector institución", Long.parseLong(tipos[3])));
        datos.add(new KeyValueContainer<>("Admin carrera", Long.parseLong(tipos[4])));

        return datos;
    }
}


