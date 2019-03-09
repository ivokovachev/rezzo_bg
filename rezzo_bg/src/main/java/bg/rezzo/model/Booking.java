package bg.rezzo.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Booking {
	private long id;
	private int numberOfTables;
	private Slot slot;
	private User user;
}
