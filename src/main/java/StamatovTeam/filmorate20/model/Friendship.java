package StamatovTeam.filmorate20.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Builder
public class Friendship {

    private int id;
    private int friend1Id;
    private int friend2Id;


}