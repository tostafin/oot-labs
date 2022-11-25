package pl.edu.agh.to.school.grade;

import pl.edu.agh.to.school.course.Course;

import javax.persistence.*;

@Entity
public class Grade {
    @Id
    @GeneratedValue
    private int id;
    private int gradeValue;

    @OneToOne
    private Course course;

    public Grade() {
    }

    public Grade(int gradeValue, Course course) {
        this.gradeValue = gradeValue;
        this.course = course;
    }
}
