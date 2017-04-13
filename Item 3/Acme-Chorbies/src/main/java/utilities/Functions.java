
package utilities;

public final class Functions {

	private Functions() {
		//
	}

	public static String replaceAllPhoneAndEmail(final String string, final String replacement) {
		String result;

		result = string;

		// Phone number:
		/*
		 * FUNCIONAN PERO NO PILLA +34 111 222 333 NI 955555555 NI 955 55 55 55:
		 * result = result.replaceAll("(\\+[0-9]{1,3} )?([0-9\\s]{4,})", replacement);
		 * result = result.replaceAll("(\\+[0-9]{1,3} )?(\\([0-9]{1,3}\\) )?([0-9]{4,})", replacement);
		 */

		//result = result.replaceAll("(\\+[0-9]{1,3} )?(\\([0-9]{1,3}\\) )?([0-9\\s]{4,})", replacement);  // FUNCIONA PERO NO ACEPTA 655 55 55 55 PERO SÍ 123*ESPACIO* 
		//result = result.replaceAll("(\\+[0-9]{1,3} )?(\\([0-9]{1,3}\\) )?([0-9]{3,} )*([0-9]{3,})", replacement); // NO ACEPTA 955 55 55 55 PERO SÍ 123
		result = result.replaceAll("(\\+[0-9]{1,3}[ -.])?(\\([0-9]{1,3}\\)[ -.])?((([0-9]{3,}[ -.])+([0-9]{3,}))|(([0-9]{2,}[ -.])+([0-9]{2,}))|([0-9]{4,}))", replacement); // FUNCIONA GENIAL

		// Email:
		result = result.replaceAll("([^.@\\s]+)(\\.[^.@\\s]+)*@([^.@\\s]+\\.)+([^.@\\s]+)", replacement);

		return result;
	}
}
