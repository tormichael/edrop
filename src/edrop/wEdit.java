package edrop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import JCommonTools.CC;

public class wEdit extends JFrame
{
	public final static String FN_RESOURCE_TEXT = "edrop.Rsc/edropText";
	public final static String FN_RESOURCE_TEXT_CT = "JCommonTools.Rsc/JCTText";
	
	private JMenu _mnuFile;
	private JMenuItem _mnuFileNew;
	private JMenuItem _mnuFileOpen;
	private JMenuItem _mnuFileSave;
	private JMenuItem _mnuFilePrint;
	private JMenuItem _mnuFileProperties;
	private JMenuItem _mnuFileExit;
	private JMenu _mnuEdit;
	private JMenuItem _mnuEditCopy;
	private JMenuItem _mnuEditCut;
	private JMenuItem _mnuEditPaste;
	private JMenu _mnuHelp;
	private JMenuItem _mnuHelpAbout;
	
	private JLabel _sbiMain;
	private JScrollPane _scp; 
	private pnlEditor _edit;
	private Hashtable<Object, Action> _actions;

	private ResourceBundle _bnd;
	private ResourceBundle _bnd_ct;
	
	public wEdit() throws HeadlessException 
	{

		//_currSesFileName = CC.STR_EMPTY;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension szScreen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(szScreen.width/2, szScreen.height/2);
		setLocation((int)(szScreen.width/2*Math.random()), (int)(szScreen.height/3*Math.random()));

		
		_edit = new pnlEditor();
		_actions = new Hashtable<Object, Action>();
		Action [] actArr = _edit.getActions();
		for (int ii=0; ii < actArr.length; ii++)
			_actions.put(actArr[ii].getValue(Action.NAME), actArr[ii]);
		
		/**
		 * M E N U
		 */
		
		JMenuBar mnuBar = new JMenuBar();
		setJMenuBar(mnuBar);
		
		_mnuFile = new JMenu();
		mnuBar.add(_mnuFile);
		_mnuFileNew = new JMenuItem();
		_mnuFile.add(_mnuFileNew);
		_mnuFileOpen = new JMenuItem(actOpenFile);
		_mnuFile.add(_mnuFileOpen);
		_mnuFileSave = new JMenuItem();
		_mnuFile.add(_mnuFileSave);
		_mnuFile.addSeparator();
		_mnuFilePrint = new JMenuItem();
		_mnuFile.add(_mnuFilePrint);
		_mnuFile.addSeparator();
		_mnuFileProperties = new JMenuItem(actPoperties);
		_mnuFile.add(_mnuFileProperties);
		_mnuFile.addSeparator();
		_mnuFileExit = new JMenuItem();
		_mnuFile.add(_mnuFileExit);

		_mnuEdit = new JMenu();
		mnuBar.add(_mnuEdit);
		_mnuEditCopy = new JMenuItem();
		_mnuEdit.add(_mnuEditCopy);
		_mnuEditCut = new JMenuItem();
		_mnuEdit.add(_mnuEditCut);
		_mnuEditPaste = new JMenuItem();
		_mnuEdit.add(_mnuEditPaste);

		_mnuHelp = new JMenu();
		mnuBar.add(_mnuHelp);
		_mnuHelpAbout = new JMenuItem();
		_mnuHelp.add(_mnuHelpAbout);
		
		/**
		 *   T O O L S   B A R
		 */
		JToolBar bar = new JToolBar();
		add(bar, BorderLayout.NORTH);

		

		/**
		 * C O N T E N T S
		 */
		
		_scp = new JScrollPane(_edit); 
		add(_scp, BorderLayout.CENTER);
		
		/**
		 * 
		 * S T A T U S   B A R
		 */
		
		JPanel statusBar = new JPanel();
		statusBar.setBorder(BorderFactory.createRaisedBevelBorder());
		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(statusBar, BorderLayout.SOUTH);
		
		_sbiMain = new JLabel();
		_sbiMain.setBorder(BorderFactory.createLoweredBevelBorder());
		statusBar.add(_sbiMain);
		
		setVisible(true);

//		final DefaultStyledDocument doc = new DefaultStyledDocument();
//		_actions = new Hashtable<Object, Action>();
//		Action [] actArr = _edit.getActions();
//		int offs = 0;
//		try
//		{
//			for (int ii=0; ii < actArr.length; ii++)
//			{
//				_actions.put(actArr[ii].getValue(Action.NAME), actArr[ii]);
//				String s = (String)actArr[ii].getValue(Action.NAME)+CC.NEW_LINE;
//				doc.insertString(offs, s, null);
//				offs += s.length();
//			}
//		}
//		catch (BadLocationException ex)
//		{
//			_sbiMain.setText(ex.getMessage());
//		}
//		_edit.setDocument(doc);

		
		_edit.runKeyWordPopUp();
		
		DisplayLocalTitle();
	}

