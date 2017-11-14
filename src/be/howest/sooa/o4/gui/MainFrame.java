package be.howest.sooa.o4.gui;

import be.howest.sooa.o4.domain.Genre;
import be.howest.sooa.o4.domain.Movie;
import be.howest.sooa.o4.data.GenreRepository;
import be.howest.sooa.o4.data.MovieRepository;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author hayk
 */
public class MainFrame extends javax.swing.JFrame {

    private final transient MovieRepository movieRepo = new MovieRepository();
    private final transient GenreRepository genreRepo = new GenreRepository();
    private transient Movie selectedMovie;

    /**
     * Creates new form MainForm
     */
    public MainFrame() {
        initComponents();
        addActionListeners();
        fillGenres();
    }

    private void addActionListeners() {
        addGeneralButtonActionListeners();
        addEditGenreButtonActionListener();
        addAddGenreButtonActionListener();
        addAddMovieButtonActionListener();
        addEditMovieButtonActionListener();
        addDeleteMovieButtonActionListener();
        addListActionListeners();
    }

    private void addListActionListeners() {
        genresList.addItemListener(new GenreItemListener(this));
        moviesList.addListSelectionListener(
                new MovieListSelectionListener(this));
    }

    private void addGeneralButtonActionListeners() {
        exitButton.addActionListener((ActionEvent e) -> {
            setVisible(false);
            dispose();
        });
    }

    public void addAddGenreButtonActionListener() {
        addGenreButton.addActionListener((ActionEvent e) -> {
            GenreDialog genreDialog = new GenreDialog(MainFrame.this, true, this);
            genreDialog.setTitle("Add Genre");
            centerScreen(genreDialog);
            genreDialog.setVisible(true);
        });
    }

    public void addEditGenreButtonActionListener() {
        editGenreButton.addActionListener((ActionEvent e) -> {
            GenreDialog genreDialog = new GenreDialog(MainFrame.this, true,
                    this, getSelectedGenre());
            genreDialog.setTitle("Edit Genre");
            centerScreen(genreDialog);
            genreDialog.setVisible(true);
        });
    }

    private Genre getSelectedGenre() {
        return (Genre) genresList.getModel().getSelectedItem();
    }

    private void addAddMovieButtonActionListener() {
        addMovieButton.addActionListener((ActionEvent e) -> {
            MovieDialog movieDialog = new MovieDialog(MainFrame.this, true,
                    this, genresList.getModel());
            movieDialog.setTitle("Add Movie");
            centerScreen(movieDialog);
            movieDialog.setVisible(true);
        });
    }

    private void addEditMovieButtonActionListener() {
        editMovieButton.addActionListener((ActionEvent e) -> {
            MovieDialog movieDialog = new MovieDialog(MainFrame.this, true,
                    this, genresList.getModel(), selectedMovie);
            movieDialog.setTitle("Edit Movie");
            centerScreen(movieDialog);
            movieDialog.setVisible(true);
        });
    }

    private void addDeleteMovieButtonActionListener() {
        deleteMovieButton.addActionListener((ActionEvent e) -> {
            Object[] options = {"Do not delete", "Delete Movie"};
            int result = JOptionPane.showOptionDialog(this,
                    "Please, apply you want to delete the following movie:\n"
                    + selectedMovie,
                    "Delete movie?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (result == 1) {
                movieRepo.delete(selectedMovie);
                fillMovies(selectedMovie.getGenre());
            }
        });
    }

    public void centerScreen() {
        centerScreen(this);
    }

