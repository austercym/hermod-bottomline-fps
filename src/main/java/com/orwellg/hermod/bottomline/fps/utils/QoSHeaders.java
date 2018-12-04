package com.orwellg.hermod.bottomline.fps.utils;

public class QoSHeaders {

    public Long getQosTimestamp() {
        return qosTimestamp;
    }

    public void setQosTimestamp(Long qosTimestamp) {
        this.qosTimestamp = qosTimestamp;
    }

    public Integer getQosSLA() {
        return qosSLA;
    }

    public void setQosSLA(Integer qosSLA) {
        this.qosSLA = qosSLA;
    }

    private Long qosTimestamp = null;
    private Integer qosSLA = null;
}
