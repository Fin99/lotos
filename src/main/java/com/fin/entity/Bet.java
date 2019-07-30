package com.fin.entity;

import com.fin.entity.game.Fighter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Bet {

    private Parent parent;

    private double coefficient;

    private Fighter fighter;

    private double rateAmount;

}
