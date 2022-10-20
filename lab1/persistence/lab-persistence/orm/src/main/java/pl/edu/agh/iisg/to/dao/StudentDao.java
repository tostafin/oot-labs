package pl.edu.agh.iisg.to.dao;

import java.util.*;

import javassist.compiler.ast.Pair;
import org.hibernate.query.Query;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;

import javax.persistence.PersistenceException;

public class StudentDao extends GenericDao<Student> {

    public Optional<Student> create(final String firstName, final String lastName, final int indexNumber) {
        try {
            Student student = new Student(firstName, lastName, indexNumber);
            save(student);
            return Optional.of(student);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Student> findByIndexNumber(final int indexNumber) {
        try {
            Student student = currentSession().createQuery("SELECT s FROM Student s WHERE s.indexNumber = :indexNumber", Student.class)
                    .setParameter("indexNumber", indexNumber).getSingleResult();
            return Optional.of(student);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Map<Course, Float> createReport(final Student student) {
        try {
            List<GradeAvg> grades = currentSession().createQuery("SELECT new pl.edu.agh.iisg.to.dao.GradeAvg(g.course, AVG(g.grade)) FROM Grade g WHERE g.student.id = :studentId GROUP BY g.course", GradeAvg.class)
                    .setParameter("studentId", student.id())
                    .getResultList();

            Map<Course, Float> reportMap = new HashMap<>();
            for (GradeAvg g : grades) {
                reportMap.put(g.getCourse(), (float) g.getGradeAvg());
            }

            return reportMap;
        } catch (PersistenceException e) {
            e.printStackTrace();
        }

        return Collections.emptyMap();
    }
}
