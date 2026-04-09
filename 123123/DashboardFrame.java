import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardFrame extends JFrame {

    private DefaultListModel<String> roomListModel;
    private JList<String> roomList;
    private JTextField tenantField;
    private JTextField electricField;
    private JTextField waterField;
    private JTextField wifiField;
    private JTextField rentField;
    private JTextField balanceField;
    private JLabel roomTitleLabel;
    private boolean isDarkMode = false;
    private JButton saveBtn;
    private JButton removeBtn;
    private JButton balanceBtn;

    private final RoomData[] roomData = new RoomData[] {
    new RoomData("Room - 1", "", "0", "0", "0", "0"),
    new RoomData("Room - 2", "", "0", "0", "0", "0"),
    new RoomData("Room - 3", "", "0", "0", "0", "0"),
    new RoomData("Room - 4", "", "0", "0", "0", "0"),
    new RoomData("Room - 5", "", "0", "0", "0", "0"),
    new RoomData("Room - 6", "", "0", "0", "0", "0")
};

    public DashboardFrame() {
        setTitle("BOARDING HOUSE RENTAL DAGUPAN");
        setSize(1100, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout(15, 15));
        content.setBorder(new EmptyBorder(15, 15, 15, 15));
        content.setBackground(Color.WHITE);

        content.add(createHeaderPanel(), BorderLayout.NORTH);
        content.add(createBodyPanel(), BorderLayout.CENTER);
        content.add(createFooterPanel(), BorderLayout.SOUTH);

        add(content);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardFrame());
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        JLabel title = new JLabel("BOARDING HOUSE RENTAL DAGUPAN", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton settingsBtn = new JButton("Settings ▾");
        settingsBtn.setBackground(new Color(240, 240, 240));
        settingsBtn.setFocusPainted(false);
        settingsBtn.addActionListener(e -> showSettingsMenu(settingsBtn));

        header.add(title, BorderLayout.CENTER);
        header.add(settingsBtn, BorderLayout.EAST);
        return header;
    }

    private void showSettingsMenu(Component invoker) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem darkLight = new JMenuItem(isDarkMode ? "Switch to Light Mode" : "Switch to Dark Mode");
        JMenuItem language = new JMenuItem("Language");
        JMenuItem report = new JMenuItem("Report Problem");

        darkLight.addActionListener(e -> toggleDarkMode());
        language.addActionListener(e -> showLanguageDialog());
        report.addActionListener(e -> showReportDialog());

        menu.add(darkLight);
        menu.add(language);
        menu.add(report);
        menu.show(invoker, 0, invoker.getHeight());
    }

    private JPanel createBodyPanel() {
        JPanel body = new JPanel(new BorderLayout(15, 15));
        body.setBackground(Color.WHITE);

        roomListModel = new DefaultListModel<>();
        for (RoomData room : roomData) {
            roomListModel.addElement(room.displayText());
        }

        roomList = new JList<>(roomListModel);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomList.setSelectedIndex(0);
        roomList.setFont(new Font("Arial", Font.PLAIN, 16));
        roomList.setFixedCellHeight(45);
        roomList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateRoomDetails(roomList.getSelectedIndex());
            }
        });

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(BorderFactory.createTitledBorder("Rooms"));
        listPanel.setPreferredSize(new Dimension(260, 0));
        listPanel.add(new JScrollPane(roomList), BorderLayout.CENTER);

        JButton addTenantBtn = new JButton("Add Tenant");
        addTenantBtn.setBackground(new Color(76, 175, 80));
        addTenantBtn.setForeground(Color.WHITE);
        addTenantBtn.setFocusPainted(false);
        addTenantBtn.addActionListener(e -> addTenant());

        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addPanel.setBackground(Color.WHITE);
        addPanel.add(addTenantBtn);
        listPanel.add(addPanel, BorderLayout.SOUTH);

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBackground(Color.WHITE);
        detailPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Room Details"),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        roomTitleLabel = new JLabel();
        roomTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        roomTitleLabel.setBorder(new EmptyBorder(5, 5, 15, 5));
        detailPanel.add(roomTitleLabel);

        tenantField = createField(false);
        electricField = createField(true);
        waterField = createField(true);
        wifiField = createField(true);
        rentField = createField(true);
        balanceField = createField(false);

        detailPanel.add(createLabeledField("Tenant", tenantField));
        detailPanel.add(createLabeledField("Electric", electricField));
        detailPanel.add(createLabeledField("Water", waterField));
        detailPanel.add(createLabeledField("WiFi", wifiField));
        detailPanel.add(createLabeledField("Room Rent", rentField));
        detailPanel.add(createLabeledField("Balance", balanceField));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        saveBtn = new JButton("Save");
        removeBtn = new JButton("Remove");
        balanceBtn = new JButton("Balance");

        saveBtn.setBackground(new Color(76, 175, 80));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> saveRoom());

        removeBtn.setBackground(new Color(244, 67, 54));
        removeBtn.setForeground(Color.WHITE);
        removeBtn.setFocusPainted(false);
        removeBtn.addActionListener(e -> removeTenant());

        balanceBtn.setBackground(new Color(255, 193, 7));
        balanceBtn.setForeground(Color.BLACK);
        balanceBtn.setFocusPainted(false);
        balanceBtn.addActionListener(e -> showBalance());

        buttonPanel.add(saveBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(balanceBtn);
        detailPanel.add(buttonPanel);

        body.add(listPanel, BorderLayout.WEST);
        body.add(detailPanel, BorderLayout.CENTER);

        updateRoomDetails(0);
        return body;
    }

    private JPanel createLabeledField(String labelText, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel label = new JLabel(labelText + ":");
        label.setPreferredSize(new Dimension(120, 25));
        label.setFont(new Font("Arial", Font.PLAIN, 14));

        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private JTextField createField(boolean editable) {
        JTextField field = new JTextField();
        field.setEditable(editable);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(editable ? Color.WHITE : new Color(245, 245, 245));
        field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        return field;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(5, 10, 5, 10));

        JButton signOut = new JButton("Sign Out");
        signOut.setBackground(new Color(244, 67, 54));
        signOut.setForeground(Color.WHITE);
        signOut.setFocusPainted(false);
        signOut.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        footer.add(signOut, BorderLayout.EAST);
        return footer;
    }

    private void updateRoomDetails(int index) {
        if (index < 0 || index >= roomData.length) {
            return;
        }
        RoomData room = roomData[index];
        roomTitleLabel.setText(room.roomName + " — " + (room.tenant.isEmpty() ? "Vacant" : room.tenant));
        tenantField.setText(room.tenant);
        electricField.setText(room.electric);
        waterField.setText(room.water);
        wifiField.setText(room.wifi);
        rentField.setText(room.rent);
        balanceField.setText(room.balance);
    }

    private void saveRoom() {
        int index = roomList.getSelectedIndex();
        if (index < 0) return;
        RoomData room = roomData[index];
        room.electric = electricField.getText();
        room.water = waterField.getText();
        room.wifi = wifiField.getText();
        room.rent = rentField.getText();
        room.balance = calculateBalance(room);
        balanceField.setText(room.balance);
        roomListModel.set(index, room.displayText());
        JOptionPane.showMessageDialog(this, "Room details saved.");
    }

    private void removeTenant() {
        int index = roomList.getSelectedIndex();
        if (index < 0) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this tenant?", "Confirm Remove", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            RoomData room = roomData[index];
            room.tenant = "";
            room.electric = "0";
            room.water = "0";
            room.wifi = "0";
            room.rent = "0";
            room.balance = "0";
            updateRoomDetails(index);
            roomListModel.set(index, room.displayText());
        }
    }

    private void showBalance() {
        int index = roomList.getSelectedIndex();
        if (index < 0) return;
        RoomData room = roomData[index];
        room.balance = calculateBalance(room);
        balanceField.setText(room.balance);
        JOptionPane.showMessageDialog(this, "Current balance for " + room.roomName + ": " + room.balance);
    }

    private String calculateBalance(RoomData room) {
        try {
            double electric = Double.parseDouble(room.electric);
            double water = Double.parseDouble(room.water);
            double wifi = Double.parseDouble(room.wifi);
            double rent = Double.parseDouble(room.rent);
            return String.format("%.2f", electric + water + wifi + rent);
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    private void addTenant() {
        int index = roomList.getSelectedIndex();
        if (index < 0) return;

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField nameInput = new JTextField();
        JTextField rentInput = new JTextField();
        JTextField roomInput = new JTextField(roomData[index].roomName);
        roomInput.setEditable(false);

        panel.add(new JLabel("Tenant Name:"));
        panel.add(nameInput);
        panel.add(new JLabel("Room Rent:"));
        panel.add(rentInput);
        panel.add(new JLabel("Room:"));
        panel.add(roomInput);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Tenant", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !nameInput.getText().trim().isEmpty()) {
            RoomData room = roomData[index];
            room.tenant = nameInput.getText().trim();
            if (!rentInput.getText().trim().isEmpty()) {
                room.rent = rentInput.getText().trim();
            }
            room.balance = calculateBalance(room);
            updateRoomDetails(index);
            roomListModel.set(index, room.displayText());
        }
    }

    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        applyTheme();
        JOptionPane.showMessageDialog(this, "Switched to " + (isDarkMode ? "Dark" : "Light") + " mode");
    }

    private void applyTheme() {
        Color bgColor = isDarkMode ? new Color(45, 45, 45) : Color.WHITE;
        Color fgColor = isDarkMode ? Color.WHITE : Color.BLACK;
        Color panelBg = isDarkMode ? new Color(60, 60, 60) : Color.WHITE;
        Color borderColor = isDarkMode ? new Color(100, 100, 100) : new Color(200, 200, 200);

        // Update main content
        getContentPane().setBackground(bgColor);

        // Update header
        JPanel header = (JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(0);
        header.setBackground(bgColor);
        for (Component c : header.getComponents()) {
            if (c instanceof JLabel) {
                c.setForeground(fgColor);
            } else if (c instanceof JButton) {
                c.setBackground(isDarkMode ? new Color(70, 70, 70) : new Color(240, 240, 240));
                c.setForeground(fgColor);
            }
        }

        // Update body
        JPanel body = (JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(1);
        body.setBackground(bgColor);

        // Update room list panel
        JPanel listPanel = (JPanel) body.getComponent(0);
        listPanel.setBackground(panelBg);
        roomList.setBackground(isDarkMode ? new Color(50, 50, 50) : Color.WHITE);
        roomList.setForeground(fgColor);

        // Update detail panel
        JPanel detailPanel = (JPanel) body.getComponent(1);
        detailPanel.setBackground(panelBg);
        roomTitleLabel.setForeground(fgColor);

        // Update all labels and text fields in detail panel
        updateComponentColors(detailPanel, fgColor, borderColor, isDarkMode);

        // Keep specific button colors unchanged
        saveBtn.setBackground(new Color(76, 175, 80));
        saveBtn.setForeground(Color.WHITE);
        removeBtn.setBackground(new Color(244, 67, 54));
        removeBtn.setForeground(Color.WHITE);
        balanceBtn.setBackground(new Color(255, 193, 7));
        balanceBtn.setForeground(Color.BLACK);

        // Update footer
        JPanel footer = (JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(2);
        footer.setBackground(bgColor);
        for (Component c : footer.getComponents()) {
            if (c instanceof JButton) {
                c.setBackground(isDarkMode ? new Color(70, 70, 70) : new Color(244, 67, 54));
                c.setForeground(fgColor);
            }
        }

        repaint();
    }

    private void updateComponentColors(Component component, Color fgColor, Color borderColor, boolean isDark) {
        if (component instanceof JPanel) {
            component.setBackground(isDark ? new Color(60, 60, 60) : Color.WHITE);
            for (Component child : ((JPanel) component).getComponents()) {
                updateComponentColors(child, fgColor, borderColor, isDark);
            }
        } else if (component instanceof JLabel) {
            component.setForeground(fgColor);
        } else if (component instanceof JTextField) {
            component.setBackground(isDark ? new Color(80, 80, 80) : Color.WHITE);
            component.setForeground(fgColor);
            ((JTextField) component).setBorder(BorderFactory.createLineBorder(borderColor));
        } else if (component instanceof JButton) {
            component.setBackground(isDark ? new Color(70, 70, 70) : component.getBackground());
            component.setForeground(fgColor);
        }
    }

    private void showLanguageDialog() {
        JDialog dialog = new JDialog(this, "Select Language", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Choose your preferred language:");
        title.setFont(new Font("Arial", Font.BOLD, 16));

        String[] languages = {
            "English", "Spanish (Español)", "French (Français)", "German (Deutsch)",
            "Italian (Italiano)", "Portuguese (Português)", "Russian (Русский)",
            "Japanese (日本語)", "Chinese (中文)", "Korean (한국어)",
            "Arabic (العربية)", "Hindi (हिन्दी)", "Bengali (বাংলা)",
            "Punjabi (ਪੰਜਾਬੀ)", "Javanese (ꦧꦱꦗꦮ)", "Turkish (Türkçe)",
            "Vietnamese (Tiếng Việt)", "Polish (Polski)", "Ukrainian (Українська)",
            "Dutch (Nederlands)"
        };

        JList<String> languageList = new JList<>(languages);
        languageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        languageList.setSelectedIndex(0); // Default to English

        JScrollPane scrollPane = new JScrollPane(languageList);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        okButton.addActionListener(e -> {
            String selectedLanguage = languageList.getSelectedValue();
            if (selectedLanguage != null) {
                JOptionPane.showMessageDialog(dialog,
                    "Language changed to: " + selectedLanguage + "\n\nNote: Language change will take effect after restart.",
                    "Language Updated", JOptionPane.INFORMATION_MESSAGE);
            }
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showReportDialog() {
        JDialog dialog = new JDialog(this, "Report Problem", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Report a Problem");
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Problem type
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Problem Type:"), gbc);
        gbc.gridx = 1;
        String[] problemTypes = {"Technical Issue", "Billing Problem", "Maintenance Request",
                                "Security Concern", "Noise Complaint", "Cleanliness Issue", "Other"};
        JComboBox<String> problemTypeCombo = new JComboBox<>(problemTypes);
        formPanel.add(problemTypeCombo, gbc);

        // Room number
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Room Number (optional):"), gbc);
        gbc.gridx = 1;
        JTextField roomField = new JTextField(10);
        formPanel.add(roomField, gbc);

        // Description
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 1;
        JTextArea descriptionArea = new JTextArea(5, 30);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        formPanel.add(descScroll, gbc);

        // Contact info
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Your Contact Info:"), gbc);
        gbc.gridx = 1;
        JTextField contactField = new JTextField(20);
        formPanel.add(contactField, gbc);

        // Urgency
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Urgency:"), gbc);
        gbc.gridx = 1;
        String[] urgencyLevels = {"Low", "Medium", "High", "Emergency"};
        JComboBox<String> urgencyCombo = new JComboBox<>(urgencyLevels);
        urgencyCombo.setSelectedIndex(1); // Default to Medium
        formPanel.add(urgencyCombo, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton submitButton = new JButton("Submit Report");
        JButton cancelButton = new JButton("Cancel");

        submitButton.addActionListener(e -> {
            String problemType = (String) problemTypeCombo.getSelectedItem();
            String room = roomField.getText().trim();
            String description = descriptionArea.getText().trim();
            String contact = contactField.getText().trim();
            String urgency = (String) urgencyCombo.getSelectedItem();

            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please provide a description of the problem.",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Here you would typically save to database or send to server
            String report = String.format(
                "Problem Report Submitted:\n\n" +
                "Type: %s\n" +
                "Room: %s\n" +
                "Urgency: %s\n" +
                "Description: %s\n" +
                "Contact: %s\n\n" +
                "Report ID: RPT-%d\n" +
                "Status: Submitted for review",
                problemType,
                room.isEmpty() ? "Not specified" : room,
                urgency,
                description,
                contact.isEmpty() ? "Not provided" : contact,
                System.currentTimeMillis() % 100000
            );

            JOptionPane.showMessageDialog(dialog, report, "Report Submitted", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        panel.add(title, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private static class RoomData {
        String roomName;
        String tenant;
        String electric;
        String water;
        String wifi;
        String rent;
        String balance;

        RoomData(String roomName, String tenant, String electric, String water, String wifi, String rent) {
            this.roomName = roomName;
            this.tenant = tenant;
            this.electric = electric;
            this.water = water;
            this.wifi = wifi;
            this.rent = rent;
            this.balance = calculate(this);
        }

        static String calculate(RoomData room) {
            try {
                double electric = Double.parseDouble(room.electric);
                double water = Double.parseDouble(room.water);
                double wifi = Double.parseDouble(room.wifi);
                double rent = Double.parseDouble(room.rent);
                return String.format("%.2f", electric + water + wifi + rent);
            } catch (NumberFormatException e) {
                return "0";
            }
        }

        String displayText() {
            return roomName + " - " + (tenant.isEmpty() ? "Vacant" : tenant);
        }
    }
}

