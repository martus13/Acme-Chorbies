
package utilities;

public final class Functions {

	private Functions() {
		//
	}

	public static String replaceAllPhoneAndEmail(final String string, final String replacement) {
		String result;

		result = string.replaceAll("(\\+[0-9]{1,3} )?(\\([0-9]{1,3}\\) )?([0-9]{4,})", replacement);
		result = result.replaceAll("([^.@\\s]+)(\\.[^.@\\s]+)*@([^.@\\s]+\\.)+([^.@\\s]+)", replacement);

		return result;
	}
}
