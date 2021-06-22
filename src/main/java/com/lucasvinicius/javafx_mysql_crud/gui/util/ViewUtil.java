package com.lucasvinicius.javafx_mysql_crud.gui.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.StringConverter;

public class ViewUtil {

	public static String toTitledCase(String str) {
		String[] words = str.split("\\s");
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < words.length; i++) {
			sb.append(words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase());
			sb.append(" ");
		}
		return sb.toString().trim();
	}

	public static void formatDatePicker(DatePicker datePicker, String format) {
		datePicker.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);

			{
				datePicker.setPromptText(format.toLowerCase());
			}

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}

	public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
		tableColumn.setCellFactory(column -> {

			TableCell<T, Double> cell = new TableCell<T, Double>() {

				String format = "%." + decimalPlaces + "f";

				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null) {
						setText(null);
						return;
					}

					Locale.setDefault(Locale.US);
					setText(String.format(format, item));
				}
			};
			return cell;
		});
	}

}
