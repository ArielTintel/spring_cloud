package br.com.arieltintel.passagem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String process(MethodArgumentNotValidException exception) {

        StringBuilder stringBuilder = new StringBuilder();

        for( ObjectError item : exception.getBindingResult().getAllErrors() ){
            stringBuilder.append(item.getDefaultMessage());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

}