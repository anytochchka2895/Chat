package org.example.dtos;

public class UserLoginDto {

	private long phone;
	private String password;

	public UserLoginDto() {
	}

	public UserLoginDto(long phone, String password) {
		this.phone = phone;
		this.password = password;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
