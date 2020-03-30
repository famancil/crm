package crm;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Clase initializer necesaria para el bootstrap de la aplicacion en un entorno de deploy war.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configura la aplicacion. En general solo se le deben entregar sources con parametros
	 * de configuracion particulares para la aplicacion. En este caso se utiliza aquella configuracion
	 * proveniente de {@link crm.Application}.
	 *
	 * @param application builder para el application context.
	 * @see org.springframework.boot.builder.SpringApplicationBuilder
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

}
