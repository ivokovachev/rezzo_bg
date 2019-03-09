package bg.rezzo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservationDTO {
	private String placeName;
	private LocalDate date;
	private String start;
	private String end;
	private int numberOfTables;
}
