package bg.rezzo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantOutputDTO {
	private Long id;
	private String restaurantName;
	private String kitchenName;
	private double rating;
	private String city;

}
