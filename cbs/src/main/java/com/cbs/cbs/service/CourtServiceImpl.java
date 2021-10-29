package com.cbs.cbs.service;

import com.cbs.cbs.entity.Court;
import com.cbs.cbs.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
public class CourtServiceImpl implements CourtService {

    @Autowired
    private CourtRepository courtRepository;

    @PostConstruct
    public boolean addDefaultCourts() {

        var court = new Court(1001L, 1, "Court1");
        var courts = new ArrayList<Court>();
        courts.add(court);
        court = new Court(1001L, 2, "Court2");
        courts.add(court);
        court = new Court(1001L, 3, "Court3");
        courts.add(court);
        courts.stream().forEach(item -> {
            courtRepository.save(item);
        });

        return true;
    }

    @Override
    public boolean addCourt(int courtNumber, String courtName) {
       var court = new Court(0L, courtNumber, courtName);
       courtRepository.save(court);
       return true;
    }
}
