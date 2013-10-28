package edrop;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import JCommonTools.GBC;

public class dlgProperties extends JDialog
{
	private JPanel[] _pnls;
	private Style _defStyle;

	public dlgProperties(JFrame aParent, Style aSytle)
	{
		super (aParent, true);
		
		_defStyle = aSytle;
		
		ResourceBundle bnd_ct = ResourceBundle.getBundle(wEdit.FN_RESOURCE_TEXT_CT, Locale.getDefault());
		ResourceBundle bnd = ResourceBundle.getBundle(wEdit.FN_RESOURCE_TEXT, Locale.getDefault());
		
		Container parentWin = this.getParent();
		if (parentWin != null)
		{
			setSize(parentWin.getSize().width*2/3, parentWin.getSize().height*2/3);
			setLocation(parentWin.getX()+100, parentWin.getY()+100);
		}
		
		
		JScrollPane pnlParamGroup = new JScrollPane();
		JPanel pnlParams = new JPanel(new BorderLayout());
		JSplitPane spp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlParamGroup, pnlParams);
		this.add(spp, BorderLayout.CENTER);

		JPanel pnlButtons = new JPanel();
		this.add(pnlButtons, BorderLayout.SOUTH);
		pnlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton cmdOk = new JButton(actOk);
		cmdOk.setText(bnd_ct.getString("Button.Ok"));
		pnlButtons.add(cmdOk);
		JButton cmdCalcel = new JButton(bnd_ct.getString("Button.Cancel"));
		pnlButtons.add(cmdCalcel);
		
		_pnls = new JPanel[3];
		
		//--------------------------------------------------------------------------
		
		JPanel pnl = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		pnl.setLayout(gbl);
		JLabel lbl = new JLabel(bnd.getString("Label.Properties.FontFamily"));
		JComboBox cboFontFamily = new JComboBox();
		pnl.add(lbl, new GBC(0, 0).setAnchor(GBC.EAST));
		pnl.add(cboFontFamily, new GBC(1, 0).setAnchor(GBC.WEST).setInsets(5, 10, 5, 5));
		lbl = new JLabel(bnd.getString("Label.Properties.FontSize"));
		JComboBox cboFontSize = new JComboBox(new String[]{"8","10","12","15","18","24","36","48","72"});
		pnl.add(lbl, new GBC(0, 1).setAnchor(GBC.EAST));
		pnl.add(cboFontSize, new GBC(1, 1).setAnchor(GBC.WEST).setInsets(5, 10, 5, 5));
		lbl = new JLabel(bnd.getString("Label.Properties.TabSet"));
		JTextField txtTab = new JTextField(5);
		pnl.add(lbl, new GBC(0, 2).setAnchor(GBC.EAST));
		pnl.add(txtTab, new GBC(1, 2).setAnchor(GBC.WEST).setInsets(5, 10, 5, 5));
		_pnls[0]= pnl;
		
		//--------------------------------------------------------------------------
		
		pnlParams.add(pnl, BorderLayout.CENTER);
	}
	
	Action actOk = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			StyleConstants.setFontFamily(_defStyle, "SansSerif");
			setVisible(false);
		}
	};
	
}
