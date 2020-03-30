package crm.services;


import crm.entities.*;
import crm.repositories.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo {@link FiltroOfertaLaboralService}
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Component
public class FiltroOfertaLaboralService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link UsuarioUsmempleoEmpresa}.
     */
    @Autowired
    private FiltroOfertaLaboralRepository filtroOfertaLaboralRepository;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Usuario}.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Servicio utilizado para el manejo de las entidades correspondientes a las tablas tipo
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoCargo}
     */
    @Autowired
    private TipoCargoRepository tipoCargoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoArea}
     */
    @Autowired
    private TipoAreaRepository tipoAreaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstudio}
     */
    @Autowired
    private TipoEstudioRepository tipoEstudioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoMoneda}.
     */
    @Autowired
    private TipoMonedaRepository tipoMonedaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@Link crm.entities.Region}
     */
    @Autowired
    private RegionRepository regionRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Pais}
     */
    @Autowired
    private PaisRepository paisRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Carrera}
     */
    @Autowired
    private CarreraRepository carreraRepository;

    /**
     * {@link Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(FiltroOfertaLaboralService.class);




    /**
     * Obtiene un listado de {@link FiltroOfertaLaboral} según el Id de la
     *
     * @param id identificador del {@link FiltroOfertaLaboral} que se desea buscar
     *
     * @return Coleccion ({@link List}) de {@link UsuarioUsmempleoEmpresa}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public FiltroOfertaLaboral findById (Integer id) {
        return filtroOfertaLaboralRepository.findById(id);
    }








    /**
     * Prepara los datos para poder guardar el FiltroOfertaLaboral en la Base de datos y registra si fue modificado o no
     *
     * @param idUsuario Id del Usuario al que pertenece el filtro
     * @param idFiltroOfertaLaboral Id del FiltroOfertaLaboral a modificar
     * @param nombreFiltro Nombre del FiltroOfertaLaboral que se desea guardar
     * @param idRegion Id del Region del FiltroOfertaLaboral que se desea guardar
     * @param idTipoCargo Id del TipoCargo del FiltroOfertaLaboral que se desea guardar
     * @param idCarrera Id del Carrera del FiltroOfertaLaboral que se desea guardar
     * @param idTipoArea Id del TipoArea del FiltroOfertaLaboral que se desea guardar
     * @param idTipoEstudio Id del TipoEstudio del FiltroOfertaLaboral que se desea guardar
     * @param rangoAniosExperiencia Rango de Experiencia del FiltroOfertaLaboral que se desea guardar
     * @param aniosExperiencia Años de experiencia del FiltroOfertaLaboral que se desea guardar
     * @param idTipoMoneda Id del TipoMoneda del FiltroOfertaLaboral que se desea guardar
     * @param salarioLimiteInferior Limite inferior del salario del FiltroOfertaLaboral que se desea guardar
     * @param salarioLimiteSuperior Limite superior del salario del FiltroOfertaLaboral que se desea guardar
     * @param enviarEmail Opcion de enviar o no email
     * @param diaEnvio Dia de la semana en que se desea enviar
     * @param momentoEnvio Momento del dia en que se desea enviar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    //@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void editarFiltroLaboralUsuario(String idUsuario, String idFiltroOfertaLaboral, String nombreFiltro, String idRegion, String idTipoCargo, String idCarrera, String idTipoArea, String idTipoEstudio, String rangoAniosExperiencia, String aniosExperiencia, String idTipoMoneda, String salarioLimiteInferior, String salarioLimiteSuperior, String enviarEmail, String diaEnvio, String momentoEnvio) {

        Date fechaActual = new Date();
        FiltroOfertaLaboral filtroOfertaLaboral = null;
        Boolean modificado = false;
        Boolean nuevo = false;

        // caso que el filtro sea nueva
        if (idFiltroOfertaLaboral.compareTo("") == 0) {
            filtroOfertaLaboral = new FiltroOfertaLaboral();
            filtroOfertaLaboral.setFechaCreacion(fechaActual);
            filtroOfertaLaboral.setUsuario((usuarioService.getUsuarioById(Long.parseLong(idUsuario))));
            nuevo = true;
        }
        else {
            filtroOfertaLaboral = filtroOfertaLaboralRepository.findById(Integer.parseInt(idFiltroOfertaLaboral));
        }




        // id del filtro no cambia, asi que no se setea


        // si filtro es nuevo, ó si no lo fuera pero hubo un cambio, entonces se modifica
        if (nuevo == true || filtroOfertaLaboral.getNombre().compareTo(nombreFiltro) != 0) {
            filtroOfertaLaboral.setNombre(nombreFiltro);
            modificado = true;
        }


        // Region
        // caso se seleccionan todas
        if ( idRegion.compareTo("Todas") == 0 ) {

            // si se modifico, se anota el cambio
            if ( nuevo == true || filtroOfertaLaboral.getRegion() != null ) {
                modificado = true;
            }

            Pais pais = paisRepository.findById(Short.parseShort("56"));
            filtroOfertaLaboral.setPais(pais);
            filtroOfertaLaboral.setRegion(null);
        }
        else {
            // si hay algun cambio se modifica, sino queda como estaba
            if ( filtroOfertaLaboral.getRegion() == null || filtroOfertaLaboral.getRegion().getId().compareTo(Short.parseShort(idRegion)) != 0) {
                modificado = true;

                Pais pais = paisRepository.findById(Short.parseShort("56"));
                Region region = regionRepository.findById(Short.parseShort(idRegion));
                filtroOfertaLaboral.setPais(pais);
                filtroOfertaLaboral.setRegion(region);
            }
        }


        // TipoCargo
        // caso se seleccionan todas
        if ( idTipoCargo.compareTo("Todas") == 0 ) {

            // si se modifico, se anota el cambio
            if ( nuevo == true || filtroOfertaLaboral.getTipoCargo() != null ) {
                modificado = true;
            }

            filtroOfertaLaboral.setTipoCargo(null);
        }
        else {
            // si hay algun cambio se modifica, sino queda como estaba
            if ( filtroOfertaLaboral.getTipoCargo() == null || filtroOfertaLaboral.getTipoCargo().getCodCargo().compareTo(Short.parseShort(idTipoCargo)) != 0) {
                modificado = true;

                TipoCargo tipoCargo = tipoCargoRepository.findByCodCargo(Short.parseShort(idTipoCargo));
                filtroOfertaLaboral.setTipoCargo(tipoCargo);
            }
        }


        // Carrera
        // si hay algun cambio se modifica, sino queda como estaba
        if ( nuevo == true || filtroOfertaLaboral.getCarrera().getCodCarrera().compareTo(Long.parseLong(idCarrera)) != 0) {
            modificado = true;

            Carrera carrera = carreraRepository.findByCodCarrera(Long.parseLong(idCarrera));
            filtroOfertaLaboral.setCarrera(carrera);
        }


        // TipoArea
        // caso se seleccionan todas
        if ( idTipoArea.compareTo("Todas") == 0 ) {

            // si se modifico, se anota el cambio
            if ( nuevo == true || filtroOfertaLaboral.getTipoArea() != null ) {
                modificado = true;
            }

            filtroOfertaLaboral.setTipoArea(null);
        }
        else {
            // si hay algun cambio se modifica, sino queda como estaba
            if ( filtroOfertaLaboral.getTipoArea() == null || filtroOfertaLaboral.getTipoArea().getCodigo().compareTo(Short.parseShort(idTipoArea)) != 0) {
                modificado = true;

                TipoArea tipoArea = tipoAreaRepository.findByCodigo(Short.parseShort(idTipoArea));
                filtroOfertaLaboral.setTipoArea(tipoArea);
            }
        }


        // TipoEstudio
        // caso se seleccionan todas
        if ( idTipoEstudio.compareTo("Todas") == 0 ) {

            // si se modifico, se anota el cambio
            if ( nuevo == true || filtroOfertaLaboral.getTipoEstudio() != null ) {
                modificado = true;
            }

            filtroOfertaLaboral.setTipoEstudio(null);
        }
        else {
            // si hay algun cambio se modifica, sino queda como estaba
            if ( filtroOfertaLaboral.getTipoEstudio() == null || filtroOfertaLaboral.getTipoEstudio().getCodEstudio().compareTo(Short.parseShort(idTipoEstudio)) != 0) {
                modificado = true;

                TipoEstudio tipoEstudio = tipoEstudioRepository.findByCodEstudio(Short.parseShort(idTipoEstudio));
                filtroOfertaLaboral.setTipoEstudio(tipoEstudio);
            }
        }

        // RangoAniosExperiencia
        // si filtro es nuevo, ó si no lo fuera pero hubo un cambio, entonces se modifica
        if (nuevo == true || filtroOfertaLaboral.getRangoAniosExperiencia().compareTo(rangoAniosExperiencia) != 0) {
            modificado = true;
            filtroOfertaLaboral.setRangoAniosExperiencia(rangoAniosExperiencia);
        }

        //AniosExperiencia
        // si filtro es (nuevo y no se dejo vacio), ó (no esta vacio y en la base de datos esta con nulo) ó (no esta vacio y se modifico)
        if ( ( nuevo == true && aniosExperiencia.compareTo("") != 0 ) || ( aniosExperiencia.compareTo("") != 0 && filtroOfertaLaboral.getAniosExperiencia() == null ) || ( aniosExperiencia.compareTo("") != 0 && filtroOfertaLaboral.getAniosExperiencia().toString().compareTo(aniosExperiencia) != 0) )  {
            modificado = true;
            filtroOfertaLaboral.setAniosExperiencia(Short.parseShort(aniosExperiencia));
        }

        // si filtro (quedó con RangoAniosExperiencia como indiferente), AniosExperiencia se setea como null
        if ( rangoAniosExperiencia.compareTo("indiferente") == 0 ) {
            modificado = true;
            filtroOfertaLaboral.setAniosExperiencia(null);
        }


        // SalarioLimiteInferior y SalarioLimiteSuperior
        // si (es nueva y no esta vacio) ó (no esta vacio y el guardado en la base de datos es nulo ) ó (no esta vacio y es distinto del ingresado)
        if ( ( nuevo == true && salarioLimiteInferior.compareTo("") != 0 ) || ( salarioLimiteInferior.compareTo("") != 0 && filtroOfertaLaboral.getSalarioLimiteInferior() == null) || ( salarioLimiteInferior.compareTo("") != 0 && filtroOfertaLaboral.getSalarioLimiteInferior().compareTo(Integer.parseInt(salarioLimiteInferior)) != 0) ) {
            filtroOfertaLaboral.setSalarioLimiteInferior(Integer.parseInt(salarioLimiteInferior));
            modificado = true;
        }

        // si (es nueva y no esta vacio) ó (no esta vacio y el guardado en la base de datos es nulo ) ó (no esta vacio y es distinto del ingresado)
        if ( ( nuevo == true && salarioLimiteSuperior.compareTo("") != 0 ) || ( salarioLimiteSuperior.compareTo("") != 0 && filtroOfertaLaboral.getSalarioLimiteSuperior() == null) || ( salarioLimiteSuperior.compareTo("") != 0 && filtroOfertaLaboral.getSalarioLimiteSuperior().compareTo(Integer.parseInt(salarioLimiteSuperior)) != 0) ) {
            filtroOfertaLaboral.setSalarioLimiteSuperior(Integer.parseInt(salarioLimiteSuperior));
            modificado = true;
        }

        // si alguno de los valores está nulos o ambos nulos
        if ( (salarioLimiteSuperior.compareTo("") == 0 && salarioLimiteSuperior.compareTo("") != 0 ) || (salarioLimiteInferior.compareTo("") == 0 && salarioLimiteInferior.compareTo("") == 0)) {
            filtroOfertaLaboral.setSalarioLimiteInferior(null);
            filtroOfertaLaboral.setSalarioLimiteSuperior(null);
            filtroOfertaLaboral.setTipoMoneda(null);
        }


        // EnviarEmail
        // si se modifico
        if (enviarEmail == null) {
            enviarEmail = "false";
        }
        else {
            enviarEmail = "true";
        }
        if (nuevo || filtroOfertaLaboral.getEnviarEmail().compareTo( Boolean.parseBoolean(enviarEmail)) != 0) {
            filtroOfertaLaboral.setEnviarEmail(Boolean.parseBoolean(enviarEmail));
            modificado = true;
        }

        // DiaEnvio
        // si se modifico
        if (nuevo || filtroOfertaLaboral.getDiaEnvio().compareTo( Short.parseShort(diaEnvio)) != 0) {
            filtroOfertaLaboral.setDiaEnvio(Short.parseShort(diaEnvio));
            modificado = true;
        }

        // MomentoEnvio
        // si se modifico
        if (nuevo || filtroOfertaLaboral.getMomentoEnvio().compareTo(Short.parseShort(momentoEnvio)) != 0) {
            filtroOfertaLaboral.setMomentoEnvio(Short.parseShort(momentoEnvio));
            modificado = true;
        }

        // TipoMoneda
        // si se modifico
        if (nuevo || filtroOfertaLaboral.getTipoMoneda() == null || filtroOfertaLaboral.getTipoMoneda().getCodigo().compareTo(Short.parseShort(idTipoMoneda)) != 0) {
            TipoMoneda tipoMoneda = tipoMonedaRepository.findByCodigo(Short.parseShort(idTipoMoneda));
            filtroOfertaLaboral.setTipoMoneda(tipoMoneda);
            modificado = true;
        }



        // si se modificó se deja registrado
        if (nuevo == true || modificado == true) {
            filtroOfertaLaboral.setFechaModificacion(fechaActual);
            filtroOfertaLaboral.setRutUsuario(usuarioService.getCurrentUser().getRut());
        }


        // almacenamiento del FiltroOfertaLaboral
        filtroOfertaLaboralRepository.save(filtroOfertaLaboral);

    }






    /**
     * Elimina un Usuario de la BD, según un id del FiltroOfertaLaboral
     *
     * @param idFiltroOfertaLaboral Id del FiltroOfertaLaboral a eliminar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    //TODO ver permisos
    public void eliminarFiltroLaboralUsuario(String idFiltroOfertaLaboral) {

        FiltroOfertaLaboral filtroOfertaLaboral = filtroOfertaLaboralRepository.findById(Integer.parseInt(idFiltroOfertaLaboral));

        filtroOfertaLaboralRepository.delete(filtroOfertaLaboral);

    }


}
