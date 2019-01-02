package Predition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PredictDriver {
	
	public static void main(String[] args) throws NumberFormatException, IOException, ClassNotFoundException, InterruptedException {
		
		args = new String[] {"e:/INPUT/TEST", "e:/z_output_class"};
		
		// 1 ��ȡjob��Ϣ
		Prediction prediction = new Prediction();
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "prediction");
		
		// 2 ��ȡjar��λ��
		job.setJarByClass(Prediction.class);
		
		// 3 �����Զ����mapper��reducer
		job.setMapperClass(PredictMapper.class);
		job.setReducerClass(PredictReducer.class);
		
		// 4 �����Զ����InputFormat��
		job.setInputFormatClass(PredictTestInputFormat.class);	
		
		// 5 ����map�����������
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		// 6 �������������������
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);	
		
		// 7 �������������ļ�·��
		ArrayList<Path> paths = GetPaths(args[0]);
		for(int i=0; i < paths.size(); i++) {
			FileInputFormat.addInputPath(job, paths.get(i));			
		}	
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		// 8 �ύ����
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
