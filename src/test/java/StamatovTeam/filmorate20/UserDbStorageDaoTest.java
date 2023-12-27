package StamatovTeam.filmorate20;

import StamatovTeam.filmorate20.dao.impl.UserDaoImpl;
import StamatovTeam.filmorate20.model.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest // указываем, о необходимости подготовить бины для работы с БД
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testFindUserById() {
        // Подготавливаем данные для теста
        User newUser = User.builder().id(1).name("Ivan Petrov").email("user@email.ru").login("vanya123").birthday(LocalDate.of(1990, 1, 1)).build();
        UserDaoImpl userStorage = new UserDaoImpl(jdbcTemplate);
        userStorage.addUser(newUser);

        // вызываем тестируемый метод
        User savedUser = userStorage.getUser(1);

        // проверяем утверждения
        assertThat(savedUser)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(newUser);        // и сохраненного пользователя - совпадают
    }
}