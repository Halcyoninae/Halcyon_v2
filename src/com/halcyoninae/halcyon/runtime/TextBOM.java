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
package com.halcyoninae.halcyon.runtime;

/**
 * Represents file encoding BOMs
 *
 * @author Jack Meng
 * @since 3.2
 */
public interface TextBOM {
  final byte[] UTF8_BOM = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}; // UTF 8 Byte Order Mark
  final byte[] UTF16_LE_BOM = {(byte) 0xFF, (byte) 0xFE}; // UTF 16 Little Endian Byte Order Mark
  final byte[] UTF16_BE_BOM = {(byte) 0xFE, (byte) 0xFF}; // UTF 16 Big Endian Byte Order Mark
}
