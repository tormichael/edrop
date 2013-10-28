package edrop;


import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;

public class pnlEditor extends JTextPane
{
	private edropDocument	_doc;
	private Thread 			_thr;
	private KeyWordPopup	_kwp;
	
	public edropDocument get_doc() 
	{
		return _doc;
	}
	
	public pnlEditor()
	{
		StyleContext sc = new StyleContext();		
		_doc = new edropDocument(sc);
		this.setDocument(_doc);
		
		_kwp = new KeyWordPopup((DefaultStyledDocument)this.getDocument());
		
		ArrayList<KeyWord> kw = new ArrayList<KeyWord>();
		kw.add(new KeyWord("SELECT"));
		kw.add(new KeyWord("FROM"));
		kw.add(new KeyWord("WHERE"));
		kw.add(new KeyWord("INNER"));
		kw.add(new KeyWord("LEFT"));
		kw.add(new KeyWord("JOIN"));
		kw.add(new KeyWord("ORDER BY"));
		
		_kwp.setKeyWordArray(kw);
		
		this.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) 
			{
				_repeatKWP();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) 
			{
				_repeatKWP();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) 
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
	public void runKeyWordPopUp()
	{
		_thr = new Thread(_kwp);
		_thr.start();
	}
	
	private void _repeatKWP()
	{
		_kwp.setIsRepeat(true);
		JViewport vp = null;
		try{ vp = (JViewport)this.getParent();}
		catch (Exception ex) {}
		int start = 0;
		int end = this.getDocument().getLength();
		if (vp != null)
		{
			start = viewToModel(vp.getViewPosition());
			end = end-start > 1000 ? 1000 : end-start;
		}
		_kwp.setStart(start);
		_kwp.setLength(end);
		_kwp.setIsRepeat(false);
	}
}
