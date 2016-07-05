import java.awt.ComponentOrientation;
import java.awt.Component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import javax.swing.JCheckBox;

/*
inputDir_Text
outputDir_Text
numOfThreads_Text
amountOfRam_Text
AB_SCIEX_MS_ConverterDir_Text
msconvertDir_Text
indexmzXMLDir_Text
DIA_Umpire_SE_Jar_Dir_Text
fastaDir_Text
ppm_Text
enzyme_Text
modsDir_Text
paramsXMLDir_Text
msgfplusJarDir_Text
cometDir_Text
cometParamsDir_Text
cometFastaDir_Text
*/

public class GetInput extends JPanel {
    private static final String APP_TITLE = "Get Input";
    private static final int APP_WIDTH = 1;
    private static final int APP_HEIGHT = 1;

	private JTextField inputDir_Text = new JTextField("C:\\");
	private JTextField outputDir_Text = new JTextField("C:\\");
	private JTextField numOfThreads_Text = new JTextField("12");
	private JTextField amountOfRam_Text = new JTextField("64");
	private JTextField AB_SCIEX_MS_ConverterDir_Text = new JTextField("C:\\Program Files (x86)\\AB SCIEX\\MS Data Converter\\AB_SCIEX_MS_Converter.exe");
	private JTextField msconvertDir_Text = new JTextField("C:\\Program Files\\ProteoWizard\\ProteoWizard 3.0.8708\\msconvert.exe");
	private JTextField indexmzXMLDir_Text = new JTextField("C:\\indexmzXML.exe");
	private JTextField DIA_Umpire_SE_Jar_Dir_Text = new JTextField("C:\\DIAUmpire_v1_4254-\\DIA_Umpire_SE.jar");
	private JTextField fastaDir_Text = new JTextField("EX:      C:\\...\\20150427_mouse_sprot.cc.fasta");
	private JTextField ppm_Text = new JTextField("25ppm");
	private JTextField enzyme_Text = new JTextField("5");
	private JTextField modsDir_Text = new JTextField("EX:       C:\\...\\Mods_acetyl.txt");
	private JTextField paramsXMLDir_Text = new JTextField("EX:      C:\\...\\tandem_Ksuc_params.xml");
	private JTextField tandemDir_Text = new JTextField("C:\\Inetpub\\tpp-bin\\tandem.exe");
	private JTextField tandem2xmlDir_Text = new JTextField("C:\\Inetpub\\tpp-bin\\Tandem2XML.exe");
	private JTextField xInteractDir_Text = new JTextField("C:\\Inetpub\\tpp-bin\\xinteract.exe");
	private JTextField InterProphetParserDir_Text = new JTextField("C:\\Inetpub\\tpp-bin\\InterProphetParser.exe");
	private JTextField msgfplusJarDir_Text = new JTextField("C:\\MSGFplus.20140716\\msgfplus.jar");
	private JTextField cometDir_Text = new JTextField("C:\\Inetpub\\tpp-bin\\comet.exe");
	private JTextField cometParamsDir_Text = new JTextField("EX:       C:\\Inetpub\\wwwroot\\ISB\\data\\DIAonly\\20160625_DIA\\comet.Kac.params");
	private JTextField cometFastaDir_Text = new JTextField("EX:       C:\\Inetpub\\wwwroot\\ISB\\data\\DIAonly\\20160625_DIA\\20150810.mouse.cc.iRT_DECOY.fasta");	
	private JTextField[] textFields = {inputDir_Text, outputDir_Text, numOfThreads_Text, amountOfRam_Text, AB_SCIEX_MS_ConverterDir_Text,
									   msconvertDir_Text, indexmzXMLDir_Text, DIA_Umpire_SE_Jar_Dir_Text, fastaDir_Text, ppm_Text, enzyme_Text,
									   modsDir_Text, paramsXMLDir_Text, tandemDir_Text, tandem2xmlDir_Text, xInteractDir_Text,
									   InterProphetParserDir_Text, msgfplusJarDir_Text, cometDir_Text, cometParamsDir_Text, cometFastaDir_Text};
	private int textFieldWidth = 250;
	private int textFieldHeight = 20;
	
