/*
* Copyright 2019 NexCloud Co.,Ltd.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.nexcloud.api.client;
import java.io.Serializable;

import javax.annotation.Resource;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.nexcloud.util.Util;

@Service
public class RedisClient implements Serializable {
	static final Logger logger = LoggerFactory.getLogger(RedisClient.class);

	// RedisConfiguration의 redisTemplate 메서드. StringRedisTemplate 타입의 객체를 주입
	// Redis 자료구조 중 hash를 사용하는 경우 hashOps로 처리
	@Resource(name = "redisTemplate")
	private HashOperations<String, String, String> hashOps;

	// Redis 자료구조 중 간단한 string을 사용하는 경우 valueOps로 처리
	@Resource(name = "redisTemplate") 
	private ValueOperations<String,String> valueOps;
    
    public void put( String key, String hashKey, String data) {
    	try {
    		hashOps.put(key, hashKey, data);
		} catch (Exception e) {
			logger.debug("[Redis Put Error key ["+ key +"], hashKey["+ hashKey +"], data["+ data +"]");
		}
    }
    
    public void put( String key, String data) {
    	try {
    		valueOps.set(key, data);
		} catch (Exception e) {
			logger.debug("[Redis Put Error key ["+ key +"], data["+ data +"]");
		}
    }
    
    public String get( String key, String hashKey) {
    	try{
	    	logger.debug("[Redis Request]"
	    			+ "\n KEY [" + key + "]" + "\n HASH KEY ["+ hashKey +"]");
			
	    	String data = null;
	    	data = hashOps.get(key, hashKey);

	    	//logger.debug("[Redis response]" + "\n Response [" + data + "]");
			return data;
    	}catch(Exception e){
    		logger.error("[Redis Get Error]"
	    			+ "\n KEY [" + key + "]" + "\n HASH KEY ["+ hashKey +"]");
    		
    		return Util.makeStackTrace(e);
    	}
    }

    
    public String get( String key ) {
    	try{
	    	logger.debug("[Redis Request]"
	    			+ "\n KEY [" + key + "]");
			
	    	String data = null;
	    	data = valueOps.get(key);
	    	
	    	//logger.debug("[Redis response]" + "\n Response [" + data + "]");
			return data;
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("[Redis Get Error]"
	    			+ "\n KEY [" + key + "]");
    		
    		return Util.makeStackTrace(e);
    	}
    }
}
