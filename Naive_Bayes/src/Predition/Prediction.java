package Predition;

import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Prediction {

	private static Hashtable<String,Double> class_prob = new Hashtable<String, Double>();
	private static Hashtable<Map<String,String>,Double> class_term_prob = new Hashtable<Map<String, String>, Double>();
	private static Hashtable<String,Double> class_term_total = new Hashtable<String, Double>();
	private static Hashtable<String,Double> class_term_num = new Hashtable<String, Double>();
	Prediction() throws NumberFormatException, IOException{
		// �����������class_prob
		BufferedReader reader = new BufferedReader(new FileReader(new File("e://z_output_doc//"+"part-r-00000")));
		double file_total = 0; // ͳ���ĵ�����
		while(reader.ready()){
            String line = reader.readLine();
            String[] args = line.split("\t");
            file_total += Double.valueOf(args[1]);
        }
		reader = new BufferedReader(new FileReader(new File("e://z_output_doc//"+"part-r-00000")));
        while(reader.ready()){
            String line = reader.readLine();
            String[] args = line.split("\t");
            class_prob.put(args[0],Double.valueOf(args[1])/file_total);
            System.out.println(String.format(("%s:%f"),args[0],Double.valueOf(args[1])/file_total));
        }

        //���㵥������
        reader = new BufferedReader(new FileReader(new File("e://z_output_word//"+"part-r-00000")));
        while(reader.ready()){
            String line = reader.readLine();
            String[] args = line.split("\t");// 0���࣬1��������2����Ƶ
            double count = Double.valueOf(args[2]);
            String classname = args[0];
            class_term_total.put(classname,class_term_total.getOrDefault(classname,0.0)+count);
        }
        //���㵥�ʼ��ϴ�С
        reader = new BufferedReader(new FileReader(new File("e://z_output_word//"+"part-r-00000")));
        while(reader.ready()){
        	String line = reader.readLine();
        	String[] args = line.split("\t");// 0���࣬1��������2����Ƶ
        	String classname = args[0];
        	class_term_num.put(classname,class_term_num.getOrDefault(classname,0.0)+1.0);
        }
        System.out.println(String.format(("%f:%f"),class_term_num.get("CANA"),class_term_num.get("CHINA")));
        //����ÿ�����������ֵĴ�������class-term prob
        reader = new BufferedReader(new FileReader(new File("e://z_output_word//"+"part-r-00000")));
        while(reader.ready()){
            String line = reader.readLine();
            String[] args = line.split("\t");// 0���࣬1��������2����Ƶ
            double count = Double.valueOf(args[2]);
            String classname = args[0];
            String term = args[1];
            Map<String,String> map = new HashMap<String, String>();
            map.put(classname,term);
            class_term_prob.put(map, (count+1)/(class_term_total.get(classname)+class_term_num.get(classname)));
        }
	}
	
	public static double conditionalProbabilityForClass(String content,String classname){
		// ����һ���ĵ�����ĳ�����������
        double result = 0;
        String[] words = content.split("\n");
        for(String word:words){
            Map<String,String> map = new HashMap<String, String>();
            map.put(classname, word);
            result += Math.log(class_term_prob.getOrDefault(map,1.0/(class_term_total.get(classname)+class_term_num.get(classname))));
        }
        result += Math.abs(Math.log(class_prob.get(classname)));
        return result;
    }
}
