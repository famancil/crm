package crm.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ActividadExalumno.class)
public abstract class ActividadExalumno_ {

	public static volatile SingularAttribute<ActividadExalumno, Short> notaAmbiente;
	public static volatile SingularAttribute<ActividadExalumno, TipoCargo> tipoCargo;
	public static volatile SingularAttribute<ActividadExalumno, TipoActividadExalumno> tipoActividadExalumno;
	public static volatile SingularAttribute<ActividadExalumno, Short> posicionOrden;
	public static volatile SingularAttribute<ActividadExalumno, String> descripcionRemuneracion;
	public static volatile SingularAttribute<ActividadExalumno, Short> notaIngreso;
	public static volatile SingularAttribute<ActividadExalumno, Short> notaEmpresa;
	public static volatile SingularAttribute<ActividadExalumno, Usuario> usuario;
	public static volatile SingularAttribute<ActividadExalumno, SucursalEmpresa> sucursalEmpresa;
	public static volatile SingularAttribute<ActividadExalumno, String> descripcion;
	public static volatile SingularAttribute<ActividadExalumno, Date> fechaIngreso;
	public static volatile SingularAttribute<ActividadExalumno, String> cargo;
	public static volatile SingularAttribute<ActividadExalumno, Long> id;
	public static volatile SingularAttribute<ActividadExalumno, Short> notaFelicidad;
	public static volatile SingularAttribute<ActividadExalumno, String> departamento;
	public static volatile SingularAttribute<ActividadExalumno, TipoMoneda> tipoMoneda;
	public static volatile SingularAttribute<ActividadExalumno, Integer> rutUsuario;
	public static volatile SingularAttribute<ActividadExalumno, Empresa> empresa;
	public static volatile SingularAttribute<ActividadExalumno, Date> fechaEgreso;
	public static volatile SingularAttribute<ActividadExalumno, Short> notaEstabilidad;
	public static volatile SingularAttribute<ActividadExalumno, Date> fechaModificacion;
	public static volatile SingularAttribute<ActividadExalumno, Short> notaRemuneracion;
	public static volatile SingularAttribute<ActividadExalumno, Integer> remuneracion;

}

