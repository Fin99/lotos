package com.fin.ejb;

import com.fin.entity.Child;
import com.fin.entity.Client;
import com.fin.entity.Jsonable;
import com.fin.entity.employee.Teacher;
import com.fin.entity.group.Diary;
import com.fin.repository.ClientRepository;
import com.fin.repository.MainRepository;
import com.fin.repository.employee.EmployeeRepository;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Stateless
public class TeacherEJB {

    @Inject
    private MainRepository mainRepository;

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private EmployeeRepository employeeRepository;

    public JsonObject getDiaries(String teacherUsername, Date date) {
        Client client = clientRepository.findByUsername(teacherUsername);
        Teacher teacher = (Teacher) employeeRepository.findByClient(client);
        List<Diary> diaries = mainRepository.findAll(Diary.class).stream().filter(diary -> {
            SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
            return fmt.format(diary.getDate()).equals(fmt.format(date));
        }).collect(Collectors.toList());
        if (diaries.size() == 0) {
            return createDiaries(teacher, date);
        }
        return Jsonable.wrapList(diaries).asJsonObject();
    }

    private JsonObject createDiaries(Teacher teacher, Date date) {
        List<Child> children = mainRepository.findAll(Child.class);
        List<Diary> diaries = new ArrayList<>();
        for (Child child : children) {
            Diary diary = new Diary();
            diary.setChild(child);
            diary.setDate(date);
            mainRepository.create(diary);
            diaries.add(diary);
        }
        return Jsonable.wrapList(diaries).asJsonObject();
    }
}
