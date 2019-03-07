package bg.rezzo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Place {
	private long id;
	private String startWorkingDay;
	private String endWorkingDay;
	private double rating;
	private String description;
	private int maxFreeTables;
	private Address address;
	
	
	
}
