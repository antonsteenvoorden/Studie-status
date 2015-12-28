package nl.antonsteenvoorden.ikpmd;

import android.app.Application;

import nl.antonsteenvoorden.ikpmd.model.Module;
import nl.antonsteenvoorden.ikpmd.service.ModuleService;

/**
 * App bootstrap class. Handy to use as singleton class.
 *
 * @author Daan Rosbergen
 */
public class App extends Application {
    private ModuleService moduleService;

    @Override
    public void onCreate() {
        super.onCreate();
        moduleService = new ModuleService(this);
    }

    public ModuleService getModuleService() {
        return moduleService;
    }
}
