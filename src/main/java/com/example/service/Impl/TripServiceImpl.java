package com.example.service.Impl;

import com.example.entity.Trip;
import com.example.mapper.TripMapper;
import com.example.service.TripService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Resource
    TripMapper tripMapper;

    @Override
    public List<Trip> listTrip() {
        return tripMapper.listTrip();
    }

    @Override
    public int addTrip(Trip trip) {
        if(trip == null){
            return -1;
        }
        tripMapper.addTrip(trip);
        return trip.getId();
    }

    @Override
    public List<Trip> showWeek(String begin, String end) {
        return tripMapper.showWeek(begin, end);
    }

    @Override
    public String deleteTrip(int id) {
        if(tripMapper.deleteTrip(id) <= 0){
            return null;
        }
        return "删除成功";
    }

    @Override
    public float[][] showHistogram(List<Trip> tripList, int timeType) {
        float[][] rawData = new float[6][7];
        for (float[] rawDatum : rawData) {
            Arrays.fill(rawDatum, 0);
        }
        for (Trip trip : tripList) {
            Date currentDate = new Date();
            String beginTime = trip.getBeginTime();
            String endTime = trip.getEndTime();
            String type = trip.getType();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = sdf.parse(beginTime);
                Date date2 = sdf.parse(endTime);
                long diffInMilliseconds = date2.getTime() - date1.getTime();
                // 转换为分钟
                float diffInMinutes = (float) diffInMilliseconds / (60 * 1000);

                Calendar beginCalendar = Calendar.getInstance();
                beginCalendar.setTime(date1);
                Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.setTime(currentDate);
                boolean isShow = false;
                switch (timeType){
                    case 1:
                        if (currentCalendar.get(Calendar.WEEK_OF_YEAR) == beginCalendar.get(Calendar.WEEK_OF_YEAR) && currentCalendar.get(Calendar.YEAR) == beginCalendar.get(Calendar.YEAR)){
                            isShow = true;
                        }
                        break;
                    case 2:
                        if (currentCalendar.get(Calendar.MONTH) == beginCalendar.get(Calendar.MONTH) && currentCalendar.get(Calendar.YEAR) == beginCalendar.get(Calendar.YEAR))
                            isShow = true;
                        break;
                    case 3:
                        if (currentCalendar.get(Calendar.YEAR) == beginCalendar.get(Calendar.YEAR))
                            isShow = true;
                        break;
                    default:
                        break;
                }
                if(isShow) {
                    int dayOfWeek = (beginCalendar.get(Calendar.DAY_OF_WEEK) + 5) % 7;
                    int typeNumber;
                    switch (type) {
                        case "杂事":
                            typeNumber = 1;
                            break;
                        case "运动":
                            typeNumber = 2;
                            break;
                        case "睡觉":
                            typeNumber = 3;
                            break;
                        case "学习":
                            typeNumber = 4;
                            break;
                        case "工作":
                            typeNumber = 5;
                            break;
                        case "娱乐":
                            typeNumber = 6;
                            break;
                        default:
                            typeNumber = 0;
                    }
                    if (typeNumber > 0) {
                        rawData[typeNumber - 1][dayOfWeek] = rawData[typeNumber - 1][dayOfWeek] + diffInMinutes;

                    } else {
                        return null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rawData;
    }

    @Override
    public float[] showPieChart(List<Trip> tripList, int timeType) {
        float[] value = new float[6];
        for (Trip trip : tripList) {
            Date currentDate = new Date();
            String beginTime = trip.getBeginTime();
            String endTime = trip.getEndTime();
            String type = trip.getType();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = sdf.parse(beginTime);
                Date date2 = sdf.parse(endTime);
                long diffInMilliseconds = date2.getTime() - date1.getTime();
                // 转换为分钟
                float diffInMinutes = (float) diffInMilliseconds / (60 * 1000);

                Calendar beginCalendar = Calendar.getInstance();
                beginCalendar.setTime(date1);
                Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.setTime(currentDate);
                boolean isShow = false;
                switch (timeType){
                    case 1:
                        if (currentCalendar.get(Calendar.WEEK_OF_YEAR) == beginCalendar.get(Calendar.WEEK_OF_YEAR) && currentCalendar.get(Calendar.YEAR) == beginCalendar.get(Calendar.YEAR)){
                            isShow = true;
                        }
                        break;
                    case 2:
                        if (currentCalendar.get(Calendar.MONTH) == beginCalendar.get(Calendar.MONTH) && currentCalendar.get(Calendar.YEAR) == beginCalendar.get(Calendar.YEAR))
                            isShow = true;
                        break;
                    case 3:
                        if (currentCalendar.get(Calendar.YEAR) == beginCalendar.get(Calendar.YEAR))
                            isShow = true;
                        break;
                    default:
                        break;
                }
                if(isShow) {
                    int typeNumber;
                    switch (type) {
                        case "杂事":
                            typeNumber = 1;
                            break;
                        case "运动":
                            typeNumber = 2;
                            break;
                        case "睡觉":
                            typeNumber = 3;
                            break;
                        case "学习":
                            typeNumber = 4;
                            break;
                        case "工作":
                            typeNumber = 5;
                            break;
                        case "娱乐":
                            typeNumber = 6;
                            break;
                        default:
                            typeNumber = 0;
                    }
                    if (typeNumber > 0) {
                        value[typeNumber - 1] = value[typeNumber - 1] + diffInMinutes;

                    } else {
                        return null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}
