package crm.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;




/**
 * Clase que permite manejar la compresion de archivos, en un contenedor zip
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class Compresion {





    /**
     * Crea un archivo comprimido
     *
     * @param rutasArchivos {@link List} con los nombres de los archivos a comprimir (como ruta absoluta desde donde serán leidos)
     * @param directorioDestino Directorio de destino, donde se desea dejar el archivo comprimido
     * @param extension Extensión del archivo comprimido
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    public void crearComprimido(List<String> rutasArchivos, String directorioDestino, String extension) {

        if (extension.compareTo(".zip") == 0) {
            try {

                // create byte buffer
                byte[] buffer = new byte[1024];

                FileOutputStream fos = new FileOutputStream(directorioDestino + ".zip");

                ZipOutputStream zos = new ZipOutputStream(fos);

                for (int i=0; i < rutasArchivos.size(); i++) {

                    File srcFile = new File(rutasArchivos.get(i));

                    FileInputStream fis = new FileInputStream(srcFile);

                    // begin writing a new ZIP entry, positions the stream to the start of the entry data
                    zos.putNextEntry(new ZipEntry(srcFile.getName()));

                    int length;

                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }

                    zos.closeEntry();

                    // close the InputStream
                    fis.close();

                }

                // close the ZipOutputStream
                zos.close();

            }
            catch (IOException ioe) {
                System.out.println("Error creating zip file: " + ioe);
            }
        }
    }
}
