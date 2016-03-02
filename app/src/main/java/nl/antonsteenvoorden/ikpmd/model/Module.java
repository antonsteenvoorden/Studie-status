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
  private Integer ects;

  @Expose
  @SerializedName("grade")
  @Column(name = "grade")
  private Double grade;

  @Expose
  @SerializedName("period")
  @Column(name = "period")
  private Integer period;

  @Expose
  @SerializedName("jaar")
  @Column(name = "jaar")
  private Integer jaar;

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
  private Integer handmatig;

  @Expose
  @SerializedName("definitief")
  @Column(name = "definitief")
  private Integer definitief;

  @Expose
  @SerializedName("toetstype")
  @Column(name = "toetstype")
  private String toetsType;

  public Module() {

  }

  public Module(String name, String longName, Integer ects, Double grade, Integer period, Integer jaar, Date toetsDatum, Date mutatieDatum, Integer handmatig, Integer definitief, String toetsType) {
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

  public Integer getEcts() {
    return ects;
  }

  public void setEcts(Integer ects) {
    this.ects = ects;
  }

  public Double getGrade() {
    return grade;
  }

  public void setGrade(Double grade) {
    this.grade = grade;
  }

  public Integer getPeriod() {
    return period;
  }

  public void setPeriod(Integer period) {
    this.period = period;
  }

  public Integer getJaar() {
    return jaar;
  }

  public void setJaar(Integer jaar) {
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

  public Integer getHandmatig() {
    return handmatig;
  }

  public void setHandmatig(Integer handmatig) {
    this.handmatig = handmatig;
  }

  public Integer getDefinitief() {
    return definitief;
  }

  public void setDefinitief(Integer definitief) {
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
          newModule.setPeriod(0);
          newModule.setEcts(0);
          newModule.setJaar(0);
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
