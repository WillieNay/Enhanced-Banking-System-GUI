import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public class EnhancedBankingSystemGUI {
    private HashMap<String, Double> accounts = new HashMap<>();
    private HashMap<String, String> passwords = new HashMap<>();
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JPanel dashboardPanel;
    private JTextField userField;
    private JPasswordField passwordField;
    private JTextField amountField;
    private JTextArea transactionHistoryArea;
    private JLabel balanceLabel;
    private String currentUser = null;
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    
    public EnhancedBankingSystemGUI() {
        // Add some sample accounts
        accounts.put("user1", 1000.0);
        passwords.put("user1", "pass1");
        
        setupFrame();
        createLoginPanel();
        createDashboardPanel();
        
        // Initially show login panel
        showLoginPanel();
        
        frame.setVisible(true);
    }
    
    private void setupFrame() {
        frame = new JFrame("The Bank of Narnia");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new CardLayout());
        frame.add(mainPanel);
    }
    
    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Bank logo/name
        JLabel titleLabel = new JLabel("THE BANK OF NARNIA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Your trusted financial partner");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Login form
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(30, 40, 10, 40));
        
        JLabel userLabel = new JLabel("Username:");
        userField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        
        // Improve login button visibility
        JButton loginButton = new JButton("LOG IN");
        loginButton.setForeground(new Color(0, 120, 215));
        loginButton.setBackground(new Color(240, 240, 240));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(true);
        loginButton.setContentAreaFilled(true);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 1));
        
        // Improve register button visibility
        JButton registerButton = new JButton("REGISTER");
        registerButton.setForeground(new Color(0, 120, 215));
        registerButton.setBackground(new Color(240, 240, 240));
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(true);
        registerButton.setContentAreaFilled(true);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 1));
        
        formPanel.add(userLabel);
        formPanel.add(userField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(registerButton);
        formPanel.add(loginButton);
        
        // Error message
        JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passwordField.getPassword());
                
                if (username.isEmpty() || password.isEmpty()) {
                    errorLabel.setText("Please enter both username and password");
                    return;
                }
                
                if (accounts.containsKey(username) && passwords.get(username).equals(password)) {
                    currentUser = username;
                    updateDashboard();
                    showDashboardPanel();
                    errorLabel.setText(" ");
                } else {
                    errorLabel.setText("Invalid username or password");
                }
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegistrationDialog();
            }
        });
        
        // Add components to panel
        loginPanel.add(Box.createVerticalGlue());
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginPanel.add(subtitleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        loginPanel.add(formPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(errorLabel);
        loginPanel.add(Box.createVerticalGlue());
        
        mainPanel.add(loginPanel, "login");
    }
    
    private void createDashboardPanel() {
        dashboardPanel = new JPanel(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 120, 215));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE);
        
        // Improve logout button visibility
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFocusPainted(false);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 1));
        
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Account summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(3, 1, 10, 5));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Account Summary"));
        
        JLabel accountLabel = new JLabel("Account: ");
        balanceLabel = new JLabel("Current Balance: ");
        
        summaryPanel.add(accountLabel);
        summaryPanel.add(balanceLabel);
        summaryPanel.add(new JLabel(" "));
        
        // Transaction panel
        JPanel transactionPanel = new JPanel(new BorderLayout());
        transactionPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        JLabel transactionLabel = new JLabel("Make a Transaction");
        transactionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JPanel transactionFormPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        
        JLabel amountLabel = new JLabel("Amount ($):");
        amountField = new JTextField();
        JButton depositButton = new JButton("Deposit");
        depositButton.setBackground(new Color(46, 125, 50));
        depositButton.setForeground(Color.BLACK);
        depositButton.setFocusPainted(false);
        depositButton.setFont(new Font("Arial", Font.BOLD, 14));
        depositButton.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 1));
        
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBackground(new Color(211, 47, 47));
        withdrawButton.setForeground(Color.BLACK);
        withdrawButton.setFocusPainted(false);
        withdrawButton.setFont(new Font("Arial", Font.BOLD, 14));
        withdrawButton.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 1));
        
        JButton transferButton = new JButton("Transfer");
        transferButton.setBackground(new Color(156, 39, 176));
        transferButton.setForeground(Color.BLACK);
        transferButton.setFocusPainted(false);
        transferButton.setFont(new Font("Arial", Font.BOLD, 14));
        transferButton.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 1));

        transactionFormPanel.add(amountLabel);
        transactionFormPanel.add(amountField);
        transactionFormPanel.add(new JLabel(""));
        transactionFormPanel.add(depositButton);
        transactionFormPanel.add(withdrawButton);
        transactionFormPanel.add(transferButton);
        
        transactionPanel.add(transactionLabel, BorderLayout.NORTH);
        transactionPanel.add(transactionFormPanel, BorderLayout.CENTER);
        
        // Transaction history panel
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Transaction History"));
        
        transactionHistoryArea = new JTextArea(8, 40);
        transactionHistoryArea.setEditable(false);
        transactionHistoryArea.setForeground(Color.BLACK);
        transactionHistoryArea.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(transactionHistoryArea);
        
        historyPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add panels to content
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(summaryPanel, BorderLayout.NORTH);
        leftPanel.add(transactionPanel, BorderLayout.CENTER);
        
        contentPanel.add(leftPanel, BorderLayout.NORTH);
        contentPanel.add(historyPanel, BorderLayout.CENTER);
        
        // Add to dashboard
        dashboardPanel.add(headerPanel, BorderLayout.NORTH);
        dashboardPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Action listeners
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser = null;
                userField.setText("");
                passwordField.setText("");
                showLoginPanel();
            }
        });
        
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processTransaction("deposit");
            }
        });
        
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processTransaction("withdraw");
            }
        });
        
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTransferDialog();
            }
        });
        
        mainPanel.add(dashboardPanel, "dashboard");
    }
    
    private void showLoginPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "login");
    }
    
    private void showDashboardPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "dashboard");
    }
    
    private void updateDashboard() {
        if (currentUser != null) {
            double balance = accounts.get(currentUser);
            balanceLabel.setText("Current Balance: " + currencyFormatter.format(balance));
        }
    }
    
    private void processTransaction(String type) {
        if (currentUser == null) return;
        
        try {
            String amountText = amountField.getText().trim();
            if (amountText.isEmpty()) {
                showMessage("Please enter an amount", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double amount = Double.parseDouble(amountText);
            
            if (amount <= 0) {
                showMessage("Amount must be greater than zero", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (type.equals("deposit")) {
                accounts.put(currentUser, accounts.get(currentUser) + amount);
                addToTransactionHistory("Deposit: +" + currencyFormatter.format(amount));
                showMessage("Successfully deposited " + currencyFormatter.format(amount), "Deposit Successful", JOptionPane.INFORMATION_MESSAGE);
            } else if (type.equals("withdraw")) {
                if (accounts.get(currentUser) >= amount) {
                    accounts.put(currentUser, accounts.get(currentUser) - amount);
                    addToTransactionHistory("Withdrawal: -" + currencyFormatter.format(amount));
                    showMessage("Successfully withdrew " + currencyFormatter.format(amount), "Withdrawal Successful", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showMessage("Insufficient funds for this withdrawal", "Transaction Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            updateDashboard();
            amountField.setText("");
            
        } catch (NumberFormatException e) {
            showMessage("Please enter a valid amount", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showTransferDialog() {
        JTextField recipientField = new JTextField();
        JTextField transferAmountField = new JTextField();
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Recipient Username:"));
        panel.add(recipientField);
        panel.add(new JLabel("Amount ($):"));
        panel.add(transferAmountField);
        
        // Improve dialog buttons
        JOptionPane optionPane = new JOptionPane(panel, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION);
        
        JDialog dialog = optionPane.createDialog(frame, "Transfer Funds");
        
        // Make sure dialog buttons are visible
        Object[] options = ((JOptionPane)optionPane).getOptions();
        if (options != null) {
            for (Object option : options) {
                if (option instanceof JButton) {
                    JButton button = (JButton) option;
                    button.setFont(new Font("Arial", Font.BOLD, 14));
                    button.setForeground(Color.BLACK);
                }
            }
        }
        
        dialog.setVisible(true);
        
        Object result = optionPane.getValue();
        if (result != null && result instanceof Integer && (Integer)result == JOptionPane.OK_OPTION) {
            String recipient = recipientField.getText();
            
            try {
                double amount = Double.parseDouble(transferAmountField.getText());
                
                if (amount <= 0) {
                    showMessage("Amount must be greater than zero", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!accounts.containsKey(recipient)) {
                    showMessage("Recipient account not found", "Transfer Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (currentUser.equals(recipient)) {
                    showMessage("Cannot transfer to your own account", "Transfer Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (accounts.get(currentUser) >= amount) {
                    accounts.put(currentUser, accounts.get(currentUser) - amount);
                    accounts.put(recipient, accounts.get(recipient) + amount);
                    
                    addToTransactionHistory("Transfer to " + recipient + ": -" + currencyFormatter.format(amount));
                    showMessage("Successfully transferred " + currencyFormatter.format(amount) + " to " + recipient, 
                            "Transfer Successful", JOptionPane.INFORMATION_MESSAGE);
                    updateDashboard();
                } else {
                    showMessage("Insufficient funds for this transfer", "Transfer Failed", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (NumberFormatException e) {
                showMessage("Please enter a valid amount", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showRegistrationDialog() {
        JTextField newUserField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        JTextField initialDepositField = new JTextField("0.00");
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Username:"));
        panel.add(newUserField);
        panel.add(new JLabel("Password:"));
        panel.add(newPasswordField);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmPasswordField);
        panel.add(new JLabel("Initial Deposit ($):"));
        panel.add(initialDepositField);
        
        // Improve dialog buttons
        JOptionPane optionPane = new JOptionPane(panel, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION);
        
        JDialog dialog = optionPane.createDialog(frame, "Register New Account");
        
        // Make sure dialog buttons are visible
        Object[] options = ((JOptionPane)optionPane).getOptions();
        if (options != null) {
            for (Object option : options) {
                if (option instanceof JButton) {
                    JButton button = (JButton) option;
                    button.setFont(new Font("Arial", Font.BOLD, 14));
                    button.setForeground(Color.BLACK);
                }
            }
        }
        
        dialog.setVisible(true);
        
        Object result = optionPane.getValue();
        if (result != null && result instanceof Integer && (Integer)result == JOptionPane.OK_OPTION) {
            String newUser = newUserField.getText();
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            if (newUser.isEmpty() || newPassword.isEmpty()) {
                showMessage("Username and password cannot be empty", "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!newPassword.equals(confirmPassword)) {
                showMessage("Passwords do not match", "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (accounts.containsKey(newUser)) {
                showMessage("Username already exists", "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                double initialDeposit = Double.parseDouble(initialDepositField.getText());
                
                if (initialDeposit < 0) {
                    showMessage("Initial deposit cannot be negative", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                accounts.put(newUser, initialDeposit);
                passwords.put(newUser, newPassword);
                
                showMessage("Account successfully created!", "Registration Complete", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (NumberFormatException e) {
                showMessage("Please enter a valid amount for initial deposit", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showMessage(String message, String title, int messageType) {
        // Create a custom JOptionPane to ensure text visibility
        JOptionPane pane = new JOptionPane(message, messageType);
        pane.setMessageType(messageType);
        
        // Customize the look of the dialog
        JDialog dialog = pane.createDialog(frame, title);
        
        // Make sure all labels and buttons in the dialog are visible
        Component[] components = pane.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                Component[] subComponents = ((JPanel) component).getComponents();
                for (Component subComponent : subComponents) {
                    if (subComponent instanceof JLabel) {
                        ((JLabel) subComponent).setForeground(Color.BLACK);
                        ((JLabel) subComponent).setFont(new Font("Arial", Font.PLAIN, 14));
                    }
                    if (subComponent instanceof JButton) {
                        ((JButton) subComponent).setForeground(Color.BLACK);
                        ((JButton) subComponent).setFont(new Font("Arial", Font.BOLD, 14));
                    }
                }
            }
        }
        
        dialog.setVisible(true);
    }
    
    private void addToTransactionHistory(String transaction) {
        String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        transactionHistoryArea.insert(timestamp + " - " + transaction + "\n", 0);
    }
    
    public static void main(String[] args) {
        try {
            // Set default colors for standard Swing components for better visibility
            UIManager.put("Panel.background", new Color(240, 240, 240));
            UIManager.put("Label.foreground", Color.BLACK);
            UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));
            UIManager.put("TextField.foreground", Color.BLACK);
            UIManager.put("TextArea.foreground", Color.BLACK);
            UIManager.put("OptionPane.messageForeground", Color.BLACK);
            UIManager.put("Button.foreground", Color.BLACK);
            UIManager.put("Button.background", new Color(220, 220, 220));
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
            
            // Improve dialog button visibility
            UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 14));
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EnhancedBankingSystemGUI();
            }
        });
    }
}