package com.nhnacademy.auth.member.repository;

import com.nhnacademy.auth.entity.pointRecord.PointRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRecordRepository extends JpaRepository<PointRecord, Long> {
    //안건드려도됌
}
