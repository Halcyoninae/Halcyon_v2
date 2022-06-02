package project.events;

import project.components.FileView;
import project.components.dialog.SelectApplicableFolders;

public class AdditiveFolders {
  private SelectApplicableFolders saf;
  private FileView fv;

  public AdditiveFolders(SelectApplicableFolders saf, FileView fv) {
    this.saf = saf;
    this.fv = fv;
  }
}
