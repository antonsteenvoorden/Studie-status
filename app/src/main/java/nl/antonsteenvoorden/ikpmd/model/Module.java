package nl.antonsteenvoorden.ikpmd.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Anton & Daan on 28/12/2015.
 */
@Table(name = "module")
public class Module extends Model {

  @Expose
  @Column(name = "name")
  @SerializedName("name")
  private String name;

  @Expose
  @Column(name = "longname")
  @SerializedName("longname")
  private String longName;

  @Expose
  @SerializedName("ects")
  @Column(name = "ects")
  private int ects;

  @Expose
  @SerializedName("grade")
  @Column(name = "grade")
  private double grade;

  @Expose
  @SerializedName("period")
  @Column(name = "period")
  private int period;

  @Expose
  @SerializedName("jaar")
  @Column(name = "jaar")
  private int jaar;

  @Expose
  @SerializedName("toetsdatum")
  @Column(name = "toetsdatum")
  private Date toetsDatum;

  @Expose
  @SerializedName("mutatiedatum")
  @Column(name = "mutatiedatum")
  private Date mutatieDatum;

  @Expose
  @SerializedName("handmatig")
  @Column(name = "handmatig")
  private int handmatig;

  @Expose
  @SerializedName("definitief")
  @Column(name = "definitief")
  private int definitief;

  @Expose
  @SerializedName("toetstype")
  @Column(name = "toetstype")
  private String toetsType;

  public Module() {

  }

  public Module(String name, String longName, int ects, double grade, int period, int jaar, Date toetsDatum, Date mutatieDatum, int handmatig, int definitief, String toetsType) {
    this.name = name;
    this.longName = longName;
    this.ects = ects;
    this.grade = grade;
    this.period = period;
    this.jaar = jaar;
    this.toetsDatum = toetsDatum;
    this.mutatieDatum = mutatieDatum;
    this.handmatig = handmatig;
    this.definitief = definitief;
    this.toetsType = toetsType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public int getEcts() {
    return ects;
  }

  public void setEcts(int ects) {
    this.ects = ects;
  }

  public double getGrade() {
    return grade;
  }

  public void setGrade(double grade) {
    this.grade = grade;
  }

  public int getPeriod() {
    return period;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

  public int getJaar() {
    return jaar;
  }

  public void setJaar(int jaar) {
    this.jaar = jaar;
  }

  public Date getToetsDatum() {
    return toetsDatum;
  }

  public void setToetsDatum(Date toetsDatum) {
    this.toetsDatum = toetsDatum;
  }

  public Date getMutatieDatum() {
    return mutatieDatum;
  }

  public void setMutatieDatum(Date mutatieDatum) {
    this.mutatieDatum = mutatieDatum;
  }

  public int getHandmatig() {
    return handmatig;
  }

  public void setHandmatig(int handmatig) {
    this.handmatig = handmatig;
  }

  public int getDefinitief() {
    return definitief;
  }

  public void setDefinitief(int definitief) {
    this.definitief = definitief;
  }

  public String getToetsType() {
    return toetsType;
  }

  public void setToetsType(String toetsType) {
    this.toetsType = toetsType;
  }

  public static void insertList(List<Module> modules) {
    ActiveAndroid.beginTransaction();
    try {

      for (Module newModule : modules) {
        boolean notStored = true;
        for (Module storedModule : getAll()) {
          if (newModule.toetsDatum.equals(storedModule.toetsDatum) && newModule.name.equals(storedModule.name)) {
            System.out.println("Module.insertList : " + newModule.toString() + " EQUALS " + storedModule.toString());
            notStored = false;
            storedModule.setMutatieDatum(newModule.mutatieDatum);
            storedModule.setGrade(newModule.grade);
            storedModule.setDefinitief(newModule.definitief);
            storedModule.setToetsType(newModule.toetsType);
            storedModule.setLongName(newModule.longName);
            storedModule.setName(newModule.name);
            //neem alle eigenschappen over
            storedModule.save();
          }
        }
        if(notStored) {
          System.out.println("Module.insertList SAVING :" + newModule.toString());
          newModule.save();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      ActiveAndroid.setTransactionSuccessful();
    }

    ActiveAndroid.endTransaction();
  }

  public void insert(Module module) {
    module.save();
  }

  @Override
  public String toString() {
    return "Module{" +
        "name='" + name + '\'' +
        ", ects=" + ects +
        ", grade='" + grade + '\'' +
        ", period=" + period +
        '}';
  }

  public static List<Module> getAll() {
    return new Select()
        .from(Module.class)
        .orderBy("period ASC")
        .execute();
  }

  public static List<Module> getPeriod(int period) {
    return new Select()
        .from(Module.class)
        .where("period = ?", period)
        .execute();
  }


  public static Module find(long id) {
    return Module.load(Module.class, id);

  }

  public void update() {
    new Update(Module.class)
        .set("mutatiedatum = ?", getMutatieDatum())
        .where("id = ?", getId())
        .execute();
    new Update(Module.class)
        .set("grade = ?", getGrade())
        .where("id = ?", getId())
        .execute();
    new Update(Module.class)
        .set("grade = ?", getGrade())
        .where("id = ?", getId())
        .execute();

  }
}
