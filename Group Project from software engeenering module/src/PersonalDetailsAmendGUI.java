import com.toedter.calendar.JDateChooser;
import datastructures.PDEmployee;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

/**
 * gui for crating Personal Details
 * Created by Group 29D on 02-Mar-17.
 */
class PersonalDetailsAmendGUI extends JFrame {
    private JFrame frame;
    //TextFields
    private JTextField txtStaffNo = new JTextField();
    private JTextField txtSurname = new JTextField();
    private JTextField txtName = new JTextField();
    private JDateChooser txtDob = new JDateChooser();
    private JTextField txtAddress = new JTextField();
    private JTextField txtTown = new JTextField();
    private JTextField txtCounty = new JTextField();
    private JTextField txtPostcode = new JTextField();
    private JTextField txtTelNo = new JTextField();
    private JTextField txtMobileNo = new JTextField();
    private JTextField txtEmergencyContact = new JTextField();
    private JTextField txtEmergencyContactNo = new JTextField();

    private GridBagConstraints constraints;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    //controller
    private PersonalDetailsController personalDetailsController;
    private AuthenticationController authenticator;

    /**
     * constructor
     * @param staffId the staff ID of the employee personal details to amend
     */
    PersonalDetailsAmendGUI(String staffId, AuthenticationController authenticator) {
        constraints = new GridBagConstraints();
        frame = new JFrame("Yuconz - Amend Personal Details");
        mainPanel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new GridBagLayout());
        this.authenticator = authenticator;

        personalDetailsController = new PersonalDetailsController();

        setup(staffId);
    }

    /**
     * Creates the gui window and it's content with use of other methods
     * once it is ready it makes the gui visible to the user.
     * @param staffId the staff ID of the employee personal details to amend
     */
    private void setup(String staffId) {
        Container contentArea = frame.getContentPane();
        contentArea.setLayout(new BorderLayout());

        //constraints that are common for all items
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        makeMenu();
        populateMainPanel(staffId);
        populateButtonPanel();

        contentArea.add(mainPanel, BorderLayout.CENTER);
        contentArea.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setMinimumSize(new Dimension(550, 200));
        frame.setLocationRelativeTo(null);//setting frame to the center of the screen
        frame.setVisible(true);
    }

    private void populateButtonPanel() {
        JButton btnSave = new JButton("Save");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        buttonPanel.add(btnSave, constraints);
        JButton btnCancel = new JButton("Cancel");
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        buttonPanel.add(btnCancel, constraints);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        btnCancel.addActionListener(e -> {

            frame.dispose();
            //redirects to createPD (will change it with the correct access right
            new PersonalDetailsMainGUI(authenticator);
        });

        btnSave.addActionListener((ActionEvent e) -> {
            //gets the input values from the gui to be passed to the controller for checking to add to the database
            int staffId = Integer.parseInt(txtStaffNo.getText());
            String surname = txtSurname.getText();
            String name = txtName.getText();
            Date dob = txtDob.getDate();
            String address = txtAddress.getText();
            String town = txtTown.getText();
            String county = txtCounty.getText();
            String postCode = txtPostcode.getText();
            String telNo = txtTelNo.getText();
            String mobileNo = txtMobileNo.getText();
            String emergencyContact = txtEmergencyContact.getText();
            String emergencyContactNo = txtEmergencyContactNo.getText();

            personalDetailsController.amendPersonalDetails(staffId,
                    surname,
                    name,
                    dob,
                    address,
                    town,
                    county,
                    postCode,
                    telNo,
                    mobileNo,
                    emergencyContact,
                    emergencyContactNo
            );

            JOptionPane.showMessageDialog(null, name + " " + surname + " successfully saved");

            frame.dispose();
            //redirects to createPD (will change it with the correct access right
            new PersonalDetailsMainGUI(authenticator);

        });
    }



    /**
     * Creates the menuBar for the gui and allocates it to the gui frame.
     *
     */
    private void makeMenu() {
        //creates and populates a menuBar with a file and help which contain quit and about respectively
        JMenuBar menuBar = new JMenuBar();
        JMenu menu;
        JMenuItem item;

        frame.setJMenuBar(menuBar);
        menu = new JMenu("File");

        item = new JMenuItem("Quit");
        item.addActionListener(e ->
                System.exit(0));
        menu.add(item);
        menuBar.add(menu);

        menu = new JMenu("Help");

        item = new JMenuItem("About");
        item.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "Created by Group 29D.", "About",
                        JOptionPane.INFORMATION_MESSAGE));
        menu.add(item);
        menuBar.add(menu);

        item = new JMenuItem("Logout");
        item.addActionListener(e -> {
            dispose();
            new AuthenticationGUI();
        });
    }

    private void populateMainPanel(String staffId) {
        //Get Data on employee with employee id from HRDatabase
        PDEmployee employee = personalDetailsController.readPersonalDetails(staffId);
        //Store in variable
        //setText of each field individually


        int labelWeight = 0;
        int fieldWeight = 1;
        int gridYValue = 0;

        //staff no to panel
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("StaffNo:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtStaffNo.setText(employee.getStaffId());
        txtStaffNo.setEditable(false);
        mainPanel.add(txtStaffNo, constraints);
        gridYValue++;

        //surname to panel
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Surname:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtSurname.setText(employee.getSurname());
        mainPanel.add(txtSurname, constraints);
        gridYValue++;

        //name to panel
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Name:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtName.setText(employee.getName());
        mainPanel.add(txtName, constraints);
        gridYValue++;

        //DOB to panel
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Date of Birth:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtDob.setDateFormatString("YYYY / MM / dd");
        txtDob.setDate(employee.getDob());
        mainPanel.add(txtDob, constraints);
        gridYValue++;

        //Address to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Address:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtAddress.setPreferredSize(new Dimension(450, 90));
        txtAddress.setText(employee.getAddress());
        mainPanel.add(txtAddress, constraints);
        gridYValue++;

        //town to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Town:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtTown.setText(employee.getTown());
        mainPanel.add(txtTown, constraints);
        gridYValue++;

        //county to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("County:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtCounty.setText(employee.getCounty());
        mainPanel.add(txtCounty, constraints);
        gridYValue++;

        //postcode to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Postcode:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtPostcode.setText(employee.getPostcode());
        mainPanel.add(txtPostcode, constraints);
        gridYValue++;

        //tel no to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Telephone Number:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtTelNo.setText(employee.getTelNo());
        mainPanel.add(txtTelNo, constraints);
        gridYValue++;

        //mobile no to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Mobile Number:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtMobileNo.setText(employee.getMobileNo());
        mainPanel.add(txtMobileNo, constraints);
        gridYValue++;

        //emergency contact to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Emergency Contact Name:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtEmergencyContact.setText(employee.getEmergencyContact());
        mainPanel.add(txtEmergencyContact, constraints);
        gridYValue++;

        //staff no to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Emergency Contact Number:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtEmergencyContactNo.setText(employee.getEmergencyContactNo());
        mainPanel.add(txtEmergencyContactNo, constraints);
    }

    private void setConstraints(int gridX, int gridY, int weightX) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.weightx = weightX;
        constraints.gridwidth = 1;
    }
}
