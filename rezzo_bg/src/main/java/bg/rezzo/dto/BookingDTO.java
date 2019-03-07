package bg.rezzo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookingDTO {
	private long id;
	private String start;
	private String end;
	private int numberOfTables;
	private LocalDate date;
	private String placeName;
}
