package view;

import javax.swing.JFrame;

public class JoueurDialogManager {
    private final JFrame parent;

    public JoueurDialogManager(JFrame parent) {
        this.parent = parent;
    }

    public String[] openJoueurDialog(String[] currentData) {
        JoueurDialog dialog = new JoueurDialog(parent, currentData);
        dialog.setVisible(true);
        return dialog.getJoueurData();
    }
}