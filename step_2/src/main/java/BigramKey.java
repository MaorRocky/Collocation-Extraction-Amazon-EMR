import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BigramKey implements WritableComparable<BigramKey> {

	private Text word1;
    private Text word2;
    private Text foreignKey;
    private IntWritable decade;

    public BigramKey(Text word1, Text word2, Text foreignKey, IntWritable decade) {
        this.word1 = word1;
        this.word2 = word2;
        this.foreignKey = foreignKey;
        this.decade = decade;
    }

    public BigramKey() {
        this (new Text("*"),new Text("*"), new Text("-1"), new IntWritable(-1));
    }

	public void readFields(DataInput in) throws IOException {
		word1.readFields(in);
		word2.readFields(in);
		foreignKey.readFields(in);
        decade.readFields(in);
	}

	public void write(DataOutput out) throws IOException {
		word1.write(out);
		word2.write(out);
        foreignKey.write(out);
        decade.write(out);
	}

	public int compareTo(BigramKey other) {
		int ret = this.decade.get() - other.decade.get();
		if (ret == 0)
            if (this.foreignKey.toString().equals("*") && !other.foreignKey.toString().equals("*"))
                ret = -1;
            else if (other.foreignKey.toString().equals("*") && !this.foreignKey.toString().equals("*"))
                ret = 1;
            else
                ret = this.foreignKey.toString().compareTo(other.foreignKey.toString());
		if (ret == 0)
            if (this.word1.toString().equals("*") && !other.word1.toString().equals("*") )
                ret = -1;
            else if (other.word1.toString().equals("*") && !this.word1.toString().equals("*") )
                ret = 1;
            else
                ret = this.word1.toString().compareTo(other.word1.toString());
		if (ret == 0)
            if (this.word2.toString().equals("*") && !other.word2.toString().equals("*"))
                ret = -1;
            else if (other.word2.toString().equals("*") && !this.word2.toString().equals("*"))
                ret = 1;
            else
                ret = this.word2.toString().compareTo(other.word2.toString());
		return ret;
	}

    public Text getWord1() {
        return this.word1;
    }

    public Text getWord2() {
        return this.word2;
    }

    public Text getForeignKey() {
		return foreignKey;
	}

	public IntWritable getDecade() {
		return this.decade;
	}

    @Override
    public String toString() {
        return "BigramKey{" +
                "word1=" + word1 +
                ", word2=" + word2 +
                ", foreignKey=" + foreignKey +
                ", decade=" + decade +
                '}';
    }
}
