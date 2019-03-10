package bg.rezzo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EditProfileDTO {
	private String telephone;
	private String dateOfBirth;
	private String street;
	private String city;
	private String country;
}
