package com.jackmeng.app.constant;

import com.jackmeng.app.components.bottompane.BottomPane;
import com.jackmeng.app.components.toppane.layout.ButtonControlTP;
import com.jackmeng.app.components.toppane.layout.InfoViewTP;
import com.jackmeng.app.connections.resource.ResourceDistributor;
import com.jackmeng.audio.Player;

/**
 * This class holds any public scoped Objects that may be used throughout
 * the program.
 *
 * This class eliminates different classes having to hot potato pass
 * difference object instances to each other.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class Global {

  private Global() {}

  public static BottomPane bp = new BottomPane();
  public static final ResourceDistributor rd = new ResourceDistributor();
  public static ButtonControlTP bctp = new ButtonControlTP();
  public static InfoViewTP ifp = new InfoViewTP();
  public static Player player = new Player();
}
