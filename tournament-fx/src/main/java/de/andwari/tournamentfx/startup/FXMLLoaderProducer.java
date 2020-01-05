package de.andwari.tournamentfx.startup;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import javafx.fxml.FXMLLoader;

public class FXMLLoaderProducer {

	@Inject
	Instance<Object> instance;

	/**
	 * Produces the {@link FXMLLoader} to make it available for injection.
	 * 
	 * @return
	 */
	@Produces
	public FXMLLoader createLoader() {
		FXMLLoader loader = new FXMLLoader();
		loader.setControllerFactory(param -> instance.select(param).get());
		return loader;
	}
}
