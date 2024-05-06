package springchatapp.demo.model.value.object;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class TaskName {
    private final String taskName;

    public static TaskName noValidateOf(String taskname) {
        return Objects.isNull(taskname) ? new TaskName("") : new TaskName(taskname);
    }

    public String getValue() {
        return this.taskName;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TaskName taskName = (TaskName) o;
//        return Objects.equals(this.taskName, taskName.taskName);
//    }
}
