package crm.services;

import crm.entities.EmailMensaje;
import crm.repositories.EmailMensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo {@link crm.entities.EmailMensaje} y con otras
 * entidades relacionadas.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Component
public class EmailService {

    @Autowired
    private EmailMensajeRepository emailMensajeRepository;

    /*
     * Retorna el contenido del EmailMensaje con el id otorgado
     */
    public String getContenidoEmailMensajeById(Integer id){
        return emailMensajeRepository.findOne(id).getMensaje();
    }

    public EmailMensaje getEmailMensajeById(int id) {
        return emailMensajeRepository.findOne(id);
    }
}
