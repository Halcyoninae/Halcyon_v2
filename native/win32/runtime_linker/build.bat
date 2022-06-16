@rem Copyright: (C) 2022 name of Jack Meng
@rem Halcyon MP4J is music-playing software.
@rem This program is free software; you can redistribute it and/or
@rem modify it under the terms of the GNU General Public License
@rem as published by the Free Software Foundation; either version 2
@rem of the License, or (at your option) any later version.
@rem This program is distributed in the hope that it will be useful,
@rem but WITHOUT ANY WARRANTY; without even the implied warranty of
@rem MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
@rem GNU General Public License for more details.
@rem You should have received a copy of the GNU General Public License
@rem along with this program; If not, see <http://www.gnu.org/licenses/>.

@rem !INSTALL

g++ main.cpp win32.hpp -o linker.exe -std=c++17 -fsanitize=undefined -O2

linker.exe