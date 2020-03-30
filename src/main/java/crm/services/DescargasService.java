package crm.services;


import crm.entities.*;
import crm.repositories.*;
import crm.utils.Compresion;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Servicio encargado de manejar las peticiones de descargas de datos de {@link Carrera}, {@link Usuario}, {@link Empresa}
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Component
public class DescargasService {


    @Autowired
    private InstitucionRepository institucionRepository;

    @Autowired
    private OfertaLaboralUsmempleoRepository ofertaLaboralUsmempleoRepository;

    @Autowired
    private OfertaCarreraUsmempleoRepository ofertaCarreraUsmempleoRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private SucursalEmpresaRepository sucursalEmpresaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntidadesTipoService entidadesTipoService;

    @Autowired
    private CompetenciaUsmempleoRepository competenciaUsmempleoRepository;



    // directorio de trabajo, para permitir guardar temporalmente los archivos creados

    private String directorio = "/tmp/crm/";

    // formatos de hora
    SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    SimpleDateFormat formatoSoloFecha = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formatoSoloAnio = new SimpleDateFormat("yyyy");
    Date fechaActual = new Date();

    // lista con los nombres de los meses
    ArrayList<String> meses = new ArrayList<>(Arrays.asList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"));




    // ------------------------------------------ KPI CARRERA ------------------------------------------------------------



    /**
     * Crea un archivo comprimido con los datos KPI de la {@link Carrera} (cantidad de ofertas, vacantes y postulaciones por mes y tipo oferta)
     * además de las ofertas registradas a la carrera y sus datos asociados. Los datos de los indices los entrega como primera hoja del excel
     * y las ofertas asociadas se entregan en la segunda hoja del excel
     *
     * @param codsCarreras Ids de las {@link Carrera} con las cuales se creará el archivo de datos
     * @param codInstitucion Ids de las {@link Institucion} a la cual pertenecen las {@Carrera}
     *
     * @return  ruta del archivo que contiene los datos solicitados
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    public String crearArchivosReporteKPICarreras(String[] codsCarreras, String codInstitucion) throws FileNotFoundException {

        preparaDirectorio ();       // crea una carpeta temporal (sino existiese) donde guardar los archivos generados en el proceso
        String filename;

        List<String> rutasArchivos = new ArrayList<>();         // lista que contiene las rutas de los archivos para cada carrera


        for (String idCarrera : codsCarreras) {
            Carrera carrera = carreraRepository.findByCodCarrera(Long.parseLong(idCarrera));        // obtencion de la carrera asociada
            Institucion institucion = institucionRepository.findByCodInstitucion(Short.parseShort(codInstitucion));  // obtencion de la institucion asociada

            List<TipoOferta> listaTipoOfertas = entidadesTipoService.listaTipoOfertas();    // obtencion de los tipos de ofertas registradas en el sistema

            String nombreBaseArchivo = carrera.getNombreCarrera().replace("/", "-");    // nombre base del archivo (ejemplo 'Ingenieria Civil en Informatica'). ( con cambio de caracter "/" en el nombre de la carrera, para evitar problemas con carpetas, existen nombres que tenian ese caracter )


            // ------------>  HOJA 0
            String titulo = "REPORTE DE GESTIÓN PROFESIONAL";                           // titulo del archivo a descargar

            // filas con detalle del archivo de descarga
            List<List<String>> detalleTitulo = new ArrayList<>();
            detalleTitulo.add( Arrays.asList("CARRERA", carrera.getNombreCarrera()) );
            detalleTitulo.add( Arrays.asList("IES", institucion.getNomInstitucion()) );
            detalleTitulo.add( Arrays.asList("Generado", formatoFechaHora.format(fechaActual)));
            List<String> nombreColumnas = new ArrayList<String>();
            List<String> filaVacia = new ArrayList<String>();


            // generacion del rango de fecha a trabajar, desde año 2011 hasta año actual
            Short anioBase = 2011;
            Short anioActual = Short.parseShort(formatoSoloAnio.format(fechaActual));


            List<List<String>> indicesOfertasCarrera = new ArrayList<>(); // Lista con los datos de todos los tipo de oferta, para todos los años

            // nombre cabecera de las columnas del archivo
            nombreColumnas.add(0, "negrita");
            nombreColumnas.add(1, "TIPO OFERTA");
            for (Short anio = anioBase; anio <=anioActual; anio++) {
                nombreColumnas.addAll(meses);
                nombreColumnas.add("Total " + anio.toString());
            }
            nombreColumnas.add("Total General");


            // -----> Indice Ofertas

            // Array tamanio = "no-negrita" + espacio en blanco + total de meses de todos los anos + totales por anio + total general. Contendra el total por cada mes
            String[] totalPorMesesOfertas = new String [1 + 1 + 12 * (anioActual-anioBase + 1) + (anioActual-anioBase + 1) + 1]; // almacenará los totales por cada mes
            totalPorMesesOfertas[0] = "no-negrita";  // indica que fila no debe ir resaltada
            totalPorMesesOfertas[1] = "";
            for (int i = 2; i< totalPorMesesOfertas.length; i++) {          // seteo en cero de los valores
                totalPorMesesOfertas[i] = "0";
            }

            // iteracion por cada tipo de oferta
            for (TipoOferta tipoOferta : listaTipoOfertas) {

                ArrayList<String> datos = new ArrayList<>();    // Lista con los datos de un solo tipo de oferta, para todos los años
                datos.add( "no-negrita");  // indica que fila no debe ir resaltada
                datos.add( tipoOferta.getNombre() );  // agrega el nombre del tipo de oferta
                Long totalPorTipo = 0L;      // almacena los totales por tipo


                // iteracion por cada año
                for (Short anio = anioBase; anio <=anioActual; anio++) {
                    ArrayList<ArrayList<String>> listaIndicesOfertasLaboralesPorTipoOferta = ofertaLaboralUsmempleoRepository.indiceCantidadOfertaLaboralPorTipoOfertaSegunCarreraYAnio(carrera.getCodCarrera(), anio, tipoOferta.getCodigo());

                    Iterator iterTotales = listaIndicesOfertasLaboralesPorTipoOferta.iterator();

                    while (iterTotales.hasNext()) {
                        Object[] datosPorTipoOferta = (Object[]) iterTotales.next();

                        // datos para cada mes del año, y por ultimo el total.
                        String dato = "";
                        for (int iterador = 0; iterador <= 12; iterador++) {
                            dato = String.valueOf(datosPorTipoOferta[iterador]);
                            datos.add( dato );  // agrega al la lista con datos
                        }
                        totalPorTipo = totalPorTipo + Long.parseLong(dato); // suma al total el total del año
                    }
                }

                // recorrer lista de datos, e ir sumando para abajo

                datos.add( totalPorTipo.toString() );  // agrega dato de totales
                indicesOfertasCarrera.add(datos);   // agrega los datos de la presente tipo de oferta, a la lista con todas las tipo de ofertas


                // sumando al total por mes, el aporte del mes del tipo de oferta
                for (int i=2; i < datos.size(); i++) {
                    Long suma = Long.parseLong(totalPorMesesOfertas[i]) + Long.parseLong(datos.get(i));
                    totalPorMesesOfertas[i] = suma.toString();
                }
            }

            indicesOfertasCarrera.add(Arrays.asList(totalPorMesesOfertas));     // agrega los datos de los totales por mes
            indicesOfertasCarrera.add(filaVacia);                               // espacio en blanco, para efectos esteticos
            indicesOfertasCarrera.add(filaVacia);                               // espacio en blanco, para efectos esteticos




            // -----> Indice Vacantes
            indicesOfertasCarrera.add(nombreColumnas);   // Agrega el nombre de la columna en la tabla

            // Array tamanio = "no-negrita" + espacio en blanco + total de meses de todos los anos + totales por anio + total general.Contendra el total por cada mes
            String[] totalPorMesesVacantes = new String [1 + 1 + 12 * (anioActual-anioBase + 1) + (anioActual-anioBase + 1) + 1]; // almacenará los totales por cada mes
            totalPorMesesVacantes[0] = "no-negrita";  // indica que fila no debe ir resaltada
            totalPorMesesVacantes[1] = "";
            for (int i = 2; i< totalPorMesesVacantes.length; i++) {         // seteo en cero de los valores
                totalPorMesesVacantes[i] = "0";
            }

            // iteracion por cada tipo de oferta
            for (TipoOferta tipoOferta : listaTipoOfertas) {

                ArrayList<String> datos = new ArrayList<>();    // Lista con los datos de un solo tipo de oferta, para todos los años
                datos.add( "no-negrita");  // indica que fila no debe ir resaltada
                datos.add( tipoOferta.getNombre() );  // agrega el nombre del tipo de oferta
                Long totalPorTipo = 0L;      // almacena los totales por tipo

                // iteracion por cada año
                for (Short anio = anioBase; anio <=anioActual; anio++) {
                    ArrayList<ArrayList<String>> listaIndicesVacantesOfertasLaboralesPorTipoOferta = ofertaLaboralUsmempleoRepository.indiceCantidadVacantesOfertaLaboralPorTipoOfertaSegunCarreraYAnio(carrera.getCodCarrera(), anio, tipoOferta.getCodigo());

                    Iterator iterTotales = listaIndicesVacantesOfertasLaboralesPorTipoOferta.iterator();


                    while (iterTotales.hasNext()) {
                        Object[] datosPorTipoOferta = (Object[]) iterTotales.next();

                        // datos para cada mes del año, y por ultimo el total.
                        String dato = "";
                        for (int iterador = 0; iterador <= 12; iterador++) {
                            dato = String.valueOf(datosPorTipoOferta[iterador]);
                            datos.add( dato );  // agrega al la lista con datos
                        }
                        totalPorTipo = totalPorTipo + Long.parseLong(dato); // suma al total el total del año
                    }
                }

                datos.add( totalPorTipo.toString() );  // agrega dato de totales
                indicesOfertasCarrera.add(datos);   // agrega los datos de la presente tipo de oferta, a la lista con todas las tipo de ofertas


                // sumando al total por mes, el aporte del mes del tipo de oferta
                for (int i=2; i < datos.size(); i++) {
                    Long suma = Long.parseLong(totalPorMesesVacantes[i]) + Long.parseLong(datos.get(i));
                    totalPorMesesVacantes[i] = suma.toString();
                }
            }


            indicesOfertasCarrera.add(Arrays.asList(totalPorMesesVacantes));    // agrega los datos de los totales por mes
            indicesOfertasCarrera.add(filaVacia);                               // espacio en blanco, para efectos esteticos
            indicesOfertasCarrera.add(filaVacia);                               // espacio en blanco, para efectos esteticos




            // -----> Indice Postulaciones
            indicesOfertasCarrera.add(nombreColumnas);   // Agrega el nombre de la columna en la tabla

            // Array tamanio = "no-negrita" + espacio en blanco + total de meses de todos los anos + totales por anio + total general
            String[] totalPorMesesPostulaciones = new String [1 + 1 + 12 * (anioActual-anioBase + 1) + (anioActual-anioBase + 1) + 1]; // almacenará los totales por cada mes
            totalPorMesesPostulaciones[0] = "no-negrita";  // indica que fila no debe ir resaltada
            totalPorMesesPostulaciones[1] = "";

            for (int i = 2; i< totalPorMesesPostulaciones.length; i++) {            // seteo en cero de los valores
                totalPorMesesPostulaciones[i] = "0";
            }

            // iteracion por cada tipo de oferta
            for (TipoOferta tipoOferta : listaTipoOfertas) {

                ArrayList<String> datos = new ArrayList<>();    // Lista con los datos de un solo tipo de oferta, para todos los años
                datos.add( "no-negrita");  // indica que fila no debe ir resaltada
                datos.add( tipoOferta.getNombre() );  // agrega el nombre del tipo de oferta
                Long totalPorTipo = 0L;      // almacena los totales por tipo

                // iteracion por cada año
                for (Short anio = anioBase; anio <=anioActual; anio++) {
                    ArrayList<ArrayList<String>> listaIndicesPostulacionesOfertasLaboralesPorTipoOferta = ofertaLaboralUsmempleoRepository.indiceCantidadPostulacionesOfertaLaboralPorTipoOfertaSegunCarreraYAnio(carrera.getCodCarrera(), anio, tipoOferta.getCodigo());

                    Iterator iterTotales = listaIndicesPostulacionesOfertasLaboralesPorTipoOferta.iterator();


                    while (iterTotales.hasNext()) {
                        Object[] datosPorTipoOferta = (Object[]) iterTotales.next();

                        // datos para cada mes del año, y por ultimo el total.
                        String dato = "";
                        for (int iterador = 0; iterador <= 12; iterador++) {
                            dato = String.valueOf(datosPorTipoOferta[iterador]);
                            datos.add( dato );  // agrega al la lista con datos
                        }
                        totalPorTipo = totalPorTipo + Long.parseLong(dato); // suma al total el total del año
                    }
                }

                datos.add( totalPorTipo.toString() );  // agrega dato de totales
                indicesOfertasCarrera.add(datos);   // agrega los datos de la presente tipo de oferta, a la lista con todas las tipo de ofertas


                // sumando al total por mes, el aporte del mes del tipo de oferta
                for (int i=2; i < datos.size(); i++) {
                    Long suma = Long.parseLong(totalPorMesesPostulaciones[i]) + Long.parseLong(datos.get(i));
                    totalPorMesesPostulaciones[i] = suma.toString();
                }
            }

            indicesOfertasCarrera.add(Arrays.asList(totalPorMesesPostulaciones));   // agrega los datos de los totales por mes


            // envio de los datos para agregarlos al archivo excel
            creaExcel( true, false, "Hoja0", nombreBaseArchivo, titulo, detalleTitulo, nombreColumnas, indicesOfertasCarrera);





            // ------------>  HOJA 1
            List<OfertaLaboralUsmempleo> listaOfertasLaborales = ofertaLaboralUsmempleoRepository.buscarPorCodCarreraYVigenciaUsuarioEmpresaUsmempleoOrdenPorFechaPublicacion(Long.parseLong(idCarrera), Short.parseShort("1"));


            titulo = "REPORTE DE GESTIÓN PROFESIONAL";                           // titulo del archivo a descargar

            // filas con detalle del archivo de descarga
            detalleTitulo = new ArrayList<>();
            detalleTitulo.add( Arrays.asList("CARRERA", carrera.getNombreCarrera()) );
            detalleTitulo.add( Arrays.asList("IES", institucion.getNomInstitucion()) );
            detalleTitulo.add( Arrays.asList("Generado", formatoFechaHora.format(fechaActual)));

            // nombre de las columnas con datos de las ofertas para la carrera
            nombreColumnas = new ArrayList<String>();
            nombreColumnas.add( "negrita");  // indica que fila debe ir resaltada
            nombreColumnas.add("Id");
            nombreColumnas.add("Empresa");
            nombreColumnas.add("Tipo Oferta");
            nombreColumnas.add("Nombre Cargo");
            nombreColumnas.add("Vacantes");
            nombreColumnas.add("Titulo");
            nombreColumnas.add("Id");
            nombreColumnas.add("Cantidad Postulantes");
            nombreColumnas.add("Es Expo Laboral");
            nombreColumnas.add("Fecha Publicacion");
            nombreColumnas.add("Mostrar Sueldo");
            nombreColumnas.add("Salario");
            nombreColumnas.add("Contador Correos");
            nombreColumnas.add("Años Experiencia");
            nombreColumnas.add("Nombre Publicacion");
            nombreColumnas.add("Vigencia Empresa");
            nombreColumnas.add("Creador");
            nombreColumnas.add("Publicador");
            nombreColumnas.add("Nombre Completo Administrador Oferta");
            nombreColumnas.add("Cargo Administrador Oferta");
            nombreColumnas.add("Email Administrador Oferta");
            nombreColumnas.add("Celular Administrador Oferta");
            nombreColumnas.add("Fono Administrador Oferta");
            nombreColumnas.add("Fono Opcional Administrador Oferta");
            nombreColumnas.add("Direccion Administrador Oferta");


            List<List<String>> datosOfertasCarrera = new ArrayList<>();


            // iteracion por cada lista de ofertas laborales, agrega cada uno de los datos para cada columna
            for (int indice = 0; indice < listaOfertasLaborales.size(); indice++) {
                OfertaLaboralUsmempleo ofertaLaboralUsmempleo = listaOfertasLaborales.get(indice);
                ArrayList<String> datos = new ArrayList<>();

                datos.add( "no-negrita");  // indica que fila no debe ir resaltada
                datos.add(ofertaLaboralUsmempleo.getId().toString());
                datos.add(ofertaLaboralUsmempleo.getEmpresa().getRazonSocial());
                datos.add(ofertaLaboralUsmempleo.getOferta().getNombre());
                datos.add(ofertaLaboralUsmempleo.getCargo().getNomCargo());
                datos.add(ofertaLaboralUsmempleo.getVacantes().toString());
                datos.add(ofertaLaboralUsmempleo.getTitulo());
                datos.add(ofertaLaboralUsmempleo.getId().toString());
                datos.add(ofertaLaboralUsmempleo.getCantPostulantes().toString());
                if (ofertaLaboralUsmempleo.getEsExpolaboral() == true) {
                    datos.add("Si");
                }
                else {
                    datos.add("No");
                }
                datos.add(formatoSoloFecha.format(ofertaLaboralUsmempleo.getFechaPublicacion()));
                if (ofertaLaboralUsmempleo.getMostrarSueldo() == true) {
                    datos.add("Si");
                }
                else {
                    datos.add("No");
                }
                datos.add(ofertaLaboralUsmempleo.getSalario().toString());
                if (ofertaLaboralUsmempleo.getContadorCorreos() != null) {                                              // manejada asi por campos nulos en la base de datos
                    datos.add(ofertaLaboralUsmempleo.getContadorCorreos().toString());
                }
                else {
                    datos.add("");
                }
                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getCreador() == true) {
                    datos.add(ofertaLaboralUsmempleo.getAniosExperiencia().toString());
                }
                else {
                    datos.add("");
                }
                datos.add(ofertaLaboralUsmempleo.getPublicacionUsmempleo().getNombre());
                datos.add(ofertaLaboralUsmempleo.getEmpresa().getVigencia().getNomVigencia());
                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getCreador() == true) {
                    datos.add("Si");
                }
                else {
                    datos.add("No");
                }
                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getPublicador() == true) {
                    datos.add("Si");
                }
                else {
                    datos.add("No");
                }
                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getNombreCompleto() != null ) {
                    datos.add(ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getNombreCompleto());
                }
                else {
                    datos.add("");
                }
                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getCargo() != null ) {
                    datos.add(ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getCargo());
                }
                else {
                    datos.add("");
                }
                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getEmail() != null ) {
                    datos.add(ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getEmail());
                }
                else {
                    datos.add("");
                }
                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getCelular() != null ) {
                    datos.add(ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getCelular());
                }
                else {
                    datos.add("");
                }
                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getFono() != null ) {
                    datos.add(ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getFono());
                }
                else {
                    datos.add("");
                }
                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getFonoOpcional() != null ) {
                    datos.add(ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getFonoOpcional());
                }
                else {
                    datos.add("");
                }
                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getDireccion() != null ) {
                    datos.add(ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getDireccion());
                }
                else {
                    datos.add("");
                }

                datosOfertasCarrera.add( datos );
            }


            filename = creaExcel( false, true, "Hoja1", nombreBaseArchivo, titulo, detalleTitulo, nombreColumnas, datosOfertasCarrera);

            rutasArchivos.add( filename );
        }

        Compresion compresion = new Compresion();
        compresion.crearComprimido(rutasArchivos, directorio + "descargas", ".zip");

        // eliminacion de los archivos creados
        for (String ruta : rutasArchivos) {
            File file = new File(ruta);
            file.delete();
        }

        // retorna ruta del comprimido
        return directorio + "descargas" + ".zip";
    }




    // ------------------------------------------ EMPRESAS ------------------------------------------------------------



    /**
     * Crea un archivo comprimido con las {@link Empresa} registradas en el sistema según el nombre de empresa especificado como parametro
     *
     * @param nombreEmpresa nombre de la {@link Empresa} a descargar los datos. Caso que sea 'Todas las Empresas' se descargarán
     *                      todas las empresas
     *
     * @return  ruta del archivo que contiene los datos solicitados
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    public String crearArchivosReporteEmpresas(String nombreEmpresa, String incluirPublicadores) throws FileNotFoundException {

        preparaDirectorio ();       // crea una carpeta temporal (sino existiese) donde guardar los archivos generados en el proceso

        List<String> rutasArchivos = new ArrayList<>();
        List<Empresa> listaEmpresas = new ArrayList<>();

        // obtencion de las empresas seleccionadas
        if (nombreEmpresa.compareTo("Todas las Empresas") == 0) {
            listaEmpresas = empresaRepository.findAll();
        }
        else {
            listaEmpresas.add(empresaRepository.buscarEmpresaEspecificaPorRazonSocialONombreFantasiaOSigla(nombreEmpresa).get(0));
        }

        for (Empresa empresa : listaEmpresas) {

            List<SucursalEmpresa> listaSucursales = sucursalEmpresaRepository.buscarSucursalesEmpresaByIdEmpresa(empresa.getId());       // busqueda de las sucursales de una empresa

            String nombreBaseArchivo; // nombre base del archivo segun el nombre de la empresa (ejemplo 'Codelco').

            if (empresa.getNombreFantasiaEmpresa() != null) {
                nombreBaseArchivo = empresa.getId() + "_" + empresa.getNombreFantasiaEmpresa();
            }
            else if (empresa.getRazonSocial() != null) {
                nombreBaseArchivo = empresa.getId() + "_" + empresa.getRazonSocial();
            }
            else {
                nombreBaseArchivo = empresa.getId() + "_" + "SinInformacionNombre";
            }

            nombreBaseArchivo = nombreBaseArchivo.replace("/", "-"); //  ( con cambio de caracter "/" en el nombre de empresa, para evitar problemas con carpetas, existen nombres que tenian ese caracter )


            // -> datos del archivo

            String titulo = "REPORTE EMPRESA";

            // filas con detalle del archivo de descarga
            List<List<String>> detalleTitulo = new ArrayList<>();
            detalleTitulo.add( Arrays.asList("Rut", empresa.getRutEmpresa()) );
            detalleTitulo.add( Arrays.asList("Nombre", empresa.getNombreFantasiaEmpresa()) );
            detalleTitulo.add( Arrays.asList("Razon Social", empresa.getRazonSocial()) );
            if (empresa.getPais() != null) {
                detalleTitulo.add(Arrays.asList("Pais", empresa.getPais().getNombre()));
            }
            else {
                detalleTitulo.add(Arrays.asList("Pais", "Sin Informacion"));
            }
            detalleTitulo.add( Arrays.asList("Giro", empresa.getGiroEmpresa()) );
            if (empresa.getHeadHunter() == true) {
                detalleTitulo.add(Arrays.asList("HeadHunter", "Si"));
            }
            else {
                detalleTitulo.add(Arrays.asList("HeadHunter", "No"));
            }
            if (empresa.getNumEmpleados() != null) {
                detalleTitulo.add(Arrays.asList("Numero Empleados", empresa.getNumEmpleados().toString()));
            }
            else {
                detalleTitulo.add(Arrays.asList("Numero Empleados", "Sin Informacion"));
            }
            if (empresa.getNumContratAnu() != null) {
                detalleTitulo.add(Arrays.asList("Numero Contratados Anual", empresa.getNumContratAnu().toString()));
            }
            else {
                detalleTitulo.add(Arrays.asList("Numero Contratados Anual", "Sin Informacion"));
            }
            if (empresa.getVigencia() != null) {
                detalleTitulo.add(Arrays.asList("Vigencia", empresa.getVigencia().getNomVigencia()));
            }
            else {
                detalleTitulo.add(Arrays.asList("Vigencia", "Sin Informacion"));
            }
            detalleTitulo.add( Arrays.asList("Generado", formatoFechaHora.format(fechaActual)));


            // nombre de las columnas con datos de las sucursales
            List<String> nombreColumnas = new ArrayList<String>();
            nombreColumnas.add( "negrita");  // indica que fila debe ir resaltada
            nombreColumnas.add("Fono Sucursal");
            nombreColumnas.add("Email Sucursal");
            nombreColumnas.add("Comuna Sucursal");
            nombreColumnas.add("Direccion Sucursal");
            nombreColumnas.add("Vigencia Sucursal");

            if (incluirPublicadores != null) {
                nombreColumnas.add("Nombre");
                nombreColumnas.add("Cargo");
                nombreColumnas.add("Direccion");
                nombreColumnas.add("Telefono");
                nombreColumnas.add("Telefono Auxiliar");
                nombreColumnas.add("Celular");
                nombreColumnas.add("Correo");
            }

            List<List<String>> datosSucursales = new ArrayList<>();

            for (int indice = 0; indice < listaSucursales.size(); indice++) {
                SucursalEmpresa sucursal = listaSucursales.get(indice);
                ArrayList<String> datos = new ArrayList<>();

                datos.add( "no-negrita");  // indica que fila no debe ir resaltada
                datos.add(sucursal.getSucFono());
                datos.add(sucursal.getSucEmail());

                if (sucursal.getComuna() != null) {
                    datos.add(sucursal.getComuna().getNombre());
                }
                else {
                    datos.add("Sin Informacion");
                }
                datos.add(sucursal.getSucDireccion());

                if (sucursal.getTipoVigencia() != null) {
                    datos.add(sucursal.getTipoVigencia().getNomVigencia());
                }
                else {
                    datos.add("Sin Informacion");
                }

                if (incluirPublicadores != null) {
                    if (sucursal.getUsuarioUsmempleoEmpresaList().size() != 0) {
                        UsuarioUsmempleoEmpresa usuarioUsmempleoEmpresa = sucursal.getUsuarioUsmempleoEmpresaList().get(0);

                        datos.add(usuarioUsmempleoEmpresa.getUsuarioEmpresaUsmempleo().getNombreCompleto());
                        datos.add(usuarioUsmempleoEmpresa.getUsuarioEmpresaUsmempleo().getCargo());
                        datos.add(usuarioUsmempleoEmpresa.getUsuarioEmpresaUsmempleo().getDireccion());
                        datos.add(usuarioUsmempleoEmpresa.getUsuarioEmpresaUsmempleo().getFono());
                        datos.add(usuarioUsmempleoEmpresa.getUsuarioEmpresaUsmempleo().getFonoOpcional());
                        datos.add(usuarioUsmempleoEmpresa.getUsuarioEmpresaUsmempleo().getCelular());
                        datos.add(usuarioUsmempleoEmpresa.getUsuarioEmpresaUsmempleo().getEmail());
                    } else {
                        datos.add("");
                        datos.add("");
                        datos.add("");
                        datos.add("");
                        datos.add("");
                        datos.add("");
                        datos.add("");
                    }
                }


                datosSucursales.add( datos );
            }

            String filename = creaExcel( true, true, "Hoja1", nombreBaseArchivo, titulo, detalleTitulo, nombreColumnas, datosSucursales);
            rutasArchivos.add( filename );

        }

        Compresion compresion = new Compresion();
        compresion.crearComprimido(rutasArchivos, directorio + "descargas", ".zip");

        // eliminacion de los archivos creados
        for (String ruta : rutasArchivos) {
            File file = new File(ruta);
            file.delete();
        }

        // retorna ruta del comprimido
        return directorio + "descargas" + ".zip";
    }





    // ------------------------------------------ USUARIOS ------------------------------------------------------------



    /**
     * Crea un archivo comprimido con los usuarios pertenecientes a una {@Carrera} dictada en una {@Institucion} especifica.
     *
     * @param codsCarreras Ids de las {@link Carrera} con las cuales se creará el archivo de datos
     * @param codInstitucion Ids de las {@link Institucion} a la cual pertenecen las {@Carrera}
     *
     * @return  ruta del archivo que contiene los datos solicitados
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    public String crearArchivosReporteUsuariosCarreras(String[] codsCarreras, String codInstitucion) throws FileNotFoundException {

        preparaDirectorio ();       // crea una carpeta temporal (sino existiese) donde guardar los archivos generados en el proceso

        List<String> rutasArchivos = new ArrayList<>();         // lista que contiene las rutas de los archivos de usuarios para cada carrera

        for (String idCarrera : codsCarreras) {                 // iteracion sobre cada carrera seleccionada en la vista
            Carrera carrera = carreraRepository.findByCodCarrera(Long.parseLong(idCarrera));
            Institucion institucion = institucionRepository.findByCodInstitucion(Short.parseShort(codInstitucion));

            List<Usuario> listaUsuarios = usuarioRepository.buscarUsuariosPorCarreraInstitucion(Long.parseLong(idCarrera), Short.parseShort(codInstitucion));       // busqueda de los usuarios pertenecientes a la carrera

            String nombreBaseArchivo = carrera.getNombreCarrera().replace("/", "-");    // nombre base del archivo (ejemplo 'Ingenieria Civil en Informatica'). ( con cambio de caracter "/" en el nombre de la carrera, para evitar problemas con carpetas, existen nombres que tenian ese caracter )

            String titulo = "REPORTE USUARIOS";                           // titulo del archivo a descargar

            // filas con detalle del archivo de descarga
            List<List<String>> detalleTitulo = new ArrayList<>();
            detalleTitulo.add( Arrays.asList("CARRERA", carrera.getNombreCarrera()) );
            detalleTitulo.add( Arrays.asList("IES", institucion.getNomInstitucion()) );
            detalleTitulo.add( Arrays.asList("Generado", formatoFechaHora.format(fechaActual)));

            // nombre de las columnas con datos de los usuarios
            List<String> nombreColumnas = new ArrayList<String>();
            nombreColumnas.add("negrita");
            nombreColumnas.add("Id");
            nombreColumnas.add("Fecha de Registro");
            nombreColumnas.add("Rut");
            nombreColumnas.add("Vocativo");
            nombreColumnas.add("Nombres");
            nombreColumnas.add("Apellido Paterno");
            nombreColumnas.add("Apellido Materno");
            nombreColumnas.add("Estado Civil");
            nombreColumnas.add("Sexo");
            nombreColumnas.add("Nacionalidad");
            nombreColumnas.add("Fecha de Nacimiento");
            nombreColumnas.add("Email");
            nombreColumnas.add("Email Opcional");
            nombreColumnas.add("Email Laboral");
            nombreColumnas.add("Codigo Postal");
            nombreColumnas.add("Direccion");
            nombreColumnas.add("Comuna");
            nombreColumnas.add("Pais");
            nombreColumnas.add("Celular");
            nombreColumnas.add("Fono Particular");
            nombreColumnas.add("Fono Opcional");
            nombreColumnas.add("Apodo");
            nombreColumnas.add("Pasaporte");
            nombreColumnas.add("Cursos");
            nombreColumnas.add("Idiomas");
            nombreColumnas.add("Herramientas");
            nombreColumnas.add("Antecedentes Laborales");


            List<List<String>> datosUsuarios = new ArrayList<>();

            for (int indice = 0; indice < listaUsuarios.size(); indice++) {
                Usuario usuario = listaUsuarios.get(indice);
                ArrayList<String> datos = new ArrayList<>();

                datos.add( "no-negrita");  // indica que fila no debe ir resaltada
                datos.add(usuario.getId().toString());
                datos.add(formatoSoloFecha.format(usuario.getFechaRegistro()));
                datos.add(usuario.getRut() + "-" + usuario.getDigitoVerificador());
                datos.add(usuario.getVocativo());
                datos.add(usuario.getNombres());
                datos.add(usuario.getApellidoPaterno());
                datos.add(usuario.getApellidoMaterno());
                datos.add(usuario.getEstadoCivil().getNombre());
                if (usuario.getSexo().compareTo( Short.parseShort("1") ) == 0) {
                    datos.add("Femenino");
                }
                else {
                    datos.add("Masculino");
                }

                datos.add(usuario.getNacionalidad());
                datos.add(formatoSoloFecha.format(usuario.getFechaNacimiento()));
                datos.add(usuario.getEmail());
                datos.add(usuario.getEmailOpcional());
                datos.add(usuario.getEmailLaboral());
                if (usuario.getCodigoPostal() != null) {
                    datos.add(usuario.getCodigoPostal().toString());
                }
                else {
                    datos.add("");
                }
                datos.add(usuario.getDireccion());
                if (usuario.getComuna() != null && usuario.getComuna().getProvincia() != null) {
                    datos.add(usuario.getComuna().getProvincia().getNombre());
                }
                else {
                    datos.add("");
                }
                if (usuario.getPais() != null) {
                    datos.add(usuario.getPais().getNombre());
                }
                else {
                    datos.add("");
                }
                datos.add(usuario.getCelular());
                datos.add(usuario.getFonoParticular());
                datos.add(usuario.getFonoOpcional());
                datos.add(usuario.getApodo());
                datos.add(usuario.getPasaporte());

                if (usuario.getCapacitacionExalumnoList().size() != 0) {
                    datos.add("Si");
                }
                else
                {
                    datos.add("No");
                }

                if (usuario.getManejoIdiomasList().size() != 0) {
                    datos.add("Si");
                }
                else
                {
                    datos.add("No");
                }

                if (usuario.getConocimientoInfoExalumnoList().size() != 0) {
                    datos.add("Si");
                }
                else
                {
                    datos.add("No");
                }

                if (usuario.getActividadExalumnoList().size() != 0) {
                    datos.add("Si");
                }
                else
                {
                    datos.add("No");
                }

                datosUsuarios.add( datos );
            }


            String filename = creaExcel( true, true, "Hoja1", nombreBaseArchivo, titulo, detalleTitulo, nombreColumnas, datosUsuarios);
            rutasArchivos.add(filename);
        }

        Compresion compresion = new Compresion();
        compresion.crearComprimido(rutasArchivos, directorio + "descargas", ".zip");

        // eliminacion de los archivos creados
        for (String ruta : rutasArchivos) {
            File file = new File(ruta);
            file.delete();
        }

        // retorna ruta del comprimido
        return directorio + "descargas" + ".zip";
    }





    // ------------------------------------------ OFERTA LABORAL ------------------------------------------------------------



    /**
     *
     *
     * @param codsCarreras Ids de las {@link Carrera} con las cuales se creará el archivo de datos
     *
     * @return  ruta del archivo que contiene los datos solicitados
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    public String crearArchivosReporteOfertaLaboral(String nombreEmpresa, String[] codsCarreras, String desde, String hasta, String[] idsCompetenciasString) throws FileNotFoundException {

        preparaDirectorio ();       // crea una carpeta temporal (sino existiese) donde guardar los archivos generados en el proceso
        String filename;
        List<String> rutasArchivos = new ArrayList<>();         // lista que contiene las rutas de los archivos para cada carrera


        // rango fechas de las ofertas a seleccionar
        Date fechaDesde = null;
        Date fechaHasta = null;
        try {
            fechaDesde = formatoSoloFecha.parse(desde);
            fechaHasta = formatoSoloFecha.parse(hasta);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // convierte los id a Short
        List<Short> idCompetencias = new ArrayList<>();

        if (idsCompetenciasString == null) {            // si no se seleccionaron competencias, se agregan todas
            List<CompetenciaUsmempleo> listaCompetencias = competenciaUsmempleoRepository.findAll();

            for (CompetenciaUsmempleo competencia : listaCompetencias) {
                idCompetencias.add(competencia.getId());
            }
        }
        else {
            for (String id : idsCompetenciasString) {
                idCompetencias.add(Short.parseShort(id));
            }
        }





        Empresa empresa = empresaRepository.buscarEmpresaEspecificaPorRazonSocialONombreFantasiaOSigla(nombreEmpresa).get(0);


        String nombreBaseArchivo = empresa.getNombreFantasiaEmpresa().replace("/", "-");


        // ------------>  HOJA 0
        String titulo = "REPORTE DE OFERTA LABORAL";                           // titulo del archivo a descargar

        // filas con detalle del archivo de descarga
        List<List<String>> detalleTitulo = new ArrayList<>();
        detalleTitulo.add( Arrays.asList("EMPRESA", empresa.getNombreFantasiaEmpresa()) );
        detalleTitulo.add(Arrays.asList("Ofertas Desde:", desde));
        detalleTitulo.add(Arrays.asList("Ofertas Hasta:", hasta));
        detalleTitulo.add(Arrays.asList("Generado", formatoFechaHora.format(fechaActual)));
        List<String> nombreColumnas = new ArrayList<String>();


        // nombre de las columnas con datos de las ofertas para la carrera
        nombreColumnas = new ArrayList<String>();
        nombreColumnas.add( "negrita");  // indica que fila debe ir resaltada
        nombreColumnas.add("Id");
        nombreColumnas.add("Vigencia");
        nombreColumnas.add("Cargo/Puesto");
        nombreColumnas.add("Cantidad Vacantes");
        nombreColumnas.add("Cantidad Postulantes Esperados");
        nombreColumnas.add("Cantidad Postulantes");
        nombreColumnas.add("Tipo Oferta");
        nombreColumnas.add("Descripción de la Oferta");
        nombreColumnas.add("Fecha Publicación");;
        nombreColumnas.add("Fecha Inicio");;
        nombreColumnas.add("Fecha Vigencia");
        nombreColumnas.add("Lugar de Trabajo");
        nombreColumnas.add("Salario");
        nombreColumnas.add("Duración Contrato");
        nombreColumnas.add("Años de Experiencia");
        nombreColumnas.add("Codigo Carrera");
        nombreColumnas.add("Nombre Carrera");
        nombreColumnas.add("Movilización Propia");
        nombreColumnas.add("Requisitos Minimos");
        nombreColumnas.add("Carta Presentación");
        nombreColumnas.add("Nombre Idioma");
        nombreColumnas.add("Nivel Hablado");
        nombreColumnas.add("Nivel Escrito");
        nombreColumnas.add("Nivel Traducción");
        nombreColumnas.add("Certificación");
        nombreColumnas.add("Tipo de Competencia");
        nombreColumnas.add("Nombre Competencia");
        nombreColumnas.add("Preguntas al Postulante");
        nombreColumnas.add("Encargado de la Oferta");
        nombreColumnas.add("Cargo Encargado");
        nombreColumnas.add("Email Encargado");
        nombreColumnas.add("Celular Encargado");
        nombreColumnas.add("Telefono Fijo Encargado");
        nombreColumnas.add("Destacado Página Inicio");
        nombreColumnas.add("Prioridad en las Búsquedas");


        List<List<String>> datosOfertasLaborales = new ArrayList<>();


        for (String idCarrera : codsCarreras) {
            Carrera carrera = carreraRepository.findByCodCarrera(Long.parseLong(idCarrera));        // obtencion de la carrera asociada

            List<OfertaLaboralUsmempleo> listaOfertasLaborales = ofertaLaboralUsmempleoRepository.buscarPorCodCarreraYFechaDesdeYFechaHastaYIdCompetenciaOrdenPorFechaPublicacion(Long.parseLong(idCarrera), fechaDesde, fechaHasta, idCompetencias);


            // iteracion por cada lista de ofertas laborales, agrega cada uno de los datos para cada columna
            for (int indice = 0; indice < listaOfertasLaborales.size(); indice++) {
                OfertaLaboralUsmempleo ofertaLaboralUsmempleo = listaOfertasLaborales.get(indice);
                ArrayList<String> datos = new ArrayList<>();

                datos.add( "no-negrita");  // indica que fila no debe ir resaltada
                datos.add(ofertaLaboralUsmempleo.getId().toString());
                datos.add(ofertaLaboralUsmempleo.getVigencia().getNomVigencia());
                datos.add(ofertaLaboralUsmempleo.getCargo().getNomCargo());
                datos.add(ofertaLaboralUsmempleo.getVacantes().toString());
                datos.add(Integer.toString(ofertaLaboralUsmempleo.getPostulacionOfertaLaboralUsmempleoList().size()));

                datos.add(ofertaLaboralUsmempleo.getCantPostulantes().toString());


                datos.add(ofertaLaboralUsmempleo.getOferta().getNombre());
                datos.add(ofertaLaboralUsmempleo.getDescripcion());

                if (ofertaLaboralUsmempleo.getFechaPublicacion() != null) {
                    datos.add(formatoSoloFecha.format((ofertaLaboralUsmempleo.getFechaPublicacion())));
                }
                else {
                    datos.add("No");
                }

                if (ofertaLaboralUsmempleo.getFechaInicio() != null) {
                    datos.add(formatoSoloFecha.format((ofertaLaboralUsmempleo.getFechaInicio())));
                }
                else {
                    datos.add("No");
                }

                if (ofertaLaboralUsmempleo.getFechaVigencia() != null) {
                    datos.add(formatoSoloFecha.format((ofertaLaboralUsmempleo.getFechaVigencia())));
                }
                else {
                    datos.add("No");
                }

                datos.add(ofertaLaboralUsmempleo.getLocalidad());
                datos.add(ofertaLaboralUsmempleo.getSalario().toString());
                datos.add(ofertaLaboralUsmempleo.getDuracionContrato());
                datos.add(ofertaLaboralUsmempleo.getAniosExperiencia().toString());


                datos.add(carrera.getCodCarrera().toString());
                datos.add(carrera.getNombreCarrera());


                if (ofertaLaboralUsmempleo.getMovilizacionPropia() == true) {
                    datos.add("Si");
                }
                else {
                    datos.add("No");
                }

                datos.add(ofertaLaboralUsmempleo.getRequisitosMinimos());

                if (ofertaLaboralUsmempleo.getCartaPresentacion() == true) {
                    datos.add("Si");
                }
                else {
                    datos.add("No");
                }


                String nombreIdioma = "";
                String nivelHablado = "";
                String nivelEscrito = "";
                String nivelTraduccion = "";
                String certificacion = "";
                for (ManejoIdiomaOfertaLaboral manejoIdiomaOfertaLaboral : ofertaLaboralUsmempleo.getManejoIdiomaOfertaLaboralList()) {
                    nombreIdioma = nombreIdioma + manejoIdiomaOfertaLaboral.getIdioma().getNomIdioma() +"\n";
                    nivelHablado = nivelHablado + manejoIdiomaOfertaLaboral.getNivelConversacion() +"\n";
                    nivelEscrito = nivelEscrito + manejoIdiomaOfertaLaboral.getNivelEscritura() +"\n";
                    nivelTraduccion = nivelTraduccion + manejoIdiomaOfertaLaboral.getNivelTraduccion() +"\n";

                    if (manejoIdiomaOfertaLaboral.requiereCertificado() == true) {
                        certificacion = certificacion + "Si" +"\n";
                    }
                    else {
                        certificacion = certificacion + "No" +"\n";
                    }
                }
                datos.add(nombreIdioma);
                datos.add(nivelHablado);
                datos.add(nivelEscrito);
                datos.add(nivelTraduccion);
                datos.add(certificacion);


                String tipoCompetencia = "";
                String nombreCompetencia = "";
                for (CompetenciaOfertaLaboral competenciaOfertaLaboral : ofertaLaboralUsmempleo.getCompetenciaOfertaLaboralList()) {
                    tipoCompetencia = tipoCompetencia + competenciaOfertaLaboral.getNivelCompetenciaUsmempleo().getCompetenciaUsmempleo().getTipoCompetencia().getNombre() +"\n";
                    nombreCompetencia = nombreCompetencia + competenciaOfertaLaboral.getNivelCompetenciaUsmempleo().getCompetenciaUsmempleo().getNombre() +"\n";
                }
                datos.add(tipoCompetencia);
                datos.add(nombreCompetencia);


                String preguntas = "";
                for (PreguntaOfertaLaboralUsmEmpleo preguntaOfertaLaboralUsmEmpleo : ofertaLaboralUsmempleo.getPreguntaOfertaLaboralUsmEmpleoList()) {
                    preguntas = preguntas + preguntaOfertaLaboralUsmEmpleo.getPregunta() +"\n";
                }
                datos.add(preguntas);

                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().size() != 0) {
                    datos.add(ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getNombreCompleto());
                }
                else {
                    datos.add("");
                }

                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().size() != 0) {
                    datos.add(ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getCargo());
                }
                else {
                    datos.add("");
                }

                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().size() != 0) {
                    datos.add(ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getEmail());
                }
                else {
                    datos.add("");
                }

                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().size() != 0) {
                    datos.add(ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getCelular());
                }
                else {
                    datos.add("");
                }

                if (ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().size() != 0) {
                    datos.add(ofertaLaboralUsmempleo.getAdminOfertaLaboralUsmempleoList().get(0).getUsuarioUsmempleoEmpresa().getUsuarioEmpresaUsmempleo().getFono());
                }
                else {
                    datos.add("");
                }

                if (ofertaLaboralUsmempleo.getPublicacionUsmempleo() != null) {
                    if (ofertaLaboralUsmempleo.getPublicacionUsmempleo().getPaginaInicioPublicacion() == true) {
                        datos.add("Si");
                    }
                    else {
                        datos.add("No");
                    }


                    if (ofertaLaboralUsmempleo.getPublicacionUsmempleo().getPrioridadPublicacion() == true) {
                        datos.add("Si");
                    }
                    else {
                        datos.add("No");
                    }
                }
                else {
                    datos.add("");
                    datos.add("");
                }



                datosOfertasLaborales.add( datos );
            }
        }


        filename = creaExcel( true, false, "Hoja1", nombreBaseArchivo, titulo, detalleTitulo, nombreColumnas, datosOfertasLaborales);

        rutasArchivos.add( filename );

        Compresion compresion = new Compresion();
        compresion.crearComprimido(rutasArchivos, directorio + "descargas", ".zip");

        // eliminacion de los archivos creados
        for (String ruta : rutasArchivos) {
            File file = new File(ruta);
            file.delete();
        }

        // retorna ruta del comprimido
        return directorio + "descargas" + ".zip";
    }






    /**
     * Verifica que exista el directorio temporal de trabajo. En caso que no exista se crea
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    void preparaDirectorio () {

        File directorioCarpeta = new File(directorio);

        if (!directorioCarpeta.exists()) {
            directorioCarpeta.mkdir();
        }
    }




    // ------------------------------------------ EXCEL ------------------------------------------------------------


    /**
     * Crea un archivo comprimido con los datos de la {@link Carrera} (cantidad de ofertas, vacantes y postulaciones por mes y tipo oferta)
     * además de las ofertas registradas a la carrera y sus datos asociados. Los datos de los indices los entrega como primera hoja del excel
     * y las ofertas asociadas se entregan en la segunda hoja del excel
     *
     * @param primeraHoja Booleano que indica si es la primera hoja del excel (true: es la primera hoja, false: no es la
     *                    primera hoja)
     * @param autoTamanio Booleano que indica si se desea que se adapte automaticamente las columnas para que abarquen todo el
     *                    tamanio del texto que contienen (true: caso que se desea, false: caso que no se desea)
     * @param nombreHoja Nombre de la hoja que se desea colocar a la hoja de trabajo del excel (Sheet)
     * @param nombreBaseArchivo Nombre que se desea colocar al archivo de salida
     * @param tituloPrincipal Titulo principal que tendrá la hoja del excel
     * @param detalleTitulo Lista con detalles que se desea agregar al titulo. Cada detalle a su vez es una lista que debe
     *                      ser en la forma del par (atributo, valor)
     * @param detalleTitulo Lista con los nombres que se desea colocar a las columnas del archivo excel
     * @param datos Lista los datos correspondientes a cada columna, el primer String de la lista indica si la fila debe ir en negrita o no
     *
     * @return  ruta del archivo que contiene los datos solicitados
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    String creaExcel (boolean primeraHoja, boolean autoTamanio, String nombreHoja, String nombreBaseArchivo, String tituloPrincipal, List<List<String>> detalleTitulo,  List<String> nombreColumnas, List<List<String>> datos) throws FileNotFoundException {

        String filename = directorio + nombreBaseArchivo  + '_' + formatoFechaHora.format(fechaActual) + ".xls";


        HSSFWorkbook workbook;
        HSSFSheet sheet;
        try {
            // caso que es la primera hoja, se crea un archivo vacio
            if (primeraHoja) {
                workbook = new HSSFWorkbook();

                FileOutputStream fileOut = new FileOutputStream(filename);
                workbook.write(fileOut);
                fileOut.close();
            }
            // caso no es primera hoja, se abre una nueva hoja en un archivo ya creado
            else {
                FileInputStream archivoEntrada = new FileInputStream(new File(filename));
                workbook = new HSSFWorkbook(archivoEntrada);
            }

            sheet = workbook.createSheet(nombreHoja);

            // definición de los formatos a utilizar como titulo del archivo y de las columnas
            HSSFFont fontBoldTitulo0 = workbook.createFont();
            HSSFFont fontBoldTitulo1 = workbook.createFont();

            HSSFCellStyle styleTitulo0 = workbook.createCellStyle();
            HSSFCellStyle styleTitulo1 = workbook.createCellStyle();

            fontBoldTitulo0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            fontBoldTitulo0.setFontHeightInPoints((short) 16);
            styleTitulo0.setFont(fontBoldTitulo0);

            fontBoldTitulo1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            fontBoldTitulo1.setFontHeightInPoints((short) 10);
            styleTitulo1.setFont(fontBoldTitulo1);


            // titulo principal
            Row rowTitulo0 = sheet.createRow(0);
            Cell celdaTitulo0 = rowTitulo0.createCell(0);
            celdaTitulo0.setCellValue(tituloPrincipal);
            celdaTitulo0.setCellStyle(styleTitulo0);
            rowTitulo0.setHeightInPoints((short) 22);


            // detalle titulo
            int indice = 1;

            // agrega las lineas de detalle del titulo, al archivo
            for (List<String> filaDetalleTitulo : detalleTitulo) {
                Row rowTitulo = sheet.createRow(indice);
                Cell celdaDetalleTitulo_0 = rowTitulo.createCell(0);
                Cell celdaDetalleTitulo_1 = rowTitulo.createCell(1);
                celdaDetalleTitulo_0.setCellValue(filaDetalleTitulo.get(0));      // nombre del dato
                celdaDetalleTitulo_0.setCellStyle(styleTitulo1);
                celdaDetalleTitulo_1.setCellValue( filaDetalleTitulo.get(1) );      // dato
                rowTitulo.setHeightInPoints((short) 16);

                indice++;
            }

            indice += 2;        // lineas en blanco, solo estetico


            // agrega los nombre de las columnas
            int indiceColumnas;

            Row rowTituloColumnas = sheet.createRow(indice);

            for (indiceColumnas = 1; indiceColumnas < nombreColumnas.size(); indiceColumnas++) {
                String nombreColumna = nombreColumnas.get(indiceColumnas);
                Cell celdaColumna = rowTituloColumnas.createCell(indiceColumnas-1);
                celdaColumna.setCellValue(nombreColumna);
                celdaColumna.setCellStyle(styleTitulo1);
            }

            indice++;       // avance en la fila del archivo




            for (List<String> filaDatos : datos) {

                // agrega cada una de las filas de datos al archivo excel
                Row rowDatos = sheet.createRow(indice);


                for (indiceColumnas = 1; indiceColumnas < filaDatos.size(); indiceColumnas++){
                    String dato = filaDatos.get(indiceColumnas);

                    if (filaDatos.get(0).compareTo("negrita") == 0) {
                        Cell celdaColumna = rowDatos.createCell(indiceColumnas-1);
                        celdaColumna.setCellValue(dato);
                        celdaColumna.setCellStyle(styleTitulo1);
                    }
                    else {
                        rowDatos.createCell(indiceColumnas-1).setCellValue(dato);
                    }
                }

                indice++;
            }

            // caso que se desea autoTamanio
            if (autoTamanio == true) {
                for (int i = 0; i < 25; i ++) {
                    sheet.autoSizeColumn(i);
                }
            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();

        } catch ( Exception ex ) {
            System.out.println(ex);
        }

        return filename;
    }


}


