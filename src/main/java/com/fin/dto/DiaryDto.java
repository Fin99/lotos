package com.fin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryDto {

    private long id;

    private int assessment;

    @JsonbDateFormat("dd-MM-yyyy")
    private Date date;

}
