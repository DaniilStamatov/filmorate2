package StamatovTeam.filmorate20.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Set;

@Data
public class User {
    @EqualsAndHashCode.Exclude
    private int id;
    @NotBlank(message = "Почта не может быть пустой и должна содержать символ @")
    @Email(message = "электронная почта не может быть пустой и должна содержать символ @")
    private String email;
    @NotBlank(message = "логин должен быть заполнен")
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
    private Set<Integer> friends;

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
