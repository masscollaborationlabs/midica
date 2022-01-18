/*
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.midica.ui.renderer;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JTable;

import org.midica.config.Dict;
import org.midica.config.Laf;
import org.midica.file.read.SoundbankParser;

/**
 * Cell renderer for the instruments and drum kits table in the
 * **Soundbank** > **Instruments & Drum Kits** tab of the info window.
 * 
 * The categories are displayed in another color than the plain syntax
 * elements.
 * 
 * For the bank column the tooltips have to show more information
 * than the cell content.
 * 
 * @author Jan Trukenmüller
 */
public class SoundbankInstrumentTableCellRenderer extends MidicaTableCellRenderer {
	
	private static final long serialVersionUID = 1L;
	
	/** List containing all elements including category entries */
	private ArrayList<HashMap<String, String>> instruments;
	
	/**
	 * Creates a cell renderer for the soundbank-based instruments and drum kits
	 * table.
	 */
	public SoundbankInstrumentTableCellRenderer() {
		this.instruments = SoundbankParser.getSoundbankInstruments();
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		row = table.convertRowIndexToModel(row);
		
		// category entries have an element with key=category and value=category
		HashMap<String, String> instrument = instruments.get(row);
		boolean isCategory = instrument.get("category") != null;
		if (isCategory) {
			if (isSelected)
				cell.setBackground(Laf.COLOR_TABLE_CELL_CAT_SELECTED);
			else
				cell.setBackground(Laf.COLOR_TABLE_CELL_CATEGORY);
		}
		else {
			if (isSelected)
				cell.setBackground(Laf.COLOR_TABLE_CELL_SELECTED);
			else
				cell.setBackground(null);
		}
		
		// bank tooltip
		if (1 == col && ! isCategory && cell instanceof JComponent) {
			JComponent jCell = (JComponent) cell;
			String text = Dict.get(Dict.TOOLTIP_BANK_MSB)  + ": " + instrument.get("bank_msb") + ", "
			            + Dict.get(Dict.TOOLTIP_BANK_LSB)  + ": " + instrument.get("bank_lsb") + ", "
			            + Dict.get(Dict.TOOLTIP_BANK_FULL) + ": " + instrument.get("bank");
			jCell.setToolTipText(text);
		}
		
		return cell;
	}
}
