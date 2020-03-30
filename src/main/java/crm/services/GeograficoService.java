package crm.services;

import crm.entities.Comuna;
import crm.entities.Pais;
import crm.repositories.ComunaRepository;
import crm.repositories.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo {@link crm.entities.Pais}
 * y con otras entidades relacionadas como {@link crm.entities.Region}, {@link crm.entities.Provincia}
 * y {@link crm.entities.Comuna}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Component
public class GeograficoService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Pais}
     */
    @Autowired
    private PaisRepository paisRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Comuna}
     */
    @Autowired
    private ComunaRepository comunaRepository;

    /**
     * Obtiene una instancia de {@link crm.entities.Pais} segun un id dado.
     *
     * @param id id del {@link crm.entities.Pais} buscado.
     *
     * @return Objeto de la clase {@link crm.entities.Pais}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Pais getPaisById(Short id){
        return paisRepository.findById(id);
    }

    /**
     * Obtiene una instancia de {@link crm.entities.Comuna} segun un id dado.
     *
     * @param id id de la {@link crm.entities.Comuna}
     *
     * @return Objeto de la clase {@link crm.entities.Comuna}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Comuna getComunaById(Short id){
        return comunaRepository.findByCodigo(id);
    }

    /**
     * Obtiene una lista de todos los paises {@link crm.entities.Pais}.
     *
     * @return Collection ({@link java.util.List}) de {@link crm.entities.Institucion}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public List<Pais> getPaises(){
        return paisRepository.findAllByOrderByNombreAsc();
    }

    /**
     * Obtiene una lista de todos los paises {@link crm.entities.Comuna}.
     *
     * @return Collection ({@link java.util.List}) de {@link crm.entities.Comuna}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public List<Comuna> getComunas(){
        return comunaRepository.findAllByOrderByNombreAsc();
    }


}
