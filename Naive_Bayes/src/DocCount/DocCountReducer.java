package DocCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DocCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	
	IntWritable value = new IntWritable();
	
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Context context) throws IOException, InterruptedException {
		
		// 1. ͳ���ĵ��ܸ���
		int sum = 0;
		for (IntWritable count : values) {
			sum += count.get();
		}
		
		// 2 ��������ܸ���
		value.set(sum);
		context.write(key, value);
	}
}
