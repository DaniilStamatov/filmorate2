package StamatovTeam.filmorate20.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class User {
    @EqualsAndHashCode.Exclude
    private int id;
    private String name;

    @NotBlank(message = "Почта не может быть пустой и должна содержать символ @")
    @Email(message = "электронная почта не может быть пустой и должна содержать символ @")
    private String email;
    @NotBlank(message = "логин должен быть заполнен")
    private String login;
    @PastOrPresent
    private LocalDate birthday;
    private List<User> friends;

    public static User makeUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        return builder()
                .id(id)
                .email(email)
                .login(login)
                .name(name)
                .birthday(birthday)
                .build();
    }
}
