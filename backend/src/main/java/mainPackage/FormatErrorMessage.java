package mainPackage;

public class FormatErrorMessage {
	private String error;

	public FormatErrorMessage(String error) {
		super();
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "FormatErrorMessage [error=" + error + ", toString()=" + super.toString() + "]";
	}
}
