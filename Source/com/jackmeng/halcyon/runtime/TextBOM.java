package com.jackmeng.halcyon.runtime;

public interface TextBOM {
  final byte[] UTF8_BOM = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}; // UTF 8 Byte Order Mark
  final byte[] UTF16_LE_BOM = {(byte) 0xFF, (byte) 0xFE}; // UTF 16 Little Endian Byte Order Mark
  final byte[] UTF16_BE_BOM = {(byte) 0xFE, (byte) 0xFF}; // UTF 16 Big Endian Byte Order Mark
}
