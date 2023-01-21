package com.github.lexakimov.omm.classes;


import lombok.var;
import java.util.Objects;

public final class Person {
    private final String firstName;
    private final String lastName;
    private final long age;

    public Person(String firstName, String lastName, long age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public long age() {
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Person) obj;
        return Objects.equals(this.firstName, that.firstName) &&
               Objects.equals(this.lastName, that.lastName) &&
               this.age == that.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }

    @Override
    public String toString() {
        return "Person[" +
               "firstName=" + firstName + ", " +
               "lastName=" + lastName + ", " +
               "age=" + age + ']';
    }

}
