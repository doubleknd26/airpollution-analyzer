package com.doubleknd.hadoop.airpollution.reducer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by doubleknd26 on 01/10/2017.
 */
public class AverageFindDustReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    private DoubleWritable result = new DoubleWritable();

    @Override
    protected void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
        Double sum = 0.0d;
        int cnt = 0;

        for (DoubleWritable value: values) {
            sum += value.get();
            cnt++;
        }

        result.set(sum / cnt);
        context.write(key, result);
    }
}
