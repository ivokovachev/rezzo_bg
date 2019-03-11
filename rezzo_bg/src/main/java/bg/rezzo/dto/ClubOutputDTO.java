package bg.rezzo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClubOutputDTO {
	
	private Long id;
	private String clubName;
	private String genreName;
	private double rating;
}
