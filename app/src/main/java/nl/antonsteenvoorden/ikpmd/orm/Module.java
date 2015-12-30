package nl.antonsteenvoorden.ikpmd.orm;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by daanrosbergen on 30/12/15.
 */
@Table(name = "module")
public class Module extends Model {

    @Column(name = "name") private String name;
    @Column(name = "ects") private int ects;
    @Column(name = "grade") private int grade;
    @Column(name = "period") private int period;

    public Module(String name, int ects, int grade, int period) {
        super();
        this.name = name;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
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

    public static List<Module> getAll() {
        return new Select()
                .from(Module.class)
                .orderBy("period ASC")
                .execute();
    }
}
