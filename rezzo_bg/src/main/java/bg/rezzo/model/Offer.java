package bg.rezzo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Offer {
	private long id;
	private String description;
	private String place;
	private String title;
	private String pictureUrl;
	private int price;
}
