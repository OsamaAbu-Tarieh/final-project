package osama.atyponfinalproject.model.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import osama.atyponfinalproject.exception.ConnectionFailedException;
import osama.atyponfinalproject.exception.RecordeAlreadyExistException;
import osama.atyponfinalproject.model.bean.Student;
import osama.atyponfinalproject.model.bean.StudentCourse;

public class StudentDao implements GenericDao<Student, Integer> {

	private static final StudentDao instance = new StudentDao();

	static List<Student> allStudentsList = new ArrayList<>();

	private StudentByCourseDao studentCourseDao = StudentByCourseDao.getInstance();

	private StudentDao() {
	}

	@Override
	public Student save(Student object) throws Exception {
		Student student = null;
		try {
			if (allStudentsList.isEmpty())
				fillDataStudentIntoList();
			synchronized (this) {
				ObjectMapper mapper = new ObjectMapper();
				student = Student.getInstance();
				student.setId(getLastID());
				student.setStudentName(object.getStudentName());
				student.setStudentAge(object.getStudentAge());
				student.setStudentMajor(object.getStudentMajor());
				student.setUser(object.getUser());
				for (Student s : allStudentsList) {
					if (s.getStudentName().equals(student.getStudentName())) {
						throw new RecordeAlreadyExistException("This recorde is already exist.");
					}
				}
				System.out.println("from studentDao " + student.getUser().getUsername());
				allStudentsList.add(student);
				mapper.writeValue(new FileWriter("src/main/resources/students.json"), allStudentsList);
			}
		} catch (Exception e) {
			student = null;
			e.printStackTrace();
		}

		return student;
	}

	@Override
	public Student getById(Integer id) throws Exception {
		if (allStudentsList.isEmpty())
			fillDataStudentIntoList();

		for (Student s : allStudentsList) {
			if (s.getId() == id)
				return s;

		}

		return null;
	}

	@Override
	public boolean deleteById(Integer id) throws Exception {
		boolean deleteSuccessfully = false;
		try {
			synchronized (this) {
				if (allStudentsList.isEmpty())
					fillDataStudentIntoList();
				ObjectMapper mapper = new ObjectMapper();

				for (Student s : allStudentsList) {
					if (s.getId() == id) {
						allStudentsList.remove(s);
						deleteSuccessfully = true;
						break;
					}
				}

				mapper.writeValue(new FileWriter("src/main/resources/students.json"), allStudentsList);
			}
			System.out.println(deleteSuccessfully);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deleteSuccessfully;
	}

	@Override
	public List<Student> getAll() throws Exception {
		List<Student> studentList = new ArrayList<>();

		if (allStudentsList.isEmpty())
			fillDataStudentIntoList();

		for (Student s : allStudentsList) {
			System.out.println(
					s.getId() + "    " + s.getStudentName() + "    " + s.getStudentMajor() + "    " + s.getId());
			studentList.add(s);

		}

		return studentList;
	}

	public void fillDataStudentIntoList() throws Exception {
		BufferedReader reader = null;
		try {
			synchronized (this) {
				reader = new BufferedReader(new FileReader("src/main/resources/students.json"));

				ObjectMapper mapper = new ObjectMapper();

				TypeReference<List<Student>> typeReference = new TypeReference<List<Student>>() {
				};
				List<Student> students = mapper.readValue(reader, typeReference);

				for (Student s : students) {
					allStudentsList.add(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConnectionFailedException("Operation failed, please check the datasource file.");
		}

		finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public int getLastID() {
		BufferedReader reader = null;
		int id = 0;
		try {
			reader = new BufferedReader(new FileReader("src/main/resources/students.json"));

			if (!allStudentsList.isEmpty()) {
				id = allStudentsList.get(allStudentsList.size() - 1).getId() + 1;
			} else {
				ObjectMapper mapper = new ObjectMapper();

				TypeReference<List<Student>> typeReference = new TypeReference<List<Student>>() {
				};
				List<Student> students = mapper.readValue(reader, typeReference);

				id = students.get(students.size() - 1).getId() + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return id;
	}

	public List<StudentCourse> getStudentsByCourse(String courseName) throws Exception {
		List<StudentCourse> studentList = new ArrayList<>();
		List<StudentCourse> studentCourseList = new ArrayList<>();
		studentList = studentCourseDao.getAll();

		if (allStudentsList.isEmpty())
			fillDataStudentIntoList();

		for (int i = 0; i < studentList.size(); i++) {
			if (studentList.get(i).getCourseName().equals(courseName)) {
				studentCourseList.add(studentList.get(i));
			}

		}

		return studentCourseList;
	}

	public static StudentDao getInstance() {
		return instance;
	}

}
