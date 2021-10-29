//package com.sso.login.pojo;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.Accessors;
//
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Accessors(chain=true)
//public class User {
//	private Integer id;
//	private String username;
//	private String password;
//}
package com.sso.login.pojo;

public class User {
	private  Integer id;
	private  String username;
	private  String password;
	public User(Integer id,String username ,String password){
		this.id=id;
		this.username=username;
		this.password=password;
	}
	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}
}

