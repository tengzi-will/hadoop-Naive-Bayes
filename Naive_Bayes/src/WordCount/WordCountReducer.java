package WordCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<TextPair, IntWritable, TextPair, IntWritable>{
	
	@Override
	protected void reduce(TextPair key, Iterable<IntWritable> values,
			Context context) throws IOException, InterruptedException {
		
		// 1 ͳ�Ƶ����ܸ���
		int sum = 0;
		for (IntWritable count : values) {
			sum += count.get();
		}
		
		// 2 ��������ܸ���
		context.write(key, new IntWritable(sum));
	}
}
