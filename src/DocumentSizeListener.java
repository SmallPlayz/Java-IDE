import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

class DocumentSizeListener implements DocumentListener {
    private JScrollPane scrollPane;
    public DocumentSizeListener(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }
    public void changedUpdate(DocumentEvent e) {
        update();
    }
    public void removeUpdate(DocumentEvent e) {
        update();
    }
    public void insertUpdate(DocumentEvent e) {
        update();
    }
    public void update() {
        scrollPane.setPreferredSize(new Dimension(480, scrollPane.getViewport().getView().getHeight()));
    }
}