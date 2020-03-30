package crm.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Institucion.class)
public abstract class Institucion_ {

	public static volatile SingularAttribute<Institucion, String> direccion;
	public static volatile SingularAttribute<Institucion, Pais> pais;
	public static volatile SingularAttribute<Institucion, Short> codInstitucion;
	public static volatile SingularAttribute<Institucion, TipoVigencia> vigencia;
	public static volatile SingularAttribute<Institucion, Integer> rutUsuario;
	public static volatile SingularAttribute<Institucion, String> telefono;
	public static volatile SingularAttribute<Institucion, Date> fechaCreacion;
	public static volatile SingularAttribute<Institucion, Date> fechaModificacion;
	public static volatile SingularAttribute<Institucion, String> nomInstitucion;

}

