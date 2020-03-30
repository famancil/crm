package crm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.log4j.Logger;
import crm.entities.EncuestaOfertaLaboral;
import crm.entities.Usuario;
import crm.repositories.EncuestaOfertaLaboralRepository;
import crm.repositories.UsuarioRepository;
import crm.services.IndicesService;
import crm.services.UsuarioService;
import crm.utils.KeyValueContainer;
import crm.utils.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.core.SpringVersion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HelloController {

    /**
     * {@link org.apache.log4j.Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(HelloController.class);


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private IndicesService indicesService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EncuestaOfertaLaboralRepository encuestaOfertaLaboralRepository;

    @RequestMapping("/")
    public String index(Model model) {
        logger.info("Spring Version: "+ SpringVersion.getVersion());
        logger.info("Hibernate Version: "+ org.hibernate.Version.getVersionString());
        List<Usuario> encontrados = usuarioRepository.findByEmail("diego_desu@hotmail.com");
        return "dashboard";
    }


    /*
    TODO BORRAR
    @RequestMapping(value = "/dashboard/usuarios")
    @ResponseBody public List<KeyValueContainer<Long>> dashboardUsuarios() {
        return usuarioService.datosDashboard();
    }
   */

    /**
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     * @return Lista con indices de empresas para usarlos en dashboard
     */
    @RequestMapping(value ="/dashboard/empresas")
    @ResponseBody public List<KeyValueContainer<Long>> dashboardEmpresas() {
        return indicesService.getDatosEmpresaSucursalesDashboard();
    }

    /**
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     * @return Lista con indices sobre las ofertas, vacantes y postulaciones para usarlos en dashboard
     */
    @RequestMapping(value = "/dashboard/ofertas-vacantes-postulaciones")
    @ResponseBody public List<KeyValueContainer<Long>> dashboardOfertasPostulacionesVacantes() {
        return indicesService.getDatosOfertasPostulacionesVacantesDashboard();
    }

    /**
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     * @return Lista con indices sobre la vigencia de las carreras para usarlos en dashboard
     */
    @RequestMapping(value = "/dashboard/carreras-vigencia")
    @ResponseBody public List<KeyValueContainer<Long>> dashboardCarrerasVigencia() {
        return indicesService.getDatosCarrerasVigencia();
    }

    /**
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     * @return Lista con indices sobre la vigencia de las ofertas para usarlos en dashboard
     */
    @RequestMapping(value = "/dashboard/ofertas-vigencia")
    @ResponseBody public List<KeyValueContainer<Long>> dashboardVigenciaOfertas() {
        return indicesService.getVigenciaOfertas();
    }

    /**
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     * @return Lista con indices sobre la vigencia de las empresas para usarlos en dashboard
     */
    @RequestMapping(value = "/dashboard/empresas-vigencia")
    @ResponseBody public List<KeyValueContainer<Long>> dashboardVigenciaEmpresas() {
        return indicesService.getVigenciaEmpresas();
    }

    /**
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     * @return Lista con indices sobre la vigencia de las sucursales para usarlos en dashboard
     */
    @RequestMapping(value = "/dashboard/sucursales-vigencia")
    @ResponseBody public List<KeyValueContainer<Long>> dashboardVigenciaSucursales() {
        return indicesService.getVigenciaSucursales();
    }

    /**
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     *
     * @return Lista con las cantidades de encuestas respondidas por oferta laboral y por postulacion laboral
     */
    @RequestMapping(value = "/dashboard/encuestas-respondidas")
    @ResponseBody public List<KeyValueContainer<Long>> dashboardEncuestasResponidas() {
        return indicesService.getEncuestasRespondidas();
    }

    /**
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     *
     * @return Lista con las cantidades de videocurriculos publicados y por publicar
     */
    @RequestMapping(value = "/dashboard/vcv-publicados")
    @ResponseBody public List<KeyValueContainer<Long>> dashboardVcvPublicados() {
        return indicesService.getVideoCurriculosPublicados();
    }

    /**
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     *
     * @return
     */
    @RequestMapping(value = "/dashboard/admins-tipo")
    @ResponseBody public List<KeyValueContainer<Long>> dashboardAdminsPorTipo() {
        return indicesService.getAdminsPorTipo();
    }

    /**
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     *
     * @return
     */
    @RequestMapping(value = "/dashboard/admins-crm")
    @ResponseBody public List<KeyValueContainer<Long>> dashboardAdminsCrm() {
        return indicesService.getAdminsCrm();
    }

    @RequestMapping(value = "/login")
    public String login() { return "login"; }

}