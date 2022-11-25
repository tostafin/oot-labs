package pl.edu.agh.to.school.course;

import pl.edu.agh.to.school.student.Student;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    @OneToMany
    private List<Student> students;

    public Course() {
    }

    public Course(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }
}
