package model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User {
    private int id;
    @NotBlank(message = "Почта не может быть пустой и должна содержать символ @")
    @Email(message = "электронная почта не может быть пустой и должна содержать символ @")
    private String email;
    @NotBlank(message = "логин должен быть заполнен")
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;

}
