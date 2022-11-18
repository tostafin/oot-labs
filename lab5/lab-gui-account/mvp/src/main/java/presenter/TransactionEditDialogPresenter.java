package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import model.Category;
import model.Transaction;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class TransactionEditDialogPresenter {

	private Transaction transaction;

	@FXML
	private TextField dateTextField;

	@FXML
	private TextField payeeTextField;

	@FXML
	private TextField categoryTextField;

	@FXML
	private TextField inflowTextField;
	
	private Stage dialogStage;
	
	private boolean approved;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setData(Transaction transaction) {
		this.transaction = transaction;
		updateControls();
	}
	
	public boolean isApproved() {
		return approved;
	}
	
	@FXML
	private void handleOkAction(ActionEvent event) throws ParseException {
		updateModel();
		approved = true;
		dialogStage.close();
	}
	
	@FXML
	private void handleCancelAction(ActionEvent event) {
		dialogStage.close();
	}
	
	private void updateModel() throws ParseException {
		String pattern = "yyyy-MM-dd";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateStringConverter converter = new LocalDateStringConverter(formatter, formatter);
		transaction.setDate(converter.fromString(dateTextField.getText()));
		transaction.setPayee(payeeTextField.getText());
		transaction.setCategory(new Category(categoryTextField.getText()));
		DecimalFormat decimalFormatter = new DecimalFormat();
		decimalFormatter.setParseBigDecimal(true);
		transaction.setInflow((BigDecimal) decimalFormatter.parse(inflowTextField.getText()));
	}
	
	private void updateControls() {
		String pattern = "yyyy-MM-dd";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateStringConverter converter = new LocalDateStringConverter(formatter, formatter);
		dateTextField.setText(converter.toString(transaction.getDate()));
		payeeTextField.setText(transaction.getPayee());
		categoryTextField.setText(transaction.getCategory().toString());
		inflowTextField.setText(transaction.getInflow().toString());
	}
}
