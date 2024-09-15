package darkpool.tasklist;

import darkpool.task.Task;

import java.util.ArrayList;

class ToFileString {
    static String toFileString(ArrayList<Task> taskList) {
        StringBuilder fileString = new StringBuilder();

        for (Task task : taskList) {
            fileString.append(task.toFileString());
        }

        return String.valueOf(fileString);
    }
}
