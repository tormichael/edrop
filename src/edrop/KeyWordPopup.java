package edrop;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class KeyWordPopup implements Runnable 
{
	private DefaultStyledDocument _doc;
	private ArrayList<KeyWord> _arrKW;
	private int _start;
	private int _len;
	private final Style _kwStyle;
	private Style _defaultStyle;
	private boolean _isRepeat;
	
	public void setIsRepeat(boolean isRepeat) 
	{
		_isRepeat = isRepeat;
	}
	
	public void setKeyWordArray(ArrayList<KeyWord> arrKW) 
	{
		_arrKW = arrKW;
	}
	
	public void setStart(int start) 
	{
		_start = start;
	}
	
	public void setLength(int len) {
		_len = len;
	}
	
	public KeyWordPopup(DefaultStyledDocument aDoc)
	{
		this (aDoc, null);
	}
	public KeyWordPopup(DefaultStyledDocument aDoc, ArrayList<KeyWord> arrKW)
	{
		_doc = aDoc;
		_arrKW = arrKW;

		_defaultStyle = _doc.getStyle(StyleContext.DEFAULT_STYLE);
		_kwStyle = _doc.addStyle("KWStyle", _defaultStyle);
		StyleConstants.setForeground(_kwStyle, Color.BLUE);
		//StyleConstants.setBold(_kwStyle, true);		
	}
	
	@Override
	public void run() 
	{
		String src = null;
		while (true)
		{
			try
			{
				src = _doc.getText(_start, _len).toUpperCase(); 
			}
			catch (BadLocationException ex)
			{
				return;
			}
			
			int offset;
			
			_doc.setCharacterAttributes(_start, _len, _defaultStyle, true);
			
			for (KeyWord kw : _arrKW)
			{
				if (Thread.currentThread().isInterrupted() || _isRepeat)
					break;
				
				offset = 0;
				while ((offset = src.indexOf(kw.wd, offset)) != -1 && !Thread.currentThread().isInterrupted() && !_isRepeat)
				{
					_doc.setCharacterAttributes(_start+offset, kw.wd.length(), _kwStyle, true);
					offset += kw.wd.length();
				}
			}
			_isRepeat = true; 
			while(_isRepeat);
		}
	}
}
