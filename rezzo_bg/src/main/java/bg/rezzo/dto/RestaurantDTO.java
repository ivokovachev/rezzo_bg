package bg.rezzo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantDTO {
	private Long id;
	private String restaurantName;
	private String kitchenName;
	private Long kitchenId;
	private double rating;
}
