package crm.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comuna.class)
public abstract class Comuna_ {

	public static volatile SingularAttribute<Comuna, String> nombre;
	public static volatile SingularAttribute<Comuna, Short> codigo;
	public static volatile SingularAttribute<Comuna, Ciudad> ciudad;
	public static volatile SingularAttribute<Comuna, Pais> pais;
	public static volatile SingularAttribute<Comuna, Integer> rutUsuario;
	public static volatile SingularAttribute<Comuna, Date> fechaModificacion;
	public static volatile SingularAttribute<Comuna, Provincia> provincia;
	public static volatile SingularAttribute<Comuna, Short> codigoArea;

}

