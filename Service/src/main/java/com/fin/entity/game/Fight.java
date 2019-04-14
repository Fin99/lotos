package com.fin.entity.game;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Fight {
    private Fighter radiant;
    private Fighter dire;
    private List<Hit> hitList;
}
