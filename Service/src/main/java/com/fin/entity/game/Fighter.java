package com.fin.entity.game;

import com.fin.entity.Child;
import com.fin.entity.Jsonable;
import com.fin.entity.group.Diary;
import com.fin.entity.group.GradeBook;
import lombok.Getter;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.List;

@Getter
@Setter
public class Fighter implements Jsonable {
    private double strength;
    private double agility;
    private double intellect;

    private double hp;
    private double damage;
    private double attackSpeed;
    private double blockChance;
    private double criticalChance;

    private Child child;

    public Fighter(Child child) {
        this.child = child;

        this.strength = calculateStrength();
        this.agility = calculateAgility();
        this.intellect = calculateIntellect();

        this.hp = calculateHp();
        this.damage = calculateDamage();
        this.attackSpeed = calculateAttackSpeed();
        this.blockChance = calculateBlockChance();
        this.criticalChance = calculateCriticalChance();
    }

    private double calculateStrength() {
        List<GradeBook> gradeBookList = child.getGradeBookList();
        int sumOfScores = gradeBookList.stream().map(GradeBook::getEatingScore).reduce(0, Integer::sum);
        return (double) sumOfScores / gradeBookList.size();
    }

    private double calculateAgility() {
        List<GradeBook> gradeBookList = child.getGradeBookList();
        int sumOfScores = gradeBookList.stream().map(GradeBook::getBehavior).reduce(0, Integer::sum);
        return (double) sumOfScores / gradeBookList.size();
    }

    private double calculateIntellect() {
        List<Diary> diaryList = child.getDiaryList();
        int sumOfScores = child.getDiaryList().stream().map(Diary::getAssessment).reduce(0, Integer::sum);
        return (double) sumOfScores / diaryList.size() - 2;
    }

    private double calculateHp() {
        return 1000 + this.strength * 1000;
    }

    private double calculateDamage() {
        return 50 + this.strength * 15;
    }

    private double calculateAttackSpeed() {
        return 1 - this.agility * 0.1;
    }

    private double calculateBlockChance() {
        return 0.1 + this.agility * 0.15;
    }

    private double calculateCriticalChance() {
        return this.intellect * 0.1;
    }

    @Override
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        if (child != null) {
            builder.add("child", child.toJson());
        }

        builder.add("strength", strength);
        builder.add("agility", agility);
        builder.add("intellect", intellect);

        builder.add("hp", hp);
        builder.add("damage", damage);
        builder.add("attackSpeed", attackSpeed);
        builder.add("blockChance", blockChance);
        builder.add("criticalChance", criticalChance);

        return builder.build();
    }
}
