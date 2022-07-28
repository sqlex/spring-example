package me.danwi.sqlex.example.controller;

import me.danwi.sqlex.core.type.PagedResult;
import me.danwi.sqlex.example.dao.DepartmentDao;
import me.danwi.sqlex.example.dao.Person;
import me.danwi.sqlex.example.dao.PersonDao;
import me.danwi.sqlex.example.dao.PersonTable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SqlExController {
    private final PersonDao personDao;

    private final PersonTable personTable;

    private final DepartmentDao departmentDao;

    public SqlExController(PersonDao personDao, PersonTable personTable, DepartmentDao departmentDao) {
        this.personDao = personDao;
        this.personTable = personTable;
        this.departmentDao = departmentDao;
    }

    @RequestMapping("/")
    public PagedResult<PersonDao.AllPerson> getAll(@RequestParam("pageSize") long pageSize, @RequestParam("pageNo") long pageNo) {
        return personDao.findAllPerson(pageSize, pageNo);
    }

    @RequestMapping("/paged")
    public PagedResult<Person> paged(@RequestParam("pageSize") long pageSize, @RequestParam("pageNo") long pageNo) {
        return personTable.select().page(pageSize, pageNo);
    }

    @Transactional
    @RequestMapping("/add")
    public void addPerson() {
        //通过factory获取到数据访问对象
        personDao.addPerson("sqlex", 0);
        departmentDao.addDepartment(0, "dev2"); //第二次执行会主键冲突,整体回滚
    }

    @RequestMapping("/get")
    public PersonDao.Person getPerson(@RequestParam("id") int id) {
        return personDao.findPerson(id);
    }

    @RequestMapping("/count")
    public List<PersonDao.DepartmentAmount> getCountOfDepartment(@RequestParam("name") String name) {
        return personDao.findAmountOfDepartment(name);
    }
}
