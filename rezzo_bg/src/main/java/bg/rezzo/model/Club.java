package bg.rezzo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Club extends Place {
	private String music;
	
	public Club(long id, String name, String startWorkingDay, String endWorkingDay, double rating,
			String description, int maxFreeTables, Address address, String music) {
		super(id, name, startWorkingDay, endWorkingDay, rating, description, maxFreeTables, address);
		this.music = music;
	}	
	
}
