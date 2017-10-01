package com.doubleknd.hadoop.airpollution;

import com.doubleknd.hadoop.airpollution.mapper.AverageFineDustMapper;
import com.doubleknd.hadoop.airpollution.reducer.AverageFindDustReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Created by doubleknd26 on 01/10/2017.
 */
public class AirPollutionAnalyzer extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new AirPollutionAnalyzer(), args);
        System.out.println("MapReduce Result : " + res);
    }

    @Override
    public int run(String[] args) throws Exception {
        String[] otherArgs = new GenericOptionsParser(getConf(), args).getRemainingArgs();

        if (otherArgs.length != 2) {
            System.err.println("USAGE :: AirPollutionAnalyzer <input> <output>");
            System.exit(2);
        }

        Job job = new Job(getConf(), "AirPollutionAnalyzer");

        // set I/O format
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

        job.setJarByClass(AirPollutionAnalyzer.class);

        job.setMapperClass(AverageFineDustMapper.class);
        job.setReducerClass(AverageFindDustReducer.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        job.waitForCompletion(true);
        return 0;
    }
}
