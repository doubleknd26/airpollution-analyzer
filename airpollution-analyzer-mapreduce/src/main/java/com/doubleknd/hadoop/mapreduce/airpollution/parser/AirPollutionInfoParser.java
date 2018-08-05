package com.doubleknd.hadoop.mapreduce.airpollution.parser;

import com.doubleknd.hadoop.mapreduce.airpollution.model.AirPollutionInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.Text;

/**
 * Created by doubleknd26 on 01/10/2017.
 */
public class AirPollutionInfoParser {

    public AirPollutionInfo parse (Text text) {
        String[] line = text.toString().split(",");

        if (line.length < 8) {
            return null;
        }

        String date = line[0]; // 날짜
        String location = line[1]; // 위치

        Double nitrogenDioxideConcentration = getNumericalValue(line[2]); // 이산화질소농도
        Double ozoneConcentration = getNumericalValue(line[3]); // 오존농도
        Double carbonMonoxideConcentration = getNumericalValue(line[4]); // 일산화탄소농도
        Double sulfurDioxideGas = getNumericalValue(line[5]); // 아황산가스
        Double fineDust = getNumericalValue(line[6]); // 미세먼지
        Double ultraFineDust = getNumericalValue(line[7]); // 초미세먼지

        AirPollutionInfo airPollutionInfo = new AirPollutionInfo();
        airPollutionInfo.setDate(date);
        airPollutionInfo.setLocation(location);
        airPollutionInfo.setNitrogenDioxideConcentration(nitrogenDioxideConcentration);
        airPollutionInfo.setOzoneConcentration(ozoneConcentration);
        airPollutionInfo.setCarbonMonoxideConcentration(carbonMonoxideConcentration);
        airPollutionInfo.setSulfurDioxideGas(sulfurDioxideGas);
        airPollutionInfo.setFineDust(fineDust);
        airPollutionInfo.setUltraFineDust(ultraFineDust);

        return airPollutionInfo;
    }

    private Double getNumericalValue (String numericalValue) {
        Double result = 0.0d;
        if (!StringUtils.isEmpty(numericalValue)) {
            result = Double.parseDouble(numericalValue);
        }
        return result;
    }
}
