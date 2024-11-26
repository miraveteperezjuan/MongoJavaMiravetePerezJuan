package database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Alumno;
import model.Profesor;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoDBConnection {

    private String connectionString = "mongodb+srv://%s:%s@cluster0.lxada.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private MongoClient mongoClient;
    private CodecRegistry codecRegistry;

    public MongoDBConnection() {
        // Crear PojoCodecProvider para las clases que se van a usar con MongoDB
        PojoCodecProvider codecProvider = PojoCodecProvider.builder().automatic(true).build();

        // Crear un CodecRegistry combinando los CodecProviders
        codecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromProviders(codecProvider),
                MongoClients.create().getCodecRegistry()
        );

        // Establecer la conexión con MongoDB utilizando el connection string
        mongoClient = MongoClients.create(String.format(connectionString, DBScheme.USER, DBScheme.PASS));
    }

    public MongoCollection getAlumnosCollection() {
        MongoDatabase database = mongoClient.getDatabase("centro_estudios").withCodecRegistry(codecRegistry);
        return database.getCollection("alumnos", Alumno.class);
    }

    public MongoCollection getProfesoresCollection() {
        MongoDatabase database = mongoClient.getDatabase("centro_estudios").withCodecRegistry(codecRegistry);
        return database.getCollection("profesores", Profesor.class);
    }

}
