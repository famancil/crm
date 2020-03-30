package crm.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase para transformar celulares a la forma +569 1234 1234
 * Acepta celulares de la forma +56912341234|56912341234|912341234|0912341234|12341234
 */
public class CelularParser {

    /**
     * Regex utilizado para determinar si el numero ingresado es un celular o no.
     * Tambien se utiliza para capturar los 8 digitos del celular.
     */
    private static String pattern = "^(\\+?569|0?9)?(\\d{8})$";

    /**
     * Funcion que transforma un celular ingresado a la forma +569 1234 1234
     * @param celular Numero telefonico a ser transformado.
     * @return String Numero telefonico transformado.
     */
    public static String parse(String celular) {
        String result = "";
        String prefix = "+56 9 ";

        celular = celular.replaceAll("\\s+", "");

        Pattern pattern = Pattern.compile(CelularParser.pattern);

        Matcher matcher = pattern.matcher(celular);

        if (matcher.find()) {
            result = prefix + result.substring(0, 4) + " " + result.substring(4, 8);
            return result;
        }

        return result;
    }

    /**
     * Funcion que determina si un numero tiene la forma de un numero de celular
     * @param celular El numero a probar
     * @return Bool true si es un celular valido, false en otro caso
     */
    public static boolean esValido(String celular) {
        celular = celular.replaceAll("\\s+", "");

        Pattern pattern = Pattern.compile(CelularParser.pattern);

        Matcher matcher = pattern.matcher(celular);

        return matcher.find();
    }
}
