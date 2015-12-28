package nl.antonsteenvoorden.ikpmd.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Anton on 28/12/2015.
 */
public class Module {

    @SerializedName("name")
    private String name;
    @SerializedName("ects")
    private int ects;
    @SerializedName("grade")
    private int grade;
    @SerializedName("period")
    private int period;

    public Module(String name, int ects, int grade, int period) {
        this.name = name;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
    }

    public Module() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
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
}
