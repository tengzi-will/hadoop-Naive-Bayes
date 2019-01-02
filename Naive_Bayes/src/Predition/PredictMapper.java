package Predition;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class PredictMapper extends Mapper<NullWritable, Text, Text, Text>{
	Text k = new Text();
	@Override
	protected void setup(Mapper<NullWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		// 获取文件的路径和名称（类名）
		FileSplit split = (FileSplit) context.getInputSplit();
		
		Path path = split.getPath();
//		k.set(path.toString());
		k.set(path.getName()+"&"+path.getParent().getName());
	}
	
	@Override
	protected void map(NullWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		Text result = new Text();
//		double prob = 0;
		String[] CLASS_NAMES = {"CHINA","CANA"};
		for(String classname:CLASS_NAMES) {
            result.set(classname+"&"+Double.toString(Prediction.conditionalProbabilityForClass(value.toString(),classname)));
            context.write(k,result);
		}
	}
}
