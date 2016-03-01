package nl.antonsteenvoorden.ikpmd;

import com.activeandroid.ActiveAndroid;

import nl.antonsteenvoorden.ikpmd.service.ModuleObtainer;

/**
 * App bootstrap class. Handy to use as singleton class.
 *
 * @author Daan Rosbergen
 */
public class App extends com.activeandroid.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    public ModuleObtainer getModuleObtainer(String username, String password) {
        return new ModuleObtainer(username, password);
    }
}
