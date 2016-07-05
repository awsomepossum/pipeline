import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

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
*/

public class GetInput extends JPanel {
    private static final long serialVersionUID = 6555747177061710030L;
    private static final String APP_TITLE = "Get Input";
    private static final int APP_WIDTH = 1;
    private static final int APP_HEIGHT = 1;

	public JTextField inputDir_Text = new JTextField("C:\\");
	public JTextField outputDir_Text = new JTextField("C:\\");
	public JTextField numOfThreads_Text = new JTextField("12");
	public JTextField amountOfRam_Text = new JTextField("64");
	public JTextField AB_SCIEX_MS_ConverterDir_Text = new JTextField("C:\\Program Files (x86)\\AB SCIEX\\MS Data Converter\\AB_SCIEX_MS_Converter.exe");
	public JTextField msconvertDir_Text = new JTextField("C:\\Program Files\\ProteoWizard\\ProteoWizard 3.0.8708\\msconvert.exe");
	public JTextField indexmzXMLDir_Text = new JTextField("C:\\indexmzXML.exe");
	public JTextField DIA_Umpire_SE_Jar_Dir_Text = new JTextField("C:\\DIAUmpire_v1_4254-\\DIA_Umpire_SE.jar");
	public JTextField fastaDir_Text = new JTextField("EX:      C:\\...\\20150427_mouse_sprot.cc.fasta");
	public JTextField ppm_Text = new JTextField("25ppm");
	public JTextField enzyme_Text = new JTextField("5");
	public JTextField modsDir_Text = new JTextField("EX:       C:\\...\\Mods_acetyl.txt");
	public JTextField paramsXMLDir_Text = new JTextField("EX:      C:\\...\\tandem_Ksuc_params.xml");
	public JTextField tandemDir_Text = new JTextField("C:\\Inetpub\\tpp-bin\\tandem.exe");
	public JTextField tandem2xmlDir_Text = new JTextField("C:\\Inetpub\\tpp-bin\\Tandem2XML.exe");
	
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

        this.constraints.ipadx = 10;
        this.constraints.ipady = 10;
        this.constraints.insets = new Insets(8, 4, 8, 4);

        //JLabel itemLabel, descriptionLabel, artistLabel, albumLabel, priceLabel;
        //JTextField itemCode, description, artist, album, price;
        //JButton addButton,editButton, deleteButton;

        this.constraints.anchor = GridBagConstraints.LAST_LINE_END;

		componentMap.put("generalInfo", new GridItem(new JLabel("General Info"), false, 1, 0, 3, 1));
        this.constraints.fill = GridBagConstraints.HORIZONTAL;
        componentMap.put("inputDir", new GridItem(new JLabel("Enter input directory"), false, 0, 1));
        componentMap.put("inputDirText", new GridItem(inputDir_Text, true, 1, 1, 1, 1));
        componentMap.put("outputDir", new GridItem(new JLabel("Enter output directory"), false, 0, 2));
        componentMap.put("outputDirText", new GridItem(outputDir_Text, true, 1, 2, 1, 1));
        componentMap.put("numOfThreads", new GridItem(new JLabel("Enter number of threads"), false, 0, 3));
        componentMap.put("numOfThreadsText", new GridItem(numOfThreads_Text, true, 1, 3, 1, 1));
        componentMap.put("amountOfRam", new GridItem(new JLabel("Enter amount of ram"), false, 0, 4));
        componentMap.put("amountOfRamText", new GridItem(amountOfRam_Text, true, 1, 4, 1, 1));
		
		componentMap.put("diaUmpire_pipe_input", new GridItem(new JLabel("diaUmpire_pipe.py Info"), false, 1, 5, 3, 1));
        componentMap.put("AB_SCIEX_MS_Converter_Dir", new GridItem(new JLabel("<html>Enter the full path to<br>AB_SCIEX_MS_Converter.exe</html>"), false, 0, 6));
        componentMap.put("AB_SCIEX_MS_ConverterDirText", new GridItem(AB_SCIEX_MS_ConverterDir_Text, true, 1, 6, 1, 1));
        componentMap.put("msconvertDir", new GridItem(new JLabel("<html>Enter the full path to<br>msconvert.exe</html>"), false, 0, 7));
        componentMap.put("msconvertDirText", new GridItem(msconvertDir_Text, true, 1, 7, 1, 1));
        componentMap.put("indexmzXMLDir", new GridItem(new JLabel("<html>Enter the full path to<br>indexmzXML.exe</html>"), false, 0, 8));
        componentMap.put("indexmzXMLDirText", new GridItem(indexmzXMLDir_Text, true, 1, 8, 1, 1));
        componentMap.put("DIA_Umpire_SE_Jar_Dir", new GridItem(new JLabel("<html>Enter the full path to<br>DIA_Umpire_SE.jar</html>"), false, 0, 9));
        componentMap.put("DIA_Umpire_SE_Jar_DirText", new GridItem(DIA_Umpire_SE_Jar_Dir_Text, true, 1, 9, 1, 1));
		
