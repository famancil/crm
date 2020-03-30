package crm.entities;

/**
 * Estado de una invitacion a una video entrevista realizada por un {@link UsuarioEmpresaUsmempleo}
 * a un {@link Usuario}. Los estados son bastante autoexplicativos.
 *
 * @author dacuna <diego.acuna@usm.cl>
 * @since 1.0
 */
public enum InvitacionVideoEntrevistaUsmEmpleoEstado {
    SIN_RESPONDER ("Pendiente de confirmaci&oacute;n"),
    ACEPTADA ("Aceptada"),
    RECHAZADA ("Rechazada"),
    CANCELADA ("Cancelada");

    private String name;

    private InvitacionVideoEntrevistaUsmEmpleoEstado(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
