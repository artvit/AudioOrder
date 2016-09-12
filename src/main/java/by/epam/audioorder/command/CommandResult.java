package by.epam.audioorder.command;

public class CommandResult {
    private String address;
    private Type type;

    public CommandResult(String address, Type type) {
        this.address = address;
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static enum Type {
        REDIRECT, FORWARD
    }
}
