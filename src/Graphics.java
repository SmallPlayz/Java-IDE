import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
public class Graphics {

    private static JTextArea console;
    private JFrame frame;
    private JTextArea text;
    private JButton newFile;
    private JButton openFile;
    private JButton compileFile;
    private JButton run;
    private JButton fileExplorer;
    private static JTextField newFileName;
    private final int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private final int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private File file;
    private static String fileDirectory;
    private JScrollPane textScrollPane;
    private JScrollPane consoleScrollPane;
    Graphics() {
        frame = new JFrame("Java IDE");
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLayout(null);

        text = new JTextArea();
        text.setBounds(15, 100, width-30, 900-100-75-225);
        text.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
        text.setForeground(new Color(255, 255, 255));
        text.setBackground(new Color(38, 38, 38));

        textScrollPane = new JScrollPane(text);
        textScrollPane.setBounds(15, 100, width-30, 900-100-75-225);
        textScrollPane.setPreferredSize(new Dimension(width-30, 900-100-75-225));
        text.getDocument().addDocumentListener(new DocumentSizeListener(textScrollPane));

        frame.add(textScrollPane);

        //frame.add(text);


        //text.setPreferredSize(new Dimension(width-30, 900-100-75-225));
        //JScrollPane scrollPanee = new JScrollPane(text);
        //scrollPanee.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //scrollPanee.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Add the JScrollPane to the JFrame
       // JPanel pp = new JPanel();
        //pp.add(scrollPanee);
        //pp.setBounds(15, 100, width-30, 900-100-75-225);
        //BoxLayout boxlayout = new BoxLayout(pp, BoxLayout.Y_AXIS);
        //pp.setLayout(boxlayout);

        console = new JTextArea();
        console.setBounds(15, 615, width-30, height-615-15-75); //210
        //console.setPreferredSize(new Dimension(width-30-15, 100));//height-615-15-75)
        // 900-615-15-75
        console.setForeground(new Color(255, 255, 255));
        console.setBackground(new Color(38, 38, 38));

        consoleScrollPane = new JScrollPane(console);
        consoleScrollPane.setBounds(15, 615, width-30, height-615-15-75);
        consoleScrollPane.setPreferredSize(new Dimension(width-30, height-615-15-75));
        console.getDocument().addDocumentListener(new DocumentSizeListener(consoleScrollPane));

        frame.add(consoleScrollPane);

        //JScrollPane scrollPane = new JScrollPane(console);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //JPanel p = new JPanel();
        //p.setBounds(15, 615, width-30, height-615-15-75);
        //BoxLayout boxlayoute = new BoxLayout(p, BoxLayout.Y_AXIS);
        //p.setLayout(boxlayoute);
        //p.add(scrollPane);
        //frame.add(console);

        newFile = new JButton("New");
        newFile.setBounds(15, 15, 100, 50);
        frame.add(newFile);

        openFile = new JButton("Open");
        openFile.setBounds(130, 15, 100, 50);
        frame.add(openFile);

        compileFile = new JButton("Compile");
        compileFile.setBounds(245, 15, 100, 50);
        frame.add(compileFile);

        run = new JButton("Run");
        run.setBounds(360, 15, 100, 50);
        frame.add(run);

        fileExplorer = new JButton("Explorer");
        fileExplorer.setBounds(475, 15, 100, 50);
        frame.add(fileExplorer);

        newFileName = new JTextField();
        newFileName.setBounds(15, 70, 100, 25);
        frame.add(newFileName);

        fileExplorer.addActionListener(e -> {
            if(!(file == null)) {
                try {
                    openDirectoryInWindows(fileDirectory);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        newFile.addActionListener(e -> {
            if (newFileName.getText().trim().length() > 0) {
                if (newFileName.getText().endsWith(".java"))
                    file = new File(newFileName.getText());
                else
                    file = new File(newFileName.getText() + ".java");
                fileDirectory = file.getAbsolutePath();
            }
        });

        openFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a Java file");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Java Files", "java"));
            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                fileDirectory = file.getAbsolutePath();
            }
        });

        compileFile.addActionListener(e -> {
            if(!(file == null)) {
                try {
                    runProcess("javac " + fileDirectory);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        run.addActionListener(e -> {
            /*
            try {
                file = new File("test.java");
                fileDirectory = file.getAbsolutePath();
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException ee) {
                System.out.println("An error occurred.");
                ee.printStackTrace();
            }
            */
            if(!(file == null)) {
                try {
                    FileWriter myWriter = new FileWriter(fileDirectory);
                    myWriter.write(text.getText());
                    myWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    //runProcess("cd C:\\Users\\10018879\\IdeaProjects\\cheese\\src\Test.java");
                    System.out.println("**********");
                    runProcess("javac " + fileDirectory);
                    System.out.println("**********");
                    runProcess("java " + fileDirectory);
                } catch (Exception eee) {
                    eee.printStackTrace();
                }
            }
        });

        frame.setVisible(true);

    }
    private static void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(cmd + " " + line);
            console.setText(console.getText() + "\n" + cmd + " " + line);
        }
    }

    private static void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
        pro.waitFor();
        System.out.println(command + " exitValue() " + pro.exitValue());
        console.setText(console.getText() + "\n" + command + " exitValue() " + pro.exitValue());
    }
    private static void openDirectoryInWindows(String directory) throws Exception {
        Runtime.getRuntime().exec("explorer " + directory);
    }
}

/*
JFileChooser chooser = new JFileChooser();
chooser.setCurrentDirectory(new java.io.File("."));
chooser.setDialogTitle("Choose a directory");
chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
chooser.setAcceptAllFileFilterUsed(false);

if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
  System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
  System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
} else {
  System.out.println("No Selection ");
}
 */