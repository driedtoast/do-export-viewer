package org.driedtoast.dodesktop;

import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Alert;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.Window;

public class DesktopMain implements Application {
	
	// @BXML
	private Window window = null;
	private Label label1;
	private PushButton button1;
	private TextInput text1;
	
	/**
	 * TODO create import splash
	 * TODO task list screen with tags / project names
	 * TODO sorting table based on date
	 * TODO nice design
	 */

	@Override
	public void startup(Display display,
			org.apache.pivot.collections.Map<String, String> properties)
			throws Exception {
		
//		String language = properties.get("locale");
//	    Locale locale = (language == null) ? Locale.getDefault() : new Locale(language);
//	    Resources resources = new Resources(this.getClass().getName(), locale);
//	 
	    // window = (StockTrackerWindow)bxmlSerializer.readObject(getClass().getResource("stock_tracker_window.bxml"),
	    //    resources);
		
		BXMLSerializer serializer = new BXMLSerializer();
		window = (Window) serializer.readObject(getClass().getResource("main-window.xml"));
		button1 = (PushButton)serializer.getNamespace().get("button1");
		text1 = (TextInput)serializer.getNamespace().get("text1");
		button1.getButtonPressListeners().add(new ButtonPressListener() {
			
			@Override
			public void buttonPressed(Button button) {
				Alert.alert(MessageType.INFO, "Hello " + text1.getText(),
						window);
			}
		});
		window.open(display);
		
		
	}

	@Override
	public boolean shutdown(boolean optional) {
		if (window != null) {
			window.close();
		}
		return false;
	}

	
	@Override
	public void suspend() {
	}

	@Override
	public void resume() {
	}

	public static void main(String[] args) {
		System.out.println("Starting the app");
		DesktopApplicationContext.main(DesktopMain.class, args);
	}


}
