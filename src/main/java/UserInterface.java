import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.lang.Runnable;

public class UserInterface extends JFrame implements Runnable {

	private ArrayList<File> listFiles = new ArrayList<File>();
    private JTextField to;
    private JTextField from;
    private JButton sendMessage;
    private JButton openFileChooser;
    private JLabel result;
    private JFileChooser chooser;

    @Override
    public void run() {
        this.setTitle("EmailA");
        this.setPreferredSize(new Dimension(200, 200));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents();
        pack();
        setVisible(true);
        this.requestFocus();
    }
    private void createComponents() {
        GridLayout layout = new GridLayout(5,1);
        this.getContentPane().setLayout(layout);
        setDefaultComponentState();
        addListeners();
    }

    private void setDefaultComponentState() {
        from = new JTextField("from");
        to = new JTextField("to");
        from.addFocusListener(fListener(from, "", "from"));
        to.addFocusListener(fListener(to, "", "to"));
        
        sendMessage = new JButton("Send");
        result = new JLabel();
        openFileChooser = new JButton("FileChooser");
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        chooser.setMultiSelectionEnabled(true);

        this.getContentPane().add(from);
        this.getContentPane().add(to);
        this.getContentPane().add(openFileChooser);
        this.getContentPane().add(sendMessage);
        this.getContentPane().add(result);
    }

    private FocusListener fListener(JTextField l, String focus, String unfocus){
        FocusListener listener = new FocusListener(){
            @Override
            public void focusLost(FocusEvent e) {
                if (l.getText().isEmpty() || l.getText().equals(unfocus))l.setText(unfocus);
            }
            @Override
            public void focusGained(FocusEvent e) {
                if (!l.getText().isEmpty() || l.getText().equals(unfocus))l.setText(focus);
            }
        };
        return listener;
    }

    private JFrame getFrame(){
        return this;
    }
    private void tryToSendMessage(){
        if (!listFiles.isEmpty()){
            if (!from.getText().isEmpty() && !from.getText().equals("from") && !to.getText().isEmpty() && !to.getText().equals("to") ){
                Thread t1 = new Thread(){
                    public void run(){
                        boolean foo = uiLogic.sendMessage(to.getText(), from.getText(), listFiles);
                        if (foo) result.setText("successful");
                        else result.setText("failed");
                    }};
                t1.start();
                }
                else result.setText("invalid from/to input");
            }
        else result.setText("no files selected");
    }
    private void openFileChooser(){
        chooser.showOpenDialog(getFrame());
        File[] files = chooser.getSelectedFiles();
        if  (files.length!=0){
            listFiles = new ArrayList<File>(Arrays.asList(files));
        }
    }

    private void addListeners(){ // please dont look
        sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                tryToSendMessage();
            }
        });
        sendMessage.addKeyListener(new KeyListener(){

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    tryToSendMessage();
                }
            }
            
            @Override
            public void keyTyped(KeyEvent e) {
            }
        
            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });

        openFileChooser.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileChooser();
            }
        });
        openFileChooser.addKeyListener(new KeyListener(){ 

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    openFileChooser();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

        });
    }
}