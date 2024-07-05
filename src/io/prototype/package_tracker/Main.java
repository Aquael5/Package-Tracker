package io.prototype.package_tracker;

import io.prototype.package_tracker.app.*;
import io.prototype.package_tracker.widget.TrackLine;
import br.nullexcept.mux.app.Application;
import br.nullexcept.mux.app.Launch;
import br.nullexcept.mux.app.Project;
import br.nullexcept.mux.res.LayoutInflater;

public class Main {

    static {
        LayoutInflater.registerView("TrackLine", TrackLine::new);


        Launch.registerCreator(LaunchActivity.class, LaunchActivity::new);
        Launch.registerCreator(CreateActivity.class, CreateActivity::new);
        Launch.registerCreator(MainActivity.class, MainActivity::new);
        Launch.registerCreator(TrackActivity.class, TrackActivity::new);
        Launch.registerCreator(TrackService.class, TrackService::new);
    }

    public static void main(String[] args) {
        Application.initialize(Project.build(new Launch<>(LaunchActivity.class),Main.class.getPackage().getName()));
    }
}
