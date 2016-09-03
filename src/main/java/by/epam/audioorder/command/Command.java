package by.epam.audioorder.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public interface Command {
    CommandResult execute(HttpServletRequest request, HttpServletResponse response);
}
