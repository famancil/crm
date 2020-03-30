package crm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import crm.entities.*;
import crm.repositories.*;
import crm.services.*;
import crm.utils.EncodingUtil;
import crm.utils.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import crm.utils.EncodingUtil.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador que funciona como una API JSON para retornar un listado con datos asociados a las empresas disponibles en la DB de la Red de Ex Alumnos.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@RestController
public class EstructuraEmpresasController {

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Empresa}.
     */
    @Autowired
    private EmpresaService empresaService;


    /**
     * Retorna una lista de las sucursales de una determinada {@link crm.entities.Empresa} expresada segun su
     * nombre de fantasia, razon social o sigla.
     *
     * @return {@link java.util.List} de {@link crm.entities.SucursalEmpresa}.
     */
    @RequestMapping(value = "/empresa/{nombreEmpresa}/nombres-sucursales", method = RequestMethod.GET)
    @JsonView(ResponseView.MainView.class)
    public @ResponseBody List<SucursalEmpresa> listaSucursales(@PathVariable("nombreEmpresa") String nombreEmpresa) {
        nombreEmpresa = EncodingUtil.decodeURIComponent(nombreEmpresa);
        List<SucursalEmpresa> li = empresaService.getSucursalesEmpresaByNombre(nombreEmpresa);
        return li;
    }

    /**
     * Retorna una lista de las compromisos de una determinada {@link crm.entities.Empresa} expresada segun su
     * nombre de fantasia, razon social o sigla.
     *
     * @return {@link java.util.List} de {@link crm.entities.CompromisoEmpresa}.
     */
    @RequestMapping(value = "/empresa/{nombreEmpresa}/compromisos", method = RequestMethod.GET)
    @JsonView(ResponseView.MainView.class)
    public @ResponseBody List<CompromisoEmpresa> listaCompromiso(@PathVariable("nombreEmpresa") String nombreEmpresa) {
        nombreEmpresa = EncodingUtil.decodeURIComponent(nombreEmpresa);
        Empresa empresa = empresaService.buscarEmpresaEspecificaPorRazonSocialONombreFantasiaOSigla(nombreEmpresa);
        Long idEmpresa = empresa.getId();
        List<CompromisoEmpresa> compromisos = empresaService.getCompromisoEmpresa(idEmpresa);
        /*for(CompromisoEmpresa comp : compromisos){
            System.out.println(comp.getTipoCompromiso().getNomCompromiso());
        }*/
        return compromisos;
    }
}
