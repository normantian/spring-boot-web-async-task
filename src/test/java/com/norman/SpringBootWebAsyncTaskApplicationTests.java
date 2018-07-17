package com.norman;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootWebAsyncTaskApplicationTests {

//	@Autowired
//	StringRedisTemplate stringRedisTemplate;

	@Resource(name = "stringRedisTemplate")
	ValueOperations<String, String> valOpsStr;

	@Test
	public void poolError() {
		for (int i= 0; i<10; i++){

//			RedisConnection connection = null;
//			connection = stringRedisTemplate.getConnectionFactory().getConnection();
//			final long longValue = stringRedisTemplate.getConnectionFactory().getConnection().incr(("node").getBytes()).longValue();
//			System.out.println(longValue);

//			try{
//				connection = stringRedisTemplate.getConnectionFactory().getConnection();
//				connection.setNX(("node"+i).getBytes(),"0".getBytes());
//				System.out.println(i);
//			} finally {
//				if(connection != null) {
//					connection.close();
//				}
//			}

			valOpsStr.setIfAbsent("node"+i, i+"");

					//.set(("node"+i).getBytes(), (i+"").getBytes());


		}
	}

	@Test
	public void right() {
		for (int i= 0; i<10; i++){
			System.out.println(valOpsStr.get("node" + i));

			valOpsStr.getOperations().delete("node" + i);
		}
	}

}
