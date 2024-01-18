package SQL;

import SQL.repository.WilliamsTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainFrame extends JFrame {

    private int[] calcVariation(int[] borders, String column) {
        int[] res;
        String col = "";
        switch (column){
            case "Любознательность": col="inquisitiveness"; break;
            case "Воображение": col="imagination"; break;
            case "Сложность": col="complexity"; break;
            case "Склонность к риску": col="risk_appetite"; break;
        }
        try {
            WilliamsTable williamsTable = new WilliamsTable();
            res = williamsTable.Variation(borders,col);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public MainFrame() {
        super("SociologicalDB");

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel tab1 = new JPanel();
        JPanel tab2 = new JPanel(new BorderLayout());

        //Заполение второй вкладки
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        //Компоненты второй вкладки
        JLabel labelTable = new JLabel("Выберите таблицу:");
        String[] tables = {"Вильямс"};
        JComboBox<String> comboBoxTable = new JComboBox<>(tables);
        JLabel labelIndicator = new JLabel("Выберите показатель:");
        String[] params = {"Любознательность", "Воображение", "Сложность", "Склонность к риску"};
        JComboBox<String> comboBoxIndicator = new JComboBox<>(params);
        JButton calculateButton = new JButton("Посчитать вариационный ряд");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(labelTable, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(comboBoxTable, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(labelIndicator, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(comboBoxIndicator, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(calculateButton, gbc);

        tab2.add(contentPanel, BorderLayout.NORTH);


        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] bords = { 0, 6, 12, 18 };
                int[] counts = calcVariation(bords,comboBoxIndicator.getSelectedItem().toString());

                DefaultTableModel tableModel = new DefaultTableModel();
                tableModel.addColumn("Интервал");
                tableModel.addColumn("Количество");
                String[] intervals = {"Меньше чем " + bords[0], "От " + bords[0] + " до " + (bords[1]-1),
                        "От " + bords[1] + " до " + (bords[2]-1), "От " + bords[2] + " до " + (bords[3]-1),
                        "Больше чем " + bords[3]};

                for (int i = 0; i < intervals.length; i++) {
                    Object[] rowData = {intervals[i], counts[i]};
                    tableModel.addRow(rowData);
                }

                Component[] components = tab2.getComponents();
                for (Component component : components) {
                    if (component instanceof JScrollPane) {
                        tab2.remove(component);
                    }
                }

                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                tab2.add(scrollPane, BorderLayout.CENTER);

                tab2.revalidate();
                tab2.repaint();
            }
        });

        tabbedPane.addTab("Корреляция значений", tab1);
        tabbedPane.addTab("Вариационный ряд", tab2);
        add(tabbedPane);

        //Отображение логотипа
        ImageIcon imageIcon = new ImageIcon(".\\src\\main\\java\\resources\\unn_logo.png");
        int maxWidth = 80;
        int maxHeight = 80;
        int width = imageIcon.getIconWidth();
        int height = imageIcon.getIconHeight();
        if (width > maxWidth || height > maxHeight) {
            double scale = Math.min((double) maxWidth / width, (double) maxHeight / height);
            width = (int) (width * scale);
            height = (int) (height * scale);
        }
        JLabel imageLabel = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.SOUTH);

        //Настройки окна
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        new MainFrame();
    }
}
