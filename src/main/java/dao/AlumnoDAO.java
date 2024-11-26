package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import model.Alumno;
import org.bson.Document;
import java.util.List;

public class AlumnoDAO {

    //MongoCollection alumnosCollection;

    private MongoCollection<Document> alumnosCollection;

    public AlumnoDAO(MongoCollection<Document> alumnosCollection) {
        this.alumnosCollection = alumnosCollection;
    }

    // Insertar un alumno
    public void insertarAlumno(Alumno alumno) {
        // Crear un documento MongoDB usando los valores del objeto alumno
        Document document = new Document()
                .append("rating", alumno.getRating())
                .append("age", alumno.getAge())
                .append("name", alumno.getName())
                .append("gender", alumno.getGender())
                .append("email", alumno.getEmail())
                .append("phone", alumno.getPhone())
                .append("calification", alumno.getCalification())
                .append("higher_grade", alumno.getHigherGrade())
                .append("fct", alumno.isFct());  // Si tienes un campo FCT en tu clase

        // Insertar el documento en la colección de alumnos
        alumnosCollection.insertOne(document);

        System.out.println("Alumno insertado correctamente: " + alumno.getName());
    }

    // Mostrar todos los alumnos
    public void mostrarAlumnos() {
        // Obtener los documentos de todos los alumnos
        FindIterable<Document> iterable = alumnosCollection.find();
        MongoCursor<Document> cursor = iterable.iterator();

        // Verificar si hay resultados y recorrerlos
        if (cursor.hasNext()) {
            while (cursor.hasNext()) {
                Document alumno = cursor.next();
                System.out.println(alumno.toJson());
            }
        } else {
            // Si no se encuentran resultados
            System.out.println("No se encontraron alumnos.");
        }
    }


    // Buscar un alumno por email
    public void buscarAlumno(String email) {
        Document busqueda = new Document("email", email);
        FindIterable<Document> iterable = alumnosCollection.find(busqueda);
        MongoCursor<Document> cursor = iterable.iterator();

        if (cursor.hasNext()) {
            while (cursor.hasNext()) {
                Document alumno = cursor.next();
                System.out.println(alumno.toJson());
            }
        } else {
            // If no document is found, print a message
            System.out.println("No se encontró un alumno con ese email.");
        }
    }

    // Eliminar alumnos con calificación >= 5
    public void eliminarAlumnosConAltaCalificacion() {
        // Crear la consulta para alumnos con calificación >= 5
        Document busqueda = new Document("calificacion", new Document("$gte", 5));

        FindIterable<Document> iterable = alumnosCollection.find(busqueda);
        MongoCursor<Document> cursor = iterable.iterator();

        int contador = 0;

        while (cursor.hasNext()) {
            Document alumno = cursor.next();
            System.out.println("Alumno a eliminar: " + alumno.toJson());
            contador++;
        }

        DeleteResult result = alumnosCollection.deleteMany(busqueda);

        System.out.println(result.getDeletedCount() + " alumnos con calificación >= 5 han sido eliminados.");
        if (contador == 0) {
            System.out.println("No se encontraron alumnos con calificación >= 5.");
        }
    }

}