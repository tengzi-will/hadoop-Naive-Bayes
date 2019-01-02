package WordCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

/*
 * �����key LongWritable   �к�
 * �����value Text         һ������
 * �����key Text           ����
 * �����value IntWritable  ���ʵĸ���
 */
public class WordCountMapper extends Mapper<LongWritable, Text, TextPair, IntWritable>{

	Text className = new Text();
	Text wordName = new Text();
	TextPair k  = new TextPair();
	IntWritable v = new IntWritable(1);
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		// 1.��ȡ����
		InputSplit inputSplit = context.getInputSplit();
		String dirName = ((FileSplit) inputSplit).getPath().getParent().getName();
		
		// 2. һ������ת����string
		String line = value.toString();
		
		// 3. �и�
		String[] words = line.split(" ");
		
		// 3 ѭ��д������һ���ض�
		for (String word : words) {
			className.set(dirName);
			wordName.set(word);
			k.set(className, wordName);
			context.write(k, v);
		}
	}
}
