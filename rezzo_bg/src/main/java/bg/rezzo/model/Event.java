package bg.rezzo.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Event {
	private long id;
	private LocalDate date;
	private String place;
	private String pictureUrl;
	private String description;
	private String title;
	
}
