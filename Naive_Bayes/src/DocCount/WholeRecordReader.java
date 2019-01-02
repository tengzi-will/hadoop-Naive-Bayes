package DocCount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class WholeRecordReader extends RecordReader<NullWritable, BytesWritable>{
	
	BytesWritable value = new BytesWritable();
	boolean isProcess = false;
	FileSplit split;
	Configuration configuration;
	
	@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		// ��ʼ��
		this.split = (FileSplit) split;
		configuration = context.getConfiguration();
	}
	
	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// ��ȡһ��һ�����ļ�
		if (!isProcess) {
			
			// 0.������
			byte[] buf = new byte[(int) split.getLength()];
			
			FileSystem fs = null;
			FSDataInputStream fis = null;
			try {
				// 1.��ȡ�ļ�ϵͳ
				Path path = split.getPath();
				fs = path.getFileSystem(configuration);
				
				// 2.���ļ�������
				fis = fs.open(path);
				
				// 3.���Ŀ���
				IOUtils.readFully(fis, buf, 0, buf.length);
				
				// 4.���������������ݵ��������
				value.set(buf, 0, buf.length);
			} catch (Exception e) {
			}finally {
				IOUtils.closeStream(fis);
				IOUtils.closeStream(fs);
			}
			isProcess = true;
			return true;
		}
		return false;
	}

	@Override
	public NullWritable getCurrentKey() throws IOException, InterruptedException {
		// ��ȡ��ǰ��
		return NullWritable.get();
	}

	@Override
	public BytesWritable getCurrentValue() throws IOException, InterruptedException {
		// ��ȡ��ǰֵ
		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// ��ȡ��ǰ����
		return isProcess? 1:0;
	}
	
	@Override
	public void close() throws IOException {
	}
}
