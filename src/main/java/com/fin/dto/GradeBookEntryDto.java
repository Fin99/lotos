package com.fin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeBookEntryDto {

    private long id;

    private boolean attend;

    private int eatingScore;

    private int behavior;

    private String note;

    @JsonbDateFormat("dd-MM-yyyy")
    private Date date;

}
