package com.hristoforov.elective.controller.actions;

import com.hristoforov.elective.controller.context.AppContext;
import org.junit.jupiter.api.Test;

import static com.hristoforov.elective.constants.CRAPaths.LOGIN_PAGE_SERVLET;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ActionFactoryTest {

    @Test
    void testCreateAction() {
        AppContext.createAppContext();
        ActionFactory actionFactory = ActionFactory.getActionFactory();
        Action action = actionFactory.createAction(LOGIN_PAGE_SERVLET);
        assertInstanceOf(Action.class, action);
    }
}