	private JCheckBox DiaUmpire_Checkbox = new JCheckBox("                        Click to run  ");
	private JCheckBox MSGFSearch_Checkbox = new JCheckBox("       Click to run  ");
	private JCheckBox XTandem_Checkbox = new JCheckBox("       Click to run  ");
	private JCheckBox Prophet_Checkbox = new JCheckBox("             Click to run  ");
	private JCheckBox CometSearch_Checkbox = new JCheckBox("             Click to run  ");
	private JCheckBox[] checkBoxFields = {DiaUmpire_Checkbox, MSGFSearch_Checkbox, XTandem_Checkbox, Prophet_Checkbox, CometSearch_Checkbox};

	
	private boolean runDiaUmpire = false;
	private boolean runMSGFSearch = false;
	private boolean runXTandem = false;
	private boolean runProphet = false;
	private boolean runCometSearch = false;
	
	
	public StringBuffer fieldData = new StringBuffer();
	public StringBuffer dontExist = new StringBuffer();
	
    private static class GridItem {
        private JComponent component;
        private boolean isExportable;
        private int xPos;
        private int yPos;
        private int colSpan;
        private int rowSpan;

        public GridItem(JComponent component, boolean isExportable, int xPos, int yPos) {
            this(component, isExportable, xPos, yPos, 1, 1);
        }

        public GridItem(JComponent component, boolean isExportable, int xPos, int yPos, int colSpan, int rowSpan) {
            this.component = component;
            this.isExportable = isExportable;
            this.xPos = xPos;
            this.yPos = yPos;
            this.colSpan = colSpan;
            this.rowSpan = rowSpan;
        }
		
    }

    private int appWidth;
    private int appHeight;
    private Map<String, GridItem> componentMap;
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    public GetInput(int width, int height) {
        super();

        this.appWidth = width;
        this.appHeight = height;

        this.init();
        this.createChildren();
    }

