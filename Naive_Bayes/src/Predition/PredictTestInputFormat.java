package Predition;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class PredictTestInputFormat extends FileInputFormat<NullWritable,Text>{
	
    @Override
	protected boolean isSplitable(JobContext context, Path filename) {
		return false;
	}

	@Override
    public RecordReader<NullWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context)
    		throws  IOException, InterruptedException{
    	// 创建对象
		PredictTestRecordReader recordReader = new PredictTestRecordReader();
    	// 返回对象
		return recordReader;
    }
}