/**
 @file main.cpp
 @author Jack Meng
 @brief This represents the compiled binary that is attached to run the JAR
 file on Win32 distributions of this program.
 @version 0.1
 @date 2022-06-15
 *
 @copyright Copyright (c) 2022
 *
 The generated binary from this native implementation should be attached both
 for a x32 and x64 version of the program.
 */

#include "win32.hpp"

const std::string LICENSE_PRINTABLE =
    "(C) 2022 name of Jack Meng\n Halcyon MP4J is music-playing software.\n "
    "This program is free software; you can redistribute it and/or\n "
    "modify it under the terms of the GNU General Public License\n as "
    "published by the Free Software Foundation; either version 2\n of the "
    "License, or (at your option) any later version.\n This program is "
    "distributed in the hope that it will be useful,\n but WITHOUT ANY "
    "WARRANTY; without even the implied warranty of\n MERCHANTABILITY or "
    "FITNESS FOR A PARTICULAR PURPOSE. See the\n GNU General Public License "
    "for more details.\n You should have received a copy of the GNU General "
    "Public License\n along with this program; If not, see "
    "<http://www.gnu.org/licenses/>.";

const std::string LINK_FAILURE =
    "The dynamic Runtime linker could not find the Halcyon.jar file.\nThis "
    "secondary executable is supposed to be located in the ./bin/ directory; "
    "however was not found.\nIn order to properly link, you must place the "
    "Halcyon.jar file in the ./bin/ directory.\n";

const std::string JRE_FAILURE =
    "The dynamic runtime linker did not find an installed JRE that can be used "
    "to run the secondary executable.\nPress \"TRY AGAIN\" to force "
    "load.\nPress \"CONTINUE\" to go to the JRE download page.\n";

inline void reconstructable() {
  std::ifstream file("./bin/Halcyon.jar");

  if (!file.good()) {
    printf(LINK_FAILURE.c_str());

    MessageBox(NULL, (LPCSTR)LINK_FAILURE.c_str(),
               (LPCSTR)"Halcyon Native Linker", MB_ICONWARNING | MB_OK);
    ShellExecute(NULL, (LPCSTR) "open", (LPCSTR) "explorer.exe", (LPCSTR) ".",
                 NULL, SW_SHOW);

   exit(1);
  } else {
    std::system("java -jar ./bin/Halcyon.jar");
  }
}

std::string exec(const char* cmd) {
  char buffer[128];
  std::string result = "";
  FILE* pipe = popen(cmd, "r");
  if (!pipe) throw std::runtime_error("popen() failed!");
  try {
    while (fgets(buffer, sizeof buffer, pipe) != NULL) {
      result += buffer;
    }
  } catch (...) {
    pclose(pipe);
    throw;
  }
  pclose(pipe);
  return result;
}

int main(int argc, char** argv) {
  printf("%s", LICENSE_PRINTABLE.c_str());

  // check if java is installed
  if (exec("which javacxzcxzcxzcx").length() == 0) {
    int id = MessageBox(NULL, (LPCSTR)JRE_FAILURE.c_str(), (LPCSTR)"JRE Missing",
                        MB_CANCELTRYCONTINUE | MB_ICONWARNING);
    if(id == IDCANCEL) {
      return 0;
    } else if(id == IDTRYAGAIN) {
      reconstructable();
    } else if(id == IDOK) {
      ShellExecute(NULL, "open", "https://www.java.com/en/download/", NULL, NULL,
                   SW_SHOW);
    }
    return 1;
  }

  reconstructable();
  return 0;
}