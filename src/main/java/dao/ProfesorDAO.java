package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import model.Profesor;
import org.bson.Document;
import java.util.List;

public class ProfesorDAO {

    private MongoCollection<Document> profesoresCollection;

    public ProfesorDAO(MongoCollection<Document> profesoresCollection) {
        this.profesoresCollection = profesoresCollection;
    }

    // Insertar un profesor
    public void insertarProfesor(Profesor profesor) {
        // Crear un documento MongoDB usando los valores del objeto profesor
        Document document = new Document()
                .append("rating", profesor.getRating())
                .append("age", profesor.getAge())
                .append("name", profesor.getName())
                .append("gender", profesor.getGender())
                .append("email", profesor.getEmail())
                .append("phone", profesor.getPhone())
                .append("subjects", profesor.getSubjects())
                .append("title", profesor.getTitle());

        // Insertar el documento en la colección de profesores
        profesoresCollection.insertOne(document);

        System.out.println("Profesor insertado correctamente: " + profesor.getName());
    }

    // Mostrar todos los profesores
    public void mostrarProfesores() {
        // Obtener los documentos de todos los profesores
        FindIterable<Document> iterable = profesoresCollection.find();
        MongoCursor<Document> cursor = iterable.iterator();

        // Verificar si hay resultados y recorrerlos
        if (cursor.hasNext()) {
            while (cursor.hasNext()) {
                Document profesor = cursor.next();
                System.out.println(profesor.toJson());
            }
        } else {
            // Si no se encuentran resultados
            System.out.println("No se encontraron profesores.");
        }
    }

    // Buscar un profesor por rango de edad
    public void buscarProfesorPorEdad(int edadMinima, int edadMaxima) {
        Document busqueda = new Document("age", new Document("$gte", edadMinima).append("$lte", edadMaxima));

        // Obtener los resultados con find() y usar un cursor para iterar
        FindIterable<Document> iterable = profesoresCollection.find(busqueda);
        MongoCursor<Document> cursor = iterable.iterator();

        // Verificar si hay resultados y recorrerlos
        if (cursor.hasNext()) {
            while (cursor.hasNext()) {
                Document profesor = cursor.next();
                System.out.println(profesor.toJson());
            }
        } else {
            System.out.println("No se encontró ningún profesor en el rango de edad especificado.");
        }
    }

    // Actualizar la calificación de un profesor
    public void actualizarProfesor(String email, double nuevaCalificacion) {
        Document busqueda = new Document("email", email);

        FindIterable<Document> iterable = profesoresCollection.find(busqueda);
        MongoCursor<Document> cursor = iterable.iterator();

        if (cursor.hasNext()) {

            Document update = new Document("$set", new Document("rating", nuevaCalificacion));

            UpdateResult result = profesoresCollection.updateOne(busqueda, update);

            if (result.getModifiedCount() > 0) {
                System.out.println("Profesor actualizado correctamente.");
            }
        } else {
            System.out.println("No se encontró un profesor con ese email.");
        }
    }

}
