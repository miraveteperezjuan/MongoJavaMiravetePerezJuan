package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Profesor {

    @BsonProperty("rating")
    private double rating;
    @BsonProperty("age")
    private int age;
    @BsonProperty("name")
    private String name;
    @BsonProperty("gender")
    private String gender;
    @BsonProperty("email")
    private String email;
    @BsonProperty("phone")
    private String phone;
    @BsonProperty("subjects")
    private List<String> subjects;
    @BsonProperty("title")
    private String title;

    public void mostrarDatos() {
        System.out.println("Nombre: " + name);
        System.out.println("Edad: " + age);
        System.out.println("Correo: " + email);
        System.out.println("Título: " + title);
    }

}
