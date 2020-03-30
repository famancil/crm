package crm.entities;

/**
 * Permite indicar cual es el idiom de un {@link VideoCurriculoUsuario}.
 * Actualmente los idiomas aceptados corresponden a espanol y ingles siendo el idioma espanol el almacenado por
 * defecto.
 *
 * @author dacuna <diego.acuna@usm.cl>
 * @since 1.0
 */
public enum IdiomaVideoCurriculo {

    ESPANOL ("Español"),
    INGLES ("Inglés");

    private String name;

    private IdiomaVideoCurriculo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
