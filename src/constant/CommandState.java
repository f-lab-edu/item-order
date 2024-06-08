package constant;

public enum CommandState {
    ITEM_Id(Constant.ITEM),
    QUANTITY(Constant.QUANTITY);

    private final String prompt;

    CommandState(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }
}
