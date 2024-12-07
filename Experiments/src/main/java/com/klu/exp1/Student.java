package com.klu.exp1;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "student")
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "department")
    private String department;

    @Column(name = "program")
    private String program;

    @Column(name = "dob")
    private String dob;

    @Column(name = "contact")
    private String contact;

    @Column(name = "graduation_status")
    private String graduationStatus;

    @Column(name = "cgpa")
    private double cgpa;

    @Column(name = "backlogs")
    private int backlogs;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getGraduationStatus() {
		return graduationStatus;
	}

	public void setGraduationStatus(String graduationStatus) {
		this.graduationStatus = graduationStatus;
	}

	public double getCgpa() {
		return cgpa;
	}

	public void setCgpa(double cgpa) {
		this.cgpa = cgpa;
	}

	public int getBacklogs() {
		return backlogs;
	}

	public void setBacklogs(int backlogs) {
		this.backlogs = backlogs;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", gender=" + gender + ", department=" + department
				+ ", program=" + program + ", dob=" + dob + ", contact=" + contact + ", graduationStatus="
				+ graduationStatus + ", cgpa=" + cgpa + ", backlogs=" + backlogs + "]";
	}
	

    
}
