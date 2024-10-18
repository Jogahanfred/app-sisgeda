package pe.mil.fap.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public final class UtilHelpers {

	public static Integer getCurrentYear() {
		try {
			Date date = new Date();
			DateFormat formatDate = new SimpleDateFormat("yyyy");
			Integer anioActual = Integer.parseInt(formatDate.format(date));
			return anioActual;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String capitalize(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}

		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}

	public static String convertListIntegerToString(List<Integer> lista) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < lista.size(); i++) {
			sb.append(lista.get(i));
			if (i < lista.size() - 1) {
				sb.append(",");
			}
		}

		return sb.toString();
	}

	public static String extractErrorMessage(String errorMessage) {
		if (errorMessage != null) {
			int oraIndex = errorMessage.indexOf("ORA-");
			if (oraIndex != -1) {
				int lineEndIndex = errorMessage.indexOf('\n', oraIndex);
				if (lineEndIndex == -1) {
					lineEndIndex = errorMessage.length();
				}

				String message = errorMessage.substring(oraIndex + 4, lineEndIndex).trim();

				int colonIndex = message.indexOf(':');
				if (colonIndex != -1) {
					return message.substring(colonIndex + 1).trim();
				}

				return message;
			}
		}
		return errorMessage;

	}
	
	public static String getGreetingTimeOfDay() {
		LocalTime now = LocalTime.now();
        if (now.isBefore(LocalTime.of(12, 0))) {
            return "Buenos Días"; // 00:00 hasta 11:59
        } else if (now.isBefore(LocalTime.of(18, 0))) {
            return "Buenas Tardes"; // 12:00 hasta 18:00
        } else {
            return "Buenas Noches"; // 18:01 hasta 23:59
        }
	}
}
