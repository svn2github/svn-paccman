/*
 *
 *  Copyright (C)    2008 Joao F. (joaof at sourceforge.net)
 *                   http://paccman.sourceforge.net
 *
 *  This file is part of PAccMan.
 *
 *  PAccMan is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  PAccMan is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with PAccMan.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.paccman.ui.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 *
 * @author joaof (joaof at sourceforge.net)
 */
public class AmountFormat extends Format {

    private final DecimalFormat decimalFormat;

    private final DecimalFormat  woSymbolFormat;
    
    private final boolean showSign;

    private final boolean showCurrencySymbol;

    public AmountFormat(boolean showSign, boolean showCurrencySymbol) {
        this.showSign = showSign;
        this.showCurrencySymbol = showCurrencySymbol;
        decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance();
        woSymbolFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        woSymbolFormat.setMaximumFractionDigits(decimalFormat.getMaximumFractionDigits());
        woSymbolFormat.setMinimumFractionDigits(decimalFormat.getMinimumFractionDigits());
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        assert obj instanceof BigDecimal;
        BigDecimal amount = (BigDecimal) obj;
        if (!showSign && amount.signum() < 0) {
            amount = amount.negate();
        }
        DecimalFormat df = showCurrencySymbol ? decimalFormat : woSymbolFormat;
        return df.format(amount, toAppendTo, pos);
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return decimalFormat.parse(source, pos);
    }

}
