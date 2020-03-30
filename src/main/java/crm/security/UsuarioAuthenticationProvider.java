package crm.security;

import crm.entities.LoginAexa;
import crm.entities.Usuario;
import crm.repositories.UsuarioLoginRepository;
import crm.services.UsuarioService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Clase que implementa la funcionalidad para autenticar usuarios para el sistema del CRM utilizando la informacion
 * desde {@link crm.entities.LoginAexa} para asignarles roles en el caso de una autenticacion exitosa. El sistema de
 * autenticacion/autorizacion utilizado corresponde a Spring Security.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Component
public class UsuarioAuthenticationProvider implements UserDetailsService {

    /**
     * {@link org.apache.log4j.Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(UsuarioAuthenticationProvider.class);

    /**
     * Repositorio JPA para el manejo de datos de los usuarios del sistema.
     */
    @Autowired
    private UsuarioLoginRepository usuarioLoginRepository;

    /**
     * Servicio para el manejo e interaccion con la entidad {@link crm.entities.Usuario}.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Realiza la validacion de un {@link crm.entities.Usuario} para la autenticacion en spring security. El usuario debe
     * tener acceso al sistema CRM.
     *
     * @param username nombre del usuario a autenticar.
     * @return Instancia del {@link crm.entities.Usuario} que ha sido autenticado con exito.
     * @throws UsernameNotFoundException En el caso que el usuario no exista o no posea las credenciales de acceso
     *                                   correspondientes.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Validando al usuario " + username+"...");
        LoginAexa usuario = usuarioLoginRepository.findByUsername(username);
        if(usuario == null) {
            logger.warn("Usuario de nombre " + username + " no existe en el sistema.");
            throw new UsernameNotFoundException("Usuario de nombre " + username + " no existe en el sistema.");
        }

        if (usuarioService.getAccesoCRMDeUsuario(usuario.getUsuario()) == null) {
            logger.warn("El usuario que intento acceder no posee los permisos necesarios para ingresar al sistema. " +
                    "Revisar accesos en tabla perfil_aexa. Para utilizar el CRM se deben tener accesos al sistema 38.");
            throw new UsernameNotFoundException("El usuario " + username + " no posee los permisos suficientes para " +
                    "entrar al sistema.");
        }

        logger.info("Usuario " + username + " existe y posee accesos, validando password...");
        return usuario.getUsuario();
    }
}
