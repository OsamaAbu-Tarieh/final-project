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
import osama.atyponfinalproject.model.bean.StudentCourse;

public class StudentByCourseDao implements GenericDao<StudentCourse, Integer> {

	private static final StudentByCourseDao instance = new StudentByCourseDao();

	static List<StudentCourse> allStudentCourseList = new ArrayList<>();

	private StudentByCourseDao() {
	}

	@Override
	public StudentCourse save(StudentCourse object) throws Exception {
		StudentCourse studentCourse = null;
		try{
			if (allStudentCourseList.isEmpty())
				fillDataStudentCourseIntoList();

			synchronized (this) {
				ObjectMapper mapper = new ObjectMapper();
				studentCourse = StudentCourse.getInstance();
				studentCourse.setId(getLastID());
				studentCourse.setCourseName(object.getCourseName());
				studentCourse.setStudentName(object.getStudentName());
				for (StudentCourse s : allStudentCourseList) {
					if (s.getStudentName().equals(studentCourse.getStudentName())&&s.getCourseName().equals(studentCourse.getCourseName())) {
						throw new RecordeAlreadyExistException("This recorde is already exist.");
					}
				}
				allStudentCourseList.add(studentCourse);
				mapper.writeValue(new FileWriter("src/main/resources/studentCourse.json"), allStudentCourseList);

			}

		} catch (Exception e) {
			throw new ConnectionFailedException("Operation failed, please check the datasource file.");
		}

		return studentCourse;
	}

	@Override
	public StudentCourse getById(Integer id) throws Exception {
		if (allStudentCourseList.isEmpty())
			fillDataStudentCourseIntoList();

		for (StudentCourse c : allStudentCourseList) {
			if (c.getId() == id)
				return c;

		}

		return null;
	}

	@Override
	public boolean deleteById(Integer id) throws Exception {
		boolean deleteSuccessfully = false;
		try{
			synchronized (this) {
				if (allStudentCourseList.isEmpty())
					fillDataStudentCourseIntoList();
				ObjectMapper mapper = new ObjectMapper();

				for (StudentCourse c : allStudentCourseList) {
					if (c.getId() == id) {
						allStudentCourseList.remove(c);
						deleteSuccessfully = true;
						break;
					}
				}

				mapper.writeValue(new FileWriter("src/main/resources/studentCourse.json"), allStudentCourseList);
			}
			System.out.println(deleteSuccessfully);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deleteSuccessfully;
	}

	@Override
	public List<StudentCourse> getAll() throws Exception {
		List<StudentCourse> studentCourseList = new ArrayList<>();

		if (allStudentCourseList.isEmpty())
			fillDataStudentCourseIntoList();

		for (StudentCourse s : allStudentCourseList) {
			System.out.println(s.getId() + "    " + s.getStudentName() + "    " + s.getCourseName());
			studentCourseList.add(s);

		}

		return studentCourseList;
	}

	public List<StudentCourse> getByStudent(String studentName) throws Exception {
		List<StudentCourse> studentCourseList = new ArrayList<>();

		if (allStudentCourseList.isEmpty())
			fillDataStudentCourseIntoList();

		for (StudentCourse s : allStudentCourseList) {
			System.out.println(s.getId() + "    " + s.getStudentName() + "    " + s.getCourseName());
			if (s.getStudentName().equals(studentName))
				studentCourseList.add(s);
		}

		return studentCourseList;
	}

	public int getLastID() {
		BufferedReader reader = null;
		int id = 0;
		try {
			reader = new BufferedReader(new FileReader("src/main/resources/studentCourse.json"));

			if (!allStudentCourseList.isEmpty()) {
				id = allStudentCourseList.get(allStudentCourseList.size() - 1).getId() + 1;
			} else {
				ObjectMapper mapper = new ObjectMapper();

				TypeReference<List<StudentCourse>> typeReference = new TypeReference<List<StudentCourse>>() {
				};
				List<StudentCourse> studentCourse = mapper.readValue(reader, typeReference);

				id = studentCourse.get(studentCourse.size() - 1).getId() + 1;
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

	public void fillDataStudentCourseIntoList() throws Exception {
		BufferedReader reader = null;
		try {
			synchronized (this) {
				reader = new BufferedReader(new FileReader("src/main/resources/studentCourse.json"));

				ObjectMapper mapper = new ObjectMapper();

				TypeReference<List<StudentCourse>> typeReference = new TypeReference<List<StudentCourse>>() {
				};

				List<StudentCourse> studentCourse = mapper.readValue(reader, typeReference);

				for (StudentCourse s : studentCourse) {
					allStudentCourseList.add(s);
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

	public static StudentByCourseDao getInstance() {
		return instance;
	}

}
