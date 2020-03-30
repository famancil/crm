package crm.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Colegio.class)
public abstract class Colegio_ {

	public static volatile SingularAttribute<Colegio, String> direccion;
	public static volatile SingularAttribute<Colegio, Pais> pais;
	public static volatile SingularAttribute<Colegio, TipoVigencia> vigencia;
	public static volatile SingularAttribute<Colegio, Integer> codigo;
	public static volatile SingularAttribute<Colegio, Comuna> comuna;
	public static volatile SingularAttribute<Colegio, String> email;
	public static volatile SingularAttribute<Colegio, String> nombreOficial;
	public static volatile SingularAttribute<Colegio, Integer> rutUsuario;
	public static volatile SingularAttribute<Colegio, Date> fechaCreacion;
	public static volatile SingularAttribute<Colegio, Integer> fonoPrincipal;
	public static volatile SingularAttribute<Colegio, Date> fechaModificacion;
	public static volatile SingularAttribute<Colegio, Integer> rbd;

}

