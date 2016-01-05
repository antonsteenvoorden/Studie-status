package nl.antonsteenvoorden.ikpmd.service;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nl.antonsteenvoorden.ikpmd.model.Module;
import nl.antonsteenvoorden.ikpmd.request.GsonRequest;

/**
 * Created by daanrosbergen on 30/11/15, edited by Anton Steenvoorden on 28/12/15
 */
public class ModuleService {
    private final Context context;
    private String url;
    private RequestQueue requestQueue;
    private ArrayList<Module> modules = new ArrayList<>();

    public ModuleService(Context context) {
        this.context = context;
        url = "http://www.fuujokan.nl/subject_lijst.json";
        requestQueue = Volley.newRequestQueue(this.context);
        requestQueue.start();
    }

    public ArrayList<Module> findAll(Response.Listener<List<Module>> listener, Response.ErrorListener errorListener) {
        Type type = new TypeToken<List<Module>>(){}.getType();
        GsonRequest<List<Module>> findAllMovies = new GsonRequest<>(url, type, null, listener,
                errorListener);
        requestQueue.add(findAllMovies);
        return modules;
    }

    public ArrayList<Module> findAll() {
        GsonRequest<List<Module>> findAllMovies = new GsonRequest<>(url, Module.class, null, succesListener(),
                errorListener());
        requestQueue.add(findAllMovies);
        return modules;
    }


    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }

    private Response.Listener<List<Module>> succesListener() {
        return new Response.Listener<List<Module>>() {
            @Override
            public void onResponse(List<Module> response) {
                for (Module module: response) {
                    System.out.println(module);
                }
            }
        };
    }
}
