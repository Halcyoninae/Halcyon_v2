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

#pragma GCC diagnostic ignored "-Wunused-variable"

#ifndef __DEFINITIONS_HPP

#define __DEFINITIONS_HPP

#define byte unsigned char

namespace packer_definitions {
class definitions {
 public:
  definitions(const char* locale);
  ~definitions();
  byte* raw_data();
  // varargs
  const char* parseable_base64(const char * data, size_t * out_len);
  const char* unparse_base64(const char * data, size_t * out_len);
};
};  // namespace packer_definitions

#endif