package com.doubleknd.hadoop.mapreduce.airpollution.model;

import lombok.Data;

/**
 * Created by Kideok Kim on 01/10/2017.
 */
@Data
public class AirPollutionInfo {
    private String date; // 날짜
    private String location; // 위치
    private Double nitrogenDioxideConcentration; // 이산화질소농도
    private Double ozoneConcentration; // 오존농도
    private Double carbonMonoxideConcentration; // 일산화탄소농도
    private Double sulfurDioxideGas; // 아황산가스
    private Double fineDust; // 미세먼지
    private Double ultraFineDust; // 초미세먼지
}
