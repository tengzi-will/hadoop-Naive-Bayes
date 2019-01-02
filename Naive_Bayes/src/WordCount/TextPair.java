package WordCount;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class TextPair implements WritableComparable<TextPair>{
	
	private Text dirName;
	private Text word;
	
	public TextPair() {
		set(new Text(), new Text());
	}
	
	public TextPair(String dirName, String word) {
		set(new Text(dirName), new Text(word));
	}
	
	public TextPair(Text dirName, Text word) {
		set(dirName, word);
	}

	public void set(Text dirName, Text word) {
		this.dirName = dirName;
		this.word = word;
	}
	
	public Text getFirst() {
		return dirName;
	}

	public Text getSecond() {
		return word;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		dirName.write(out);
		word.write(out);
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		dirName.readFields(in);
		word.readFields(in);
	}
	
	@Override
	public int hashCode() {
//		return super.hashCode();
		return dirName.hashCode() * 163 + word.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof TextPair) {
			TextPair tp = (TextPair) o;
			return dirName.equals(tp.dirName) && word.equals(tp.word);
		}
		return false;
//		return super.equals(obj);
	}

	@Override
	public String toString() {
		return dirName + "\t" + word;
//		return super.toString();
	}

	@Override
	public int compareTo(TextPair tp) {
		int cmp = dirName.compareTo(tp.dirName);
		if (cmp !=0) {
			return cmp;
		}
		return word.compareTo(tp.word);
	}
}
