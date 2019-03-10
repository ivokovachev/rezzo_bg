package bg.rezzo.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class User {	
	private long id;
	private String email;
	private String password;
	private String telephone;
	private LocalDate dateOfBirth;
	private Address address;
	private int isAdmin;
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof User) {
			return this.email.equals(((User)(obj)).getEmail());
		}
		
		return false;
	}
	
}
