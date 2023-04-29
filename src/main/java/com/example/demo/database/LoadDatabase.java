package com.example.demo.database;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDataBase(EmployeeRepository repository) {
        return args -> {
          log.info("Preloading " + repository.save(new Employee("NAM", "Giung", "programmer")));
            log.info("Preloading " + repository.save(new Employee("NAM", "Giyun", "chef")));
        };
    }
}
