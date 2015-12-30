package nl.antonsteenvoorden.ikpmd;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import nl.antonsteenvoorden.ikpmd.model.Module;
import nl.antonsteenvoorden.ikpmd.service.ModuleService;

/**
 * App bootstrap class. Handy to use as singleton class.
 *
 * @author Daan Rosbergen
 */
public class App extends com.activeandroid.app.Application {
    private ModuleService moduleService;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        moduleService = new ModuleService(this);
    }

    public ModuleService getModuleService() {
        return moduleService;
    }
}
