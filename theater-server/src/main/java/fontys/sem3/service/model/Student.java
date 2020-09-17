package fontys.sem3.service.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")
@XmlRootElement
public class Student {

	private int studentNumber;
	private String name; // full name, e.g., "Joe Smith"
	private Country country; // country where the students comes from

	public Student(int studentNumber, String name, Country country) {
		this.studentNumber = studentNumber;
		this.name = name;
		this.country = country;
	}


	public Student() {
	}



	public int getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Student student = (Student) o;
		return studentNumber == student.studentNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(studentNumber);
	}

	@Override
	public String toString() {
		return "Student{" +
				"studentNumber=" + studentNumber +
				", name='" + name + '\'' +
				", country=" + country +
				'}';
	}
}
