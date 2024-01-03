package StamatovTeam.filmorate20.controllers;

import StamatovTeam.filmorate20.model.Mpa;
import StamatovTeam.filmorate20.service.MpaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mpa")

public class MpaController {

    private final MpaService mpaService;


    @GetMapping
    public List<Mpa> getAll() {
        return mpaService.getAllMpaFromDb();
    }

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable int id) {
        return mpaService.getMpaByIdFromDb(id);
    }
}
