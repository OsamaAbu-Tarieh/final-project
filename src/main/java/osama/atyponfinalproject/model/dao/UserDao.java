package osama.atyponfinalproject.model.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Synchronization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import osama.atyponfinalproject.config.SecurityConfigurer;
import osama.atyponfinalproject.exception.ConnectionFailedException;
import osama.atyponfinalproject.exception.RecordeAlreadyExistException;
import osama.atyponfinalproject.model.bean.User;

public class UserDao implements GenericDao<User, Integer> {

	static List<User> allUsersList = new ArrayList<>();

	SecurityConfigurer security = new SecurityConfigurer();
	
	private static final UserDao instance = new UserDao();

	private UserDao() {
	}

	@Override
	public User save(User object) throws ConnectionFailedException {
		User user = null;
		try{
			if (allUsersList.isEmpty())
				fillDataUserIntoList();
			synchronized (this) {
				ObjectMapper mapper = new ObjectMapper();
				user = User.getInstance();
				user.setId(getLastID());
				user.setUsername(object.getUsername());
				user.setPassword(security.getEncodedPassword(object.getPassword()));
				user.setRole(object.getRole());
				for (User u : allUsersList) {
					if (u.getUsername().equals(user.getUsername())) {
						throw new RecordeAlreadyExistException("This recorde is already exist.");
					}
				}
				allUsersList.add(user);
				mapper.writeValue(new FileWriter("src/main/resources/users.json"), allUsersList);
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConnectionFailedException("Operation failed, please check the datasource file.");
		}
	}

	@Override
	public User getById(Integer id) throws Exception {
		if (allUsersList.isEmpty())
			fillDataUserIntoList();

		for (User u : allUsersList) {
			System.out.println(u.getId() + "    " + u.getUsername() + "    " + u.getPassword() + "    " + u.getRole());
			if (u.getId() == id)
				return u;

		}

		return null;
	}

	@Override
	public boolean deleteById(Integer id) {
		boolean deleteSuccessfully = false;
		try{
			synchronized (this) {
				if (allUsersList.isEmpty())
					fillDataUserIntoList();
				ObjectMapper mapper = new ObjectMapper();

				for (User u : allUsersList) {
					if (u.getId() == id) {
						allUsersList.remove(u);
						deleteSuccessfully = true;
						break;
					}
				}

				mapper.writeValue(new FileWriter("src/main/resources/users.json"), allUsersList);
			}
			System.out.println(deleteSuccessfully);
			return deleteSuccessfully;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deleteSuccessfully;

	}

	@Override
	public List<User> getAll() throws Exception {
		List<User> userList = new ArrayList<>();

		if (allUsersList.isEmpty())
			fillDataUserIntoList();

		for (User u : allUsersList) {
			System.out.println(u.getId() + "    " + u.getUsername() + "    " + u.getPassword() + "    " + u.getRole());
			userList.add(u);

		}

		return userList;
	}

	public User getByName(String name) throws Exception {
		if (allUsersList.isEmpty())
			fillDataUserIntoList();

		for (User u : allUsersList) {
			System.out.println(u.getId() + "    " + u.getUsername() + "    " + u.getPassword() + "    " + u.getRole());
			if (u.getUsername().equals(name))
				return u;

		}

		return null;
	}

	public void fillDataUserIntoList() throws Exception {
		BufferedReader reader = null;
		try {
			synchronized (this) {
				reader = new BufferedReader(new FileReader("src/main/resources/users.json"));

				ObjectMapper mapper = new ObjectMapper();

				TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {
				};
				List<User> users = mapper.readValue(reader, typeReference);

				for (User u : users) {
					allUsersList.add(u);
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
			reader = new BufferedReader(new FileReader("src/main/resources/users.json"));

			if (!allUsersList.isEmpty()) {
				id = allUsersList.get(allUsersList.size() - 1).getId() + 1;
			} else {
				ObjectMapper mapper = new ObjectMapper();

				TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {
				};
				List<User> students = mapper.readValue(reader, typeReference);

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

	public static UserDao getInstance() {
		return instance;
	}

}
