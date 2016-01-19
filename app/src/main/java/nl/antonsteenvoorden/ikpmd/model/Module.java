package nl.antonsteenvoorden.ikpmd.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

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
    @SerializedName("gradeset")
    @Column(name = "gradeset")
    private int gradeSet;

    public Module(String name, int ects, double grade, int period, int gradeSet) {
        super();
        this.gradeSet = gradeSet;
        this.name = name;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
    }

    public int isGradeSet() {
        return gradeSet;
    }

    public void setGradeSet(int gradeSet) {
        this.gradeSet = gradeSet;
    }

    public Module() {
        super();
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

    @Override
    public String toString() {
        return "Module{" +
                "name='" + name + '\'' +
                ", ects=" + ects +
                ", grade='" + grade + '\'' +
                ", period=" + period +
                ", gradeSet=" +gradeSet +
                '}';
    }

    public static List<Module> getAll() {
        return new Select()
                .from(Module.class)
                .orderBy("period ASC")
                .execute();
    }


    public static Module find(long id) {
        return Module.load(Module.class, id);

    }

    public void update() {
        new Update(Module.class)
                .set("grade = ?", getGrade())
                .where("id = ?",getId())
                .execute();
        new Update(Module.class)
                .set("gradeSet = ?", isGradeSet())
                .where("id = ?",getId())
                .execute();
    }
}
