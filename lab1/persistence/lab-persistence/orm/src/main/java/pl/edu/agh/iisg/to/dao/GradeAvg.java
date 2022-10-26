package pl.edu.agh.iisg.to.dao;

import pl.edu.agh.iisg.to.model.Course;

public class GradeAvg {
    private final Course course;
    private final double gradeAvg;

    public GradeAvg(Course course, double gradeAvg) {
        this.course = course;
        this.gradeAvg = gradeAvg;
    }

    public Course getCourse() {
        return course;
    }

    public double getGradeAvg() {
        return gradeAvg;
    }
}
