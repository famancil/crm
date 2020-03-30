package crm.services;


import crm.entities.CompetenciaUsmempleo;
import crm.entities.Empresa;
import crm.entities.UsuarioEmpresaUsmempleo;
import crm.entities.UsuarioUsmempleoEmpresa;
import crm.repositories.CompetenciaUsmempleoRepository;
import crm.repositories.UsuarioUsmempleoEmpresaRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo {@link crm.entities.CompetenciaUsmempleo}
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Component
public class CompetenciaUsmempleoService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CompetenciaUsmempleo}
     */
    @Autowired
    private CompetenciaUsmempleoRepository competenciaUsmempleoRepository;

    /**
     * {@link Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(CompetenciaUsmempleoService.class);




    /**
     * Obtiene una lista con todas los {@link CompetenciaUsmempleo} asociadas a una {@link crm.entities.TipoCompetencia}
     * según un id de la {@link crm.entities.TipoCompetencia}
     *
     * @return Coleccion ({@link List}) de {@link CompetenciaUsmempleo}
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<CompetenciaUsmempleo> buscarCompetenciaUsmempleoPorIdTipoCompetencia(Integer codigo) {

        return competenciaUsmempleoRepository.buscarCompetenciaUsmempleoPorIdTipoCompetencia(codigo);
    }


}
