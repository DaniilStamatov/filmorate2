package StamatovTeam.filmorate20.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Mpa {
    private Integer id;
    private String name;

    public Mpa(int id){
        this.id = id;
    }
}