    protected void init() {
        this.constraints = new GridBagConstraints();
        this.layout = new GridBagLayout();
        this.componentMap = new LinkedHashMap<String, GridItem>();

        this.setLayout(this.layout);

        this.constraints.ipadx = 11;
        this.constraints.ipady = 11;
        this.constraints.insets = new Insets(8, 4, 8, 4);

        this.constraints.anchor = GridBagConstraints.LAST_LINE_START;
        this.constraints.fill = GridBagConstraints.HORIZONTAL;
		
		/*
		 * Make all the text boxes the same size and disable them
		 */
		for( JTextField element : textFields ) {
			element.setPreferredSize( new Dimension(textFieldWidth, textFieldHeight) );
			element.setEnabled(false);
		}
		
		for( JCheckBox element : checkBoxFields ) {
			element.setHorizontalTextPosition(SwingConstants.LEFT);
		}
		
		/*
		 * Create the general info part and enable the text boxes here
		 */
		componentMap.put("generalInfo", new GridItem(new JLabel("General Info"), false, 1, 0, 3, 1));
        componentMap.put("inputDir", new GridItem(new JLabel("Enter input directory"), false, 0, 1));
        componentMap.put("inputDirText", new GridItem(inputDir_Text, true, 1, 1, 1, 1));
        componentMap.put("outputDir", new GridItem(new JLabel("Enter output directory"), false, 0, 2));
        componentMap.put("outputDirText", new GridItem(outputDir_Text, true, 1, 2, 1, 1));
        componentMap.put("numOfThreads", new GridItem(new JLabel("Enter number of threads"), false, 0, 3));
        componentMap.put("numOfThreadsText", new GridItem(numOfThreads_Text, true, 1, 3, 1, 1));
        componentMap.put("amountOfRam", new GridItem(new JLabel("Enter amount of ram"), false, 0, 4));
        componentMap.put("amountOfRamText", new GridItem(amountOfRam_Text, true, 1, 4, 1, 1));
		inputDir_Text.setEnabled(true);
		outputDir_Text.setEnabled(true);
		numOfThreads_Text.setEnabled(true);
		amountOfRam_Text.setEnabled(true);
		
		
		componentMap.put("diaUmpire_pipe_input", new GridItem(new JLabel("diaUmpire_pipe.py Info"), false, 1, 5, 3, 1));
        componentMap.put("AB_SCIEX_MS_Converter_Dir", new GridItem(new JLabel("<html>Enter the full path to<br>AB_SCIEX_MS_Converter.exe</html>"), false, 0, 6));
        componentMap.put("AB_SCIEX_MS_ConverterDirText", new GridItem(AB_SCIEX_MS_ConverterDir_Text, true, 1, 6, 1, 1));
        componentMap.put("msconvertDir", new GridItem(new JLabel("<html>Enter the full path to<br>msconvert.exe</html>"), false, 0, 7));
        componentMap.put("msconvertDirText", new GridItem(msconvertDir_Text, true, 1, 7, 1, 1));
        componentMap.put("indexmzXMLDir", new GridItem(new JLabel("<html>Enter the full path to<br>indexmzXML.exe</html>"), false, 0, 8));
        componentMap.put("indexmzXMLDirText", new GridItem(indexmzXMLDir_Text, true, 1, 8, 1, 1));
        componentMap.put("DIA_Umpire_SE_Jar_Dir", new GridItem(new JLabel("<html>Enter the full path to<br>DIA_Umpire_SE.jar</html>"), false, 0, 9));
        componentMap.put("DIA_Umpire_SE_Jar_DirText", new GridItem(DIA_Umpire_SE_Jar_Dir_Text, true, 1, 9, 1, 1));
		
		
		componentMap.put("MSGFSearch_input", new GridItem(new JLabel("MSGFSearch Info"), false, 4, 0, 3, 1));
		componentMap.put("msgfplusJarDir", new GridItem(new JLabel("<html>Enter the full path to<br>msgfplus.jar</html>"), false, 3, 1));
        componentMap.put("msgfplusJarDirText", new GridItem(msgfplusJarDir_Text, true, 4, 1, 1, 1));
        componentMap.put("fastaDir", new GridItem(new JLabel("<html>Enter the full path to<br>the .fasta file</html>"), false, 3, 2));
        componentMap.put("fastaDirText", new GridItem(fastaDir_Text, true, 4, 2, 1, 1));
		componentMap.put("ppm", new GridItem(new JLabel("Enter ppm"), false, 3, 3));
        componentMap.put("ppmText", new GridItem(ppm_Text, true, 4, 3, 1, 1));
		componentMap.put("enzyme", new GridItem(new JLabel("Enter enzyme (-e)"), false, 3, 4));
        componentMap.put("enzymeText", new GridItem(enzyme_Text, true, 4, 4, 1, 1));
		componentMap.put("modsDir", new GridItem(new JLabel("<html>Enter the full path to<br>the mods .txt file</html>"), false, 3, 5));
        componentMap.put("modsDirText", new GridItem(modsDir_Text, true, 4, 5, 1, 1));

		
		componentMap.put("XTandem_input", new GridItem(new JLabel("XTandem Info"), false, 4, 6, 3, 1));
        componentMap.put("paramsXMLDir", new GridItem(new JLabel("<html>Enter the full path to<br>the _params.xml file</html>"), false, 3, 7));
        componentMap.put("paramsXMLDirText", new GridItem(paramsXMLDir_Text, true, 4, 7, 1, 1));
        componentMap.put("tandemDir", new GridItem(new JLabel("<html>Enter the full path to<br>tandem.exe</html>"), false, 3, 8));
        componentMap.put("tandemDirText", new GridItem(tandemDir_Text, true, 4, 8, 1, 1));
        componentMap.put("tandem2xmlDir", new GridItem(new JLabel("<html>Enter the full path to<br>tandem2xml.exe</html>"), false, 3, 9));
        componentMap.put("tandem2xmlDirText", new GridItem(tandem2xmlDir_Text, true, 4, 9, 1, 1));

		
		componentMap.put("Comet_input", new GridItem(new JLabel("Comet Search Info"), false, 7, 0, 3, 1));
		componentMap.put("cometDir", new GridItem(new JLabel("<html>Enter the full path to<br>comet.exe</html>"), false, 6, 1));
        componentMap.put("cometDirText", new GridItem(cometDir_Text, true, 7, 1, 1, 1));
		componentMap.put("cometParamsDir", new GridItem(new JLabel("<html>Enter the full path to<br>the comet .params file</html>"), false, 6, 2));
        componentMap.put("cometParamsDirText", new GridItem(cometParamsDir_Text, true, 7, 2, 1, 1));
		componentMap.put("cometFastaDir", new GridItem(new JLabel("<html>Enter the full path to<br>the comet .fasta file</html>"), false, 6, 3));
        componentMap.put("cometFastaDirText", new GridItem(cometFastaDir_Text, true, 7, 3, 1, 1));

		
		componentMap.put("Prophet_input", new GridItem(new JLabel("Peptide Prophet and IProphet Info"), false, 7, 6, 3, 1));
		componentMap.put("xInteractDir", new GridItem(new JLabel("<html>Enter the full path to<br>xinteract.exe</html>"), false, 6, 7));
        componentMap.put("xInteractDirText", new GridItem(xInteractDir_Text, true, 7, 7, 1, 1));
		componentMap.put("InterProphetParserDir", new GridItem(new JLabel("<html>Enter the full path to<br>InterProphetParser.exe</html>"), false, 6, 8));
        componentMap.put("InterProphetParserDirText", new GridItem(InterProphetParserDir_Text, true, 7, 8, 1, 1));
		

		/*
		 * edit actionPerformed, defaultButton, checkIfFilesExist() grabFieldData()
		 */
		
		
		/*
		 * Check boxes
		 */
		componentMap.put("DiaUmpireCheckbox", new GridItem(DiaUmpire_Checkbox, false, 0, 5));
		componentMap.put("MSGFSearchCheckbox", new GridItem(MSGFSearch_Checkbox, false, 3, 0));
		componentMap.put("XTandemCheckbox", new GridItem(XTandem_Checkbox, false, 3, 6));
		componentMap.put("ProphetCheckbox", new GridItem(Prophet_Checkbox, false, 6, 6));
		componentMap.put("CometSearchCheckbox", new GridItem(CometSearch_Checkbox, false, 6, 0));
		
		/*
		 * Buttons
		 */
        componentMap.put("clearButton", new GridItem(new JButton("Clear"), false, 2, this.constraints.ipady));
        componentMap.put("defaultButton", new GridItem(new JButton("Default"), false, 3, this.constraints.ipady));
        componentMap.put("doneButton", new GridItem(new JButton("Done"), false, 4, this.constraints.ipady));
		componentMap.put("exitButton", new GridItem(new JButton("Exit"), false, 5, this.constraints.ipady));
		
        ((JButton) componentMap.get("doneButton").component).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				boolean errorOccurred = false;
				dontExist.delete(0, dontExist.length());
				fieldData.delete(0, fieldData.length());
				
				if(inputDir_Text.getText().equals("") || outputDir_Text.getText().equals("") || 
				   numOfThreads_Text.getText().equals("") || amountOfRam_Text.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "         Fill in all the fields");
					return;
				}
				if(!checkIfFilesExist(1)) {
					errorOccurred = true;
				}
				
