package crm.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Region.class)
public abstract class Region_ {

	public static volatile SingularAttribute<Region, String> nombre;
	public static volatile SingularAttribute<Region, Short> id;
	public static volatile ListAttribute<Region, Provincia> provincias;
	public static volatile SingularAttribute<Region, Integer> rutUsuario;
	public static volatile SingularAttribute<Region, Date> fechaModificacion;

}

