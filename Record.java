package Application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Record {
  private File file;
  private FileWriter writer;
  private FileReader reader;
  private String FileName;
  private int RecordScore;
  public Record(String filename) throws IOException{
    this.FileName = filename;
    file = new File(this.FileName);
    file.createNewFile();
    writer = new FileWriter(file);
    reader = new FileReader(file);
    getRecord();
  }
  public void getRecord() throws IOException{
    BufferedReader br = new BufferedReader(reader);
    this.RecordScore = Integer.valueOf(br.readLine());
  }
  public int getRecordScore() {
    return RecordScore;
  }
  public void setRecord (int newRecord) throws IOException{
    writer.write(Integer.toString(newRecord));
    RecordScore = newRecord;
  }
}
