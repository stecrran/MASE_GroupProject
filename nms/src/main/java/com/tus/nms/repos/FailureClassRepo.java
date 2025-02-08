package com.tus.nms.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.tus.nms.model.EventCause;
import com.tus.nms.model.EventRecord;
import com.tus.nms.model.FailureClass;

public interface FailureClassRepo extends JpaRepository<FailureClass, Integer>{

	@Modifying
    @Transactional
    @Query("DELETE FROM FailureClass")
    void deleteAllEntities();
}
