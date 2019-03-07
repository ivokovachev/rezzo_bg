package bg.rezzo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Address {
	private long id;
	private String street;
	private String city;
	private String country;
	
	
}
