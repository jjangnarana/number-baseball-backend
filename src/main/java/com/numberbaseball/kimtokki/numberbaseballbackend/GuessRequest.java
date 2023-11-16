package com.numberbaseball.kimtokki.numberbaseballbackend;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GuessRequest {
    private int[] guess;
    private int attempts;
}
