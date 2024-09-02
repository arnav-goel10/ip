package darkpool.command;

import darkpool.util.Storage;
import darkpool.util.TaskList;
import darkpool.util.Ui;
import darkpool.util.DarkpoolException;

/**
 * Represents a command to mark a task in the task list as completed.
 */
public class MarkCommand extends Command {

    private final int index;

    /**
     * Constructs a MarkCommand with the specified task index.
     *
     * @param index The index of the task to be marked as completed.
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the mark command, marking the task as completed in the task list and updating the UI.
     *
     * @param taskList The task list containing the task to be marked.
     * @param ui The UI to update.
     * @param storage The storage (not used in this command).
     * @throws DarkpoolException If the task index is out of range.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DarkpoolException {
        if (index < 0 || index >= taskList.getSize()) {
            throw new DarkpoolException("\tdo you know how to count? the task number is out of range");
        }
        ui.mark(taskList.markTask(index));
    }

}