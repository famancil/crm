Sistema CRM de la Red de Ex Alumnos

Guia de instalación: 

Luego de haber clonado el repositorio en nuestro computador se procede a configurar el IDE, para este ejemplo usaremos:

  -IDE IntelliJ IDEA 2016.2 - (https://www.jetbrains.com/idea/)
  
  -Gradle 2.13 (no se ha comprobado compatibilidad con versiones posteriores) - (https://services.gradle.org/distributions/gradle-2.13-bin.zip)

  -Java JDK 1.7.0_79 - (http://www.oracle.com/technetwork/es/java/javase/downloads/jdk7-downloads-1880260.html)

Una vez descargadas las herramientas anteriores procedemos a instalar IntelliJ IDEA, dejando todas las opciones por defecto. 

Cuando se ha instalado correctamente debemos setear el SDK del proyecto como se muestra a continuación

![alt tag](https://github.com/diegoacuna/crm/blob/master/var/Paso%201.png)

Con esto, se abrirá una ventana donde tenemos que ir a la opción SDKs en el menú de la izquiera y agregar la ruta del JDK de Java que hemos descargado previamente

![alt tag](https://github.com/diegoacuna/crm/blob/master/var/Paso%202.png)

![alt tag](https://github.com/diegoacuna/crm/blob/master/var/Paso%202.1.png)

Luego de esto, procedemos a importar el proyecto desde el archivo build.gradle que se encuentra dentro del repositorio que hemos clonado previamente

![alt tag](https://github.com/diegoacuna/crm/blob/master/var/Paso%203.png)

Finalmente, se deben configurar el Gradle Home y Gradle JVM como se aprecia a continuación

![alt tag](https://github.com/diegoacuna/crm/blob/master/var/Paso%204.png)

Luego de esto, IntelliJ comenzará a descargar todas las dependencias necesarias para el proyecto y una vez terminado esto estaremos listos para trabajar




  