    private void centerScreen(Window window) {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - window.getWidth()) / 2;
        final int y = (screenSize.height - window.getHeight()) / 2;
        window.setLocation(x, y);
    }

    private void fillGenres() {
        DefaultComboBoxModel<Genre> model = new DefaultComboBoxModel<>();
        model.addElement(null);
        genreRepo.findAll().forEach(genre -> {
            model.addElement(genre);
        });
        genresList.setModel(model);
    }

    private void fillMovies(Genre genre) {
        DefaultListModel<Movie> model = new DefaultListModel<>();
        movieRepo.findByGenre(genre).forEach(movie -> {
            model.addElement(movie);
        });
        moviesList.setModel(model);
    }

    private void clearMovies() {
        DefaultListModel<Movie> model = (DefaultListModel) moviesList.getModel();
        model.removeAllElements();
        editMovieButton.setEnabled(false);
    }

    public void saveMovie(Movie movie) {
        movieRepo.save(movie);
        fillMovies(movie.getGenre());
    }

    public void updateMovie(Movie movie) {
        movieRepo.update(movie);
        fillMovies(movie.getGenre());
    }

    public void saveGenre(Genre genre) {
        genreRepo.save(genre);
        fillGenres();
        selectGenre(genre);
    }

    public void updateGenre(Genre genre) {
        genreRepo.update(genre);
        fillGenres();
        selectGenre(genre);
    }

    public void deleteGenre(Genre genre) {
        genreRepo.delete(genre);
        fillGenres();
        editGenreButton.setEnabled(false);
    }

    private void selectGenre(Genre genre) {
        genresList.setSelectedItem(genre);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelH1 = new javax.swing.JLabel();
        genresList = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        moviesList = new javax.swing.JList<>();
        addMovieButton = new javax.swing.JButton();
        editMovieButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        editGenreButton = new javax.swing.JButton();
        deleteMovieButton = new javax.swing.JButton();
        addGenreButton = new javax.swing.JButton();
        menuBarMain = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemExit = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Movie Management System");
        setResizable(false);

        labelH1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelH1.setText("Movie Management System");

        jScrollPane1.setViewportView(moviesList);

        addMovieButton.setText("Add Movie...");
        addMovieButton.setName(""); // NOI18N

        editMovieButton.setText("Edit Movie...");
        editMovieButton.setEnabled(false);

        exitButton.setText("Exit");

        editGenreButton.setText("Edit...");
        editGenreButton.setActionCommand("Edit...");
        editGenreButton.setEnabled(false);

        deleteMovieButton.setText("Delete Movie");
        deleteMovieButton.setEnabled(false);
        deleteMovieButton.setName(""); // NOI18N

        addGenreButton.setText("Add...");

        menuFile.setText("File");

        menuItemExit.setText("Exit");
        menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitActionPerformed(evt);
            }
        });
        menuFile.add(menuItemExit);

        menuBarMain.add(menuFile);

        menuHelp.setText("Edit");

        menuItemAbout.setText("About");
        menuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuItemAbout);

        menuBarMain.add(menuHelp);

        setJMenuBar(menuBarMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addMovieButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editMovieButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteMovieButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(exitButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelH1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(genresList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editGenreButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addGenreButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelH1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genresList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editGenreButton)
                    .addComponent(addGenreButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addMovieButton)
                    .addComponent(editMovieButton)
                    .addComponent(exitButton)
                    .addComponent(deleteMovieButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_menuItemExitActionPerformed

    private void menuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAboutActionPerformed
        JOptionPane.showMessageDialog(this, "Movie management system (c) 2018 Hayk AVETISYAN");
    }//GEN-LAST:event_menuItemAboutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.out.println(ex.getMessage());
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addGenreButton;
    private javax.swing.JButton addMovieButton;
    private javax.swing.JButton deleteMovieButton;
    private javax.swing.JButton editGenreButton;
    private javax.swing.JButton editMovieButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JComboBox<Genre> genresList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelH1;
    private javax.swing.JMenuBar menuBarMain;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemAbout;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JList<Movie> moviesList;
    // End of variables declaration//GEN-END:variables

    private static class MovieListSelectionListener implements ListSelectionListener {

        MainFrame frame;

        MovieListSelectionListener(MainFrame frame) {
            this.frame = frame;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            boolean isAdjusting = e.getValueIsAdjusting();
            if (isAdjusting) {
                int index = e.getLastIndex();
                boolean enabled = index >= 0;
                if (enabled) {
                    frame.selectedMovie
                            = frame.moviesList.getModel().getElementAt(index);
                    frame.selectedMovie.setGenre(
                            (Genre) frame.genresList.getSelectedItem());
                } else {
                    frame.selectedMovie = null;
                }

                frame.editMovieButton.setEnabled(enabled);
                frame.deleteMovieButton.setEnabled(enabled);
            }
        }
    }

    private static class GenreItemListener implements ItemListener {

        MainFrame frame;

        GenreItemListener(MainFrame frame) {
            this.frame = frame;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            boolean itemSelected = e.getStateChange() == ItemEvent.SELECTED;
            if (itemSelected) {
                Genre selected = (Genre) e.getItem();
                frame.fillMovies(selected);
            } else {
                frame.clearMovies();
            }
            frame.editGenreButton.setEnabled(itemSelected);

        }

    }
}
