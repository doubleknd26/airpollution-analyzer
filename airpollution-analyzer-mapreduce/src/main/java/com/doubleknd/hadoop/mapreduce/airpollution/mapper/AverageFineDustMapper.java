package com.doubleknd.hadoop.mapreduce.airpollution.mapper;

import com.doubleknd.hadoop.mapreduce.airpollution.common.Counters;
import com.doubleknd.hadoop.mapreduce.airpollution.model.AirPollutionInfo;
import com.doubleknd.hadoop.mapreduce.airpollution.parser.AirPollutionInfoParser;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by doubleknd26 on 01/10/2017.
 */
public class AverageFineDustMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    private AirPollutionInfoParser airPollutionInfoParser = new AirPollutionInfoParser();

    private Text outputKey = new Text();
    private DoubleWritable outputValue = new DoubleWritable();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        AirPollutionInfo airPollutionInfo = airPollutionInfoParser.parse(value);

        if (null == airPollutionInfo) {
            context.getCounter(Counters.invalid_format).increment(1);
        }
        else {
            outputKey.set(airPollutionInfo.getLocation());
            outputValue.set(airPollutionInfo.getFineDust());
            context.write(outputKey, outputValue);
        }
    }
}
