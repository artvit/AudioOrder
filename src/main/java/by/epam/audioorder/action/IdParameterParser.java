package by.epam.audioorder.action;

public final class IdParameterParser {
    private long result;

    public boolean parse(String idParameter) {
        if (idParameter != null) {
            try {
                result = Long.parseLong(idParameter);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public long getResult() {
        return result;
    }
}
