package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.service.HackCatService;

import java.util.Optional;

@RestController
public class SimpleController {

    private final HackCatService hackCatService;

    // внедрите бин HackCatService
    @Autowired
    public SimpleController(HackCatService hackCatService) {
        this.hackCatService = hackCatService;
    }

    @GetMapping("/do-hack")
    public Optional<String> doHack(){
        // хакните этих котиков
        return hackCatService.doHackNow()
                .map(p -> "Пароль подобран! Вот он: " + p)
                .or(() -> Optional.of("Не удалось подобрать пароль. Проверьте состояние и настройки БД."));
    }

    @GetMapping("/home")
    public String homePage() {
        return "Котограм";
    }
}
