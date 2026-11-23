package com.jobmate.jobmate.repository;

import com.jobmate.jobmate.model.job.Jobs;
import org.springframework.beans.PropertyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Jobs, Long> {
    List<Jobs> findByUsername(String username);

    List<Jobs> findByUsernameAndUrlIn(String username, List<String> jobUrls);
}
