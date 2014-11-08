package org.jcryptool.analysis.ngram.views;

import java.io.*;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.custom.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.*;
import org.eclipse.wb.swt.*;
import org.jcryptool.analysis.ngram.tools.*;
import org.osgi.framework.Bundle;

public class NgramView extends ViewPart
{
	public static final String ID = "org.jcryptool.analysis.ngram.views.NgramView";

	private ScrolledComposite sc_Container;
	private Composite cp_Container;
	private Label lbl_Header;
	private Group grp_LoadText;
	private GridData gd_grp_LoadText;
	private Combo cb_LoadText;
	private Label lbl_InputMethod;
	private Combo cb_ChooseLang;
	private Label lbl_ChooseLang;
	private Combo cb_ChooseDist;
	private Label lbl_ChooseDist;
	private Group grp_CypherText;
	private GridData gd_grp_CypherText;
	private FillLayout fl_grp_CypherText;
    private Text txt_CypherText;
    private Group grp_LoadReferenceText;
    private GridData gd_grp_LoadReferenceText;
    private Group grp_AnalizeText;
    private GridData gd_grp_AnalizeText;
    private Button btn_AnalizeText;
    private Button btn_LoadReferenceText;
    private Group grp_ResultText;
    private GridData gd_grp_ResultText;
    private FillLayout fl_grp_ResultText;
    private Text txt_Reference;
    private Label lbl_Reference;
    private Button btn_ReferenceSubmit;
    private Label lbl_FileInfo;
    private Text txt_ResultText;
    private String referenceText;


    public NgramView()
    {
        referenceText = "";
    }

    public void setFocus()
    {
        txt_CypherText.setFocus();
    }


