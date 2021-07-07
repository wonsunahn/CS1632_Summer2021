import java.awt.*;
import javax.swing.*;

public class ButtonPanel extends JPanel {

	private RunButton _run;

	private RunContinuousButton _runContinuous;

	private StopButton _stop;

	private WriteButton _write;

	private UndoButton _undo;

	private LoadButton _load;

	private ClearButton _clear;

	private SafeSaveButton _safeSave;

	/**
	 * Constructor - add all of the buttons to the ButtonPanel.
	 */

	public ButtonPanel(MainPanel m) {

		// Send a reference to the Main Panel
		// to all of the buttons.

		_run = new RunButton(m);
		_runContinuous = new RunContinuousButton(m);
		_stop = new StopButton(m);
		_write = new WriteButton(m);
		_undo = new UndoButton(m);
		_load = new LoadButton(m);
		_clear = new ClearButton(m);
		setLayout(new FlowLayout());

		// Add all of the buttons

		add(_run);
		add(_runContinuous);
		add(_stop);
		add(_write);
		add(_undo);
		add(_load);
		add(_clear);
	}

}
