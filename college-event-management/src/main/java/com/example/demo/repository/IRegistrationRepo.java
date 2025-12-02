package com.example.demo.repository;

import com.example.demo.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface IRegistrationRepo extends JpaRepository<Registration, Long> {
    List<Registration> findByStudentId(Long studentId);
    List<Registration> findByEventId(Integer eventId);
    Optional<Registration> findByStudentIdAndEventId(Long studentId, Integer eventId);
}
