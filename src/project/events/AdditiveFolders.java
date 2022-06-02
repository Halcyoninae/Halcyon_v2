package project.events;

import project.components.dialog.SelectApplicableFolders;
import project.components.tabs.FileView;

public class AdditiveFolders {
  private SelectApplicableFolders saf;
  private FileView fv;

  public AdditiveFolders(SelectApplicableFolders saf, FileView fv) {
    this.saf = saf;
    this.fv = fv;
  }
}