	private void DisplayLocalTitle()
	{
		_bnd = ResourceBundle.getBundle(wEdit.FN_RESOURCE_TEXT, Locale.getDefault());
		_bnd_ct = ResourceBundle.getBundle(wEdit.FN_RESOURCE_TEXT_CT, Locale.getDefault());

		_mnuFile.setText(_bnd_ct.getString("Menu.File"));
		_mnuFileNew.setText(_bnd_ct.getString("Menu.File.New"));
		_mnuFileOpen.setText(_bnd_ct.getString("Menu.File.Open"));
		_mnuFileSave.setText(_bnd_ct.getString("Menu.File.Save"));
		_mnuFilePrint.setText(_bnd_ct.getString("Menu.File.Print"));
		_mnuFileProperties.setText(_bnd_ct.getString("Menu.File.Properties"));
		_mnuFileExit.setText(_bnd_ct.getString("Menu.General.Exit"));
		_mnuEdit.setText(_bnd_ct.getString("Menu.Edit"));
		_mnuEditCopy.setText(_bnd_ct.getString("Menu.Edit.Copy"));
		_mnuEditCut.setText(_bnd_ct.getString("Menu.Edit.Cut"));
		_mnuEditPaste.setText(_bnd_ct.getString("Menu.Edit.Paste"));
		_mnuHelp.setText(_bnd_ct.getString("Menu.Help"));
		_mnuHelpAbout.setText(_bnd_ct.getString("Menu.Help.About"));

		_sbiMain.setText(_bnd.getString("StatusBar.Main.Welcome"));
		
	}
	
	
	public Action getActionName(String aName)
	{
		return _actions.get(aName);
	}
	
	Action actOpenFile = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			JFileChooser dlg = new JFileChooser();
			dlg.setCurrentDirectory(new File("C:\\ASW\\SQ4\\"));
			//dlg.setFileFilter(new FileNameExtensionFilter("Session", "sq3", "sq4"));
			if (dlg.showOpenDialog(wEdit.this) == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					FileInputStream fReader = new FileInputStream(dlg.getSelectedFile());
					BufferedInputStream in = new BufferedInputStream(fReader);
					byte[] bb = new byte[1024];
					int off = 0;
					Charset cset = Charset.forName("UTF-8");
					String text = CC.STR_EMPTY;
					while (in.read(bb, off, bb.length) == bb.length)
					{
						text += cset.decode(ByteBuffer.wrap(bb)).toString();
					}

					_edit.getDocument().insertString(0, text, null);

					_edit.runKeyWordPopUp();
					
					_edit.addCaretListener(new CaretListener() {
						
						@Override
						public void caretUpdate(CaretEvent e) 
						{
							
							
							_sbiMain.setText(
									"CP:"+_edit.getCaretPosition()
									+ " TVP:"+_edit.viewToModel(_scp.getViewport().getViewPosition())
									+ " BVP:"+_edit.viewToModel(new Point(
											_scp.getViewport().getViewPosition().x + _edit.getWidth()
											,_scp.getViewport().getViewPosition().y + _edit.getHeight()
											))
							);
						}
					});
					
					in.close();
					fReader.close();
				}
				catch (FileNotFoundException ex)
				{
					
				}
				catch (IOException ex)
				{
					
				}
				catch (BadLocationException ex)
				{
					
				}

			}
		}
	};

	Action actPoperties = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			dlgProperties dlg = new dlgProperties(wEdit.this, _edit.get_doc().getStyle(StyleContext.DEFAULT_STYLE));
			
			dlg.setVisible(true);
		}
	};
}
