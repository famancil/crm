package crm.entities;

import javax.persistence.*;

/**
 * Entidad que corresponde a la tabla empleo.encuesta_oferta_laboral
 *
 * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
 */

@Entity
@Table(name = "encuesta_oferta_laboral", schema = "empleo")
public class EncuestaOfertaLaboral {

    /**
     * Clave primaria de la encuesta
     * Cada oferta laboral tiene una encuesta de oferta laboral
     */
    private Long idOfertaLaboralUsmempleo;

    /**
     * La oferta laboral {@link crm.entities.OfertaLaboralUsmempleo}
     * asociada a la encuestas de oferta laboral
     */
    private OfertaLaboralUsmempleo ofertaLaboral;

    /**
     * El id de la empresa que respondió la encuesta
     */
    private Long idPerfilEmpresa;

    //private Empresa empresa;

    /**
     * La cantidad de candidatos que se contrataron gracias a la oferta laboral
     */
    private Integer candidatosContratados;

    /**
     * La nota de satisfaccion que le da a los resultados de la oferta laboral
     */
    private Integer notaSatisfaccion;

    /**
     * Para saber si la encuesta fue respondida o no
     */
    private Boolean opina;


    public EncuestaOfertaLaboral() {

    }

    public EncuestaOfertaLaboral(EncuestaOfertaLaboral encuesta) {
        this.idOfertaLaboralUsmempleo = encuesta.getIdOfertaLaboralUsmempleo();
        this.ofertaLaboral = encuesta.getOfertaLaboral();
        this.idPerfilEmpresa = encuesta.getIdPerfilEmpresa();
        //this.empresa = encuesta.getEmpresa();
        this.candidatosContratados = encuesta.getCandidatosContratados();
        this.notaSatisfaccion = encuesta.getNotaSatisfaccion();
        this.opina = encuesta.getOpina();
    }

    @Id
    @Column(name = "ofelabusm_id")
    public Long getIdOfertaLaboralUsmempleo() {
        return idOfertaLaboralUsmempleo;
    }

    public void setIdOfertaLaboralUsmempleo(Long idOfertaLaboralUsmempleo) {
        this.idOfertaLaboralUsmempleo = idOfertaLaboralUsmempleo;
    }

    @JoinColumn(name = "ofelabusm_id", referencedColumnName = "ofelabusm_id", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    public OfertaLaboralUsmempleo getOfertaLaboral() {
        return ofertaLaboral;
    }

    public void setOfertaLaboral(OfertaLaboralUsmempleo ofertaLaboral) {
        this.ofertaLaboral = ofertaLaboral;
    }

    @Column(name = "perempusm_id")
    public Long getIdPerfilEmpresa() {
        return idPerfilEmpresa;
    }

    public void setIdPerfilEmpresa(Long idPerfilEmpresa) {
        this.idPerfilEmpresa = idPerfilEmpresa;
    }

    /**public Empresa getEmpresa() {
        return empresa;
    }**/

    /**public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }**/

    @Column(name = "encofelab_num_candidatos_contratados")
    public Integer getCandidatosContratados() {
        return candidatosContratados;
    }

    public void setCandidatosContratados(Integer candidatosContratados) {
        this.candidatosContratados = candidatosContratados;
    }

    @Column(name = "encofelab_nota_satisfaccion")
    public Integer getNotaSatisfaccion() {
        return notaSatisfaccion;
    }

    public void setNotaSatisfaccion(Integer notaSatisfaccion) {
        this.notaSatisfaccion = notaSatisfaccion;
    }

    @Column(name = "encofelab_opina")
    public Boolean getOpina() {
        return opina;
    }

    public void setOpina(Boolean opina) {
        this.opina = opina;
    }
}
