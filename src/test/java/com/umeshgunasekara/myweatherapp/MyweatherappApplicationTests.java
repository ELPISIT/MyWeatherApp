package com.umeshgunasekara.myweatherapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.TimeZone;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyweatherappApplicationTests {

	@Test
	public void contextLoads() {

	}

	@Test
	public void testTimeProblem(){
//		Timestamp timestamp = new Timestamp(System.currentTimeMillis()/1000L);
//		System.out.println(timestamp.getTime());
//		System.out.println("hello");
//		Date today=new Date(1570417200*1000L);
//		System.out.println(today);
//		LocalDateTime lk = LocalDateTime.now();
//		ZoneId zoneId = ZoneId.of("Europe/Paris");
//		ZonedDateTime now=lk.withMinute(0).withSecond(0).atZone(zoneId);
//		int year = now.getYear();
//		int month = now.getMonthValue();
//		int day = now.getDayOfMonth();
//		int hour = now.getHour();
//		int minute = now.getMinute();
//		int second = now.getSecond();
//		System.out.println(now);
//		System.out.println(year+" , "+month+" , "+day+" , "+hour+" , "+minute+" , "+second);
//		ZoneId zoneMontréal = ZoneId.of("America/Montreal");
//
//		ZonedDateTime nowMontréal = ZonedDateTime.now ( zoneMontréal );
//		System.out.println(nowMontréal);
//		ZoneId zoneTokyo = ZoneId.of("Asia/Tokyo");
//		ZonedDateTime nowTokyo = nowMontréal.withZoneSameInstant( zoneTokyo );
//		System.out.println(nowTokyo);
//		ZonedDateTime nowUtc = nowMontréal.withZoneSameInstant( zoneId );
//		System.out.println(nowUtc);
//		ZoneId sltz = ZoneId.of("Asia/Colombo");
//		ZoneId singtz = ZoneId.of("Asia/Singapore");
//		LocalDateTime tsing = LocalDateTime.now();
//		System.out.println(tsing);
//		ZonedDateTime tsl = ZonedDateTime.now(sltz);
//		System.out.println(tsl);
//		LocalDateTime editadsing=tsing.withMinute(0).withSecond(0);
//		System.out.println(tsl);
//		Timestamp timestamp = Timestamp.valueOf(editadsing);
//		System.out.println(timestamp.getTime()/1000L);

		ZoneId tmap = ZoneId.of("Asia/Jakarta");
		Timestamp timestamp2 = Timestamp.valueOf(ZonedDateTime.now(tmap).withMinute(0).withSecond(0).toLocalDateTime());
		System.out.println(timestamp2.getTime()/1000L);

		long test_timestamp = 1570453885*1000L;
		LocalDateTime triggerTime =
				LocalDateTime.ofInstant(Instant.ofEpochMilli(test_timestamp),
						tmap);
		ZonedDateTime triggerTime2 =
				ZonedDateTime.ofInstant(Instant.ofEpochMilli(test_timestamp),
						tmap);
		String pattern = "EEEEE MMMMM yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		System.out.println(date);
//		ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
		Date output = Date.from(triggerTime2.toInstant());
		String date2 = simpleDateFormat.format(output);
		System.out.println(date2);
		System.out.println(triggerTime2);
		System.out.println(triggerTime2.getHour()+"");
//		Timestamp timestamp3 = Timestamp.valueOf(triggerTime.withMinute(0).withSecond(0));
////

//		System.out.println(triggerTime);
//		System.out.println(triggerTime2);
//		System.out.println(timestamp3.getTime()/1000L);
//
//		ZonedDateTime tsl = ZonedDateTime.now(tmap);
//		System.out.println(tsl);
//
//		Timestamp timestamp4 = Timestamp.valueOf(ZonedDateTime.now(tmap).withMinute(0).withSecond(0).toLocalDateTime());
//		System.out.println(timestamp4.getTime()/1000L);

	}

}
