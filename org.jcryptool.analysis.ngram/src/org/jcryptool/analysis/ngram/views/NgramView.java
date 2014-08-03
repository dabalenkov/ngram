package org.jcryptool.analysis.ngram.views;

import java.io.*;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.custom.*;
import org.eclipse.ui.part.*;
import org.eclipse.wb.swt.*;
import org.jcryptool.analysis.ngram.tools.*;

public class NgramView extends ViewPart
{
	public static final String ID = "org.jcryptool.analysis.ngram.views.NgramView";

	private ScrolledComposite sc_Container;
	private Composite cp_Container;
	private Label lbl_Header;
	private Group grp_LoadText;
	private GridData gd_grp_LoadText;
	private Combo cb_LoadText;
	private Combo cb_ChooseLang;
	private Combo cb_ChooseDist;
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
    private Button btn_ReferenceSubmit;
    private Text txt_ResultText;
    private String referenceText;

    public NgramView()
    {
        referenceText = "";
    }

    public void setFocus()
    {
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
		gd_grp_LoadText.heightHint = 45;
		grp_LoadText.setLayoutData(gd_grp_LoadText);
		grp_LoadText.setText("Three input parameters: the input method, the language of the text you want to analyze, and the analysis method.");

		cb_LoadText = new Combo(grp_LoadText, SWT.READ_ONLY);
		cb_LoadText.setItems(new String[] {"From manual input", "From file", "English samle text", "German sample text"});
		cb_LoadText.setBounds(10, 25, 145, 23);
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
						fd_ChooseFile.setFilterExtensions(new String[] {"*.*"});
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
//					  FileInputStream fin;  
//
//					  try
//					  {
//						  String current = new java.io.File( "." ).getCanonicalPath();
//					      System.out.println("Current dir:"+current);
//						  
//					      fin = new FileInputStream ("/Users/dimitri/Documents/ngram/ngram/org.jcryptool.analysis.ngram/asset/Sample_text_EN.txt");
//					      txt_CypherText.setText(new BufferedReader(new InputStreamReader(fin)).readLine());
//					      fin.close();  
//					  }
//					  catch (IOException ex)
//					  {
//						  ex.printStackTrace();
//					  }															
					
					txt_CypherText.setText(
					    "The text below is a part of Jack London's \"White Phang\". To see, if the analysis will recognize it as a Literature, click the \"Analyze text\" button.\n" +
					    "--\n" +
					    "In the fall of the year, when the days were shortening and the bite of the frost was coming into the air, White Fang got his chance for\n" +
					    "liberty. For several days there had been a great hubbub in the village. The summer camp was being dismantled, and the tribe, bag and baggage, was preparing\n" +
						"to go off to the fall hunting. White Fang watched it all with eager eyes, and when the tepees began to come down and the canoes were loading at the bank,\n" +
						"he understood. Already the canoes were departing, and some had disappeared down the river. Quite deliberately he determined to stay behind. He waited his\n" +
						"opportunity to slink out of camp to the woods. Here, in the running stream where ice was beginning to form, he hid his trail. Then he crawled into the\n" +
						"heart of a dense thicket and waited. The time passed by, and he slept intermittently for hours. Then he was aroused by Grey Beaver's voice calling him\n" +
						"by name. There were other voices. White Fang could hear Grey Beaver's squaw taking part in the search, and Mit-sah, who was Grey Beaver's son.\n" +
						"White Fang trembled with fear, and though the impulse came to crawl out of his hiding-place, he resisted it. After a time the voices died away, and\n" +
						"some time after that he crept out to enjoy the success of his undertaking. Darkness was coming on, and for a while he played about among the trees,\n" +
						"pleasuring in his freedom. Then, and quite suddenly, he became aware of loneliness. He sat down to consider, listening to the silence of the forest\n" +
						"and perturbed by it. That nothing moved nor sounded, seemed ominous. He felt the lurking of danger, unseen and unguessed. He was suspicious of the\n" +
						"looming bulks of the trees and of the dark shadows that might conceal all manner of perilous things. Then it was cold. Here was no warm side of a\n" +
						"tepee against which to snuggle. The frost was in his feet, and he kept lifting first one fore-foot and then the other. He curved his bushy tail\n" +
						"around to cover them, and at the same time he saw a vision. There was nothing strange about it. Upon his inward sight was impressed a succession\n" +
						"of memory-pictures. He saw the camp again, the tepees, and the blaze of the fires. He heard the shrill voices of the women, the gruff basses of\n" +
						"the men, and the snarling of the dogs. He was hungry, and he remembered pieces of meat and fish that had been thrown him. Here was no meat,\n" +
						"nothing but a threatening and inedible silence.");

					txt_CypherText.setEditable(true);
					cb_ChooseLang.select(0);
				}

