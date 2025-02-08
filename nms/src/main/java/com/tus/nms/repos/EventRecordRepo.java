package com.tus.nms.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.tus.nms.model.EventRecord;

public interface EventRecordRepo extends JpaRepository<EventRecord, Long>{

	EventRecord findByEventId(String eventId);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM EventRecord")
    void deleteAllEntities();
}
