package bg.rezzo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClubDTO {
	private Long id;
	private String clubName;
	private String genreName;
	private Long genreId;
	private double rating;

}
