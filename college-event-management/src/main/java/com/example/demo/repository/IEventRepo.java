package com.example.demo.repository;

import com.example.demo.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IEventRepo extends JpaRepository<Event, Integer> {
    List<Event> findByEventDate(LocalDate date);
}
