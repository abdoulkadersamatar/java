package view;

import controller.EquipeController;
import controller.JoueurController;
import model.dao.DAOEquipe;
import model.dao.IDAOEquipe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame {

    private String currentUser = null; // Sert à suivre le statut de l'utilisateur connecté
    private final JFrame frame; // Fenêtre principale
    private final JList<String> equipeList;
    private final EquipeController equipeController;
    private final JoueurController joueurController;
    private final EquipeDialogManager equipeDialogManager;
    private final JoueurDialogManager joueurDialogManager;

    public MainFrame() {
        // Initialisation de la fenêtre principale
        frame = new JFrame("Gestion des équipes et joueurs de basket");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 800);
        frame.setMinimumSize(new Dimension(1400, 400));
        frame.setLayout(new BorderLayout());

        // ================================
        // Menu de navigation et statut utilisateur
        // ================================
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Fichier");
        JMenuItem importItem = new JMenuItem("Importer");
        JMenuItem exportItem = new JMenuItem("Exporter");
        fileMenu.add(importItem);
        fileMenu.add(exportItem);

        JMenu sessionMenu = new JMenu("Session");
        JMenuItem loginItem = new JMenuItem("Se connecter");
        JMenuItem logoutItem = new JMenuItem("Se déconnecter");
        logoutItem.setEnabled(false); // "Déconnecter" désactivé par défaut
        sessionMenu.add(loginItem);
        sessionMenu.add(logoutItem);

        menuBar.add(fileMenu);
        menuBar.add(sessionMenu);

        JLabel userInfoLabel = new JLabel("Non connecté");
        userInfoLabel.setForeground(Color.BLUE);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(userInfoLabel);

        // Actions pour la connexion
        loginItem.addActionListener(e -> {
            ConnexionFrame connexionFrame = new ConnexionFrame(frame);
            connexionFrame.setVisible(true);

            String loggedInUser = connexionFrame.getLoggedInUser();
            if (loggedInUser != null) {
                currentUser = loggedInUser;
                userInfoLabel.setText("Connecté en tant que : " + currentUser);
                userInfoLabel.setForeground(Color.GREEN);
                loginItem.setEnabled(false);
                logoutItem.setEnabled(true);
            }
        });

        logoutItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Êtes-vous sûr de vouloir vous déconnecter ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                currentUser = null;
                userInfoLabel.setText("Non connecté");
                userInfoLabel.setForeground(Color.BLUE);
                loginItem.setEnabled(true);
                logoutItem.setEnabled(false);
                JOptionPane.showMessageDialog(frame, "Vous êtes déconnecté.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // ================================
        // Panneaux des équipes et joueurs
        // ================================
        // Modèle pour les équipes
        DefaultListModel<String> equipeListModel = new DefaultListModel<>();
        equipeList = new JList<>(equipeListModel);
        equipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane equipeScrollPane = new JScrollPane(equipeList);
        JButton ajouterEquipeBtn = new JButton("Ajouter équipe");
        JButton modifierEquipeBtn = new JButton("Modifier équipe");
        JButton supprimerEquipeBtn = new JButton("Supprimer équipe");

        JPanel equipeButtonPanel = new JPanel(new FlowLayout());
        equipeButtonPanel.add(ajouterEquipeBtn);
        equipeButtonPanel.add(modifierEquipeBtn);
        equipeButtonPanel.add(supprimerEquipeBtn);

        JPanel equipePanel = new JPanel(new BorderLayout());
        equipePanel.setBorder(BorderFactory.createTitledBorder("Équipes"));
        equipePanel.add(equipeScrollPane, BorderLayout.CENTER);
        equipePanel.add(equipeButtonPanel, BorderLayout.SOUTH);

        // Modèle pour les joueurs
        String[] colonnes = {"ID", "Nom", "Prénom", "Date de naissance", "Taille", "Poids", "Poste", "Numero", "Année de rejoint"};
        DefaultTableModel joueurTableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre les cellules non éditables
            }
        };
        JTable joueurTable = new JTable(joueurTableModel);
        joueurTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane joueurScrollPane = new JScrollPane(joueurTable);
        JButton ajouterJoueurBtn = new JButton("Ajouter joueur");
        JButton modifierJoueurBtn = new JButton("Modifier joueur");
        JButton supprimerJoueurBtn = new JButton("Supprimer joueur");

        JPanel joueurButtonPanel = new JPanel(new FlowLayout());
        joueurButtonPanel.add(ajouterJoueurBtn);
        joueurButtonPanel.add(modifierJoueurBtn);
        joueurButtonPanel.add(supprimerJoueurBtn);

        JPanel joueurPanel = new JPanel(new BorderLayout());
        joueurPanel.setBorder(BorderFactory.createTitledBorder("Joueurs"));
        joueurPanel.add(joueurScrollPane, BorderLayout.CENTER);
        joueurPanel.add(joueurButtonPanel, BorderLayout.SOUTH);

        // Panel sud contenant un "bouton suivant"
        JButton suivantButton = new JButton("Suivant");
        suivantButton.setBackground(Color.LIGHT_GRAY);
        suivantButton.setForeground(Color.BLACK);
        suivantButton.setPreferredSize(new Dimension(120, 40));

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        southPanel.add(suivantButton);

        frame.add(equipePanel, BorderLayout.WEST);
        frame.add(joueurPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        // ================================
        // Création des DAO et contrôleurs
        // ================================
        IDAOEquipe daoEquipe = new DAOEquipe();
        equipeDialogManager = new EquipeDialogManager(frame);
        joueurDialogManager = new JoueurDialogManager(frame);

        // Instanciation des contrôleurs avec passage de la MainFrame
        equipeController = new EquipeController(daoEquipe, equipeListModel, this);
        joueurController = new JoueurController(joueurTableModel, this);

        // Connexions des actions liées aux équipes
        equipeController.handleAddEquipeAction(ajouterEquipeBtn);
        equipeController.handleEditEquipeAction(modifierEquipeBtn, equipeList);
        equipeController.handleDeleteEquipeAction(supprimerEquipeBtn, equipeList);

        // Connexions des actions liées aux joueurs
        ajouterJoueurBtn.addActionListener(e -> joueurController.addJoueur());
        modifierJoueurBtn.addActionListener(e -> joueurController.editJoueur(joueurTable.getSelectedRow()));
        supprimerJoueurBtn.addActionListener(e -> joueurController.deleteJoueur(joueurTable.getSelectedRow()));

        // Ajout d'un écouteur pour afficher les joueurs de l'équipe sélectionnée
        equipeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Éviter les événements multiples
                int selectedIndex = equipeList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    int equipeId = equipeController.getEquipeIdByIndex(selectedIndex); // Récupérer l'ID de l'équipe
                    joueurController.loadJoueursByEquipe(equipeId); // Charger les joueurs de l'équipe
                } else {
                    joueurController.loadJoueursByEquipe(-1); // Aucun joueur si aucune équipe sélectionnée
                }
            }
        });

        // Charger les données initiales
        equipeController.loadEquipes();

        frame.setVisible(true);
    }

    public int getSelectedEquipeIndex() {
        return equipeList.getSelectedIndex();
    }

    public int getEquipeIdByIndex(int index) {
        return equipeController.getEquipeIdByIndex(index);
    }

    public EquipeController getEquipeController() {
        return equipeController;
    }

    public String[] openEquipeDialog(String[] currentData) {
        return equipeDialogManager.openEquipeDialog(currentData);
    }

    public String[] openJoueurDialog(String[] currentData) {
        return joueurDialogManager.openJoueurDialog(currentData);
    }

    public void showMessageDialog(String message, int messageType) {
        JOptionPane.showMessageDialog(frame, message, "Message", messageType);
    }

    public boolean showConfirmationDialog(String message) {
        int result = JOptionPane.showConfirmDialog(frame, message, "Confirmation", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}