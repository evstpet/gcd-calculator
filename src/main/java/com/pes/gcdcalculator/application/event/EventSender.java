package com.pes.gcdcalculator.application.event;

import com.pes.gcdcalculator.domain.vo.Calculation;

public interface EventSender {

    void sendEvent(Calculation calculation);

}
