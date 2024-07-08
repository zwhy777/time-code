package com.example.service.Impl;

import com.example.entity.Trip;
import com.example.mapper.TripMapper;
import com.example.service.TripService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
