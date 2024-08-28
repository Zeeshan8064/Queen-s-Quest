package ResultModel;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {


    private String Player1name;
    private String Player2name;
    private String winner;
    private int numberOfMovesPlayer1;
    private int numberOfMovesPlayer2;
    private LocalDateTime startDateTime;


}
