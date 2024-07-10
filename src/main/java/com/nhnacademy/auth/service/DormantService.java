package com.nhnacademy.auth.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

public interface DormantService {
	public void saveVerificationCode(String memberId, String verificationCode);
	public String getVerificationCode(String memberId);
	public boolean checkVerificationCode(String memberId, String verificationCode);
}

