import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class NumberConversionApp {
    public static void main(String[] args) {
        // Launch the SystemMenu on the Event Dispatch Thread
        SwingUtilities.invokeLater(SystemMenu::new);
    }
}

class SystemMenu extends JFrame {
    public SystemMenu() {
        // Set up the main frame for the system menu
        setTitle("System Menu");
        setSize(820, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and configure the welcome message label
        JLabel welcomeLabel = new JLabel("Welcome to Number Conversion Calculator", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16)); 

        // Create a panel for navigation buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton detailsButton = new JButton("Details");
        JButton enterProgramButton = new JButton("Enter Program");

        // Action Listener for the details button
        detailsButton.addActionListener(e -> JOptionPane.showMessageDialog(
                this, "This program converts numbers between Decimal, Binary, and Hexadecimal formats."));
        
        // Action Listener for the enter program button
        enterProgramButton.addActionListener(e -> {
            dispose(); // Close the current menu
            new NumberConverterInterface(); // Open the number converter interface
        });

        // Add buttons to the button panel
        buttonPanel.add(detailsButton);
        buttonPanel.add(enterProgramButton);

        // Add components to the main frame
        add(welcomeLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Show the window centered on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class NumberConverterInterface extends JFrame implements ActionListener {
    private JTextField inputField, decimalField, hexField, binaryField; // Input and output fields
    private JComboBox<String> inputTypeComboBox; // Dropdown for input type selection
    private JCheckBox CBDecimal, CBHex, CBBinary; // Checkboxes for conversion options
    private static ArrayList<String[]> conversionResults = new ArrayList<>(); // Store conversion results
    private static int conversionCount = 0; // Count of conversions performed

    public NumberConverterInterface() {
        // Set up the frame for the number converter interface
        setTitle("Number Converter");
        setSize(820, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel for user input
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputTypeComboBox = new JComboBox<>(new String[]{"Decimal", "Hexadecimal", "Binary"});
        inputField = new JTextField();

        // Add input type selection and input field to the panel
        inputPanel.add(new JLabel("Input Type:"));
        inputPanel.add(inputTypeComboBox);
        inputPanel.add(new JLabel("Input:"));
        inputPanel.add(inputField);

        // Checkboxes for conversion options
        JPanel checkboxPanel = new JPanel(new GridLayout(1, 4));
        CBDecimal = new JCheckBox("Decimal");
        CBHex = new JCheckBox("Hexadecimal");
        CBBinary = new JCheckBox("Binary");
        checkboxPanel.add(new JLabel("Convert to:"));
        checkboxPanel.add(CBDecimal);
        checkboxPanel.add(CBHex);
        checkboxPanel.add(CBBinary);
        inputPanel.add(checkboxPanel);

        // Output Panel for displaying conversion results
        JPanel outputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        decimalField = new JTextField();
        hexField = new JTextField();
        binaryField = new JTextField();
        decimalField.setEditable(false); // Make output fields non-editable
        hexField.setEditable(false);
        binaryField.setEditable(false);

        // Add output labels and fields to the output panel
        outputPanel.add(new JLabel("Decimal:"));
        outputPanel.add(decimalField);
        outputPanel.add(new JLabel("Hexadecimal:"));
        outputPanel.add(hexField);
        outputPanel.add(new JLabel("Binary:"));
        outputPanel.add(binaryField);

        // Buttons Panel for actions
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton bConvert = new JButton("Convert");
        JButton bClear = new JButton("Clear");
        JButton bSummary = new JButton("Summary");
        JButton bBack = new JButton("Back to Menu");

        // Add action listeners to buttons
        bConvert.addActionListener(this);
        bClear.addActionListener(this);
        bSummary.addActionListener(this);
        bBack.addActionListener(this);

        // Add buttons to the button panel
        buttonPanel.add(bConvert);
        buttonPanel.add(bClear);
        buttonPanel.add(bSummary);
        buttonPanel.add(bBack);

        // Add all panels to the main frame
        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Show the window centered on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button actions based on the command
        switch (e.getActionCommand()) {
            case "Convert":
                convertInput(); // Call method to convert input
                break;
            case "Clear":
                clearFields(); // Call method to clear input and output fields
                break;
            case "Summary":
                new SummaryInterface(); // Open summary interface
                break;
            case "Back to Menu":
                dispose(); // Close current interface
                new SystemMenu(); // Return to system menu
                break;
        }
    }

    private void clearFields() {
        // Clear all input and output fields
        inputField.setText("");
        decimalField.setText("");
        hexField.setText("");
        binaryField.setText("");
        CBDecimal.setSelected(false);
        CBHex.setSelected(false);
        CBBinary.setSelected(false);
    }

    private void convertInput() {
        try {
            // Get the selected input type and the input value
            String inputType = (String) inputTypeComboBox.getSelectedItem();
            String inputValue = inputField.getText().trim();

            // Check if the input is empty
            if (inputValue.isEmpty()) {
                throw new NumberFormatException("Input cannot be empty.");
            }

            // Declare the decimalValue to store the converted number
            int decimalValue;

            // Convert the input based on its type
            switch (inputType) {
                case "Decimal":
                    decimalValue = Integer.parseInt(inputValue);
                    break;
                case "Hexadecimal":
                    decimalValue = Integer.parseInt(inputValue, 16);
                    break;
                case "Binary":
                    decimalValue = Integer.parseInt(inputValue, 2);
                    break;
                default:
                    throw new NumberFormatException("Invalid input type.");
            }

            // Handle conversion for Decimal
            String decimalResult;
            if (CBDecimal.isSelected()) {
                decimalResult = String.valueOf(decimalValue);
            } else {
                decimalResult = "No Conversion";
            }

            // Handle conversion for Hexadecimal
            String hexResult;
            if (CBHex.isSelected()) {
                hexResult = Integer.toHexString(decimalValue).toUpperCase();
            } else {
                hexResult = "No Conversion";
            }

            // Handle conversion for Binary
            String binaryResult;
            if (CBBinary.isSelected()) {
                binaryResult = Integer.toBinaryString(decimalValue);
            } else {
                binaryResult = "No Conversion";
            }

            // Set the results in the output fields
            decimalField.setText(decimalResult);
            hexField.setText(hexResult);
            binaryField.setText(binaryResult);

            // Store the conversion results for summary or logging
            conversionResults.add(new String[]{inputType, inputValue, decimalResult, hexResult, binaryResult});
            conversionCount++;

        } catch (NumberFormatException ex) {
            // Show an error message if input is invalid
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.");
        }
    }

    public static ArrayList<String[]> getConversionResults() {
        return conversionResults; // Return the list of conversion results
    }

    public static int getConversionCount() {
        return conversionCount; // Return the count of conversions
    }
}

class SummaryInterface extends JFrame {
    public SummaryInterface() {
        // Set up the frame for the summary interface
        setTitle("Summary");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Retrieve conversion results and set up the table
        ArrayList<String[]> results = NumberConverterInterface.getConversionResults();
        String[] columns = {"Input Type", "Input", "Decimal", "Hexadecimal", "Binary"};

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        results.forEach(tableModel::addRow); // Add each result to the table model

        JTable table = new JTable(tableModel); // Create a table with the model
        JScrollPane scrollPane = new JScrollPane(table); // Add scroll functionality

        // Create a text area to display the total conversions
        JTextArea summaryArea = new JTextArea("Total Conversions: " + NumberConverterInterface.getConversionCount());
        summaryArea.setEditable(false); // Make the summary area non-editable
        
        // Button panel for navigation
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton backButton = new JButton("Back to Menu");
        JButton exitButton = new JButton("Exit");

        // Action listener for the back button
        backButton.addActionListener(e -> {
            dispose(); // Close the summary interface
            new SystemMenu(); // Return to the system menu
        });

        // Action listener for the exit button
        exitButton.addActionListener(e -> System.exit(0)); // Exit the application

        // Add buttons to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(exitButton);

        // Add components to the main frame
        add(summaryArea, BorderLayout.NORTH); // Add summary area at the top
        add(scrollPane, BorderLayout.CENTER); // Add the table in the center
        add(buttonPanel, BorderLayout.SOUTH); // Add button panel at the bottom

        // Show the window centered on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }
}