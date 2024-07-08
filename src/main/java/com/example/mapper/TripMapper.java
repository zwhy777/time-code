package com.example.mapper;

import com.example.entity.Trip;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface TripMapper {
    @Select("select * from Trip")
    List<Trip> listTrip();

    @Insert("insert into Trip(name, type, beginTime, endTime, notes) values (#{name}, #{type}, #{beginTime}, #{endTime}, #{notes})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addTrip(Trip trip);

    @Select("select * from Trip where beginTime>=#{begin} and endTime<=#{end} ")
    List<Trip> showWeek(String begin,String end);
}
