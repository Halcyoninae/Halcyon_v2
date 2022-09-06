/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly..
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

package com.jackmeng.halcyoninae.cosmos.components.command;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
/**
 * This annotation is used by the Command
 * Prompt to determine what methods to load
 * from a class instance automatically. Any
 * methods with this annotation will be
 * loaded and visible to the user for usage.
 *
 * @author Jack Meng
 * @since 3.3
 */
public @interface Invokable {

  /**
   * Aliases are additional commands
   * that point to this same method, but
   * with a different name. This functionality
   * should be avoided as much as possible
   * if there can be aliasing duplications.
   */
  String[] aliases() default {};

  /**
   * This string is primarily used to get
   * the details of a command in order to
   * display a "helpful" message about it.
   */
  String commandDescription() default "";
}
