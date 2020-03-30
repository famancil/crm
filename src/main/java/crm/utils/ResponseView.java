package crm.utils;

/**
 * Clase que permite definir vistas a utilizar con la anotacion {@link com.fasterxml.jackson.annotation.JsonView} en
 * entidades y controladores. De esta forma se pueden filtrar los atributos que se desean exponer en una llamada en
 * particular (siendo una llamada una accion de un controlador).
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
public class ResponseView {
    public interface MainView {}
}
