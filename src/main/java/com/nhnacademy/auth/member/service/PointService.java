package com.nhnacademy.auth.member.service;

import com.nhnacademy.auth.entity.pointRecord.PointRecord;
import com.nhnacademy.auth.member.repository.PointRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;


@RequiredArgsConstructor
@Service
public class PointService {
    private final PointRecordRepository pointRecordRepository;

    /**
     * @Author -유지아
     * Save point record. -point가 추가,사용 되었을때 레코드를 저장한다.
     *
     * @param pointRecord the point record -pointrecord를 받아온다.
     * @return the point record -저장한 포인트값반환 - 저장한 후 그대로 반환한다.
     */
    public PointRecord save(PointRecord pointRecord) {
        return pointRecordRepository.save(pointRecord);
    }
}