				if (cb_LoadText.getSelectionIndex() == 3)
				{
					txt_CypherText.setText(
					    "Der Text unten ist ein Auszug aus \"Drei Kameraden\" von Erich Maria Remarque. Um zu sehen, ob die Analyse es richtig als Literatur erkennt, clicke auf \"Analyze text\" Knopf.\n" + 
						"--\n" +
					    "Ich zog einen Briefbogen aus dem Fach und fing an zu rechnen. Die Kinderzeit, die Schule – das war ein Komplex,\n" +           
						"fern, irgendwo, schon nicht mehr wahr. Das richtige Leben begann erst 1916. Da war ich gerade Rekrut geworden, dünn,\n" +
						"hochgeschossen, achtzehn Jahre alt, und übte nach dem Kommando eines schnauzbärtigen Unteroffiziers auf den\n" +
						"Sturzäckern hinter der Kaserne Hinlegen und Aufstehen. An einem der ersten Abende kam meine Mutter in die\n" +
						"Kaserne, um mich zu besuchen; aber sie mußte über eine Stunde auf mich warten. Ich hatte meinen Tornister nicht\n" +
						"vorschriftsmäßig gepackt gehabt und mußte deshalb in der freien Zeit zur Strafe die Latrinen scheuern. Sie wollte mir\n" +
						"helfen, aber das durfte sie nicht. Sie weinte, und ich war so müde, daß ich einschlief, als sie noch bei mir saß.\n" +
						"1917. Flandern. Middendorf und ich hatten in der Kantine eine Flasche Rotwein gekauft. Damit wollten wir feiern.\n" +
						"Aber wir kamen nicht dazu. Frühmorgens fing das schwere Feuer der Engländer an. Köster wurde mittags verwundet.\n" +
						"Meyer und Deters fielen nachmittags. Und abends, als wir schon glaubten, Ruhe zu haben, und die Flasche\n" +
						"aufmachten, kam Gas und quoll in die Unterstände. Wir hatten zwar rechtzeitig die Masken auf, aber die von\n" +
						"Middendorf war kaputt. Als er es merkte, war es zu spät. Bis sie abgerissen und eine neue gefunden war, hatte er schon\n" +
						"zuviel Gas geschluckt und brach bereits Blut. Er starb am nächsten Morgen, grün und schwarz im Gesicht. Sein Hals\n" +
						"war ganz zerrissen – so hatte er mit den Nägeln versucht, ihn aufzukratzen, um Luft zu kriegen.");

					txt_CypherText.setEditable(true);
                    cb_ChooseLang.select(1);
				}

				txt_ResultText.setText("");
			}
	    });
		
        cb_ChooseLang = new Combo(grp_LoadText, SWT.READ_ONLY);     
        cb_ChooseLang.setItems(new String[] {"English", "German"});
        cb_ChooseLang.setBounds(235, 25, 145, 23);
        cb_ChooseLang.select(0); 
        cb_ChooseLang.addModifyListener(new ModifyListener()
        {
            public void modifyText(ModifyEvent e)
            {
                txt_ResultText.setText("");
            }
        });

		cb_ChooseDist = new Combo(grp_LoadText, SWT.READ_ONLY);		
		cb_ChooseDist.setItems(new String[] {"Euclidean", "Least Squares"});
		cb_ChooseDist.setBounds(460, 25, 145, 23);
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
		txt_CypherText.setEditable(true);
		txt_CypherText.setFocus();
		
		grp_LoadReferenceText = new Group(cp_Container, SWT.NONE);
		gd_grp_LoadReferenceText = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_grp_LoadReferenceText.heightHint = 40;
		grp_LoadReferenceText.setLayoutData(gd_grp_LoadReferenceText);
		grp_LoadReferenceText.setText("Option: Select your own reference text and link its label with a new topic.");

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
					fis.read(content);
					fis.close();
					referenceText = new String(content);
					txt_Reference.setEnabled(true);
					txt_Reference.setFocus();
				}
				catch (Exception ex)
				{
					txt_Reference.setEnabled(false);
			    }
			}
		});
		
		btn_LoadReferenceText.setBounds(10, 22, 145, 23);		
		btn_LoadReferenceText.setText("Load reference text...");

		txt_Reference = new Text(grp_LoadReferenceText, SWT.BORDER);
		txt_Reference.setEnabled(false);
		txt_Reference.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, false, false, 1, 1));
		txt_Reference.setBounds(235, 25, 145, 23);
		
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
				else
				{
					txt_Reference.setEnabled(false);
				}
			}
		});
		btn_ReferenceSubmit.setBounds(460, 25, 145, 23);
		btn_ReferenceSubmit.setText("Submit name");		
		
		grp_AnalizeText = new Group(cp_Container, SWT.NONE);
		gd_grp_AnalizeText = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_grp_AnalizeText.heightHint = 45;
		grp_AnalizeText.setLayoutData(gd_grp_AnalizeText);
		grp_AnalizeText.setText("Click the Analyze button to start the analysis.");
		btn_AnalizeText = new Button(grp_AnalizeText, SWT.NONE);
		btn_AnalizeText.addMouseListener(new MouseAdapter()
		{
			public void mouseDown(MouseEvent e)
			{
				if ((txt_Reference.getText().length() > 0) && !txt_Reference.getEnabled())
				{
					NgramCalculate();
				}
				else
				{
					Display display = Display.getDefault();
					Shell dialogShell = new Shell(display, SWT.APPLICATION_MODAL);
					
					MessageBox messageBox = new MessageBox(dialogShell, SWT.ICON_WARNING | SWT.OK);
					messageBox.setText("Error");
					messageBox.setMessage("Submit reference name.");
					messageBox.open();
				}
			}
		});
		btn_AnalizeText.setBounds(10, 22, 145, 23);
		btn_AnalizeText.setText("Analyze text");		

		grp_ResultText = new Group(cp_Container, SWT.NONE);
		gd_grp_ResultText = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_grp_ResultText.heightHint = 70;
		grp_ResultText.setLayoutData(gd_grp_ResultText);
		grp_ResultText.setText("Result: To which topic does the given text probably belong to?");
		fl_grp_ResultText = new FillLayout(SWT.HORIZONTAL);
		fl_grp_ResultText.marginWidth = 8;
		fl_grp_ResultText.marginHeight = 8;
		grp_ResultText.setLayout(fl_grp_ResultText);
		txt_ResultText = new Text(grp_ResultText, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txt_ResultText.setEditable(false);

		sc_Container.setContent(cp_Container);
		sc_Container.setMinSize(cp_Container.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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