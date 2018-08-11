package com.doubleknd.hadoop.mapreduce.airpollution;

import com.doubleknd.hadoop.mapreduce.airpollution.mapper.AverageFineDustMapper;
import com.doubleknd.hadoop.mapreduce.airpollution.reducer.AverageFindDustReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kideok Kim on 01/10/2017.
 */
public class AirPollutionAnalyzer extends Configured implements Tool {
    private static final String SOURCE_FILE = "air_pollution.csv";
    private static final String SOURCE_PATH = "data/" + SOURCE_FILE;
    private static final String JOB_NAME = "AirPollutionAnalyzer";
    private static final String SUCCESS_FILE = "_SUCCESS";

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new AirPollutionAnalyzer(), args);
        System.out.println("MapReduce Result : " + res);
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

        if (otherArgs.length != 1) {
            System.err.println("Only need output path to run this application.");
            System.err.println("e.g) hadoop jar airpollution-analyzer-mapreduce-1.0-SNAPSHOT.jar output_path");
            System.exit(1);
        }

        // load data from local to hdfs.
        FileSystem fs = FileSystem.get(conf);
        final Path dir = new Path("/input");
        fs.delete(dir, true);
        fs.mkdirs(dir);
        fs.copyFromLocalFile(new Path(SOURCE_PATH), dir);

        // create a mapreduce job
        Job job = Job.getInstance(getConf(), JOB_NAME);

        // set I/O format
        Path inputPath = new Path(dir.toString() + "/" + SOURCE_FILE);
        Path outputPath = new Path(otherArgs[0]);
        FileInputFormat.addInputPath(job, inputPath);
        FileOutputFormat.setOutputPath(job, outputPath);

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
