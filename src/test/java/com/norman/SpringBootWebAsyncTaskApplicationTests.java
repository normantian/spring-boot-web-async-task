package com.norman;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootWebAsyncTaskApplicationTests {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Test
	public void poolError() {
		for (int i= 0; i<10000; i++){

//			RedisConnection connection = null;
//			connection = stringRedisTemplate.getConnectionFactory().getConnection();
			final long longValue = stringRedisTemplate.getConnectionFactory().getConnection().incr(("node").getBytes()).longValue();
			System.out.println(longValue);

//			try{
//				connection = stringRedisTemplate.getConnectionFactory().getConnection();
//				connection.setNX(("node"+i).getBytes(),"0".getBytes());
//				System.out.println(i);
//			} finally {
//				if(connection != null) {
//					connection.close();
//				}
//			}

					//.set(("node"+i).getBytes(), (i+"").getBytes());


		}
	}

	@Test
	public void right() {
		for (int i= 0; i<10000; i++){
			stringRedisTemplate.delete("node"+i);
		}
	}

}
