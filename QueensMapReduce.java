//package org.myorg;
import java.io.IOException;
import java.util.*;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class WordCount {

    public static boolean containsAnswer(int[] a, ArrayList<int[]> l){
	for(int i = 0; i < l.size(); i++){
	    if(Arrays.equals(a, l.get(i)))
		return true;
	}
		
	return false;
    }
	
    public static boolean isConsistent(int[] q, int n) {
	for (int i = 0; i < n; i++) {
	    if (q[i] == q[n])
		return false;
	    if ((q[i] - q[n]) == (n - i))
		return false;
	    if ((q[n] - q[i]) == (n - i))
		return false;
	}
	return true;
    }

    public static void enumerate(int n, List<int[]> a2, int x, int y) {
	int[] a = new int[n];
	a[x] = y;
	enumerate(a, a2, 0, x, y);
    }

    public static void enumerate(int[] q, List<int[]> a, int k, int x, int y) {
	int n = q.length;
	if (k == n) {
	    oibarnei++;
	    a.add(q.clone());
	} else {
	    if (k != x) {
		for (int i = 0; i < n; i++) {
		    q[k] = i;
		    if (isConsistent(q, k))
			enumerate(q, a, k + 1, x, y);
		}
	    } else {
		if (isConsistent(q, k))
		    enumerate(q, a, k + 1, x, y);
	    }
	}
    }


    public static void printList(List<int[]> l) {
	for (int i = 0; i < l.size(); i++) {
	    printQueens(l.get(i));
	}
    }
    
    public static String printQueens(int[] q) {
	int n = q.length;
	String board = new String();
	
	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < n; j++) {
		if (q[i] == j)
		    board.append("Q ");
		else
		    board.append("* ");
	    }
	    board.append("\n");
	}
	board.append("\n");
    }

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, int[], ArrayList<int[]>> {
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();

	public void map(LongWritable key, Text value, OutputCollector<int[], ArrayList<int[]>> output, Reporter reporter) throws IOException {
	    String line = value.toString();
	    StringTokenizer s = new StringTokenizer(line); 
	    int n = Integer.parseInt(s.nextToken());
	    int x = Integer.parseInt(s.nextToken());
	    int y = Integer.parseInt(s.nextToken());
	    List<int[]> answers = new ArrayList<int[]>();

	    int[] key = {x, y};
	    enumerate(n, answers, x, y);

	    output.collect(key, answers);
	}
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, int[], ArrayList<int[]>> {
	public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<int[], ArrayList<int[]>> output, Reporter reporter) throws IOException {
	    int sum = 0;
	    while (values.hasNext()) {
		sum += values.next().get();
	    }
	    output.collect(key, new IntWritable(sum));
	}
    }

    public static void main(String[] args) throws Exception {
	JobConf conf = new JobConf(WordCount.class);
	conf.setJobName("wordcount");

	conf.setOutputKeyClass(Text.class);
	conf.setOutputValueClass(IntWritable.class);

	conf.setMapperClass(Map.class);
	//conf.setCombinerClass(Reduce.class);
	conf.setReducerClass(Reduce.class);

	conf.setInputFormat(TextInputFormat.class);
	conf.setOutputFormat(TextOutputFormat.class);

	FileInputFormat.setInputPaths(conf, new Path(args[0]));
	FileOutputFormat.setOutputPath(conf, new Path(args[1]));

	JobClient.runJob(conf);
    }
}
//package org.myorg;
import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class WordCount {

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();

	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	    String line = value.toString();
	    StringTokenizer tokenizer = new StringTokenizer(line);
	    while (tokenizer.hasMoreTokens()) {
		word.set(tokenizer.nextToken());
		output.collect(word, one);
	    }
	}
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
	public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	    int sum = 0;
	    while (values.hasNext()) {
		sum += values.next().get();
	    }
	    output.collect(key, new IntWritable(sum));
	}
    }

    public static void main(String[] args) throws Exception {
	JobConf conf = new JobConf(WordCount.class);
	conf.setJobName("wordcount");

	conf.setOutputKeyClass(Text.class);
	conf.setOutputValueClass(IntWritable.class);

	conf.setMapperClass(Map.class);
	//conf.setCombinerClass(Reduce.class);
	conf.setReducerClass(Reduce.class);

	conf.setInputFormat(TextInputFormat.class);
	conf.setOutputFormat(TextOutputFormat.class);

	FileInputFormat.setInputPaths(conf, new Path(args[0]));
	FileOutputFormat.setOutputPath(conf, new Path(args[1]));

	JobClient.runJob(conf);
    }
}
//package org.myorg;
import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class QueensMapReduce {

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();

	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	    String line = value.toString();
	    StringTokenizer tokenizer = new StringTokenizer(line);
	    while (tokenizer.hasMoreTokens()) {
		word.set(tokenizer.nextToken());
		output.collect(word, one);
	    }
	}
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
	public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	    int sum = 0;
	    while (values.hasNext()) {
		sum += values.next().get();
	    }
	    output.collect(key, new IntWritable(sum));
	}
    }

    public static void main(String[] args) throws Exception {
	JobConf conf = new JobConf(WordCount.class);
	conf.setJobName("wordcount");

	conf.setOutputKeyClass(Text.class);
	conf.setOutputValueClass(IntWritable.class);

	conf.setMapperClass(Map.class);
	//conf.setCombinerClass(Reduce.class);
	conf.setReducerClass(Reduce.class);

	conf.setInputFormat(TextInputFormat.class);
	conf.setOutputFormat(TextOutputFormat.class);

	FileInputFormat.setInputPaths(conf, new Path(args[0]));
	FileOutputFormat.setOutputPath(conf, new Path(args[1]));

	JobClient.runJob(conf);
    }
}
