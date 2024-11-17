import java.applet.Applet;
import java.awt.Button;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * A Java Applet that calculates the age based on the date of birth input.
 */
public class AgeCalculatorApplet extends Applet implements ActionListener {

    // Components
    Label dobLabel, ageLabel;
    TextField dobField, ageField;
    Button calculateButton;

    @Override
    public void init() {
        // Set the layout
        setLayout(null);

        // Create and position components
        dobLabel = new Label("Enter DOB (YYYY-MM-DD):");
        dobLabel.setBounds(30, 50, 150, 20);
        add(dobLabel);

        dobField = new TextField();
        dobField.setBounds(190, 50, 100, 20);
        add(dobField);

        calculateButton = new Button("Calculate Age");
        calculateButton.setBounds(100, 100, 120, 30);
        add(calculateButton);

        ageLabel = new Label("Your Age:");
        ageLabel.setBounds(30, 150, 70, 20);
        add(ageLabel);

        ageField = new TextField();
        ageField.setBounds(100, 150, 50, 20);
        ageField.setEditable(false);
        add(ageField);

        // Add ActionListener to the button
        calculateButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the input DOB from the text field
        String dobInput = dobField.getText();

        try {
            // Parse the input DOB
            String[] dobParts = dobInput.split("-");
            int yearOfBirth = Integer.parseInt(dobParts[0]);
            int monthOfBirth = Integer.parseInt(dobParts[1]);
            int dayOfBirth = Integer.parseInt(dobParts[2]);

            // Get the current year, month, and day
            Calendar currentDate = Calendar.getInstance();
            int currentYear = currentDate.get(Calendar.YEAR);
            int currentMonth = currentDate.get(Calendar.MONTH) + 1; // Months are 0-based
            int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

            // Calculate the age
            int age = currentYear - yearOfBirth;
            if (currentMonth < monthOfBirth || (currentMonth == monthOfBirth && currentDay < dayOfBirth)) {
                age--; // If the current date is before the DOB this year, subtract one year
            }

            // Display the calculated age
            ageField.setText(String.valueOf(age));

        } catch (Exception ex) {
            // Handle incorrect input format
            ageField.setText("Error");
        }
    }
}