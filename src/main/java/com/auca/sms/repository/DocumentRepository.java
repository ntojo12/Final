package com.auca.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.auca.sms.entity.*;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}

