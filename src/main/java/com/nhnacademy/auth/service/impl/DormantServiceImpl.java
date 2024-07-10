package com.nhnacademy.auth.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.nhnacademy.auth.entity.DormantObject;
import com.nhnacademy.auth.service.DormantService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class DormantServiceImpl implements DormantService {
	private static final String MEMBER_PREFIX = "email:";
	@Autowired
	private RedisTemplate<String, Object> dormantTemplate;

	public String saveVerificationCode(String email, String access, String refresh){
		String uuid = UUID.randomUUID().toString();
		String key = MEMBER_PREFIX + email;
		DormantObject dormantObject = new DormantObject();
		dormantObject.setUuid(uuid);
		dormantObject.setAccess(access);
		dormantObject.setRefresh(refresh);
		dormantTemplate.opsForValue().set(key, dormantObject, 3, TimeUnit.MINUTES);
		log.info("Verification code saved for email: {}", email);
		return uuid;
	}
	public DormantObject getVerificationCode(String email){
		String key = MEMBER_PREFIX + email;
		if(dormantTemplate.opsForValue().get(key) instanceof DormantObject){
			return (DormantObject) dormantTemplate.opsForValue().get(key);
		}
		return null;
	}
	public DormantObject checkVerificationCode(String email, String verificationCode){
		DormantObject dormantObject = getVerificationCode(email);
		if(dormantObject.getUuid().equals(verificationCode)){
			return dormantObject;
		};
		return null;
	}//비교값 넣어준다.
}

