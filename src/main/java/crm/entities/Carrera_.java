package crm.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Carrera.class)
public abstract class Carrera_ {

	public static volatile SingularAttribute<Carrera, TipoGrado> tipoGrado;
	public static volatile SingularAttribute<Carrera, String> titulo;
	public static volatile SingularAttribute<Carrera, String> nombreCarrera;
	public static volatile SingularAttribute<Carrera, String> abreviacion;
	public static volatile SingularAttribute<Carrera, TipoVigencia> tipoVigencia;
	public static volatile SingularAttribute<Carrera, Integer> rutUsuario;
	public static volatile SingularAttribute<Carrera, Long> codCarrera;
	public static volatile SingularAttribute<Carrera, Date> fechaCreacion;
	public static volatile SingularAttribute<Carrera, Date> fechaModificacion;
	public static volatile SingularAttribute<Carrera, String> mencion;
	public static volatile SingularAttribute<Carrera, Integer> duracion;

}

