package com.example.mapper;

import com.example.entity.Trip;
import org.apache.ibatis.annotations.*;
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

    @Delete("delete from Trip where id = #{id}")
    int deleteTrip(int id);
}
