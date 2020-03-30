package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla public.tipo_pagina
 * Contiene un listado con los tipos de pagina ingresados por los usuarios
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_pagina")
public class TipoPagina {

    /*
    * Codigo (Id) del tipo de pagina
    */
    private Integer codigo;

    /*
    * Nombre del tipo de pagina
    */
    private String nombre;



    @Id
    @Column(name = "cod_pagina")
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_pagina")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}