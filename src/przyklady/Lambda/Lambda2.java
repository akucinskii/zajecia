package przyklady.Lambda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Lambda2 {
	public static void main(String[] args) {
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("Jan", "Kowalski", LocalDate.of(2000, 12, 11)));
		persons.add(new Person("Jan", "Kowalski", LocalDate.of(2000, 12, 12)));
		persons.add(new Person("Jan", "Kowalski", LocalDate.of(2000, 12, 13)));
		persons.add(new Person("Andrzej", "Kowalski", LocalDate.of(2000, 12, 11)));
		persons.add(new Person("Jan", "Matejko", LocalDate.of(2000, 12, 11)));
		persons.add(new Person("Anna", "Kowalska", LocalDate.of(2000, 12, 11)));
		persons.add(new Person("Jan", "Kowalski", LocalDate.of(2000, 12, 11)));

		// kopiowanie listy
		List<Person> personsCopy = new ArrayList<>(persons);

		System.out.println("Przed sortowaniem");
		for (Person p : persons) {
			System.out.println(p);
		}
		// implementacja komparatora i sortowanie przed pojawieniem się lambdy w Javie
		Collections.sort(persons, new Comparator<Person>() {

			@Override
			public int compare(Person person1, Person person2) {
				// Sortujemy według nazwiska, imienia a na końcu daty urodzenia
				int lastNameComparison = person1.getLastName().compareTo(person2.getLastName());
				if (lastNameComparison != 0) {
					return lastNameComparison;
				}
				int firstNameComparison = person1.getFirstName().compareTo(person2.getFirstName());
				if (firstNameComparison != 0) {
					return firstNameComparison;
				}
				return person1.getDateOfBirth().compareTo(person2.getDateOfBirth());
			}
		});

		System.out.println("Po sortowaniu");
		for (Person p : persons) {
			System.out.println(p);
		}

		System.out.println("sortowanie - lambda");
		for (Person p : personsCopy) {
			System.out.println(p);
		}
		
		// implementacja komparatora i sortowanie przed pojawieniem się lambdy w Javie
		personsCopy.sort((person1, person2) -> {
			int lastNameComparison = person1.getLastName().compareTo(person2.getLastName());
			if (lastNameComparison != 0) {
				return lastNameComparison;
			}
			int firstNameComparison = person1.getFirstName().compareTo(person2.getFirstName());
			if (firstNameComparison != 0) {
				return firstNameComparison;
			}
			return person1.getDateOfBirth().compareTo(person2.getDateOfBirth());
		});
		
		System.out.println("Po sortowaniu - lambda");
		for (Person p : personsCopy) {
			System.out.println(p);
		}

	}
}

class Person {
	String firstName;
	String lastName;
	LocalDate dateOfBirth;

	public Person(String firstName, String lastName, LocalDate dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + "]";
	}

}
