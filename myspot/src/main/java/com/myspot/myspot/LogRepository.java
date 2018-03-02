package com.myspot.myspot;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface LogRepository extends JpaRepository<Log, Long> {
    Collection<Log> findByAccountUsername(String username);
}
