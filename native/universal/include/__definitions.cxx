#include "__definitions.hxx"

#ifndef _GLIBCXX_FSTREAM
#include <fstream>
#endif

#include <cstdlib>
#include <cstring>
#include <string>
#include <vector>

const char dictionary[] = {
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

namespace packer_definitions {
class definitions {
 protected:
  const char* def_locale;
  std::vector<byte*> def_data;
  int* mod_table;

 public:
  const static int16_t serialUID = 1L;
  definitions(const char* locale) {
    this->def_locale = locale;
    this->mod_table = new int[3];
    this->mod_table[0] = 0;
    this->mod_table[1] = 2;
    this->mod_table[2] = 1;
  }
  byte* raw_data() {}
  const char* parseable_base64(const char* data, size_t* out_len) {
    *out_len = 4 * ((strlen(data) + 2) / 3);
    char* out = new char[*out_len];
    int i = 0, j = 0;
    while (data[i] != '\0') {
      int32_t octet_a = data[i] == '\r'   ? 0
                        : data[i] == '\n' ? 0
                        : data[i] == '='  ? 0
                                          : data[i];
      int32_t octet_b = data[i + 1] == '\r'   ? 0
                        : data[i + 1] == '\n' ? 0
                        : data[i + 1] == '='  ? 0
                                              : data[i + 1];
      int32_t octet_c = data[i + 2] == '\r'   ? 0
                        : data[i + 2] == '\n' ? 0
                        : data[i + 2] == '='  ? 0
                                              : data[i + 2];
      int32_t triple = (octet_a << 16) + (octet_b << 8) + octet_c;
      for (int k = 0; k < 4; k++) {
        out[j++] = this->mod_table[triple >> 18] << 4 |
                   this->mod_table[triple >> 12 & 0x3F];
        triple <<= 6;
      }
      i += 3;
    }
    return out;
  }
  const char* unparse_base64(const char* data, size_t* out_len) {
    if (strlen(data) % 4 != 0) {
      return nullptr;
    }

    *out_len = strlen(data) / 4 * 3;
    char* out = new char[*out_len];
    int i = 0, j = 0;
    while (data[i] != '\0') {
      int32_t octet_a = data[i] == '\r'   ? 0
                        : data[i] == '\n' ? 0
                        : data[i] == '='  ? 0
                                          : data[i];
      int32_t octet_b = data[i + 1] == '\r'   ? 0
                        : data[i + 1] == '\n' ? 0
                        : data[i + 1] == '='  ? 0
                                              : data[i + 1];
      int32_t octet_c = data[i + 2] == '\r'   ? 0
                        : data[i + 2] == '\n' ? 0
                        : data[i + 2] == '='  ? 0
                                              : data[i + 2];
      int32_t triple = (octet_a << 18) + (octet_b << 12) + (octet_c << 6);
      for (int k = 0; k < 3; k++) {
        out[j++] = triple >> 16;
        triple <<= 8;
      }
      i += 4;
    }
    return out;
  }
};
};  // namespace packer_definitions