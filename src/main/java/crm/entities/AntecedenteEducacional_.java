package crm.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AntecedenteEducacional.class)
public abstract class AntecedenteEducacional_ {

	public static volatile SingularAttribute<AntecedenteEducacional, Pais> pais;
	public static volatile SingularAttribute<AntecedenteEducacional, Usuario> usuario;
	public static volatile SingularAttribute<AntecedenteEducacional, TipoEstadoEstudio> tipoEstadoEstudio;
	public static volatile SingularAttribute<AntecedenteEducacional, String> abstractMemoria;
	public static volatile SingularAttribute<AntecedenteEducacional, Carrera> carrera;
	public static volatile SingularAttribute<AntecedenteEducacional, Short> anioEgreso;
	public static volatile SingularAttribute<AntecedenteEducacional, Institucion> institucion;
	public static volatile SingularAttribute<AntecedenteEducacional, TipoEstudio> tipoEstudio;
	public static volatile SingularAttribute<AntecedenteEducacional, Short> anioIngreso;
	public static volatile SingularAttribute<AntecedenteEducacional, Integer> rutUsuario;
	public static volatile SingularAttribute<AntecedenteEducacional, Long> id;
	public static volatile SingularAttribute<AntecedenteEducacional, Date> fechaModificacion;
	public static volatile SingularAttribute<AntecedenteEducacional, Short> anioTitulo;

}

