package crm.services;

import crm.entities.AntecedenteColegio;
import crm.entities.AntecedenteEducacional;
import crm.repositories.ActividadExalumnoRepository;
import crm.repositories.AntecedenteColegioRepository;
import crm.repositories.AntecedenteEducacionalRepository;
import crm.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Servicio que contiene los metodos para interactuar con entidades relacionadas con los antecedentes de un usuario,
 * como por ejemplo la entidad {@link crm.entities.AntecedenteEducacional}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Controller
public class EntidadesAntecedenteService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AntecedenteEducacional}.
     */
    @Autowired
    private AntecedenteEducacionalRepository antecedenteEducacionalRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Usuario}.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ActividadExalumno}.
     */
    @Autowired
    private ActividadExalumnoRepository actividadExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AntecedenteColegio}.
     */
    @Autowired
    private AntecedenteColegioRepository antecedenteColegioRepository;

    /**
     * Guarda un {@link crm.entities.AntecedenteEducacional} nuevo en la base de datos.
     *
     * @param antecedente {@link crm.entities.AntecedenteEducacional} a guardar en la base de datos.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public AntecedenteEducacional guardarAntecedenteEducacional(AntecedenteEducacional antecedente){
        antecedente.setUsuario(usuarioRepository.findById(antecedente.getUsuario().getId()));
        antecedenteEducacionalRepository.save(antecedente);
        return antecedente;
    }

    /**
     * Elimina un {@link crm.entities.AntecedenteEducacional} de la base de datos segun su id.
     *
     * @param id Id del {@link crm.entities.AntecedenteEducacional} a eliminar en la base de datos.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public void eliminarAntecedenteEducacional(Long id){
        antecedenteEducacionalRepository.delete(id);
    }

    /**
     * Elimina un {@link crm.entities.ActividadExalumno} de la base de datos segun su id.
     *
     * @param id Id del {@link crm.entities.ActividadExalumno} a eliminar en la base de datos.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public void eliminarAntecedenteLaboral(Long id){
        actividadExalumnoRepository.delete(id);
    }

    /**
     * Elimina un {@link crm.entities.AntecedenteColegio} de la base de datos segun su id.
     *
     * @param id Id del {@link crm.entities.AntecedenteColegio} a eliminar en la base de datos.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public void eliminarAntecedenteColegio(Long id){
        antecedenteColegioRepository.delete(id);
    }



    /**
     * Obtiene un listado de  {@link crm.entities.AntecedenteEducacional}, según un id de {@link crm.entities.Carrera}
     *
     * @param codCarrera id de la {@link crm.entities.Carrera} de las  {@link crm.entities.AntecedenteEducacional}
     *
     * @return  {@link crm.entities.AntecedenteEducacional} buscados
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<AntecedenteEducacional> buscarAntecedenteEducacionalPorCodCarrera(Long codCarrera) {
        return antecedenteEducacionalRepository.buscarPorCodCarrera(codCarrera);
    }



    /**
     * Obtiene la cantidad de  {@link crm.entities.AntecedenteEducacional}, según un id de {@link crm.entities.Carrera}
     *
     * @param codCarrera id de la {@link crm.entities.Carrera} de las  {@link crm.entities.AntecedenteEducacional}
     *
     * @return  {@link crm.entities.AntecedenteEducacional} buscados
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public Long buscarCantidadAntecedenteEducacionalPorCodCarrera(Long codCarrera) {
        return antecedenteEducacionalRepository.buscarCantidadPorCodCarrera(codCarrera);
    }

    /**
     * Obtiene un {@link crm.entities.AntecedenteEducacional} según su id
     *
     * @param id id del  {@link crm.entities.AntecedenteEducacional}
     *
     * @return  {@link crm.entities.AntecedenteEducacional} buscado
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     *
     */
    public AntecedenteEducacional getAntecedenteEducacionalById(Long id) {
            return antecedenteEducacionalRepository.getAntecedenteEducacionalById(id);
    }

    /**
     * Obtiene un {@link crm.entities.AntecedenteColegio} según su id
     *
     * @param id id del  {@link crm.entities.AntecedenteColegio}
     *
     * @return  {@link crm.entities.Colegio} buscado
     *
     * @author Fernando Da Silva <ignacio.oneto@alumnos.usm.cl>
     *
     */
    public AntecedenteColegio getAntecedenteColegioById(Long id) {
        return antecedenteColegioRepository.getAntecedenteColegioById(id);
    }

    /**
     * Guarda un {@link crm.entities.AntecedenteColegio} nuevo en la base de datos.
     *
     * @param antecedente {@link crm.entities.AntecedenteColegio} a guardar en la base de datos.
     *
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     */
    public AntecedenteColegio guardarAntecedenteColegio(AntecedenteColegio antecedente){
        antecedente.setUsuario(usuarioRepository.findById(antecedente.getUsuario().getId()));
        antecedenteColegioRepository.save(antecedente);
        return antecedente;
    }
}
