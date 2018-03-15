package eu.epitech.todolist;

import eu.epitech.todolist.Activities.MainActivity;
import eu.epitech.todolist.POJO.Task;
import org.junit.Test;

import static org.junit.Assert.*;


public class TaskUnitTest {

    private Task createTaskTest() {
        Task task = new Task();
        task.setTitle("Test");
        task.setId(5);
        task.setState("0");
        task.setCategory("0");
        task.setDate("20/05/2018");
        task.setTime("15:30");
        return task;
    }

    @Test
    public void TestTaskData() throws Exception {
        Task task = createTaskTest();
        assertEquals("Test", task.getTitle());
        assertEquals(5, task.getId());
        assertEquals("0", task.getCategory());
        assertEquals("0", task.getState());
        assertEquals("20/05/2018", task.getDate());
        assertEquals("15:30", task.getTime());
    }

}
