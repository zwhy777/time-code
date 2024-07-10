package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.Trip;
import com.example.service.TripService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Validated
@RestController
@RequestMapping("/api/index")
public class TripController {

    @Resource
    TripService service;
    
    @PostMapping("/list_trip")
    public RestBean<List<Trip>> listTrip(){
        List<Trip> tripList = service.listTrip();
        return RestBean.success(tripList);
    }

    @PostMapping("/add_trip")
    public RestBean<Integer> addTrip(@RequestParam String name,
                                     @RequestParam String type,
                                     @RequestParam String beginTime,
                                     @RequestParam String endTime,
                                     @RequestParam String notes){
        if(StringUtils.isAnyEmpty(name,type,beginTime,endTime)) {
            return RestBean.failure(400,-1);
        }
        Trip trip = new Trip();
        trip.setName(name);
        trip.setType(type);
        trip.setBeginTime(beginTime);
        trip.setEndTime(endTime);
        trip.setNotes(notes);
        int id = service.addTrip(trip);
        if(id == -1){
            return RestBean.failure(400,-1);
        }
        return RestBean.success(id);
    }

    @PostMapping("/show_week")
    public RestBean<List> showWeek(@RequestParam String begin, @RequestParam String end){
        List<Long> list = new ArrayList<>();
        List<Trip> tripList = service.showWeek(begin,end);
        long workhour = 0,workmin =0, worksecond = 0 , learnhour = 0, learnmin = 0, learnsecond =0;
        int sporttime = 0,playtime = 0;
        for(Trip trip:tripList){
            if(Objects.equals(trip.getType(),"sport")){
                sporttime = sporttime + 1;
            }else if(Objects.equals(trip.getType(),"amusement")){
                playtime = playtime + 1;
            }else{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime1 = LocalDateTime.parse(trip.getBeginTime(), formatter);
                LocalDateTime dateTime2 = LocalDateTime.parse(trip.getEndTime(), formatter);
                Duration duration = Duration.between(dateTime1, dateTime2);
                System.out.println(trip.getType());
                if(Objects.equals(trip.getType(),"work")){
                    workhour =workhour + duration.toHours();
                    workmin = workmin+duration.toMinutesPart();
                    worksecond = worksecond+duration.toSecondsPart();
                    System.out.println("工作" +workhour+ " 小时 " +workmin+ " 分钟 " +worksecond+ " 秒");
                }else if(Objects.equals(trip.getType(), "study")){
                    learnhour =learnhour + duration.toHours();
                    learnmin = learnmin+duration.toMinutesPart();
                    learnsecond = learnsecond+duration.toSecondsPart();
                    System.out.println("学习"+ learnhour+ " 小时 " +learnmin+ " 分钟 " +learnsecond+ " 秒");
                }
                System.out.println(duration.toHours());
                System.out.println("时间差值： " + duration.toHours() + " 小时 " + duration.toMinutesPart() + " 分钟 " + duration.toSecondsPart() + " 秒");
            }
        }
        System.out.println(playtime);
        System.out.println(sporttime);
        list.add(workhour);
        list.add(learnhour);
        list.add((long)playtime);
        list.add((long)sporttime);
        return RestBean.success(list);
    }

    @PostMapping("delete_trip")
    public RestBean<String> deleteTrip(@RequestParam int id){
        if(id < 0){
            return RestBean.failure(401,"参数错误");
        }
        String s = service.deleteTrip(id);
        return RestBean.success(s);
    }

    @PostMapping("show_histogram")
    public RestBean<float[][]> showHistogram(@RequestParam int type){
        if(type < 1 || type > 3){
            return RestBean.failure(401);
        }
        List<Trip> tripList = service.listTrip();
        float[][] rawData = service.showHistogram(tripList, type);
        return RestBean.success(rawData);
    }

    @PostMapping("show_pieChart")
    public RestBean<float[]> showPieChart(@RequestParam int type){
        if(type < 1 || type > 3){
            return RestBean.failure(401);
        }
        List<Trip> tripList = service.listTrip();
        float[] value = service.showPieChart(tripList, type);
        return RestBean.success(value);
    }
}

