package crm.controllers;

import crm.entities.*;
import crm.repositories.*;
import crm.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controlador que permite que en una vista, se vean los paises,
 * regiones, provincias y comunas consecutivamente
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Controller
public class MostrarOpcionesGeograficasController {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Pais}.
     */
    @Autowired
    private PaisRepository paisRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Region}.
     */
    @Autowired
    private RegionRepository regionRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Comuna}.
     */
    @Autowired
    private ComunaRepository comunaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Provincia}.
     */
    @Autowired
    private ProvinciaRepository provinciaRepository;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Empresa}.
     */
    @Autowired
    private EmpresaService empresaService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Carrera}.
     */
    @Autowired
    private CarreraService carreraService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.CarreraInstitucion}.
     */
    @Autowired
    private CarreraInstitucionService carreraInstitucionService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Institucion}.
     */
    @Autowired
    private InstitucionService institucionService;

    Short pais;



    /**
     * Obtiene una lista con los nombres de {@link crm.entities.Empresa} asociados a {@link crm.entities.ActividadExalumno},
     * según el calce con la razon social, nombre de fantasía o sigla pasada como parámetro.
     *
     * @param tagName nombre (o porción de nombre) sobra la cual se desea realizar la busqueda
     *
     * @return Una vista de una lista de {@link crm.entities.Empresa}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *  TODO ver donde dejar esta funcion despues
     */
    @RequestMapping(value = "/opciones/empresasActividadExalumno", method = RequestMethod.GET)
    public @ResponseBody List<String> buscarNombreEmpresasPorNombreAsociadasActividadExalumno(@RequestParam("term") String tagName) {

        List<Empresa> listaEmpresas = empresaService.buscarEmpresasDeActividadExalumnoPorCalceRazonSocialONombreFantasiaOSigla(tagName);

        List<String> empresas = new ArrayList<String>();

        // Obtiene el nombre de fantasia, razon social o sigla de la empresa, dependiendo de cual no esté nulo en la base de datos
        for (Empresa empresa: listaEmpresas){
            if(empresa.getNombreFantasiaEmpresa() != null){
                empresas.add(empresa.getNombreFantasiaEmpresa().toUpperCase());
            }
            else if (empresa.getRazonSocial() != null){
                empresas.add(empresa.getRazonSocial().toUpperCase());
            }
            else if (empresa.getSigla() != null){
                empresas.add(empresa.getSigla().toUpperCase());
            }
        }

        return empresas;
    }



    /**
     * Obtiene una lista con los nombres de {@link crm.entities.Empresa} según el calce con la razon social, nombre de
     * fantasía o sigla pasada como parámetro.
     *
     * @param tagName nombre (o porción de nombre) sobra la cual se desea realizar la busqueda
     *
     * @return Una vista de una lista de {@link crm.entities.Empresa}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *  TODO ver donde dejar esta funcion despues
     */
    @RequestMapping(value = "/opciones/empresas", method = RequestMethod.GET)
    public @ResponseBody List<String> buscarNombreEmpresasPorNombre(@RequestParam("term") String tagName) {

        List<Empresa> listaEmpresas = empresaService.buscarEmpresasPorCalceRazonSocialONombreFantasiaOSigla(tagName);

        List<String> empresas = new ArrayList<String>();

        // Obtiene el nombre de fantasia, razon social o sigla de la empresa, dependiendo de cual no esté nulo en la base de datos
        for (Empresa empresa: listaEmpresas){
            if(empresa.getNombreFantasiaEmpresa() != null){
                empresas.add(empresa.getNombreFantasiaEmpresa().toUpperCase());
            }
            else if (empresa.getRazonSocial() != null){
                empresas.add(empresa.getRazonSocial().toUpperCase());
            }
            else if (empresa.getSigla() != null){
                empresas.add(empresa.getSigla().toUpperCase());
            }
        }

        return empresas;
    }





    /**
     * Obtiene una lista de {@link crm.entities.CarreraInstitucion} que no se encuentran asociadas a una
     * {@link crm.entities.Institucion} en especifica
     *
     * @param codInstitucion Id de la {@link crm.entities.Institucion} sobre la cual buscar en
     *                      {@link crm.entities.Carrera} no asociadas asociadas, en {@link crm.entities.CarreraInstitucion}
     * @param model
     *
     * @return La lista de {@link crm.entities.CarreraInstitucion} no asociadas a la {@link crm.entities.Institucion}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *  TODO ver donde dejar esta funcion despues
     */
    @RequestMapping(value="/opciones/carreras/noAsociadasAInstitucion/{codInstitucion}", method = RequestMethod.GET)
    public @ResponseBody List<Carrera> opcionesCarrerasNoAsociadasAInstitucion(@PathVariable("codInstitucion") String codInstitucion,
                                                                                            Model model) {

        // busca carreras que no estén asociadas a una institucion, según el codInstitucion
        List<Carrera> carrerasNoAsociadasAInstitucion = carreraService.buscarCarrerasNoAsociadasAInstitucionPorCodInstitucion(codInstitucion);

        return carrerasNoAsociadasAInstitucion;
    }




    /**
     * Obtiene una lista de {@link crm.entities.CarreraInstitucion} que se encuentran asociadas a una {@link crm.entities.Institucion}
     *
     * @param codInstitucion Id de la {@link crm.entities.Institucion} sobre la cual buscar en
     *                      {@link crm.entities.Carrera} asociadas, en {@link crm.entities.CarreraInstitucion}
     * @param model
     *
     * @return La lista de {@link crm.entities.CarreraInstitucion} asociadas a la {@link crm.entities.Institucion}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *  TODO ver donde dejar esta funcion despues
     */
    @RequestMapping(value="/opciones/institucion/{codInstitucion}/carreras",method = RequestMethod.GET)
    public @ResponseBody List<CarreraInstitucion> opcionesCarrerasAsociadasAInstitucion(@PathVariable("codInstitucion") String codInstitucion,
                                                                               Model model) {

        // busca carreras que no estén asociadas a una institucion, según el codInstitucion
        List<CarreraInstitucion> carrerasInstitucion = carreraInstitucionService.buscarCarrerasInstitucionAsociadasAInstitucionPorCodInstitucion(codInstitucion);

        return carrerasInstitucion;
    }


}
