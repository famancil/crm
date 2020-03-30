package crm.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Provincia.class)
public abstract class Provincia_ {

	public static volatile SingularAttribute<Provincia, Region> region;
	public static volatile SingularAttribute<Provincia, String> nombre;
	public static volatile SingularAttribute<Provincia, Short> id;
	public static volatile SingularAttribute<Provincia, Pais> pais;
	public static volatile ListAttribute<Provincia, Comuna> comunas;
	public static volatile SingularAttribute<Provincia, Integer> rutUsuario;
	public static volatile SingularAttribute<Provincia, Date> fechaModificacion;

}

