package rmi_phone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javax.swing.border.Border;

public class PhoneManagementGUI {

    private JFrame frame;
    private JFrame loginFrame;
    private PhoneInterface server;

    public PhoneManagementGUI(PhoneInterface server) {
        this.server = server;
        initializeLoginUI();
    }

    private void initialize() {
        frame = new JFrame("Phone Management");
        frame.setBounds(500, 300, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JLabel lblHeading = new JLabel("Phone Information Management");
        lblHeading.setHorizontalAlignment(SwingConstants.CENTER);
        lblHeading.setFont(new Font("Tahoma", Font.BOLD, 18));
        frame.getContentPane().add(lblHeading, BorderLayout.NORTH);

        JPanel panelButtons = new JPanel();
        frame.getContentPane().add(panelButtons, BorderLayout.CENTER);
        panelButtons.setLayout(new GridLayout(2, 2, 10, 10));

        JButton btnFindPhone = new JButton("Find Phone");
        btnFindPhone.addActionListener(e -> findPhone());
        panelButtons.add(btnFindPhone);

        JButton btnAddPhone = new JButton("Add Phone");
        btnAddPhone.addActionListener(e -> addPhone());
        panelButtons.add(btnAddPhone);

        JButton btnUpdatePhone = new JButton("Update Phone");
        btnUpdatePhone.addActionListener(e -> updatePhone());
        panelButtons.add(btnUpdatePhone);

        JButton btnDeletePhone = new JButton("Delete Phone");
        btnDeletePhone.addActionListener(e -> deletePhone());
        panelButtons.add(btnDeletePhone);
    } 
    
	 private void findPhone() {
	    String id = JOptionPane.showInputDialog(frame, "Enter the ID of the phone to find:");
	    if (id != null && !id.trim().isEmpty()) {
	        try {
	            Phone phone = server.find(id);
	            if (phone != null) {
	                String result = "Found phone:\n" +
	                                "ID: " + phone.getId() + "\n" +
	                                "Name: " + phone.getName() + "\n" +
	                                "Brand: " + phone.getBrand() + "\n" +
	                                "Basic Info: " + phone.getBasicInfo() + "\n" +
	                                "Price: " + phone.getPrice() + "\n\n"; 
	                textAreaResults.append(result);
	            } else {
	                textAreaResults.append("No phone found with ID: " + id + "\n\n");
	            }
	        } catch (Exception e) {
	            textAreaResults.append("Error: " + e.getMessage() + "\n\n");
	            e.printStackTrace();
	        }
	    }
	}

	  private void addPhone() {
	      JTextField idField = new JTextField(5);
	      JTextField nameField = new JTextField(10);
	      JTextField brandField = new JTextField(10);
	      JTextField basicInfoField = new JTextField(20);
	      JTextField priceField = new JTextField(10);
	
	      JPanel myPanel = new JPanel();
	      myPanel.setLayout(new GridLayout(5, 2));
	      myPanel.add(new JLabel("ID:"));
	      myPanel.add(idField);
	      myPanel.add(new JLabel("Name:"));
	      myPanel.add(nameField);
	      myPanel.add(new JLabel("Brand:"));
	      myPanel.add(brandField);
	      myPanel.add(new JLabel("Basic Info:"));
	      myPanel.add(basicInfoField);
	      myPanel.add(new JLabel("Price:"));
	      myPanel.add(priceField);
	
	      int result = JOptionPane.showConfirmDialog(null, myPanel,
	              "Enter phone details", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	          String id = idField.getText();
	          String name = nameField.getText();
	          String brand = brandField.getText();
	          String basicInfo = basicInfoField.getText();
	          double price = Double.parseDouble(priceField.getText());
	          Phone newPhone = new Phone(id, name, brand, basicInfo, price);
	          try {
	              int insertResult = server.insert(newPhone);
	              if (insertResult > 0) {
	                  textAreaResults.append("Phone added successfully with ID: " + id + "\n\n");
	              } else {
	                  textAreaResults.append("Failed to add phone with ID: " + id + "\n\n");
	              }
	          } catch (Exception e) {
	              textAreaResults.append("Error adding phone: " + e.getMessage() + "\n\n");
	              e.printStackTrace();
	          }
	      }
	  }
		  
	  private void updatePhone() {
	      JTextField idField = new JTextField(5);
	      JTextField nameField = new JTextField(10);
	      JTextField brandField = new JTextField(10);
	      JTextField basicInfoField = new JTextField(20);
	      JTextField priceField = new JTextField(10);
	
	      JPanel myPanel = new JPanel();
	      myPanel.setLayout(new GridLayout(5, 2));
	      myPanel.add(new JLabel("ID:"));
	      myPanel.add(idField);
	      myPanel.add(new JLabel("New Name:"));
	      myPanel.add(nameField);
	      myPanel.add(new JLabel("New Brand:"));
	      myPanel.add(brandField);
	      myPanel.add(new JLabel("New Basic Info:"));
	      myPanel.add(basicInfoField);
	      myPanel.add(new JLabel("New Price:"));
	      myPanel.add(priceField);
	
	      int result = JOptionPane.showConfirmDialog(null, myPanel,
	              "Enter phone ID and new details", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	          String id = idField.getText();
	          String name = nameField.getText();
	          String brand = brandField.getText();
	          String basicInfo = basicInfoField.getText();
	          double price = Double.parseDouble(priceField.getText());
	          Phone updatedPhone = new Phone(id, name, brand, basicInfo, price);
	          try {
	              int updateResult = server.update(updatedPhone);
	              if (updateResult > 0) {
	                  textAreaResults.append("Phone updated successfully for ID: " + id + "\n\n");
	              } else {
	                  textAreaResults.append("Failed to update phone with ID: " + id + "\n\n");
	              }
	          } catch (Exception e) {
	              textAreaResults.append("Error updating phone: " + e.getMessage() + "\n\n");
	              e.printStackTrace();
	          }
	      }
	  }
		  
	  private void deletePhone() {
	      String id = JOptionPane.showInputDialog(frame, "Enter the ID of the phone to delete:");
	      if (id != null && !id.trim().isEmpty()) {
	    	  try {
	              int deleteResult = server.delete(id);
	              if (deleteResult > 0) {
	                  textAreaResults.append("Phone deleted successfully for ID: " + id + "\n\n");
	              } else {
	                  textAreaResults.append("Failed to delete phone with ID: " + id + "\n\n");
	              }
	          } catch (Exception e) {
	              textAreaResults.append("Error deleting phone: " + e.getMessage() + "\n\n");
	              e.printStackTrace();
	          }
	      }
	  }

	  private void initializeLoginUI() {
		    loginFrame = new JFrame("Login");
		    loginFrame.setSize(700, 500); 
		    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    loginFrame.setLayout(new GridBagLayout());

		    GridBagConstraints gbc = new GridBagConstraints();
		    gbc.fill = GridBagConstraints.HORIZONTAL;
		    gbc.insets = new Insets(20, 20, 20, 20); 

		    Font labelFont = new Font("Arial", Font.BOLD, 17); 
		    JLabel userLabel = new JLabel("Username:");
		    userLabel.setFont(labelFont);
		    JTextField userText = new JTextField(20);
		    userText.setFont(labelFont);
		    userText.setPreferredSize(new Dimension(200, 40)); 

		    JLabel passwordLabel = new JLabel("Password:");
		    passwordLabel.setFont(labelFont);
		    JPasswordField passwordText = new JPasswordField(20);
		    passwordText.setFont(labelFont);
		    passwordText.setPreferredSize(new Dimension(200, 40)); 

		    JButton loginButton = new JButton("Login");
		    JButton registerButton = new JButton("Register");

		    // Username row
		    gbc.gridx = 0;
		    gbc.gridy = 0;
		    loginFrame.add(userLabel, gbc);
		    gbc.gridx = 1;
		    loginFrame.add(userText, gbc);

		    // Password row
		    gbc.gridx = 0;
		    gbc.gridy = 1;
		    loginFrame.add(passwordLabel, gbc);
		    gbc.gridx = 1;
		    loginFrame.add(passwordText, gbc);

		    // Buttons row
		    gbc.gridx = 0;
		    gbc.gridy = 2;
		    loginFrame.add(registerButton, gbc);
		    gbc.gridx = 1;
		    loginFrame.add(loginButton, gbc);

		    loginButton.addActionListener(e -> {
		        String username = userText.getText();
		        String password = new String(passwordText.getPassword());
		        // Implement your login logic here
		        performLogin(username, password);
		    });

		    registerButton.addActionListener(e -> {
		        JTextField usernameField = new JTextField(20);
		        JPasswordField passwordField = new JPasswordField(20);
		        JPasswordField confirmPasswordField = new JPasswordField(20);

		        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
		        panel.add(new JLabel("Username:"));
		        panel.add(usernameField);
		        panel.add(new JLabel("Password:"));
		        panel.add(passwordField);
		        panel.add(new JLabel("Confirm Password:"));
		        panel.add(confirmPasswordField);

		        int result = JOptionPane.showConfirmDialog(null, panel, 
		                 "Register", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		        if (result == JOptionPane.OK_OPTION) {
		            String username = usernameField.getText();
		            String password = new String(passwordField.getPassword());
		            String confirmPassword = new String(confirmPasswordField.getPassword());
		            if (password.equals(confirmPassword)) {
		                registerUser(username, password, "user");
		            } else {
		                JOptionPane.showMessageDialog(loginFrame, "Passwords do not match.", "Registration Error", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    });
		    loginFrame.setLocationRelativeTo(null); 
		    loginFrame.setVisible(true);
		}


		private void performLogin(String username, String password) {
		    try {
		        String role = server.authenticateUser(username, password);
		        if (role != null) {
		            loginFrame.dispose();
		            initialize(role); 
		            frame.setVisible(true);
		        } else {
		            JOptionPane.showMessageDialog(loginFrame, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
		        }
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(loginFrame, "Error: " + e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
		        e.printStackTrace();
		    }
		}

		private void registerUser(String username, String password, String role) {
		    try {
		        boolean success = server.registerUser(username, password, role);
		        if (success) {
		            JOptionPane.showMessageDialog(loginFrame, "User registered successfully!");
		        } else {
		            JOptionPane.showMessageDialog(loginFrame, "Failed to register user.", "Registration Error", JOptionPane.ERROR_MESSAGE);
		        }
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(loginFrame, "Error: " + e.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
		        e.printStackTrace();
		    }
		}
		    
		private JTextArea textAreaResults;

		private void initialize(String role) {
		    frame = new JFrame("Phone Management");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setSize(1100, 700); 
		    frame.setLocationRelativeTo(null); 

		    JLabel lblHeading = new JLabel("Phone Information Management");
		    lblHeading.setHorizontalAlignment(SwingConstants.CENTER);
		    lblHeading.setFont(new Font("Tahoma", Font.BOLD, 18));
		    frame.getContentPane().add(lblHeading, BorderLayout.NORTH);

		    JPanel mainPanel = new JPanel();
		    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS)); 

		    JPanel panelButtons = new JPanel();
		    panelButtons.setLayout(new GridLayout(4, 1, 8, 8)); 
		    panelButtons.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); 

		    JButton btnFindPhone = new JButton("Find Phone");
		    btnFindPhone.addActionListener(e -> findPhone());
		    panelButtons.add(btnFindPhone);

		    if ("user".equals(role)) {
		        JButton btnShowAll = new JButton("Show All");
		        btnShowAll.addActionListener(e -> showAllPhones());
		        panelButtons.add(btnShowAll);
		    }

		    if ("admin".equals(role)) {
		        JButton btnAddPhone = new JButton("Add Phone");
		        btnAddPhone.addActionListener(e -> addPhone());
		        panelButtons.add(btnAddPhone);

		        JButton btnUpdatePhone = new JButton("Update Phone");
		        btnUpdatePhone.addActionListener(e -> updatePhone());
		        panelButtons.add(btnUpdatePhone);

		        JButton btnDeletePhone = new JButton("Delete Phone");
		        btnDeletePhone.addActionListener(e -> deletePhone());
		        panelButtons.add(btnDeletePhone);
		    }
		    
		    textAreaResults = new JTextArea();
		    textAreaResults.setEditable(false);
		    textAreaResults.setFont(new Font("SansSerif", Font.BOLD, 16));
		    JScrollPane scrollPaneResults = new JScrollPane(textAreaResults);
		    scrollPaneResults.setPreferredSize(new Dimension(500, 350)); 
		    // Create an outer margin border
		    Border outerMargin = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		    // Create an inner border with a title and padding
		    Border innerBorder = BorderFactory.createCompoundBorder(
		            BorderFactory.createTitledBorder("Results"),
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
		    
		    scrollPaneResults.setBorder(BorderFactory.createCompoundBorder(outerMargin, innerBorder));

		    mainPanel.add(panelButtons);
		    mainPanel.add(scrollPaneResults);

		    frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		    frame.setVisible(true);
		}

		private void showAllPhones() {
		    try {
		        List<Phone> phones = server.getAllRecords();
		        if (phones != null && !phones.isEmpty()) {
		            textAreaResults.append("All Phones:\n");
		            for (Phone phone : phones) {
		                textAreaResults.append("ID: " + phone.getId() + "\n" +
		                        "Name: " + phone.getName() + "\n" +
		                        "Brand: " + phone.getBrand() + "\n" +
		                        "Basic Info: " + phone.getBasicInfo() + "\n" +
		                        "Price: " + phone.getPrice() + "\n\n");
		            }
		        } else {
		            textAreaResults.append("No phones to display.\n\n");
		        }
		    } catch (Exception e) {
		        textAreaResults.append("Error retrieving phones: " + e.getMessage() + "\n\n");
		        e.printStackTrace();
		    }
		}

		private void displayPhones(List<Phone> phones) {
		    String[] columnNames = {"ID", "Name", "Brand", "Basic Info", "Price"};
		    Object[][] data = new Object[phones.size()][columnNames.length];
		    for (int i = 0; i < phones.size(); i++) {
		        Phone phone = phones.get(i);
		        data[i][0] = phone.getId();
		        data[i][1] = phone.getName();
		        data[i][2] = phone.getBrand();
		        data[i][3] = phone.getBasicInfo();
		        data[i][4] = phone.getPrice();
		    }

		    JTable table = new JTable(data, columnNames);
		    JScrollPane scrollPane = new JScrollPane(table);
		    table.setFillsViewportHeight(true);

		    // Tạo và hiển thị cửa sổ mới chứa bảng
		    JFrame frameTable = new JFrame("All Phones");
		    frameTable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    frameTable.add(scrollPane);
		    frameTable.pack();
		    frameTable.setLocationRelativeTo(null);
		    frameTable.setVisible(true);
		}

	  public static void main(String[] args) {
	        try {
	            Registry registry = LocateRegistry.getRegistry("localhost", 7777);
	            PhoneInterface server = (PhoneInterface) registry.lookup("Server");
	            EventQueue.invokeLater(() -> {
	                try {
	                    new PhoneManagementGUI(server);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            });
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 }
}
