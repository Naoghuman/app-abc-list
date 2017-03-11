/*
 * Copyright (C) 2017 Naoghuman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.naoghuman.abclist.view.components;

/**
 *
 * @author Naoghuman
 */
public enum ESign {
    
    A('a'), B('b'), C('c'), D('d'), E('e'),
    F('f'), G('g'), H('h'), I('i'), J('j'),
    K('k'), L('l'), M('m'), N('n'), O('o'),
    P('p'), Q('q'), R('r'), S('s'), T('t'),
    U('u'), V('v'), W('w'), X('x'), Y('y'),
    Z('z');
        
    private final char sign;
    
    ESign(char sign) {
        this.sign = sign;
    }
    
    public char getSign() {
        return sign;
    }
    
    public boolean isSign(char sign) {
        return this.sign == sign;
    }
    
}
