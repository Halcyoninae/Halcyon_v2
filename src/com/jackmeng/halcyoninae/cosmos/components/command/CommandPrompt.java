package com.jackmeng.halcyoninae.cosmos.components.command;

import com.jackmeng.halcyoninae.cosmos.inheritable.NavFilterText;
import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import com.jackmeng.halcyoninae.halcyon.global.Pair;
import com.jackmeng.halcyoninae.halcyon.utils.ColorTool;
import com.jackmeng.halcyoninae.halcyon.utils.TimeParser;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class CommandPrompt extends JFrame implements Runnable {

  static final int WIDTH = 690, HEIGHT = 450;

  private final String HEADER = "<html><body style=\"background:" + ColorTool.rgbTohex(ColorManager.ONE_DARK_BG)
      + ";color:" + ColorManager.MAIN_FG_STR + "\"><pre>", FOOTER = "</pre></body></html>";
  private StringBuilder buffer;
  private JEditorPane commandOut;
  private volatile String prompt;
  private JTextField commandIn;
  private transient Map<String, Pair<Object, Method>> invokables;

  public CommandPrompt() {
    super("Halcyon ~ exoad (CLI)");
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    prompt = "$ ";

    buffer = new StringBuilder(
        "<strong>Halcyon - Command Line</strong><br>Copyright (C) Jack Meng (exoad) 2021<br><br>Type \"help\" for more info.<br>--------------<br>");

    invokables = new HashMap<>();

    commandIn = new JTextField();
    commandIn.setAutoscrolls(false);
    commandIn.setBackground(Color.BLACK);
    commandIn.setForeground(ColorManager.MAIN_FG_THEME);
    commandIn.setSelectedTextColor(ColorManager.BORDER_THEME);
    commandIn.setPreferredSize(new Dimension(getPreferredSize().width, 42));
    commandIn.setMinimumSize(commandIn.getPreferredSize());
    commandIn.setText(prompt);
    commandIn.addActionListener(x -> invoke__());
    commandIn.setNavigationFilter(new NavFilterText(prompt.length(), commandIn));

    commandOut = new JEditorPane();
    commandOut.setContentType("text/html");
    commandOut.setText(get_str__());
    commandOut.setAutoscrolls(true);
    commandOut.setPreferredSize(new Dimension(commandIn.getPreferredSize().width,
        getPreferredSize().height - commandIn.getPreferredSize().height));
    commandOut.setEditable(false);
    commandOut.setOpaque(false);

    JScrollPane jsp = new JScrollPane(commandOut);
    jsp.setPreferredSize(commandOut.getPreferredScrollableViewportSize());

    JSplitPane jspl = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jsp, commandIn);
    jspl.setPreferredSize(getPreferredSize());
    jspl.setDividerLocation(commandOut.getPreferredSize().height - commandIn.getPreferredSize().height);

    getContentPane().add(jspl);

  }

  public static String arrayStr(Object... e) {
    StringBuilder sb = new StringBuilder();
    for (Object t : e) {
      sb.append(t + " ");
    }
    return sb.toString();
  }

  private String get_str__() {
    return HEADER + buffer.toString() + FOOTER;
  }

  private static String[] purge__(String[] arr, String ele) {
    ArrayList<String> newArgs = new ArrayList<>(arr.length);
    for (String s : arr) {
      if (!s.equals(ele)) {
        newArgs.add(s);
      }
    }
    return newArgs.toArray(new String[0]);
  }

  private void invoke__() {
    StringTokenizer st = new StringTokenizer(prompt_zero_cmd__());
    String cmd = st.nextToken();
    print("<strong>" + (prompt.isBlank() ? "> " : prompt)
        + wrap(prompt_zero_cmd__(), null, ColorTool.hexToRGBA("#e6ac00")) + "</strong>");
    ArrayList<String> sb = new ArrayList<>();
    while (st.hasMoreTokens()) {
      sb.add(st.nextToken());
    }
    String[] argTokens = sb.toArray(new String[0]);
    argTokens = purge__(argTokens, "");
    Debugger.warn(cmd + " | " + Arrays.toString(argTokens));
    if (invokables.get(cmd) == null) {
      print(wrap("<em>Failed to find the command:</em><br>&emsp;", null, Color.RED) + cmd
          + "<br><em>With arguments (length:" + argTokens.length + "):</em> " + Arrays.toString(argTokens));
    } else {
      try {
        if (!invokables.get(cmd).second.getReturnType().equals(Void.TYPE)) {
          String str;
          if (invokables.get(cmd).second.getParameterCount() > 0) {
            str = (String) (invokables.get(cmd).second.invoke(invokables.get(cmd).first,
                (Object) (argTokens.length > 0 ? argTokens : null)));
          } else {
            str = (String) (invokables.get(cmd).second.invoke(invokables.get(cmd).first));
          }
          print(wrap(str, null, ColorTool.hexToRGBA("#99f3ff")));
        } else {
          invokables.get(cmd).second.invoke(invokables.get(cmd).first,
              argTokens.length > 0 ? null : argTokens);
        }
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        print(wrap("Error while invoking: <br>" + e.getMessage() + "<br>", Color.BLACK, Color.RED)
            + "<br>Command Arguments: " + cmd + " (" + argTokens.length + "):" + Arrays.toString(argTokens)
            + "<br><u>Diagnostics:</u><br>Required ArgTokens: " + invokables.get(cmd).second.getParameterCount()
            + "<br>Got Len: " + argTokens.length);
      } finally {
        commandIn.setText(prompt);
      }
    }
    commandIn.setText(prompt);
  }

  private String param_str__(Class<?>[] paramsDefs) {
    if (paramsDefs.length > 0) {
      StringBuilder sbt = new StringBuilder();
      for (Class<?> c : paramsDefs) {
        sbt.append("<" + c.getSimpleName() + "> ");
      }
      return sbt.toString();
    }
    return "No Arguments";
  }

  @Invokable()
  public String help() {
    StringBuilder sb = new StringBuilder();
    sb.append(wrap("<strong><u>Available Commands</u></strong><br>", null, ColorTool.hexToRGBA("#ff7039")));
    for (String key : invokables.keySet()) {
      sb.append(invokables.get(key).second.getName() + " | "
          + param_str__(invokables.get(key).second.getParameterTypes())).append("<br>");
    }
    return sb.toString();
  }

  private String prompt_zero_cmd__() {
    return commandIn.getText().substring(prompt.length(), commandIn.getText().length());
  }

  public static String wrap(String text, Color bg, Color fg) {
    return "<code style=\"background-color:" + (bg == null ? "" : ColorTool.rgbTohex(bg)) + ";color:"
        + (fg == null ? "" : ColorTool.rgbTohex(fg)) + "\">"
        + text + "</code>";
  }

  @Invokable(aliases = { "clear" })
  public void clearBuffer() {
    buffer = new StringBuilder("");
    commandOut.setText(get_str__());
  }

  public void print(Object... e) {
    for (Object ex : e) {
      buffer.append(wrap((ex == null ? "[NULL_CONT]" : ex) + "<br>", null, null));
    }
    commandOut.setText(get_str__());
  }

  public void addInvokable(Object instance, Class<?> invs) {
    for (Method s : invs.getDeclaredMethods()) {
      if (s.getAnnotation(Invokable.class) != null) {
        Invokable k = s.getAnnotation(Invokable.class);
        try {
          for (String al : k.aliases()) {
            invokables.put(al,
                new Pair<>((instance == null && Modifier.isStatic(s.getModifiers())
                    ? invs.getDeclaredConstructor().newInstance()
                    : instance), s));
          }
          invokables.put(s.getName(),
              new Pair<>(
                  (instance == null && Modifier.isStatic(s.getModifiers()) ? invs.getDeclaredConstructor().newInstance()
                      : instance),
                  s));
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
          print(wrap("Failed to load: " + invs.getSimpleName() + "<br><u>Diagnostics:</u><br>" + e.getMessage(),
              Color.BLACK, Color.RED));
          e.printStackTrace();
        }
        Debugger.crit("Added (@Invokable): " + s.getName() + " | Aliases: " + Arrays.toString(k.aliases()));
      }
    }
  }

  @Override
  public void run() {
    pack();
    setVisible(true);
  }

  // INTERNAL INVOKABLES
  public static String print_hello() {
    return "Hello there! :)";
  }

  public static String hello() {
    return print_hello();
  }

  @Invokable(aliases = { "time" })
  public static String print_time() {
    return TimeParser.fromRealMillis(System.currentTimeMillis());
  }

  public static String echo(String[] args) {
    return args == null ? "" : arrayStr((Object[]) args);
  }

  // END INTERNAL INVOKABLES

  public static void main(String... args) {
    CommandPrompt cp = new CommandPrompt();
    try {
      cp.addInvokable(cp, cp.getClass());
      cp.addInvokable(null, CommandPrompt.class);
    } catch (SecurityException e) {
      e.printStackTrace();
    }
    cp.run();
  }
}
