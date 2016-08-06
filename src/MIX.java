import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class MIX extends JPanel{
	
	public MIX() {
		setLayout(new BorderLayout());
		
		// JPanel for source code
		JPanel source = new JPanel();
		JButton assembleButton = new JButton("Assemble");
		assembleButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		source.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Source Code"), BorderFactory.createEmptyBorder(5,5,5,5)));
		source.setLayout(new BorderLayout());
		JTextArea input = new JTextArea();
		input.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		source.add(assembleButton, BorderLayout.PAGE_START);
		source.add(input);
		
		add(source, BorderLayout.CENTER);
		
		// JPanel for assembled instructions
		JPanel assembled = new JPanel();
		

		Object[][] memory = new Object[4000][6];
		for(int i = 0; i < 4000; i ++) {
			memory[i][0] = "+";
			for (int j = 1; j < 6; j++) {
				memory[i][j] = 0;
			}
		}
		String[] columnNames = {"+/-", "1", "2", "3", "4", "5"};

		JTable table = new JTable(memory, columnNames);
		JScrollPane sp = new JScrollPane(table);
		assembled.add(sp);
		assembled.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Assembled Instructions"),
				BorderFactory.createEmptyBorder(10,10,10,10)));
		add(assembled, BorderLayout.LINE_START);
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("MIX Assembler and Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(800,600));
		frame.add(new MIX());
		frame.pack();
		frame.setVisible(true);
	}
}