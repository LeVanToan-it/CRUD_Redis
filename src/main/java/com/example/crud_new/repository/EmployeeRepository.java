package com.example.crud_new.repository;

import com.example.crud_new.model.Employee;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    //private HashOperations hashOperations;//crud hash
	private ListOperations listOperations; //crud list



    private RedisTemplate redisTemplate;

    public EmployeeRepository(RedisTemplate redisTemplate) {
    	this.listOperations = redisTemplate.opsForList();
        //this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;

    }

    public void saveEmployee(Employee employee){
    	listOperations.leftPush("LEMPLOYEE", employee.getId(), employee);
        //hashOperations.put("EMPLOYEE", employee.getId(), employee);
    }
    public List<Employee> findAll(){
    	return (List<Employee>) listOperations.range("LEMPLOYEE",0,2);
        //return hashOperations.values("EMPLOYEE");
    }
    public Employee findById(Integer id){
    	return (Employee) listOperations.index("LEMPLOYEE", id);
        //return (Employee) hashOperations.get("EMPLOYEE", id);
    }

    public void update(Employee employee){
        saveEmployee(employee);
    }
    public void delete(Integer id){
    	listOperations.remove("LEMPLOYEE", 1,id);
        //hashOperations.delete("EMPLOYEE", id);
    }
}
