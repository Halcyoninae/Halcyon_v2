/*
 *	Line.java
 *
 *	This file is part of Tritonus: http://www.tritonus.org/
 */

/*
 *  Copyright (c) 1999 by Matthias Pfisterer
 *
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
 *
 */

/*
|<---            this code is formatted to fit into 80 columns             --->|
*/

package javax.sound.sampled;


public interface Line {
    Line.Info getLineInfo();


    void open()
            throws LineUnavailableException;


    void close();


    boolean isOpen();


    Control[] getControls();


    boolean isControlSupported(Control.Type controlType);


    Control getControl(Control.Type controlType);


    void addLineListener(LineListener listener);


    void removeLineListener(LineListener listener);


    class Info {
        private final Class m_lineClass;


        public Info(Class lineClass) {
            m_lineClass = lineClass;
        }


        public Class getLineClass() {
            return m_lineClass;
        }


        public boolean matches(Line.Info info) {
            return this.getLineClass() == info.getLineClass();
        }


        public String toString() {
            return super.toString() + "[lineClass=" + getLineClass() + "]";
        }
    }
}


/*** Line.java ***/
