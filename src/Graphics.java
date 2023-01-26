import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Graphics {

    private static JTextArea console;
    Graphics() {
        JFrame frame = new JFrame("das IDE ist so cool, richtig?");
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLayout(null);

        JTextArea text = new JTextArea();
        text.setBounds(15, 100, 1600-30, 900-100-75-225);
        frame.add(text);

        console = new JTextArea();
        console.setBounds(15, 615, 1600-30, 210);
        //console.setBackground(Color.black);
        frame.add(console);

        JButton run = new JButton("Run");
        run.setBounds(15, 15, 100, 50);
        frame.add(run);
        run.addActionListener(e -> {
            try {
                File myObj = new File("test.java");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException ee) {
                System.out.println("An error occurred.");
                ee.printStackTrace();
            }
            try {
                FileWriter myWriter = new FileWriter("test.java");
                myWriter.write(text.getText());
                myWriter.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            try {
                //runProcess("cd C:\\Users\\10018879\\IdeaProjects\\cheese\\src\Test.java");
                System.out.println("**********");
                runProcess("javac C:\\Users\\10018879\\IdeaProjects\\IDE\\Test.java");
                System.out.println("**********");
                runProcess("java C:\\Users\\10018879\\IdeaProjects\\IDE\\Test.java");
            } catch (Exception eee) {
                eee.printStackTrace();
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
    }
}
