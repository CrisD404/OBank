package controller;

import lombok.Data;
import model.UserModel;
import ui.Layout;
import ui.Welcome;

public @Data class WelcomeController {
    private UserModel userModel;
    Welcome view = new Welcome();

    public WelcomeController(UserModel userModel) {
        this.userModel = userModel;

        view.getIClientButton().addActionListener(e -> this.handleSubmit(false));
        view.getIEmployeeIcon().addActionListener(e -> this.handleSubmit(true));
    }



    public void handleSubmit(Boolean isEmployee) {
        System.out.println("Going to submit the user");
        //TODO: it is necessary?
        userModel.setIsEmployee(isEmployee);

        String nextPanel = isEmployee ? "Login" : "SplashBeforeClientLogin";
        Layout.getInstance().showPanel(nextPanel);
    }

}
