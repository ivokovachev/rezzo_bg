package bg.rezzo.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Restaurant extends Place {
	private String kitchen;
	
	public Restaurant(long id, String name, String startWorkingDay, String endWorkingDay, double rating,
			String description, int maxFreeTables, Address address, String kitchen) {
		super(id, name, startWorkingDay, endWorkingDay, rating, description, maxFreeTables, address);
		this.kitchen = kitchen;
	}

	
}
