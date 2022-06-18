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