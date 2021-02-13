/**
Design Pattern - Command
All classes and interfaces are in a single file for quick copy-paste and test run of the code.

You may represent the classes and interfaces in separate Java files:

commands
|_ Command.java
|_ CopyCommand.java
|_ PasteCommand.java
|_ CutCommand.java
|_ CommandHistory.java

editor
|_ Editor.java

Main.java

Source: https://refactoring.guru/design-patterns/command/java/example
**/

import java.util.Stack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Command declares and interface for all commands
abstract class Command {
  public Editor editor;
  private String backup;

  Command(Editor editor) {
    this.editor = editor;
  }

  void backup() {
    backup = editor.textField.getText();
  }

  public void undo() {
    editor.textField.setText(backup);
  }

  public abstract boolean execute();
}

// CopyCommand (a ConcreteCommand) defintes binding between action/s (getSelectedText()) and a Receiver (Editor)
class CopyCommand extends Command {

  public CopyCommand(Editor editor) {
    super(editor);
  }

  @Override
  public boolean execute() {
    editor.clipboard = editor.textField.getSelectedText();
    return false;
  }
}

// PasteCommand (a ConcreteCommand) defintes binding between action/s (insert() and getCaretPosition()) and a Receiver (Editor)
class PasteCommand extends Command {

  public PasteCommand(Editor editor) {
    super(editor);
  }

  @Override
  public boolean execute() {
    if (editor.clipboard == null || editor.clipboard.isEmpty()) return false;

    backup();
    editor.textField.insert(editor.clipboard, editor.textField.getCaretPosition());
    return true;
  }
}

// CutCommand (a ConcreteCommand) defintes binding between action/s (getText(), getSelectedText(), and setText()) and a Receiver (Editor)
class CutCommand extends Command {

  public CutCommand(Editor editor) {
      super(editor);
  }

  @Override
  public boolean execute() {
    if (editor.textField.getSelectedText().isEmpty()) return false;

    backup();
    String source = editor.textField.getText();
    editor.clipboard = editor.textField.getSelectedText();
    editor.textField.setText(cutString(source));
    return true;
  }

  private String cutString(String source) {
    String start = source.substring(0, editor.textField.getSelectionStart());
    String end = source.substring(editor.textField.getSelectionEnd());
    return start + end;
  }
}

// CommandHistory to track/revert text edits
class CommandHistory {
  private Stack<Command> history = new Stack<>();

  public void push(Command c) {
    history.push(c);
  }

  public Command pop() {
    return history.pop();
  }

  public boolean isEmpty() { return history.isEmpty(); }
}

/*
Receiver (Editor) knows how to perform the work needed to carryout the request. 

Editor is also the Invoker
Invoker holds a command and at some point asks the command 
to carry out a request by calling its execute() method
*/
// GUI of text editor
class Editor {
  public JTextArea textField;
  public String clipboard;
  private CommandHistory history = new CommandHistory();

  public void init() {
    JFrame frame = new JFrame("Text editor (type & use buttons, Luke!)");
    JPanel content = new JPanel();
    frame.setContentPane(content);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    textField = new JTextArea();
    textField.setLineWrap(true);
    content.add(textField);
    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton ctrlC = new JButton("Ctrl+C");
    JButton ctrlX = new JButton("Ctrl+X");
    JButton ctrlV = new JButton("Ctrl+V");
    JButton ctrlZ = new JButton("Ctrl+Z");
    Editor editor = this;
    ctrlC.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        executeCommand(new CopyCommand(editor));
      }
    });
    ctrlX.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        executeCommand(new CutCommand(editor));
      }
    });
    ctrlV.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        executeCommand(new PasteCommand(editor));
      }
    });
    ctrlZ.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        undo();
      }
    });
    buttons.add(ctrlC);
    buttons.add(ctrlX);
    buttons.add(ctrlV);
    buttons.add(ctrlZ);
    content.add(buttons);
    frame.setSize(450, 200);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private void executeCommand(Command command) {
    if (command.execute()) {
      history.push(command);
    }
  }

  private void undo() {
    if (history.isEmpty()) return;

    Command command = history.pop();
    if (command != null) {
      command.undo();
    }
  }
}

class Main {
  public static void main(String[] args) {
    Editor editor = new Editor();
    editor.init();
  }
}
