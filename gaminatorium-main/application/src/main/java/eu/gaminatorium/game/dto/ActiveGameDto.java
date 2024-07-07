package eu.gaminatorium.game.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.gaminatorium.game.Game;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public class ActiveGameDto {

    private Long id;
    private Game game;
    @Min(value = 0, message = "Number of current players can not be negative.")
    private int currentPlayers;
    private int maxPlayers;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime startedAt = LocalDateTime.now();
    private int timerStartedMinutesAgo;

    public ActiveGameDto(Game game) {
        this.game = game;
        this.currentPlayers = 1;
        this.maxPlayers = game.getMaxPlayers();
        this.timerStartedMinutesAgo = Duration.between(startedAt, LocalDateTime.now()).toMinutesPart();
    }

    void updateTimer(){
        this.timerStartedMinutesAgo = Duration.between(startedAt, LocalDateTime.now()).toMinutesPart();
    }
}
