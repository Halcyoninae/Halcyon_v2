/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @file main.cpp
 * @author Jack Meng
 * @brief This represents the compiled binary that is attached to run the JAR
 * file on NIX distributions of this program.
 * @version 0.1
 * @date 2022-06-15
 *
 * @copyright Copyright (c) 2022
 *
 * The generated binary from this native implementation should be attached both
 * for a x32 and x64 version of the program.
 */

#include "nix.hpp"

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

  if (exec("which java").length() == 0) {
    printf(JRE_FAILURE.c_str() "\nWould you like to install via SNAP?");
    if (std::getchar() == 'y' && exec("which snap".length() != 0)) {
          std::system("sudo snap install openjdk");
    } else {
      printf("\nPlease install Java Runtime Environment (JRE) and try again.\n");
      exit(1);
    }
    return 1;
  }

  reconstructable();
  return 0;
}