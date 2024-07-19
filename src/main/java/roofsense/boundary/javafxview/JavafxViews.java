package roofsense.boundary.javafxview;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JavafxViews {

    private static final RootJavafxView ROOT_VIEW = (RootJavafxView) loadView("views/Root.fxml");
    private static final DevicesOverviewJavafxView DEVICES_OVERVIEW_VIEW =
            (DevicesOverviewJavafxView) loadView("views/DevicesOverview.fxml");
    private static final Map<String, DashboardJavafxView> DASHBOARD_VIEWS = new HashMap<>();

    private JavafxViews() {
    }

    public static RootJavafxView getRootView() {
        return ROOT_VIEW;
    }

    public static DevicesOverviewJavafxView getDevicesOverview() {
        return DEVICES_OVERVIEW_VIEW;
    }

    public static DashboardJavafxView getDashboard(final String dashboardId) {
        return DASHBOARD_VIEWS.computeIfAbsent(
                dashboardId, id -> (DashboardJavafxView) loadView("views/Dashboard" + ".fxml"));
    }

    private static JavafxView loadView(final String fxmlPath) {
        try {
            final var view = new FXMLLoader(ClassLoader.getSystemResource(fxmlPath));
            view.load();
            return view.getController();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