		componentMap.put("MSGFSearch_input", new GridItem(new JLabel("MSGFSearch Info"), false, 5, 0, 3, 1));
        componentMap.put("fastaDir", new GridItem(new JLabel("<html>Enter the full path to<br>the .fasta file</html>"), false, 3, 1));
        componentMap.put("fastaDirText", new GridItem(fastaDir_Text, true, 4, 1, 20, 1));
		componentMap.put("ppm", new GridItem(new JLabel("Enter ppm"), false, 3, 2));
        componentMap.put("ppmText", new GridItem(ppm_Text, true, 4, 2, 20, 1));
		componentMap.put("enzyme", new GridItem(new JLabel("Enter enzyme (-e)"), false, 3, 3));
        componentMap.put("enzymeText", new GridItem(enzyme_Text, true, 4, 3, 20, 1));
		componentMap.put("modsDir", new GridItem(new JLabel("<html>Enter the full path to<br>the mods .txt file</html>"), false, 3, 4));
        componentMap.put("modsDirText", new GridItem(modsDir_Text, true, 4, 4, 20, 1));

		componentMap.put("XTandem_input", new GridItem(new JLabel("XTandem Info"), false, 5, 5, 3, 1));
        componentMap.put("paramsXMLDir", new GridItem(new JLabel("<html>Enter the full path to<br>the _params.xml file</html>"), false, 3, 6));
        componentMap.put("paramsXMLDirText", new GridItem(paramsXMLDir_Text, true, 4, 6, 20, 1));
        componentMap.put("tandemDir", new GridItem(new JLabel("<html>Enter the full path to<br>tandem.exe</html>"), false, 3, 7));
        componentMap.put("tandemDirText", new GridItem(tandemDir_Text, true, 4, 7, 20, 1));
        componentMap.put("tandem2xmlDir", new GridItem(new JLabel("<html>Enter the full path to<br>tandem2xml.exe</html>"), false, 3, 8));
        componentMap.put("tandem2xmlDirText", new GridItem(tandem2xmlDir_Text, true, 4, 8, 20, 1));
		/*
		ask where tandem.exe and tandem2xml.exe is. edit actionPerformed, defaultButton, checkIfFilesExist() grabFieldData()
		*/

        componentMap.put("doneButton", new GridItem(new JButton("Done"), false, 1, this.constraints.ipady));
        componentMap.put("defaultButton", new GridItem(new JButton("Default"), false, 2, this.constraints.ipady));
        componentMap.put("clearButton", new GridItem(new JButton("Clear"), false, 3, this.constraints.ipady));
		
        ((JButton) componentMap.get("doneButton").component).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				if( inputDir_Text.getText().equals("") || outputDir_Text.getText().equals("") || 
				    numOfThreads_Text.getText().equals("") || amountOfRam_Text.getText().equals("") || 
				    AB_SCIEX_MS_ConverterDir_Text.getText().equals("") || msconvertDir_Text.getText().equals("") || 
				    indexmzXMLDir_Text.getText().equals("") || DIA_Umpire_SE_Jar_Dir_Text.getText().equals("") || 
				    fastaDir_Text.getText().equals("") || ppm_Text.getText().equals("") ||
				    enzyme_Text.getText().equals("") || modsDir_Text.getText().equals("") ||
				    paramsXMLDir_Text.getText().equals("") || tandemDir_Text.getText().equals("") ||
				    tandem2xmlDir_Text.getText().equals("") ) {
					JOptionPane.showMessageDialog(null, "         Fill in all the fields");
				}
				else if(fastaDir_Text.getText().contains("EX:") || modsDir_Text.getText().contains("EX:") || paramsXMLDir_Text.getText().contains("EX:")) {
					JOptionPane.showMessageDialog(null, "     Fill in all fields correctly");
				}
				else if(checkIfFilesExist() == false){
				}
				else {
					System.out.print(grabFieldData());
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
	
	private boolean checkIfFilesExist() {
		boolean filesExist = true;
		StringBuffer dontExist = new StringBuffer();
		Path tempPath;
		
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
		
		if( !filesExist ) {
			JOptionPane.showMessageDialog(null, dontExist.toString());
		}

		return filesExist;
	}

    private String grabFieldData() {
		StringBuffer fieldData = new StringBuffer();
		
		fieldData.append("Keep this line\r\n");
		fieldData.append(inputDir_Text.getText()).append("\r\n");
		fieldData.append(outputDir_Text.getText()).append("\r\n");
		fieldData.append(numOfThreads_Text.getText()).append("\r\n");
		fieldData.append(amountOfRam_Text.getText()).append("\r\n");
		fieldData.append(AB_SCIEX_MS_ConverterDir_Text.getText()).append("\r\n");
		fieldData.append(msconvertDir_Text.getText()).append("\r\n");
		fieldData.append(indexmzXMLDir_Text.getText()).append("\r\n");
		fieldData.append(DIA_Umpire_SE_Jar_Dir_Text.getText()).append("\r\n");
		fieldData.append(fastaDir_Text.getText()).append("\r\n");
		fieldData.append(ppm_Text.getText()).append("\r\n");
		fieldData.append(enzyme_Text.getText()).append("\r\n");
		fieldData.append(modsDir_Text.getText()).append("\r\n");
		fieldData.append(paramsXMLDir_Text.getText()).append("\r\n");
		fieldData.append(tandemDir_Text.getText()).append("\r\n");
		fieldData.append(tandem2xmlDir_Text.getText()).append("\r\n");
		
		return fieldData.toString();
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
            }
        });
    }
}