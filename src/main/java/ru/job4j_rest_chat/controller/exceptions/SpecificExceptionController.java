package ru.job4j_rest_chat.controller.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exceptions")
public class SpecificExceptionController {

    @GetMapping("/npe")
    public void getNpe() {
        throw new NullPointerException();
    }

    @ExceptionHandler(value = {NullPointerException.class})
    private ResponseEntity<String> npeExceptionHandle() {
        return ResponseEntity.ok("You've got this message caused by request NullPointerException");
    }

}
