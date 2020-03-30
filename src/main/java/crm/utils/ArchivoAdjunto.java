package crm.utils;

import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Clase que permite manejar la descarga de archivos
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class ArchivoAdjunto {




    /**
     * Maneja la descarga de archivos como response HTTP
     *
     * @param response
     * @param rutaArchivoDescargar ruta del archivo que se va a descargar
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    public void generarArchivoAdjunto ( HttpServletResponse response, String rutaArchivoDescargar) throws IOException {
        File file = new File(rutaArchivoDescargar);

        if(!file.exists()){
            String errorMessage = "Existen problemas en la descarga del archivo";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }

        String mimeType= URLConnection.guessContentTypeFromName(file.getName());

        if(mimeType == null){
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);

        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

        response.setContentLength((int) file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        FileCopyUtils.copy(inputStream, response.getOutputStream());

        file.delete();
    }

}
