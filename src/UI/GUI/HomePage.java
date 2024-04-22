package UI.GUI;

// ##### 简介 #####
// AI交流图形界面
// 通过输入语句和AI进行交流
// 可以实时观测到日志以及AI思考内容

// ##### 日志 #####
// 1.0 版本
// 初步完成
// done

import AI.Mind.General.Debug;
import AI.Mind.MindController;
import UI.Translator.Composer;
import UI.Translator.Splitter;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class HomePage {

    // ##### 变量 #####
    // 当前版本
    private String version = "1.0.0v";
    // 主体框架
    private JFrame frame;
    // 日志窗口
    private JTextPane logWindow;
    // 聊天窗口
    private JTextArea chatWindow;
    // 思考窗口
    private JTextArea thinkWindow;
    // 输入窗口
    private JTextField inputWindow;
    // AI语言编译器
    private Splitter splitter;
    // AI语言转译器
    private Composer composer;
    // AI思考终端
    private MindController mind;
    // 报错标记
    private boolean isError;
    // 开始标记
    private boolean start;
    // 是否包含颜色
    private boolean color;
    private boolean print;
    // 颜色编号
    private int colorNum;
    // 临时文本储存
    private StringBuilder temp_string;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 启动器 #####
    // 启动程序
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HomePage window = new HomePage();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // ##### 初始化 #####
    // 主页
    public HomePage() {
        this.init();
        setFrame();
    }

    // 初始化变量
    public void init() {
        temp_string = new StringBuilder();
        splitter = new Splitter();
        composer = new Composer();
        isError = false;
        start = false;
        color = false;
        print = true;
        colorNum = 0;
    }

    // ##### 显示内容 #####
    // 设置主体界面
    private void setFrame() {
        // 窗口
        frame = new JFrame();
        frame.setBounds(100, 100, 1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // 界面标题
        JLabel titleLabel = new JLabel("AI");
        titleLabel.setFont(new Font("Calibri", Font.PLAIN, 24));
        titleLabel.setBounds(605, 10, 25, 30);
        frame.getContentPane().add(titleLabel);

        // 思考界面标题
        JLabel thinkPanelLabel = new JLabel("AI Thinking");
        thinkPanelLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        thinkPanelLabel.setBounds(20, 35, 85, 23);
        frame.getContentPane().add(thinkPanelLabel);

        // 思考频率标题
        JLabel thinkRatePanelLabel = new JLabel("Think rate: " + 0 + " /sec");
        thinkRatePanelLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        thinkRatePanelLabel.setBounds(450, 35, 150, 23);
        frame.getContentPane().add(thinkRatePanelLabel);

        // 记录界面标题
        JLabel logPanelLabel = new JLabel("AI Log");
        logPanelLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        logPanelLabel.setBounds(20, 395, 65, 23);
        frame.getContentPane().add(logPanelLabel);

        // AI状态标题
        JLabel statusPanelLabel = new JLabel("AI Status: Stop");
        statusPanelLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        statusPanelLabel.setBounds(450, 395, 150, 23);
        frame.getContentPane().add(statusPanelLabel);

        // 聊天界面
        JLabel chatPanelLabel = new JLabel("AI Chat");
        chatPanelLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        chatPanelLabel.setBounds(634, 35, 55, 23);
        frame.getContentPane().add(chatPanelLabel);

        // 版本信息
        JLabel versionLabel = new JLabel(version + " By Yan");
        versionLabel.setFont(new Font("Calibri", Font.PLAIN, 12));
        versionLabel.setBounds(20, 10, 85, 17);
        frame.getContentPane().add(versionLabel);

        // 思考板块
        JPanel think_panel = new JPanel();
        think_panel.setBounds(10, 50, 603, 340);
        frame.getContentPane().add(think_panel);
        think_panel.setLayout(null);

        // 记录板块
        JPanel log_panel = new JPanel();
        log_panel.setBounds(10, 411, 603, 340);
        frame.getContentPane().add(log_panel);
        log_panel.setLayout(null);

        // 聊天板块
        JPanel chat_panel = new JPanel();
        chat_panel.setBounds(623, 50, 551, 701);
        frame.getContentPane().add(chat_panel);
        chat_panel.setLayout(null);

        // 思考窗口
        thinkWindow = new JTextArea();
        thinkWindow.setEditable(false);
        thinkWindow.setFont(new Font("Lucida Console", Font.PLAIN, 12));
        thinkWindow.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
        thinkWindow.setBounds(10, 10, 583, 320);
        thinkWindow.setText("\n");
        JScrollPane thinkScrollWindow = new JScrollPane(thinkWindow);
        thinkScrollWindow.setBounds(10, 10, 583, 320);
        thinkScrollWindow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        think_panel.add(thinkScrollWindow);

        // 信息窗口
        logWindow = new JTextPane();
        logWindow.setFont(new Font("Lucida Console", Font.PLAIN, 12));
        PrintStream out = new PrintStream(new OutputStream() {
            @Override
            public void write(int input_num) {
                char temp_char = (char) input_num;
                if (temp_char == '$') {
                    color = true;
                    return;
                }
                if (temp_char == '\n') {
                    if (print && !temp_string.isEmpty()) {
                        switch (colorNum) {
                            case (1) -> setColor(logWindow, String.valueOf(temp_string), Color.RED);
                            case (2) -> setColor(logWindow, String.valueOf(temp_string), Color.GREEN);
                            case (3) -> setColor(logWindow, String.valueOf(temp_string), Color.BLUE);
                            case (4) -> setColor(logWindow, String.valueOf(temp_string), Color.ORANGE);
                            case (5) -> setColor(logWindow, String.valueOf(temp_string), Color.BLACK);
                        }
                        setColor(logWindow, "\n", Color.BLACK);
                        temp_string.setLength(0);
                        colorNum = 0;
                    }
                    return;
                }
                if (color) {
                    switch (temp_char) {
                        case ('R') -> {
                            colorNum = 1;
                            isError = true;
                        }
                        case ('G') -> colorNum = 2;
                        case ('B') -> colorNum = 3;
                        case ('Y') -> colorNum = 4;
                        case ('N') -> colorNum = 5;
                    }
                    color = false;
                    return;
                }
                temp_string.append(temp_char);
            }
        });
        System.setOut(out);
        // error remover
        PrintStream err = new PrintStream(new OutputStream() {
            @Override
            public void write(int input_num) {}});
        System.setErr(err);
        // commend before debug
        logWindow.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
        logWindow.setBounds(10, 10, 583, 320);
        JScrollPane logScrollWindow = new JScrollPane(logWindow);
        logScrollWindow.setBounds(10, 10, 583, 320);
        logScrollWindow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        log_panel.add(logScrollWindow);


        // 聊天显示窗口
        chatWindow = new JTextArea();
        chatWindow.setEditable(false);
        chatWindow.setFont(new Font("Lucida Console", Font.PLAIN, 12));
        chatWindow.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
        chatWindow.setBounds(10, 10, 531, 507);
        chatWindow.setText("\n");
        JScrollPane chatScrollWindow = new JScrollPane(chatWindow);
        chatScrollWindow.setBounds(10, 10, 531, 603);
        chatScrollWindow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chat_panel.add(chatScrollWindow);

        // 输入窗口
        inputWindow = new JTextField();
        inputWindow.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (start) {
                        enter();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Need Start AI First", "Warning",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        inputWindow.setFont(new Font("Lucida Console", Font.PLAIN, 12));
        inputWindow.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
        inputWindow.setBounds(10, 623, 531, 35);
        chat_panel.add(inputWindow);
        inputWindow.setColumns(10);
        inputWindow.setText(" what is yan?");

        // 启动按钮
        JButton start_button = new JButton("Weak Up");
        start_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!start) {
                    isError = false;
                    start = true;
                    print = true;
                    startAI(statusPanelLabel, thinkRatePanelLabel);
                }
                else {
                    JOptionPane.showMessageDialog(null, "AI Already Start", "Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        start_button.setFont(new Font("Arial", Font.PLAIN, 12));
        start_button.setBounds(875, 10, 93, 23);
        frame.getContentPane().add(start_button);

        // 重置按钮
        JButton reset_button = new JButton("Reset");
        reset_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!start) {
                    print = true;
                    debug.title("################ RESET AI ################");
                    mind = new MindController();
                    mind.doReset();
                    mind.doSleep();
                    mind.interrupt();
                    debug.title("################ RESET AI ################");
                    print = false;
                }
                else {
                    JOptionPane.showMessageDialog(null, "Need Stop AI First", "Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        reset_button.setFont(new Font("Arial", Font.PLAIN, 12));
        reset_button.setBounds(1081, 10, 93, 23);
        frame.getContentPane().add(reset_button);

        // 休眠按钮
        JButton sleep_button = new JButton("Sleep");
        sleep_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (start) {
                    start = false;
                    mind.doSleep();
                    mind.interrupt();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Need Start AI First", "Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        sleep_button.setFont(new Font("Arial", Font.PLAIN, 12));
        sleep_button.setBounds(978, 10, 93, 23);
        frame.getContentPane().add(sleep_button);

        // 增强按钮
        JButton enhance_button = new JButton("Enhance");
        enhance_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (start) {
                    mind.enhance();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Need Start AI First", "Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        enhance_button.setFont(new Font("Arial", Font.PLAIN, 12));
        enhance_button.setBounds(10, 668, 93, 23);
        chat_panel.add(enhance_button);

        // 抑制按钮
        JButton restrain_button = new JButton("Restrain");
        restrain_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (start) {
                    mind.restrain();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Need Start AI First", "Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        restrain_button.setFont(new Font("Arial", Font.PLAIN, 12));
        restrain_button.setBounds(113, 668, 93, 23);
        chat_panel.add(restrain_button);

        // 发送按钮
        JButton send_button = new JButton("Send");
        send_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (start) {
                    enter();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Need Start AI First", "Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        send_button.setFont(new Font("Arial", Font.PLAIN, 12));
        send_button.setBounds(448, 668, 93, 23);
        chat_panel.add(send_button);
    }

    // ##### 功能函数 #####
    // 发送输入栏内容
    public void enter() {
        String input_text = inputWindow.getText().trim();
        inputWindow.setText(" ");
        if (!input_text.isEmpty()) {
            try {
                ArrayList<String> input_listen = splitter.getSplit(input_text);
                if (input_listen.size() > 1) {
                    chatWindow.append(" User:\n" + "  " + composer.getCompose(input_listen) + "\n\n");
                    chatWindow.setCaretPosition(chatWindow.getDocument().getLength());
                    mind.addListen(input_listen);
                }
            }
            catch (Exception e) {
                debug.fail("#ChatPanel           <Typing>       -WrongEnter");
            }
        }
    }

    // 独立线程运行AI
    public void startAI(JLabel statusPanelLabel, JLabel thinkRatePanelLabel) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    statusPanelLabel.setText("AI Status: Running");
                    debug.title("################ START AI ################");
                    mind = new MindController();
                    showRate(thinkRatePanelLabel);
                    while (mind.isAlive() && start) {
                        mind.doThink();
                        if (mind.getSay() != null) {
                            chatWindow.append(" AI:\n" + "  " + composer.getCompose(mind.getSay()) + "\n\n");
                            chatWindow.setCaretPosition(chatWindow.getDocument().getLength());
                        }
                        if (mind.getThink() != null) {
                            thinkWindow.append(" Thinking: " + composer.getCompose(mind.getThink()) + "\n\n");
                            thinkWindow.setCaretPosition(thinkWindow.getDocument().getLength());
                        }
                    }
                    Thread.sleep(1000);
                    statusPanelLabel.setText("AI Status: Stop");
                }
                catch (Exception e) {
                    debug.error(e);
                    statusPanelLabel.setText("AI Status: ERROR");
                    debug.title("################# ERROR! #################");
                }
                debug.title("################# END AI #################");
                start = false;
                print = false;
            }
        }).start();
    }

    // 显示刷新率
    public void showRate(JLabel thinkRatePanelLabel) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (mind.isAlive()) {
                        Thread.sleep(1000);
                        thinkRatePanelLabel.setText("Think rate: " + mind.getThinkRate() + " /sec");
                        if (isError) {
                            start = false;
                        }
                    }
                    thinkRatePanelLabel.setText("Think rate: " + 0 + " /sec");
                } catch (Exception e) {
                    debug.error(e);
                    start = false;
                }
            }
        }).start();
    }

    // 设置字体颜色
    private void setColor(JTextPane input_pane, String input_text, Color input_color) {
        try {
            input_pane.setCharacterAttributes(StyleContext.getDefaultStyleContext().addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, input_color), false);
            input_pane.setCaretPosition(input_pane.getDocument().getLength());
            input_pane.replaceSelection(input_text);
        }
        catch (Exception e) {

        }
    }
}