				if(runDiaUmpire) {
					if(AB_SCIEX_MS_ConverterDir_Text.getText().equals("") || msconvertDir_Text.getText().equals("") ||
					   indexmzXMLDir_Text.getText().equals("") || DIA_Umpire_SE_Jar_Dir_Text.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "         Fill in all the fields");
						return;
					}
					if(!checkIfFilesExist(2)) {
						errorOccurred = true;
					}
				}
				
				if(runMSGFSearch) {
					if(msgfplusJarDir_Text.getText().equals("") || fastaDir_Text.getText().equals("") || 
					   ppm_Text.getText().equals("") || enzyme_Text.getText().equals("") ||
					   modsDir_Text.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "         Fill in all the fields");
						return;
					  }
					if(fastaDir_Text.getText().contains("EX:") || modsDir_Text.getText().contains("EX:")) {
						JOptionPane.showMessageDialog(null, "     Fill in all fields correctly");
						return;
					}
					if(!checkIfFilesExist(3)) {
						errorOccurred = true;
					}
				}
				
				if(runXTandem) {
					if(paramsXMLDir_Text.getText().equals("") || tandemDir_Text.getText().equals("") ||
					   tandem2xmlDir_Text.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "         Fill in all the fields");
						return;
					}
					if(paramsXMLDir_Text.getText().contains("EX:")) {
						JOptionPane.showMessageDialog(null, "     Fill in all fields correctly");
						return;
					}
					if(!checkIfFilesExist(4)) {
						errorOccurred = true;
					}
				}
				
				if(runCometSearch) {
					if( cometDir_Text.getText().equals("") || cometParamsDir_Text.getText().equals("") || cometFastaDir_Text.getText().equals("") ) {
						JOptionPane.showMessageDialog(null, "         Fill in all the fields");
						return;
					}
					if(cometParamsDir_Text.getText().contains("EX:") || cometFastaDir_Text.getText().contains("EX:")) {
						JOptionPane.showMessageDialog(null, "     Fill in all fields correctly");
						return;
					}
					if(!checkIfFilesExist(5)) {
						errorOccurred = true;
					}
				}
				
				if(runProphet) {
					if(xInteractDir_Text.getText().equals("") || InterProphetParserDir_Text.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "         Fill in all the fields");
						return;
					}
					if(!checkIfFilesExist(6)) {
						errorOccurred = true;
					}
				}
				
				
				if( errorOccurred == true ) {
					JOptionPane.showMessageDialog(null, dontExist.toString());
				}
				else {
					grabFieldData();
					System.exit(0);
				}
				
            }
        });;

        ((JButton) componentMap.get("defaultButton").component).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] lines = {
                    "inputDirText::C:\\",
                    "outputDirText::C:\\",
                    "numOfThreadsText::12",
                    "amountOfRamText::64",
					"AB_SCIEX_MS_ConverterDirText::C:\\Program Files (x86)\\AB SCIEX\\MS Data Converter\\AB_SCIEX_MS_Converter.exe",
					"msconvertDirText::C:\\Program Files\\ProteoWizard\\ProteoWizard 3.0.8708\\msconvert.exe",
					"indexmzXMLDirText::C:\\indexmzXML.exe",
					"DIA_Umpire_SE_Jar_DirText::C:\\DIAUmpire_v1_4254-\\DIA_Umpire_SE.jar",
					"fastaDirText::EX:      C:\\...\\20150427_mouse_sprot.cc.fasta",
					"ppmText::25ppm",
					"enzymeText::5",
					"modsDirText::EX:       C:\\...\\Mods_acetyl.txt",
					"paramsXMLDirText::EX:      C:\\...\\tandem_Ksuc_params.xml",
					"tandemDirText::C:\\Inetpub\\tpp-bin\\tandem.exe",
					"tandem2xmlDirText::C:\\Inetpub\\tpp-bin\\Tandem2XML.exe",
					"xInteractDirText::C:\\Inetpub\\tpp-bin\\xinteract.exe",
					"InterProphetParserDirText::C:\\Inetpub\\tpp-bin\\InterProphetParser.exe",
					"msgfplusJarDirText::C:\\MSGFplus.20140716\\msgfplus.jar"
                };

                setFieldData(lines);
            }
        });;

        ((JButton) componentMap.get("clearButton").component).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFieldData();
            }
        });;
		
		((JButton) componentMap.get("exitButton").component).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });;
		
		((JCheckBox) componentMap.get("DiaUmpireCheckbox").component).addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
					runDiaUmpire = true;
					AB_SCIEX_MS_ConverterDir_Text.setEnabled(true);
					msconvertDir_Text.setEnabled(true);
					indexmzXMLDir_Text.setEnabled(true);
					DIA_Umpire_SE_Jar_Dir_Text.setEnabled(true);
				}
				else {
					runDiaUmpire = false;
					AB_SCIEX_MS_ConverterDir_Text.setEnabled(false);
					msconvertDir_Text.setEnabled(false);
					indexmzXMLDir_Text.setEnabled(false);
					DIA_Umpire_SE_Jar_Dir_Text.setEnabled(false);
				}
            }
        });;
		
		((JCheckBox) componentMap.get("MSGFSearchCheckbox").component).addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
					runMSGFSearch = true;
					msgfplusJarDir_Text.setEnabled(true);
					fastaDir_Text.setEnabled(true);
					ppm_Text.setEnabled(true);
					enzyme_Text.setEnabled(true);
					modsDir_Text.setEnabled(true);
				}
				else {
					runMSGFSearch = false;
					msgfplusJarDir_Text.setEnabled(false);
					fastaDir_Text.setEnabled(false);
					ppm_Text.setEnabled(false);
					enzyme_Text.setEnabled(false);
					modsDir_Text.setEnabled(false);
				}
            }
        });;
		
		((JCheckBox) componentMap.get("XTandemCheckbox").component).addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
					runXTandem = true;
					paramsXMLDir_Text.setEnabled(true);
					tandemDir_Text.setEnabled(true);
					tandem2xmlDir_Text.setEnabled(true);
				}
				else {
					runXTandem = false;
					paramsXMLDir_Text.setEnabled(false);
					tandemDir_Text.setEnabled(false);
					tandem2xmlDir_Text.setEnabled(false);
				}
            }
        });;
		
		((JCheckBox) componentMap.get("ProphetCheckbox").component).addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
					runProphet = true;
					xInteractDir_Text.setEnabled(true);
					InterProphetParserDir_Text.setEnabled(true);
				}
				else {
					runProphet = false;
					xInteractDir_Text.setEnabled(false);
					InterProphetParserDir_Text.setEnabled(false);
				}
            }
        });;
		
		((JCheckBox) componentMap.get("CometSearchCheckbox").component).addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
					runCometSearch = true;
					cometDir_Text.setEnabled(true);
					cometParamsDir_Text.setEnabled(true);
					cometFastaDir_Text.setEnabled(true);
				}
				else {
					runCometSearch = false;
					cometDir_Text.setEnabled(false);
					cometParamsDir_Text.setEnabled(false);
					cometFastaDir_Text.setEnabled(false);
				}
            }
        });;	
	
    }

    protected void createChildren() {
        Iterator<Map.Entry<String, GridItem>> it;

        for (it = componentMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, GridItem> item = it.next();
            GridItem gridItem = item.getValue();

            this.constraints.gridx = gridItem.xPos;
            this.constraints.gridy = gridItem.yPos;
            this.constraints.gridwidth = gridItem.colSpan;
            this.constraints.gridheight = gridItem.rowSpan;

            this.add(gridItem.component, this.constraints);
        }
    }
	
	private boolean checkIfFilesExist(int option) {
		boolean filesExist = true;
		Path tempPath;
		try{		
			if(option == 1) {
				tempPath = Paths.get( inputDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("input directory does not exist\r\n");
					filesExist = false;
				}
				
				tempPath = Paths.get( outputDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("output directory does not exist\r\n");
					filesExist = false;
				}
			}
		
			if(option == 2) {
				tempPath = Paths.get( AB_SCIEX_MS_ConverterDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("AB_SCIEX_MS_Converter.exe does not exist\r\n");
					filesExist = false;
				}
				
				tempPath = Paths.get( msconvertDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("msconvert.exe does not exist\r\n");
					filesExist = false;
				}
				
				tempPath = Paths.get( indexmzXMLDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("indexMZXML.exe does not exist\r\n");
					filesExist = false;
				}
				
				tempPath = Paths.get( DIA_Umpire_SE_Jar_Dir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("DIA_Umpire_SE.jar does not exist\r\n");
					filesExist = false;
				}
			}
			
			if(option == 3) {
				tempPath = Paths.get( msgfplusJarDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("msgfplus.jar does not exist\r\n");
					filesExist = false;
				}
				
				tempPath = Paths.get( fastaDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append(".fasta file does not exist\r\n");
					filesExist = false;
				}
				
				tempPath = Paths.get( modsDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("mods .txt file does not exist\r\n");
					filesExist = false;
				}
			}
			
			if(option == 4) {
				tempPath = Paths.get( paramsXMLDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("_params.xml file does not exist\r\n");
					filesExist = false;
				}
				
				tempPath = Paths.get( tandemDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("tandem.exe does not exist\r\n");
					filesExist = false;
				}
				
				tempPath = Paths.get( tandem2xmlDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("tandem2xml.exe does not exist\r\n");
					filesExist = false;
				}
			}
			
			if(option == 5) {
				tempPath = Paths.get( cometDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("comet.exe does not exist\r\n");
					filesExist = false;
				}
				
				tempPath = Paths.get( cometFastaDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("The comet .fasta file does not exist\r\n");
					filesExist = false;
				}
				
				tempPath = Paths.get( cometParamsDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("The comet .params file does not exist\r\n");
					filesExist = false;
				}
			}
			
			if(option == 6) {
				tempPath = Paths.get( xInteractDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("xinteract.exe does not exist\r\n");
					filesExist = false;
				}
				
				tempPath = Paths.get( InterProphetParserDir_Text.getText() );
				if( !Files.exists(tempPath) ) {
					dontExist.append("InterProphetParser.exe does not exist\r\n");
					filesExist = false;
				}
			}
		}
		catch( Exception e ) {
			dontExist.delete(0, dontExist.length());
			dontExist.append("Some file paths have been entered incorrectly.\r\n");
			dontExist.append("Make sure the paths are in this format:\r\n");
			dontExist.append("         EX: C:\\Inetpub\\tpp-bin\\sed.exe\r\n");
			filesExist = false;
		}
		

		return filesExist;
	}

    private void grabFieldData() {
		String inputDirSTR = inputDir_Text.getText();
		String outputDirSTR = outputDir_Text.getText();
		
		if( inputDirSTR.lastIndexOf('\\') != inputDirSTR.length()-1 ) {
			inputDirSTR += "\\";
		}
		
		if( outputDirSTR.lastIndexOf('\\') != outputDirSTR.length()-1 ) {
			outputDirSTR += "\\";
		}
		
		fieldData.append("Keep this line\r\n");
		fieldData.append(inputDirSTR).append("\r\n");
		fieldData.append(outputDirSTR).append("\r\n");
		fieldData.append(numOfThreads_Text.getText()).append("\r\n");
		fieldData.append(amountOfRam_Text.getText()).append("\r\n");
		
		if(runDiaUmpire) {
			fieldData.append(AB_SCIEX_MS_ConverterDir_Text.getText()).append("\r\n");
			fieldData.append(msconvertDir_Text.getText()).append("\r\n");
			fieldData.append(indexmzXMLDir_Text.getText()).append("\r\n");
			fieldData.append(DIA_Umpire_SE_Jar_Dir_Text.getText()).append("\r\n");
		}
		else {
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
		}
		
		if(runMSGFSearch) {
			fieldData.append(msgfplusJarDir_Text.getText()).append("\r\n");
			fieldData.append(fastaDir_Text.getText()).append("\r\n");
			fieldData.append(ppm_Text.getText()).append("\r\n");
			fieldData.append(enzyme_Text.getText()).append("\r\n");
			fieldData.append(modsDir_Text.getText()).append("\r\n");
		}
		else {
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("25ppm").append("\r\n");
			fieldData.append("5").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
		}
		
		if(runXTandem) {
			fieldData.append(paramsXMLDir_Text.getText()).append("\r\n");
			fieldData.append(tandemDir_Text.getText()).append("\r\n");
			fieldData.append(tandem2xmlDir_Text.getText()).append("\r\n");
		}
		else {
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
		}
		
		if(runCometSearch) {
		}
		else {
		}
		
		if(runProphet) {
			fieldData.append(xInteractDir_Text.getText()).append("\r\n");
			fieldData.append(InterProphetParserDir_Text.getText()).append("\r\n");
		}
		else {
			fieldData.append("C:\\").append("\r\n");
			fieldData.append("C:\\").append("\r\n");
		}
		
		fieldData.append(runDiaUmpire).append("\r\n");
		fieldData.append(runMSGFSearch).append("\r\n");
		fieldData.append(runXTandem).append("\r\n");
		fieldData.append(runCometSearch).append("\r\n");
		fieldData.append(runProphet).append("\r\n");
    }

    private void clearFieldData() {
        Iterator<Map.Entry<String, GridItem>> it;
        for (it = componentMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, GridItem> item = it.next();
            GridItem gridItem = item.getValue();

            if (gridItem.isExportable) {
                if (gridItem.component instanceof JTextComponent) {
                    ((JTextComponent) gridItem.component).setText("");
                }
            }
        }
    }

    private void setFieldData(String[] textLines) {
        clearFieldData();

        for (String line : textLines) {
            String[] values = line.split("::");

            if (values.length == 2) {
                GridItem gridItem = componentMap.get(values[0]);

                if (gridItem.isExportable && gridItem.component instanceof JTextComponent) {
                    JTextComponent field = ((JTextComponent) gridItem.component);
                    field.setText(values[1]);
                    field.setCaretPosition(0);
                }
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame(APP_TITLE);

                f.setContentPane(new GetInput(APP_WIDTH, APP_HEIGHT));
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
				f.setResizable(false);
            }
        });
    }
}