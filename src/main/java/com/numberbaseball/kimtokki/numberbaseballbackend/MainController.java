package com.numberbaseball.kimtokki.numberbaseballbackend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class MainController {
    private int[] answer = generateRandomNumber();

    private int[] generateRandomNumber() {
        Random rand = new Random();
        Set<Integer> numberSet = new LinkedHashSet<>();
        while (numberSet.size() < 4) {
            int randomNumber = 1 + rand.nextInt(9);
            numberSet.add(randomNumber);
        }
        return numberSet.stream().mapToInt(Number::intValue).toArray();
    }

    //사용자 추측값 검증
    @PostMapping("/guess")
    public ResponseEntity<String> guess(@RequestBody GuessRequest request) {
        int[] guess = request.getGuess();
        int attempts = request.getAttempts();

        if (guess.length != 4) {
            return ResponseEntity.badRequest().body("Invalid input");
        }

        int strikes = 0;
        int balls = 0;

        for (int i = 0; i < 4; i++) {
            if (guess[i] == answer[i]) {
                strikes++;
            } else if (intContains(answer,guess[i])) {
                balls++;
            }
        }

        if (strikes == 4) {
            answer = generateRandomNumber();
            return ResponseEntity.ok("HOMERUN");
        } else {
            if (attempts == 9) {
                answer = generateRandomNumber();
            }
            return ResponseEntity.ok(strikes + "S" + balls + "B");
        }
    }
    private boolean intContains(int[] array, int key) {
        for (int i : array) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }
}
