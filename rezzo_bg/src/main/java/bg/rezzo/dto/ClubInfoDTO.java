package bg.rezzo.dto;

import bg.rezzo.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClubInfoDTO {
	private Long id;
	private String name;
	private String startWorkingDay;
	private String endWorkingDay;
	private double rating;
	private String description;
	private int maxFreeTables;
	private Address address;
}
