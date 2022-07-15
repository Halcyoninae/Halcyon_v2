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

#pragma once

#ifndef HALCYON_HPP

#define HALCYON_HPP

/**
 * @brief Just a header that defines some random BS
 * that is required for the main program to check before
 * enabling a runtime linker.
 */

const char * HALCYON_RSC_FOLDER = "/bin/halcyon";

namespace halcyon {
  /**
   * @brief A class that is used to check if the program is running on a
   * Win32 distribution.
   */
  class win32 {
  public:
    /**
     * @brief A function that is used to check if the program is running on a
     * Win32 distribution.
     * @return A boolean value that is true if the program is running on a
     * Win32 distribution.
     */
    static bool is_win32() {
      return (GetVersion() & 0x80000000) == 0;
    }
  };
}

#endif