/*
 *  Halcyon ~ exoad:
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https: //raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
 */

package com.jackmeng.locale;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * A localized tag adapted during
 * version 3.4.1 to increase transparency
 * of Localized classes meant either partially
 * or purely for the GUI wrapper.
 *
 * A localized functionality means
 * it's highly unsafe or broken to use
 * this function outside of the GUI wrapper
 * build routine.
 *
 * Notice: It is best for this annotation
 * to be used within the internals of the
 * Halcyon source code and not be
 * used else in order to not confuse any
 * other contributors.
 *
 * @author Jack Meng
 * @since 3.4.1
 */
@Retention(value = RetentionPolicy.SOURCE)
@Documented
public @interface Localized {

  /**
   * Stability is a factor for a third party
   * programmer to decide to either risk
   * it and use this localized component
   * or not.
   *
   * Stability of true represents there
   * is a partial support for delocalized
   * components.
   *
   * Stability of false represents there
   * is a HIGHLY unlikely chance it will
   * work.
   *
   * @return (true || false) for stability factor
   */
  boolean stability() default false;
}
