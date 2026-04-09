import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Dashboardframe2 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }

    // ================= LOGIN PAGE =================
    static class LoginPage extends JFrame {

        JTextField username;
        JPasswordField password;

        LoginPage() {
            setTitle("Login");
            setSize(400, 250);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));

            username = new JTextField();
            password = new JPasswordField();

            JButton loginBtn = new JButton("Login");

            loginBtn.addActionListener(e -> login());

            panel.add(new JLabel("Username:"));
            panel.add(username);
            panel.add(new JLabel("Password:"));
            panel.add(password);
            panel.add(new JLabel(""));
            panel.add(loginBtn);

            add(panel);
            setVisible(true);
        }

        private void login() {
            if (username.getText().equals("admin") &&
                new String(password.getPassword()).equals("1234")) {

                JOptionPane.showMessageDialog(this, "Login Success");
                dispose();
                new Dashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }
        }
    }

    // ================= DASHBOARD =================
    static class Dashboard extends JFrame {

        private DefaultListModel<String> model;
        private JList<String> roomList;

        private JTextField tenant, electric, water, wifi, rent, balance;
        private JLabel title;

        private Room[] rooms = {
            new Room("Room 1", "", 0, 0, 0, 0),
            new Room("Room 2", "", 0, 0, 0, 0),
            new Room("Room 3", "", 0, 0, 0, 0),
            new Room("Room 4", "", 0, 0, 0, 0)
        };

        Dashboard() {
            setTitle("Boarding House System");
            setSize(1000, 600);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            setLayout(new BorderLayout());

            add(header(), BorderLayout.NORTH);
            add(body(), BorderLayout.CENTER);
            add(footer(), BorderLayout.SOUTH);

            setVisible(true);
        }

        // HEADER
        private JPanel header() {
            JPanel panel = new JPanel(new BorderLayout());
            JLabel lbl = new JLabel("BOARDING HOUSE RENTAL", JLabel.CENTER);
            lbl.setFont(new Font("Arial", Font.BOLD, 24));
            panel.add(lbl);
            return panel;
        }

        // BODY
        private JPanel body() {
            JPanel panel = new JPanel(new BorderLayout());

            // LEFT (ROOM LIST)
            model = new DefaultListModel<>();
            for (Room r : rooms) model.addElement(r.display());

            roomList = new JList<>(model);
            roomList.setSelectedIndex(0);
            roomList.addListSelectionListener(e -> loadRoom());

            panel.add(new JScrollPane(roomList), BorderLayout.WEST);

            // RIGHT (DETAILS)
            JPanel right = new JPanel();
            right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

            title = new JLabel();
            right.add(title);

            tenant = field();
            electric = field();
            water = field();
            wifi = field();
            rent = field();
            balance = field();
            balance.setEditable(false);

            right.add(row("Tenant", tenant));
            right.add(row("Electric", electric));
            right.add(row("Water", water));
            right.add(row("Wifi", wifi));
            right.add(row("Rent", rent));
            right.add(row("Balance", balance));

            JPanel buttons = new JPanel();

            JButton save = new JButton("Save");
            JButton remove = new JButton("Remove");
            JButton pay = new JButton("Pay");

            save.addActionListener(e -> save());
            remove.addActionListener(e -> removeTenant());
            pay.addActionListener(e -> processPayment());

            buttons.add(save);
            buttons.add(remove);
            buttons.add(pay);

            right.add(buttons);

            panel.add(right, BorderLayout.CENTER);

            loadRoom();
            return panel;
        }

        private JPanel row(String label, JTextField field) {
            JPanel p = new JPanel(new BorderLayout());
            p.add(new JLabel(label), BorderLayout.WEST);
            p.add(field, BorderLayout.CENTER);
            return p;
        }

        private JTextField field() {
            return new JTextField();
        }

        // FOOTER
        private JPanel footer() {
            JPanel panel = new JPanel();
            JButton logout = new JButton("Logout");

            logout.addActionListener(e -> {
                dispose();
                new LoginPage();
            });

            panel.add(logout);
            return panel;
        }

        // LOAD ROOM
        private void loadRoom() {
            int i = roomList.getSelectedIndex();
            if (i < 0) return;

            Room r = rooms[i];

            title.setText(r.name + " - " + (r.tenant.isEmpty() ? "Vacant" : r.tenant));

            tenant.setText(r.tenant);
            electric.setText("" + r.electric);
            water.setText("" + r.water);
            wifi.setText("" + r.wifi);
            rent.setText("" + r.rent);
            balance.setText("" + r.total());
        }

        // SAVE
        private void save() {
            int i = roomList.getSelectedIndex();
            Room r = rooms[i];

            r.tenant = tenant.getText();
            r.electric = Double.parseDouble(electric.getText());
            r.water = Double.parseDouble(water.getText());
            r.wifi = Double.parseDouble(wifi.getText());
            r.rent = Double.parseDouble(rent.getText());

            model.set(i, r.display());
            loadRoom();

            JOptionPane.showMessageDialog(this, "Saved!");
        }

        // REMOVE
        private void removeTenant() {
            int i = roomList.getSelectedIndex();
            Room r = rooms[i];

            r.tenant = "";
            r.electric = r.water = r.wifi = r.rent = 0;

            model.set(i, r.display());
            loadRoom();
        }

        // PAYMENT FLOW (FLOWCHART MATCH)
        private void processPayment() {
            int i = roomList.getSelectedIndex();
            Room r = rooms[i];

            double total = r.total();

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Total: ₱" + total + "\nConfirm Payment?",
                "Payment",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                showReceipt(r, total);

                r.electric = r.water = r.wifi = r.rent = 0;

                loadRoom();

                JOptionPane.showMessageDialog(this, "Payment Complete!");
            }
        }

        // RECEIPT
        private void showReceipt(Room r, double total) {
            String text =
                "===== RECEIPT =====\n" +
                "Room: " + r.name + "\n" +
                "Tenant: " + r.tenant + "\n" +
                "Amount: ₱" + total + "\n" +
                "Date: " + new java.util.Date() + "\n" +
                "Status: PAID\n";

            JTextArea area = new JTextArea(text);
            area.setEditable(false);

            JOptionPane.showMessageDialog(this, new JScrollPane(area));
        }
    }

    // ================= ROOM DATA =================
    static class Room {
        String name, tenant;
        double electric, water, wifi, rent;

        Room(String n, String t, double e, double w, double wi, double r) {
            name = n;
            tenant = t;
            electric = e;
            water = w;
            wifi = wi;
            rent = r;
        }

        double total() {
            return electric + water + wifi + rent;
        }

        String display() {
            return name + " - " + (tenant.isEmpty() ? "Vacant" : tenant);
        }
    }
}