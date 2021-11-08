package com.example.crud_new.repository;

import com.example.crud_new.model.Employee;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class EmployeeRepository {

    //private HashOperations hashOperations;//crud hash
	//private ListOperations listOperations; //crud list
	private SetOperations setOperations; //crud set
	
    private RedisTemplate redisTemplate;

    public EmployeeRepository(RedisTemplate redisTemplate) {
    	this.setOperations = redisTemplate.opsForSet();
    	//this.listOperations = redisTemplate.opsForList();
        //this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;

    }

    public void saveEmployee(Employee employee){
    	setOperations.add("SEMPLOYEE", employee);
    	//listOperations.leftPush("LEMPLOYEE", employee);
        //hashOperations.put("EMPLOYEE", employee.getId(), employee);
    }
    public Set<Employee> findAll(){
    	return (Set<Employee>) setOperations.members("SEMPLOYEE");
    	//return (List<Employee>) listOperations.range("LEMPLOYEE", 0, -1);
        //return hashOperations.values("EMPLOYEE");
    }
    public Employee findById(Integer id){
    	return (Employee) setOperations.randomMember("SEMPLOYEE");
    	//return (Employee) listOperations.index("LEMPLOYEE", id);
        //return (Employee) hashOperations.get("EMPLOYEE", id);
    }

    public void update(Employee employee){
    	setOperations.intersect("SEMPLOYEE", employee);
    	//listOperations.set("LEMPLOYEE", 1, employee);
        //saveEmployee(employee);
    }
    public void delete(Integer id){
    	setOperations.pop("SEMPLOYEE",id);
    	//listOperations.leftPop("LEMPLOYEE");
        //hashOperations.delete("EMPLOYEE", id);
    }
}
