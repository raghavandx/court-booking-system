package com.cbs.cbs.service;

import com.cbs.cbs.entity.Booking;
import org.springframework.dao.DataAccessException;

import java.util.Date;

public interface CourtAllocationEngine {
    void allocate(Booking currentBooking);
}
