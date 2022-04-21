package me.danwi.sqlex.example.controller;

import me.danwi.sqlex.core.type.PagedResult;
import me.danwi.sqlex.example.dao.DepartmentDao;
import me.danwi.sqlex.example.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/test")
public class SqlEx2Controller {

    @Autowired
    private PersonDao personDao;
    @Autowired
    private DepartmentDao departmentDao;

    @RequestMapping("/")
    public PagedResult<PersonDao.AllPerson> getAll(@RequestParam("pageSize") long pageSize, @RequestParam("pageNo") long pageNo) {
        return personDao.findAllPerson(pageSize, pageNo);
    }

    @RequestMapping("/add")
    public void addPerson() {
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
