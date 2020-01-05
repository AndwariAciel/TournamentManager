package de.andwari.tournamentfx.startup;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.util.AnnotationLiteral;

import javafx.application.Application;
import javafx.stage.Stage;

public class CdiApplication extends Application {

	/**
	 * This initializes the CDI container and throws an event, catched by the actual
	 * App {@link AppInitializer}. This makes CDI-injection available within the
	 * JavaFX classes. <br/>
	 * Discovery mode is set to all and only the project packages are scanned. See the beans.xml in the resource folder.<br/>
	 * To be able to inject the JavaFX FXMLLoader, a producer is neccessary, see {@link FXMLLoaderProducer}
	 */
	@SuppressWarnings("serial")
	@Override
	public void start(Stage primaryStage) {
		SeContainerInitializer initializer = SeContainerInitializer.newInstance();
		final SeContainer container = initializer.initialize();
		container.getBeanManager().fireEvent(primaryStage, new AnnotationLiteral<StartupScene>() {
		});
	}

	public static void launchAppWithCdi() {
		launch();
	}

}
