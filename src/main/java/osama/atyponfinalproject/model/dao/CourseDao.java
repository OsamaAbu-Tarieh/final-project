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
import osama.atyponfinalproject.model.bean.Course;
import osama.atyponfinalproject.model.bean.Student;

public class CourseDao implements GenericDao<Course, Integer> {

	static List<Course> allCoursesList = new ArrayList<>();

	private static final CourseDao instance = new CourseDao();

	private CourseDao() {
	}

	@Override
	public Course save(Course object) throws Exception {
		Course course = null;
		try{
			if (allCoursesList.isEmpty())
				fillDataCourseIntoList();
			synchronized (this) {
				ObjectMapper mapper = new ObjectMapper();
				course = Course.getInstance();
				course.setCourseId(getLastID());
				course.setCourseName(object.getCourseName());
				for (Course s : allCoursesList) {
					if (s.getCourseName().equals(course.getCourseName())) {
						throw new RecordeAlreadyExistException("This recorde is already exist.");
					}
				}
				allCoursesList.add(course);
				mapper.writeValue(new FileWriter("src/main/resources/courses.json"), allCoursesList);
			}
		} catch (Exception e) {
			course = null;
			e.printStackTrace();
		}

		return course;
	}

	@Override
	public Course getById(Integer id) throws Exception {
		if (allCoursesList.isEmpty())
			fillDataCourseIntoList();

		for (Course c : allCoursesList) {
			if (c.getCourseId() == id)
				return c;

		}

		return null;
	}

	@Override
	public boolean deleteById(Integer id) throws Exception {
		boolean deleteSuccessfully = false;
		try{
			synchronized (this) {
				if (allCoursesList.isEmpty())
					fillDataCourseIntoList();
				ObjectMapper mapper = new ObjectMapper();

				for (Course c : allCoursesList) {
					if (c.getCourseId() == id) {
						allCoursesList.remove(c);
						deleteSuccessfully = true;
						break;
					}
				}

				mapper.writeValue(new FileWriter("src/main/resources/courses.json"), allCoursesList);
			}
			System.out.println(deleteSuccessfully);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deleteSuccessfully;
	}

	@Override
	public List<Course> getAll() throws Exception {
		List<Course> courseList = new ArrayList<>();

		if (allCoursesList.isEmpty())
			fillDataCourseIntoList();

		for (Course c : allCoursesList) {
			System.out.println(c.getCourseId() + "    " + c.getCourseName());
			courseList.add(c);
		}

		return courseList;
	}

	private void fillDataCourseIntoList() throws ConnectionFailedException {
		BufferedReader reader = null;
		try {
			synchronized (this) {
				reader = new BufferedReader(new FileReader("src/main/resources/courses.json"));

				ObjectMapper mapper = new ObjectMapper();

				TypeReference<List<Course>> typeReference = new TypeReference<List<Course>>() {
				};
				List<Course> courses = mapper.readValue(reader, typeReference);

				for (Course c : courses) {
					allCoursesList.add(c);
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
			reader = new BufferedReader(new FileReader("src/main/resources/courses.json"));

			if (!allCoursesList.isEmpty()) {
				id = allCoursesList.get(allCoursesList.size() - 1).getCourseId() + 1;
			} else {
				ObjectMapper mapper = new ObjectMapper();

				TypeReference<List<Course>> typeReference = new TypeReference<List<Course>>() {
				};
				List<Course> courses = mapper.readValue(reader, typeReference);

				id = courses.get(courses.size() - 1).getCourseId() + 1;
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

	public static CourseDao getInstance() {
		return instance;
	}

}
