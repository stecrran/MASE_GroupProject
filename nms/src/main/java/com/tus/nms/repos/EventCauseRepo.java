package com.tus.nms.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.tus.nms.model.EventCause;
import com.tus.nms.model.EventRecord;

public interface EventCauseRepo extends JpaRepository<EventCause, Long>{

	@Modifying
    @Transactional
    @Query("DELETE FROM EventCause")
    void deleteAllEntities();
}
