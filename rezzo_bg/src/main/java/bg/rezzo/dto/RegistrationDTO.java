package bg.rezzo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationDTO {
	private String email;
	private String password;
	private String street;
	private String city;
	private String country;
	private String telephone;
	private LocalDate dateOfBirth;
}
