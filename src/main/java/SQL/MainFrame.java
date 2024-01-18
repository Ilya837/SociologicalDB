package SQL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MainFrame extends JFrame {

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
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        new MainFrame();
    }
}
