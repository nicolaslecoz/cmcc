package eu.nicolaslecoz.cmcc.ui;

import com.vaadin.ui.Window;

/**
 * 
 * @author Nicolas LE COZ
 * @since 6th september 2010
 */
public class Application extends com.vaadin.Application {

	private static final long serialVersionUID = 1L;

	public void init() {
	    Window main = new Window("The Main Window");
	    setMainWindow(main);
	}

}
