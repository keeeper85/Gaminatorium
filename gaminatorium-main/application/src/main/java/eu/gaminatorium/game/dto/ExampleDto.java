package eu.gaminatorium.game.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Dto to obiekt transferowy który pobiera tylko te pola encji które są niezbędne do wykonania żądania.
 * Dobrym sposobem na implementację Dto jest użycie wzorca Budowniczy - Builder, który redukuje ilość potrzebnych konstruktorów.
 */

@Builder public record ExampleDto(Long id, String title, LocalDate publishDate) {}
