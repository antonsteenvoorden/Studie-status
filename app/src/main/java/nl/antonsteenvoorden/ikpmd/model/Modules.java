package nl.antonsteenvoorden.ikpmd.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Anton & Daanon 28/12/2015.
 */
public class Modules {
    @SerializedName("modules")
    public List<Modules> modules;
}
