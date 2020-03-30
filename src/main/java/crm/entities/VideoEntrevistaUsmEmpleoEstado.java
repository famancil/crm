package crm.entities;

/**
 * Estado de una {@link VideoEntrevistaUsmEmpleo}. Permite reconocer si es que uno/las dos
 * personas de la video entrevista accedieron y llevaron a cabo esta o no.
 *
 * @author dacuna <diego.acuna@usm.cl>
 * @since 1.0
 */
public enum VideoEntrevistaUsmEmpleoEstado {
    CREADA, //esta creada pero aun nadie ha accedido
    ACCEDIO_USUARIO_EXALUMNO,
    ACCEDIO_USUARIO_EMPRESA,
    REALIZADA,
    NO_REALIZADA
}
