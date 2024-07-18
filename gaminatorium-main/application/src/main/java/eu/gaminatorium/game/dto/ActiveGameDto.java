package eu.gaminatorium.game.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.gaminatorium.game.Game;
import eu.gaminatorium.user.dto.UserDto;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public class ActiveGameDto {

    private Long activegameid;
    private Long gameid;
    @Min(value = 0, message = "Number of current players must be 0 or higher.")
    private int currentPlayers;
    private int maxPlayers;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime startedAt;
    private int timerStartedMinutesAgo;
    private long hostid;

    public ActiveGameDto(Game game) {
        this.gameid = game.getId();
        this.maxPlayers = game.getMaxPlayers();
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
        updateTimer();
    }

    private void updateTimer(){
        this.timerStartedMinutesAgo = Duration.between(startedAt, LocalDateTime.now()).toMinutesPart();
    }
}
