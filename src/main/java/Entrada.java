import com.mongodb.client.*;
import dao.AlumnoDAO;
import dao.ProfesorDAO;
import database.DBScheme;
import model.Alumno;
import model.Profesor;
import org.bson.Document;

import java.util.Arrays;
import java.util.Scanner;

public class Entrada {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Crear conexión MongoDB
        String connectionString = "mongodb+srv://%s:%s@cluster0.lxada.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        MongoClient mongoClient = MongoClients.create(String.format(connectionString, DBScheme.USER, DBScheme.PASS));

        MongoDatabase database = mongoClient.getDatabase("centro_estudios");
        MongoCollection<Document> alumnosCollection = database.getCollection("alumnos");
        MongoCollection<Document> profesoresCollection = database.getCollection("profesores");

        // Crear instancias de DAOs
        AlumnoDAO alumnoDAO = new AlumnoDAO(alumnosCollection);
        ProfesorDAO profesorDAO = new ProfesorDAO(profesoresCollection);

        int opcion;
        do {
            mostrarOpciones();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1:
                    insertarProfesor(scanner, profesorDAO);
                    break;
                case 2:
                    insertarAlumno(scanner, alumnoDAO);
                    break;
                case 3:
                    mostrarTodos(alumnoDAO, profesorDAO);
                    break;
                case 4:
                    mostrarProfesores(profesorDAO);
                    break;
                case 5:
                    mostrarAlumnos(alumnoDAO);
                    break;
                case 6:
                    buscarAlumno(scanner, alumnoDAO);
                    break;
                case 7:
                    buscarProfesor(scanner, profesorDAO);
                    break;
                case 8:
                    actualizarProfesor(scanner, profesorDAO);
                    break;
                case 9:
                    eliminarAlumnosConAltaCalificacion(alumnoDAO);
                    break;
                case 10:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 10);

        mongoClient.close();
    }

    public static void mostrarOpciones() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Insertar profesor");
        System.out.println("2. Insertar alumno");
        System.out.println("3. Mostrar todos los datos");
        System.out.println("4. Mostrar profesores");
        System.out.println("5. Mostrar alumnos");
        System.out.println("6. Buscar alumno por email");
        System.out.println("7. Buscar profesor por rango de edad");
        System.out.println("8. Actualizar calificación de profesor");
        System.out.println("9. Dar de baja alumnos con calificación >= 5");
        System.out.println("10. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // Delegar operaciones a los DAOs
    public static void insertarProfesor(Scanner scanner, ProfesorDAO profesorDAO) {
        System.out.println("\n--- Insertar Profesor ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        System.out.print("Género: ");
        String genero = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Asignaturas (separadas por comas): ");
        String[] asignaturas = scanner.nextLine().split(",");
        System.out.print("Rating: ");
        double rating = scanner.nextDouble();

        // Crear el objeto Profesor
        Profesor profesor = new Profesor();
        profesor.setName(nombre);
        profesor.setAge(edad);
        profesor.setGender(genero);
        profesor.setEmail(email);
        profesor.setPhone(telefono);
        profesor.setTitle(titulo);
        profesor.setSubjects(Arrays.asList(asignaturas));
        profesor.setRating(rating);

        // Pasar el objeto Profesor al ProfesorDAO para la inserción
        profesorDAO.insertarProfesor(profesor);
    }

    // Delegar inserción de alumno
    public static void insertarAlumno(Scanner scanner, AlumnoDAO alumnoDAO) {
        System.out.println("\n--- Insertar Alumno ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Género: ");
        String genero = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Calificación: ");
        double calificacion = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Grado superior: ");
        String gradoSuperior = scanner.nextLine();
        System.out.print("Rating: ");
        double rating = scanner.nextDouble();


        // Crear el objeto Alumno
        Alumno alumno = new Alumno();
        alumno.setName(nombre);
        alumno.setAge(edad);
        alumno.setGender(genero);
        alumno.setEmail(email);
        alumno.setPhone(telefono);
        alumno.setCalification(calificacion);
        alumno.setHigherGrade(gradoSuperior);
        alumno.setRating(rating);
        alumno.setFct(true);  // Asumir que siempre se marca como verdadero, o agregar lógica aquí

        alumnoDAO.insertarAlumno(alumno);
    }

    public static void mostrarTodos(AlumnoDAO alumnoDAO, ProfesorDAO profesorDAO) {
        System.out.println("\n--- Mostrar Todos ---");
        profesorDAO.mostrarProfesores();
        alumnoDAO.mostrarAlumnos();
    }

    public static void mostrarProfesores(ProfesorDAO profesorDAO) {
        System.out.println("\n--- Mostrar Profesores ---");
        profesorDAO.mostrarProfesores();
    }

    public static void mostrarAlumnos(AlumnoDAO alumnoDAO) {
        System.out.println("\n--- Mostrar Alumnos ---");
        alumnoDAO.mostrarAlumnos();
    }

    public static void buscarAlumno(Scanner scanner, AlumnoDAO alumnoDAO) {
        System.out.print("\nIngrese el email del alumno a buscar: ");
        String email = scanner.nextLine();
        alumnoDAO.buscarAlumno(email);
    }

    public static void buscarProfesor(Scanner scanner, ProfesorDAO profesorDAO) {
        System.out.print("\nIngrese la edad mínima: ");
        int edadMinima = scanner.nextInt();
        System.out.print("Ingrese la edad máxima: ");
        int edadMaxima = scanner.nextInt();

        profesorDAO.buscarProfesorPorEdad(edadMinima, edadMaxima);
    }

    public static void actualizarProfesor(Scanner scanner, ProfesorDAO profesorDAO) {
        System.out.print("\nIngrese el email del profesor a actualizar: ");
        String email = scanner.nextLine();
        System.out.print("Ingrese la nueva calificación: ");
        double nuevaCalificacion = scanner.nextDouble();

        profesorDAO.actualizarProfesor(email, nuevaCalificacion);
    }

    public static void eliminarAlumnosConAltaCalificacion(AlumnoDAO alumnoDAO) {
        alumnoDAO.eliminarAlumnosConAltaCalificacion();
    }
}
