package com.example.service;

import com.example.entity.Trip;

import java.util.List;

public interface TripService {
    List<Trip> listTrip();

    int addTrip(Trip trip);

    List<Trip> showWeek(String begin, String end);

    String deleteTrip(int id);

    float[][] showHistogram(List<Trip> tripList, int timeType);

    float[] showPieChart(List<Trip> tripList, int timeType);
}
