/*
 *	VorbisConstants.java
 *
 *	This file is part of Tritonus: http://www.tritonus.org/
 */

/*
 *  Copyright (c) 2005 by Matthias Pfisterer
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

/*
|<---            this code is formatted to fit into 80 columns             --->|
*/

package org.tritonus.lowlevel.pvorbis;


/**
 * Constants, especially error codes from vorbis/codec.h.
 */
public interface VorbisConstants {
    int OV_FALSE = -1;
    int OV_EOF = -2;
    int OV_HOLE = -3;

    int OV_EREAD = -128;
    int OV_EFAULT = -129;
    int OV_EIMPL = -130;
    int OV_EINVAL = -131;
    int OV_ENOTVORBIS = -132;
    int OV_EBADHEADER = -133;
    int OV_EVERSION = -134;
    int OV_ENOTAUDIO = -135;
    int OV_EBADPACKET = -136;
    int OV_EBADLINK = -137;
    int OV_ENOSEEK = -138;
}


/*** VorbisConstants.java ***/
