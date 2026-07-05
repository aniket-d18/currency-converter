package converter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ResourceBundle;

/**
 * A simple "About" dialog that displays application info,
 * version number, and author details.
 * Uses the Singleton pattern to prevent multiple instances.
 * 
 * @author Aniket Chaudhari
 */
public class AboutDialog extends JFrame {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("localization.translation");
    private static AboutDialog instance = null;

    /**
     * Private constructor — use getInstance() to access.
     */
    private AboutDialog() {
        setTitle(BUNDLE.getString("AboutWindow.this.title"));
        setBounds(100, 100, 347, 260);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(null);
        setContentPane(contentPanel);

        // Application title
        JLabel titleLabel = new JLabel("Currency Converter");
        titleLabel.setBounds(65, 12, 219, 33);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        contentPanel.add(titleLabel);

        // Version info
        JLabel versionLabel = new JLabel("Version 1.0");
        versionLabel.setBounds(65, 45, 219, 33);
        versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(versionLabel);

        // License info
        JLabel licenseLabel = new JLabel("Licence MIT");
        licenseLabel.setBounds(65, 77, 219, 33);
        licenseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(licenseLabel);

        // Author name
        JLabel authorLabel = new JLabel(BUNDLE.getString("AboutWindow.lblAuthor.text"));
        authorLabel.setBounds(65, 122, 219, 33);
        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(authorLabel);

        // Clickable GitHub link
        JLabel githubLink = new JLabel("github.com/aniket-d18");
        githubLink.setBounds(65, 159, 219, 33);
        githubLink.setHorizontalAlignment(SwingConstants.CENTER);
        githubLink.setForeground(Color.BLUE);
        githubLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        githubLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/aniket-d18"));
                } catch (Exception ex) {
                    System.err.println("Could not open browser: " + ex.getMessage());
                }
            }
        });
        contentPanel.add(githubLink);
    }

    /**
     * Returns the single instance of this dialog, creating it if needed.
     */
    public static AboutDialog getInstance() {
        if (instance == null) {
            instance = new AboutDialog();
        }
        return instance;
    }
}
