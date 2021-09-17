package osama.atyponfinalproject.config;

import java.util.Base64;

public class SecurityConfigurer {
	
	 public String getEncodedPassword(String password){
        return Base64.getEncoder().encodeToString(password.getBytes());
	 }

	 public String getDecodedPassword(String password){
		 return new String(Base64.getMimeDecoder().decode(password));
	 }

	public static SecurityConfigurer getInstance() {
		return new SecurityConfigurer();
	}

}