	public void createPartControl(Composite parent)
	{
		sc_Container = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		sc_Container.setExpandHorizontal(true);
		sc_Container.setExpandVertical(true);

		cp_Container = new Composite(sc_Container, SWT.NONE);
		cp_Container.setLayout(new GridLayout(1, false));

		lbl_Header = new Label(cp_Container, SWT.NONE);
		lbl_Header.setFont(SWTResourceManager.getFont("Lucida Grande", 14, SWT.BOLD));
		lbl_Header.setText("Analysis of N-Grams to Identify the Topic of a Text");
		lbl_Header.setBounds(10, 10, 600, 100);

		grp_LoadText = new Group(cp_Container, SWT.NONE);
		gd_grp_LoadText = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_grp_LoadText.widthHint = 462;
		gd_grp_LoadText.heightHint = 60;
		grp_LoadText.setLayoutData(gd_grp_LoadText);
		grp_LoadText.setText("Three input parameters");
		//the input method, the language of the text you want to analyze, and the analysis method

		lbl_InputMethod = new Label(grp_LoadText, SWT.NONE);
		lbl_InputMethod.setText("Input method:");
		lbl_InputMethod.setBounds(14, 20, 145, 14);
		
		cb_LoadText = new Combo(grp_LoadText, SWT.READ_ONLY);
		cb_LoadText.setItems(new String[] {"From manual input", "From file", "English samle text", "German sample text"});
		cb_LoadText.setBounds(10, 37, 145, 23);
		cb_LoadText.select(0);
		cb_LoadText.addModifyListener(new ModifyListener()
		{			
			public void modifyText(ModifyEvent e)
			{
				if (cb_LoadText.getSelectionIndex() == 0)
				{
					txt_CypherText.setText("");
					txt_CypherText.setEditable(true);
					txt_CypherText.setFocus();
				}

				if (cb_LoadText.getSelectionIndex() == 1)
				{
					try
					{
						Display display = Display.getDefault();
						Shell dialogShell = new Shell(display, SWT.APPLICATION_MODAL);
						FileDialog fd_ChooseFile = new FileDialog(dialogShell, SWT.OPEN);
						fd_ChooseFile.setFilterPath("\\");
						fd_ChooseFile.setFilterExtensions(new String[] {"*.txt"});
						File file_CypherText = new File(fd_ChooseFile.open());
						FileInputStream fis;
						fis = new FileInputStream(file_CypherText);
						byte[] content = new byte[fis.available()];
						fis.read(content);
						fis.close();
						txt_CypherText.setText(new String(content));
						txt_CypherText.setEditable(true);
					}
					catch (Exception ex)
					{
						txt_CypherText.setText("");
						txt_CypherText.setEditable(false);
				    }
				}

				if (cb_LoadText.getSelectionIndex() == 2)
				{			
					Bundle bundle = Platform.getBundle("org.jcryptool.analysis.ngram");
					java.net.URL fileURL = bundle.getEntry("asset/Sample_text_EN.txt");
					
					File file = null;
					FileInputStream fin = null;
					byte[] content = null; 	
					
					try 
					{
					    file = new File(FileLocator.resolve(fileURL).toURI());
					    fin = new FileInputStream(file);
						content = new byte[fin.available()];
						fin.read(content);
						fin.close();
					} 
					catch (Exception ex) 
					{					    					
					}							
					
					txt_CypherText.setText(new String(content));					
					txt_CypherText.setEditable(true);
					cb_ChooseLang.select(0);
				}

				if (cb_LoadText.getSelectionIndex() == 3)
				{
					Bundle bundle = Platform.getBundle("org.jcryptool.analysis.ngram");
					java.net.URL fileURL = bundle.getEntry("asset/Sample_text_DE.txt");
					
					File file = null;
					FileInputStream fin = null;
					byte[] content = null; 	
					
					try 
					{
					    file = new File(FileLocator.resolve(fileURL).toURI());
					    fin = new FileInputStream(file);
						content = new byte[fin.available()];
						fin.read(content);
						fin.close();
					} 
					catch (Exception ex) 
					{					    					
					}							
					
					txt_CypherText.setText(new String(content));					
					txt_CypherText.setEditable(true);
					cb_ChooseLang.select(1);                    
				}

				txt_ResultText.setText("");
			}
	    });
		
		lbl_ChooseLang = new Label(grp_LoadText, SWT.NONE);
		lbl_ChooseLang.setText("Language of the text:");
		lbl_ChooseLang.setBounds(239, 20, 145, 14);
		
        cb_ChooseLang = new Combo(grp_LoadText, SWT.READ_ONLY);     
        cb_ChooseLang.setItems(new String[] {"English", "German"});
        cb_ChooseLang.setBounds(235, 37, 145, 23);
        cb_ChooseLang.select(0); 
        cb_ChooseLang.addModifyListener(new ModifyListener()
        {
            public void modifyText(ModifyEvent e)
            {
                txt_ResultText.setText("");
            }
        });

		lbl_ChooseDist = new Label(grp_LoadText, SWT.NONE);
		lbl_ChooseDist.setText("Analysis method:");
		lbl_ChooseDist.setBounds(464, 20, 145, 14);
        
		cb_ChooseDist = new Combo(grp_LoadText, SWT.READ_ONLY);		
		cb_ChooseDist.setItems(new String[] {"Euclidean", "Least Squares"});
		cb_ChooseDist.setBounds(460, 37, 145, 23);
		cb_ChooseDist.select(0);
		cb_ChooseDist.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				txt_ResultText.setText("");
			}
		});

		grp_CypherText = new Group(cp_Container, SWT.NONE);
		gd_grp_CypherText = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_grp_CypherText.heightHint = 160;
		grp_CypherText.setLayoutData(gd_grp_CypherText);
		grp_CypherText.setText("The following text will be analyzed");
		fl_grp_CypherText = new FillLayout(SWT.HORIZONTAL);
		fl_grp_CypherText.marginWidth = 8;
		fl_grp_CypherText.marginHeight = 8;
		grp_CypherText.setLayout(fl_grp_CypherText);
		txt_CypherText = new Text(grp_CypherText, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.MULTI);
		
		//TODO: empty other variables as well?
		txt_CypherText.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				txt_ResultText.setText("");
				grp_CypherText.setText("The following text will be analyzed" + " (characters: " + txt_CypherText.getText().length() +")");
			}
		});		
		
		txt_CypherText.setEditable(true);
		txt_CypherText.setFocus();
		
		grp_LoadReferenceText = new Group(cp_Container, SWT.NONE);
		gd_grp_LoadReferenceText = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_grp_LoadReferenceText.heightHint = 65;
		grp_LoadReferenceText.setLayoutData(gd_grp_LoadReferenceText);
		grp_LoadReferenceText.setText("Option: Select your own reference text and link its label with a new topic");

		btn_LoadReferenceText = new Button(grp_LoadReferenceText, SWT.NONE);
		btn_LoadReferenceText.addMouseListener(new MouseAdapter()
		{
		     public void mouseDown(MouseEvent e)
	            {
	                try
	                {
	                    Display display = Display.getDefault();
	                    Shell dialogShell = new Shell(display, SWT.APPLICATION_MODAL);
	                    FileDialog fd_ChooseFile = new FileDialog(dialogShell, SWT.OPEN);
	                    fd_ChooseFile.setFilterPath("\\");
	                    fd_ChooseFile.setFilterExtensions(new String[] {"*.*"});
	                    File file_LoadReferenceText = new File(fd_ChooseFile.open());
	                    FileInputStream fis;
	                    fis = new FileInputStream(file_LoadReferenceText);
	                    byte[] content = new byte[fis.available()];
	                    referenceText = new String(content);
	                    txt_Reference.setEnabled(true);
	                    txt_Reference.setText("Name_of_new_topic");
	                    txt_Reference.setFocus();
	                    btn_ReferenceSubmit.setEnabled(true);
	                    String path = file_LoadReferenceText.getAbsolutePath();
	                    if (fis.available() < 512)
	                    {
	                        lbl_FileInfo.setText("File info: File should be at least 512 characters long.");
	                        throw new Exception();
	                    }
	                    else
	                    {
	                        lbl_FileInfo.setText("File path: " + (path.length() - 60 < 0? "": "...") 
	                        								   + path.substring(Math.max(path.length() - 60, 0), Math.max(path.length(), 60)) 
	                        								   + " (characters: " +fis.available()+")");
	                    }
	                    fis.read(content);
	                    fis.close();
	                }
	                catch (Exception ex)
	                {
	                    txt_Reference.setEnabled(false);
	                }
	            }
		});
		
		btn_LoadReferenceText.setBounds(10, 22, 145, 23);		
		btn_LoadReferenceText.setText("Load reference text...");

		lbl_Reference = new Label(grp_LoadReferenceText, SWT.NONE);		
		lbl_Reference.setText("New label:");
		lbl_Reference.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, false, false, 1, 1));
		lbl_Reference.setBounds(170, 27, 60, 23);		
		
		txt_Reference = new Text(grp_LoadReferenceText, SWT.BORDER);
		txt_Reference.setEnabled(false);
		txt_Reference.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, false, false, 1, 1));
		txt_Reference.setBounds(235, 25, 145, 23);
		txt_Reference.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{       //file we have loaded		   topic's name
				 if (referenceText.length() < 1 || txt_Reference.getText().length() < 1)
	                    btn_ReferenceSubmit.setEnabled(false);
	             else
	                    btn_ReferenceSubmit.setEnabled(true);					
			}
		});
		
		btn_ReferenceSubmit = new Button(grp_LoadReferenceText, SWT.NONE);		
		btn_ReferenceSubmit.addMouseListener(new MouseAdapter()
		{
			public void mouseDown(MouseEvent e)
			{	
				if (txt_Reference.getText().equals("Literature") || 
					txt_Reference.getText().equals("Game rules") ||	
					txt_Reference.getText().equals("Politics")   ||
					txt_Reference.getText().equals("Football")   ||
					txt_Reference.getText().equals("Law"))
				{						
					Display display = Display.getDefault();
					Shell dialogShell = new Shell(display, SWT.APPLICATION_MODAL);
					
					MessageBox messageBox = new MessageBox(dialogShell, SWT.ICON_WARNING | SWT.OK);
					messageBox.setText("Error");
					messageBox.setMessage("Choose an alternative name for your reference text.");
					messageBox.open();
				}
				
				else if (txt_Reference.getText().equals("Name_of_new_topic"))
				{
					txt_Reference.setEnabled(false);
					btn_ReferenceSubmit.setEnabled(false);
				}
				
				else if (txt_Reference.getText().length() > 0)
				{
					txt_Reference.setEnabled(false);
					btn_ReferenceSubmit.setEnabled(false);
				}
			}
		});
		btn_ReferenceSubmit.setBounds(460, 25, 145, 23);
		btn_ReferenceSubmit.setText("Submit new topic");
		btn_ReferenceSubmit.setEnabled(false);
		
		lbl_FileInfo = new Label(grp_LoadReferenceText, SWT.NONE);     
        lbl_FileInfo.setText("File info: File not loaded.");
        lbl_FileInfo.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, false, false, 1, 1));
        lbl_FileInfo.setBounds(14, 55, 600, 23);
		
		grp_AnalizeText = new Group(cp_Container, SWT.NONE);
		gd_grp_AnalizeText = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_grp_AnalizeText.heightHint = 45;
		grp_AnalizeText.setLayoutData(gd_grp_AnalizeText);
		grp_AnalizeText.setText("Click the Analyze button to start the analysis");
		btn_AnalizeText = new Button(grp_AnalizeText, SWT.NONE);
		btn_AnalizeText.addMouseListener(new MouseAdapter()
		{
				public void mouseDown(MouseEvent e)
			    {
			       if (txt_CypherText.getText().length() < 512)
			       {
			    	   	Display display = Display.getDefault();
			       		Shell dialogShell = new Shell(display, SWT.APPLICATION_MODAL);
			                    
			            MessageBox messageBox = new MessageBox(dialogShell, SWT.ICON_WARNING | SWT.OK);
			            messageBox.setText("Error");
			            messageBox.setMessage("Your text is too short. Make sure it has at least 512 characters.");
			            messageBox.open();           
			       }

			       else if ((referenceText.isEmpty() && !txt_Reference.getEnabled()) || (!referenceText.isEmpty() && !txt_Reference.getEnabled()))
			       {
			    	   	NgramCalculate();
			       }
			    
			       else
			       {
			        	Display display = Display.getDefault();
			        	Shell dialogShell = new Shell(display, SWT.APPLICATION_MODAL);
			     
			        	MessageBox messageBox = new MessageBox(dialogShell, SWT.ICON_WARNING | SWT.OK);
			        	messageBox.setText("Error");
			        	messageBox.setMessage("Submit topic name.");
			        	messageBox.open();
			       }
			    }
		});
		btn_AnalizeText.setBounds(10, 22, 145, 23);
		btn_AnalizeText.setText("Analyze text");		

		grp_ResultText = new Group(cp_Container, SWT.NONE);
		gd_grp_ResultText = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_grp_ResultText.heightHint = 130;
		grp_ResultText.setLayoutData(gd_grp_ResultText);
		grp_ResultText.setText("Result: To which topic does the given text probably belong to (out of the 5 given topics plus one optional own reference)?");
		fl_grp_ResultText = new FillLayout(SWT.HORIZONTAL);
		fl_grp_ResultText.marginWidth = 8;
		fl_grp_ResultText.marginHeight = 8;
		grp_ResultText.setLayout(fl_grp_ResultText);
		txt_ResultText = new Text(grp_ResultText, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txt_ResultText.setEditable(false);

		sc_Container.setContent(cp_Container);
		sc_Container.setMinSize(cp_Container.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, "org.jcryptool.analysis.ngram.view");

	}
	
	public void reset() 
	{
		cb_LoadText.select(0);
		cb_ChooseLang.select(0);
		cb_ChooseDist.select(0);
		txt_CypherText.setText("");
		referenceText = "";
		txt_Reference.setText("");
		txt_Reference.setEnabled(false);
		lbl_FileInfo.setText("File info: File not loaded.");
		grp_CypherText.setText("The following text will be analyzed");
		txt_ResultText.setText("");		   
	}

	public void NgramCalculate()
	{
	    new NgramCode().NgramCalculate(this);
	}

    public String getCypherText()
    {
        return(txt_CypherText.getText());
    }

    public int getCypherLanguage()
    {
        return(cb_ChooseLang.getSelectionIndex());
    }

    public int getDistanceMethod()
    {
        return(cb_ChooseDist.getSelectionIndex());
    }

    public String getReferenceText()
    {
        return(referenceText);
    }

    public String getReferenceTitle()
    {
        return(txt_Reference.getText());
    }

    public void setResultText(String text)
    {
        txt_ResultText.setText(text);
    }
}