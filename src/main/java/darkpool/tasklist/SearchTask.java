package darkpool.tasklist;

import darkpool.task.Task;
import darkpool.util.DarkpoolException;

import java.util.ArrayList;

public class SearchTask {
    static public String searchTask(ArrayList<Task> taskList, String searchQuery) throws DarkpoolException {
        if (taskList.isEmpty()) {
            throw new DarkpoolException("bozo you got no tasks");
        } else if (searchQuery.isEmpty()) {
            throw new DarkpoolException("bozo you gotta tell me what to search for");
        }

        StringBuilder temp = new StringBuilder("fine! here are the matching tasks\n");

        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getDescription().contains(searchQuery)) {
                temp.append((i + 1)).append(". ").append(taskList.get(i).toString()).append("\n");
            }
        }

        temp.setLength(temp.length());

        if (temp.toString().equals("fine! here are the matching tasks\n")) {
            return "bozo there are no matching tasks";
        }

        return String.valueOf(temp);
    }

}
