package zad3;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;

public class TaskList extends JFrame {

    private final JList<FutureTask<String>> taskList;
    private final DefaultListModel<FutureTask<String>> listModel;
    private int index;
    public TaskList()
    {
        this.listModel = new DefaultListModel<>();
        this.taskList = new JList<>(listModel);

        setTitle("Task List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton newTaskButton = new JButton("Add Task");
        newTaskButton.addActionListener(e -> addNewTask());

        JButton statusButton = new JButton("Check Status");
        statusButton.addActionListener(e -> checkStatus());

        JButton cancelButton = new JButton("Cancel Task");
        cancelButton.addActionListener(e -> cancelTask());

        JButton resultButton = new JButton("Show Result");
        resultButton.addActionListener(e -> showResult());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));
        buttonPanel.add(newTaskButton);
        buttonPanel.add(statusButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(resultButton);

        add(new JScrollPane(taskList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(640,480);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addNewTask()
    {
        Callable<String> newTask = () -> {
            Thread.sleep(2500);
            return "RESULT";
        };

        FutureTask<String> task = new FutureTask<>(newTask);
        listModel.addElement(task);

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(task);
        service.shutdown();
    }

    public void checkStatus()
    {
        index = taskList.getSelectedIndex();
        if (index != -1) {
            FutureTask<String> selectedTask = listModel.getElementAt(index);
            if (selectedTask.isDone())
                JOptionPane.showMessageDialog(this, "TASK DONE");
            else if (selectedTask.isCancelled())
                JOptionPane.showMessageDialog(this, "TASK CANCELLED");
            else
                JOptionPane.showMessageDialog(this, "TASK RUNNING");
        }
    }

    public void cancelTask()
    {
        index = taskList.getSelectedIndex();
        if (index != -1) {
            FutureTask<String> selectedTask = listModel.getElementAt(index);
            if (!selectedTask.isDone()) {
                selectedTask.cancel(true);
                listModel.remove(index);
            } else
                JOptionPane.showMessageDialog(this, "TASK DONE ALREADY.");
        }
    }
    public void showResult()
    {
        index = taskList.getSelectedIndex();
        if (index != -1) {
            FutureTask<String> selectedTask = listModel.getElementAt(index);
            try {
                String result = selectedTask.get();
                JOptionPane.showMessageDialog(this, "TASK RESULT: " + result);
            } catch (InterruptedException | ExecutionException e) {
                JOptionPane.showMessageDialog(this, "ERROR");
            }
        }
    }
}
