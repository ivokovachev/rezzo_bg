package bg.rezzo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OfferInputDTO {
	private String description;
	private String title;
	private String url;
	private String placeName;
	private int price;

}
