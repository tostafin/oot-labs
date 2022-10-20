package pl.edu.agh.iisg.to.dao;

import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;

import javax.persistence.PersistenceException;
import java.util.List;

public class GradeDao extends GenericDao<Grade> {

    public boolean gradeStudent(final Student student, final Course course, final float grade) {
        Grade studentGrade = new Grade(student, course, grade);
        try {
            save(studentGrade);
            if (!student.gradeSet().add(studentGrade) || !course.gradeSet().add(studentGrade)) {
                return false;
            }
            update(studentGrade);
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
        }

        return false;
    }
}
