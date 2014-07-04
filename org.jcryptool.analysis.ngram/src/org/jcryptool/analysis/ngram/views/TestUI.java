package org.jcryptool.analysis.ngram.views;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.jcryptool.analysis.ngram.tools.ConvencienceTools;
import org.jcryptool.core.operations.alphabets.AbstractAlphabet;
import org.jcryptool.crypto.ui.alphabets.AlphabetSelectorComposite;
import org.jcryptool.crypto.ui.alphabets.AlphabetSelectorComposite.*;
import org.jcryptool.crypto.ui.textloader.ui.wizard.TextLoadController;

public class TestUI extends Composite {
	private Text text;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TestUI(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText(ConvencienceTools.getCurrentEditorText());
			}
		});
		btnNewButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnNewButton.setText("Click me");
		
		text = new Text(this, SWT.MULTI);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		AlphabetSelectorComposite AlphaSelector = new AlphabetSelectorComposite(parent, null, null); 
		final TextLoadController tlc = new TextLoadController(parent, parent, style, getEnabled(), getEnabled());
				
		tlc.addObserver(new Observer() {

			@Override
			public void update(Observable arg0, Object arg1) {
				// TODO Auto-generated method stub
				//String tesStr = (String) arg1;
				//System.out.println(tesStr);
				//im Observer einfach tlc.getText()(*text-mit-Ursprung-Objekt*).getText()(*String*)
				String testStr = tlc.getText().getText();
				System.out.println(testStr);
			}
			});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
