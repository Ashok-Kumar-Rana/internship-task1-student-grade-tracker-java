import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentGradeTrackerGUI {

    private JTextField nameField;
    private JTextField marksField;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private ArrayList<Student> studentList;

    public StudentGradeTrackerGUI() {
        JFrame frame = new JFrame("Student Grade Tracker");
        frame.setSize(500, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel("Student Name:");
        nameField = new JTextField(20);

        JLabel marksLabel = new JLabel("Marks:");
        marksField = new JTextField(5);

        JButton addButton = new JButton("Add Student");
        JButton summaryButton = new JButton("Show Summary");

        // Table columns: No., Student Name, Marks
        String[] columnNames = {"No.", "Student Name", "Marks"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Make cells non-editable
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reportTable = new JTable(tableModel);

        // Styling JTable
        reportTable.setFont(new Font("Times New Roman", Font.BOLD, 14));
        reportTable.setForeground(new Color(25, 25, 112));
        reportTable.setBackground(new Color(230, 230, 250));
        reportTable.setRowHeight(25);

        // Border for JTable
        reportTable.setBorder(new LineBorder(new Color(25, 25, 112), 2));

        // Center align No. column (index 0)
        DefaultTableCellRenderer centerRendererNo = new DefaultTableCellRenderer();
        centerRendererNo.setHorizontalAlignment(JLabel.CENTER);
        reportTable.getColumnModel().getColumn(0).setCellRenderer(centerRendererNo);

        // Center align Marks column (index 2)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        reportTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        // Header font and color
        reportTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
        reportTable.getTableHeader().setForeground(new Color(25, 25, 112));

        // Set preferred width for columns as per request
        reportTable.getColumnModel().getColumn(0).setPreferredWidth(40);   // No. column - smallest
        reportTable.getColumnModel().getColumn(1).setPreferredWidth(300);  // Student Name - largest
        reportTable.getColumnModel().getColumn(2).setPreferredWidth(80);   // Marks - medium

        // Prevent column resizing
        reportTable.getColumnModel().getColumn(0).setResizable(false);
        reportTable.getColumnModel().getColumn(1).setResizable(false);
        reportTable.getColumnModel().getColumn(2).setResizable(false);

        JScrollPane scrollPane = new JScrollPane(reportTable);
        scrollPane.setPreferredSize(new Dimension(460, 250));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        studentList = new ArrayList<>();

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(marksLabel);
        frame.add(marksField);
        frame.add(addButton);
        frame.add(summaryButton);
        frame.add(scrollPane);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String marksText = marksField.getText().trim();

                if (name.isEmpty() || marksText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both name and marks.");
                    return;
                }

                try {
                    int marks = Integer.parseInt(marksText);
                    studentList.add(new Student(name, marks));
                    updateTableData(studentList);

                    nameField.setText("");
                    marksField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Marks must be a number.");
                }
            }
        });

        summaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (studentList.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No students added yet.");
                    return;
                }

                double avg = StudentProcessor.calculateAverageMarks(studentList);
                int high = StudentProcessor.findHighestMarks(studentList);
                int low = StudentProcessor.findLowestMarks(studentList);

                StringBuilder summary = new StringBuilder();
                summary.append("===== Summary Report =====\n\n");
                summary.append(String.format("%-5s %-20s %5s\n", "No.", "Student Name", "Marks"));
                summary.append("----------------------------------------------\n");
                int count = 1;
                for (Student s : studentList) {
                    summary.append(String.format("%-5d %-20s %5d\n", count++, s.getStudentName(), s.getStudentMarks()));
                }
                summary.append("----------------------------------------------\n");
                summary.append("Average Marks: " + String.format("%.2f", avg) + "\n");
                summary.append("Highest Marks: " + high + "\n");
                summary.append("Lowest Marks : " + low + "\n");

                JOptionPane.showMessageDialog(frame, summary.toString(), "Summary", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    private void updateTableData(ArrayList<Student> list) {
        // Clear existing rows
        tableModel.setRowCount(0);

        int serial = 1;
        for (Student s : list) {
            Object[] row = {serial++, s.getStudentName(), s.getStudentMarks()};
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        new StudentGradeTrackerGUI();
    }
}
