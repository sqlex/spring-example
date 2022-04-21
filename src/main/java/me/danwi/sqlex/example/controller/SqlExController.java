package me.danwi.sqlex.example.controller;

import me.danwi.sqlex.example.dao.DepartmentDao;
import me.danwi.sqlex.example.dao.PersonDao;
import me.danwi.sqlex.spring.SpringDaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class SqlExController {

    @Autowired
    private SpringDaoFactory factory;

    @Transactional
    @RequestMapping("/")
    public void addPerson() {
        //通过factory获取到数据访问对象
        DepartmentDao departmentDao = factory.getInstance(DepartmentDao.class);
        PersonDao personDao = factory.getInstance(PersonDao.class);
        personDao.addPerson("sqlex", 0);
        departmentDao.addDepartment(0, "dev2"); //第二次执行会主键冲突,整体回滚
    }

    @RequestMapping("/count")
    public String getCountOfDepartment(@RequestParam("name") String name) {
        return factory.getInstance(PersonDao.class)
                .findAmountOfDepartment(name)
                .stream().map(it -> it.getDepartmentName() + " : " + it.getAmount())
                .collect(Collectors.joining(" - "));
    }
}
