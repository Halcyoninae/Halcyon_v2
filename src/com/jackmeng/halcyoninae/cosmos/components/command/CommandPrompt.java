package com.jackmeng.halcyoninae.cosmos.components.command;

import javax.swing.*;

import com.jackmeng.halcyoninae.cosmos.inheritable.NavFilterText;
import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.utils.ColorTool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.awt.*;

public class CommandPrompt extends JFrame implements Runnable {

  static final int WIDTH = 690, HEIGHT = 450;

  private final String HEADER = "<html><body style=\"background:" + ColorTool.rgbTohex(ColorManager.ONE_DARK_BG)
      + ";color:" + ColorManager.MAIN_FG_STR + "\"><pre><code>", FOOTER = "</code></pre></body></html>";
  private StringBuilder buffer;
  private JEditorPane commandOut;
  private volatile String prompt;
  private JTextField commandIn;
  private transient Map<String, Method> invokables;

  public CommandPrompt() {
    super("Halcyon ~ exoad (CLI)");
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    prompt = "$ ";

    buffer = new StringBuilder(
        "<strong>Halcyon - Command Line</strong><br>Copyright (C) Jack Meng 2021<br>Type \"help\" for more info.<br><br>");

    JPanel contentLayout = new JPanel();
    contentLayout.setPreferredSize(getPreferredSize());
    contentLayout.setLayout(new BorderLayout());
    contentLayout.setOpaque(false);
    contentLayout.setBorder(BorderFactory.createEmptyBorder());

    invokables = new HashMap<>();

    commandIn = new JTextField();
    commandIn.setAutoscrolls(false);
    commandIn.setBackground(Color.BLACK);
    commandIn.setForeground(ColorManager.MAIN_FG_THEME);
    commandIn.setSelectedTextColor(ColorManager.BORDER_THEME);
    commandIn.setPreferredSize(new Dimension(getPreferredSize().width, 35));
    commandIn.setMinimumSize(commandIn.getPreferredSize());
    commandIn.setText(prompt);
    commandIn.addActionListener(x -> invoke__());
    commandIn.setNavigationFilter(new NavFilterText(prompt.length(), commandIn));

    commandOut = new JEditorPane();
    commandOut.setContentType("text/html");
    commandOut.setBorder(BorderFactory.createEmptyBorder());
    commandOut.setText(get_str__());
    commandOut
        .setPreferredSize(new Dimension(getPreferredSize().width,
            getPreferredSize().height - commandIn.getPreferredSize().height));
    commandOut.setEditable(false);
    commandOut.setOpaque(false);

    JScrollPane jsp = new JScrollPane(commandOut);
    jsp.setPreferredSize(commandOut.getPreferredSize());
    jsp.getViewport().setPreferredSize(commandOut.getPreferredSize());

    contentLayout.add(commandIn, BorderLayout.SOUTH);
    contentLayout.add(jsp, BorderLayout.NORTH);

    getContentPane().add(contentLayout);
  }

  private String get_str__() {
    return HEADER + buffer.toString() + FOOTER;
  }

  private void invoke__() {
    String cmd = prompt_zero_cmd__();
    Object[] args = null;
    if (cmd.contains(" ")) {
      cmd = prompt_zero_cmd__().split(" ")[0];
      args = new Object[prompt_zero_cmd__().split(" ").length - 1];
      for (int i = 0; i < args.length; i++) {
        args[i] = prompt_zero_cmd__().split(" ")[i + 1];
      }
    }
    println(prompt + cmd);
    if (invokables.get(cmd) == null) {
      print("Failed to find the command:<br>" + cmd);
    } else {
      try {
        if (!invokables.get(cmd).getReturnType().equals(Void.class)) {
          println(invokables.get(cmd).invoke(null, args == null ? (Object[]) null : null).toString());
        }
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    commandIn.setText(prompt);
  }

  private String prompt_zero_cmd__() {
    return commandIn.getText().substring(prompt.length() - 1, commandIn.getText().length());
  }

  public void clearBuffer() {
    buffer = new StringBuilder("");
    commandOut.setText(get_str__());
    commandOut.revalidate();
  }

  public void println(Object... e) {
    print(e, "<br>");
  }

  public void print(Object... e) {
    for (Object ex : e) {
      buffer.append(ex + "<br>");
    }
    commandOut.setText(get_str__());
    commandOut.revalidate();
  }

  public void addInvokable(Method... e) {
    for (Method x : e) {
      invokables.put(x.getName(), x);
    }
  }

  public void prompt(String str) {
    this.prompt = str;
    commandIn.setText(prompt);
    commandIn.setNavigationFilter(new NavFilterText(prompt.length(), commandIn));
    commandIn.repaint();
    commandIn.revalidate();
  }

  @Override
  public void run() {
    pack();
    setVisible(true);
  }

  public static String print_hello() {
    return "hello";
  }

  public static void main(String... args) {
    CommandPrompt cp = new CommandPrompt();
    try {
      cp.addInvokable(CommandPrompt.class.getMethod("print_hello"));
    } catch (NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    }
    cp.run();
  }
}
