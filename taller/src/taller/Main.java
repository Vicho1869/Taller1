// Vicente Jara - 22108526-4 - icci 




package taller;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static final int MAX = 100;

    static String[] grupoNombre = new String[MAX];
    
    static String[] grupoApellido = new String[MAX];
    
    static String[] grupoRut = new String[MAX];
    static String[] grupoParalelo = new String[MAX];
    static int totalGrupo = 0;

    static String[] alumnoNombre = new String[MAX];
    static String[] alumnoApellido = new String[MAX];
    
    
    static String[] alumnoRut = new String[MAX];
    static String[] alumnoParalelo = new String[MAX];
    static int totalAlumnos = 0;
    

    static String[] solNombre = new String[MAX];
    
    
    static String[] solApellido = new String[MAX];
    static int totalSolicitudes = 0;

    static String[] rechNombre = new String[MAX];
    static String[] rechApellido = new String[MAX];
    static String[] rechRut = new String[MAX];
    
    
    
    static int totalRechazados = 0;

    static int inscManual = 0;
    
    
    static int inscArchivo = 0;
    
    

    static boolean archivosCargados = false;

    public static void main(String[] args) {

        int opcion;

        do {
            opcion = mostrarMenu();

            if (opcion == 1) {
            	
            	
                cargarArchivos();
                
            } else if (opcion == 2) {
            	
            	
                if (!archivosCargados) {
                	
                    System.out.println("Debe cargar los archivos primero (opcion 1)");
                } else {
                	
                    procesarSolicitudes();
                }
            } else if (opcion == 3) {
            	
            	
            	
            	
                if (!archivosCargados) {
                    System.out.println("Debe cargar los archivos primero (opcion 1)");
                } else {
                    inscripcionManual();
                    
                    
                    
                }
            } else if (opcion == 4) {
                if (!archivosCargados) {
                    System.out.println("Debe cargar los archivos primero (opcion 1)");
                } else {
                	
                    administracionCurso();
                }
                
            } else if (opcion == 5) {
            	
                if (!archivosCargados) {
                    System.out.println("Debe cargar los archivos primero (opcion 1)");
                } else {
                    generarReportes();
                }
                
                
            } else if (opcion == 6) {
            	
            	
            	
                if (!archivosCargados) {
                    System.out.println("Debe cargar los archivos primero (opcion 1)");
                } else {
                	
                	
                    analisisEstadistico();
                }
            } else if (opcion == 7) {
            	
            	
            	
                System.out.println("Saliendo del sistema...");
            } else {
                System.out.println("Opcion invalida, intente de nuevo");
            }

        } while (opcion != 7);

        
    }

    private static int mostrarMenu() {
    	
    	
        System.out.println("\n===== Sistema de Control del Grupo POO =====");
        System.out.println("1) Cargar archivos (Alumnos y Solicitudes)");
        System.out.println("2) Procesar solicitudes (Filtrado automatico)");
        System.out.println("3) Inscripcion manual al grupo");
        System.out.println("4) Administracion del curso");
        System.out.println("5) Generar reportes");
        System.out.println("6) Analisis estadistico");
        System.out.println("7) Salir");

        System.out.print("Ingrese opcion: ");

        if (!scanner.hasNextInt()) {
        	
        	
            scanner.nextLine();
            return -1;
        }

        int opcion = scanner.nextInt();
        scanner.nextLine();

        return opcion;
    }

    private static void cargarArchivos() {
    	
    	
        totalAlumnos = 0;
        totalSolicitudes = 0;
        totalGrupo = 0;
        totalRechazados = 0;

        try {
            File archivoAlumnos = new File("Alumnos.txt");
            
            Scanner lectorAlumnos = new Scanner(archivoAlumnos);

            while (lectorAlumnos.hasNextLine() && totalAlumnos < MAX) {
            	
                String linea = lectorAlumnos.nextLine();
                
                String[] partes = linea.split(";");

                if (partes.length == 4) {
                    alumnoNombre[totalAlumnos] = partes[0].trim();
                    
                    alumnoApellido[totalAlumnos] = partes[1].trim();
                    
                    alumnoRut[totalAlumnos] = partes[2].trim();
                    
                    alumnoParalelo[totalAlumnos] = partes[3].trim();
                    
                    totalAlumnos++;
                }
            }
            lectorAlumnos.close();
        } catch (FileNotFoundException e) {
        	
        	
            System.out.println("No se encontro el archivo Alumnos.txt");
            return;
        }

        try {
            File archivoSol = new File("Solicitudes.txt");
            Scanner lectorSol = new Scanner(archivoSol);

            while (lectorSol.hasNextLine() && totalSolicitudes < MAX) {
                String linea = lectorSol.nextLine();
                String[] partes = linea.split("-");

                if (partes.length >= 2) {
                    solNombre[totalSolicitudes] = partes[0].trim();
                    solApellido[totalSolicitudes] = partes[1].trim();
                    totalSolicitudes++;
                }
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo Solicitudes.txt");
            return;
        }

        archivosCargados = true;
        
        System.out.println("archivos cargados con exito");
        System.out.println("- Alumnos cargados: " + totalAlumnos);
        System.out.println("- Solicitudes cargadas: " + totalSolicitudes);
    }

    private static void procesarSolicitudes() {
        totalGrupo = 0;
        totalRechazados = 0;
        
        
        inscArchivo = 0;
        int aceptados = 0;
        int rechazados = 0;

        System.out.println("Procesando solicitudes...\n");

        for (int i = 0; i < totalSolicitudes; i++) {
            int indice = -1;

            
            for (int j = 0; j < totalAlumnos; j++) {
            	
                if (solNombre[i].equalsIgnoreCase(alumnoNombre[j]) && solApellido[i].equalsIgnoreCase(alumnoApellido[j])) {
                    indice = j;
                }
            }

            if (indice != -1) {
                if (buscarEnGrupoPorRut(alumnoRut[indice]) != -1) {
                	
                	
                	
                    System.out.println("[REPETIDO] " + solNombre[i] + " " + solApellido[i] + " -> ya estaba admitido");
                    continue;
                }

                if (totalGrupo >= MAX) {
                    System.out.println("No hay espacio en el grupo, se detiene procesamiento.");
                    break;
                }

                grupoNombre[totalGrupo] = alumnoNombre[indice];
                
                grupoApellido[totalGrupo] = alumnoApellido[indice];
                
                grupoRut[totalGrupo] = alumnoRut[indice];
                
                grupoParalelo[totalGrupo] = alumnoParalelo[indice];
                totalGrupo++;
                
                aceptados++;
                
                inscArchivo++;
                System.out.println("[OK]       " + solNombre[i] + " " + solApellido[i] + " -> admitido en " + alumnoParalelo[indice]);
            } else {
                rechazados++;
                agregarRechazadoNombre(solNombre[i], solApellido[i]);
                
                
                System.out.println("[RECHAZO]  " + solNombre[i] + " " + solApellido[i] + " -> no pertenece a ningun paralelo");
            }
        }

        System.out.println("\nResumen: " + aceptados + " admitidos / " + rechazados + " rechazados.");
        
    }

    private static void inscripcionManual() {
        System.out.println("Como desea inscribir a la persona?");
        
        System.out.println("1) Por nombre completo");
        System.out.println("2) Por RUT");
        System.out.print("Ingrese opcion: ");

        if (!scanner.hasNextInt()) {
        	
        	
            scanner.nextLine();
            
            
            System.out.println("Opcion invalida");
            return;
        }

        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            System.out.print("Ingrese nombre: ");
            
            String nombre = scanner.nextLine().trim();
            
            
            
            System.out.print("Ingrese apellido: ");
            String apellido = scanner.nextLine().trim();

            if (nombre.isEmpty() || apellido.isEmpty()) {
                System.out.println("Nombre y apellido no pueden estar vacios");
                
                
                
                return;
            }

            int idx = buscarAlumnoPorNombre(nombre, apellido);

            
            
            
            
            if (idx == -1) {
                System.out.println(nombre + " " + apellido + " no pertenece a ningun paralelo del curso.");
                agregarRechazadoNombre(nombre, apellido);
                
                
                
                return;
            }

            agregarAlGrupoManual(idx);

        } else if (op == 2) {
            System.out.print("Ingrese RUT: ");
            
            
            
            String rut = scanner.nextLine().trim();

            if (rut.isEmpty()) {
                System.out.println("el rut no puede estar vacio");
                return;
            }

            int idx = buscarAlumnoPorRut(rut);
            
            

            if (idx == -1) {
                System.out.println("El RUT " + rut + " no pertece a ningun paralelo del curso.");
                System.out.println("No tenemos su nombre, por lo que se registrara solo el RUT en los rechazados.");
                
                
                
                agregarRechazadoRut(rut);
                return;
            }

            agregarAlGrupoManual(idx);

        } else {
            System.out.println("Opcion invalida");
        }
    }

    private static void agregarAlGrupoManual(int idxAlumno) {
    	
    	
        if (buscarEnGrupoPorRut(alumnoRut[idxAlumno]) != -1) {
            System.out.println("Esa persona ya es miembro del grupo.");
            return;
        }

        if (totalGrupo >= MAX) {
        	
        	
        	
            System.out.println("No hay espacio en el grupo.");
            return;
        }

        grupoNombre[totalGrupo] = alumnoNombre[idxAlumno];
        grupoApellido[totalGrupo] = alumnoApellido[idxAlumno];
        grupoRut[totalGrupo] = alumnoRut[idxAlumno];
        
        
        grupoParalelo[totalGrupo] = alumnoParalelo[idxAlumno];
        totalGrupo++;
        inscManual++;

        System.out.println(alumnoNombre[idxAlumno] + " " + alumnoApellido[idxAlumno] + " ingreso al grupo en " + alumnoParalelo[idxAlumno]);
    }

    private static void administracionCurso() {
        boolean seguir = true;

        while (seguir) {
            System.out.println("\n--- Administracion del curso ---");
            System.out.println("1) Cambiar paralelo de un alumno");
            System.out.println("2) Eliminar alumno del curso");
            System.out.println("3) Inscribir alumno nuevo");
            System.out.println("4) Volver");
            System.out.print("Ingrese opcion: ");

            if (!scanner.hasNextInt())
            
            {
                scanner.nextLine();
                System.out.println("Opcion invalida");
                continue;
            }

            int op = scanner.nextInt();
            
            
            scanner.nextLine();

            if (op == 1) {
                cambiarParalelo();
                
                
                
            } else if (op == 2) {
            	
            	
                eliminarAlumno();
            } else if (op == 3) {
            	
            	
                inscribirAlumnoNuevo();
            } else if (op == 4) {
            	
            	
            	
                seguir = false;
                
                
            } else {
                System.out.println("Opcion invalida");
            }
        }
    }

    private static void cambiarParalelo() {
        System.out.print("Ingrese RUT del alumno: ");
        
        
        String rut = scanner.nextLine().trim();
        

        int idx = buscarAlumnoPorRut(rut);

        if (idx == -1) {
            System.out.println("No existe un alumno con ese RUT.");
            return;
        }

        System.out.println("Alumno: " + alumnoNombre[idx] + " " + alumnoApellido[idx] + " (actualmente en " + alumnoParalelo[idx] + ")");
        
        
        System.out.print("Nuevo paralelo (C1/C2): ");
        String nuevoParalelo = scanner.nextLine().trim().toUpperCase();
        
        
        

        if (!nuevoParalelo.equals("C1") && !nuevoParalelo.equals("C2")) {
            System.out.println("Paralelo invalido, debe ser C1 o C2.");
            
            
            
            return;
            
            
        }

        alumnoParalelo[idx] = nuevoParalelo;

        int idxGrupo = buscarEnGrupoPorRut(alumnoRut[idx]);
        if (idxGrupo != -1) {
            grupoParalelo[idxGrupo] = nuevoParalelo;
        }

        guardarAlumnosArchivo();
        
        
        System.out.println("oarallelo actualizad Cambios guardados en Alumnos.txt");
    }

    private static void eliminarAlumno() {
        System.out.print("Ingrese RUT del alumno a elimnar: ");
        String rut = scanner.nextLine().trim();

        int idx = buscarAlumnoPorRut(rut);

        if (idx == -1) {
            System.out.println("No existe u alumno con ese RUT.");
            return;
        }

        System.out.println("elliminando a " + alumnoNombre[idx] + " " + alumnoApellido[idx] + "...");

        for (int i = idx; i < totalAlumnos - 1; i++) {
            alumnoNombre[i] = alumnoNombre[i + 1];
            alumnoApellido[i] = alumnoApellido[i + 1];
            alumnoRut[i] = alumnoRut[i + 1];
            alumnoParalelo[i] = alumnoParalelo[i + 1];
        }
        totalAlumnos--;
        alumnoNombre[totalAlumnos] = null;
        
        alumnoApellido[totalAlumnos] = null;
        
        alumnoRut[totalAlumnos] = null;
        
        alumnoParalelo[totalAlumnos] = null;
        

        int idxGrupo = buscarEnGrupoPorRut(rut);
        
        if (idxGrupo != -1) {
        	
            for (int i = idxGrupo; i < totalGrupo - 1; i++) {
            	
            	
                grupoNombre[i] = grupoNombre[i + 1];
                grupoApellido[i] = grupoApellido[i + 1];
                
                grupoRut[i] = grupoRut[i + 1];
                grupoParalelo[i] = grupoParalelo[i + 1];
                
            }
            totalGrupo--;
            
            grupoNombre[totalGrupo] = null;
            
            grupoApellido[totalGrupo] = null;
            
            grupoRut[totalGrupo] = null;
            
            
            grupoParalelo[totalGrupo] = null;
            System.out.println("Tambien fue scado del grupo (habia ingresado).");
        }

        guardarAlumnosArchivo();
        
        System.out.println("alumno eliminad y cambios guardados en Alumnos.txt");
    }

    private static void inscribirAlumnoNuevo() {
        if (totalAlumnos >= MAX) {
        	
        	
            System.out.println("No hay espacio para nuevos alumnos.");
            return;
        }

        System.out.print("Nombre: ");
        
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        
        String apellido = scanner.nextLine().trim();
        System.out.print("RUT: ");
        
        String rut = scanner.nextLine().trim();
        
        System.out.print("Paralelo (C1/C2): ");
        String paralelo = scanner.nextLine().trim().toUpperCase();

        if (nombre.isEmpty() || apellido.isEmpty() || rut.isEmpty()) {
            System.out.println("Ningun camo puede quedar vacio.");
            return;
        }

        if (!paralelo.equals("C1") && !paralelo.equals("C2")) {
        	
        	
            System.out.println("paralelo invalido debe ser C1 o C2.");
            return;
        }

        if (buscarAlumnoPorRut(rut) != -1) {
            System.out.println("ya existe un alumno con ese RUT.");
            return;
        }

        alumnoNombre[totalAlumnos] = nombre;
        alumnoApellido[totalAlumnos] = apellido;
        
        
        alumnoRut[totalAlumnos] = rut;
        alumnoParalelo[totalAlumnos] = paralelo;
        totalAlumnos++;
        
        

        
        
        guardarAlumnosArchivo();
        
        System.out.println("alumno inscito en la lista y guardado en Alumnos.txt");
        
        
        
        System.out.println("Recuerda que aun debe procesarse o inscribirse manualmente para entrar al grupo.");
    }

    private static void guardarAlumnosArchivo() {
    	
    	
        try {
            FileWriter fw = new FileWriter("Alumnos.txt");
            
            BufferedWriter bw = new BufferedWriter(fw);

            
            for (int i = 0; i < totalAlumnos; i++) {
                bw.write(alumnoNombre[i] + ";" + alumnoApellido[i] + ";" + alumnoRut[i] + ";" + alumnoParalelo[i]);
                bw.newLine();
            }
            

            
        } catch (IOException e) {
            System.out.println("error a guardar el archivo Alumnos.txt");
        }
    }

    private static void generarReportes() {
        File carpeta = new File("Reportes");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        boolean seguir = true;
        

        while (seguir) {
            System.out.println("\n--- Generar reportes ---");
            System.out.println("1 Reporte paralelo C1");
            System.out.println("2 Reporte paralelo C2");
            System.out.println("3 Reporte de rechazados");
            System.out.println("4 Volver");
            System.out.print("Ingrese opcion: ");

            if (!scanner.hasNextInt()) {
            	
            	
                scanner.nextLine();
                System.out.println("Opcion invalida");
                continue;
            }

            int op = scanner.nextInt();
            
            scanner.nextLine();

            if (op == 1) {
            	
                generarReporteParalelo("C1");
                
                
            } else if (op == 2) {
            	
            	
                generarReporteParalelo("C2");
                
                
            } else if (op == 3) {
            	
            	
                generarReporteRechazados();
                
                
            } else if (op == 4) {
            	
            	
                seguir = false;
            } else {
            	
            	
            	
                System.out.println("Opcion invalida");
            }
        }
    }

    private static int siguienteVersion(String prefijo) {
    	
    	
    	
        int version = 1;
        
        
        File archivo = new File("Reportes/" + prefijo + "-V" + version + ".txt");

        while (archivo.exists()) {
        	
        	
        	
            version++;
            
            
            archivo = new File("Reportes/" + prefijo + "-V" + version + ".txt");
        }

        return version;
        
        
    }

    private static void generarReporteParalelo(String paralelo) {
    	
    	
        String prefijo = "Reporte" + paralelo;
        
        
        int version = siguienteVersion(prefijo);
        
        String nombreArchivo = "Reportes/" + prefijo + "-V" + version + ".txt";

        try {
        	
        	
            FileWriter fw = new FileWriter(nombreArchivo);
            
            
            
            BufferedWriter bw = new BufferedWriter(fw);
            
            

            bw.write("=== Miembros del grupo - Paralelo " + paralelo + " ===");
            
            
            bw.newLine();

            for (int i = 0; i < totalGrupo; i++) {
                if (grupoParalelo[i].equalsIgnoreCase(paralelo)) {
                    bw.write(grupoNombre[i] + " " + grupoApellido[i] + " - " + grupoRut[i]);
                    bw.newLine();
                }
            }

          
            
            
            System.out.println("reporte generado " + nombreArchivo);
            
            
        } catch (IOException e) {
        	
        	
            System.out.println("rrror al generar el reporte " + paralelo);
        }
    }

    private static void generarReporteRechazados() {
    	
    	
        int version = siguienteVersion("Rechazados");
        
        
        String nombreArchivo = "Reportes o Rechazados-V" + version + ".txt";

        try {
        	
        	
        	
            FileWriter fw = new FileWriter(nombreArchivo);
            
            
            BufferedWriter bw = new BufferedWriter(fw);
            
            
            

            bw.write("=== Solicitudes rechazadas ===");
            
            
            bw.newLine();

            for (int i = 0; i < totalRechazados; i++) {
            	
            	
            	
                if (rechNombre[i] != null) {
                	
                	
                	
                    bw.write(rechNombre[i] + " " + rechApellido[i] + " - No pertenece a ningun paralelo del curso");
                } else {
                    bw.write("Sin nombre registrado, RUT: " + rechRut[i]);
                }
                bw.newLine();
                
                
                
            }

            
            
            
            bw.close();
            System.out.println("Reporte generado: " + nombreArchivo);
        } catch (IOException e) {
        	
        	
        	
            System.out.println("Error al generar el reporte de rechazados");
        }
    }

    private static void analisisEstadistico() {
    	
    	
    	
        System.out.println("\n--- Analisis estadistico ---");

        
        
        int totalIntentos = totalSolicitudes;
        
        
        
        double porcRechazo = totalIntentos == 0 ? 0 : (totalRechazados * 100.0) / totalIntentos;

        System.out.println("tota de intentos de ingreso (solicitudes): " + totalIntentos);
        System.out.println("rechazados: " + totalRechazados + " (" + String.format("%.1f", porcRechazo) + "%)");

        int c1 = 0;
        int c2 = 0;
        
        
        for (int i = 0; i < totalGrupo; i++) {
            if (grupoParalelo[i].equalsIgnoreCase("C1")) {
                c1++;
                
                
            } else if (grupoParalelo[i].equalsIgnoreCase("C2")) {
            	
            	
                c2++;
            }
        }
        System.out.println("Miembros del grupo -> C1: " + c1 + " | C2: " + c2);

        double tasaAdmision = totalIntentos == 0 ? 0 : (totalGrupo * 100.0) / totalIntentos;
        System.out.println("Tasa de admision: " + String.format("%.1f", tasaAdmision) + "%");

        int alumnosC1 = 0;
        
        int alumnosC2 = 0;
        
        for (int i = 0; i < totalAlumnos; i++) {
        	
        	
        	
            if (alumnoParalelo[i].equalsIgnoreCase("C1")) {
                alumnosC1++;
            } else if (alumnoParalelo[i].equalsIgnoreCase("C2")) {
                alumnosC2++;
            }
        }
        
        
        System.out.println("Alumnos inscritos en el curso -> C1: " + alumnosC1 + " | C2: " + alumnosC2);
        
        
        if (alumnosC1 >= alumnosC2) {
        	
        	
        	
            System.out.println("Paralelo con mas alumnos: C1");
            
            
            
        } else {
        	
        	
        	
            System.out.println("Paralelo con mas alumnos: C2");
        }

        int soloRut = 0;
        
        
        
        for (int i = 0; i < totalRechazados; i++) {
        	
        	
            if (rechNombre[i] == null) {
            	
            	
                soloRut++;
            }
        }
        System.out.println("Rechazados de los que solo se tiene el RUT: " + soloRut);

        System.out.println("Inscripciones manuales: " + inscManual + " | Inscripciones por archivo: " + inscArchivo);

        int duplicadas = 0;
        for (int i = 0; i < totalSolicitudes; i++) {
        	
        	
        	
            for (int j = i + 1; j < totalSolicitudes; j++) {
            	
            	
            	
                if (solNombre[i].equalsIgnoreCase(solNombre[j]) && solApellido[i].equalsIgnoreCase(solApellido[j])) {
                    duplicadas++;
                    break;
                }
            }
        }
        System.out.println("Solicitudes duplicadas (personas que intentaron mas de una vez): " + duplicadas);
    }

    private static int buscarAlumnoPorNombre(String nombre, String apellido) {
    	
    	
    	
        for (int i = 0; i < totalAlumnos; i++) {
        	
        	
        	
            if (alumnoNombre[i].equalsIgnoreCase(nombre) && alumnoApellido[i].equalsIgnoreCase(apellido)) {
                return i;
            }
        }
        return -1;
    }

    private static int buscarAlumnoPorRut(String rut) {
    	
    	
        for (int i = 0; i < totalAlumnos; i++) {
        	
        	
            if (alumnoRut[i].equalsIgnoreCase(rut)) {
                return i;
            }
        }
        return -1;
    }

    private static int buscarEnGrupoPorRut(String rut) {
    	
    	
        for (int i = 0; i < totalGrupo; i++) {
        	
        	
            if (grupoRut[i].equalsIgnoreCase(rut)) {
                return i;
            }
        }
        return -1;
    }

    private static void agregarRechazadoNombre(String nombre, String apellido) {
    	
    	
        if (totalRechazados >= MAX) {
        	
        	
            System.out.println("No hay espacio para mas rechazados.");
            
            return;
        }
        rechNombre[totalRechazados] = nombre;
        
        
        rechApellido[totalRechazados] = apellido;
        
        
        rechRut[totalRechazados] = null;
        
        
        totalRechazados++;
    }

    private static void agregarRechazadoRut(String rut) {
    	
    	
    	
        if (totalRechazados >= MAX) {
        	
        	
        	
            System.out.println("No hay espacio para mas rechazados.");
            return;
        }
        rechNombre[totalRechazados] = null;
        
        rechApellido[totalRechazados] = null;
        
        rechRut[totalRechazados] = rut;
        totalRechazados++;
    }
}
