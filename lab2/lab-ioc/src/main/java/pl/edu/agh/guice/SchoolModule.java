package pl.edu.agh.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import pl.edu.agh.school.persistence.IPersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

import javax.inject.Named;

public class SchoolModule extends AbstractModule {

    @Provides
    public IPersistenceManager providePersistenceManager(SerializablePersistenceManager manager) {
        return manager;
    }

    @Provides
    @Named("teachers")
    public String provideTeachersStorageFileName() {
        return "guice-teachers.dat";
    }

    @Provides
    @Named("class")
    public String provideClassStorageFileName() {
        return "guice-classes.dat";
    }
}
