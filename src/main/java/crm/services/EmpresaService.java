package crm.services;

import crm.entities.*;
import crm.parsers.CelularParser;
import crm.repositories.*;
import crm.validators.EmpresaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.orm.hibernate3.SessionFactoryUtils.getSession;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo {@link crm.entities.Empresa} y con otras
 * entidades relacionadas.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Component
public class EmpresaService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Empresa}.
     */
    @Autowired
    private EmpresaRepository empresaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoNivelFacturacion}.
     */
    @Autowired
    private TipoNivelFacturacionRepository nivelFacturacionRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoNivelInteres}.
     */
    @Autowired
    private TipoNivelInteresRepository tipoNivelInteresRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Pais}.
     */
    @Autowired
    private PaisRepository paisRepository;
    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoSector}.
     */
    @Autowired
    private SectorRepository sectorRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoVigencia}.
     */
    @Autowired
    private TipoVigenciaRepository tipoVigenciaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioUsmempleoEmpresa}.
     */
    @Autowired
    private UsuarioUsmempleoEmpresaRepository usuarioUsmempleoEmpresaRepository;
    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}.
     */
    @Autowired
    private ContactoHistoricoEmpresaPersonaParticipanteRepository contactoHistoricoEmpresaPersonaParticipanteRepository;
    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ActividadExalumno}.
     */
    @Autowired
    private ActividadExalumnoRepository actividadExalumnoRepository;
    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.OfertaLaboralUsmempleo}.
     */
    @Autowired
    private OfertaLaboralUsmempleoRepository ofertaLaboralUsmempleoRepository;
    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.SucursalEmpresa}.
     */
    @Autowired
    private SucursalEmpresaRepository sucursalEmpresaRepository;
    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioVistoUsmEmpleo}.
     */
    @Autowired
    private UsuarioVistoUsmempleoRepository usuarioVistoUsmempleoRepository;
    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.DuenoEmpresa}.
     */
    @Autowired
    private DuenoEmpresaRepository duenoEmpresaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CompromisoEmpresa}.
     */
    @Autowired
    private CompromisoEmpresaRepository compromisoEmpresaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AporteEmpresa}.
     */
    @Autowired
    private AporteEmpresaRepository aporteEmpresaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TipoContactoRepository tipoContactoRepository;

    @Autowired
    private TipoOportunidadRepository tipoOportunidadRepository;

    /**
     * Objeto del tipo EntityManagerFactory utilizado para el manejo de criterias.
     */
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private EntityManagerFactory emf;
    /**
     * Obtiene todos los {@link crm.entities.TipoSector} de la base de datos
     *
     * @return todos los {@link crm.entities.TipoSector} de la base de datos.
     */
    public List<TipoSector> cargarSectores() {
        return sectorRepository.findAllByOrderByCodigoAsc();
    }

    /**
     * Obtiene el nombre de un {@link crm.entities.TipoSector} con un codSector especifico.
     *
     * @param codSector Integer del codigo del sector.
     *
     * @return Nombre de un {@link crm.entities.TipoSector}.
     */
    public String buscarNombreSectorPorCodSector(Short codSector) {
        String nombre = sectorRepository.buscarNombreTipoSector(codSector);
        if(nombre==null) return "%";
        else return nombre;
    }

    /**
     * Metodo encargado de realizar la consulta de busqueda de empresas con todos los criterios
     * recibidos como parametros.
     *
     * @param empresa objeto del tipo empresa que contiene posibles criterios de busqueda tales como
     *                rutEmpresa, idEmpresaExtranjera, razonSocial, pais, sector, etc.
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
     * @return lista de empresas con el largo del tamano de la pagina que se especifico en el parametro
     *         tamanoPagina empezando desde el elemento en la posicion offSet.
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public Page<Empresa> buscarEmpresas(
            Empresa empresa,
            String headHunter,
            String empresaPremium,
            String logo,
            String critNumEmpleados,
            String critNumContratAnu,
            Pageable pageable) {

        EntityManager em = emf.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Empresa> query = builder.createQuery(Empresa.class);
        Root<Empresa> root = query.from(Empresa.class);
        final List<Predicate> predicates = new ArrayList<Predicate>();
        if (empresa.getNombreFantasiaEmpresa().compareTo("") != 0) {
            predicates.add(builder.or(
                    builder.or(
                            builder.like(builder.upper(root.get(Empresa_.nombreFantasiaEmpresa)), "%" + empresa.getNombreFantasiaEmpresa().toUpperCase() + "%"),
                            builder.like(builder.upper(root.get(Empresa_.razonSocial)), "%" + empresa.getNombreFantasiaEmpresa().toUpperCase() + "%")),
                    builder.like(builder.upper(root.get(Empresa_.sigla)), "%" + empresa.getNombreFantasiaEmpresa().toUpperCase() + "%")));
        }

        if(empresa.getRutEmpresa().compareTo("") != 0) {
            predicates.add(builder.equal(root.get(Empresa_.rutEmpresa), empresa.getRutEmpresa()));
        }
        else if (empresa.getIdEmpresaExtranjera().compareTo("") != 0) {
            predicates.add(builder.equal(root.get(Empresa_.idEmpresaExtranjera), empresa.getIdEmpresaExtranjera()));
        }

        if (empresa.getGiroEmpresa().compareTo("") != 0) {
            predicates.add(builder.like(builder.upper(root.get(Empresa_.giroEmpresa)), "%" + empresa.getGiroEmpresa().toUpperCase() + "%"));
        }

        if (empresa.getPais().getId().compareTo((short) 0) != 0) {
            Join<Empresa, Pais> paisJoin = root.join(Empresa_.pais);
            predicates.add(builder.equal(paisJoin.get(Pais_.id), empresa.getPais().getId()));
        }
        if(empresa.getNivelFacturacion().getCodNivelFacturacion().compareTo((short)0) != 0){
            Join<Empresa, TipoNivelFacturacion> facturacionJoin = root.join(Empresa_.nivelFacturacion);
            predicates.add(builder.equal(facturacionJoin.get(TipoNivelFacturacion_.codNivelFacturacion), empresa.getNivelFacturacion().getCodNivelFacturacion()));
        }
        if (empresa.getSector().getCodigo() != -1) {
            Join<Empresa, TipoSector> sectorJoin = root.join(Empresa_.sector);
            predicates.add(builder.equal(sectorJoin.get(TipoSector_.codigo), empresa.getSector().getCodigo()));
        }
        if (headHunter.compareTo("1") == 0) {
            predicates.add(builder.isTrue(root.get(Empresa_.headHunter)));
        }
        else if (headHunter.compareTo("2") == 0) {
            predicates.add(builder.isFalse(root.get(Empresa_.headHunter)));
        }
        if (empresaPremium.compareTo("1") == 0) {
            predicates.add(builder.isTrue(root.get(Empresa_.premiumEmpresa)));
        }
        else if (empresaPremium.compareTo("2") == 0) {
            predicates.add(builder.isFalse(root.get(Empresa_.premiumEmpresa)));
        }
        if (logo.compareTo("1") == 0) {
            predicates.add(builder.isTrue(root.get(Empresa_.logo)));
        }
        else if (logo.compareTo("2") == 0) {
            predicates.add(builder.isFalse(root.get(Empresa_.logo)));
        }
        if (empresa.getNumEmpleados() != null) {
            if(critNumEmpleados.compareTo("0") == 0) predicates.add(builder.equal(root.get(Empresa_.numEmpleados), empresa.getNumEmpleados()));
            else if (critNumEmpleados.compareTo("1") == 0) predicates.add(builder.lessThan(root.get(Empresa_.numEmpleados), empresa.getNumEmpleados()));
            else if (critNumEmpleados.compareTo("2") == 0) predicates.add(builder.greaterThan(root.get(Empresa_.numEmpleados), empresa.getNumEmpleados()));
        }
        if (empresa.getNumContratAnu() != null) {
            if(critNumContratAnu.compareTo("0") == 0) predicates.add(builder.equal(root.get(Empresa_.numContratAnu), empresa.getNumContratAnu()));
            else if (critNumContratAnu.compareTo("1") == 0) predicates.add(builder.lessThan(root.get(Empresa_.numContratAnu), empresa.getNumContratAnu()));
            else if (critNumContratAnu.compareTo("2") == 0) predicates.add(builder.greaterThan(root.get(Empresa_.numContratAnu), empresa.getNumContratAnu()));
        }
        //em.createQuery(countQuery);
        //countQuery.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(builder.asc(root.get("razonSocial")));
        TypedQuery<Empresa> typedQuery = em.createQuery(query.select(root));

        //Long totalResultados = em.createQuery(countQuery).getSingleResult();
        Long totalResultados = getResultCount(em,empresa,headHunter,empresaPremium,logo,critNumEmpleados,critNumContratAnu);

        typedQuery.setFirstResult((pageable.getPageNumber()-1)*pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Empresa> empresas = typedQuery.getResultList();
        Empresa emp;
        for(int i=0;i<empresas.size();i++){
            emp = empresas.get(i);
            emp.setCantidadOfertas(empresaRepository.calcularCantidadOfertasEmpresa(emp.getId()));
            emp.setCantidadPublicadores(empresaRepository.calcularCantidadPublicadoresEmpresa(emp.getId()));
            emp.setCantidadUsuarios(empresaRepository.calcularCantidadUsuariosEmpresa(emp.getId()));
            emp.setCantidadSucursales(empresaRepository.calcularCantidadSucursales(emp.getId()));
            empresas.set(i, emp);
        }
        Page<Empresa> resultado = new PageImpl<>(empresas, pageable, totalResultados);
        return resultado;
    }

    /**
     * Metodo encargado de realizar la consulta de conteo de resultados de la busqueda de empresas con todos los criterios
     * recibidos como parametros.
     *
     * @param empresa objeto del tipo empresa que contiene posibles criterios de busqueda tales como
     *                rutEmpresa, idEmpresaExtranjera, razonSocial, pais, sector, etc.
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
     * @return Cantidad de elementos encontrados en la busqueda de empresas.
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    private Long getResultCount(EntityManager em,
                        Empresa empresa,
                        String headHunter,
                        String empresaPremium,
                        String logo,
                        String critNumEmpleados,
                        String critNumContratAnu) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Empresa> root = countQuery.from(Empresa.class);
        final List<Predicate> predicates = new ArrayList<Predicate>();
        if (empresa.getNombreFantasiaEmpresa().compareTo("") != 0) {
            predicates.add(builder.or(
                    builder.or(
                            builder.like(builder.upper(root.get(Empresa_.nombreFantasiaEmpresa)), "%" + empresa.getNombreFantasiaEmpresa().toUpperCase() + "%"),
                            builder.like(builder.upper(root.get(Empresa_.razonSocial)), "%" + empresa.getNombreFantasiaEmpresa().toUpperCase() + "%")),
                    builder.like(builder.upper(root.get(Empresa_.sigla)), "%" + empresa.getNombreFantasiaEmpresa().toUpperCase() + "%")));
        }

        if(empresa.getRutEmpresa().compareTo("") != 0) {
            predicates.add(builder.equal(root.get(Empresa_.rutEmpresa), empresa.getRutEmpresa()));
        }
        else if (empresa.getIdEmpresaExtranjera().compareTo("") != 0) {
            predicates.add(builder.equal(root.get(Empresa_.idEmpresaExtranjera), empresa.getIdEmpresaExtranjera()));
        }

        if (empresa.getGiroEmpresa().compareTo("") != 0) {
            predicates.add(builder.like(builder.upper(root.get(Empresa_.giroEmpresa)), "%" + empresa.getGiroEmpresa().toUpperCase() + "%"));
        }

        if (empresa.getPais().getId().compareTo((short) 0) != 0) {
            Join<Empresa, Pais> paisJoin = root.join(Empresa_.pais);
            predicates.add(builder.equal(paisJoin.get(Pais_.id), empresa.getPais().getId()));
        }
        if(empresa.getNivelFacturacion().getCodNivelFacturacion().compareTo((short)0) != 0){
            Join<Empresa, TipoNivelFacturacion> facturacionJoin = root.join(Empresa_.nivelFacturacion);
            predicates.add(builder.equal(facturacionJoin.get(TipoNivelFacturacion_.codNivelFacturacion), empresa.getNivelFacturacion().getCodNivelFacturacion()));
        }
        if (empresa.getSector().getCodigo() != -1) {
            Join<Empresa, TipoSector> sectorJoin = root.join(Empresa_.sector);
            predicates.add(builder.equal(sectorJoin.get(TipoSector_.codigo), empresa.getSector().getCodigo()));
        }
        if (headHunter.compareTo("1") == 0) {
            predicates.add(builder.isTrue(root.get(Empresa_.headHunter)));
        }
        else if (headHunter.compareTo("2") == 0) {
            predicates.add(builder.isFalse(root.get(Empresa_.headHunter)));
        }
        if (empresaPremium.compareTo("1") == 0) {
            predicates.add(builder.isTrue(root.get(Empresa_.premiumEmpresa)));
        }
        else if (empresaPremium.compareTo("2") == 0) {
            predicates.add(builder.isFalse(root.get(Empresa_.premiumEmpresa)));
        }
        if (logo.compareTo("1") == 0) {
            predicates.add(builder.isTrue(root.get(Empresa_.logo)));
        }
        else if (logo.compareTo("2") == 0) {
            predicates.add(builder.isFalse(root.get(Empresa_.logo)));
        }
        if (empresa.getNumEmpleados() != null) {
            if(critNumEmpleados.compareTo("0") == 0) predicates.add(builder.equal(root.get(Empresa_.numEmpleados), empresa.getNumEmpleados()));
            else if (critNumEmpleados.compareTo("1") == 0) predicates.add(builder.lessThan(root.get(Empresa_.numEmpleados), empresa.getNumEmpleados()));
            else if (critNumEmpleados.compareTo("2") == 0) predicates.add(builder.greaterThan(root.get(Empresa_.numEmpleados), empresa.getNumEmpleados()));
        }
        if (empresa.getNumContratAnu() != null) {
            if(critNumContratAnu.compareTo("0") == 0) predicates.add(builder.equal(root.get(Empresa_.numContratAnu), empresa.getNumContratAnu()));
            else if (critNumContratAnu.compareTo("1") == 0) predicates.add(builder.lessThan(root.get(Empresa_.numContratAnu), empresa.getNumContratAnu()));
            else if (critNumContratAnu.compareTo("2") == 0) predicates.add(builder.greaterThan(root.get(Empresa_.numContratAnu), empresa.getNumContratAnu()));
        }
        countQuery.select(builder.count(root));
        countQuery.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        Long count = em.createQuery(countQuery).getSingleResult();
        return count;
    }


    /**
     * Obtiene la {@link crm.entities.Empresa} segun el id que se pase como parametro
     *
     * @param idEmpresa id de la empresa a buscar.
     * @return la {@link crm.entities.Empresa} o null si es que no se encontraron resultados.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public Empresa getEmpresaById(Long idEmpresa){
        Empresa empresa = empresaRepository.findById(idEmpresa);
        if(empresa!=null){
            return empresa;
        }
        else return null;
    }

    /**
     * Obtiene una lista con todos los objetos de tipo {@link crm.entities.Pais}
     * que tengan asociada una empresa.
     *
     * @return Coleccion {@link java.util.List} de los paises que tienen asociada una empresa
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public List<Pais> cargarPaisesEmpresas() {
        List<Pais> paises = paisRepository.cargarPaisesEmpresas();
        return paises;
    }

    /**
     * Obtiene una lista con todos los objetos de tipo {@link crm.entities.Pais}
     * que hayan en la base de datos.
     *
     * @return Coleccion {@link java.util.List} de los paises.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public List<Pais> cargarPaises() {
        List<Pais> paises = paisRepository.findAllByOrderByNombreAsc();
        return paises;
    }

    /**
     * Obtiene una lista con todos los objetos de tipo {@link crm.entities.TipoNivelFacturacion}
     * que hay en la base de datos.
     *
     * @return Coleccion {@link java.util.List} de los {@Link crm.entities.TipoNivelFacturacion}
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public List<TipoNivelFacturacion> cargarFacturaciones() {
        return nivelFacturacionRepository.findAllByOrderByCodNivelFacturacionAsc();
    }

    /**
     * Obtiene una lista de  {@link crm.entities.Empresa}, asociados a {@link crm.entities.ActividadExalumno},
     * seleccionandola por el calce de nombre de carrera o abreviacion o titulo. (El calce de ellos)
     *
     * @param nombreEmpresa razonSocial, nombre de fantasia o sigla que se desea calzar con la carrera buscada
     *
     * @return {@link crm.entities.Carrera} buscada
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public List<Empresa> buscarEmpresasDeActividadExalumnoPorCalceRazonSocialONombreFantasiaOSigla(String nombreEmpresa) {
        return empresaRepository.buscarEmpresasDeActividadExalumnoPorCalceRazonSocialONombreFantasiaOSigla(nombreEmpresa);
    }





    /**
     * Obtiene una lista de  {@link crm.entities.Empresa}, seleccionandola por el calce de nombre de carrera o
     * abreviacion o titulo. (El calce de ellos)
     *
     * @param nombreEmpresa razonSocial, nombre de fantasia o sigla que se desea calzar con la empresa buscada
     *
     * @return {@link crm.entities.Empresa} buscada
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public List<Empresa> buscarEmpresasPorCalceRazonSocialONombreFantasiaOSigla(String nombreEmpresa) {
        return empresaRepository.buscarEmpresasPorCalceRazonSocialONombreFantasiaOSigla(nombreEmpresa);
    }





    /**
     * Obtiene la cantidad de correos electronicos que se han emitido entre la
     * {@link crm.entities.Empresa} y la red de exalumnos
     *
     * @param id id de la empresa
     * @return Cantidad {@link java.lang.Integer} de correos entre la empresa y la red.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public Integer getNCorreosEmpresa(Long id) {
        return empresaRepository.calcularCorreosEmpresa(id);
    }

    /**
     * Retorna la cantidad {@Link java.lang.Integer} total de reuniones que han habido entre la empresa con id
     * igual al parametro otorgado y la red de exalumnos
     *
     * @param id id de la empresa que se desea buscar.
     *
     * @return Cantidad {@Link java.lang.Integer} de reuniones con una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public Integer getNReunionesEmpresa(Long id){
        return empresaRepository.calcularReunionesEmpresa(id);
    }

    /**
     * Retorna la cantidad {@Link java.lang.Integer} total de llamadas telefonicas que han habido entre la empresa con id
     * igual al parametro otorgado y la red de exalumnos
     *
     * @param id id de la empresa que se desea buscar.
     *
     * @return Cantidad {@Link java.lang.Integer} de llamadas telefonicas a una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public Integer getNLlamadasEmpresa(Long id) {
        return empresaRepository.calcularLlamadasEmpresa(id);
    }

    /**
     * Retorna la cantidad {@Link java.lang.Integer} total de ofertas laborales que ha generado la empresa con id
     * igual al parametro otorgado
     *
     * @param id id de la empresa que se desea buscar.
     *
     * @return Cantidad {@Link java.lang.Integer} de ofertas laborales que ha generado una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public Integer getNOfertasEmpresa(Long id) {
        return empresaRepository.calcularCantidadOfertasEmpresa(id);
    }

    /**
     * Retorna la cantidad {@Link java.lang.Integer} total de vacantes a ofertas laborales que ha generado la empresa con id
     * igual al parametro otorgado
     *
     * @param id id de la empresa que se desea buscar.
     *
     * @return Cantidad {@Link java.lang.Integer} de vacantes que ha generado una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public Integer getNVacantesEmpresa(Long id) {
        return empresaRepository.calcularVacantesTotalesOfertasEmpresa(id);
    }

    /**
     * Retorna la cantidad {@Link java.lang.Integer} total de postulantes a ofertas laborales que ha tenido la empresa con id
     * igual al parametro otorgado
     *
     * @param id id de la empresa que se desea buscar.
     *
     * @return Cantidad {@Link java.lang.Integer} de postulantes que ha tenido una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public Integer getNPostulantesEmpresa(Long id) { return empresaRepository.calcularPostulantesTotalesOfertasEmpresa(id); }

    /**
     * Retorna la {@Link java.util.Date} de la primera oferta que realizo la empresa con id
     * igual al parametro otorgado
     *
     * @param id id de la empresa que se desea buscar.
     *
     * @return {@Link java.util.Date} de la primera oferta que publico una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public Date getFechaPrimeraOfertaEmpresa(Long id) {
        return empresaRepository.fechaPrimeraOfertaEmpresa(id);
    }

    /**
     * Retorna la {@Link java.util.Date} de la ultima oferta que realizo la empresa con id
     * igual al parametro otorgado
     *
     * @param id id de la empresa que se desea buscar.
     *
     * @return {@Link java.util.Date} de la ultima oferta que publico una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public Date getFechaUltimaOfertaEmpresa(Long id) {
        return empresaRepository.fechaUltimaOfertaEmpresa(id);
    }

    /**
     * Retorna  una {@Link java.util.List} con los contactos activos (que tienen codigo de tipo de vigencia 1)
     * que tienen relacion con la {@Link crm.entities.Empresa} que tiene el id del parametro otorgado (los contactos se obtienen
     * a partir de la clase {@Link crm.entities.UsuarioUsmempleoEmpresa}).
     *
     * @param id id de la empresa que se desea buscar.
     *
     * @return Coleccion {@Link.util.List} de {@Link crm.entities.UsuarioUsmempleoEmpresa} activos que tienen relacion con la empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public List<UsuarioUsmempleoEmpresa> getContactosActivosEmpresa(Long id) {
        return usuarioUsmempleoEmpresaRepository.getListaContactosVigentesEmpresa(id);
    }

    /**
     * Retorna  una {@Link java.util.List} con los contactos no activos (que no tienen codigo de tipo de vigencia 1)
     * que tienen relacion con la {@Link crm.entities.Empresa} que tiene el id del parametro otorgado (los contactos se obtienen
     * a partir de la clase {@Link crm.entities.UsuarioUsmempleoEmpresa}).
     *
     * @param id id de la empresa que se desea buscar.
     *
     * @return Coleccion {@Link.util.List} de {@Link crm.entities.UsuarioUsmempleoEmpresa} no activos que tienen relacion con la empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public List<UsuarioUsmempleoEmpresa> getContactosInactivosEmpresa(Long id) {
        return usuarioUsmempleoEmpresaRepository.getListaContactosNoVigentesEmpresa(id);
    }

    /**
     * Retorna  una {@Link java.util.List} con todos los registros que existen en la base de datos
     * de la entidad {@Link ContactoHistoricoEmpresaPersonaParticipante} y que tienen relacion con la
     * {@Link crm.entities.Empresa} que tiene el id del parametro otorgado.
     *
     * @param id id de la empresa que se desea buscar.
     *
     * @return Coleccion {@Link.util.List} de {@Link ContactoHistoricoEmpresaPersonaParticipante} que tienen relacion con la empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public List<ContactoHistoricoEmpresaPersonaParticipante> getContactoHistoricoConEmpresa(Long id) {
        List<ContactoHistoricoEmpresaPersonaParticipante> contactoHistorico = contactoHistoricoEmpresaPersonaParticipanteRepository.getContactoHistoricoEmpresaPersonaParticipante(id);
        if(contactoHistorico.size()==0) return contactoHistorico;
        List<Usuario> U = contactoHistoricoEmpresaPersonaParticipanteRepository.getUsuarioContactoHistoricoEmpresaPersonaParticipante(id);
        List<UsuarioEmpresaUsmempleo> UEU = contactoHistoricoEmpresaPersonaParticipanteRepository.getUsuarioEmpresaUsmempleoContactoHistoricoEmpresaPersonaParticipante(id);
        ContactoHistoricoEmpresaPersonaParticipante cp;
        UsuarioUsmempleoEmpresa uue = new UsuarioUsmempleoEmpresa();
        ContactoHistoricoEmpresa che = new ContactoHistoricoEmpresa();
        for(int i=0;i==contactoHistorico.size();i++){
            cp = contactoHistorico.get(i);
            uue.setUsuarioEmpresaUsmempleo(UEU.get(i));
            che.setUsuario(U.get(i));
            cp.setUsuarioUsmempleoEmpresa(uue);
            cp.setContactoHistoricoEmpresa(che);
            contactoHistorico.set(i,cp);
        }
        return contactoHistorico;
    }

    /**
     * Retorna  una {@Link java.util.List} con todos los exalumnos que tienen algun tipo de
     * relacion con la {@Link crm.entities.Empresa} que tiene el id del parametro otorgado.
     *
     * @param id id de la empresa que se desea buscar.
     *
     * @return Coleccion {@Link.util.List} de los exalumnos que tienen algun tipo de actividad en la empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public List<ActividadExalumno> getExalumnosYSocios(Long id) {
        List<ActividadExalumno> act = actividadExalumnoRepository.getActividadExalumnosEmpresa(id);
        ActividadExalumno temp;
        for(Integer i=0;i<act.size();i++){
            temp = act.get(i);
            temp.setCantidadCompromisos(actividadExalumnoRepository.getCompromisosVigentes(temp.getUsuario().getId()));
            act.set(i,temp);
        }
        return act;
    }

    /**
     * Retorna  una {@Link java.util.List} con todos los exalumnos que tienen algun tipo de
     * relacion con la {@Link crm.entities.Empresa} que tiene el id del parametro otorgado.
     *
     * @param id id de la empresa que se desea buscar.
     *
     * @return Coleccion {@Link.util.List} de los exalumnos que tienen algun tipo de actividad en la empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public List<ActividadExalumno> getExalumnosYSociosPrimeros20(Long id) {
        PageRequest pageRequest = new PageRequest(0,20);
        Page<ActividadExalumno> actPage = actividadExalumnoRepository.getActividadExalumnosEmpresaPrimeros20(id, pageRequest);
        List<ActividadExalumno> act = actPage.getContent();
        List<ActividadExalumno> resultado = new ArrayList<>();
        ActividadExalumno temp;
        for(Integer i = 0; i<act.size();i++){
            temp = act.get(i);
            temp.setCantidadCompromisos(actividadExalumnoRepository.getCompromisosVigentes(temp.getUsuario().getId()));
            temp.setRemuneracion((int) actPage.getTotalElements());
            resultado.add(temp);
        }
        return resultado;
    }

    /**
     * Retorna  un {@Link java.util.ArrayList} de {@Link java.util.ArrayList} con el resumen de ofertas laborales que ha publicado
     * la empresa que tiene el id del parametro otorgado para cada mes de cada año, y la suma total de ofertas por meses y por años.
     * El metodo toma una lista con los años en los cuales la empresa ha realizado publicaciones, calcula la suma de ofertas de cada mes
     * de los años que se encuentran en la lista y los almacena en listas individuales las cuales son almacenadas posteriormente en otra lista.
     * El resultado final es una lista de listas, donde la primera lista contiene como primer item el total de ofertas de la empresa y luego
     * la suma total de ofertas para cada mes (suma total de ofertas realizadas en enero, febrero, etc.) y las demas listas contienen las
     * ofertas realizadas en cada año, e.g. para la lista del año 2012 el primer item seria la suma total de ofertas en ese año y los siguientes
     * elementos serian la suma de ofertas por mes de ese año (enero, febrero, etc.).
     *
     * @param id id de la empresa.
     *
     * @return Coleccion {@Link.util.ArrayList} de {@Link.util.ArrayList} con el resumen de ofertas laborales de una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public ArrayList<ArrayList<Integer>> getResumenOfertasEmpresas(Long id) {
        List<Double> anios = ofertaLaboralUsmempleoRepository.getListaAniosOfertasEmpresas(id);
        ArrayList<ArrayList<Integer>> resumen = new ArrayList<>();
        ArrayList<Integer> resumenAnio = new ArrayList<>();
        if(anios.size()== 0) return resumen;
        for(int i=0;i<anios.size()+1;i++){
            if(i==0){
                for(int j=0;j<14;j++){
                    if(j==0){
                        resumenAnio.add(-1);
                    }
                    else if(j==1){
                        resumenAnio.add(ofertaLaboralUsmempleoRepository.getSumaTotalOfertasEmpresa(id));
                    }
                    else{
                        resumenAnio.add(ofertaLaboralUsmempleoRepository.getSumaOfertasMesEmpresa(id,j-1));
                    }
                }
                resumen.add(i,resumenAnio);
                resumenAnio = new ArrayList<>();
            }
            else{
                for(Integer j=0;j<14;j++){
                    if(anios.get(i-1)!= null) {
                        if (j == 0) {
                            resumenAnio.add(anios.get(i - 1).intValue());
                        } else if (j == 1) {
                            resumenAnio.add(ofertaLaboralUsmempleoRepository.getSumaOfertasAnioEmpresa(id, anios.get(i - 1)));
                        } else {
                            resumenAnio.add(ofertaLaboralUsmempleoRepository.getSumaOfertasMesAnioEmpresa(id, anios.get(i - 1), j - 1));
                        }
                    }
                }
                resumen.add(i,resumenAnio);
                resumenAnio = new ArrayList<>();
            }
        }
        return resumen;
    }

    /**
     * Retorna  una {@Link java.util.List} con todas las {@Link crm.entities.OfertaLaboralUsmempleo} de la {@Link crm.entities.Empresa}
     * que tiene el id del parametro otorgado.
     *
     * @param id id de empresa que se desean buscar.
     *
     * @return Coleccion {@Link.util.List} de las {@Link crm.entities.OfertaLaboralUsmempleo} de una {@Link crm.entities.Empresa}
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public List<OfertaLaboralUsmempleo> getOfertasLaboralesEmpresaDetalle(Long id){
        return ofertaLaboralUsmempleoRepository.getOfertasLaboralesEmpresaDetalle(id);
    }

    /**
     * Retorna  una {@Link java.util.List} con las primeras 20 {@Link crm.entities.OfertaLaboralUsmempleo} de la {@Link crm.entities.Empresa}
     * que tiene el id del parametro otorgado.
     *
     * @param id id de empresa que se desean buscar.
     *
     * @return Coleccion {@Link.util.List} de las primeras 20 {@Link crm.entities.OfertaLaboralUsmempleo} de una {@Link crm.entities.Empresa}
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public List<OfertaLaboralUsmempleo> getOfertasLaboralesEmpresaDetallePrimeras20(Long id){
        PageRequest pageRequest = new PageRequest(0,20);
        Page<OfertaLaboralUsmempleo> ofelabPaged =ofertaLaboralUsmempleoRepository.getOfertasLaboralesEmpresaDetallePrimeras20(id,pageRequest);
        List<OfertaLaboralUsmempleo> resultado = ofelabPaged.getContent();
        if(resultado.size()!=0)
        resultado.get(0).setEstadoMailing((int)ofelabPaged.getTotalElements());
        return resultado;
    }

    /**
     * Retorna  una {@Link java.util.List} con todas las {@Link crm.entities.SucursalEmpresa} de la {@Link crm.entities.Empresa}
     * que tiene el id del parametro otorgado.
     *
     * @param id id de empresa que se desean buscar.
     *
     * @return Coleccion {@Link.util.List} de las {@Link crm.entities.SucursalEmpresa} de una {@Link crm.entities.Empresa}
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public List<SucursalEmpresa> getSucursalesEmpresa(Long id){
        List<SucursalEmpresa> sucursales = sucursalEmpresaRepository.buscarSucursalesEmpresaByIdEmpresa(id);
        SucursalEmpresa suc;
        Comuna comuna = new Comuna();
        Provincia provincia = new Provincia();
        for(Integer i=0;i<sucursales.size();i++){
            suc = sucursales.get(i);
            if(suc.getComuna() != null) {
                provincia.setRegion(sucursalEmpresaRepository.getRegionComuna(suc.getComuna().getCodigo()));
                comuna.setProvincia(provincia);
                suc.setComuna(comuna);
            }else{
                provincia.setRegion(new Region());
                comuna.setProvincia(provincia);
                suc.setComuna(comuna);
            }
            suc.setCantidadOfertas(sucursalEmpresaRepository.getCantidadOfertasSucursal(id, suc.getSucursalCodigo()));
            suc.setCantidadUsuarios(sucursalEmpresaRepository.getCantidadExalumnosSucursal(suc.getSucursalCodigo()));
            suc.setCantidadPublicadores(sucursalEmpresaRepository.getCantidadContactosSucursal(suc.getSucursalCodigo()));
            sucursales.set(i,suc);
            comuna = new Comuna();
            provincia= new Provincia();
        }
        return sucursales;
    }

    /**
     * Retorna  un {@Link java.util.ArrayList} con la cantidad {@Link java.lang.Integer} de ofertas laborales que ha publicado
     * cada sucursal de la empresa que tiene el id del parametro otorgado (relacion tomada desde la entidad {@Link crm.entities.OfertaLaboralUsmempleo}).
     * El metodo toma la lista de todas las {@Link crm.entities.SucursalEmpresa} de la {@Link crm.entities.Empresa} con id
     * otorgado como parametro y para cada sucursal de la lista calcula la cantidad de ofertas laborales publicadas y lo almacena en el
     * arraylist ofertasSucursales en el mismo indice de la sucursal correspondiente.
     *
     * @param id id de la empresa.
     *
     * @return Coleccion {@Link.util.ArrayList} de la cantidad {@link java.lang.Integer} de ofertas laborales por sucursal de una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public ArrayList<Integer> getCantidadOfertasSucursalesEmpresa(Long id){
        List<SucursalEmpresa> sucursales = sucursalEmpresaRepository.buscarSucursalesEmpresaByIdEmpresa(id);
        ArrayList<Integer> ofertasSucursales = new ArrayList<>();
        for(Integer i=0;i<sucursales.size();i++){
            ofertasSucursales.add(sucursalEmpresaRepository.getCantidadOfertasSucursal(id, sucursales.get(i).getSucursalCodigo()));
        }
        return ofertasSucursales;
    }


    /**
     * Retorna  un {@Link java.util.ArrayList} con la cantidad {@Link java.lang.Integer} de contactos que tienen alguna relacion
     * con las sucursales de la empresa que tiene el id del parametro otorgado (relacion tomada desde la entidad {@Link crm.entities.UsuarioEmpresaUsmempleo}).
     * El metodo toma la lista de todas las {@Link crm.entities.SucursalEmpresa} de la {@Link crm.entities.Empresa} con id
     * otorgado como parametro y para cada sucursal de la lista calcula la cantidad de contactos y lo almacena en el
     * arraylist contactosSucursales en el mismo indice de la sucursal correspondiente. El ultimo valor almacenado
     * en la lista es la suma total de los contactos por sucursal.
     *
     * @param id id de la empresa.
     *
     * @return Coleccion {@Link.util.ArrayList} de la cantidad {@link java.lang.Integer} de contactos por sucursal de una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public ArrayList<Integer> getCantidadContactosSucursalesEmpresa(Long id){
        List<SucursalEmpresa> sucursales = sucursalEmpresaRepository.buscarSucursalesEmpresaByIdEmpresa(id);
        ArrayList<Integer> contactosSucursales = new ArrayList<>();
        Integer suma = 0;
        for(Integer i=0;i<sucursales.size();i++){
            contactosSucursales.add(sucursalEmpresaRepository.getCantidadContactosSucursal(sucursales.get(i).getSucursalCodigo()));
            suma = suma + contactosSucursales.get(i);
        }
        contactosSucursales.add(suma);
        return contactosSucursales;
    }

    /**
     * Retorna  un {@Link java.util.ArrayList} con la cantidad {@Link java.lang.Integer} de exalumnos que tienen alguna relacion
     * con las sucursales de la empresa que tiene el id del parametro otorgado (relacion tomada desde la entidad {@Link crm.entities.ActividadExalumno}).
     * El metodo toma la lista de todas las {@Link crm.entities.SucursalEmpresa} de la {@Link crm.entities.Empresa} con id
     * otorgado como parametro y para cada sucursal de la lista calcula la cantidad de exalumnos y lo almacena en el
     * arraylist exalumnosSucursales en el mismo indice de la sucursal correspondiente. El ultimo valor almacenado
     * en la lista es la suma total de los exalumnos por sucursal.
     *
     * @param id id de la empresa.
     *
     * @return Coleccion {@Link.util.ArrayList} de la cantidad {@link java.lang.Integer} de exalumnos por sucursal de una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public ArrayList<Integer> getCantidadExalumnosSucursalesEmpresa(Long id){
        List<SucursalEmpresa> sucursales = sucursalEmpresaRepository.buscarSucursalesEmpresaByIdEmpresa(id);
        ArrayList<Integer> exalumnosSucursales = new ArrayList<>();
        Integer suma = 0;
        for(Integer i=0;i<sucursales.size();i++){
            exalumnosSucursales.add(sucursalEmpresaRepository.getCantidadExalumnosSucursal(sucursales.get(i).getSucursalCodigo()));
            suma = suma + exalumnosSucursales.get(i);
        }
        exalumnosSucursales.add(suma);
        return exalumnosSucursales;
    }

    /**
     * Retorna un objeto {@link crm.entities.Empresa} que posea una Razon Social, nombre de fantasia o sigla
     * identico al parametro pasado.
     *
     * @param nombreEmpresa nombre de empresa que se desean buscar.
     *
     * @return Objeto {@link crm.entities.Empresa}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public Empresa buscarEmpresaEspecificaPorRazonSocialONombreFantasiaOSigla(String nombreEmpresa) {
        return empresaRepository.buscarEmpresaEspecificaPorRazonSocialONombreFantasiaOSigla(nombreEmpresa).get(0);
    }

    /**
     * Actualiza la {@link crm.entities.Empresa} que se pase como parametro en la base de datos
     *
     * @param empresa objeto empresa que se desea actualizar.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     *
     */
    public void saveEmpresa(Empresa empresa) {
        Empresa antiguo = empresaRepository.findById(empresa.getId());

        if ((antiguo.getNombreFantasiaEmpresa() == null) || (antiguo.getNombreFantasiaEmpresa()!= null && empresa.getNombreFantasiaEmpresa().compareTo(antiguo.getNombreFantasiaEmpresa()) != 0))
            antiguo.setNombreFantasiaEmpresa(empresa.getNombreFantasiaEmpresa());

        if ((antiguo.getRazonSocial() == null) || (antiguo.getRazonSocial()!= null && empresa.getRazonSocial().compareTo(antiguo.getRazonSocial()) != 0))
            antiguo.setRazonSocial(empresa.getRazonSocial());

        if ((antiguo.getSigla() == null) || (antiguo.getSigla()!= null && empresa.getSigla().compareTo(antiguo.getSigla()) != 0))
            antiguo.setSigla(empresa.getSigla());

        if ((antiguo.getGiroEmpresa() == null) || (antiguo.getGiroEmpresa()!= null && empresa.getGiroEmpresa().compareTo(antiguo.getGiroEmpresa()) != 0))
            antiguo.setGiroEmpresa(empresa.getGiroEmpresa());

        if ((antiguo.getUrl() == null) || (antiguo.getUrl()!= null && empresa.getUrl().compareTo(antiguo.getUrl()) != 0))
            antiguo.setUrl(empresa.getUrl());

        if((antiguo.getDescripcion() == null) || (antiguo.getDescripcion()!= null && empresa.getDescripcion().compareTo(antiguo.getDescripcion()) != 0))
            antiguo.setDescripcion(empresa.getDescripcion());

        if (empresa.getSector().getCodigo().compareTo(antiguo.getSector().getCodigo()) != 0) {
            TipoSector nuevoSector = sectorRepository.findOne(empresa.getSector().getCodigo());
            antiguo.setSector(nuevoSector);
        }

        if(empresa.getVigencia().getCodVigencia().compareTo(antiguo.getVigencia().getCodVigencia()) != 0){
            antiguo.setVigencia(tipoVigenciaRepository.findByCodVigencia(empresa.getVigencia().getCodVigencia()));
        }

        if (empresa.getNivelFacturacion().getCodNivelFacturacion().compareTo(antiguo.getNivelFacturacion().getCodNivelFacturacion()) != 0) {
            TipoNivelFacturacion nuevoNivelFacturacion = nivelFacturacionRepository.findOne(empresa.getNivelFacturacion().getCodNivelFacturacion());
            antiguo.setNivelFacturacion(nuevoNivelFacturacion);
        }

        if ((antiguo.getNumEmpleados() == null) || (antiguo.getNumEmpleados()!= null && empresa.getNumEmpleados()!= null && empresa.getNumEmpleados().compareTo(antiguo.getNumEmpleados()) != 0))
            antiguo.setNumEmpleados(empresa.getNumEmpleados());

        if ((antiguo.getNumContratAnu() == null) || (antiguo.getNumContratAnu()!= null && empresa.getNumContratAnu()!= null && empresa.getNumContratAnu().compareTo(antiguo.getNumContratAnu()) != 0))
            antiguo.setNumContratAnu(empresa.getNumContratAnu());

        if ((antiguo.getRutEmpresa() == null) || (antiguo.getRutEmpresa()!= null && empresa.getRutEmpresa() != null && empresa.getRutEmpresa().compareTo(antiguo.getRutEmpresa()) != 0)) {
            if(empresa.getRutEmpresa().compareTo("") == 0) antiguo.setRutEmpresa(null);
            else antiguo.setRutEmpresa(empresa.getRutEmpresa());
        }

        if ((antiguo.getIdEmpresaExtranjera() == null) || (antiguo.getIdEmpresaExtranjera()!= null && empresa.getIdEmpresaExtranjera() != null && empresa.getIdEmpresaExtranjera().compareTo(antiguo.getIdEmpresaExtranjera()) != 0)) {
            if(empresa.getIdEmpresaExtranjera().compareTo("") == 0) antiguo.setIdEmpresaExtranjera(null);
            else antiguo.setIdEmpresaExtranjera(empresa.getIdEmpresaExtranjera());
        }

        if (empresa.getPais().getCodigo().compareTo(antiguo.getPais().getCodigo()) != 0) {
            Pais pais = paisRepository.findById(empresa.getPais().getCodigo());
            antiguo.setPais(pais);
        }

        if(antiguo.getPremiumEmpresa() != empresa.getPremiumEmpresa())
            antiguo.setPremiumEmpresa(empresa.getPremiumEmpresa());

        if(antiguo.getHeadHunter() != empresa.getHeadHunter())
            antiguo.setHeadHunter(empresa.getHeadHunter());

        if(antiguo.getBoletinExalumnos() != empresa.getBoletinExalumnos())
            antiguo.setBoletinExalumnos(empresa.getBoletinExalumnos());

        empresaRepository.save(antiguo);
    }

    /**
     * Obtiene la lista de todas las empresas.
     *
     * @return {@link java.util.Iterator} de {@link crm.entities.Empresa}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public List<Empresa> getEmpresasPorPais(Short id) {
        return empresaRepository.findByidPais(id);
    }

    /**
     * Guarda una entidad {@Link crm.entities.Empresa} nueva en la base de datos
     *
     * @param empresa empresa que se quiere guardar en la base de datos
     * @return La {@Link crm.entities.Empresa} recien creada
     * @author ignacio oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public Empresa guardarEmpresa(Empresa empresa) {
        Empresa e = empresaRepository.save(empresa);
        return e;
    }

    /**
     * Retorna la cantidad de sucursales que tiene una empresa en particular
     *
     * @param id Identificador de la empresa
     * @return Cantidad {@Link java.lang.Integer} de sucursales de una empresa
     */
    public Integer getCantidadSucursalesEmpresa(Long id) {
        return empresaRepository.calcularCantidadSucursales(id);
    }

    /**
     * Retorna un objeto {@Link java.utils.Iterable} con todas las empresas que hay en la base de datos.
     *
     * @return Coleccion {@Link java.utils.Iterable} de empresas.
     */
    //@PostFilter("hasPermission(filterObject,'Leer')")
    public Iterable<Empresa> getEmpresas(){
        return empresaRepository.findAll();
    }

    public List<SucursalEmpresa> getSucursalesEmpresaByNombre(String nombre){
        Empresa emp = empresaRepository.buscarEmpresaEspecificaPorRazonSocialONombreFantasiaOSigla(nombre).size() > 0 ? empresaRepository.buscarEmpresaEspecificaPorRazonSocialONombreFantasiaOSigla(nombre).get(0) : null;
        if(emp != null)return sucursalEmpresaRepository.buscarSucursalesEmpresaByIdEmpresa(emp.getId());
        else return null;
    }

    /**
     * Guarda una empresa y una de sus sucursales en la base de datos
     *
     * @param empresa
     * @param sucursalEmpresa
     * @return
     */
    public Empresa registrarEmpresaYSucursal(Empresa empresa, SucursalEmpresa sucursalEmpresa) {
        Date actual = new Date();
        empresa.setEstado(true);
        empresa.setFechaModificacion(actual);
        if(empresa.getRutEmpresa().compareTo("") == 0){
            empresa.setRutEmpresa(null);
        } else if(empresa.getIdEmpresaExtranjera().compareTo("")==0) {
            empresa.setIdEmpresaExtranjera(null);
        }
        empresa.setRutUsuario(usuarioService.getCurrentUser().getRut());
        empresa = empresaRepository.save(empresa);

        sucursalEmpresa.setEmpresa(empresa);
        sucursalEmpresa.setFechaModificacion(actual);
        sucursalEmpresa.setRutUsuario(usuarioService.getCurrentUser().getRut());
        if (CelularParser.esValido(sucursalEmpresa.getSucFono())) {
            sucursalEmpresa.setSucFono(CelularParser.parse(sucursalEmpresa.getSucFono()));
        }
        sucursalEmpresaRepository.save(sucursalEmpresa);

        return empresa;
    }

    public Page<Empresa> buscarEmpresasCreadasPorUsuarioAexaPorValidar(String criterio, String rut, String idExtranjero, String nombre, Integer numeroPagina) {
        PageRequest pageRequest = new PageRequest(numeroPagina, 15);
        if((criterio.compareTo("rut") == 0 && rut.compareTo("") != 0) || (criterio.compareTo("rut") == 0 && idExtranjero.compareTo("") != 0)) return empresaRepository.buscarEmpresasCreadasPorUsuarioAexaPorValidarPorRut(rut, idExtranjero, pageRequest);
        else if (criterio.compareTo("nombre") == 0 && nombre.compareTo("") != 0) return empresaRepository.buscarEmpresasCreadasPorUsuarioAexaPorValidarPorNombre(nombre, pageRequest);
        else return empresaRepository.buscarEmpresasCreadasPorUsuarioAexaPorValidar(pageRequest);
    }

    public Page<Empresa> buscarEmpresasCreadasPorUsuarioEmpresaUsmempleoPorValidar(String criterio, String rut, String idExtranjero, String nombre, Integer numeroPagina) {
        PageRequest pageRequest = new PageRequest(numeroPagina, 15);
        if((criterio.compareTo("rut") == 0 && rut.compareTo("") != 0) || (criterio.compareTo("rut") == 0 && idExtranjero.compareTo("") != 0)) return empresaRepository.buscarEmpresasCreadasPorUsuarioEmpresaUsmempleoPorValidarPorRut(rut, idExtranjero, pageRequest);
        else if (criterio.compareTo("nombre") == 0 && nombre.compareTo("") != 0) return empresaRepository.buscarEmpresasCreadasPorUsuarioEmpresaUsmempleoPorValidarPorNombre(nombre, pageRequest);
        else return empresaRepository.buscarEmpresasCreadasPorUsuarioEmpresaUsmempleoPorValidar(pageRequest);
    }

    public Page<Empresa> buscarEmpresasConSucursalesCreadasPorUsuarioAexaPorValidar(String criterio, String rut, String idExtranjero, String nombre, Integer numeroPagina) {
        PageRequest pageRequest = new PageRequest(numeroPagina, 15);
        Page<Empresa> empresas;
        if((criterio.compareTo("rut") == 0 && rut.compareTo("") != 0) || (criterio.compareTo("rut") == 0 && idExtranjero.compareTo("") != 0)) empresas = empresaRepository.buscarEmpresasConSucursalesCreadasPorUsuarioAexaPorValidarPorRut(rut, idExtranjero, pageRequest);
        else if (criterio.compareTo("nombre") == 0 && nombre.compareTo("") != 0) empresas = empresaRepository.buscarEmpresasConSucursalesCreadasPorUsuarioAexaPorValidarPorNombre(nombre, pageRequest);
        else empresas = empresaRepository.buscarEmpresasConSucursalesCreadasPorUsuarioAexaPorValidar(pageRequest);
        for(int i=0;i<empresas.getContent().size();i++){
            empresas.getContent().get(i).setSucursalesPorValidar(sucursalEmpresaRepository.buscarSucursalesEmpresaByIdEmpresaPorValidar(empresas.getContent().get(i).getId()));
            if(empresas.getContent().get(i).getSucursalesPorValidar() == null){
                empresas.getContent().get(i).setCantidadSucursalesPorValidar(0);
            }
            empresas.getContent().get(i).setCantidadSucursalesPorValidar(empresas.getContent().get(i).getSucursalesPorValidar().size());
        }
        return empresas;
    }

    public Page<Empresa> buscarEmpresasConSucursalesCreadasPorUsuarioEmpresaUsmempleoPorValidar(String criterio, String rut, String idExtranjero, String nombre, Integer numeroPagina) {
        PageRequest pageRequest = new PageRequest(numeroPagina, 15);
        Page<Empresa> empresas;
        if((criterio.compareTo("rut") == 0 && rut.compareTo("") != 0) || (criterio.compareTo("rut") == 0 && idExtranjero.compareTo("") != 0)) empresas = empresaRepository.buscarEmpresasConSucursalesCreadasPorUsuarioEmpresaUsmempleoPorValidarPorRut(rut, idExtranjero, pageRequest);
        else if (criterio.compareTo("nombre") == 0 && nombre.compareTo("") != 0) empresas = empresaRepository.buscarEmpresasConSucursalesCreadasPorUsuarioEmpresaUsmempleoPorValidarPorNombre(nombre, pageRequest);
        else empresas = empresaRepository.buscarEmpresasConSucursalesCreadasPorUsuarioEmpresaUsmempleoPorValidar(pageRequest);
        for(int i=0;i<empresas.getContent().size();i++){
            empresas.getContent().get(i).setSucursalesPorValidar(sucursalEmpresaRepository.buscarSucursalesEmpresaByIdEmpresaPorValidar(empresas.getContent().get(i).getId()));
            if(empresas.getContent().get(i).getSucursalesPorValidar() == null){
                empresas.getContent().get(i).setCantidadSucursalesPorValidar(0);
            }
            empresas.getContent().get(i).setCantidadSucursalesPorValidar(empresas.getContent().get(i).getSucursalesPorValidar().size());
        }
        return empresas;
    }

    public void rechazarEmpresa(Empresa empresaCorregir) {

        List<UsuarioUsmempleoEmpresa> listaUsuariosUsmempleoEmpresa = usuarioUsmempleoEmpresaRepository.buscarUsuariosUsmempleoEmpresaPorIdEmpresa(empresaCorregir.getId());
        for(int i=0;i<listaUsuariosUsmempleoEmpresa.size();i++){
            listaUsuariosUsmempleoEmpresa.get(i).setEmpresa(empresaRepository.buscarEmpresaPorRut(String.valueOf(0)));
            listaUsuariosUsmempleoEmpresa.get(i).setSucursalEmpresa(sucursalEmpresaRepository.buscarSucursalesEmpresaByIdEmpresa(listaUsuariosUsmempleoEmpresa.get(i).getId()).get(0));
            listaUsuariosUsmempleoEmpresa.get(i).setFechaModificacion(new Date());
            listaUsuariosUsmempleoEmpresa.get(i).setRutUsuario(usuarioService.getCurrentUser().getRut());
            listaUsuariosUsmempleoEmpresa.get(i).setVigencia(new TipoVigencia(TipoVigencia.ID_NO_VIGENTE, TipoVigencia.TIPO_NO_VIGENTE));
            usuarioUsmempleoEmpresaRepository.save(listaUsuariosUsmempleoEmpresa.get(i));
        }

        List<UsuarioVistoUsmEmpleo> listaUsuarioVistoUsmempleo = usuarioVistoUsmempleoRepository.buscarUsuarioVistoUsmempleoPorIdEmpresa(empresaCorregir.getId());
        for(int i=0;i<listaUsuarioVistoUsmempleo.size();i++){
            usuarioVistoUsmempleoRepository.delete(listaUsuarioVistoUsmempleo.get(i));
        }

        List<OfertaLaboralUsmempleo> listaOfertasLaboralesUsmempleo = ofertaLaboralUsmempleoRepository.buscarPorIdEmpresa(empresaCorregir.getId());
        for(int i=0;i<listaOfertasLaboralesUsmempleo.size();i++){
            ofertaLaboralUsmempleoRepository.delete(listaOfertasLaboralesUsmempleo.get(i));
        }

        List<ActividadExalumno> listaActividadesExalumno = actividadExalumnoRepository.getActividadExalumnosEmpresa(empresaCorregir.getId());
        for(int i=0;i<listaActividadesExalumno.size();i++){
            actividadExalumnoRepository.delete(listaActividadesExalumno.get(i));
        }

        List<DuenoEmpresa> listaDuenosEmpresa = duenoEmpresaRepository.buscarPorIdEmpresa(empresaCorregir.getId());
        for(int i=0;i<listaDuenosEmpresa.size();i++){
            duenoEmpresaRepository.delete(listaDuenosEmpresa.get(i));
        }

        List<SucursalEmpresa> listaSucursalesCorregir = sucursalEmpresaRepository.buscarSucursalesEmpresaByIdEmpresa(empresaCorregir.getId());
        for(int i=0;i<listaSucursalesCorregir.size();i++){
            sucursalEmpresaRepository.delete(listaSucursalesCorregir.get(i));
        }
        empresaRepository.delete(empresaCorregir);
    }

    public void corregirEmpresa(Empresa empresaReasignar, SucursalEmpresa sucursalReasignar, Empresa empresaCorregir) {
            List<UsuarioUsmempleoEmpresa> listaUsuariosUsmempleoEmpresa = usuarioUsmempleoEmpresaRepository.buscarUsuariosUsmempleoEmpresaPorIdEmpresa(empresaCorregir.getId());
            for (int i = 0; i < listaUsuariosUsmempleoEmpresa.size(); i++) {
                listaUsuariosUsmempleoEmpresa.get(i).setEmpresa(empresaReasignar);
                listaUsuariosUsmempleoEmpresa.get(i).setSucursalEmpresa(sucursalReasignar);
                listaUsuariosUsmempleoEmpresa.get(i).setFechaModificacion(new Date());
                listaUsuariosUsmempleoEmpresa.get(i).setRutUsuario(usuarioService.getCurrentUser().getRut());
                usuarioUsmempleoEmpresaRepository.save(listaUsuariosUsmempleoEmpresa.get(i));
            }

            List<UsuarioVistoUsmEmpleo> listaUsuarioVistoUsmempleo = usuarioVistoUsmempleoRepository.buscarUsuarioVistoUsmempleoPorIdEmpresa(empresaCorregir.getId());
            for (int i = 0; i < listaUsuarioVistoUsmempleo.size(); i++) {
                listaUsuarioVistoUsmempleo.get(i).setEmpresa(empresaReasignar);
                listaUsuarioVistoUsmempleo.get(i).setIdEmpresa(empresaReasignar.getId());
                listaUsuarioVistoUsmempleo.get(i).setFechaModificacion(new Date());
                listaUsuarioVistoUsmempleo.get(i).setRutUsuario(usuarioService.getCurrentUser().getRut());
                usuarioVistoUsmempleoRepository.save(listaUsuarioVistoUsmempleo.get(i));
            }

            List<OfertaLaboralUsmempleo> listaOfertasLaboralesUsmempleo = ofertaLaboralUsmempleoRepository.buscarPorIdEmpresa(empresaCorregir.getId());
            for (int i = 0; i < listaOfertasLaboralesUsmempleo.size(); i++) {
                listaOfertasLaboralesUsmempleo.get(i).setEmpresa(empresaReasignar);
                listaOfertasLaboralesUsmempleo.get(i).setFechaModificacion(new Date());
                listaOfertasLaboralesUsmempleo.get(i).setRutUsuario(usuarioService.getCurrentUser().getRut());
                ofertaLaboralUsmempleoRepository.save(listaOfertasLaboralesUsmempleo.get(i));
            }

            List<ActividadExalumno> listaActividadesExalumno = actividadExalumnoRepository.getActividadExalumnosEmpresa(empresaCorregir.getId());
            for (int i = 0; i < listaActividadesExalumno.size(); i++) {
                listaActividadesExalumno.get(i).setEmpresa(empresaReasignar);
                listaActividadesExalumno.get(i).setSucursalEmpresa(sucursalReasignar);
                listaActividadesExalumno.get(i).setFechaModificacion(new Date());
                listaActividadesExalumno.get(i).setRutUsuario(usuarioService.getCurrentUser().getRut());
                actividadExalumnoRepository.save(listaActividadesExalumno.get(i));
            }

            List<DuenoEmpresa> listaDuenosEmpresa = duenoEmpresaRepository.buscarPorIdEmpresa(empresaCorregir.getId());
            for (int i = 0; i < listaDuenosEmpresa.size(); i++) {
                listaDuenosEmpresa.get(i).setEmpresa(empresaReasignar);
                listaDuenosEmpresa.get(i).setEmpresaId(empresaReasignar.getId());
                listaDuenosEmpresa.get(i).setFechaModificacion(new Date());
                listaDuenosEmpresa.get(i).setRutUsuario(usuarioService.getCurrentUser().getRut());
                duenoEmpresaRepository.save(listaDuenosEmpresa.get(i));
            }

            List<SucursalEmpresa> listaSucursalesCorregir = sucursalEmpresaRepository.buscarSucursalesEmpresaByIdEmpresa(empresaCorregir.getId());
            for (int i = 0; i < listaSucursalesCorregir.size(); i++) {
                sucursalEmpresaRepository.delete(listaSucursalesCorregir.get(i));
            }
            empresaRepository.delete(empresaCorregir);
    }


    /**
     * Verifica si la empresa contiene datos incompletos o invalidos, de ser asi returna true. En otro caso retorna false.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     * @param id
     * @return
     */
    public boolean verificarDatosInvalidosOIncompletosEmpresa(Long id) {
        Empresa empresa = empresaRepository.findOne(id);
        String PATTERN_RUT = "[0-9]+";
        Pattern pattern = Pattern.compile(PATTERN_RUT);

        if (empresa.getUrl() != null && empresa.getUrl().length() > 200) {
            return true;
        } if (empresa.getDescripcion() != null && empresa.getDescripcion().length() > 255) {
            return true;
        } if(empresa.getSigla() != null && empresa.getSigla().length() > 100) {
            return true;
        } if(empresa.getNumContratAnu() != null && empresa.getNumContratAnu() < 0){
            return true;
        } if (empresa.getNumEmpleados() != null && empresa.getNumEmpleados() < 0) {
            return true;
        }
        if(empresa.getRutEmpresa()!=null && empresa.getIdEmpresaExtranjera()!=null && empresa.getRutEmpresa().isEmpty() && empresa.getIdEmpresaExtranjera().isEmpty()) {
            return true;
        }else if(empresa.getRutEmpresa()!=null && empresa.getIdEmpresaExtranjera()==null && empresa.getRutEmpresa().isEmpty()) {
            return true;
        }
        if(empresa.getRutEmpresa()!=null && !empresa.getRutEmpresa().isEmpty()) {
            Matcher matcher = pattern.matcher(empresa.getRutEmpresa());
            if (!matcher.matches()) {
                return true;
            }
            if (empresa.getRutEmpresa().length() > 10) {
                return true;
            }

        } if (empresa.getGiroEmpresa() != null && empresa.getGiroEmpresa().length() > 500) {
            return true;
        } if (empresa.getRazonSocial() != null && empresa.getRazonSocial().length() > 500) {
            return true;
        } if (empresa.getNombreFantasiaEmpresa() != null && empresa.getNombreFantasiaEmpresa().length() > 500) {
            return true;
        } if(empresa.getRutEmpresa()!=null && empresa.getIdEmpresaExtranjera()!=null && empresa.getRutEmpresa().isEmpty() && empresa.getIdEmpresaExtranjera().isEmpty()) {
            return true;
        }else if(empresa.getRutEmpresa()==null && empresa.getIdEmpresaExtranjera()!=null && empresa.getIdEmpresaExtranjera().isEmpty()){
            return true;
        }
        if(empresa.getIdEmpresaExtranjera()!=null && !empresa.getIdEmpresaExtranjera().isEmpty()) {
            Matcher matcher = pattern.matcher(empresa.getIdEmpresaExtranjera());
            if (!matcher.matches()) {
                return true;
            }
            if (empresa.getIdEmpresaExtranjera().length() > 20) {
                return true;
            }
        }

        if((empresa.getRutEmpresa() == null || empresa.getIdEmpresaExtranjera() == null) || empresa.getPais() == null || empresa.getNombreFantasiaEmpresa() == null || empresa.getRazonSocial() == null
                || empresa.getGiroEmpresa() == null || empresa.getUrl() == null || empresa.getSigla() == null || empresa.getDescripcion() == null
                || empresa.getSector() == null || empresa.getNivelFacturacion() == null || empresa.getHeadHunter() == null || empresa.getPremiumEmpresa() == null
                || empresa.getBoletinExalumnos() == null)
            return true;
        else if((empresa.getRutEmpresa() == "" || empresa.getIdEmpresaExtranjera() == "") || empresa.getNombreFantasiaEmpresa() == "" || empresa.getRazonSocial() == ""
                || empresa.getGiroEmpresa() == "" || empresa.getUrl() == "" || empresa.getSigla() == "" || empresa.getDescripcion() == "")
            return true;
        return false;
    }

    public List<SucursalEmpresa> getSucursalesEmpresaPorValidar(Long idEmpresa) {
        List<SucursalEmpresa> sucursales = sucursalEmpresaRepository.buscarSucursalesEmpresaByIdEmpresaPorValidar(idEmpresa);
        return sucursales;
    }

    public void validarEmpresa(Long idEmpresa) {
        Empresa empresa = empresaRepository.findOne(idEmpresa);
        empresa.setVigencia(new TipoVigencia(TipoVigencia.ID_VIGENTE,TipoVigencia.TIPO_VIGENTE));
        empresaRepository.save(empresa);
    }

    public Empresa getEmpresaByRut(String rutEmpresa) {
        return empresaRepository.buscarEmpresaPorRut(rutEmpresa);
    }

    public Empresa buscarEmpresaPorIdEmpresaExtranjera(String idEmpresaExtranjera) {
            return empresaRepository.buscarEmpresaPorIdEmpresaExtranjera(idEmpresaExtranjera);
    }

    /**
     * Edita los contactos historicos
     *
     * @author Rocío Barramuño <rocio.barramuno@alumnos.usm.cl>
     *
     */
    public ContactoHistoricoEmpresaPersonaParticipante mostrarContactoHistoricoEmpresa(Long idUsuario, Long idContactoEmpresa){
        ContactoHistoricoEmpresaPersonaParticipante listaContactoEmpresaParticipante = contactoHistoricoEmpresaPersonaParticipanteRepository.editarContactoEmpresa(idContactoEmpresa, idUsuario);
        return listaContactoEmpresaParticipante;
    }

    public List<TipoContacto> mostrarTipoContactos() {
        List<TipoContacto> tipoContactos = tipoContactoRepository.findAll();
        return tipoContactos;
    }

    public List<TipoOportunidad> mostrarTipoOportunidad() {
        List<TipoOportunidad> tipoOportunidad = tipoOportunidadRepository.findAll();
        return tipoOportunidad;
    }

    public List<TipoNivelInteres> mostrarTipoNivelInteres() {
        List<TipoNivelInteres> tipoNivelInteres = tipoNivelInteresRepository.findAllByOrderByCodNivelInteresAsc();
        return tipoNivelInteres;
    }

    public void modificarContactoHistoricoEmpresaParticipante(ContactoHistoricoEmpresaPersonaParticipante contacto){
        ContactoHistoricoEmpresaPersonaParticipante original = contactoHistoricoEmpresaPersonaParticipanteRepository
                .findOne(new ContactoHistoricoEmpresaPersonaParticipantePK(contacto.getContactoHistoricoEmpresaId(), contacto.getUsuarioUsmempleoEmpresaId()));

        original.getContactoHistoricoEmpresa().setAsunto(contacto.getContactoHistoricoEmpresa().getAsunto());
        original.getContactoHistoricoEmpresa().setAcuerdos(contacto.getContactoHistoricoEmpresa().getAcuerdos());
        original.getContactoHistoricoEmpresa().setTipoNivelInteres(contacto.getContactoHistoricoEmpresa().getTipoNivelInteres());
        original.getContactoHistoricoEmpresa().setFechaContacto(contacto.getContactoHistoricoEmpresa().getFechaContacto());
        original.getContactoHistoricoEmpresa().setFechaProximoContacto(contacto.getContactoHistoricoEmpresa().getFechaProximoContacto());
        contactoHistoricoEmpresaPersonaParticipanteRepository.save(original);
    }

    @PreAuthorize("hasPermission(#contacto, 'Editar')")
    public void saveContactoHistoricoEmpresaParticipante(ContactoHistoricoEmpresaPersonaParticipante contacto) {
        ContactoHistoricoEmpresaPersonaParticipante antiguo = contactoHistoricoEmpresaPersonaParticipanteRepository.findOne(new ContactoHistoricoEmpresaPersonaParticipantePK(contacto.getContactoHistoricoEmpresaId(), contacto.getUsuarioUsmempleoEmpresaId()));


        if ((antiguo.getContactoHistoricoEmpresa().getAsunto() == null) || (antiguo.getContactoHistoricoEmpresa().getAsunto() != null && contacto.getContactoHistoricoEmpresa().getAsunto().compareTo(antiguo.getContactoHistoricoEmpresa().getAsunto()) != 0))
            antiguo.getContactoHistoricoEmpresa().setAsunto(contacto.getContactoHistoricoEmpresa().getAsunto());

        if ((antiguo.getContactoHistoricoEmpresa().getAcuerdos() == null) || (antiguo.getContactoHistoricoEmpresa().getAcuerdos() != null && contacto.getContactoHistoricoEmpresa().getAcuerdos().compareTo(antiguo.getContactoHistoricoEmpresa().getAcuerdos()) != 0))
            antiguo.getContactoHistoricoEmpresa().setAcuerdos(contacto.getContactoHistoricoEmpresa().getAcuerdos());

        if ((antiguo.getContactoHistoricoEmpresa().getFechaContacto() == null) || (antiguo.getContactoHistoricoEmpresa().getFechaContacto() != null && contacto.getContactoHistoricoEmpresa().getFechaContacto().compareTo(antiguo.getContactoHistoricoEmpresa().getFechaContacto()) != 0))
            antiguo.getContactoHistoricoEmpresa().setFechaContacto(contacto.getContactoHistoricoEmpresa().getFechaContacto());

        if (contacto.getContactoHistoricoEmpresa().getTipoContacto().getCodContacto().compareTo(antiguo.getContactoHistoricoEmpresa().getTipoContacto().getCodContacto()) != 0)
        {
            TipoContacto nuevoTipoContacto = tipoContactoRepository.findOne(contacto.getContactoHistoricoEmpresa().getTipoContacto().getCodContacto());
            antiguo.getContactoHistoricoEmpresa().setTipoContacto(nuevoTipoContacto);
        }

        if (contacto.getContactoHistoricoEmpresa().getTipoOportunidad().getCodOportunidad().compareTo(antiguo.getContactoHistoricoEmpresa().getTipoOportunidad().getCodOportunidad()) != 0)
        {
            TipoOportunidad nuevoTipoOportunidad = tipoOportunidadRepository.findOne(contacto.getContactoHistoricoEmpresa().getTipoOportunidad().getCodOportunidad());
            antiguo.getContactoHistoricoEmpresa().setTipoOportunidad(nuevoTipoOportunidad);
        }

        antiguo.getContactoHistoricoEmpresa().setFechaProximoContacto(contacto.getContactoHistoricoEmpresa().getFechaProximoContacto());
        antiguo.getContactoHistoricoEmpresa().setTipoNivelInteres(contacto.getContactoHistoricoEmpresa().getTipoNivelInteres());




        contactoHistoricoEmpresaPersonaParticipanteRepository.save(antiguo);
    }

    /**
     * Retorna  una {@Link java.util.List} con todas los {@Link crm.entities.CompromisoEmpresa} de la {@Link crm.entities.Empresa}
     * que tiene el id del parametro otorgado.
     *
     * @param id id de empresa que se desean buscar.
     *
     * @return Coleccion {@Link.util.List} de las {@Link crm.entities.SucursalEmpresa} de una {@Link crm.entities.Empresa}
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @PostFilter("hasPermission(filterObject, 'Leer')")
    public List<CompromisoEmpresa> getCompromisoEmpresa(Long id) {

        List<CompromisoEmpresa> compromisos = compromisoEmpresaRepository.buscarCompromisoEmpresaPorIdEmpresa(id);
        return compromisos;
    }

    @PostAuthorize("hasPermission(returnObject,'Leer')")
    public CompromisoEmpresa getCompromisoEmpresaPorId(Long idCompromiso) {

        return compromisoEmpresaRepository.findById(idCompromiso);
    }

    @PreAuthorize("hasPermission(returnObject,'Crear')")
    public CompromisoEmpresa agregarCompromisoEmpresa(CompromisoEmpresa compromisoEmpresa) {
        //List <Usuario> usuarios=usuarioService.buscarUsuariosPorCalceNombreCompleto(nombreOperador);
        Date fechaActual = new Date();
        compromisoEmpresa.setRutUsuario(usuarioService.getCurrentUser().getRut());
        compromisoEmpresa.setFechaModificacion(fechaActual);
        //compromisoEmpresa.setOperadorACargo(Long.parseLong(operador.getRut().toString()));

        return compromisoEmpresaRepository.save(compromisoEmpresa);
    }

    @PostAuthorize("hasPermission(returnObject,'Leer')")
    public CompromisoEmpresa updateCompromisoEmpresa(CompromisoEmpresa compromiso) {

        CompromisoEmpresa s = compromisoEmpresaRepository.save(compromiso);
        return s;
    }

    /**
     * Retorna  una {@Link java.util.Page} con todas los {@Link crm.entities.CompromisoEmpresa} de un tipo de compromiso.
     *
     * @param nombreCompromiso String con el compromiso a buscar.
     *
     * @return Coleccion {@Link.util.Page} de los {@Link crm.entities.CompromisoEmpresa} de las {@Link crm.entities.Empresa}
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    public Page<CompromisoEmpresa> buscarCompromisoEmpresaPorTipoCompromisoYTipoSector(String nombreCompromiso, int pagina,Short sector) {
        PageRequest page=new PageRequest(pagina,20);
        if(sector!=-1)return compromisoEmpresaRepository.buscarCompromisoEmpresaPorTipoCompromisoYTipoSector(nombreCompromiso,page,sector);
        else return compromisoEmpresaRepository.buscarCompromisoEmpresaPorTipoCompromiso(nombreCompromiso,page);
    }

    /**
     * Retorna  una {@Link java.util.Page} con todas los {@Link crm.entities.CompromisoEmpresa} de un tipo de compromiso y vigencia pasada por parametros.
     *
     * @param nombreCompromiso String con el compromiso a buscar.
     * @param nombreVigencia String con el compromiso a buscar.
     *
     * @return Coleccion {@Link.util.Page} de los {@Link crm.entities.CompromisoEmpresa} de las {@Link crm.entities.Empresa}
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    public Page<CompromisoEmpresa> buscarCompromisoEmpresaPorTipoCompromisoTipoVigenciaYTipoSector(String nombreCompromiso, String nombreVigencia, int pagina, Short sector) {
        PageRequest page=new PageRequest(pagina,20);
        if(sector!=-1)return compromisoEmpresaRepository.buscarCompromisoEmpresaPorTipoCompromisoTipoVigenciaYTipoSector(nombreCompromiso,nombreVigencia,page,sector);
        else return compromisoEmpresaRepository.buscarCompromisoEmpresaPorTipoCompromisoYTipoVigencia(nombreCompromiso,nombreVigencia,page);
    }

    public Page<CompromisoEmpresa> buscarCompromisoEmpresaPorTipoCompromisoTipoVigenciaYAporte(String nombreCompromiso, String nombreVigencia, int pagina) {
        PageRequest page=new PageRequest(pagina,20);
        return compromisoEmpresaRepository.buscarCompromisoEmpresaPorTipoCompromisoTipoVigenciaYAporte(nombreCompromiso,nombreVigencia,page);
    }

    //@PostAuthorize("hasPermission(returnObject,'Leer')")
    public AporteEmpresa getAporteEmpresaPorId(Long idAporte) {
        return aporteEmpresaRepository.findById(idAporte);
    }

    //@PostFilter("hasPermission(filterObject, 'Leer')")
    public List<AporteEmpresa> getAporteEmpresa(Long id) {

        //PageRequest pageRequest = new PageRequest(0, 20);
        List<AporteEmpresa> aportes = aporteEmpresaRepository.buscarAporteEmpresaPorIdEmpresa(id);

        return aportes;
    }

    public Integer obtenerTotalAporteEmpresaPorIdEmpresa(Long idEmpresa,Long idCompromiso) {

        //PageRequest pageRequest = new PageRequest(0, 20);
        return aporteEmpresaRepository.obtenertotalAporteEmpresaPorEmpresaYCompromiso(idEmpresa,idCompromiso);
    }

    public Integer getTotalAporteEmpresaPorSucursalYCompromiso(Long id,Long compId) {

        //PageRequest pageRequest = new PageRequest(0, 20);
        return aporteEmpresaRepository.totalAporteEmpresaPorSucursalYCompromiso(id,compId);
    }

    //@PreAuthorize("hasPermission(returnObject,'Crear')")
    public AporteEmpresa agregarAporteEmpresa(AporteEmpresa aporteEmpresa) {
        //List <Usuario> usuarios=usuarioService.buscarUsuariosPorCalceNombreCompleto(nombreOperador);
        Date fechaActual = new Date();
        aporteEmpresa.setRutUsuario(usuarioService.getCurrentUser().getRut());
        aporteEmpresa.setFechaModificacion(fechaActual);
        //compromisoEmpresa.setOperadorACargo(Long.parseLong(operador.getRut().toString()));

        return aporteEmpresaRepository.save(aporteEmpresa);
    }

    //@PostAuthorize("hasPermission(returnObject,'Leer')")
    public AporteEmpresa updateAporteEmpresa(AporteEmpresa aporte) {

        AporteEmpresa apo = aporteEmpresaRepository.save(aporte);
        return apo;
    }

    /**
     * Retorna  una {@Link java.util.Page} con todas los {@Link crm.entities.AporteEmpresa}.
     *
     * @param nombreCompromiso String con el compromiso a buscar.
     * @param vigente String de la vigencia del compromiso.
     *
     * @return Coleccion {@Link.util.Page} de los {@Link crm.entities.AporteEmpresa} de una {@Link crm.entities.Empresa}
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    public Page<AporteEmpresa> buscarAporteEmpresaPorTipoCompromisoYTipoVigente(String nombreCompromiso,String vigente,int pagina,Short sector) {
        PageRequest page=new PageRequest(pagina,20);
        if(sector!=-1)return aporteEmpresaRepository.buscarAporteEmpresaPorTipoCompromisoYTipoVigenteYTipoSector(nombreCompromiso,vigente,page, sector);
        else return aporteEmpresaRepository.buscarAporteEmpresaPorTipoCompromisoYTipoVigente(nombreCompromiso,vigente,page);
    }

    /**
     * Retorna  una {@Link java.util.Page} con todas los {@Link crm.entities.AporteEmpresa}.
     *
     * @param nombreCompromiso String con el compromiso a buscar.
     *
     * @return Coleccion {@Link.util.Page} de los {@Link crm.entities.AporteEmpresa} de una {@Link crm.entities.Empresa}
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    public Page<AporteEmpresa> buscarAporteEmpresaPorTipoCompromiso(String nombreCompromiso,int pagina,Short sector) {
        PageRequest page=new PageRequest(pagina,20);
        if(sector!=-1)return aporteEmpresaRepository.buscarAporteEmpresaPorTipoCompromisoYTipoSector(nombreCompromiso,page, sector);
        else return aporteEmpresaRepository.buscarAporteEmpresaPorTipoCompromiso(nombreCompromiso,page);
    }

    /**
     * Retorna  un {@Link java.util.Page} con todas las {@Link crm.entities.Empresa} no contactadas.
     *
     * @param pagina Integer de la pagina.
     *
     * @return Coleccion {@Link.util.Page} de las {@Link crm.entities.Empresa}.
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    public Page<Empresa> buscarEmpresasNoContactadas(int pagina, Short sector) {
        PageRequest pageRequest = new PageRequest(pagina,20);
        if(sector!=-1)return empresaRepository.buscarEmpresasNoContactadasPorTipoSector(pageRequest,sector);
        else return empresaRepository.buscarEmpresasNoContactadas(pageRequest);
    }
}

