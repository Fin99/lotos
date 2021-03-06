package com.fin.entity.game;

import com.fin.entity.Child;
import com.fin.entity.Jsonable;
import com.fin.entity.group.Diary;
import com.fin.entity.group.GradeBook;
import com.fin.entity.medical.DiseaseStrength;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Fighter implements Jsonable {
    private double strength;
    private double agility;
    private double intellect;

    private double hp;
    private double damage;
    private double cooldown;
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
        this.cooldown = calculateCooldown();
        this.blockChance = calculateBlockChance();
        this.criticalChance = calculateCriticalChance();
    }

    private double calculateStrength() {
        List<GradeBook> gradeBookList = child.getGradeBookList();
        int sumOfScores = gradeBookList.stream().map(GradeBook::getEatingScore).reduce(0, Integer::sum);
        if (gradeBookList.size() == 0) {
            return 0;
        }
        return (double) sumOfScores / gradeBookList.size();
    }

    private double calculateAgility() {
        List<GradeBook> gradeBookList = child.getGradeBookList();
        int sumOfScores = gradeBookList.stream().map(GradeBook::getBehavior).reduce(0, Integer::sum);
        if (gradeBookList.size() == 0) {
            return 0;
        }
        return (double) sumOfScores / gradeBookList.size();
    }

    private double calculateIntellect() {
        List<Diary> diaryList = child.getDiaryList();
        int sumOfScores = child.getDiaryList().stream().map(Diary::getAssessment).reduce(0, Integer::sum);
        if (diaryList.size() == 0) {
            return 0;
        }
        return (double) sumOfScores / diaryList.size() - 2;
    }

    private double calculateHp() {
        return 1000 + this.strength * 1000;
    }

    private double calculateDamage() {
        double baseDamage = 50 + this.strength * 15;

        long hardIllsAmount = child.getMedicalBook().getIllSet().stream()
                .filter(ill -> ill.getDiseaseStrength() == DiseaseStrength.HARD).count();
        if (hardIllsAmount > 0) {
            return baseDamage * (1 - calculateDecreasingFactor(0.2, hardIllsAmount));
        }

        long mediumIllsAmount = child.getMedicalBook().getIllSet().stream()
                .filter(ill -> ill.getDiseaseStrength() == DiseaseStrength.MEDIUM).count();
        if (mediumIllsAmount > 0) {
            return baseDamage * (1 - calculateDecreasingFactor(0.1, hardIllsAmount));
        }

        long minimumIllsAmount = child.getMedicalBook().getIllSet().stream()
                .filter(ill -> ill.getDiseaseStrength() == DiseaseStrength.MINIMUM).count();
        if (minimumIllsAmount > 0) {
            return baseDamage * (1 - calculateDecreasingFactor(0.05, hardIllsAmount));
        }

        return baseDamage;
    }

    private double calculateDecreasingFactor(double baseFactor, long amount) {
        double resultFactor = 0;
        for (int i = 0; i < amount; i++) {
            resultFactor += baseFactor / Math.pow(2, i);
        }
        return resultFactor;
    }

    private double calculateCooldown() {
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
            builder.add("id", child.getId());
            if (child.getName() != null) {
                builder.add("name", child.getName());
            }
            if (child.getSurname() != null) {
                builder.add("surname", child.getSurname());
            }
            builder.add("strength", strength);
            builder.add("agility", agility);
            builder.add("intellect", intellect);
        }

        return builder.build();
    }
}
