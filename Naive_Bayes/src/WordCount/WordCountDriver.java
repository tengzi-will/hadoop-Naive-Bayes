package WordCount;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountDriver {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		args = new String[] {"e:/INPUT/TRAIN", "e:/z_output_word"};
		
		// 1 ��ȡjob��Ϣ
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);
		
		// 2 ��ȡjar��λ��
		job.setJarByClass(WordCountDriver.class);
		
		// 3 �����Զ����mapper��reducer
		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordCountReducer.class);
		
		// 4 ����map�����������
		job.setMapOutputKeyClass(TextPair.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		// 5 �������������������
		job.setOutputKeyClass(TextPair.class);
		job.setOutputValueClass(IntWritable.class);	
		
		// 6 �������������ļ�·��
		ArrayList<Path> paths = GetPaths(args[0]);
		for(int i=0; i < paths.size(); i++) {
			FileInputFormat.addInputPath(job, paths.get(i));			
		}
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		// 7 �ύ����
//		job.submit();
		boolean result = job.waitForCompletion(true);
		System.exit(result?0:1);
	}
	
	private static ArrayList<Path> GetPaths(String path) {
		// ��ȡpath·�����������ļ���·��
		ArrayList<Path> paths = new ArrayList<Path>();
		File file = new File(path);
		// ������·�����ļ���
		if (file.isDirectory()) {
			// ��ȡ·���µ������ļ�
			File[] files = file.listFiles();
			for (int i=0; i<files.length; i++) {
				// ��������ļ���
				if (files[i].isDirectory()) {
					// �������·���б�
					paths.add(new Path(files[i].getPath()));
				}
				else {continue;}
			}
		}
		return paths;
	}
	
}
