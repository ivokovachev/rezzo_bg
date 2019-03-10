package bg.rezzo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClubInputDTO {
	private String clubName;
    private String startWorkingDay;
    private String endWorkingDay;
    private double rating;
    private String description;
    private String street;
    private String city;
    private String country;
    private String genreName;
}
