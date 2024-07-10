package com.nhnacademy.auth.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.nhnacademy.auth.service.DormantService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class DormantServiceImpl implements DormantService {
	private static final String MEMBER_PREFIX = "email:";
	@Autowired
	private RedisTemplate<String, Object> dormantTemplate;

	public void saveVerificationCode(String email, String access, String refresh){
		String uuid = UUID.randomUUID().toString();
		String key = MEMBER_PREFIX + email;
		dormantTemplate.opsForValue().set(key, uuid, 3, TimeUnit.MINUTES);
		log.info("Verification code saved for email: {}", email);
	}
	public String getVerificationCode(String email){
		String key = MEMBER_PREFIX + email;
		return dormantTemplate.opsForValue().get(key);
	}
	public boolean checkVerificationCode(String memberId, String verificationCode){
		String verificationCodeFromRedis = getVerificationCode(memberId);
		return verificationCode.equals(verificationCodeFromRedis);
	}//비교값 넣어준다.
}

