package crm.controllers;

import crm.entities.Colegio;
import crm.services.ColegioService;
import crm.services.EntidadesTipoService;
import crm.services.GeograficoService;
import crm.utils.EncodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * Controlador que maneja la busqueda de una {@link crm.entities.Colegio}
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Controller
public class BusquedaColegiosController {

    /**
     * Servicio utilizado para el manejo de la entidad {@link crm.entities.Colegio} y sus entidades asociadas.
     */
    @Autowired
    private ColegioService colegioService;

    /**
     * Servicio utilizado para el manejo de las entidades tipo.
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Tamano de las paginas de listados a mostrar
     */
    private static final int PAGE_SIZE = 20;

    /**
     * Clase utilizada para codificar y decodificar URIs
     */
    EncodingUtil encodingUtil;




    /**
     * Muestra el form donde se busca una {@link crm.entities.Colegio}
     * segun el pais, nombre, comuna o rbd.
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Una vista con el form para buscar la {@link crm.entities.Colegio}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/colegio")
    public String busquedaColegios(Model model){

        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());
        model.addAttribute("colegio", new Colegio());
        
        return "/colegio/busquedaColegios";
    }




    /**
     * Muestra el resultado de la busqueda de una {@link crm.entities.Colegio}.
     *
     * @param model Modelo utilizado en la vista.
     * @param nombreColegio Nombre del colegio que se desea buscar.
     * @param codigoRbd Codigo rbd del colegio que se desea buscar.
     * @param idPais Id del pais que se desea buscar.
     * @param idComuna Id de la comuna que se desea buscar
     * @param pagina Numero de la pagina actual de busqueda.
     *
     * @return Una vista con los resultados de la busqueda.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/colegio/buscar", method= RequestMethod.GET)
    public String resultadoBusquedas(Model model,
                                     @RequestParam ("nombreColegio") String nombreColegio,
                                     @RequestParam ("codigoRbd") String codigoRbd,
                                     @RequestParam("idPais") String idPais,
                                     @RequestParam("idComuna") String idComuna ,
                                     @RequestParam("tipoVigencia") String tipoVigencia,
                                     @RequestParam("pagina") String pagina){

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

        nombreColegio = encodingUtil.decodeURIComponent(nombreColegio);

        Page<Colegio> colegios = colegioService.busquedaColegioPorCalceNombreYRbdYPaisYComunaYVigencia(nombreColegio, codigoRbd, idPais, idComuna, tipoVigencia, PAGE_SIZE, numeroPagina - 1);

        model.addAttribute("colegios", colegios);

        model.addAttribute("nombreColegio", nombreColegio);
        model.addAttribute("codigoRbd", codigoRbd);
        model.addAttribute("idPais", idPais);
        model.addAttribute("idComuna", idComuna);
        model.addAttribute("idPais", idPais);
        model.addAttribute("tipoVigencia", tipoVigencia);
        model.addAttribute("pagina", pagina);

        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());

        generatePagination(model, colegios);

        return "/colegio/busquedaColegios";
    }




    /**
     * Metodo utilizado para realizar la paginacion de la busqueda.
     *
     * @param model Modelo utilizado en la vista.
     * @param page Objeta de la entidad {@link org.springframework.data.domain.Page}
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
