package bg.rezzo.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Slot {
	private long id;
	private int freeTables;
	private double discount;
	private String start;
	private String end;
	private LocalDate date;
	private long placeId;
	private long bookingId;
}
