import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class StopButton extends JButton {

	private MainPanel _m;

	public StopButton(MainPanel m) {
		super("Stop");
		_m = m;
		addActionListener(new StopButtonListener());
	}

	class StopButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			_m.stop();
		}
	}

}
