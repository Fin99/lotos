package com.fin.ejb;

import com.fin.dto.GradeBookEntryDto;
import com.fin.entity.Client;
import com.fin.entity.Jsonable;
import com.fin.entity.employee.Educator;
import com.fin.entity.group.GradeBook;
import com.fin.repository.ClientRepository;
import com.fin.repository.MainRepository;
import com.fin.repository.employee.EmployeeRepository;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Stateless
public class EducatorEJB {

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private MainRepository mainRepository;

    public JsonObject getGradeBook(String educatorUsername, Date day) {
        // TODO create new one if doesn't exist
        Client client = clientRepository.findByUsername(educatorUsername);
        Educator educator = (Educator) employeeRepository.findByClient(client);
        List<GradeBook> gradeBooks = educator.getGradeBookList().stream().filter(gradeBook -> {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
            return fmt.format(gradeBook.getDate()).equals(fmt.format(day));
        }).collect(Collectors.toList());
        return Jsonable.wrapList(gradeBooks).asJsonObject();
    }

    public void fillGradeBookList(List<GradeBookEntryDto> dtoList, String educatorUsername) {
        Client client = clientRepository.findByUsername(educatorUsername);
        Educator educator = (Educator) employeeRepository.findByClient(client);

        for (GradeBookEntryDto dto : dtoList) {
            GradeBook gradeBook = mainRepository.find(GradeBook.class, dto.getId());
            gradeBook.setAttend(dto.isAttend());
            gradeBook.setBehavior(dto.getBehavior());
            gradeBook.setDate(dto.getDate());
            gradeBook.setEatingScore(dto.getEatingScore());
            gradeBook.setNote(dto.getNote());
            gradeBook.setEducator(educator);
            mainRepository.update(gradeBook);
        }
    }


}
