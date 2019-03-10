package bg.rezzo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventInputDTO {
	private String date;
	private String url;
	private String description;
	private String title;
	private String placeName;
}
