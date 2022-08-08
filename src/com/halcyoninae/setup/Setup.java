package com.halcyoninae.setup;

import com.halcyoninae.cloudspin.enums.SpeedStyle;
import com.halcyoninae.cloudspin.lib.gradient.GradientGenerator;
import com.halcyoninae.cosmos.components.dialog.ErrorWindow;
import com.halcyoninae.halcyon.constant.ColorManager;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;
import com.halcyoninae.halcyon.constant.ProgramResourceManager;
import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.halcyon.utils.ColorTool;
import com.halcyoninae.halcyon.utils.DeImage;
import com.halcyoninae.halcyon.utils.DeImage.Directional;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class runs a setup routine everytime
 * the main program is run. The setup routine helps
 * to make sure certain things are setup before the main
 * program is run; for example: making sure the correct
 * assets are present etc.. It also helps to setup
 * the program to a first time user.
 * <p>
 * To run this class, simply call the static main() instead
 * of creating a new object. Creating a new object will
 * only instantiate the GUI part and not the file access part.
 *
 * @author Jack Meng
 * @since 3.3
 */
public class Setup extends JFrame implements Runnable {
    public static final File MAIN_LOCK_USER_SETUP = new File(
            ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + ProgramResourceManager.FILE_SLASH
                    + ProgramResourceManager.RESOURCE_SUBFOLDERS[2]
                    + ProgramResourceManager.FILE_SLASH + "__user.lock");
    public static final String KILL_ARG = "kill";
    private static final transient java.util.List<SetupListener> listener = new ArrayList<>(); // must specify java.util cuz
    public static boolean SETUP_EXISTS = false;
    private final JPanel content;
    // Java.AWT is stupid and its own
    // List class??!?!??!!?
    private final JLabel welcomeLabel;

    public Setup() {
        setTitle("Setup Routine");
        setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        setPreferredSize(new Dimension(350, 350));
        setResizable(false);

        content = new JPanel();
        content.setPreferredSize(getPreferredSize());
        content.setOpaque(false);
        content.setLayout(new GridLayout(3, 0));

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new OverlayLayout(welcomePanel));
        welcomePanel.setPreferredSize(new Dimension(350, 100));

        JPanel welcomePanelBack = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(
                        AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER,
                                0.5f));
                g2.drawImage(DeImage.createGradient(GradientGenerator.make(SpeedStyle.QUALITY, welcomePanel.getPreferredSize(),
                        ColorTool.rndColor(), ColorTool.rndColor(), false), 200, 0, Directional.TOP), 0, 0, null);
                g2.dispose();
            }
        };
        welcomePanelBack.setPreferredSize(welcomePanel.getPreferredSize());
        welcomePanel.setOpaque(true);
        welcomePanel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent arg0) {
                welcomePanelBack.repaint(150);
                welcomePanel.repaint(20);
                Runtime.getRuntime().gc();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent arg0) {
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent arg0) {
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent arg0) {
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent arg0) {
            }

        });

        welcomeLabel = new JLabel("<html><p style=\"font-size:18px;color:" + ColorManager.MAIN_FG_STR
                + ";\"><strong>Welcome :3</strong></p><p style=\"font-size:10px;color:#a6a6a6;\">Let's get started</p></html>");
        welcomeLabel.setPreferredSize(welcomePanel.getPreferredSize());
        welcomeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        welcomePanel.add(welcomeLabel);
        welcomePanel.add(welcomePanelBack);

        JPanel buttonsCtrl = new JPanel();
        buttonsCtrl.setPreferredSize(welcomePanel.getPreferredSize()); // should leave us with 200px to work with after this
        buttonsCtrl.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));

        JButton okButton = new JButton("Moosic!");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireListeners();
                dispose();
            }
        });

        JButton cancel = new JButton("Quit");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Debugger.warn("Failing setup... [0]");
                System.exit(0);
            }
        });

        buttonsCtrl.add(okButton);
        buttonsCtrl.add(cancel);

        JScrollPane contentPanel = new JScrollPane();
        contentPanel.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        contentPanel.setPreferredSize(new Dimension(350, getPreferredSize().height - (welcomePanel.getPreferredSize().height + buttonsCtrl.getPreferredSize().height)));
        contentPanel.getViewport().setPreferredSize(contentPanel.getPreferredSize());

        JLabel themeLabel = new JLabel("Preferred Theme (dark | light):");


        content.add(welcomePanel);
        content.add(contentPanel);
        content.add(buttonsCtrl);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        getContentPane().add(content);
    }

    private static void fireListeners() {
        listener.forEach(x -> x.updateStatus(SETUP_EXISTS ? SetupStatus.PASSED : SetupStatus.NEW));
    }

    public static void addSetupListener(SetupListener... listeners) {
        for (SetupListener e : listeners) {
            listener.add(e);
        }
    }

    public static void main(String... args) {

        if (args != null && args.length > 0) {
            if (args[0].equals(KILL_ARG) && MAIN_LOCK_USER_SETUP.isFile() && MAIN_LOCK_USER_SETUP.exists()) {
                MAIN_LOCK_USER_SETUP.delete();
            }
        }
        if (MAIN_LOCK_USER_SETUP.isFile() && MAIN_LOCK_USER_SETUP.exists()) {
            SETUP_EXISTS = true;
            Debugger.info("Found a setup to be already run. Not deploying GUI");
            fireListeners();
        } else {
            SETUP_EXISTS = false;
            Debugger.info("Failed to find a lock. Deploying GUI");
            Setup s = new Setup();
            s.run();
            s.dispatchSetupYay();
        }
    }

    /**
     * Is called upon to send a notice to the file service
     * writer that we need to place the setup lock file signifying
     * that the user has performed a setup
     */
    public void dispatchSetupYay() {
        if (!MAIN_LOCK_USER_SETUP.exists() || !MAIN_LOCK_USER_SETUP.isFile()) {
            try {
                MAIN_LOCK_USER_SETUP.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try (PrintWriter bos = new PrintWriter(MAIN_LOCK_USER_SETUP)) {
                bos.write("Hello! This is just a file to keep track of whether you have setup the program or not :)");
                bos.flush();
            } catch (IOException e) {
                e.printStackTrace();
                new ErrorWindow(e.getMessage()).run();
            }
        }
    }

    public void addContent(JComponent... components) {
        for (JComponent c : components) {
            content.add(c);
            content.revalidate();
        }
    }

    @Override
    public void run() {
        pack();
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getPreferredSize().width) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - this.getPreferredSize().height) / 2);
        setVisible(true);
        Debugger.warn("Running setup routine...");
    }
}