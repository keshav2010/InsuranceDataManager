package DisplayForms;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class WarningDialog extends JDialog{
	JLabel lbl_warning;
	public WarningDialog(String warningText){
		lbl_warning = new JLabel(warningText);
		this.setMinimumSize(new Dimension(lbl_warning.getText().length()*8, 100));
		this.add(lbl_warning);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setTitle("Warning");
		this.setVisible(true);
	}
}
