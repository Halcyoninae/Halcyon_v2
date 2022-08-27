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

package com.jackmeng.halcyoninae.halcyon.global;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * An ArrayList extension that uses a WeakReference
 * management
 *
 * @author Jack Meng
 * @since 3.3
 * @see java.lang.ref.WeakReference
 * @see java.lang.ref.ReferenceQueue
 */
public class WeakList<T> extends ArrayList<WeakReference<T>> {
    private static final long serialVersionUID = 2L;
    private transient ReferenceQueue<T> q = new ReferenceQueue<>();

    public WeakList() {
        super();
    }

    public synchronized void kick() {
        Reference<? extends T> r;
        while((r = q.poll()) != null) {
            remove(r);
        }
    }

    @Override
    public boolean equals(Object o) {
        kick();
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        kick();
        return super.hashCode();
    }

    @Override
    public void trimToSize() {
        kick();
        super.trimToSize();
    }

    @Override
    public Object[] toArray() {
        kick();
        return super.toArray();
    }

    @Override
    public boolean contains(Object o) {
        kick();
        return super.contains(o);
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsAll(Collection<?> cc) {
        kick();
        return super.containsAll(cc);
    }

    @Override
    public int indexOf(Object o) {
        kick();
        return super.indexOf(o);
    }

    @Override
    public <X>X[] toArray(X[] a) {
        kick();
        return super.toArray(a);
    }

    @Override
    public int lastIndexOf(Object o) {
        kick();
        return super.lastIndexOf(o);
    }

    @Override
    public ListIterator<WeakReference<T>> listIterator() {
        kick();
        return super.listIterator();
    }

    @Override
    public Iterator<WeakReference<T>> iterator() {
        kick();
        return super.iterator();
    }

    public int length() {
        return this.size();
    }

    @Override
    public int size() {
        kick();
        return super.size();
    }
}
