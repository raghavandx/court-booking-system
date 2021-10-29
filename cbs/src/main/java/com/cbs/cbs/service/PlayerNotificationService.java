package com.cbs.cbs.service;

import com.cbs.cbs.entity.Booking;
import com.cbs.cbs.entity.Player;

import java.util.List;

public interface PlayerNotificationService {
    void notify(Booking booking);
}
